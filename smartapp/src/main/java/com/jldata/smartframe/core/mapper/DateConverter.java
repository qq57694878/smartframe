package com.jldata.smartframe.core.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateConverter implements Converter<String, Date> {
    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);
    private List<String> patterns = new ArrayList<String>();

    public DateConverter() {
        patterns.add("yyyy-MM-dd'T'HH:mm:ss");
        patterns.add("yyyy-MM-dd HH:mm");
        patterns.add("yyyy-MM-dd");
    }

    public Date convert(String text) {
        if (text == null) {
            return null;
        }

        for (String pattern : patterns) {
            Date date = tryConvert(text, pattern);

            if (date != null) {
                return date;
            }
        }

        return null;
    }

    public Date tryConvert(String text, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);

        try {
            return dateFormat.parse(text);
        } catch (ParseException ex) {
            logger.debug(ex.getMessage(), ex);
        }

        return null;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }
}
