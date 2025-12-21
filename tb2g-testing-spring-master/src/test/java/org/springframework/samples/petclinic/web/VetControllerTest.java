package org.springframework.samples.petclinic.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

@ExtendWith(MockitoExtension.class)
public class VetControllerTest {
    @Mock
    ClinicService clinicService;
    @InjectMocks
    VetController controller;

    @Test
    void showResourcesVetList() {
        // Given
        when(clinicService.findVets()).thenReturn(List.of(new Vet(), new Vet()));
        // When
        Vets result = controller.showResourcesVetList();
        // Then
        assertNotNull(result);
        assertEquals(2, result.getVetList().size());
    }

    @Test
    void showVetList() {
        // Given
        Map<String, Object> model = new HashMap<>();
        when(clinicService.findVets()).thenReturn(List.of(new Vet()));
        // When
        String result = controller.showVetList(model);
        // Then
        assertEquals("vets/vetList", result);
        assertNotNull(model.get("vets"));
        assertEquals(1, ((Vets)model.get("vets")).getVetList().size());
    }
}
