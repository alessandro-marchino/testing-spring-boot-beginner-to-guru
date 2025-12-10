package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

@Tag("repeated")
public interface RepeatedTests {

    @BeforeEach
    default void setUp(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println("[RepeatedTests::setUp]: " + testInfo.getDisplayName() + " - " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }
}
