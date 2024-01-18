package com.kivred.web.dto.request;

import lombok.Data;

@Data
public class SupportRequest {
    private String name;
    private String email;
    private String message;
}
