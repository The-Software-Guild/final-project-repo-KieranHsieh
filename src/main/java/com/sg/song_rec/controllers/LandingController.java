package com.sg.song_rec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The controller for the landing page of the application
 */
@Controller
public class LandingController {

    /**
     * Returns the landingPage Thymeleaf template
     * @return The name of the landingPage Thymeleaf template
     */
    @GetMapping
    public String getLandingPage() {
        return "landingPage";
    }

}
