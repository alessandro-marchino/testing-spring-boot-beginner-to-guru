package guru.springframework;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GreatingTest {
    private Greeting greeting;

    @BeforeAll
    static void beforeClass() {
        System.out.println("Before - I am only called once!");
    }
    @AfterAll
    static void afterClass() {
        System.out.println("After - I am only called once!");
    }

    @BeforeEach
    void setUp() {
        greeting = new Greeting();
        System.out.println("In before each...");
    }
    @AfterEach
    void tearDown() {
        System.out.println("In after each...");
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void helloWorld1() {
        System.out.println(greeting.helloWorld("John"));
    }
}