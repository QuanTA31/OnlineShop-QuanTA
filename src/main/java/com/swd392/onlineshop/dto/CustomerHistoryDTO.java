package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerHistoryDTO {

    private Long id;
    private String email;
    private String fullName;
    private Gender gender;
    private String mobile;
    private String address;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
