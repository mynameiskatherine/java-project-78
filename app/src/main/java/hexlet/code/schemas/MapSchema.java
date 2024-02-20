package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private Boolean presenceRequirement = false;
    private Integer sizeRequirement;

    public MapSchema required() {
        if (!presenceRequirement) {
            presenceRequirement = true;
        }
        return this;
    }

    public MapSchema sizeof(Integer size) {
        sizeRequirement = size;
        return this;
    }

    @Override
    public Boolean isValid(Map<?, ?> objectToCheck) {
        if (Objects.equals(objectToCheck, null)) {
            return !presenceRequirement;
        }
        if (!Objects.equals(sizeRequirement, null)) {
            return objectToCheck.size() == sizeRequirement;
        }
        return true;
    }
}
