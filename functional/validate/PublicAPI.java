package util.functional.validate;

import io.vavr.Function3;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import telsoft.gateway.core.message.GatewayMessage;

import java.util.List;
import java.util.function.*;

public final class PublicAPI {
    public interface LongSetter<T> extends BiFunction<T, Long, T>{}
    public interface IntegerSetter<T> extends BiFunction<T, Integer, T>{}
    public interface StringSetter<T> extends BiFunction<T, String, T>{}
    public interface DoubleSetter<T> extends BiFunction<T, Double, T>{}
    public interface FloatSetter<T> extends BiFunction<T, Float, T>{}
    public interface BooleanSetter<T> extends BiFunction<T, Boolean, T>{}
    public interface ValidateParamBuilder<E, T> {
        Validation<E, Function<T, T>> of(LongSetter<T> setter, Object value, StringBuilder receiverFieldName);
        Validation<E, Function<T, T>> of(IntegerSetter<T> setter, Object value, StringBuilder receiverFieldName);
        Validation<E, Function<T, T>> of(StringSetter<T> setter, Object value, StringBuilder receiverFieldName);
        Validation<E, Function<T, T>> of(DoubleSetter<T> setter, Object value, StringBuilder receiverFieldName);
        Validation<E, Function<T, T>> of(FloatSetter<T> setter, Object value, StringBuilder receiverFieldName);
        Validation<E, Function<T, T>> of(BooleanSetter<T> setter, Object value, StringBuilder receiverFieldName);
    }

    // E is error's result type
    // T is lombok builder class
    public interface GatewayMessageValidator<E, T> {
        Either<List<E>, Consumer<T>> accumulateValidateResult(GatewayMessage gatewayMessage);
    }
    public static <E, T> GatewayMessageValidator<E, T>
    createValidator(Class<?> pojoClass, // pojo class that need to validate
                    Class<T> lombokBuilder, // lombok builder class of pojo
                    // Object is a value in GatewayMessage
                    // Class<?> is type of current pojo's field
                    // String is name of current pojo's field
                    Function3<Object, Class<?>, String, E> classCastError,
                    BiFunction<Class<?>, String, E> nullNotAllowedError,
                    BiFunction<Class<?>, String, E> keyNotExist){
        final PojoMetaInfo<T> pojoMetaInfo = new PojoMetaInfo<>(pojoClass, lombokBuilder);
        return new InternalImp.ValidateParamBuilderImp<E, T>() {
            @Override
            public PojoMetaInfo<T> getPojoMetaInfo() {
                return pojoMetaInfo;
            }

            @Override
            public E classCastError(Object value, Class<?> receiverFieldType, String receiverFieldName) {
                return classCastError.apply(value, receiverFieldType, receiverFieldName);
            }

            @Override
            public E nullNotAllowedError(Class<?> receiverFieldType, String receiverFieldName) {
                return nullNotAllowedError.apply(receiverFieldType, receiverFieldName);
            }

            @Override
            public E keyNotExistInGatewayMessage(Class<?> receiverFieldType, String receiverFieldName) {
                return keyNotExist.apply(receiverFieldType, receiverFieldName);
            }
        };
    }

}
