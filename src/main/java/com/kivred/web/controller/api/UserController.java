package com.kivred.web.controller.api;

import com.kivred.web.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/o/free", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractController {
    @Value("${user.token}")
    private String token;

    @PostMapping("/reg")
    public ResponseEntity<ApiResponse<String>> postView() {
        return returnJson(true, token, "Success");
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<Object>> getView() {
        return returnJson(false, null, "User not found");
    }
}
