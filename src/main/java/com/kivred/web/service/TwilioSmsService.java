package com.kivred.web.service;

import java.util.Map;

public interface TwilioSmsService {
    void saveIncomingSms(Map<String, String> content);
}
