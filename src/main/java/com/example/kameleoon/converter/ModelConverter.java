package com.example.kameleoon.converter;

import com.example.kameleoon.model.entity.QuoteEntity;
import com.example.kameleoon.model.entity.VoteEntity;
import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.BarResponse;
import com.example.kameleoon.model.response.QuoteResponse;
import com.example.kameleoon.model.response.QuotesResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelConverter {
    public QuoteEntity quoteRequestToQuoteEntity(QuoteRequest quoteRequest) {
        QuoteEntity entity = new QuoteEntity();
        entity.setQuote(quoteRequest.getQuote());
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));
        entity.setVotesSum(0);
        return entity;
    }

    public QuoteResponse quoteEntityToQuoteResponse(QuoteEntity quoteEntity) {
        QuoteResponse response = new QuoteResponse();
        response.setQuote(quoteEntity.getQuote());
        response.setId(quoteEntity.getId());
        response.setTimestamp(quoteEntity.getTimestamp());
        response.setUsername(quoteEntity.getUsername());
        response.setVotesSum(quoteEntity.getVotesSum());
        return response;
    }

    public QuotesResponse entityPageToQuotesResponse(Page<QuoteEntity> quoteEntityPage) {
        QuotesResponse response = new QuotesResponse();

        List<QuoteResponse> elements = quoteEntityPage.getContent().stream()
                .map(this::quoteEntityToQuoteResponse).collect(Collectors.toList());

        response.setElements(elements);
        response.setTotalElements((int) quoteEntityPage.getTotalElements());

        return response;
    }

    public BarResponse voteEntityToBarResponse(VoteEntity voteEntity) {
        BarResponse barResponse = new BarResponse();
        barResponse.setTimestamp(voteEntity.getTimestamp());
        barResponse.setValue(voteEntity.getCurrentVotesSum());
        return barResponse;
    }
}
