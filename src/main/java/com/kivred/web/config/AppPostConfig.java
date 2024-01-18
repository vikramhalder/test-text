package com.kivred.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivred.web.db.CountryCodeRepository;
import com.kivred.web.db.entity.CountryCodeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AppPostConfig {
    private final CountryCodeRepository countryCodeRepository;

    @PostConstruct
    public void init() throws Exception {
        if (countryCodeRepository.count() == 0) {
            CountryCodeEntity[] list = new ObjectMapper().readValue(Files.newInputStream(ResourceUtils.getFile("classpath:static/public/file/country.json").toPath()), CountryCodeEntity[].class);
            countryCodeRepository.saveAll(Arrays.stream(list).collect(Collectors.toList()));
        }
    }
}
