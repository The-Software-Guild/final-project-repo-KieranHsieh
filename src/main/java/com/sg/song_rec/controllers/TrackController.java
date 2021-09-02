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
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("tracks")
public class TrackController {

    private TrackDao trackDao;
    private UserDao userDao;

    @Autowired
    public TrackController(TrackDao trackDao, UserDao userDao) {
        this.trackDao = trackDao;
        this.userDao = userDao;
    }

    @GetMapping("{id}")
    public Track getTrack(HttpServletRequest request, @PathVariable String id) {
        User currentUser = userDao.getUserById(request.getSession().getId());
        if(currentUser == null) {
            return null;
        }
        return trackDao.getTrack(currentUser, id);
    }

}
