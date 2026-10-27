package com.swd392.onlineshop.service;

import com.swd392.onlineshop.dto.ProductDTO;
import com.swd392.onlineshop.dto.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductDTO> search(ProductFilter filter, Pageable pageable);

    ProductDTO findById(Long id);

    /** Danh sach rut gon de do vao dropdown loc feedback. */
    List<ProductDTO> findAllForDropdown();

    ProductDTO create(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    /** Chuc nang show/hide trong dac ta "Products List". */
    void toggleStatus(Long id);

    void delete(Long id);
}
