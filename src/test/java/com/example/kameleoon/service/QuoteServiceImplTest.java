package com.example.kameleoon.service;

import com.example.kameleoon.converter.ModelMapper;
import com.example.kameleoon.exception.QuoteNotFoundException;
import com.example.kameleoon.exception.VoteForbiddenException;
import com.example.kameleoon.model.entity.QuoteEntity;
import com.example.kameleoon.model.enums.VoteUnit;
import com.example.kameleoon.repository.QuoteRepository;
import com.example.kameleoon.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QuoteServiceImplTest {

    private QuoteService quoteService;
    private QuoteRepository quoteRepository;

    @BeforeEach
    void setUp() {
        quoteRepository = Mockito.mock(QuoteRepository.class);
        VoteRepository voteRepository = Mockito.mock(VoteRepository.class);
        quoteService = new QuoteServiceImpl(quoteRepository, voteRepository, new ModelMapper());
    }

    @Test
    void voteThenQuoteNotFoundException() {
        Integer id = 1;
        String username = "testuser";

        Mockito.when(quoteRepository.findByIdLock(id)).thenReturn(Optional.empty());

        assertThrows(QuoteNotFoundException.class, () -> quoteService.vote(id, VoteUnit.UP, username));
    }

    @Test
    void voteThenVoteForbiddenException() {
        Integer id = 1;
        String username = "testuser";
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setUsername(username);

        Mockito.when(quoteRepository.findByIdLock(id)).thenReturn(Optional.of(quoteEntity));

        assertThrows(VoteForbiddenException.class, () -> quoteService.vote(id, VoteUnit.UP, username));
    }
}
