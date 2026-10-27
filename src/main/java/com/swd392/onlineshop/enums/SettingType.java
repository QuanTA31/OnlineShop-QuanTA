package com.swd392.onlineshop.enums;

/**
 * Cac loai du lieu cau hinh luu trong bang settings.
 * Theo muc 3.a cua ban requirements: Post Categories, Product Categories,
 * User Roles, Admin Menu... deu la system setting.
 */
public final class SettingType {

    public static final String PRODUCT_CATEGORY = "PRODUCT_CATEGORY";
    public static final String POST_CATEGORY = "POST_CATEGORY";
    public static final String USER_ROLE = "USER_ROLE";
    public static final String ADMIN_MENU = "ADMIN_MENU";

    private SettingType() {
    }
}
