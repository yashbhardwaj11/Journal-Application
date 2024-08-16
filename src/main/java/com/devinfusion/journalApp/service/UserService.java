package com.devinfusion.journalApp.service;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class); ////isko hatane k liye hum slf4j annotation ka bhi use kr skte h


    @Autowired 
    private UserRepository userRepository;

    public User saveEntry(User user){
        return userRepository.save(user);
    }

    public User saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            return userRepository.save(user);
        }catch (Exception e){
//            logger.error("Error occurred for {} : " , user.getUserName(),e); ////jb feild m create krte h toh logger vrna log
            log.error("Error occurred for {} : " , user.getUserName(),e);
            return null;
        }
    }

    public boolean saveUserForTest(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
        return true;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void deleteByUserName(String username){
        userRepository.deleteByUserName(username);
    }


    public User saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ADMIN","USER"));
        return userRepository.save(user);
    }
}