package com.github.eric.course.service;

import com.github.eric.course.dao.CourseOrderDao;
import com.github.eric.course.model.CourseOrder;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseOrderService {
    @Autowired
    public CourseOrderDao courseOrderDao;

    public CourseOrder getCourseOrderByCourseIdAndUserId(Integer courseId, Integer userId) {
        return courseOrderDao.findByCourseIdAndUserId(courseId, userId)
                .orElseThrow(() -> new HttpException(404, "找不到订单！"));
    }


    public CourseOrder findById(Integer orderId) {
        return courseOrderDao.findById(orderId)
                .orElseThrow(() -> new HttpException(404, "找不到订单！"));
    }

    public CourseOrder save(CourseOrder courseOrderIndb) {
        courseOrderDao.save(courseOrderIndb);
        return courseOrderIndb;
    }

    public boolean isCourseOrderRepeated(CourseOrder courseOrder) {
        Optional<CourseOrder> courserOrderOptional = courseOrderDao
                .findByCourseIdAndUserId(courseOrder.getCourse().getId(), courseOrder.getUser().getId());
        boolean result = courserOrderOptional.isPresent();
        if (result) {
            CourseOrder courseOrderInDb = courserOrderOptional.get();
            if(courseOrderInDb.getStatus().equals(Status.DELETED)){
                courseOrder.setId(courseOrderInDb.getId());
                result=false;
            }
        }
        return result;
    }

//    public CourseOrder deleteById(Integer orderId) {
//        return courseOrderDao.d;
//    }
}
