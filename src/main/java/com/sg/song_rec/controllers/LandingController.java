package com.sg.song_rec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LandingController {

    @GetMapping
    public String getLandingPage(Model model, HttpServletRequest request) {
        return "landingPage";
    }

}
