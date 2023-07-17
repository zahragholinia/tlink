package ir.tinyLink.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Mujtaba Hossaini
 * @project PodBusinessPanel
 * @created 10/04/2023 - 13:30
 * @email mujtaba.hossaini94@gmail.com
 */
public class LocalizedMessages {

    public static String getMessage(String message, Locale locale) throws UnsupportedEncodingException {

        if (StringUtil.checkPattern(message, "^\\{[\\w.]+\\}$")) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            message = message.replaceAll("[\\{\\}]", "");
            String bundleVal = bundle.getString(message);
            try {
                return new String(bundleVal.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                String errMsg = bundle.getString("err.server.process");
                return new String(errMsg.getBytes("ISO-8859-1"), "UTF-8");
            }
        } else
            return message;
    }


}
