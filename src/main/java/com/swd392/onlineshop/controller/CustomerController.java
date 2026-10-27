package com.swd392.onlineshop.controller;

import com.swd392.onlineshop.dto.CustomerDTO;
import com.swd392.onlineshop.dto.CustomerFilter;
import com.swd392.onlineshop.enums.CustomerStatus;
import com.swd392.onlineshop.enums.Gender;
import com.swd392.onlineshop.service.CustomerService;
import com.swd392.onlineshop.util.DataUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Set;

/**
 * Man hinh "Customers List" & "Customer Details" cua Marketing.
 */
@Controller
@RequestMapping("/mkt/customers")
@RequiredArgsConstructor
public class CustomerController {

    private static final Set<String> SORTABLE = Set.of(
            "id", "fullName", "email", "mobile", "status");
    private static final String DEFAULT_SORT = "updatedDate";

    /**
     * Chua co dang nhap nen tam ghi nhan nguoi sua la "Marketing".
     * Khi bo sung authentication thi lay tu nguoi dung dang dang nhap.
     */
    private static final String CURRENT_USER = "Marketing";

    private final CustomerService customerService;

    @GetMapping
    public String list(@RequestParam(required = false) CustomerStatus status,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer size,
                       @RequestParam(required = false) String sort,
                       @RequestParam(required = false, defaultValue = "asc") String dir,
                       Model model) {

        CustomerFilter filter = CustomerFilter.builder()
                .status(status)
                .keyword(DataUtil.trimToNull(keyword))
                .build();

        Pageable pageable = DataUtil.buildPageable(page, size, sort, dir, SORTABLE, DEFAULT_SORT);
        Page<CustomerDTO> result = customerService.search(filter, pageable);

        model.addAttribute("page", result);
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("statuses", CustomerStatus.values());
        model.addAttribute("baseUrl", "/mkt/customers");
        model.addAttribute("activeMenu", "customers");

        Map<String, Object> filterParams = DataUtil.params();
        filterParams.put("status", status);
        filterParams.put("keyword", filter.getKeyword());
        String filterQuery = DataUtil.toQueryString(filterParams);

        Map<String, Object> allParams = DataUtil.params();
        allParams.putAll(filterParams);
        allParams.put("sort", sort);
        allParams.put("dir", dir);

        model.addAttribute("filterParams", filterQuery);
        model.addAttribute("queryParams", DataUtil.toQueryString(allParams));
        return "mkt/customer/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        prepareFormData(model);
        return "mkt/customer/form";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        prepareFormData(model);
        return "mkt/customer/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") CustomerDTO customer,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Giu lai bang lich su khi form bi tra ve vi loi validate
            if (customer.getId() != null) {
                customer.setHistories(customerService.findById(customer.getId()).getHistories());
            }
            prepareFormData(model);
            return "mkt/customer/form";
        }

        if (customer.getId() == null) {
            customerService.create(customer);
            redirectAttributes.addFlashAttribute("success", "Da them customer moi");
        } else {
            customerService.update(customer.getId(), customer, CURRENT_USER);
            redirectAttributes.addFlashAttribute("success", "Da cap nhat customer");
        }
        return "redirect:/mkt/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Da xoa customer");
        return "redirect:/mkt/customers";
    }

    private void prepareFormData(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("statuses", CustomerStatus.values());
        model.addAttribute("activeMenu", "customers");
    }
}
