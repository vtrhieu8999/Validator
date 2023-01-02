package util.functional.validate.test;

import io.vavr.control.Option;
import io.vavr.control.Validation;
import util.functional.validate.PublicAPI;

import java.util.function.BiFunction;
import java.util.function.Function;

public class NonReflectionImp {
    interface ValidateParamBuilderImp<E, T> extends PublicAPI.ValidateParamBuilder<E, T> {
        <F> Validation<E, Function<T, T>> generalImp(
                Class<F> receiverFieldType, Object value,
                StringBuilder receiverFieldName, BiFunction<T, F, T> setter
        );

        @Override
        default Validation<E, Function<T, T>> of(PublicAPI.LongSetter<T> setter, Object value, StringBuilder receiverFieldName) {
            return generalImp(Long.class, value, receiverFieldName, setter);
        }

        @Override
        default Validation<E, Function<T, T>> of(PublicAPI.IntegerSetter<T> setter, Object value, StringBuilder receiverFieldName) {
            return generalImp(Integer.class, value, receiverFieldName, setter);
        }

        @Override
        default Validation<E, Function<T, T>> of(PublicAPI.StringSetter<T> setter, Object value, StringBuilder receiverFieldName) {
            return generalImp(String.class, value, receiverFieldName, setter);
        }

        @Override
        default Validation<E, Function<T, T>> of(PublicAPI.DoubleSetter<T> setter, Object value, StringBuilder receiverFieldName) {
            return generalImp(Double.class, value, receiverFieldName, setter);
        }

        @Override
        default Validation<E, Function<T, T>> of(PublicAPI.FloatSetter<T> setter, Object value, StringBuilder receiverFieldName) {
            return generalImp(Float.class, value, receiverFieldName, setter);
        }

        @Override
        default Validation<E, Function<T, T>> of(PublicAPI.BooleanSetter<T> setter, Object value, StringBuilder receiverFieldName) {
            return generalImp(Boolean.class, value, receiverFieldName, setter);
        }
    }

    interface AcceptNullValue<E, T> extends ValidateParamBuilderImp<E, T>{
        <F, P> E mapFailureResult(Class<F> receiverFieldType, Class<P> providerType, StringBuilder name);
        @Override
        default <F> Validation<E, Function<T, T>> generalImp(Class<F> receiverFieldType, Object value, StringBuilder receiverFieldName, BiFunction<T, F, T> setter) {
            return !receiverFieldType.isInstance(value) && value!=null ?
                    Validation.invalid(mapFailureResult(receiverFieldType, value.getClass(), receiverFieldName)) :
                    Validation.valid(t -> setter.apply(t, receiverFieldType.cast(value)));
        }
        static <E, T> AcceptNullValue<E, T> of(AcceptNullValue<E, T> lambda){
            return lambda;
        }
    }

    interface NonNullValue<E, T> extends ValidateParamBuilderImp<E, T>{
        <F> E mapFailureResult(Class<F> receiverFieldType, Option<Object> value, StringBuilder receiverFieldName);
        @Override
        default <F> Validation<E, Function<T, T>> generalImp(Class<F> receiverFieldType, Object value, StringBuilder receiverFieldName, BiFunction<T, F, T> setter) {
            Class<Integer> abc = int.class;
            return !receiverFieldType.isInstance(value) ?
                    Validation.invalid(mapFailureResult(receiverFieldType, Option.of(value), receiverFieldName)) :
                    Validation.valid(t -> setter.apply(t, receiverFieldType.cast(value)));
        }
        static <E, T> NonNullValue<E, T> of(NonNullValue<E, T> lambda){
            return lambda;
        }
    }
}
