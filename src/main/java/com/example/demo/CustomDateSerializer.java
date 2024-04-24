package com.example.demo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class CustomDateSerializer extends JsonSerializer<Date>{

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (date.getHours() == 0 && date.getMinutes() == 0 && date.getSeconds() == 0) {
            gen.writeString(dateFormat.format(date));
        } else {
            gen.writeString(dateTimeFormat.format(date));
        }
    }
	
}
