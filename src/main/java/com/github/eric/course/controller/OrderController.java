package com.github.eric.course.controller;

import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.model.*;
import com.github.eric.course.service.AlipayService;
import com.github.eric.course.service.CourseOrderService;
import com.github.eric.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    public AlipayService alipayService;

    @Autowired
    public CourseOrderService courseOrderService;
    @Autowired
    public CourseService courseService;

    /**
     * @api {post} /api/v1/course/order 对指定课程下订单
     * @apiName 对指定课程下订单
     * @apiGroup 订单支付管理
     * @apiDescription
     *   查看订单的用户Id是否是当前登录的用户，不是就不允许下订单
     *   查看指定课程是否存在，不存在就不允许下订单
     *   根据courseId和userId查看是否已有订单，没有就下订单
     *   有且订单的状态是删除状态，就修改订单的状态为UNPAID，其他的状态则直接返回不允许重复下订单
     * @apiHeader {String} Accept  application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample Request-Example:
     *          POST /api/v1/course/order
     *        {
     *             "course": {
     *                 "id": "2",
     *                 "name": "Java体系课",
     *                 "description": "从零开始学习Java",
     *                 "teacher_name": "james gosling",
     *                 "teacher_description": "Creator of Java",
     *                 "price": "8888",
     *                 "videos": [
     *                     {
     *                         "id": "4",
     *                         "name": "Java体系课 - 1",
     *                         "description": "Java体系课 第一集",
     *                         "video_url": "cource-1/第03集.mp4";
     *                     }
     *                 ]
     *             },
     *             "user": {
     *                 "id":"2",
     *                 "username": "李四"
     *             }
     *        }
     * @apiSuccess {CourseOrder} courseOrder 订单信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
    {
     *             "id": 3,
     *             "course": {
     *                 "id": "2",
     *                 "name": "Java体系课",
     *                 "description": "从零开始学习Java",
     *                 "teacher_name": "james gosling",
     *                 "teacher_description": "Creator of Java",
     *                 "price": "8888",
     *                 "videos": [
     *                     {
     *                         "id": "4",
     *                         "name": "Java体系课 - 1",
     *                         "description": "Java体系课 第一集",
     *                         "video_url": "cource-1/第03集.mp4";
     *                     }
     *                 ]
     *             },
     *             "user": {
     *                 "id":"2",
     *                 "username": "李四"
     *             },
     *             "status": "UNPAID"
     *        }
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 无权给其他的用户下订单
     * @apiError 404 Unauthorized 找不到指定的课程
     * @apiErrorExample Error-Response:
     * HTTP/1.1 403 Forbidden
     * {
     * "message": "Forbidden"
     * }
     */
    /**
     * 下订单
     * @param courseOrder 订单信息
     * @return 订单信息
     */
    @PostMapping("/course/order")
    public CourseOrder createdCourseOrder(@RequestBody CourseOrder courseOrder) {
        clearCheckCourseOrder(courseOrder);
        return courseOrderService.save(courseOrder);
    }

    private void clearCheckCourseOrder(CourseOrder courseOrder) {
        User currentUser = UserContext.getCurrentUser();
        if (!Objects.equals(courseOrder.getUser().getId(), courseOrder.getId())) {
            throw new HttpException(403, "无权给其他的用户下订单");
        }
        courseOrder.setUser(currentUser);
        Course courseInDb = courseService.getCourseById(courseOrder.getCourse().getId());
        courseOrder.setCourse(courseInDb);
        courseOrder.setPrice(courseInDb.getPrice());
        courseOrder.setStatus(Status.UNPAID);

        if(courseOrderService.isCourseOrderRepeated(courseOrder)){
            throw new HttpException(403,"该课程已经下订单了，无法重复下订单");
        }
    }

    /**
     * @api {get} /api/v1/course/order/{id} 根据订单id查找订单
     * @apiName 根据订单id查找订单
     * @apiGroup 订单支付管理
     * @apiDescription
     *   查看指定id订单是否存在，不存在直接返回
     *   存在就判断下订单的用户是否是当前登录的用户，不是就拒绝查看订单
     *   是就返回订单信息
     * @apiHeader {String} Accept  application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample Request-Example:
     *          GET /api/v1/course/order/1
     * @apiSuccess {CourseOrder} courseOrder 订单信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *        {
     *             "id": "1",
     *             "course": {
     *                 "id": "1",
     *                 "name": "21天精通C++",
     *                 "description": "让你21天精通C++",
     *                 "teacher_name": "Torvalds Linus",
     *                 "teacher_description": "Creator of Linux",
     *                 "price": "10000",
     *                 "videos": [
     *                     {
     *                         "id": "1",
     *                         "name": "21天精通C++ - 1",
     *                         "description": "21天精通C++ 第一集",
     *                         "video_url": "cource-1/第01集.mp4"
     *                     },
     *                     {
     *                         "id": "2",
     *                         "name": "21天精通C++ - 2",
     *                         "description": "21天精通C++ 第二集",
     *                         "video_url": "cource-1/第02集.mp4"
     *                     },
     *                     {
     *                         "id": "3",
     *                         "name": "21天精通C++ - 3",
     *                         "description": "21天精通C++ 第三集",
     *                         "video_url": "cource-1/第03集.mp4"
     *                     }
     *                 ]
     *             },
     *             "user": {
     *                 "id":"1",
     *                 "username": "张三"
     *             },
     *             "status": "PAID"
     *        }
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 无权访问其他人的订单
     * @apiError 404 Unauthorized 找不到指定的订单
     * @apiErrorExample Error-Response:
     * HTTP/1.1 403 Forbidden
     *     {
     *         "message": "Forbidden"
     *     }
     */
    /**
     * 查看订单详情
     * @param orderId 订单Id
     * @return 订单详细信息
     */
    @GetMapping("/course/order")
    public CourseOrder getCourseOrderById(@RequestParam("id") Integer orderId) {
        return queryCheckCourseOrder(orderId);
    }

    /**
     * @api {delete} /api/v1/course/order/{id} 根据订单id删除订单
     * @apiName 根据订单id删除订单
     * @apiGroup 订单支付管理
     * @apiDescription
     *   查看指定id订单是否存在，不存在直接返回
     *   存在就判断下订单的用户是否是当前登录的用户，不是就拒绝删除订单
     *   是就判断订单状态是否支付，以支付则不允许删除订单，否则就修改为删除状态
     * @apiHeader {String} Accept  application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample Request-Example:
     *          DELETE /api/v1/course/order/1
     * @apiSuccess {CourseOrder} courseOrder 订单信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 无法删除其他用户的订单 无法删除已经付款的订单
     * @apiError 404 Unauthorized 找不到指定的订单
     * @apiErrorExample Error-Response:
     * HTTP/1.1 403 Forbidden
     *     {
     *         "message": "Forbidden"
     *     }
     */
    /**
     * 删除订单
     * @param orderId 订单Id
     */
    @DeleteMapping("/course/order")
    public void deleteCourseOrderById(@RequestParam("id") Integer orderId,
                                      HttpServletResponse response) {
        CourseOrder courseOrderInDb = courseOrderService.findById(orderId);
        if (!Objects.equals(courseOrderInDb.getUser().getId(), courseOrderInDb.getId())) {
            throw new HttpException(403, "无法删除其他用户的订单");
        }
        if(courseOrderInDb.getStatus().equals(Status.PAID)){
            throw new HttpException(403, "无法删除已经付款的订单");
        }
        courseOrderInDb.setStatus(Status.DELETED);
        courseOrderService.save(courseOrderInDb);
        response.setStatus(204);
    }



    /**
     * @api {get} /api/v1/showPay?courseId={id} 获取指定id的课程的付款页面
     * @apiName 获取指定id课程的付款页面
     * @apiGroup 订单支付管理
     * @apiDescription
     *  查看指定课程订单用户是否以支付，若以支付就跳转到指定的课程页面
     *  未支付就调用支付宝的接口，获取付款页面HTML。
     *
     * @apiHeader {String} Accept text/html
     *
     * @apiParamExample Request-Example:
     *    GET /api/v1/showPay?courseId=1
     * @apiSuccess {HTML} html 付款页面的HTML
     * @apiSuccessExample Success-Response:
     *
     *     HTTP/1.1 200 OK
     *     <html>
     *        付款页面HTML
     *     </html>
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 404 Unauthorized 找不到指定的订单
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *       "message": "Forbidden"
     *     }
     */
    /**
     * @param courseId 课程Id
     * @return 付款页面HTML
     */
    @GetMapping("/showPay")
    public Object showPay(@RequestParam("courseId") Integer courseId,
                          HttpServletResponse response) {
        Integer userId = UserContext.getCurrentUser().getId();
        CourseOrder courseOrderIndb = courseOrderService.getCourseOrderByCourseIdAndUserId(courseId, userId);
        if (courseOrderIndb.getStatus() == Status.UNPAID) {
            return alipayService.getPayPageHtml(courseOrderIndb);
        } else {
            skipToCoursePage(response, courseId);
            return "";
        }
    }

    /**
     * @api {get} /api/v1/checkPay?orderId={id} 付款完成之后检查支付状态页面
     * @apiName 检查支付状态页面
     * @apiGroup 订单支付管理
     * @apiDescription
     *  在付款完成后，由支付宝负责在浏览器端跳转到此页面，
     *  后端收到此请求后开始向检查订单状态并修改对应数据库的状态。
     *  若订单已经付款，跳转到该订单对应到课程页面。
     *
     * @apiParamExample Request-Example:
     *    GET /api/v1/checkPay?orderId=123
     * @apiSuccess {HTML} html 付款页面的HTML
     * @apiSuccessExample Success-Response:
     *
     *     HTTP/1.1 302 Found
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若非本人订单
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *       "message": "Forbidden"
     *     }
     */
    /**
     * @param orderId       订单Id 必须的
     * @param alipayTradeNo 商户订单号 非必须的
     */
    @GetMapping("/checkPay")
    public void checkPay(@RequestParam(value = "orderId") Integer orderId,
                         @RequestParam(value = "trade_no", required = false) String alipayTradeNo,
                         HttpServletResponse response) {
        CourseOrder courseOrderIndb = queryCheckCourseOrder(orderId);

        if (courseOrderIndb.getStatus() != Status.UNPAID) {
            skipToCoursePage(response, courseOrderIndb.getCourse().getId());
        } else {
            String checkOrderStatus = alipayService.checkOrderStatus(courseOrderIndb, alipayTradeNo);
            if (Objects.equals("TRADE_SUCCESS", checkOrderStatus)) {
                //支付成功，修改订单的状态为以支付
                courseOrderIndb.setStatus(Status.PAID);
                courseOrderService.save(courseOrderIndb);
            } else if (Objects.equals("交易不存在", checkOrderStatus)) {
                //支付失败
                throw new HttpException(400, "订单支付失败");
            } else {
                //其他情况
                throw new HttpException(400, "请求参数错误");
            }
        }
    }

    private CourseOrder queryCheckCourseOrder(@RequestParam("orderId") Integer orderId) {
        Integer userId = UserContext.getCurrentUser().getId();
        CourseOrder courseOrderIndb = courseOrderService.findById(orderId);

        if (!courseOrderIndb.getUser().getId().equals(userId)) {
            throw new HttpException(403, "无权访问其他人的订单");
        }
        return courseOrderIndb;
    }

    private void skipToCoursePage(HttpServletResponse response, Integer courseId) {
        try {
            // 跳转到以购买的课程中去;
            response.sendRedirect("http://localhost:8080/api/v1/course/" + courseId);
        } catch (IOException e) {
            throw new HttpException(400, "页面跳转失败");
        }
    }
}
