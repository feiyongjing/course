package com.github.eric.course.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eric.course.model.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ExceptionHandlingController {
    private ObjectMapper objectMapper=new ObjectMapper();

    @ExceptionHandler({HttpException.class})
    public void handleException(HttpServletResponse response, HttpException e) throws IOException {
        response.setStatus(e.getStatusCode());

        Map<String,Object> jsonObject=new HashMap<>();
        jsonObject.put("massage",e.getMassage());

        response.getOutputStream().write(objectMapper.writeValueAsBytes(jsonObject));
        response.getOutputStream().flush();
    }
}
