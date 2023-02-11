package com.example.kameleoon.config;

import com.example.kameleoon.converter.StringToTimeUnitEnumConverter;
import com.example.kameleoon.converter.StringToVoteUnitEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToVoteUnitEnumConverter());
        registry.addConverter(new StringToTimeUnitEnumConverter());
    }
}
