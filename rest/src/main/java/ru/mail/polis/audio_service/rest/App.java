package ru.mail.polis.audio_service.rest;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public MongoDatabase db() {
        return new MongoClient("localhost", 27017).getDatabase("Audio");
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
