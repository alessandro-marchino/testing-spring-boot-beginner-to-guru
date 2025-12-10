package guru.springframework.sfgpetclinic.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import guru.springframework.ModelTests;

class PersonTest implements ModelTests {
    @Test
    void groupedAssertions() {
        Person person = new Person(1L, "Joe", "Buck");
        assertAll("Test Props set",
            () -> assertEquals("Joe", person.getFirstName()),
            () -> assertEquals("Buck", person.getLastName()),
            () -> assertEquals(Long.valueOf(1L), person.getId())
        );
    }

    @Test
    void groupedAssertionsMsg() {
        Person person = new Person(1L, "Joe", "Buck");
        assertAll("Test Props set",
            () -> assertEquals("Joe", person.getFirstName(), "First Name failed"),
            () -> assertEquals("Buck", person.getLastName(), "Last Name failed"),
            () -> assertEquals(Long.valueOf(1L), person.getId(), "Id failed")
        );
    }
}
