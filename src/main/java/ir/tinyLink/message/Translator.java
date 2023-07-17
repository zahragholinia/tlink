package ir.tinyLink.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    static String toLocale(String messageCode, Object[] params) {
        Locale requestLocale = LocaleContextHolder.getLocale();

        return sendMessage(messageCode, params, requestLocale);
    }

    private static String sendMessage(String messageCode, Object[] params, Locale requestLocale) {
        return messageSource.getMessage(messageCode, params, requestLocale);
    }

}