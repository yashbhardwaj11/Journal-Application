package com.devinfusion.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired 
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }



    
}