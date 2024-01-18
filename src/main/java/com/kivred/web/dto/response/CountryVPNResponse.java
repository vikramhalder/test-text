package com.kivred.web.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CountryVPNResponse {
    private String country;
    private List<VpnResponse> data;
}
