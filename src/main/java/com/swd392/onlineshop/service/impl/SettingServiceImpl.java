package com.swd392.onlineshop.service.impl;

import com.swd392.onlineshop.entity.Setting;
import com.swd392.onlineshop.enums.Status;
import com.swd392.onlineshop.exception.ResourceNotFoundException;
import com.swd392.onlineshop.repository.SettingRepository;
import com.swd392.onlineshop.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    public List<Setting> findActiveByType(String type) {
        return settingRepository.findByTypeAndStatusOrderByDisplayOrderAsc(type, Status.ACTIVE);
    }

    @Override
    public Setting getById(Long id) {
        return settingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setting", id));
    }
}
