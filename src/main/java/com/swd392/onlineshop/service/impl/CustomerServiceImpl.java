package com.swd392.onlineshop.service.impl;

import com.swd392.onlineshop.dto.CustomerDTO;
import com.swd392.onlineshop.dto.CustomerFilter;
import com.swd392.onlineshop.dto.CustomerHistoryDTO;
import com.swd392.onlineshop.entity.Customer;
import com.swd392.onlineshop.entity.CustomerHistory;
import com.swd392.onlineshop.enums.CustomerStatus;
import com.swd392.onlineshop.enums.OrderStatus;
import com.swd392.onlineshop.exception.ResourceNotFoundException;
import com.swd392.onlineshop.repository.CustomerHistoryRepository;
import com.swd392.onlineshop.repository.CustomerRepository;
import com.swd392.onlineshop.repository.OrderRepository;
import com.swd392.onlineshop.repository.specification.CustomerSpecification;
import com.swd392.onlineshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerHistoryRepository customerHistoryRepository;
    private final OrderRepository orderRepository;

    @Override
    public Page<CustomerDTO> search(CustomerFilter filter, Pageable pageable) {
        return customerRepository.findAll(CustomerSpecification.build(filter), pageable)
                .map(c -> toDTO(c, false));
    }

    @Override
    public CustomerDTO findById(Long id) {
        return toDTO(getEntity(id), true);
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerDTO dto) {
        Customer customer = new Customer();
        applyChanges(customer, dto);
        // Khach moi tao tu man hinh Marketing luon bat dau o muc CONTACT
        customer.setStatus(CustomerStatus.CONTACT);
        return toDTO(customerRepository.save(customer), false);
    }

    @Override
    @Transactional
    public CustomerDTO update(Long id, CustomerDTO dto, String updatedBy) {
        Customer customer = getEntity(id);

        // Ghi lai GIA TRI CU truoc khi ghi de - dung yeu cau bang lich su
        customerHistoryRepository.save(CustomerHistory.builder()
                .customer(customer)
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .mobile(customer.getMobile())
                .address(customer.getAddress())
                .updatedBy(updatedBy)
                .updatedDate(LocalDateTime.now())
                .build());

        applyChanges(customer, dto);
        customer.setStatus(resolveStatus(customer));
        return toDTO(customerRepository.save(customer), false);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        customerRepository.delete(getEntity(id));
    }

    // ===================== Helper =====================

    private Customer getEntity(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    private void applyChanges(Customer customer, CustomerDTO dto) {
        customer.setFullName(dto.getFullName().trim());
        customer.setGender(dto.getGender());
        customer.setEmail(dto.getEmail().trim());
        customer.setMobile(dto.getMobile());
        customer.setAddress(dto.getAddress());
        // status KHONG lay tu form - he thong tu sinh (theo dac ta man hinh)
    }

    /**
     * Status do he thong tu sinh theo lich su mua hang:
     * - Da co don hang thanh cong  -> CUSTOMER
     * - Da tung dat don bat ky     -> POTENTIAL
     * - Chua co don nao            -> CONTACT
     */
    private CustomerStatus resolveStatus(Customer customer) {
        if (customer.getId() == null) {
            return CustomerStatus.CONTACT;
        }
        if (orderRepository.countByCustomerIdAndStatus(customer.getId(), OrderStatus.SUCCESS) > 0) {
            return CustomerStatus.CUSTOMER;
        }
        if (orderRepository.countByCustomerId(customer.getId()) > 0) {
            return CustomerStatus.POTENTIAL;
        }
        return CustomerStatus.CONTACT;
    }

    private CustomerDTO toDTO(Customer c, boolean withHistories) {
        CustomerDTO dto = CustomerDTO.builder()
                .id(c.getId())
                .fullName(c.getFullName())
                .gender(c.getGender())
                .email(c.getEmail())
                .mobile(c.getMobile())
                .address(c.getAddress())
                .status(c.getStatus())
                .createdDate(c.getCreatedDate())
                .updatedDate(c.getUpdatedDate())
                .build();

        if (withHistories) {
            List<CustomerHistoryDTO> histories = customerHistoryRepository
                    .findByCustomerIdOrderByUpdatedDateDesc(c.getId())
                    .stream()
                    .map(h -> CustomerHistoryDTO.builder()
                            .id(h.getId())
                            .email(h.getEmail())
                            .fullName(h.getFullName())
                            .gender(h.getGender())
                            .mobile(h.getMobile())
                            .address(h.getAddress())
                            .updatedBy(h.getUpdatedBy())
                            .updatedDate(h.getUpdatedDate())
                            .build())
                    .toList();
            dto.setHistories(histories);
        }
        return dto;
    }
}
