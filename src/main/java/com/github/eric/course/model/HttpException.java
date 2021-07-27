package com.github.eric.course.model;

public class HttpException extends RuntimeException {
    private int statusCode;
    private String massage;
    public HttpException(int statusCode, String massage) {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
