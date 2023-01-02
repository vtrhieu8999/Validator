package util.checked;

import java.util.function.Function;

public interface CheckedFunc4<T, R,
        E1 extends Exception,
        E2 extends Exception,
        E3 extends Exception,
        E4 extends Exception> extends Commutable._4<E1, E2, E3, E4>{
    R apply(T val) throws E1, E2, E3, E4;

    default <V> CheckedFunc4<T, V, E1, E2, E3, E4> andThen(Function<? super R, ? extends V> mapper) {
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc4<V, R, E1, E2, E3, E4> compose(Function<? super V, ? extends T> mapper) {
        return t -> apply(mapper.apply(t));
    }
}
