package com.mrfox.prize.resonse;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author z0228
 */
public class ResponseTimestampStringConverter extends StdConverter<Date, String> {
    static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZZZZZ", Locale.ENGLISH);

    @Override
    public String convert(Date value) {
        return "" + (null != value ? value : new Date()).getTime();
    }
}
