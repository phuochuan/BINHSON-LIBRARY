package com.library.binhson.borrowingservice.config.CustomSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {
    private final DateTimeFormatter formatter;

    public CustomDateTimeSerializer(String pattern) {
        this.formatter = DateTimeFormat.forPattern(pattern);
    }

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.print(dateTime));
    }
}

