package com.swd392.onlineshop.service;

import com.swd392.onlineshop.dto.FeedbackDTO;
import com.swd392.onlineshop.dto.FeedbackFilter;
import com.swd392.onlineshop.enums.FeedbackStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Theo dac ta "Feedbacks List": Marketing chi duoc xem va doi status,
 * nen khong co ham create.
 */
public interface FeedbackService {

    Page<FeedbackDTO> search(FeedbackFilter filter, Pageable pageable);

    FeedbackDTO findById(Long id);

    void changeStatus(Long id, FeedbackStatus status);

    void delete(Long id);
}
