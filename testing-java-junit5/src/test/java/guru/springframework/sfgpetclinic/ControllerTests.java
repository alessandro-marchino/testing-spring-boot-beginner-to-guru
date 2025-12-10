package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@Tag("Controller")
@TestInstance(Lifecycle.PER_CLASS)
public interface ControllerTests {
    @BeforeAll
    default void beforeAll() {
        System.out.println("Let's do something here");
    }

}
