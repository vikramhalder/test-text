package com.kivred.web.controller.api;

import com.kivred.web.service.TwilioSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/twilio", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TwilioController {
    private final TwilioSmsService twilioSmsService;

    @PostMapping(value = "/sms/response", produces = MediaType.APPLICATION_XML_VALUE)
    public String twilioSmsResponse(@RequestParam(required = false) Map<String, String> params, @RequestBody(required = false) String body) {
        twilioSmsService.saveIncomingSms(params);
        log.info("TWILIO INCOMING: param={} body={}", params, body);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response></Response>";
    }
}
