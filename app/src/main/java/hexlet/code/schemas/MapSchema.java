package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private Boolean presenceRequirement = false;
    private Integer sizeRequirement;
    private Map<?, ? extends BaseSchema> shapeRequirement;

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

    public MapSchema shape(Map<?, ? extends BaseSchema> mapToShapeTo) {
        shapeRequirement = mapToShapeTo;
        return this;
    }

    @Override
    public Boolean isValid(Map<?, ?> objectToCheck) {
        if (Objects.equals(objectToCheck, null)) {
            return !presenceRequirement;
        }
        if (!Objects.equals(sizeRequirement, null)) {
            if (objectToCheck.size() != sizeRequirement) {
                return false;
            }
        }
        if (!Objects.equals(shapeRequirement, null)) {
            if (!Objects.equals(shapeRequirement.keySet(), objectToCheck.keySet())) {
                return false;
            }
            final Boolean[] result = {true};
            shapeRequirement.keySet().forEach(k -> {
                if (!shapeRequirement.get(k).isValid(objectToCheck.get(k))) {
                    result[0] = false;
                }
            });
            return result[0];
        }
        return true;
    }
}
