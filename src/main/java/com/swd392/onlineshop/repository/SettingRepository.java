package com.swd392.onlineshop.repository;

import com.swd392.onlineshop.entity.Setting;
import com.swd392.onlineshop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, Long> {

    List<Setting> findByTypeAndStatusOrderByDisplayOrderAsc(String type, Status status);

    List<Setting> findByTypeOrderByDisplayOrderAsc(String type);
}
