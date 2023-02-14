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

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserService service, QuoteService quoteService) {
        return args -> {
            List<String> userList = generateUsers(service);
            List<Integer> quotesIdList = generateQuotes(quoteService, userList);
            generateData(quoteService, userList, quotesIdList);
        };
    }

    private static List<String> generateUsers(UserService service) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setUsername("user" + i);
            signUpRequest.setPassword("user" + i);
            service.createUser(signUpRequest);
            list.add(signUpRequest.getUsername());
        }

        return list;
    }

    private static List<Integer> generateQuotes(QuoteService quoteService, List<String> userList) {
        List<Integer> list = new ArrayList<>();

        for (String user : userList) {
            for (int i = 0; i < 4; i++) {
                QuoteRequest quoteRequest = new QuoteRequest();
                quoteRequest.setQuote("hello test " + i + user);
                Integer quoteId = quoteService.createQuote(quoteRequest, user);
                list.add(quoteId);
            }
        }

        return list;
    }


    private static void generateData(QuoteService quoteService, List<String> userList, List<Integer> quoteIdList) {
        Random RANDOM = new Random();

        for (Integer quoteId : quoteIdList) {
            for (String user : userList) {

                VoteUnit value =  VoteUnit.values()[RANDOM.nextInt(VoteUnit.values().length)];
                try {
                    quoteService.vote(quoteId, value, user);
                } catch (Exception ignored) {

                }
            }
        }
    }
}
