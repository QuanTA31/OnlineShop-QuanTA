package com.swd392.onlineshop.service;

import com.swd392.onlineshop.dto.CustomerDTO;
import com.swd392.onlineshop.dto.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Page<CustomerDTO> search(CustomerFilter filter, Pageable pageable);

    /** Tra ve customer kem bang lich su thay doi. */
    CustomerDTO findById(Long id);

    CustomerDTO create(CustomerDTO dto);

    /** Moi lan sua deu ghi them mot dong vao customer_histories. */
    CustomerDTO update(Long id, CustomerDTO dto, String updatedBy);

    void delete(Long id);
}
