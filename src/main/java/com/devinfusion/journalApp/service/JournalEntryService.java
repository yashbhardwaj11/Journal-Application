package com.devinfusion.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.devinfusion.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.repository.JournalEntryRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalEntryService {

    @Autowired 
    private JournalEntryRepository journalEntryRepository;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private UserService userService;

    public JournalEntry saveEntry(JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveEntry(user);
            return savedEntry;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry", e);
        }
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        try{
            User user = userService.findByUserName(username);
            boolean removed = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
            if (removed){
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry ", e);
        }
    }

}