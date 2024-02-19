package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

    @Test
    void testStringRequiredOption1() {
        Validator v = new Validator();
        var schema = v.string();
        List<Boolean> actual = new ArrayList<>();
        actual.add(schema.isValid("")); // true
        actual.add(schema.isValid(null)); // true
        actual.add(schema.contains("wh").isValid(null)); // true
        actual.add(schema.isValid("")); // true
        actual.add(schema.minLength(5).isValid(null)); // true
        actual.add(schema.isValid("")); // true

        schema.required();

        actual.add(schema.isValid(null)); // false
        actual.add(schema.isValid("")); // false

        List<Boolean> expected = List.of(true, true, true, true, true, true, false, false);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testStringContentOption() {
        Validator v = new Validator();
        var schema = v.string();
        List<Boolean> actual = new ArrayList<>();
        schema.required();

        actual.add(schema.isValid("what does the fox say")); // true
        actual.add(schema.isValid("hexlet")); // true

        actual.add(schema.contains("what").isValid("what does the fox say")); // true
        actual.add(schema.contains("fox").isValid("what does the fox say")); // true
        actual.add(schema.contains("cat").isValid("what does the fox say")); // false

        actual.add(schema.isValid("what does the fox say")); // false

        List<Boolean> expected = List.of(true, true, true, true, false, false);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testStringMinLengthOption() {
        Validator v = new Validator();
        var schema = v.string();
        List<Boolean> actual = new ArrayList<>();
        schema.required();

        actual.add(schema.isValid("what does the fox say")); // true
        actual.add(schema.isValid("hexlet")); // true

        actual.add(schema.minLength(7).isValid("what does the fox say")); // true
        actual.add(schema.isValid("hexlet")); // false
        actual.add(schema.minLength(10).isValid("what does the fox say")); // true
        actual.add(schema.minLength(0).isValid("hexlet")); // true
        actual.add(schema.isValid("")); // false, as we have 'required' option

        List<Boolean> expected = List.of(true, true, true, false, true, true, false);
        assertThat(actual).isEqualTo(expected);
    }
}
