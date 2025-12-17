package guru.springframework;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AnnotationMockTest {

    @Mock
    Map<String, Object> mock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMock() {
        mock.put("ketValue", "foo");
    }
}
