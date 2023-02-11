package com.example.kameleoon;

import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.QuoteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrudQuoteTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String quoteIdCrud;

    @Test
    @Order(2)
    @WithMockUser(authorities = "USER", username = "crudTestUser0", password = "crudTestUser0")
    void createAndGetThenOk() throws Exception {
        String quote = "hello new quote test";

        QuoteRequest request = new QuoteRequest();
        request.setQuote(quote);

        quoteIdCrud = this.mockMvc.perform(myPost(request))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        requestGetAndCheckBody(quote, quoteIdCrud);
    }

    @Test
    @Order(3)
    @WithMockUser(authorities = "USER", username = "crudTestUser1", password = "crudTestUser1")
    void updateThenForbidden() throws Exception {
        String quote = "hello updateQuoteById new quote test";

        QuoteRequest request = new QuoteRequest();
        request.setQuote(quote);

        this.mockMvc.perform(myPut("/quotes/" + quoteIdCrud, request))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    @WithMockUser(authorities = "USER", username = "crudTestUser0", password = "crudTestUser0")
    void updateThenOk() throws Exception {
        String quote = "hello updateQuoteById new quote test";

        QuoteRequest request = new QuoteRequest();
        request.setQuote(quote);

        this.mockMvc.perform(myPut("/quotes/" + quoteIdCrud, request))
                .andExpect(status().isOk());

        requestGetAndCheckBody(quote, quoteIdCrud);
    }

    @Test
    @Order(5)
    @WithMockUser(authorities = "USER", username = "crudTestUser1", password = "crudTestUser1")
    void deleteOtherUserThenOk() throws Exception {
        this.mockMvc.perform(delete("/quotes/" + quoteIdCrud))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @WithMockUser(authorities = "USER", username = "crudTestUser0", password = "crudTestUser0")
    void deleteThenOk() throws Exception {
        this.mockMvc.perform(delete("/quotes/" + quoteIdCrud))
                .andExpect(status().isOk());
    }

    private void requestGetAndCheckBody(String quote, String id) throws Exception {
        String json = this.mockMvc.perform(get("/quotes/" + id))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        QuoteResponse response = objectMapper.readValue(json, QuoteResponse.class);
        assertEquals(response.getQuote(), quote);
    }

    private RequestBuilder myPost(Object body) throws JsonProcessingException {
        return post("/quotes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body));
    }

    private RequestBuilder myPut(String url, Object body) throws JsonProcessingException {
        return put(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body));
    }
}
