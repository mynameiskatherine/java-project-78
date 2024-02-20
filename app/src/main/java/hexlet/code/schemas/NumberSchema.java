package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NumberSchema extends BaseSchema<Integer> {
    private Boolean presenceRequirement = false;
    private  Boolean positiveRequirement = false;
    private Map<String, Integer> rangeRequirement = new HashMap<>();
    public NumberSchema required() {
        if (!presenceRequirement) {
            presenceRequirement = true;
        }
        return this;
    }

    public NumberSchema positive() {
        if (!positiveRequirement) {
            positiveRequirement = true;
        }
        return this;
    }

    public NumberSchema range(Integer from, Integer to) {
        if (!rangeRequirement.isEmpty()) {
            rangeRequirement.clear();
        }
        if (from < to) {
            rangeRequirement.put("from", from);
            rangeRequirement.put("to", to);
        } else {
            rangeRequirement.put("from", to);
            rangeRequirement.put("to", from);
        }
        return this;
    }

    @Override
    public Boolean isValid(Integer objectToCheck) {
        if (Objects.isNull(objectToCheck)) {
            return !presenceRequirement;
        }
        if (positiveRequirement && objectToCheck <= 0) {
            return false;
        }
        if (!rangeRequirement.isEmpty()) {
            return objectToCheck >= rangeRequirement.get("from") && objectToCheck <= rangeRequirement.get("to");
        }
        return true;
    }
}
