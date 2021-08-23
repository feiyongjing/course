package com.github.eric.course.controller;

import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Status;
import com.github.eric.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    @Autowired
    public CourseDao courseDao;

    @Autowired
    public CourseService courseService;

    /**
     * @api {patch} /api/v1/course/{id} 修改课程
     * @apiName 修改课程
     * @apiGroup 课程管理
     * @apiDescription
     *  需要"课程管理"权限。
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     * PATCH /api/v1/course
     *          {
     *             "id": 12345,
     *             "name": "21天精通C++",
     *             "teacherName": "Torvalds Linus",
     *             "teacherDescription": "Creator of Linux",
     *             "price": 9900
     *          }
     *
     * @apiSuccess {Course} course 修改后的课程信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *             "id": 12345,
     *             "name": "21天精通C++",
     *             "teacherName": "Torvalds Linus",
     *             "teacherDescription": "Creator of Linux",
     *             "price": 9900
     *          }
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若无权限
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @return
     */
    @PatchMapping("/course/{id}")
    public Course updateCourse(@PathVariable("id") Integer id, @RequestBody Course course) {
        return courseService.updateCourse(id,course);
    }
    /**
     * @api {post} /api/v1/course 创建课程
     * @apiName 创建课程
     * @apiGroup 课程管理
     * @apiDescription
     *  填写必要信息，创建一门课程。需要"课程管理"权限。
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     *          POST /api/v1/course
     *          {
     *             "name": "21天精通C++",
     *             "teacherName": "Torvalds Linus",
     *             "teacherDescription": "Creator of Linux",
     *             "price": 9900
     *          }
     * @apiSuccess {Course} course 新创建的课程信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *             "id": 4,
     *             "name": "消息队列",
     *             "teacherName": "老张",
     *             "teacherDescription": "xxx员工",
     *             "price": "333"
     *          }
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若无权限
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @return
     */
    @PostMapping("/course")
    public Course createdCourse(@RequestBody Course course) {
        check(course);
        return courseService.createdCourse(course);
    }

    private void check(Course course) {
        course.setVideos(null);
        course.setStatus(Status.OK);
    }

    /**
     * @api {delete} /api/v1/course 删除课程
     * @apiName 删除课程
     * @apiGroup 课程管理
     * @apiDescription
     *  需要"课程管理"权限。
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     *            DELETE /api/v1/course
     *
     * @apiSuccess {String} empty 空字符串
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若无权限
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @return
     */
    @DeleteMapping("/course")
    public void deleteCourse(@RequestParam("id") Integer id, HttpServletResponse response) {
        courseService.deleteCourse(id,response);
    }
    /**
     * @api {get} /api/v1/course/{id} 获取课程
     * @apiName 获取课程
     * @apiGroup 课程管理
     * @apiDescription
     *  获取指定id的课程信息。课程信息里包含视频信息和教师信息。
     *  若视频的URL为空，证明未购买该课程。
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParamExample Request-Example:
     *            GET /api/v1/course/123
     *
     * @apiSuccess {Course} course 课程信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *             "id": 12345,
     *             "name": "21天精通C++",
     *             "teacherName": "Torvalds Linus",
     *             "teacherDescription": "Creator of Linux",
     *             "videos": [
     *                {
     *                    "id": 456,
     *                    "name": "第一课",
     *                    "description": "",
     *                    "url": "https://oss.aliyun.com/xxx"
     *                }
     *             ]
     *             "price": 9900,
     *             "purchased": false
     *          }
     * @apiError 400 Bad request 若请求中包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @param id
     * @return
     */
    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable("id") Integer id) {
        return courseService.getCourseById(id);
    }

    /**
     * @api {get} /api/v1/course 获取课程列表
     * @apiName 获取课程列表
     * @apiGroup 课程管理
     * @apiDescription
     *  获取分页的课程信息。课程信息里包含视频信息和教师信息。
     *  若视频的URL为空，证明未购买该课程。
     *
     * @apiHeader {String} Accept application/json
     * @apiParam {String} [search] 搜索关键字
     * @apiParam {Number} [pageSize] 每页包含多少个课程
     * @apiParam {Number} [pageNum] 页码，从1开始
     * @apiParam {String} [orderBy] 排序字段，如price/createdAt
     * @apiParam {String} [orderType] 排序方法，Asc/Desc
     *
     * @apiParamExample Request-Example:
     *            GET /api/v1/course?pageSize=10&pageNum=1&orderBy=price&orderType=Desc&search=21天
     * @apiSuccess {Number} totalPage 总页数
     * @apiSuccess {Number} pageNum 当前页码，从1开始
     * @apiSuccess {Number} pageSize 每页包含多少个课程
     * @apiSuccess {List[Course]} data 课程列表
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "totalPage": 100,
     *       "pageSize": 10,
     *       "pageNum": 1,
     *       "data": [
     *          {
     *             "id": 12345,
     *             "name": "21天精通C++",
     *             "teacherName": "Torvalds Linus",
     *             "teacherDescription": "Creator of Linux",
     *             videos: [
     *                {
     *                    "id": 456,
     *                    "name": "第一课",
     *                    "description": "",
     *                    "url": "https://oss.aliyun.com/xxx"
     *                }
     *             ]
     *             price: 9900,
     *             purchased: true
     *          }
     *       ]
     *     }
     * @apiError 400 Bad request 若请求中包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @param pageSize
     * @param pageNum
     * @param orderBy
     * @return
     */
    @GetMapping("/course")
    public PageResponse<Course> getCourse(@RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                  @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                  @RequestParam(value = "orderBy", required = false) String orderBy,
                                  @RequestParam(value = "orderType", required = false) String orderType) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (orderType != null && orderBy == null) {
            throw new HttpException(400, "缺少orderBy!");
        }
        return courseService.getUserAllCourse(search, pageSize, pageNum, orderBy, orderType == null ? null : Sort.Direction.fromString(orderType));
    }


}
