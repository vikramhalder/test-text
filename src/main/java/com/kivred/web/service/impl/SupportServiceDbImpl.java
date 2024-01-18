package com.kivred.web.service.impl;

import com.kivred.web.db.SupportRepository;
import com.kivred.web.db.entity.SupportMessageEntity;
import com.kivred.web.dto.request.SupportRequest;
import com.kivred.web.service.SupportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportServiceDbImpl implements SupportService {
    private final SupportRepository supportRepository;

    public void save(SupportRequest request) {
        SupportMessageEntity sm = new SupportMessageEntity();
        BeanUtils.copyProperties(sm, request);
        supportRepository.save(sm);
    }
}
