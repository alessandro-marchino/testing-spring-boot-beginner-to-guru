package guru.springframework;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JUnitExtensionMockTest {

    @Mock
    Map<String, Object> mock;

    @Test
    void testMock() {
        mock.put("ketValue", "foo");
    }
}
