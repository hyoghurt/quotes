package com.example.kameleoon.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Table("QUOTES")
public class QuoteEntity {
    @Id
    private Integer id;
    private String quote;
    private Timestamp timestamp;
    private String username;
    @Column("VOTES_SUM")
    private Integer votesSum;
}
