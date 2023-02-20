package com.example.kameleoon.service;


import com.example.kameleoon.converter.ModelMapper;
import com.example.kameleoon.exception.QuoteNotFoundException;
import com.example.kameleoon.exception.VoteForbiddenException;
import com.example.kameleoon.model.entity.QuoteEntity;
import com.example.kameleoon.model.entity.VoteEntity;
import com.example.kameleoon.model.enums.VoteUnit;
import com.example.kameleoon.model.request.QuoteRequest;
import com.example.kameleoon.model.response.BarResponse;
import com.example.kameleoon.model.response.QuoteEvolutionResponse;
import com.example.kameleoon.model.response.QuoteResponse;
import com.example.kameleoon.model.response.QuotesResponse;
import com.example.kameleoon.repository.QuoteRepository;
import com.example.kameleoon.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final VoteRepository voteRepository;
    private final ModelMapper modelMapper;

    @Override
    public Integer createQuote(QuoteRequest quoteRequest, String username) {
        QuoteEntity entity = modelMapper.toEntity(quoteRequest);
        entity.setUsername(username);
        return quoteRepository.save(entity).getId();
    }

    @Override
    public QuoteResponse getQuoteById(Integer quoteId) {
        QuoteEntity entity = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new QuoteNotFoundException("not found " + quoteId));
        return modelMapper.toDto(entity);
    }

    @Override
    public void updateQuoteById(Integer quoteId, QuoteRequest request, String username) {
        QuoteEntity quoteEntity = quoteRepository.findQuoteEntityByIdAndUsername(quoteId, username)
                .orElseThrow(() -> new QuoteNotFoundException("not found " + quoteId));
        quoteEntity.setQuote(request.getQuote());
        quoteRepository.save(quoteEntity);
    }

    @Override
    public void deleteQuoteById(Integer quoteId, String username) {
        quoteRepository.deleteQuoteEntityByIdAndUsername(quoteId, username);
    }

    @Override
    public QuotesResponse getTopQuotes() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("votesSum").descending());
        Page<QuoteEntity> entityPage = quoteRepository.findAll(pageable);
        return modelMapper.toDto(entityPage);
    }

    @Override
    public QuoteEvolutionResponse getQuoteEvolution(Integer quoteId, Integer page, Long period, TimeUnit timeunit) {
        long currentTimeMillis = System.currentTimeMillis();
        long periodMillis = timeunit.toMillis(period);
        long endTime = currentTimeMillis - (periodMillis * page);
        long startTime = endTime - periodMillis;

        List<VoteEntity> VoteEntityList = voteRepository.findVoteEntitiesByQuoteIdAndTimestampBetween
                (quoteId, new Timestamp(startTime), new Timestamp(endTime));

        List<BarResponse> elements = VoteEntityList.stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());

        QuoteEvolutionResponse response = new QuoteEvolutionResponse();
        response.setQuoteId(quoteId);
        response.setElements(elements);

        return response;
    }

    @Override
    @Transactional
    public void vote(Integer quoteId, VoteUnit value, String username) {
        QuoteEntity quoteEntity = quoteRepository.findByIdLock(quoteId)
                .orElseThrow(() -> new QuoteNotFoundException("not found " + quoteId));

        if (quoteEntity.getUsername().equals(username)) {
            throw new VoteForbiddenException("your quote forbidden up or down");
        }

        Integer votesSum = quoteEntity.getVotesSum();
        String voteUnitResult = value.name();
        VoteEntity newVote;

        Optional<VoteEntity> voteEntityOptional = voteRepository.findVoteEntityByQuoteIdAndUsername(quoteId, username);

        if (voteEntityOptional.isPresent()) {

            newVote = voteEntityOptional.get();
            String voteUnitEntity = newVote.getVote();

            if (voteUnitEntity == null) {

                votesSum = (value == VoteUnit.UP) ? votesSum + 1 : votesSum - 1;

            } else if (VoteUnit.valueOf(voteUnitEntity.toUpperCase()) == value) {

                votesSum = (value == VoteUnit.DOWN) ? votesSum + 1 : votesSum - 1;
                voteUnitResult = null;

            } else if (VoteUnit.valueOf(voteUnitEntity.toUpperCase()) != value) {

                votesSum = (value == VoteUnit.UP) ? votesSum + 2 : votesSum - 2;
            }

        } else {

            newVote = createNewVote(quoteId, username);
            votesSum = (value == VoteUnit.UP) ? votesSum + 1 : votesSum - 1;
        }

        newVote.setVote(voteUnitResult);
        newVote.setCurrentVotesSum(votesSum);
        voteRepository.save(newVote);

        quoteEntity.setVotesSum(votesSum);
        quoteRepository.save(quoteEntity);
    }

    private VoteEntity createNewVote(Integer quoteId, String username) {
        VoteEntity entity = new VoteEntity();
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));
        entity.setUsername(username);
        entity.setQuoteId(quoteId);
        return entity;
    }
}
