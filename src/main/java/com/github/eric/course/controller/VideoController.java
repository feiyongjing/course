package com.github.eric.course.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.github.eric.course.model.Video;
import com.github.eric.course.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VideoController {
    @Autowired
    public VideoService videoService;

    @PatchMapping("/video/{id}")
    public Video updateVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        return videoService.updateVideo(id, video);
    }

    @PostMapping("/course/{id}/video")
    public Video createdVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        check(video);
        return videoService.createdVideo(id, video);
    }

    private void check(Video video) {

    }


    @DeleteMapping("/course/{id}/video")
    public void deleteVideo(@PathVariable("id") Integer id, HttpServletResponse response) {
        videoService.deleteVideoByVideoId(id, response);
    }

    @GetMapping("/course/{id}/token")
    public Token getVideoToken(@PathVariable("id") Integer id) {
        String accessKeyId = "LTAI5tDbhXMJXysRo5QdVZtf"; // 请填写您的AccessKeyId。
        String accessKeySecret = "XdcMhefgLsPgoPjEoXtqdB6unk1HBV"; // 请填写您的AccessKeySecret。
        String endpoint = "oss-cn-beijing.aliyuncs.com"; // 请填写您的 endpoint。
        String bucket = "eric-course"; // 请填写您的 bucketname 。
        String host = "http://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
//        String callbackUrl = "http://88.88.88.88.:8888";

        String dir = "course-" + id + "/"; // 用户上传文件时指定的前缀。

        OSSClient client = new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), null);

        long expireTimeSeconds = 30;
        long expireEndTime = System.currentTimeMillis() + expireTimeSeconds * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);


        Token token = new Token();
        token.setAccessId(accessKeyId);
        token.setHost(host);
        token.setPolicy(encodedPolicy);
        token.setSignature(postSignature);
        token.setExpire(expireEndTime / 1000);
        token.setDir(dir);

        return token;
    }

    static class Token {
        private String accessId;
        private String host;
        private String policy;
        private String signature;
        private Long expire;
        private String dir;

        public String getAccessId() {
            return accessId;
        }

        public void setAccessId(String accessId) {
            this.accessId = accessId;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public Long getExpire() {
            return expire;
        }

        public void setExpire(Long expire) {
            this.expire = expire;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }

    @GetMapping("/video/{id}")
    public Video getVideo(@PathVariable("id") Integer id) {
        return videoService.getVideoByVideoId(id);
    }

    @GetMapping("/course/{id}/video")
    public List<Video> getVideoListByCourseId(@PathVariable("id") Integer id) {

        return videoService.getVideoListByCourseId(id);
    }
}
