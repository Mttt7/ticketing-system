package com.mt.jwtstarter.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mt.jwtstarter.enums.Priority;

import java.io.IOException;

public class EmptyStringToPriorityDeserializer extends JsonDeserializer<Priority> {
    @Override
    public Priority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return value == null || value.isEmpty() ? null : Priority.valueOf(value);
    }
}
