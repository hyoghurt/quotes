package com.example.kameleoon.repository;

import com.example.kameleoon.model.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {

    List<VoteEntity> findVoteEntitiesByQuoteIdAndTimestampBetween(Integer quoteId,
                                                                  Timestamp timestamp, Timestamp timestamp2);

    Optional<VoteEntity> findVoteEntityByQuoteIdAndUsername(Integer quoteId, String username);
}
