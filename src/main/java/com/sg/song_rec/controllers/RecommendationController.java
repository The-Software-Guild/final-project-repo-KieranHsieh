package com.sg.song_rec.controllers;

import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.service.AuthenticationService;
import com.sg.song_rec.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("recommendations")
public class RecommendationController {

    private AuthenticationService authService;
    private RecommendationService recommendationService;

    @Autowired
    public RecommendationController(AuthenticationService authService, RecommendationService recommendationService) {
        this.authService = authService;
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public String display(HttpServletRequest request, Model model) {
        User currentUser = authService.getUserById(request.getSession().getId());
        if(currentUser != null) {
            List<Track> tracks = recommendationService.getRecommendations(currentUser);
            model.addAttribute("tracks", tracks);
            model.addAttribute("user", recommendationService.getUserProfile(currentUser));
            return "recommendationView";
        }
        else {
            return "redirect:/api/";
        }
    }
}
