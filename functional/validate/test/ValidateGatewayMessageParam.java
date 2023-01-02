package util.functional.validate.test;

import io.vavr.control.Validation;
import util.functional.validate.PublicAPI;

import java.util.function.Function;

public class ValidateGatewayMessageParam<E, T> {
    public Validation<StringBuilder, Function<T, T>> of(PublicAPI.LongSetter<T> setter, Object value, StringBuilder name) {
        return !(value instanceof Long) ?
                Validation.invalid(name.append("'s type is not Long \n")) :
                Validation.valid(t -> setter.apply(t, (Long) value));
    }

    public Validation<StringBuilder, Function<T, T>> of(PublicAPI.IntegerSetter<T> setter, Object value, StringBuilder name) {
        return !(value instanceof Integer) ?
                Validation.invalid(name.append("'s type is not Integer \n")) :
                Validation.valid(t -> setter.apply(t, (Integer) value));
    }

    public Validation<StringBuilder, Function<T, T>> of(PublicAPI.StringSetter<T> setter, Object value, StringBuilder name) {
        return !(value instanceof String) ?
                Validation.invalid(name.append("'s type is not String \n")) :
                Validation.valid(t -> setter.apply(t, (String) value));
    }

    public Validation<StringBuilder, Function<T, T>> of(PublicAPI.DoubleSetter<T> setter, Object value, StringBuilder name) {
        return !(value instanceof Double) ?
                Validation.invalid(name.append("'s type is not Double \n")) :
                Validation.valid(t -> setter.apply(t, (Double) value));
    }

    public Validation<StringBuilder, Function<T, T>> of(PublicAPI.FloatSetter<T> setter, Object value, StringBuilder name) {
        return !(value instanceof Float) ?
                Validation.invalid(name.append("'s type is not Float \n")) :
                Validation.valid(t -> setter.apply(t, (Float) value));
    }

    public Validation<StringBuilder, Function<T, T>> of(PublicAPI.BooleanSetter<T> setter, Object value, StringBuilder name) {
        return !(value instanceof Boolean) ?
                Validation.invalid(name.append("'s type is not Boolean \n")) :
                Validation.valid(t -> setter.apply(t, (Boolean) value));
    }
}
