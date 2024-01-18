package com.kivred.web.service.impl;

import com.kivred.web.db.PhoneNumberRepository;
import com.kivred.web.db.SmsRepository;
import com.kivred.web.db.entity.PhoneNumberEntity;
import com.kivred.web.db.entity.SmsEntity;
import com.kivred.web.dto.response.SmsNumberResponse;
import com.kivred.web.service.ScrapeScheduleService;
import com.kivred.web.service.SmsReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsReaderServiceDbImpl implements SmsReaderService {
    private final ScrapeScheduleService scrapeScheduleService;
    private final PhoneNumberRepository phoneNumberRepository;
    private final SmsRepository smsRepository;

    public List<SmsNumberResponse> getAll(boolean force) {
        scrapeScheduleService.fetchNumber(force);
        return phoneNumberRepository.findAll().stream().map(p -> {
            SmsNumberResponse item = new SmsNumberResponse();
            item.setUrl(p.getUrl()).setCountry(p.getCountry()).setMessage(p.getMessage()).setNumber(p.getNumber()).setValid(p.getMessage().equals("OPEN"));
            return item;
        }).collect(Collectors.toList());
    }

    public List<Map<String, String>> getReadView(String phoneToken) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            final PhoneNumberEntity phoneNumber = phoneNumberRepository.findByUrlIsLike(("token=" + phoneToken)).orElse(null);
            if (phoneNumber != null) {
                scrapeScheduleService.fetchAllSms(phoneNumber, phoneToken);
                for (SmsEntity phoneNumberMessage : smsRepository.findAllByPhoneNumber(phoneNumber.getNumber())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("message", phoneNumberMessage.getMessage());
                    map.put("from", phoneNumberMessage.getSender());
                    map.put("time", phoneNumberMessage.getTime());
                    map.put("name", phoneNumberMessage.getSender() != null ? phoneNumberMessage.getSender().substring(0, 1) : "");
                    data.add(map);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
