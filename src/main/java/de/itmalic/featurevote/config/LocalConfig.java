package de.itmalic.featurevote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalConfig {

    @Autowired
    FilterLocaleResolver filterLocaleResolver;

    @Bean
    public LocaleResolver localeResolver() {

        return filterLocaleResolver;
    }
}
