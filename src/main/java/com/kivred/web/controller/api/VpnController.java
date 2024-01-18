package com.kivred.web.controller.api;

import com.kivred.web.dto.response.ApiResponse;
import com.kivred.web.dto.response.CountryCodeResponse;
import com.kivred.web.dto.response.CountryVPNResponse;
import com.kivred.web.dto.response.VpnResponse;
import com.kivred.web.service.VpnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/vap-token-light", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VpnController extends AbstractController {
    final VpnService vpnService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CountryCodeResponse>>> getView() throws Exception {
        return super.returnJson(vpnService.getAllCountryCode());
    }

    @GetMapping("/code-save")
    public ResponseEntity<ApiResponse<List<CountryCodeResponse>>> saveCode() throws Exception {
        return this.getView();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CountryVPNResponse>>> geAllView() {
        return super.returnJson(vpnService.getAllCountryVpn(false));
    }

    @GetMapping("/all-new")
    public ResponseEntity<ApiResponse<List<CountryVPNResponse>>> getAllNewView(@RequestParam(required = false) Boolean force) {
        return super.returnJson(vpnService.getAllCountryVpn(force != null ? force : false));
    }

    @GetMapping("/all-new/{countryCode}")
    public ResponseEntity<ApiResponse<List<VpnResponse>>> getAllByCountryView(@PathVariable String countryCode) {
        return super.returnJson(vpnService.getAllByCountryCode(countryCode));
    }
}
