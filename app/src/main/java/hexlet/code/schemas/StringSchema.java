package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringSchema extends BaseSchema<String> {
    private Boolean presenceRequirement = false;
    private List<Integer> minLengthRequirement = new ArrayList<>();
    private List<String> contentRequirement = new ArrayList<>();

    public StringSchema required() {
        if (!presenceRequirement) {
            presenceRequirement = true;
        }
        return this;
    }

    public StringSchema minLength(Integer minLength) {
        if (minLength >= 0) {
            minLengthRequirement.add(minLength);
        } else {
            throw new IllegalArgumentException("Number cannot be below zero");
        }
        return this;
    }

    public StringSchema contains(String content) {
        contentRequirement.add(content);
        return this;
    }

    @Override
    public Boolean isValid(String objectToCheck) {
        if (Objects.equals(objectToCheck, "") || Objects.equals(objectToCheck, null)) {
            return !presenceRequirement;
        }
        if (!minLengthRequirement.isEmpty()) {
            Integer minLength = minLengthRequirement.stream().min(Integer::compareTo).get();
            if (objectToCheck.length() < minLength) {
                return false;
            }
        }
        if (!contentRequirement.isEmpty()) {
            for (String substring : contentRequirement) {
                if (!objectToCheck.contains(substring)) {
                    return false;
                }
            }
        }
        return true;
    }
}
