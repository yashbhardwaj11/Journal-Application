package com.devinfusion.journalApp.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devinfusion.journalApp.entity.User;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    
    @Test
    public void getUsersForSentimentAnalysis(){
        List<User> users = userRepositoryImpl.getUsersForSentimentAnalysis();
        log.info(users.toString());
        assertNotNull(users);
    }

}
