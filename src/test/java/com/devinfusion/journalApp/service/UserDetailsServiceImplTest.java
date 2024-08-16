package com.devinfusion.journalApp.service;

import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.impl.UserDetailsServiceImpl;
import com.devinfusion.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
//@SpringBootTest ////this is used to initiase with spring context if we dont wanna do that we can use mock
public class UserDetailsServiceImplTest {
//    @Autowired //// when we were using spring boot test annotation spring is managing the complete context if we only want to test this service indivisually we can do using injectmocks annotations
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

//    @MockBean this is also done when we were using spring boot test annotation
    @Mock
    private UserRepository userRepository;

    @BeforeEach // this is called when we use mock annotation other then mockbean this help to initialise all the mocks and then inject in the above injectmock service
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("yash").password("hsbdgfbdshhdbgb").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("yash");
        Assertions.assertNotNull(user);
    }

}
