package com.swd392.onlineshop.enums;

/**
 * Trang thai cua customer/contact. Theo dac ta: he thong tu sinh,
 * nguoi dung khong nhap tay.
 */
public enum CustomerStatus {
    CONTACT("Contact"),
    POTENTIAL("Potential"),
    CUSTOMER("Customer");

    private final String label;

    CustomerStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
