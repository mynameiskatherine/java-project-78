package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

    @Test
    void testStringRequiredOption() {
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

    @Test
    void testNumberRequiredOption() {
        Validator v = new Validator();
        var schema = v.number();
        List<Boolean> actual = new ArrayList<>();

        actual.add(schema.isValid(null)); // true
        actual.add(schema.positive().isValid(null)); // true
        actual.add(schema.range(1, 5).isValid(null)); // true
        actual.add(schema.isValid(0)); //false

        schema.required();
        actual.add(schema.isValid(null)); //false
        actual.add(schema.isValid(3)); //true
        actual.add(schema.isValid(33)); //false

        List<Boolean> expected = List.of(true, true, true, false, false, true, false);
        assertThat(actual).isEqualTo(expected);
    }

    void testNumberPositiveOption() {
        Validator v = new Validator();
        var schema = v.number();
        List<Boolean> actual = new ArrayList<>();

        schema.required();
        actual.add(schema.isValid(-342)); // true
        actual.add(schema.isValid(0)); // true
        actual.add(schema.positive().isValid(-2)); // false
        actual.add(schema.isValid(0)); //false
        actual.add(schema.isValid(10)); //true
        actual.add(schema.range(-11, -5).isValid(-6)); // false
        actual.add(schema.isValid(10)); // false

        List<Boolean> expected = List.of(true, true, false, false, true, false, false);
        assertThat(actual).isEqualTo(expected);
    }

    void testNumberRangeOption() {
        Validator v = new Validator();
        var schema = v.number();
        List<Boolean> actual = new ArrayList<>();

        schema.required();
        actual.add(schema.isValid(-342)); // true
        actual.add(schema.isValid(0)); // true
        actual.add(schema.isValid(100)); // true
        actual.add(schema.range(11, 45).isValid(14)); // true
        actual.add(schema.isValid(10)); // false
        actual.add(schema.range(-11, -5).isValid(-6)); // true
        actual.add(schema.isValid(10)); // false
        actual.add(schema.positive().isValid(-10)); // false
        actual.add(schema.isValid(20)); //false

        List<Boolean> expected = List.of(true, true, true, true, false, true, false, false, false);
        assertThat(actual).isEqualTo(expected);
    }
}
