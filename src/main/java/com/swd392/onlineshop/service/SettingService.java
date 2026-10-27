package com.swd392.onlineshop.service;

import com.swd392.onlineshop.entity.Setting;

import java.util.List;

public interface SettingService {

    /** Lay danh sach setting dang active theo type, dung do vao dropdown. */
    List<Setting> findActiveByType(String type);

    Setting getById(Long id);
}
