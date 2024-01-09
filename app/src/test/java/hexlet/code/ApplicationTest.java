package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {
    @Test
    void testEmptyDifferStylish() {
        String expected = "";
        String actual = "";
        assertThat(actual).isEqualTo(expected);
    }
}
