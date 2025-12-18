package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    VisitSDJpaService service;

    @Test
    void testDelete() {
        // Given
        // When
        service.delete(new Visit());
        // Then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void testDeleteById() {
        // Given
        // When
        service.deleteById(1L);
        // Then
        then(visitRepository).should().deleteById(1L);
    }

    @Test
    void testFindAll() {
        // Given
        given(visitRepository.findAll()).willReturn(List.of(
            new Visit(1L),
            new Visit(2L)
        ));
        // When
        Set<Visit> result = service.findAll();
        // Then
        assertThat(result)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
        then(visitRepository).should().findAll();
    }

    @Test
    void testFindById() {
        // Given
        Visit visit = new Visit(1L);
        given(visitRepository.findById(1L)).willReturn(Optional.of(visit));
        // When
        Visit result = service.findById(1L);
        // Then
        assertThat(result)
            .isNotNull()
            .isEqualTo(visit);
        then(visitRepository).should().findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(visitRepository.findById(1L)).willReturn(Optional.empty());
        // When
        Visit result = service.findById(1L);
        // Then
        assertThat(result)
            .isNull();
        then(visitRepository).should().findById(1L);
    }

    @Test
    void testSave() {
        // Given
        Visit visit = new Visit(1L);
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        // When
        Visit result = service.save(visit);
        // Then
        assertThat(result)
            .isNotNull()
            .isEqualTo(visit);
        then(visitRepository).should().save(visit);
    }
}
