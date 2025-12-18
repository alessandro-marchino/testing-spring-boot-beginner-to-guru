package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        service.delete(new Visit());
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void testDeleteById() {
        service.deleteById(1L);
        verify(visitRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        when(visitRepository.findAll()).thenReturn(List.of(
            new Visit(1L),
            new Visit(2L)
        ));
        Set<Visit> result = service.findAll();
        assertThat(result)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
        verify(visitRepository).findAll();
    }

    @Test
    void testFindById() {
        Visit visit = new Visit(1L);
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        Visit result = service.findById(1L);
        assertThat(result)
            .isNotNull()
            .isEqualTo(visit);
        verify(visitRepository).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(visitRepository.findById(1L)).thenReturn(Optional.empty());
        Visit result = service.findById(1L);
        assertThat(result)
            .isNull();
        verify(visitRepository).findById(1L);
    }

    @Test
    void testSave() {
        Visit visit = new Visit(1L);
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit result = service.save(visit);
        assertThat(result)
            .isNotNull()
            .isEqualTo(visit);
        verify(visitRepository).save(visit);
    }
}
