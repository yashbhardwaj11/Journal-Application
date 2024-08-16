package com.devinfusion.journalApp.service;

import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testFindByUserName() {
        assertNotNull(userRepository.findByUserName("yash"));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "yash",
            "ram",
            "kumar"
    })
    public void testFindByUserName(String username) {
        assertNotNull(userRepository.findByUserName(username), "Failed for name: " + username);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testUserCreation(User user) {
        assertTrue(userService.saveUserForTest(user));
    }

   /* @BeforeEach //run before each test case
    void setup(){

    }

    @BeforeAll //run before each test case
    void  setup2(){

    }

    same like this AfterAll and AfterEach will run after the test
    */
}
