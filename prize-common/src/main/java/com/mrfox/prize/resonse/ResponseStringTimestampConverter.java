package com.mrfox.prize.resonse;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Date;

/**
 * @author z0228
 */
public class ResponseStringTimestampConverter extends StdConverter<String, Date> {
    @Override
    public Date convert(String value) {
        return new Date(Long.valueOf(value));
    }
}
