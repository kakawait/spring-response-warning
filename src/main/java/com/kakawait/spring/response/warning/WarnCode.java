package com.kakawait.spring.response.warning;

/**
 * Warn codes define by <a href="http://www.iana.org/assignments/http-warn-codes/http-warn-codes.xhtml">iana</a>
 *
 * @author Thibaud Leprêtre
 */
public enum WarnCode {
    /**
     * <blockquote>
     *   A cache SHOULD generate this whenever the sent response is stale
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.1">
     *     RFC7234 - Warning: 110 - "Response is Stale"
     *   </a>
     * </cite>
     */
    RESPONSE_IS_STALE(110),

    /**
     * <blockquote>
     *   A cache SHOULD generate this when sending a stale response because an attempt to validate the response failed,
     *   due to an inability to reach the server.
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.2">
     *     RFC7234 - Warning: 111 - "Revalidation Failed"
     *   </a>
     * </cite>
     */
    REVALIDATION_FAILED(111),

    /**
     * <blockquote>
     *   A cache SHOULD generate this if it is intentionally disconnected from the rest of the network
     *   for a period of time.
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.3">
     *     RFC7234 - Warning: 112 - "Disconnected Operation"
     *   </a>
     * </cite>
     */
    DISCONNECTED_OPERATION(112),

    /**
     * <blockquote>
     *   A cache SHOULD generate this if it heuristically chose a freshness lifetime greater than 24 hours
     *   and the response's age is greater than 24 hours.
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.4">
     *     RFC7234 - Warning: 113 - "Heuristic Expiration"
     *   </a>
     * </cite>
     */
    HEURISTIC_EXPIRATION(113),

    /**
     * <blockquote>
     *   The warning text can include arbitrary information to be presented to a human user or logged.
     *   A system receiving this warning MUST NOT take any automated action, besides presenting the warning to the user.
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.5">
     *     RFC7234 - Warning: 199 - "Miscellaneous Warning"
     *   </a>
     * </cite>
     */
    MISCELLANEOUS_WARNING(199),

    /**
     * <blockquote>
     *   This Warning code MUST be added by a proxy if it applies any transformation to the representation,
     *   such as changing the content-coding, media-type, or modifying the representation data,
     *   unless this Warning code already appears in the response.
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.6">
     *     RFC7234 - Warning: 214 - "Transformation Applied"
     *   </a>
     * </cite>
     */
    TRANSFORMATION_APPLIED(214),

    /**
     * <blockquote>
     *   The warning text can include arbitrary information to be presented to a human user or logged.
     *   A system receiving this warning MUST NOT take any automated action.
     * </blockquote>
     * <cite>
     *   <a href="https://tools.ietf.org/html/rfc7234#section-5.5.7">
     *     RFC7234 - Warning: 299 - "Miscellaneous Persistent Warning"
     *   </a>
     * </cite>
     */
    MISCELLANEOUS_PERSISTENT_WARNING(299);

    private final int code;

    WarnCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public int getFamily() {
        return code / 100;
    }
}
