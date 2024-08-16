package com.devinfusion.journalApp.controller;

import com.devinfusion.journalApp.cache.AppCache;
import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all =  userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            return ResponseEntity.ok(userService.saveAdmin(user));
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/clear-cache")
    public String clearCache(){
        try{
            appCache.init();
            return "Cache cleared";
        }catch (Exception e){
            return "Something went wrong error : " + e.getMessage();
        }
    }

}
