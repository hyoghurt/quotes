package com.example.kameleoon.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.concurrent.TimeUnit;

public class StringToTimeUnitEnumConverter implements Converter<String, TimeUnit> {
    @Override
    public TimeUnit convert(String source) {
        return TimeUnit.valueOf(source.toUpperCase());
    }
}
