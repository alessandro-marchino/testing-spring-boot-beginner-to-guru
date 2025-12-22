package org.springframework.samples.petclinic.sfg.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@TestPropertySource("classpath:yanni.properties")
@ActiveProfiles("externalized")
@SpringJUnitConfig(classes = HearingInterpreterPropertiesTest.TestConfig.class)
class HearingInterpreterPropertiesTest {

    @Configuration
    @Profile("externalized")
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig {
        // Empty
    }

    @Autowired HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("YaNNi", word);
    }
}
