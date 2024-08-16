package com.devinfusion.journalApp.controller;

import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.entity.WeatherResponse;
import com.devinfusion.journalApp.service.UserService;
import com.devinfusion.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User existingUser = userService.findByUserName(username);
        if (existingUser != null){
            if (user.getUserName() != null && !user.getUserName().isEmpty()){
                existingUser.setUserName(user.getUserName());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()){
                existingUser.setPassword(user.getPassword());
            }
            userService.saveNewUser(existingUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUserName(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String greetings = "";
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        if (weatherResponse != null){
            greetings = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + username + greetings, HttpStatus.OK);
    }

}
