package com.kakawait.spring.response.warning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.HttpHeaders.WARNING;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Thibaud Leprêtre
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ResponseWarningTest.ResponseWarningSampleApplication.class})
public class ResponseWarningTest {

    @SpringBootApplication
    public static class ResponseWarningSampleApplication {
        public static void main(String[] args) {
            SpringApplication.run(ResponseWarningSampleApplication.class, args);
        }

        @RestController
        public static class SampleController {

            @RequestMapping("/new")
            public String newApi() {
                return "hipster things";
            }

            @RequestMapping("/old")
            @ResponseWarning(
                    code = WarnCode.MISCELLANEOUS_PERSISTENT_WARNING,
                    text = "Deprecated API: use /new instead.")
            public String oldApi() {
                return "old things";
            }

            @RequestMapping("/exception")
            @ResponseWarning(
                    code = WarnCode.MISCELLANEOUS_PERSISTENT_WARNING,
                    text = "Deprecated API: use /new instead.")
            public String exceptionApi() {
                throw new RuntimeException("Oups!");
            }

            @RequestMapping("/date-time")
            @ResponseWarning(
                    code = WarnCode.MISCELLANEOUS_PERSISTENT_WARNING,
                    text = "Deprecated API: use /new instead.",
                    dateTime = true)
            public String dateTimeApi() {
                return "old things";
            }

            @ExceptionHandler
            @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            public void exceptionHandler(RuntimeException e) {

            }

        }
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void responseWarning_AnnotationPresents_ExpectedWarningHeader() throws Exception {
        mockMvc.perform(get("/old"))
               .andExpect(status().isOk())
               .andExpect(header().string(WARNING, "299 - \"Deprecated API: use /new instead.\""));
    }

    @Test
    public void responseWarning_AnnotationAbsents_WarningHeaderUnexpected() throws Exception {
        mockMvc.perform(get("/new"))
               .andExpect(status().isOk())
               .andExpect(header().doesNotExist(WARNING));
    }

    @Test
    public void responseWarning_ExceptionHandler_ExpectedWarningHeader() throws Exception {
        mockMvc.perform(get("/exception"))
               .andExpect(status().isInternalServerError())
               .andExpect(header().string(WARNING, "299 - \"Deprecated API: use /new instead.\""));
    }
    @Test
    public void responseWarning_AnnotationPresentsWithDateTime_ExpectedWarningWithDate() throws Exception {
        MvcResult result = mockMvc.perform(get("/date-time"))
                .andExpect(status().isOk())
                .andExpect(header().string(WARNING, matchesPattern("299 - \"Deprecated API: use /new instead.\" \".+?\"")))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertThat(response.containsHeader(DATE)).isTrue();
        assertThat(response.getHeader(WARNING)).contains(response.getHeader(DATE));
    }
}
