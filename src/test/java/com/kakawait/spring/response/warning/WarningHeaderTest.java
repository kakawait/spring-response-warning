package com.kakawait.spring.response.warning;

import org.junit.Test;

import java.time.Instant;
import java.util.regex.Pattern;

import static com.kakawait.spring.response.warning.WarnCode.DISCONNECTED_OPERATION;
import static com.kakawait.spring.response.warning.WarningHeader.UNKNOWN_AGENT;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thibaud Leprêtre
 */
public class WarningHeaderTest {

    @Test
    public void warningHeader_WithoutDateTime_ExpectedNoDateTimeOnValue() {
        WarningHeader warning = new WarningHeader(DISCONNECTED_OPERATION, UNKNOWN_AGENT, "network down");

        assertThat(warning.toString())
                .containsPattern(Pattern.compile("[0-9]{3} .+? \".+\""))
                .contains(String.valueOf(DISCONNECTED_OPERATION.getCode()), UNKNOWN_AGENT, "network down");
    }

    @Test
    public void warningHeader_WithDateTime_ExpectedDateTimeOnValue() {
        String dateTime = HttpDateFormatter.RFC_1123.getDateTimeFormatter().withZone(UTC).format(Instant.now());
        WarningHeader warning = new WarningHeader(DISCONNECTED_OPERATION, UNKNOWN_AGENT, "network down");
        warning.setDateTime(dateTime);

        assertThat(warning.toString())
                .containsPattern(Pattern.compile("[0-9]{3} .+? \".+\" \".+?\""))
                .contains(String.valueOf(DISCONNECTED_OPERATION.getCode()), UNKNOWN_AGENT, "network down", dateTime);
    }
}
