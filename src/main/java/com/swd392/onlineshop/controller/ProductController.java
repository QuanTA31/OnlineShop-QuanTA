package com.swd392.onlineshop.controller;

import com.swd392.onlineshop.dto.ProductDTO;
import com.swd392.onlineshop.dto.ProductFilter;
import com.swd392.onlineshop.enums.SettingType;
import com.swd392.onlineshop.enums.Status;
import com.swd392.onlineshop.service.ProductService;
import com.swd392.onlineshop.service.SettingService;
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
 * Man hinh "Products List" & "Product Details" cua Marketing.
 */
@Controller
@RequestMapping("/mkt/products")
@RequiredArgsConstructor
public class ProductController {

    /** Chi cho phep sort theo cac cot dac ta liet ke. */
    private static final Set<String> SORTABLE = Set.of(
            "id", "title", "category.value", "listPrice", "salePrice", "featured", "status");
    private static final String DEFAULT_SORT = "updatedDate";

    private final ProductService productService;
    private final SettingService settingService;

    @GetMapping
    public String list(@RequestParam(required = false) Long categoryId,
                       @RequestParam(required = false) Status status,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer size,
                       @RequestParam(required = false) String sort,
                       @RequestParam(required = false, defaultValue = "asc") String dir,
                       Model model) {

        ProductFilter filter = ProductFilter.builder()
                .categoryId(categoryId)
                .status(status)
                .keyword(DataUtil.trimToNull(keyword))
                .build();

        Pageable pageable = DataUtil.buildPageable(page, size, sort, dir, SORTABLE, DEFAULT_SORT);
        Page<ProductDTO> result = productService.search(filter, pageable);

        model.addAttribute("page", result);
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("categories", settingService.findActiveByType(SettingType.PRODUCT_CATEGORY));
        model.addAttribute("statuses", Status.values());
        model.addAttribute("baseUrl", "/mkt/products");
        model.addAttribute("activeMenu", "products");

        Map<String, Object> filterParams = DataUtil.params();
        filterParams.put("categoryId", categoryId);
        filterParams.put("status", status);
        filterParams.put("keyword", filter.getKeyword());
        String filterQuery = DataUtil.toQueryString(filterParams);

        Map<String, Object> allParams = DataUtil.params();
        allParams.putAll(filterParams);
        allParams.put("sort", sort);
        allParams.put("dir", dir);

        model.addAttribute("filterParams", filterQuery);
        model.addAttribute("queryParams", DataUtil.toQueryString(allParams));
        return "mkt/product/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", ProductDTO.builder()
                .quantity(0)
                .featured(Boolean.FALSE)
                .status(Status.ACTIVE)
                .build());
        prepareFormData(model);
        return "mkt/product/form";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        prepareFormData(model);
        return "mkt/product/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") ProductDTO product,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            prepareFormData(model);
            return "mkt/product/form";
        }

        if (product.getId() == null) {
            productService.create(product);
            redirectAttributes.addFlashAttribute("success", "Da them san pham moi");
        } else {
            productService.update(product.getId(), product);
            redirectAttributes.addFlashAttribute("success", "Da cap nhat san pham");
        }
        return "redirect:/mkt/products";
    }

    @PostMapping("/{id}/toggle")
    public String toggleStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.toggleStatus(id);
        redirectAttributes.addFlashAttribute("success", "Da doi trang thai san pham");
        return "redirect:/mkt/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Da xoa san pham");
        return "redirect:/mkt/products";
    }

    private void prepareFormData(Model model) {
        model.addAttribute("categories", settingService.findActiveByType(SettingType.PRODUCT_CATEGORY));
        model.addAttribute("statuses", Status.values());
        model.addAttribute("activeMenu", "products");
    }
}
