package ir.tinyLink.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Configuration
public class AcceptHeaderResolver extends AcceptHeaderLocaleResolver {

    List<Locale> LOCALES = Arrays.asList(new Locale("fa"), new Locale("en"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? new Locale("fa")
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    @Override
    public Locale getDefaultLocale() {
        return new Locale("fa");
    }


}
