package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    @Test
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

    @Test
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

    @Test
    void testMapRequiredOption() {
        Validator v = new Validator();
        var schema = v.map();
        List<Boolean> actual = new ArrayList<>();

        actual.add(schema.isValid(null)); // true
        actual.add(schema.isValid(new HashMap<>())); // true
        actual.add(schema.isValid(new TreeMap<>())); // true
        actual.add(schema.isValid(Map.of())); // true
        actual.add(schema.isValid(Map.of("key", "value"))); // true
        actual.add(schema.sizeof(1).isValid(null)); // true
        actual.add(schema.isValid(new TreeMap<>())); // false

        schema.required();
        actual.add(schema.isValid(null)); // false
        actual.add(schema.isValid(new HashMap<>())); // false
        actual.add(schema.isValid(new TreeMap<>())); // false
        actual.add(schema.isValid(Map.of("key", "value"))); // true

        List<Boolean> expected = List.of(true, true, true, true, true, true, false, false, false, false, true);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testMapSizeOption() {
        Validator v = new Validator();
        var schema = v.map();
        List<Boolean> actual = new ArrayList<>();

        schema.required();
        actual.add(schema.isValid(new HashMap<>())); // true
        actual.add(schema.isValid(new TreeMap<>())); // true
        actual.add(schema.sizeof(1).isValid(new TreeMap<>())); // false
        actual.add(schema.isValid(Map.of("key", "value"))); // true

        actual.add(schema.sizeof(0).isValid(new TreeMap<>())); // true
        actual.add(schema.isValid(Map.of("key", "value"))); // false

        List<Boolean> expected = List.of(true, true, false, true, true, false);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testMapShapeOption() {
        Validator v = new Validator();

        List<Boolean> actual = new ArrayList<>();

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", v.string().required());
        schemas1.put("age", v.number().positive());

        MapSchema schema = v.map().sizeof(2).shape(schemas1);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        actual.add(schema.isValid(human1)); // true

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Petya");
        human2.put("age", null);
        actual.add(schema.isValid(human2)); // true, as we do not have required for age

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", 10);
        actual.add(schema.isValid(human3)); //false as name cannot be ""

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Vasya");
        human4.put("age", -10);
        actual.add(schema.isValid(human4)); //false

        Map<String, BaseSchema<String>> schemas2 = new HashMap<>();
        schemas2.put("firstName", v.string().required());
        schemas2.put("lastName", v.string().required().minLength(2));

        schema = v.map().shape(schemas2);

        Map<String, String> human21 = new HashMap<>();
        human21.put("firstName", "John");
        human21.put("lastName", "Smith");
        actual.add(schema.isValid(human21)); // true

        Map<String, String> human22 = new HashMap<>();
        human22.put("firstName", "John");
        human22.put("lastName", null);
        actual.add(schema.isValid(human22)); // false

        Map<String, String> human23 = new HashMap<>();
        human23.put("firstName", "Anna");
        human23.put("lastName", "B");
        actual.add(schema.isValid(human23)); // false

        List<Boolean> expected = List.of(true, true, false, false, true, false, false);
        assertThat(actual).isEqualTo(expected);
    }
}
