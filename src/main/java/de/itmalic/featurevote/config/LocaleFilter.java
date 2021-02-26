package de.itmalic.featurevote.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class LocaleFilter implements Filter {

    private static final String DEFAULT_LOCALE = "en";
    private static final String[] AVAILABLE_LOCALES = new String[] {"en", "de"};

    public LocaleFilter() {}

    private List<String> getServletRequestParts(ServletRequest request)
    {
        String[] splittedParts = ((HttpServletRequest) request).getServletPath().split("/");
        List<String> result = new ArrayList<String>();

        for (String sp : splittedParts)
        {
            if (sp.trim().length() > 0)
                result.add(sp);
        }

        return result;
    }

    private Locale getLocaleFromRequestParts(List<String> parts)
    {
        if (parts.size() > 0)
        {
            for (String lang : AVAILABLE_LOCALES)
            {
                if (lang.equals(parts.get(0)))
                {
                    return new Locale(lang);
                }
            }
        }

        return null;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        List<String> requestParts = this.getServletRequestParts(request);
        Locale locale = this.getLocaleFromRequestParts(requestParts);

        if (locale != null)
        {
            request.setAttribute(LocaleFilter.class.getName() + ".LOCALE", locale);

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < requestParts.size(); i++)
            {
                sb.append('/');
                sb.append((String) requestParts.get(i));
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(sb.toString());
            dispatcher.forward(request, response);
        }
        else
        {
            request.setAttribute(LocaleFilter.class.getName() + ".LOCALE", new Locale(DEFAULT_LOCALE));
            chain.doFilter(request, response);
        }
    }

}

