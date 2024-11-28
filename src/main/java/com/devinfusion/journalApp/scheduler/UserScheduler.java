package com.devinfusion.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.devinfusion.journalApp.cache.AppCache;
import com.devinfusion.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.repository.UserRepositoryImpl;
import com.devinfusion.journalApp.service.EmailService;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

//    @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail() {
        List<User> users = userRepositoryImpl.getUsersForSentimentAnalysis();
        for (User user : users) {
            List<JournalEntry> entries = user.getJournalEntries();
            List<String> filteredEntries = entries.stream()
                    .filter(entry -> entry.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getContent)
                    .toList();
            String entry = String.join(" ",filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days",sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }

}
