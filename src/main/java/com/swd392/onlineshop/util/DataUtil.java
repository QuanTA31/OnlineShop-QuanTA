package com.swd392.onlineshop.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Helper dung chung cho cac man hinh danh sach.
 */
public final class DataUtil {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private DataUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String trimToNull(String value) {
        return isBlank(value) ? null : value.trim();
    }

    /** Bao quanh tu khoa bang % va ha chu thuong de dung voi LIKE. */
    public static String likePattern(String keyword) {
        return "%" + keyword.trim().toLowerCase() + "%";
    }

    /**
     * Dung Pageable tu tham so URL. Chi chap nhan cac cot nam trong allowedSorts
     * de tranh nguoi dung truyen ten cot tuy y gay loi query.
     */
    public static Pageable buildPageable(Integer page, Integer size, String sort, String dir,
                                         Set<String> allowedSorts, String defaultSort) {
        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;

        String sortField = (sort != null && allowedSorts.contains(sort)) ? sort : defaultSort;
        Sort.Direction direction = "desc".equalsIgnoreCase(dir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));
    }

    /**
     * Noi cac tham so thanh chuoi dang "&a=1&b=2", bo qua gia tri rong.
     * Dung de giu nguyen filter khi bam sang trang khac hoac doi cot sap xep.
     */
    public static String toQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value == null || (value instanceof String s && s.isBlank())) {
                continue;
            }
            sb.append('&')
                    .append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    /** Tien ich tao map giu nguyen thu tu chen de truyen vao toQueryString. */
    public static Map<String, Object> params() {
        return new LinkedHashMap<>();
    }
}
