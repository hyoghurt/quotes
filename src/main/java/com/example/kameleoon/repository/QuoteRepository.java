package com.example.kameleoon.repository;

import com.example.kameleoon.model.entity.QuoteEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<QuoteEntity, Integer>, PagingAndSortingRepository<QuoteEntity, Integer> {

    void deleteQuoteEntityByIdAndUsername(Integer id, String username);

    Optional<QuoteEntity> findQuoteEntityByIdAndUsername(Integer id, String username);

    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("SELECT QUOTES.ID, QUOTES.VOTES_SUM, QUOTES.QUOTE, QUOTES.TIMESTAMP, QUOTES.USERNAME " +
            "FROM QUOTES WHERE QUOTES.ID = :id FOR UPDATE")
    Optional<QuoteEntity> findByIdLock(Integer id);
}
