package util.functional.validate;

import io.vavr.control.Either;
import io.vavr.control.Option;
import telsoft.gateway.core.message.GatewayMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InternalImp {
    interface ValidateParamBuilderImp<E, T> extends PublicAPI.GatewayMessageValidator<E, T> {
        PojoMetaInfo<T> getPojoMetaInfo();
        E classCastError(Object value, Class<?> receiverFieldType, String receiverFieldName);
        E nullNotAllowedError(Class<?> receiverFieldType, String receiverFieldName);
        E keyNotExistInGatewayMessage(Class<?> receiverFieldType, String receiverFieldName);

        default <U> Either<E, Consumer<T>> resolveIfKeyNotExist(FieldMetaInfo<U, T> fieldMetaInfo, Map<String, Object> map) {
            return map.containsKey(fieldMetaInfo.NAME) ?
                    resolveNullableValue(fieldMetaInfo, Option.of(map.get(fieldMetaInfo.NAME))) :
                    setDefaultOrError(fieldMetaInfo,
                            Condition.KEY_NOT_EXIST,
                            () -> keyNotExistInGatewayMessage(fieldMetaInfo.TYPE, fieldMetaInfo.NAME)
                    );
        }

        default <U> Either<E, Consumer<T>> resolveNullableValue(FieldMetaInfo<U, T> fieldMetaInfo, Option<Object> value) {
            return value.fold(
                    () -> fieldMetaInfo.fold(
                            nonNull -> setDefaultOrError(nonNull, Condition.NULL,
                                    () -> nullNotAllowedError(fieldMetaInfo.TYPE, fieldMetaInfo.NAME)
                            ),
                            nullable -> nullable.isSettingDefaultWhen(Condition.NULL) ?
                                    getDefault() :
                                    Either.right(fieldMetaInfo.SETTER.apply(null))
                    ),
                    objValue -> toWrapperIfPrimitive(fieldMetaInfo.TYPE).isInstance(objValue) ?
                            Either.right(fieldMetaInfo.SETTER.apply(fieldMetaInfo.TYPE.cast(objValue))) :
                            setDefaultOrError(fieldMetaInfo,
                                    Condition.WRONG_TYPE,
                                    () -> classCastError(objValue, fieldMetaInfo.TYPE, fieldMetaInfo.NAME)
                            )
            );
        }

        default Class<?> toWrapperIfPrimitive(Class<?> clazz) {
            return clazz.isPrimitive() ?
                    primitiveToWrapperClass.get(clazz) :
                    clazz;
        }

        default Either<E, Consumer<T>> getDefault() {
            return Either.right(t -> {});
        }

        default Either<E, Consumer<T>> setDefaultOrError(FieldMetaInfo<?, T> fieldMetaInfo,
                                                         Condition condition,
                                                         Supplier<E> error){
            return fieldMetaInfo.isSettingDefaultWhen(condition) ?
                    getDefault() :
                    Either.left(error.get());
        }

        default List<Either<E, Consumer<T>>> validateAll(GatewayMessage gatewayMessage) {
            return getPojoMetaInfo()
                    .streamOfFieldMetaInfo()
                    .map(fieldMetaInfo -> resolveIfKeyNotExist(
                            fieldMetaInfo,
                            gatewayMessage.getValues())
                    )
                    .collect(Collectors.toList());
        }

        default boolean isAllSuccess(List<Either<E, Consumer<T>>> validateResults) {
            for (Either<E, Consumer<T>> result : validateResults)
                if (result.isLeft()) return false;
            return true;
        }

        @Override
        default Either<List<E>, Consumer<T>> accumulateValidateResult(GatewayMessage gatewayMessage) {
            List<Either<E, Consumer<T>>> validateResults = validateAll(gatewayMessage);
            return isAllSuccess(validateResults) ?
                    Either.right(validateResults.stream()
                            .map(Either::get)
                            .reduce(t -> {}, Consumer::andThen)
                    ) :
                    Either.left(validateResults.stream()
                            .filter(Either::isLeft)
                            .map(Either::getLeft)
                            .collect(Collectors.toList())
                    );
        }
    }

    private static final Map<Class<?>, Class<?>> primitiveToWrapperClass;

    static {
        primitiveToWrapperClass = new HashMap<>(8);
        primitiveToWrapperClass.put(int.class, Integer.class);
        primitiveToWrapperClass.put(long.class, Long.class);
        primitiveToWrapperClass.put(double.class, Double.class);
        primitiveToWrapperClass.put(float.class, Float.class);
        primitiveToWrapperClass.put(boolean.class, Boolean.class);
        primitiveToWrapperClass.put(short.class, Short.class);
        primitiveToWrapperClass.put(char.class, Character.class);
        primitiveToWrapperClass.put(byte.class, Byte.class);
    }
}
