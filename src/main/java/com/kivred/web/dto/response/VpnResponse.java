package com.kivred.web.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VpnResponse {
    private String country;
    private String port;
    private String ip;
    private String type;
    private String speed;
}
