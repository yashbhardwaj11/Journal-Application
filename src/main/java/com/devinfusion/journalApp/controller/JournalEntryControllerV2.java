package com.devinfusion.journalApp.controller;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.service.JournalEntryService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id) {
        return journalEntryService.getJournalEntryById(id);
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry existingEntry = journalEntryService.getJournalEntryById(id);
        if (existingEntry != null) {
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                existingEntry.setTitle(newEntry.getTitle());
            }
            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                existingEntry.setContent(newEntry.getContent());
            }
        }
        journalEntryService.saveEntry(existingEntry);
        return existingEntry;
    }

    @DeleteMapping("/id/{id}")
    public boolean delelteJournalEntry(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return true;
    }

}
