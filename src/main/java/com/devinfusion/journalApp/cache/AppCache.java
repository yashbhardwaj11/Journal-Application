package com.devinfusion.journalApp.cache;

import com.devinfusion.journalApp.entity.ConfigJournalAppEntity;
import com.devinfusion.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    
    public enum keys{
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct //whenever a method in a bean have an annotation postConstruct the complete flows goes first to that method
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity entity : all){
            APP_CACHE.put(entity.getKey(),entity.getValue());
        }
    }
}
