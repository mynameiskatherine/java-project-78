package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NumberSchema extends BaseSchema<Integer> {
    private Boolean presenceRequirement = false;
    private  Boolean positiveRequirement = false;
    private List<Integer> rangeRequirement = new ArrayList<>();
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
            rangeRequirement.add(from);
            rangeRequirement.add(to);
        } else {
            rangeRequirement.add(to);
            rangeRequirement.add(from);
        }
        return this;
    }

    @Override
    public Boolean isValid(Integer objectToCheck) {
        if (Objects.equals(objectToCheck, null)) {
            return !presenceRequirement;
        }
        if (positiveRequirement && objectToCheck <= 0) {
            return false;
        }
        if (!rangeRequirement.isEmpty()) {
            return objectToCheck >= rangeRequirement.get(0) && objectToCheck <= rangeRequirement.get(1);
        }
        return true;
    }
}
