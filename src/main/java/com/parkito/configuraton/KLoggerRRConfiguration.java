package com.parkito.configuraton;

import org.springframework.context.annotation.Bean;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class KLoggerRRConfiguration {
    @Bean
    public SpringConfiguration springConfiguration() {
        return new SpringConfiguration();
    }

}
