[![Actions Status](https://github.com/mynameiskatherine/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/mynameiskatherine/java-project-78/actions)
[![Gradle build status](https://github.com/mynameiskatherine/java-project-78/actions/workflows/my-build-checks.yml/badge.svg)](https://github.com/mynameiskatherine/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/8661525bdb41fd42dec1/maintainability)](https://codeclimate.com/github/mynameiskatherine/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/8661525bdb41fd42dec1/test_coverage)](https://codeclimate.com/github/mynameiskatherine/java-project-78/test_coverage)

# Validator

## Description
Data validator is a Java library that can be used to check the correctness of data like Strings, Integers and Maps. There are many similar libraries in every language, since almost all programs work with external data that needs to be checked for correctness.

## Functionality
### General
To start the work with the validator, we need to create a new Validator object and a new schema object. There are three schemas available: `string()`, `number()` and `map()`.
```sh
var v = new Validator();
var schema = v.string();
```
Each schema has its own set of options available. We apply them to our schema object, chain calls are available. All the options are cumulative and applicable together. To validate the data, we just call isValid method with the data and receive a true/false validation answer.
```sh
schema.required().contains("cat").minLength(6).isValid("catty");   //false
schema.isValid("kittycat");   //true
```
Note: If a reset of applied options is needed - just create a new schema object.

### Strings
The following validation options are available for string schema:\
`required()` - requires non-empty string and non-null value. Until this option is applied, empty string and null value will be valid, even if the other options are set.\
`minLength(int)` - specifies minimal string length. Option accumulates the parameters from each call and applies the smallest one during the validation. The minimum length cannot be below zero.\
`contains("substring")` - specifies substring, which should be presented in the data for validation. Option accumulates the substrings from each call and checks all of them during the validation.

```sh
var v = new Validator();
var schema = v.string();

schema.isValid("");   //true
schema.isValid(null);   //true
schema.contains("cat").minLength(5).isValid(null);   //true, as required option is not applied
schema.isValid("");   //true, as required option is not applied
schema.required();
schema.isValid(null);   //false
schema.isValid("");   //false
schema.isValid("cat");   //false, as minimal length should be 5 symbols
schema.isValid("cat was here");   //true
schema.contains("dog").isValid("dog was here");   //false, as contains option is cumulative itself
```

### Numbers
The following validation options are available for number schema:\
`required()` - requires non-null value. Until this option is applied, null value will be valid, even if the other options are set.\
`positive()` - requires value to be above zero (zero is not positive).\
`range(int, int)` - requires value to be in specified range, including both borders. The option is not cumulative: new range call will substitute old range requirement.\
Note: number schema is applicable to Integers only.

```sh
var v = new Validator();
var schema = v.number();

schema.isValid(null);   //true
schema.positive().range(-10, 15).isValid(null);   //true, as required option is not applied
schema.isValid(0);   //false, as 0 is not positive
schema.required();
schema.isValid(null);   //false
schema.isValid(3);   //true
schema.isValid(-3);   //false, as required to be positive
```

### Maps
The following validation options are available for map schema:\
`required()` - requires non-null value. Until this option is applied, null value will be valid, even if the other options are set.\
`sizeof(int)` - specifies exact size of the validated map object. Option is not cumulative: new sizeof call will substitute old size requirement.\
`shape(Map<>)` - specifies patterns for Map values. Schema should be specified for each value. The option is not cumulative: new shape call will substitute old shape requirement.

```sh
var v = new Validator();
var schema = v.map();

schema.isValid(null);   // true
schema.isValid(new HashMap<>());   // true
schema.required();
schema.isValid(null);   // false
schema.isValid(new HashMap<>());   // true
var data = new HashMap<String, String>();
data.put("key1", "value1");
schema.isValid(data);   // true
schema.sizeof(2).isValid(data);   // false
data.put("key2", "value2");
schema.isValid(data);   // true



Map<String, BaseSchema> mapShape = new HashMap<>();
mapShape.put("name", v.string().required());
mapShape.put("age", v.number().positive());

var schema1 = v.map().sizeof(2).shape(mapShape);

Map<String, Object> human1 = new HashMap<>();
human1.put("name", "Kolya");
human1.put("age", 100);
schema.isValid(human1); // true

Map<String, Object> human2 = new HashMap<>();
human2.put("name", "Petya");
human2.put("age", null);
schema.isValid(human2); // true

Map<String, Object> human2 = new HashMap<>();
human2.put("name", "");
human2.put("age", 50);
schema.isValid(human2); // false

```


## Demo
[![asciicast](https://asciinema.org/a/Y7rpdZMH8IBsFmNOd3FjL6pF1.svg)](https://asciinema.org/a/Y7rpdZMH8IBsFmNOd3FjL6pF1)
