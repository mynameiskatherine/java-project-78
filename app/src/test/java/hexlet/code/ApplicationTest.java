package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

    @Test
    void testStringRequiredOption() {
        Validator v = new Validator();
        var schema = v.string();
        assertThat(schema.isValid("")).isTrue(); // true
        assertThat(schema.isValid(null)).isTrue(); // true
        assertThat(schema.contains("wh").isValid(null)).isTrue(); // true
        assertThat(schema.isValid("")).isTrue(); // true
        assertThat(schema.minLength(5).isValid(null)).isTrue(); // true
        assertThat(schema.isValid("")).isTrue(); // true

        schema.required();

        assertThat(schema.isValid(null)).isFalse(); // false
        assertThat(schema.isValid("")).isFalse(); // false
    }

    @Test
    void testStringContentOption() {
        Validator v = new Validator();
        var schema = v.string();
        schema.required();

        assertThat(schema.isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.isValid("hexlet")).isTrue(); // true

        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.contains("fox").isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.contains("cat").isValid("what does the fox say")).isFalse(); // false

        assertThat(schema.isValid("what does the cat say")).isTrue(); // true
    }

    @Test
    void testStringMinLengthOption() {
        Validator v = new Validator();
        var schema = v.string();
        schema.required();

        assertThat(schema.isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.isValid("hexlet")).isTrue(); // true

        assertThat(schema.minLength(7).isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.isValid("hexlet")).isFalse(); // false
        assertThat(schema.minLength(10).isValid("what does the fox say")).isTrue(); // true
        assertThat(schema.minLength(0).isValid("hexlet")).isTrue(); // true
        assertThat(schema.isValid("")).isFalse(); // false, as we have 'required' option
    }

    @Test
    void testNumberRequiredOption() {
        Validator v = new Validator();
        var schema = v.number();

        assertThat(schema.isValid(null)).isTrue(); // true
        assertThat(schema.positive().isValid(null)).isTrue(); // true
        assertThat(schema.range(1, 5).isValid(null)).isTrue(); // true
        assertThat(schema.isValid(0)).isFalse(); //false

        assertThat(schema.required().isValid(null)).isFalse(); //false
        assertThat(schema.isValid(3)).isTrue(); //true
        assertThat(schema.isValid(33)).isFalse(); //false
    }

    @Test
    void testNumberPositiveOption() {
        Validator v = new Validator();
        var schema = v.number();

        assertThat(schema.required().isValid(-342)).isTrue(); // true
        assertThat(schema.isValid(0)).isTrue(); // true
        assertThat(schema.positive().isValid(-2)).isFalse(); // false
        assertThat(schema.isValid(0)).isFalse(); //false
        assertThat(schema.isValid(10)).isTrue(); //true
        assertThat(schema.range(-11, -5).isValid(-6)).isFalse(); // false
        assertThat(schema.isValid(10)).isFalse(); // false
    }

    @Test
    void testNumberRangeOption() {
        Validator v = new Validator();
        var schema = v.number();

        schema.required();
        assertThat(schema.isValid(-342)).isTrue(); // true
        assertThat(schema.isValid(0)).isTrue(); // true
        assertThat(schema.isValid(100)).isTrue(); // true
        assertThat(schema.range(11, 45).isValid(14)).isTrue(); // true
        assertThat(schema.isValid(10)).isFalse(); // false
        assertThat(schema.range(-11, -5).isValid(-6)).isTrue(); // true
        assertThat(schema.isValid(10)).isFalse(); // false
        assertThat(schema.positive().isValid(-10)).isFalse(); // false
        assertThat(schema.isValid(20)).isFalse(); //false
    }

    @Test
    void testMapRequiredOption() {
        Validator v = new Validator();
        var schema = v.map();

        assertThat(schema.isValid(null)).isTrue(); // true
        assertThat(schema.isValid(new HashMap<>())).isTrue(); // true
        assertThat(schema.isValid(new TreeMap<>())).isTrue(); // true
        assertThat(schema.isValid(Map.of())).isTrue(); // true
        assertThat(schema.isValid(Map.of("key", "value"))).isTrue(); // true
        assertThat(schema.sizeof(1).isValid(null)).isTrue(); // true
        assertThat(schema.isValid(new TreeMap<>())).isFalse(); // false

        schema.required();
        assertThat(schema.isValid(null)).isFalse(); // false
        assertThat(schema.isValid(new HashMap<>())).isFalse(); // false
        assertThat(schema.isValid(new TreeMap<>())).isFalse(); // false
        assertThat(schema.isValid(Map.of("key", "value"))).isTrue(); // true
    }

    @Test
    void testMapSizeOption() {
        Validator v = new Validator();
        var schema = v.map();

        schema.required();
        assertThat(schema.isValid(new HashMap<>())).isTrue(); // true
        assertThat(schema.isValid(new TreeMap<>())).isTrue(); // true
        assertThat(schema.sizeof(1).isValid(new TreeMap<>())).isFalse(); // false
        assertThat(schema.isValid(Map.of("key", "value"))).isTrue(); // true

        assertThat(schema.sizeof(0).isValid(new TreeMap<>())).isTrue(); // true
        assertThat(schema.isValid(Map.of("key", "value"))).isFalse(); // false
    }

    @Test
    void testMapShapeOption() {
        Validator v = new Validator();

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", v.string().required());
        schemas1.put("age", v.number().positive());

        MapSchema schema = v.map().sizeof(2).shape(schemas1);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertThat(schema.isValid(human1)).isTrue(); // true

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Petya");
        human2.put("age", null);
        assertThat(schema.isValid(human2)).isTrue(); // true, as we do not have required for age

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", 10);
        assertThat(schema.isValid(human3)).isFalse(); //false as name cannot be ""

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Vasya");
        human4.put("age", -10);
        assertThat(schema.isValid(human4)).isFalse(); //false

        Map<String, BaseSchema<String>> schemas2 = new HashMap<>();
        schemas2.put("firstName", v.string().required());
        schemas2.put("lastName", v.string().required().minLength(2));

        schema = v.map().shape(schemas2);

        Map<String, String> human21 = new HashMap<>();
        human21.put("firstName", "John");
        human21.put("lastName", "Smith");
        assertThat(schema.isValid(human21)).isTrue(); // true

        Map<String, String> human22 = new HashMap<>();
        human22.put("firstName", "John");
        human22.put("lastName", null);
        assertThat(schema.isValid(human22)).isFalse(); // false

        Map<String, String> human23 = new HashMap<>();
        human23.put("firstName", "Anna");
        human23.put("lastName", "B");
        assertThat(schema.isValid(human23)).isFalse(); // false

        Map<String, Object> human24 = new HashMap<>();
        human4.put("name", "Vasya");
        human4.put("age", 10);
        assertThat(schema.isValid(human24)).isFalse(); //false
    }
}
