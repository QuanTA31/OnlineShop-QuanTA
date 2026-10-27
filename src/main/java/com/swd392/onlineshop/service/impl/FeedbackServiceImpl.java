package com.swd392.onlineshop.service.impl;

import com.swd392.onlineshop.dto.FeedbackDTO;
import com.swd392.onlineshop.dto.FeedbackFilter;
import com.swd392.onlineshop.entity.Feedback;
import com.swd392.onlineshop.enums.FeedbackStatus;
import com.swd392.onlineshop.exception.ResourceNotFoundException;
import com.swd392.onlineshop.repository.FeedbackRepository;
import com.swd392.onlineshop.repository.specification.FeedbackSpecification;
import com.swd392.onlineshop.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements FeedbackService {

    private static final String GENERAL_FEEDBACK = "(General feedback)";

    private final FeedbackRepository feedbackRepository;

    @Override
    public Page<FeedbackDTO> search(FeedbackFilter filter, Pageable pageable) {
        return feedbackRepository.findAll(FeedbackSpecification.build(filter), pageable)
                .map(this::toDTO);
    }

    @Override
    public FeedbackDTO findById(Long id) {
        return toDTO(getEntity(id));
    }

    @Override
    @Transactional
    public void changeStatus(Long id, FeedbackStatus status) {
        Feedback feedback = getEntity(id);
        feedback.setStatus(status);
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        feedbackRepository.delete(getEntity(id));
    }

    // ===================== Helper =====================

    private Feedback getEntity(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));
    }

    private FeedbackDTO toDTO(Feedback f) {
        return FeedbackDTO.builder()
                .id(f.getId())
                .status(f.getStatus())
                .customerId(f.getCustomer() != null ? f.getCustomer().getId() : null)
                .customerFullName(f.getCustomer() != null ? f.getCustomer().getFullName() : null)
                .customerEmail(f.getCustomer() != null ? f.getCustomer().getEmail() : null)
                .customerMobile(f.getCustomer() != null ? f.getCustomer().getMobile() : null)
                .productId(f.getProduct() != null ? f.getProduct().getId() : null)
                .productName(f.getProduct() != null ? f.getProduct().getTitle() : GENERAL_FEEDBACK)
                .ratedStar(f.getRatedStar())
                .content(f.getContent())
                .createdDate(f.getCreatedDate())
                .build();
    }
}
