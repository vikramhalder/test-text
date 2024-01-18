package com.kivred.web.controller.api;

import com.kivred.web.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {
    protected <T> ResponseEntity<ApiResponse<T>> returnJson(T data) {
        return this.returnJson(true, data, "Success");
    }

    protected <T> ResponseEntity<ApiResponse<T>> returnJson(boolean status, T data, String message) {
        return ResponseEntity.ok(new ApiResponse<T>().setStatus(status).setMessage(message).setData(data));
    }
}
