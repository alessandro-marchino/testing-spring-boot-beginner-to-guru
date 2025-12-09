package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;

@Disabled("Disabled until we learn Mocking")
class OwnerSDJpaServiceTest {
    OwnerSDJpaService service;

    @BeforeEach
    void setUp() {
        service = new OwnerSDJpaService(null, null, null);
    }

    @Test
    void testDelete() {
        
    }

    @Test
    void testDeleteById() {
        
    }

    @Test
    void testFindAll() {
        
    }

    @Test
    void testFindAllByLastNameLike() {
        
    }

    @Test
    void testFindById() {
        
    }

    @Test
    @Disabled
    void testFindByLastName() {
        Owner owner = service.findByLastName("Buck");
        assertNotNull(owner);
    }

    @Test
    void testSave() {
        
    }
}
