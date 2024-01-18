package com.kivred.web.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SmsNumberResponse {
    private String number;
    private String country;
    private String url;
    private String message;
    private boolean valid;
}
