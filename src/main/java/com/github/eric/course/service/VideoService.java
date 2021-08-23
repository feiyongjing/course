package com.github.eric.course.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.github.eric.course.annotation.PermissionRequired;
import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.dao.VideoDao;
import com.github.eric.course.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    public VideoDao videoDao;

    @Autowired
    public CourseDao courseDao;
    @Autowired
    CourseOrderService courseOrderService;

    @Value("${oss.accessKeyId}")
    public String accessKeyId;

    @Value("${oss.accessKeySecret}")
    public String accessKeySecret;
    @Value("${oss.endpoint}")
    public String endpoint;
    @Value("${oss.bucket}")
    public String bucket;

    @PermissionRequired(value = {"课程管理"})
    public Video updateVideo(Integer videoId, Video video) {
        Video videoInDb = videoDao.findById(videoId)
                .orElseThrow(() -> new HttpException(404, "视频不存在！"));
        videoInDb.setName(video.getName());
        videoInDb.setDescription(video.getDescription());

        return videoDao.saveAndFlush(videoInDb);
    }

    @PermissionRequired(value = {"课程管理"})
    public Video createdVideo(Integer courseId, Video video) {
        Course courseInDb = courseDao.
                findById(courseId).orElseThrow(() -> new HttpException(404, "课程不存在！"));
        video.setCourse(courseInDb);
        video.setStatus(Status.OK);
        courseInDb.getVideos().add(video);
        courseDao.saveAndFlush(courseInDb);
        return video;
    }

    @PermissionRequired(value = {"课程管理"})
    public void deleteVideoByVideoId(Integer id, HttpServletResponse response) {
        Video videoInDb=videoDao.findById(id).orElseThrow(() -> new HttpException(404, "视频不存在！"));
        videoInDb.setStatus(Status.DELETED);
        videoDao.saveAndFlush(videoInDb);
        response.setStatus(204);
    }

    public Video getVideoByVideoId(Integer id) {
        Video result = videoDao.findByIdAndStatus(id,Status.OK)
                .orElseThrow(() -> new HttpException(404, "视频不存在！"));
        whetherCoursePay(result);
        return result;
    }

    private void whetherCoursePay(Video video) {
        User currentUser = UserContext.getCurrentUser();
        CourseOrder courseOrder = courseOrderService
                .getCourseOrderByCourseIdAndUserId(video.getCourse().getId(), currentUser.getId());
        if (courseOrder.getStatus()== Status.PAID) {
            getOssVideoTemporarilyUrl(video);
        } else {
            video.setVideo_url("");
        }
    }

    private void getOssVideoTemporarilyUrl(Video video) {
        String objectName = video.getVideo_url();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url = ossClient.generatePresignedUrl(bucket, objectName, expiration);
//        System.out.println(url);
//        System.out.println(url.toExternalForm());
        ossClient.shutdown();
        video.setVideo_url(url.toExternalForm());
    }

    public List<Video> getVideoListByCourseId(Integer courseId) {
        Course courseInDb = courseDao.
                findById(courseId).orElseThrow(() -> new HttpException(404, "课程不存在！"));
        List<Video> videos = courseInDb.getVideos();
        videos.forEach(this::getOssVideoTemporarilyUrl);
        return videos;
    }

    @PermissionRequired(value = {"课程管理"})
    public Token getToken(Integer id, HttpServletResponse response) {
        String host = "http://" + bucket + "." + endpoint;

        String dir = "cource-" + id + "/"; // 用户上传文件时指定的前缀。

        OSSClient client = new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), null);

        long expireTimeSeconds = 30;
        long expireEndTime = System.currentTimeMillis() + expireTimeSeconds * 1000;
        java.sql.Date expiration = new java.sql.Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");

        Token token = new Token();
        token.setAccessid(accessKeyId);
        token.setHost(host);
        token.setPolicy(encodedPolicy);
        token.setSignature(postSignature);
        token.setExpire(expireEndTime / 1000);
        token.setDir(dir);
        return  token;
    }
}
