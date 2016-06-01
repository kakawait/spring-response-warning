package com.kakawait.spring.response.warning;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Thibaud Leprêtre
 */
@Configuration
@ConditionalOnClass(WebMvcConfigurerAdapter.class)
@ConditionalOnWebApplication
public class ResponseWarningAutoConfiguration {

    @Configuration
    protected static class ResponseWarningMvcConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new ResponseWarningHandlerInterceptor());
        }
    }
}
