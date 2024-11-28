package com.devinfusion.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testEmail() {
        assertTrue(emailService.sendEmail(
                "yash82206@gmail.com",
                "This is test mail from java",
                "Hi we are testing test mail from java using java mail sender and i am running from test class hope you have a great day ahead."
        ));
    }

}
