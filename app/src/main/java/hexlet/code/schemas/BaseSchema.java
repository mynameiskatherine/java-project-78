package hexlet.code.schemas;

public abstract class BaseSchema<T> {

    public abstract Boolean isValid(T objectToCheck);
}
