package ru.siksmfp.kloggerrr.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.siksmfp.kloggerrr.controller.LoggingFilter;

import javax.servlet.Filter;

/**
 * @author Artem Karnov @date 27.03.2017.
 * artem.karnov@t-systems.com
 */
@Configuration
public class LoggingConfig {

    /**
     * If you want to customize default 1024 length size
     * you have to add parameter to registry
     * <p>
     * eobr:
     * logging:
     * filter:
     * maxByteBodyToLog: 1024
     */
    @Value("${eobr.logging.filter.maxByteBodyToLog:1024}")
    private int maxByteBodyToLog;

    @Bean
    public Filter loggingFilter() {
        return new LoggingFilter(maxByteBodyToLog);
    }
}
