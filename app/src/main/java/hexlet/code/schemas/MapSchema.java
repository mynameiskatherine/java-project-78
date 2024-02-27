package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema required() {
        if (!super.presenceRequirement) {
            super.presenceRequirement = true;
        }
        return this;
    }

    public MapSchema sizeof(Integer size) {
        addCheck(CheckName.MAP_SIZEOF, o -> o.size() == size);
        return this;
    }

    public MapSchema shape(Map<?, ? extends BaseSchema> mapToShapeTo) {
        addCheck(CheckName.MAP_SHAPE, o -> {
            if (!Objects.equals(mapToShapeTo.keySet(), o.keySet())) {
                return false;
            }
            for (Object k : mapToShapeTo.keySet()) {
                if (!mapToShapeTo.get(k).isValid(o.get(k))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
