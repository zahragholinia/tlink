package ir.tinyLink.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String generateRandomString() {
        UUID prefix = UUID.randomUUID();

        return prefix.toString().replace("-", "");
    }

    public static String generateReferenceId(String timestamp) {
        UUID prefix = UUID.nameUUIDFromBytes(timestamp.getBytes());
        return prefix.toString().concat("-").concat("podBusinessPanel");
    }

//	public static String maskString(String str) {
//		if (str == null)
//			return null;
//		return str.replaceAll("(\\w{5})(\\w+)(\\w{5})", "$1***$3");
//	}


    public static String encodeUTF8String(String str) throws UnsupportedEncodingException {

        return URLEncoder.encode(str, "UTF-8");
    }

    /**
     * حروف فارسی را جایگزین حروف عربی می کند
     * همچنین اسپیس را نورمال می کند
     *
     * @param content
     * @return
     */
    public static String replaceArabicAlphabetsToPersianAlphabets(String content) {
        return StringUtils.normalizeSpace(content.replaceAll("ي", "ی").replaceAll("ك", "ک"));
    }

    /**
     * تبدیل حروف عربی به فارسی به همراه انکودینگ
     *
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String replaceArabicAlphabetsToPersianAlphabetsWithEncoding(String content) throws UnsupportedEncodingException {
        content = StringUtils.normalizeSpace(content.replaceAll("ي", "ی").replaceAll("ك", "ک"));
        return URLEncoder.encode(content, "UTF-8");
    }

    public static String decodeUTF8String(String str) throws UnsupportedEncodingException {

        return URLDecoder.decode(str, "UTF-8");
    }


    public static String generateRandomString(int length, boolean useLetter, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetter, useNumbers);
    }

    /**
     * checks pattern of a String
     *
     * @param content
     * @param regex
     * @return boolean
     */
    public static boolean checkPattern(String content, String regex) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(regex))
            return false;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        return matcher.matches();
    }

    public static String fillStringValuesForCells(Object object) {
        return object != null ? String.valueOf(object) : "";
    }

//<<<<<<< HEAD

    /**
     * دریافت مبلغ که با کاما جدا شده اند
     * اگر شی نال باشد صفر مقداردهی می شود
     *
     * @param object
     * @return 35, 000, 000 -> نمونه
     */
    public static String fillPriceValue(Object object) {
        BigDecimal price = object != null ? new BigDecimal(String.valueOf(object)) : new BigDecimal("0");
        return price.toString();
    }


    //=======
//>>>>>>> 45-refactor
    public static String convertPersianNumber(String value) {
        value = value.replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹")
                .replace("0", "۰");

        return value;
    }
//<<<<<<< HEAD

    public static String maskString(String str) {
        if (str == null)
            return null;
        return str.replaceAll("(\\w{5})(\\w+)(\\w{5})", "$1***$3");
    }
//=======
//>>>>>>> 45-refactor

}
