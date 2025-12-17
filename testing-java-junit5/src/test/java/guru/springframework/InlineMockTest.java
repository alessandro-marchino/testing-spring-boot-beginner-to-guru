package guru.springframework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.jupiter.api.Test;

class InlineMockTest {

    @Test
    void testInlineMock() {
        @SuppressWarnings("rawtypes")
        Map mock = mock(Map.class);
        assertEquals(mock.size(), 0);
    }
}
