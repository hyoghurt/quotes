package com.example.kameleoon.model.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuoteResponse {
    private Integer id;
    private String quote;
    private String username;
    private Timestamp timestamp;
    private Integer votesSum;
}
