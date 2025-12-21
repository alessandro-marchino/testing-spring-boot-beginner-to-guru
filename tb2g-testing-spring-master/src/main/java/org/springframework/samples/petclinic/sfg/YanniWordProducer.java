package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Component;

@Component
public class YanniWordProducer implements WordProducer {

    @Override
    public String getWord() {
        return "Yanni";
    }

}
