package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        if (!super.presenceRequirement) {
            super.presenceRequirement = true;
        }
        return this;
    }

    public StringSchema minLength(Integer minLength) {
        if (minLength >= 0) {
            addCheck(CheckName.STR_MINLENGTH, o ->
                    o.length() >= minLength);
        } else {
            throw new IllegalArgumentException("Number cannot be below zero");
        }
        return this;
    }

    public StringSchema contains(String content) {
        addCheck(CheckName.STR_CONTAINS, o -> o.contains(content));
        return this;
    }
}
