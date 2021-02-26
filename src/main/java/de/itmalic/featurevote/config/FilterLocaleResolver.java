package de.itmalic.featurevote.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class FilterLocaleResolver implements LocaleResolver {

    private Locale DEFAULT_LOCALE = new Locale("en");

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = (Locale) request.getAttribute(LocaleFilter.class.getName() + ".LOCALE");
        return (locale != null ? locale : DEFAULT_LOCALE);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        request.setAttribute(LocaleFilter.class.getName() + ".LOCALE", locale);
    }

}
