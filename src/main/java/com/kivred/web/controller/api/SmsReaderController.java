package com.kivred.web.controller.api;

import com.kivred.web.dto.response.ApiResponse;
import com.kivred.web.dto.response.SmsNumberResponse;
import com.kivred.web.service.SmsReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sms-reader", produces = MediaType.APPLICATION_JSON_VALUE)
public class SmsReaderController extends AbstractController {
    final SmsReaderService smsReaderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SmsNumberResponse>>> getView(@RequestParam(required = false) Boolean force) {
        return super.returnJson(smsReaderService.getAll(force != null ? force : false));
    }

    @GetMapping("/read")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getReadView(@RequestParam String token) {
        return super.returnJson(true, smsReaderService.getReadView(token), "Success");
    }
}
