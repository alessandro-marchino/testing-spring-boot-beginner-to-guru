package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("base-test")
public class YanniConfig {

    @Bean
    YanniWordProducer yanniWordProducer() {
        System.out.println("wawa");
        return new YanniWordProducer();
    }
}
