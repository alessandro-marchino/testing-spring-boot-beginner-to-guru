package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;
    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteById() {
        // Given
        // When
        service.deleteById(1L);
        service.deleteById(1L);
        // Then
        then(specialtyRepository).should(times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        // Given
        // When
        service.deleteById(1L);
        service.deleteById(1L);
        // Then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        // Given
        // When
        service.deleteById(1L);
        service.deleteById(1L);
        // Then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        // Given
        // When
        service.deleteById(1L);
        service.deleteById(1L);
        // Then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void delete() {
        // Given
        // When
        service.delete(new Speciality());
        // Then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findById() {
        // Given
        Speciality speciality = new Speciality(1L, "Test");
        given(specialtyRepository.findById(1L))
            .willReturn(Optional.of(speciality));
        // When
        Speciality foundSpeciality = service.findById(1L);
        // Then
        assertThat(foundSpeciality)
            .isNotNull()
            .isEqualTo(speciality);
        then(specialtyRepository).should().findById(1L);
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByObject() {
        // Given
        // When
        Speciality speciality = new Speciality();
        service.delete(speciality);
        // Then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }
}
