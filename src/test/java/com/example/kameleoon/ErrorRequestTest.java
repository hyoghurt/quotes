package com.example.kameleoon;

import com.example.kameleoon.model.request.QuoteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ErrorRequestTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(authorities = "USER")
    void getError() throws Exception {
        this.mockMvc.perform(get("/quotes/922"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void errorVote() throws Exception {
        this.mockMvc.perform(post("/quotes/1/vote/LIKEs"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/quotes/1/vote/l"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void updateError() throws Exception {
        String quote = "hello new quote test";

        QuoteRequest request = new QuoteRequest();
        request.setQuote(quote);

        this.mockMvc.perform(put("/quotes/922")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void deleteError() throws Exception {
        this.mockMvc.perform(delete("/quotes/922"))
                .andExpect(status().isOk());
    }
}
