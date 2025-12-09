package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@DisplayName("Owner Map Service Test -")
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    PetTypeService petTypeService;
    PetService petService;

    @BeforeEach
    void setUp() {
        petTypeService = new PetTypeMapService();
        petService = new PetMapService();
        ownerMapService = new OwnerMapService(petTypeService, petService);
    }

    @Test
    @DisplayName("Verify Zero Owners")
    void ownersAreZero() {
        int ownerCount = ownerMapService.findAll().size();
        assertEquals(0, ownerCount);
    }

    @Nested
    @DisplayName("Pet Type -")
    class TestCreatePetTypes {
        @BeforeEach
        void setUp() {
            petTypeService.save(new PetType(1L, "Dog"));
            petTypeService.save(new PetType(2L, "Cat"));
        }
        @Test
        void testPetCount() {
            int petCountType = petTypeService.findAll().size();
            assertEquals(2, petCountType);
        }

        @Nested
        @DisplayName("Save Owners Test -")
        class SaveOwnersTest {
            @BeforeEach
            void setUp() {
                ownerMapService.save(new Owner(1L, "Before", "Each"));
            }
            @Test
            void saveOwner() {
                Owner owner = new Owner(null, "Joe", "Buck");
                Owner savedOwner = ownerMapService.save(owner);
                assertNotNull(savedOwner);
                assertEquals(Long.valueOf(2L), savedOwner.getId());
            }

            @Nested
            @DisplayName("Find Owners Tests -")
            class FindOwnersTests {
                @Test
                void findOwnerFound() {
                    Owner owner = ownerMapService.findById(1L);
                    assertNotNull(owner);
                }

                @Test
                void findOwnerNotFound() {
                    Owner owner = ownerMapService.findById(2L);
                    assertNull(owner);
                }
            }
        }
    }

    @Test
    @DisplayName("Verify Still Zero Owners")
    void ownersAreStillZero() {
        int ownerCount = ownerMapService.findAll().size();
        assertEquals(0, ownerCount);
    }
}
