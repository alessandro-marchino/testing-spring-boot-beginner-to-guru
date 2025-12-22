package org.springframework.samples.petclinic.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

@ExtendWith(MockitoExtension.class)
public class ClinicServiceImplTest {

    @Mock PetRepository petRepository;
    @Mock VetRepository vetRepository;
    @Mock OwnerRepository ownerRepository;
    @Mock VisitRepository visitRepository;
    @InjectMocks ClinicServiceImpl service;

    @Test
    void testFindOwnerById() {
        // Given
        Owner owner = new Owner();
        owner.setId(1);
        when(ownerRepository.findById(1)).thenReturn(owner);
        // When
        Owner found = service.findOwnerById(1);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1);
    }

    @Test
    void testFindOwnerByIdNotFound() {
        // Given
        when(ownerRepository.findById(2)).thenReturn(null);
        // When
        Owner found = service.findOwnerById(2);
        // Then
        assertThat(found).isNull();
    }

    @Test
    void testFindOwnerByLastName() {
        // Given
        when(ownerRepository.findByLastName(anyString())).thenReturn(List.of(new Owner(), new Owner()));
        // When
        Collection<Owner> found = service.findOwnerByLastName("TEST");
        // Then
        assertThat(found)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void testFindPetById() {
        // Given
        Pet pet = new Pet();
        pet.setId(1);
        when(petRepository.findById(1)).thenReturn(pet);
        // When
        Pet found = service.findPetById(1);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1);
    }

    @Test
    void testFindPetByIdNotFound() {
        // Given
        when(petRepository.findById(1)).thenReturn(null);
        // When
        Pet found = service.findPetById(1);

        // Then
        assertThat(found).isNull();
    }

    @Test
    void testFindPetTypes() {
        // Given
        when(petRepository.findPetTypes()).thenReturn(List.of(new PetType(), new PetType()));
        // When
        Collection<PetType> found = service.findPetTypes();
        // Then
        assertThat(found)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void testFindVets() {
        // Given
        when(vetRepository.findAll()).thenReturn(List.of(new Vet(), new Vet()));
        // When
        Collection<Vet> found = service.findVets();
        // Then
        assertThat(found)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void testFindVisitsByPetId() {
        // Given
        when(visitRepository.findByPetId(1)).thenReturn(List.of(new Visit(), new Visit()));
        // When
        Collection<Visit> found = service.findVisitsByPetId(1);
        // Then
        assertThat(found)
            .isNotNull()
            .isNotEmpty()
            .hasSize(2);
    }

    @Test
    void testSaveOwner() {
        // Given
        Owner owner = new Owner();
        // When
        service.saveOwner(owner);
        // Then
        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void testSavePet() {
        // Given
        Pet pet = new Pet();
        // When
        service.savePet(pet);
        // Then
        verify(petRepository).save(any(Pet.class));
    }

    @Test
    void testSaveVisit() {
        // Given
        Visit visit = new Visit();
        // When
        service.saveVisit(visit);
        // Then
        verify(visitRepository).save(visit);
    }
}
