package com.kivred.web.controller.api;

import com.kivred.web.dto.request.SupportRequest;
import com.kivred.web.dto.response.ApiResponse;
import com.kivred.web.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/support", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SupportController extends AbstractController {
    private final SupportService supportService;

    @PostMapping(value = "/post")
    public ResponseEntity<ApiResponse<Boolean>> postView(@RequestBody SupportRequest request) {
        supportService.save(request);
        return super.returnJson(true);
    }
}
