package com.feignz.candidate_vacancy_api.config;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@JsonComponent
public class TrimStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JacksonException {
        String value = arg0.getValueAsString();
        return StringUtils.hasText(value) ? value.trim() : value;

    }

}
