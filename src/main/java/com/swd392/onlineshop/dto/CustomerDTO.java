package com.swd392.onlineshop.dto;

import com.swd392.onlineshop.enums.CustomerStatus;
import com.swd392.onlineshop.enums.Gender;
import com.swd392.onlineshop.validator.UniqueCustomerEmail;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Du lieu binding cho form "Customer Details".
 * status KHONG binding tu form - he thong tu sinh theo dac ta.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueCustomerEmail
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Full name khong duoc de trong")
    @Size(max = 150, message = "Full name toi da 150 ky tu")
    private String fullName;

    @NotNull(message = "Vui long chon gender")
    private Gender gender;

    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong dung dinh dang")
    @Size(max = 150, message = "Email toi da 150 ky tu")
    private String email;

    @Pattern(regexp = "^$|^0[0-9]{9,10}$",
            message = "Mobile phai bat dau bang 0 va co 10-11 chu so")
    private String mobile;

    @Size(max = 500, message = "Address toi da 500 ky tu")
    private String address;

    // ===== Chi de hien thi =====
    private CustomerStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder.Default
    private List<CustomerHistoryDTO> histories = new ArrayList<>();
}
