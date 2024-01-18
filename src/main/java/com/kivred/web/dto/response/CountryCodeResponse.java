package com.kivred.web.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class CountryCodeResponse {
    private String code;
    private String name;
}
