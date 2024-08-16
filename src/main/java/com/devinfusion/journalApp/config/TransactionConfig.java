package com.devinfusion.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public PlatformTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}




/*We have added transactional annotation in the journalEntryController -> saveEntry function which means it will take the complete request as one request if by any chance the
request failed in the middle it will revert back the previous changes also that were pushed into the database

For this to happen Spring has provided PlatformTransactionManager interface which work around with MongoTransactionManager
and maintain the atom principles of database

MongoTransactionManager required database factory which help it to connect to the database
*
*/
