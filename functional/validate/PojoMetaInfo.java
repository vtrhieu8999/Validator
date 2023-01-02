package util.functional.validate;

import io.vavr.control.Option;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class PojoMetaInfo<T> {
    private final Class<T> pojoBuilder;
    private final FieldMetaInfo<?, T>[] fieldsMetaInfo;

    @SuppressWarnings("unchecked")
    PojoMetaInfo(Class<?> pojoClass, Class<T> lombokBuilder) {
        this.pojoBuilder = lombokBuilder;
        this.fieldsMetaInfo = Stream.of(pojoClass.getDeclaredFields())
                .map(this::createFieldMetaInfo)
                .toArray(FieldMetaInfo[]::new);
    }

    Stream<FieldMetaInfo<?, T>> streamOfFieldMetaInfo() {
        return Stream.of(this.fieldsMetaInfo);
    }

    private FieldMetaInfo<?, T> createFieldMetaInfo(Field field) {
        final String name = field.getName();
        final Class<?> type = field.getType();
        final Option<Condition[]> defaultConditions = Option
                .of(field.getAnnotation(DefaultCondition.class))
                .map(DefaultCondition::conditions);
        return !type.isPrimitive() && field.getAnnotation(NotNull.class) == null ?
                new FieldMetaInfo.Nullable<>(type, name, this.pojoBuilder, defaultConditions) :
                new FieldMetaInfo.NonNull<>(type, name, this.pojoBuilder, defaultConditions);
    }
}
