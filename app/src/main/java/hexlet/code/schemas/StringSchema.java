package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public final class StringSchema extends BaseSchema<String, StringSchema> {
    private List<Integer> minLengthRequirement = new ArrayList<>();
    private List<String> contentRequirement = new ArrayList<>();

    public StringSchema minLength(Integer minLength) {
        if (minLength >= 0) {
            minLengthRequirement.add(minLength);
            addCheck(CheckName.STR_MINLENGTH, o ->
                    o.length() >= minLengthRequirement.stream().min(Integer::compare).get());
        } else {
            throw new IllegalArgumentException("Number cannot be below zero");
        }
        return this;
    }

    public StringSchema contains(String content) {
        contentRequirement.add(content);
        addCheck(CheckName.STR_CONTAINS, o -> {
            for (String substring : contentRequirement) {
                if (!o.contains(substring)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
