package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.Map;
import java.util.HashMap;

public abstract class BaseSchema<T, U extends BaseSchema> {

    protected Map<CheckName, Predicate<T>> checks = new HashMap<>();
    protected boolean presenceRequirement = false;

    public U required() {
        if (!presenceRequirement) {
            presenceRequirement = true;
        }
        return (U) this;
    }

    protected final void addCheck(CheckName checkName, Predicate<T> check) {
        checks.put(checkName, check);
    }

    public final Boolean isValid(T objectToCheck) {
        if (Objects.isNull(objectToCheck) || Objects.equals("", objectToCheck)) {
            return !presenceRequirement;
        }
        for (CheckName check : checks.keySet()) {
            if (!checks.get(check).test(objectToCheck)) {
                return false;
            }
        }
        return true;
    }
}
