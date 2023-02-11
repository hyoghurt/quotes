package com.example.kameleoon.controller;

import com.example.kameleoon.model.enums.VoteUnit;
import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.QuoteEvolutionResponse;
import com.example.kameleoon.model.response.QuoteResponse;
import com.example.kameleoon.model.response.QuotesResponse;
import com.example.kameleoon.service.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @PostMapping("/quotes")
    public Integer createQuote(@RequestBody QuoteRequest request, Principal principal) {
        return quoteService.createQuote(request, principal.getName());
    }

    @GetMapping("/quotes/{id}")
    public QuoteResponse getQuoteById(@PathVariable("id") Integer quoteId) {
        return quoteService.getQuoteById(quoteId);
    }

    @PutMapping("/quotes/{id}")
    public void updateQuoteById(@PathVariable("id") Integer quoteId,
                                @RequestBody QuoteRequest request, Principal principal) {
        quoteService.updateQuoteById(quoteId, request, principal.getName());
    }

    @DeleteMapping("/quotes/{id}")
    public void deleteQuoteById(@PathVariable("id") Integer id, Principal principal) {
        quoteService.deleteQuoteById(id, principal.getName());
    }

    @PostMapping("/quotes/{id}/vote/{value}")
    public void vote(@PathVariable("id") Integer quoteId, @PathVariable("value") VoteUnit value,
                     Principal principal) {
        quoteService.vote(quoteId, value, principal.getName());
    }

    @GetMapping("/quotes/{id}/evolution")
    public QuoteEvolutionResponse getQuoteEvolution(@PathVariable("id") Integer quoteId,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "1") Long period,
                                                    @RequestParam(defaultValue = "HOURS") TimeUnit timeunit) {
        return quoteService.getQuoteEvolution(quoteId, page, period, timeunit);
    }

    @GetMapping("/quotes")
    public QuotesResponse getTopQuotes() {
        return quoteService.getTopQuotes();
    }
}
