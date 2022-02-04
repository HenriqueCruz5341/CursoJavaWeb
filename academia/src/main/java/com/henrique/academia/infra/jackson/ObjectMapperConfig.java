package com.henrique.academia.infra.jackson;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.henrique.academia.infra.jackson.deser.LocalDateDeserializer;
import com.henrique.academia.infra.jackson.deser.LocalDateTimeDeserializer;
import com.henrique.academia.infra.jackson.ser.LocalDateSerializer;
import com.henrique.academia.infra.jackson.ser.LocalDateTimeSerializer;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfig implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .serializerByType(LocalDate.class, new LocalDateSerializer())
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
                .deserializerByType(LocalDate.class, new LocalDateDeserializer())
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
    }
}