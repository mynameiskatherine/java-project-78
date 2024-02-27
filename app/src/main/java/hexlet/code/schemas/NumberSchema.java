package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        if (!super.presenceRequirement) {
            super.presenceRequirement = true;
        }
        return this;
    }
    public NumberSchema positive() {
        addCheck(CheckName.INT_POSITIVE, o -> o > 0);
        return this;
    }

    public NumberSchema range(Integer from, Integer to) {
        if (from <= to) {
            addCheck(CheckName.INT_RANGE, o -> (o >= from && o <= to));
        } else {
            addCheck(CheckName.INT_RANGE, o -> (o >= to && o <= from));
        }
        return this;
    }
}
