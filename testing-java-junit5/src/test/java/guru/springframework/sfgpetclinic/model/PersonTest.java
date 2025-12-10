package guru.springframework.sfgpetclinic.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import guru.springframework.ModelTests;
import guru.springframework.sfgpetclinic.RepeatedTests;

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

    @Nested
    class PersonRepeatedTest implements RepeatedTests {
        
        @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
        @DisplayName("My Repeated Test")
        void myRepeatedTest() {
            // TODO
        }
    
        @RepeatedTest(5)
        void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {
            System.out.println(testInfo.getDisplayName() + ": " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
        }
    }
}
