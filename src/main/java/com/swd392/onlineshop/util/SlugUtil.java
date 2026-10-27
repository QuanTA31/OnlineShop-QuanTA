package com.swd392.onlineshop.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Sinh slug tu tieu de, ho tro tieng Viet co dau.
 * Vi du: "Ao so mi trang" -> "ao-so-mi-trang"
 */
public final class SlugUtil {

    private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern NON_SLUG_CHAR = Pattern.compile("[^a-z0-9-]");
    private static final Pattern MULTI_DASH = Pattern.compile("-{2,}");
    private static final Pattern EDGE_DASH = Pattern.compile("^-+|-+$");

    private SlugUtil() {
    }

    public static String toSlug(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        String text = input.trim().toLowerCase(Locale.ROOT);
        // Tieng Viet: chu "d" gach ngang khong tach dau duoc bang NFD nen phai thay truoc
        text = text.replace('đ', 'd');
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = DIACRITICS.matcher(text).replaceAll("");
        text = WHITESPACE.matcher(text).replaceAll("-");
        text = NON_SLUG_CHAR.matcher(text).replaceAll("");
        text = MULTI_DASH.matcher(text).replaceAll("-");
        text = EDGE_DASH.matcher(text).replaceAll("");
        return text;
    }
}
