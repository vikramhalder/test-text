package com.kivred.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivred.web.db.CountryVpnRepository;
import com.kivred.web.db.entity.CountryCodeEntity;
import com.kivred.web.db.entity.CountryVpnEntity;
import com.kivred.web.dto.response.CountryCodeResponse;
import com.kivred.web.dto.response.CountryVPNResponse;
import com.kivred.web.dto.response.VpnResponse;
import com.kivred.web.service.ScrapeScheduleService;
import com.kivred.web.service.VpnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VpnServiceDbImpl implements VpnService {
    private final ResourceLoader resourceLoader;
    private final CountryVpnRepository countryVpnRepository;
    private final ScrapeScheduleService scrapeScheduleService;

    @Override
    public List<CountryCodeResponse> getAllCountryCode() throws Exception {
        return Arrays.stream(new ObjectMapper().readValue(resourceLoader.getResource("classpath:static/public/file/country_code.json").getInputStream(), CountryCodeEntity[].class)).map(m -> new CountryCodeResponse().setCode(m.getCode()).setName(m.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<VpnResponse> getAllByCountryCode(String countryCode) {
        try {
            final List<CountryVpnEntity> list = scrapeScheduleService.getCountryVpnByCountryCode(countryCode).stream().filter(m -> {
                if (m.getType().contains("Elite")) {
                    m.setType("Elite");
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            countryVpnRepository.deleteAllByIdentityIn(list.stream().map(CountryVpnEntity::getIdentity).collect(Collectors.toList()));
            countryVpnRepository.saveAll(list);

            return countryVpnRepository.findAllByCountryCodeOrderByUpdatedDesc(countryCode.toLowerCase()).stream().map(m -> {
                final VpnResponse item = new VpnResponse();
                BeanUtils.copyProperties(m, item);
                return item.setCountry(m.getCountryCode());
            }).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Fetch by code: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<CountryVPNResponse> getAllCountryVpn(boolean scrapeInBackground) {
        scrapeScheduleService.fetchVpn(scrapeInBackground);
        final List<CountryVPNResponse> response = new ArrayList<>();
        for (String cc : countryVpnRepository.distinctCountryCode()) {
            List<VpnResponse> list = countryVpnRepository.findAllByCountryCodeOrderByUpdatedDesc(cc.toLowerCase()).stream().map(m -> {
                final VpnResponse item = new VpnResponse();
                BeanUtils.copyProperties(m, item);
                return item.setCountry(m.getCountryCode());
            }).collect(Collectors.toList());
            if (!list.isEmpty()) {
                response.add(new CountryVPNResponse().setCountry(cc.toLowerCase()).setData(list));
            }
        }
        return response;
    }
}
