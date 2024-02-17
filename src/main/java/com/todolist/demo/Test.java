package com.todolist.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {
    
    private static final Logger log = LoggerFactory.getLogger(Test.class);
    @Bean
    CommandLineRunner initDataBase(TaskRepository repository){

        return args -> {
            log.info("Preloading" + repository.save(new Task("Take a bath")));
            log.info("Preloading" + repository.save(new Task("Go shopping")));
        };
    }
}
