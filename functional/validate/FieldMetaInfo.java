package util.functional.validate;

import io.vavr.control.Option;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class FieldMetaInfo<U, T> {
    public final Class<U> TYPE;
    public final String NAME;
    //U is field type, T is lombok Builder class
    public final Function<U, Consumer<T>> SETTER;
    private final Method rawSetter;
    private final Option<Condition[]> defaultConditions;

    @SneakyThrows
    private FieldMetaInfo(Class<U> type, String name, Class<T> lombokBuilder, Option<Condition[]> defaultConditions){
        this.TYPE = type;
        this.NAME = name;
        this.defaultConditions = defaultConditions;
        this.rawSetter = lombokBuilder.getMethod(name, type);
        // improve invokes performance
        this.rawSetter.setAccessible(true);
        this.SETTER = u -> t -> invokeSetter(t, u);
    }

    boolean isSettingDefaultWhen(Condition condition){
        return defaultConditions.fold(
                () -> false,
                conditions -> {
                    for(Condition current : conditions)
                        if(current == condition) return true;
                        return false;
                }
        );
    }

    @SneakyThrows
    private void invokeSetter(T builder, U value){
        this.rawSetter.invoke(builder, value);
    }

    abstract <R> R fold(Function<? super NonNull<U, T>, ? extends R> nonNullMap,
                        Function<? super Nullable<U, T>, ? extends R> nullableMap);

    static class NonNull<U, T> extends FieldMetaInfo<U, T>{
        NonNull(Class<U> type, String name, Class<T> lombokBuilder, Option<Condition[]> defaultConditions){
            super(type, name, lombokBuilder, defaultConditions);
        }
        @Override
        <R> R fold(Function<? super NonNull<U, T>, ? extends R> nonNullMap,
                   Function<? super Nullable<U, T>, ? extends R> nullableMap) {
            return nonNullMap.apply(this);
        }
    }

    static class Nullable<U, T> extends FieldMetaInfo<U, T>{
        Nullable(Class<U> type, String name, Class<T> lombokBuilder, Option<Condition[]> defaultConditions){
            super(type, name, lombokBuilder, defaultConditions);
        }
        @Override
        <R> R fold(Function<? super NonNull<U, T>, ? extends R> nonNullMap,
                   Function<? super Nullable<U, T>, ? extends R> nullableMap) {
            return nullableMap.apply(this);
        }
    }
}
