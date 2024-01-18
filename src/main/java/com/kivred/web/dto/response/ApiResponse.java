package com.kivred.web.dto.response;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;

@Data
@Accessors(chain = true)
public class ApiResponse<T> {
    private boolean status;
    private String message;
    private String token;
    private T data;
}
