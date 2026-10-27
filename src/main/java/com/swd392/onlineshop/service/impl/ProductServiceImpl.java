package com.swd392.onlineshop.service.impl;

import com.swd392.onlineshop.dto.ProductDTO;
import com.swd392.onlineshop.dto.ProductFilter;
import com.swd392.onlineshop.entity.Product;
import com.swd392.onlineshop.entity.Setting;
import com.swd392.onlineshop.enums.Status;
import com.swd392.onlineshop.exception.ResourceNotFoundException;
import com.swd392.onlineshop.repository.ProductRepository;
import com.swd392.onlineshop.repository.SettingRepository;
import com.swd392.onlineshop.repository.specification.ProductSpecification;
import com.swd392.onlineshop.service.ProductService;
import com.swd392.onlineshop.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SettingRepository settingRepository;

    @Override
    public Page<ProductDTO> search(ProductFilter filter, Pageable pageable) {
        return productRepository.findAll(ProductSpecification.build(filter), pageable)
                .map(this::toDTO);
    }

    @Override
    public ProductDTO findById(Long id) {
        return toDTO(getEntity(id));
    }

    @Override
    public List<ProductDTO> findAllForDropdown() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO dto) {
        Product product = new Product();
        applyChanges(product, dto);
        product.setSlug(generateUniqueSlug(dto.getTitle(), null));
        return toDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = getEntity(id);
        applyChanges(product, dto);
        // Doi title thi slug phai doi theo
        product.setSlug(generateUniqueSlug(dto.getTitle(), id));
        return toDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public void toggleStatus(Long id) {
        Product product = getEntity(id);
        product.setStatus(product.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.delete(getEntity(id));
    }

    // ===================== Helper =====================

    private Product getEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    private void applyChanges(Product product, ProductDTO dto) {
        product.setTitle(dto.getTitle().trim());
        product.setThumbnail(dto.getThumbnail());
        product.setBriefInfo(dto.getBriefInfo());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setListPrice(dto.getListPrice());
        product.setSalePrice(dto.getSalePrice());
        product.setFeatured(Boolean.TRUE.equals(dto.getFeatured()));
        product.setStatus(dto.getStatus());

        Setting category = settingRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", dto.getCategoryId()));
        product.setCategory(category);
    }

    /**
     * Slug phai duy nhat (cot unique). Neu trung thi them hau to -2, -3...
     * Khi sua, bo qua chinh ban ghi dang sua.
     */
    private String generateUniqueSlug(String title, Long currentId) {
        String base = SlugUtil.toSlug(title);
        if (base.isEmpty()) {
            base = "product";
        }
        String candidate = base;
        int suffix = 2;
        while (isSlugTaken(candidate, currentId)) {
            candidate = base + "-" + suffix++;
        }
        return candidate;
    }

    private boolean isSlugTaken(String slug, Long currentId) {
        return productRepository.findBySlug(slug)
                .filter(found -> !found.getId().equals(currentId))
                .isPresent();
    }

    private ProductDTO toDTO(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .title(p.getTitle())
                .slug(p.getSlug())
                .thumbnail(p.getThumbnail())
                .categoryId(p.getCategory() != null ? p.getCategory().getId() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getValue() : null)
                .briefInfo(p.getBriefInfo())
                .description(p.getDescription())
                .quantity(p.getQuantity())
                .listPrice(p.getListPrice())
                .salePrice(p.getSalePrice())
                .featured(p.getFeatured())
                .status(p.getStatus())
                .createdDate(p.getCreatedDate())
                .updatedDate(p.getUpdatedDate())
                .build();
    }
}
