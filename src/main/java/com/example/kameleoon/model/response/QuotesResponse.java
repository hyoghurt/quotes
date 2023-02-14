package com.example.kameleoon.model.response;

import lombok.Data;

import java.util.List;

@Data
public class QuotesResponse {
    private Integer totalElements;
    private List<QuoteResponse> elements;
}
