package com.example.kameleoon.service;

import com.example.kameleoon.model.enums.VoteUnit;
import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.QuoteEvolutionResponse;
import com.example.kameleoon.model.response.QuoteResponse;
import com.example.kameleoon.model.response.QuotesResponse;

import java.util.concurrent.TimeUnit;

public interface QuoteService {
    Integer createQuote(QuoteRequest request, String username);
    QuoteResponse getQuoteById(Integer quoteId);
    void deleteQuoteById(Integer quoteId, String username);
    void updateQuoteById(Integer quoteId, QuoteRequest request, String username);
    void vote(Integer quoteId, VoteUnit value, String username);
    QuotesResponse getTopQuotes();
    QuoteEvolutionResponse getQuoteEvolution(Integer quoteId, Integer page, Long period, TimeUnit timeunit);
}
