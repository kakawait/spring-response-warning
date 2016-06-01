package com.kakawait.spring.response.warning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Instant;

import static com.kakawait.spring.response.warning.WarningHeader.UNKNOWN_AGENT;

/**
 * Annotation to be used inside controller method to determine {@code Warning} header.
 *
 * Example to deprecated a REST API
 *
 * <pre>
 * <code>
 * &#064;RestController
 * public class MyController {
 *     &#064;RequestMapping("/old")
 *     &#064;ResponseWarning(
 *         httpWarning = WarnCode.MISCELLANEOUS_PERSISTENT_WARNING,
 *         message = "Deprecated API: use /new instead.")
 *     public void oldApi() {
 *         ...
 *     }
 *
 *     &#064;RequestMapping("/new")
 *     public void newApi() {
 *         ...
 *     }
 * }
 * </code>
 * </pre>
 *
 * Related to <a href="https://tools.ietf.org/html/rfc7234#section-7.2">RFC7234</a>
 *
 * @author Thibaud Leprêtre
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseWarning {

    /**
     * The name or pseudonym of the server adding the Warning header field, for use in debugging
     * a single "-" is recommended when agent unknown.
     */
    String agent() default UNKNOWN_AGENT;

    /**
     * Warn-text that describes the error, e.g., for logging.  It is advisory only, and its content does not affect
     * interpretation of the warn-code.
     */
    String text();

    WarnCode code();

    /**
     * If {@code dateTime} is true current instant datetime will be added to {@code Warning} header,
     * moreover a {@code Date} header will be generated with same instant datetime value.
     * <p>
     * Adding should be useful in such situation:
     * <blockquote>
     *   If a recipient that uses, evaluates, or displays Warning header fields receives a warn-date that is
     *   different from the Date value in the same message, the recipient MUST exclude the warning-value containing
     *   that warn-date before storing, forwarding, or using the message.
     *   This allows recipients to exclude warning-values that were improperly retained after a cache validation.
     *   If all of the warning-values are excluded, the recipient MUST exclude the Warning header field as well.
     * </blockquote>
     * <cite><a href="https://tools.ietf.org/html/rfc7234#section-5.5">RFC7234 - Warning</a></cite>
     *
     * @see Instant#now()
     */
    boolean dateTime() default false;

}
