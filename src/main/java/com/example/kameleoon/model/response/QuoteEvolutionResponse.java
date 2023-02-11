package com.example.kameleoon.model.response;

import lombok.Data;

import java.util.List;

@Data
public class QuoteEvolutionResponse {
    private Integer quoteId;
    private List<BarResponse> elements;
}
