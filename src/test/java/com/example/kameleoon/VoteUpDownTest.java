package com.example.kameleoon;

import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.QuotesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoteUpDownTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String quoteIdUpDown;

    @Test
    @Order(1)
    @WithMockUser(authorities = "USER", username = "userupdown0", password = "user0")
    @Sql(value = {"classpath:./drop_table.sql", "classpath:./schema.sql"})
    void initQuote() throws Exception {
        QuoteRequest request = new QuoteRequest();
        String quote = "hello new quote test";
        request.setQuote(quote);

        quoteIdUpDown = this.mockMvc.perform(myPost(request))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test
    @Order(2)
    @WithMockUser(authorities = "USER", username = "userupdown1", password = "user1")
    void voteLike() throws Exception {
        assertTop(0);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/up"))
                .andExpect(status().isOk());

        assertTop(1);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/up"))
                .andExpect(status().isOk());

        assertTop(0);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/down"))
                .andExpect(status().isOk());

        assertTop(-1);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/down"))
                .andExpect(status().isOk());

        assertTop(0);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/up"))
                .andExpect(status().isOk());

        assertTop(1);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/down"))
                .andExpect(status().isOk());

        assertTop(-1);

        this.mockMvc.perform(post("/quotes/" + quoteIdUpDown + "/vote/up"))
                .andExpect(status().isOk());

        assertTop(1);
    }

    private void assertTop(Integer value) throws Exception {
        String json = this.mockMvc.perform(get("/quotes"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        QuotesResponse quotesResponse = objectMapper.readValue(json, QuotesResponse.class);
        assertEquals(quotesResponse.getElements().get(0).getVotesSum(), value);
    }

    private RequestBuilder myPost(Object body) throws JsonProcessingException {
        return post("/quotes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body));
    }
}
