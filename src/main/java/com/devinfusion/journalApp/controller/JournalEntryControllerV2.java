package com.devinfusion.journalApp.controller;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.entity.User;
import com.devinfusion.journalApp.service.JournalEntryService;
import com.devinfusion.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> entries = user.getJournalEntries();
        if (entries != null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(entries,HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            JournalEntry savedEntry = journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(id);
        return journalEntry.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId id,
                                                            @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).toList();
        if (!collect.isEmpty()){
            Optional<JournalEntry> existingEntryOptional = journalEntryService.getJournalEntryById(id);
            if (existingEntryOptional.isPresent()) {
                JournalEntry existingEntry = existingEntryOptional.get();
                if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                    existingEntry.setTitle(newEntry.getTitle());
                }
                if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                    existingEntry.setContent(newEntry.getContent());
                }
                JournalEntry updatedEntry = journalEntryService.saveEntry(existingEntry);
                return ResponseEntity.ok(updatedEntry);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(id);
        if (journalEntry.isPresent()) {
            boolean removed = journalEntryService.deleteById(id,username);
            if(removed)
                return ResponseEntity.noContent().build();
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
