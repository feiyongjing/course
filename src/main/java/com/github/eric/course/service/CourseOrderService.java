package com.github.eric.course.service;

import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.dao.CourseOrderDao;
import com.github.eric.course.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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
            if (courseOrderInDb.getStatus().equals(Status.DELETED)) {
                courseOrder.setId(courseOrderInDb.getId());
                result = false;
            }
        }
        return result;
    }

    public PageResponse<CourseOrder> getUserAllCourseOrder(String search, Integer pageSize, Integer pageNum, String field, Sort.Direction orderType) {
        Pageable pageable = orderType == null ?
                PageRequest.of(pageNum - 1, pageSize) :
                PageRequest.of(pageNum - 1, pageSize, Sort.by(orderType, field));

        Page<CourseOrder> page = ObjectUtils.isEmpty(search) ?
                courseOrderDao.findAll(getCourseOrderSpecification(null), pageable) :
                courseOrderDao.findAll(getCourseOrderSpecification(search), pageable);

        return new PageResponse<>(page.getTotalPages(), pageSize, pageNum, page.toList());
    }

    private Specification<CourseOrder> getCourseOrderSpecification(String search) {
        return (Specification<CourseOrder>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (search != null) {
                list.add(criteriaBuilder.like(root.get("course").get("name"), "%" + search + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("user").get("id"), UserContext.getCurrentUser().getId()));
            list.add(criteriaBuilder.notEqual(root.get("status").as(Status.class), Status.DELETED));

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
    }

}
