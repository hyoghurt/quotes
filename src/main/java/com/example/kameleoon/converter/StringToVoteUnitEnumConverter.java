package com.example.kameleoon.converter;

import com.example.kameleoon.model.enums.VoteUnit;
import org.springframework.core.convert.converter.Converter;

public class StringToVoteUnitEnumConverter implements Converter<String, VoteUnit> {
    @Override
    public VoteUnit convert(String source) {
        return VoteUnit.valueOf(source.toUpperCase());
    }
}
