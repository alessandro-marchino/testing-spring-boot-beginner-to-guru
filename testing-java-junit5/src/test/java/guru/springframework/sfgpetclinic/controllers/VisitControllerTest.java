package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    VisitService visitService;
    @Spy
    PetMapService petService;
    @InjectMocks
    VisitController controller;

    @Test
    void loadPetWithVisit() {
        // Given
        Map<String, Object> model = new HashMap<>();
        Pet pet12 = petService.save(new Pet(12L));
        petService.save(new Pet(3L));
        //given(petService.findById(12L)).willCallRealMethod();
        given(petService.findById(12L)).willReturn(pet12);
        // When
        Visit visit = controller.loadPetWithVisit(12L, model);
        // Then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(12L);

        then(petService).should(times(2)).save(any(Pet.class));
        then(petService).should().findById(anyLong());
    }
}
