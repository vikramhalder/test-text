package com.kivred.web.service.impl;

import com.kivred.web.db.TwilioSMSRepository;
import com.kivred.web.db.entity.TwilioSMSEntity;
import com.kivred.web.service.TwilioSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TwilioSmsServiceDbImpl implements TwilioSmsService {
    private final TwilioSMSRepository twilioSMSRepository;

    @Override
    public void saveIncomingSms(Map<String, String> content) {
        TwilioSMSEntity twilioSMSEntity = new TwilioSMSEntity();
        twilioSMSEntity.setNumberTo(content.get("To") != null ? content.get("To") : "");
        twilioSMSEntity.setNumberFrom(content.get("From") != null ? content.get("From") : "");
        twilioSMSEntity.setText(content.get("Body") != null ? content.get("Body") : "");
        twilioSMSEntity.setCreated(new Date());
        twilioSMSRepository.save(twilioSMSEntity);
    }
}
