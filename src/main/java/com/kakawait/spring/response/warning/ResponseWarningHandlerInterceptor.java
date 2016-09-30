package com.kakawait.spring.response.warning;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.HttpHeaders.WARNING;

/**
 * Intercepts incoming HTTP requests and set {@code Warning} header if {@link HandlerMethod} is annotated with
 * {@link ResponseWarning}.
 *
 * {@link #preHandle(HttpServletRequest, HttpServletResponse, Object)} is used in favor of
 * {@link #postHandle(HttpServletRequest, HttpServletResponse, Object, ModelAndView)} related to following note
 * <blockquote>
 *   Note that the postHandle method of HandlerInterceptor is not always ideally suited for use
 *   with @ResponseBody and ResponseEntity methods.
 *   In such cases an HttpMessageConverter writes to and commits the response before postHandle is called
 *   which makes it impossible to change the response, for example to add a header
 * </blockquote>
 * <cite>
 *   <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-handlermapping-interceptor">
 *     Intercepting requests with a HandlerInterceptor
 *   </a>
 * </cite>
 *
 * @author Thibaud Leprêtre
 */
public class ResponseWarningHandlerInterceptor extends HandlerInterceptorAdapter {

   private final DateTimeFormatter dateTimeFormatter;

    public ResponseWarningHandlerInterceptor() {
        this(HttpDateFormatter.RFC_1123);
    }

    public ResponseWarningHandlerInterceptor(HttpDateFormatter httpDateFormatter) {
        this.dateTimeFormatter = httpDateFormatter.getDateTimeFormatter().withZone(ZoneOffset.UTC);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            if (method.getMethod().isAnnotationPresent(ResponseWarning.class)) {
                ResponseWarning annotation = method.getMethod().getAnnotation(ResponseWarning.class);
                WarningHeader warning = new WarningHeader(annotation.code(), annotation.agent(), annotation.text());
                if (annotation.dateTime()) {
                    String dateTime = dateTimeFormatter.format(Instant.now());
                    response.setHeader(DATE, dateTime);
                    warning.setDateTime(dateTime);
                }
                response.addHeader(WARNING, warning.toString());
            }
        }
        return true;
    }
}
