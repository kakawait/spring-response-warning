package com.kakawait.spring.response.warning;

import java.time.format.DateTimeFormatter;

/**
 * Recommended date time formatting for HTTP 1.1
 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3
 *
 * @author Thibaud Leprêtre
 */
public enum HttpDateFormatter {
    RFC_1123(DateTimeFormatter.RFC_1123_DATE_TIME),

    /**
     * @deprecated Prefer internet standard date format {@link #RFC_1123}
     */
    @Deprecated
    RFC_1036(DateTimeFormatter.ofPattern("EEEE, dd-MMM-yy HH:mm:ss zzz")),

    /**
     * @deprecated Prefer internet standard date format {@link #RFC_1123}
     */
    @Deprecated
    ASCTIME(DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyyy"));

    private final DateTimeFormatter formatter;

    HttpDateFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return formatter;
    }
}
