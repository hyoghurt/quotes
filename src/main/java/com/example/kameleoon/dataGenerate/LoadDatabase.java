package com.example.kameleoon.dataGenerate;


import com.example.kameleoon.model.enums.VoteUnit;
import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.request.SignUpRequest;
import com.example.kameleoon.service.QuoteService;
import com.example.kameleoon.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserService service, QuoteService quoteService) {
        return args -> {
            generateData(quoteService);
//            generateUsers(service);
//            generateQuotes(quoteService);
        };
    }

    private static void generateQuotes(QuoteService quoteService) {
        for (int i = 0; i < 10; i++) {
            QuoteRequest quoteRequest = new QuoteRequest();
            quoteRequest.setQuote("hello test " + i);
            quoteService.createQuote(quoteRequest, "user" + i);
        }
    }

    private static void generateUsers(UserService service) {
        for (int i = 0; i < 5; i++) {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setUsername("user" + i);
            signUpRequest.setPassword("user" + i);
            service.createUser(signUpRequest);
        }
    }

    private static void generateData(QuoteService quoteService) {
        Random RANDOM = new Random();
        List<Integer> quoteIds = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            QuoteRequest request = new QuoteRequest();
            request.setQuote("hello new " + i);
            String usernamequote = "quote" + i;
            quoteIds.add(quoteService.createQuote(request, usernamequote));
        }

        for (int i = 0; i < 50; i++) {
            for (Integer quoteId : quoteIds) {
                VoteUnit value =  VoteUnit.values()[RANDOM.nextInt(VoteUnit.values().length)];
                quoteService.vote(quoteId, value, String.valueOf(UUID.randomUUID()));
            }
        }
    }
}
