package com.library.binhson.userservice.ultils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    private  static final String PHONE_REGEX="/\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})/";
    public static boolean isValidPhone(String phone) {
        Pattern pattern=Pattern.compile(PHONE_REGEX);
        Matcher matcher=pattern.matcher(phone);
        return matcher.matches();
    }
}
