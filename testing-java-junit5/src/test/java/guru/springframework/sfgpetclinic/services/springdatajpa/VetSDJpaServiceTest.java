package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.repositories.VetRepository;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {
    @Mock
    VetRepository vetRepository;
    @InjectMocks
    VetSDJpaService service;

    @Test
    void testDeleteById() {
        service.deleteById(1L);
        verify(vetRepository).deleteById(1L);
    }
}
