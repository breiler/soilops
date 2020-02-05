package com.tietoevry.soilops.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


public class LocalDateTimeToStringAttributeConverter implements AttributeConverter<LocalDateTime, String> {
 
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").appendFraction(ChronoField.NANO_OF_SECOND, 6, 6, true).toFormatter();
 
    @Override
    public String convertToDatabaseColumn(LocalDateTime locDate) {
        return (locDate == null ? null : locDate.format(formatter));
    }
 
    @Override
    public LocalDateTime convertToEntityAttribute(String sqlDate) {
        return (sqlDate == null ? null : LocalDateTime.parse(sqlDate, formatter));
    }
}