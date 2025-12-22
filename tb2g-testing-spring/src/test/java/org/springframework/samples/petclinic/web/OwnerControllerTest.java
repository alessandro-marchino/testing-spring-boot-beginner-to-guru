package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitWebConfig(locations = { "classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml" })
class OwnerControllerTest {
    @Autowired
    OwnerController ownerController;
    @Autowired
    ClinicService clinicService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void tempTest() {
        // Context loads
    }

    @Test
    void initCreationForm() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/owners/new"))
        // Then
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("owner"))
            .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void findByNameNotFound() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/owners")
                .param("lastName", "Don't find me"))
        // Then
            .andExpect(status().isOk())
            .andExpect(view().name("owners/findOwners"));
    }
}
