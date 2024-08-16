package com.devinfusion.journalApp.repository;

import com.devinfusion.journalApp.entity.JournalEntry;
import com.devinfusion.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,ObjectId>{

    User findByUserName(String username);

    void deleteByUserName(String username);
    
}
