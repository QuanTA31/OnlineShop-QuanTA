package com.swd392.onlineshop.controller;

import com.swd392.onlineshop.dto.FeedbackDTO;
import com.swd392.onlineshop.dto.FeedbackFilter;
import com.swd392.onlineshop.enums.FeedbackStatus;
import com.swd392.onlineshop.service.FeedbackService;
import com.swd392.onlineshop.service.ProductService;
import com.swd392.onlineshop.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Man hinh "Feedbacks List" & "Feedback Details".
 * Theo dac ta, Marketing chi duoc xem va doi status nen khong co
 * mapping /new va /save.
 */
@Controller
@RequestMapping("/mkt/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private static final Set<String> SORTABLE = Set.of(
            "id", "customer.fullName", "product.title", "ratedStar", "status", "createdDate");
    private static final String DEFAULT_SORT = "createdDate";

    private final FeedbackService feedbackService;
    private final ProductService productService;

    @GetMapping
    public String list(@RequestParam(required = false) FeedbackStatus status,
                       @RequestParam(required = false) Long productId,
                       @RequestParam(required = false) Integer ratedStar,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer size,
                       @RequestParam(required = false) String sort,
                       @RequestParam(required = false, defaultValue = "asc") String dir,
                       Model model) {

        FeedbackFilter filter = FeedbackFilter.builder()
                .status(status)
                .productId(productId)
                .ratedStar(ratedStar)
                .keyword(DataUtil.trimToNull(keyword))
                .build();

        Pageable pageable = DataUtil.buildPageable(page, size, sort, dir, SORTABLE, DEFAULT_SORT);
        Page<FeedbackDTO> result = feedbackService.search(filter, pageable);

        model.addAttribute("page", result);
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("statuses", FeedbackStatus.values());
        model.addAttribute("products", productService.findAllForDropdown());
        model.addAttribute("stars", List.of(1, 2, 3, 4, 5));
        model.addAttribute("baseUrl", "/mkt/feedbacks");
        model.addAttribute("activeMenu", "feedbacks");

        Map<String, Object> filterParams = DataUtil.params();
        filterParams.put("status", status);
        filterParams.put("productId", productId);
        filterParams.put("ratedStar", ratedStar);
        filterParams.put("keyword", filter.getKeyword());
        String filterQuery = DataUtil.toQueryString(filterParams);

        Map<String, Object> allParams = DataUtil.params();
        allParams.putAll(filterParams);
        allParams.put("sort", sort);
        allParams.put("dir", dir);

        model.addAttribute("filterParams", filterQuery);
        model.addAttribute("queryParams", DataUtil.toQueryString(allParams));
        return "mkt/feedback/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("feedback", feedbackService.findById(id));
        model.addAttribute("statuses", FeedbackStatus.values());
        model.addAttribute("activeMenu", "feedbacks");
        return "mkt/feedback/details";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam FeedbackStatus status,
                               RedirectAttributes redirectAttributes) {
        feedbackService.changeStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Da doi trang thai feedback");
        return "redirect:/mkt/feedbacks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        feedbackService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Da xoa feedback");
        return "redirect:/mkt/feedbacks";
    }
}
