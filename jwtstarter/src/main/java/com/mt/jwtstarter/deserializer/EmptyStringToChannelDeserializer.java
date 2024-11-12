package com.mt.jwtstarter.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mt.jwtstarter.enums.Channel;

import java.io.IOException;

public class EmptyStringToChannelDeserializer extends JsonDeserializer<Channel> {
    @Override
    public Channel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return value == null || value.isEmpty() ? null : Channel.valueOf(value);
    }
}
