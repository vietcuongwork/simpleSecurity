package com.vietcuong.simpleSecurity.response;


import com.vietcuong.simpleSecurity.common.ApplicationError;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class GlobalResponse<T> {
    private String statusCode;
    private String description;
    private T content;

    public <T> GlobalResponse<T> initializeResponse(
            T content) {
        GlobalResponse<T> response = new GlobalResponse<>();
        response.setStatusCode(
                ApplicationError.GlobalError.USER_REGISTRATION_SUCCESS.getStatusCode());
        response.setDescription(
                ApplicationError.GlobalError.USER_REGISTRATION_SUCCESS.getDescription());
        response.setContent(content);
        return response;
    }

    public <T> GlobalResponse<T> initializeResponse(
            ApplicationError.GlobalError errorEnum, T content) {
        GlobalResponse<T> response = new GlobalResponse<>();
        response.setStatusCode(errorEnum.getStatusCode());
        response.setDescription(errorEnum.getDescription());
        response.setContent(content);
        return response;
    }

    public GlobalResponse<Map<String, String>> createMessageResponse(
            ApplicationError.GlobalError errorEnum, String contentType,
            String contentValue) {
        GlobalResponse<Map<String, String>> response = new GlobalResponse<>();
        response.setStatusCode(errorEnum.getStatusCode());
        response.setDescription(errorEnum.getDescription());
        Map<String, String> content = new HashMap<>();
        content.put(contentType, contentValue);
        response.setContent(content);
        return response;
    }

}
