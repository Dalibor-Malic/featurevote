package de.itmalic.featurevote.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;

@Controller
public class LandingPageController {

    @GetMapping("/landing")
    public String getInternationalPage() {
        return "international";
    }
}
