package com.sg.song_rec.controllers;

import com.sg.song_rec.data.TrackDao;
import com.sg.song_rec.data.UserDao;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * The controller for the tracks endpoint
 */
@RestController
@RequestMapping("tracks")
public class TrackController {

    private TrackDao trackDao;
    private UserDao userDao;

    /**
     * Constructs a new TrackController object
     * @param trackDao The DAO used to access track information
     * @param userDao The DAO used to access users
     */
    @Autowired
    public TrackController(TrackDao trackDao, UserDao userDao) {
        this.trackDao = trackDao;
        this.userDao = userDao;
    }

    /**
     * A pass-through method that retrieves track information
     * @param request The request automatically passed to the method
     * @param id The ID of the track to get information for
     * @return The retrieved track if the ID and user were both valid, or null otherwise
     */
    @GetMapping("{id}")
    public Track getTrack(HttpServletRequest request, @PathVariable String id) {
        User currentUser = userDao.getUserById(request.getSession().getId());
        if(currentUser == null) {
            return null;
        }
        return trackDao.getTrack(currentUser, id);
    }

}
