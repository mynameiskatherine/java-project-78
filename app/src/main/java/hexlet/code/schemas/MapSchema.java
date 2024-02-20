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
        Boolean result = true;
        if (Objects.isNull(objectToCheck)) {
            return !presenceRequirement;
        }
        if (Objects.nonNull(sizeRequirement) && objectToCheck.size() != sizeRequirement) {
            result = false;
        }
        if (Objects.nonNull(shapeRequirement)) {
            if (!Objects.equals(shapeRequirement.keySet(), objectToCheck.keySet())) {
                result = false;
            }
            for (Object k : shapeRequirement.keySet()) {
                if (!shapeRequirement.get(k).isValid(objectToCheck.get(k))) {
                    result = false;
                }
            }
        }
        return result;
    }
}
