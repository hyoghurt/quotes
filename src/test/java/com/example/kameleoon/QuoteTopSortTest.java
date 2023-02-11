package com.example.kameleoon;

import com.example.kameleoon.model.enums.VoteUnit;
import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.QuoteResponse;
import com.example.kameleoon.model.response.QuotesResponse;
import com.example.kameleoon.service.QuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuoteTopSortTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuoteService quoteService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random RANDOM = new Random();

    @Test
    @WithMockUser(authorities = "USER", username = "user0", password = "user0")
    void initQuote() throws Exception {

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

        String json = this.mockMvc.perform(get("/quotes"))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        QuotesResponse quotesResponse = objectMapper.readValue(json, QuotesResponse.class);

        int first = Integer.MAX_VALUE;
        List<QuoteResponse> list = quotesResponse.getElements();
        for (QuoteResponse quoteResponse : list) {
            int one = quoteResponse.getVotesSum();
            assertTrue(first >= one);
            first = one;
        }

    }
}
