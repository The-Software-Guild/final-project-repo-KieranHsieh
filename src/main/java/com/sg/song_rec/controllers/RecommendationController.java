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

/**
 * The controller used to display recommendations to the user
 */
@Controller
@RequestMapping("recommendations")
public class RecommendationController {

    private AuthenticationService authService;
    private RecommendationService recommendationService;

    /**
     * Constructs a new RecommendationController
     * @param authService The authentication service used by the controller
     * @param recommendationService The recommendation service used by the controller
     */
    @Autowired
    public RecommendationController(AuthenticationService authService, RecommendationService recommendationService) {
        this.authService = authService;
        this.recommendationService = recommendationService;
    }

    /**
     * Gets the Thymeleaf template for the recommendation page
     * @param request The request automatically passed into the method
     * @param model THe model automatically passed into the method
     * @return The Thymeleaf template name for the recommendation page, or a redirect to the API endpoint
     */
    @GetMapping
    public String getRecommendations(HttpServletRequest request, Model model) {
        User currentUser = authService.getUserById(request.getSession().getId());
        // See if the current user session has an associated token in the database
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
