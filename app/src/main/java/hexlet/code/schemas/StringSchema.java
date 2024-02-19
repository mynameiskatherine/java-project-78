package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringSchema {
    private List<Integer> minLengthRequirement = new ArrayList<>();
    private List<String> contentRequirement = new ArrayList<>();
    private boolean presenceRequirement = false;
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

    public boolean isValid(String string) {
        if (Objects.equals(string, "") || Objects.equals(string, null)) {
            return !presenceRequirement;
        }
        if (!minLengthRequirement.isEmpty()) {
            Integer minLength = minLengthRequirement.stream().min(Integer::compareTo).get();
            if (string.length() < minLength) {
                return false;
            }
        }
        if (!contentRequirement.isEmpty()) {
            for (String substring : contentRequirement) {
                if (!string.contains(substring)) {
                    return false;
                }
            }
        }
        return true;
    }
}
