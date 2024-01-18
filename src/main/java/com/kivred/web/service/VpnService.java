package com.kivred.web.service;

import com.kivred.web.dto.response.CountryCodeResponse;
import com.kivred.web.dto.response.CountryVPNResponse;
import com.kivred.web.dto.response.VpnResponse;

import java.util.List;

public interface VpnService {
    List<CountryCodeResponse> getAllCountryCode() throws Exception;

    List<VpnResponse> getAllByCountryCode(String code);

    List<CountryVPNResponse> getAllCountryVpn(boolean scrapeInBackground);
}
