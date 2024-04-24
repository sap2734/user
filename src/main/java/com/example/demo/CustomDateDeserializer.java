package com.example.demo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@JsonComponent
public class CustomDateDeserializer extends JsonDeserializer<Date> {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String dateStr = p.getText();
        try {
            if (dateStr.contains(":")) {
                return dateTimeFormat.parse(dateStr);
            } else {
                return dateFormat.parse(dateStr);
            }
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }

}
