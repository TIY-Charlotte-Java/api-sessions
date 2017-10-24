package com.example.apisessions.controllers;


import com.example.apisessions.models.User;
import com.example.apisessions.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class ApiController {
    private final String USER_KEY = "user";

    @Autowired
    UserRepository userRepo;

    @CrossOrigin
    @GetMapping("/user")
    public User getUser(HttpSession session) {
        return (User)session.getAttribute(USER_KEY);
    }

    // persist (aka save) the user they sent us
    @CrossOrigin
    @PostMapping("/user")
    public void signUp(@RequestBody User user, HttpServletResponse response) throws IOException {
        // if we can't find a user with the name specified..
        if (userRepo.findFirstByName(user.getName()) == null) {
            userRepo.save(user);
        } else {
            // we found a user with that name, respond with
            // an error code
            response.sendError(422, "User already exists");
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public void login(@RequestBody User user, HttpSession session, HttpServletResponse response) throws IOException {
        // HEY, REPOSITORY
        // Do you have any users with this guy's name and password?
        User repoUser = userRepo.findFirstByNameAndPassword(user.getName(), user.getPassword());

        if (repoUser != null) {
            session.setAttribute(USER_KEY, repoUser);
        } else {
            response.sendError(401, "Bad credentials. Nice try a-hole.");
        }
    }
}
