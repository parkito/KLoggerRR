package com.parkito.kloggerrr.configuraton;

import org.springframework.context.annotation.Bean;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class BeanRequestLoggingConfiguration {
    @Bean
    public SpringRequestLoggingConfiguration springConfiguration() {
        return new SpringRequestLoggingConfiguration();
    }
}
