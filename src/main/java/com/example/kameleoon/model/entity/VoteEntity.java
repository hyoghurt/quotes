package com.example.kameleoon.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Table("VOTES")
public class VoteEntity {
    @Id
    private Integer id;
    private Integer quoteId;
    private String username;
    private String vote;
    private Timestamp timestamp;
    private Integer currentVotesSum;
}
