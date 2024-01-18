package com.kivred.web.service;

import com.kivred.web.dto.response.SmsNumberResponse;

import java.util.List;
import java.util.Map;

public interface SmsReaderService {
    List<SmsNumberResponse> getAll(boolean force);

    List<Map<String, String>> getReadView(String phoneToken);
}
