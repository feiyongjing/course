package com.github.eric.course.model;

import java.util.List;

public class PageResponse <T> {
    private Integer totalPage;
    private Integer pageSize;
    private Integer  pageNum;
    private List<T> date;

    public PageResponse() {
    }

    public PageResponse(Integer totalPage, Integer pageSize, Integer pageNum, List<T> date) {
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.date = date;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getDate() {
        return date;
    }

    public void setDate(List<T> date) {
        this.date = date;
    }
}
