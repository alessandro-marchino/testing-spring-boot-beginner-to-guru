package org.springframework.samples.petclinic.web;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = { "classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml" })
class OwnerControllerTest {
    @Autowired
    OwnerController ownerController;
    @Autowired
    ClinicService clinicService;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
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

    @Test
    void findByNameFindOne() throws Exception {
        // Given
        Owner owner = new Owner();
        owner.setId(1);
        when(clinicService.findOwnerByLastName("FindOne")).thenReturn(List.of(owner));
        // When
        mockMvc.perform(get("/owners")
                .param("lastName", "FindOne"))
        // Then
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/owners/1"));
        verify(clinicService).findOwnerByLastName("FindOne");
    }

    @Test
    void findByNameFindMany() throws Exception {
        // Given
        when(clinicService.findOwnerByLastName("Find multiple")).thenReturn(List.of(new Owner(), new Owner()));
        // When
        mockMvc.perform(get("/owners")
                .param("lastName", "Find multiple"))
        // Then
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("selections"))
            .andExpect(view().name("owners/ownersList"));
        verify(clinicService).findOwnerByLastName("Find multiple");
    }

    @Test
    void findByNameFindManyNullLastName() throws Exception {
        // Given
        when(clinicService.findOwnerByLastName(stringArgumentCaptor.capture())).thenReturn(List.of(new Owner(), new Owner()));
        // When
        mockMvc.perform(get("/owners"))
        // Then
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("selections"))
            .andExpect(view().name("owners/ownersList"));
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }

    @Test
    void newOwnerPostValid() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/owners/new")
            .param("firstName", "Jimmy")
            .param("lastName", "Buffer")
            .param("address", "123 Duval St.")
            .param("city", "Key West")
            .param("telephone", "1234567890")
        )
        // Then
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/owners/null"));
    }

    @Test
    void newOwnerPostNotValid() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/owners/new")
            .param("firstName", "Jimmy")
            .param("lastName", "Buffer")
            .param("city", "Key West")
        )
        // Then
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("owner"))
            .andExpect(model().attributeHasFieldErrors("owner", "address"))
            .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
            .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }
    
    @Test
    void updateOwnerPostValid() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
            .param("firstName", "Jimmy")
            .param("lastName", "Buffer")
            .param("address", "123 Duval St.")
            .param("city", "Key West")
            .param("telephone", "1234567890")
        )
        // Then
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/owners/22"));
    }

    @Test
    void updateOwnerPostNotValid() throws Exception {
        // Given
        // When
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
            .param("firstName", "Jimmy")
            .param("lastName", "Buffer")
            .param("city", "Key West")
        )
        // Then
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("owner"))
            .andExpect(model().attributeHasFieldErrors("owner", "address"))
            .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
            .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }
}
