package com.kakawait.spring.response.warning;

/**
 * A convenient class to represent a {@code Warning} header.
 *
 * <pre>
 *   Warning       = 1#warning-value
 *
 *   warning-value = warn-code SP warn-agent SP warn-text
 *                                         [ SP warn-date ]
 *
 *   warn-code  = 3DIGIT
 *   warn-agent = ( uri-host [ ":" port ] ) / pseudonym
 *                   ; the name or pseudonym of the server adding
 *                   ; the Warning header field, for use in debugging
 *                   ; a single "-" is recommended when agent unknown
 *   warn-text  = quoted-string
 *   warn-date  = DQUOTE HTTP-date DQUOTE
 * </pre>
 * <cite><a href="https://tools.ietf.org/html/rfc7234#section-5.5">RFC7234 - Warning</a></cite>
 *
 * @author Thibaud Leprêtre
 */
final class WarningHeader {

    static final String UNKNOWN_AGENT = "-";

    private final WarnCode code;

    private final String agent;

    private final String text;

    private String dateTime;

    WarningHeader(WarnCode code, String agent, String text) {
        this.code = code;
        this.agent = agent;
        this.text = text;
    }

    void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        String value = String.format("%d %s \"%s\"", code.getCode(), agent, text);
        if (dateTime != null) {
            value += " \"" + dateTime + "\"";
        }
        return value;
    }
}
