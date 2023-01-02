package util.checked;

import java.util.function.Function;

public interface CheckedFunc6<T, R,
        E1 extends Exception,
        E2 extends Exception,
        E3 extends Exception,
        E4 extends Exception,
        E5 extends Exception,
        E6 extends Exception> extends Commutable._6<E1, E2, E3, E4, E5, E6>{
    R apply(T val) throws E1, E2, E3, E4, E5, E6;

    default <V> CheckedFunc6<T, V, E1, E2, E3, E4, E5, E6> andThen(Function<? super R, ? extends V> mapper) {
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc6<V, R, E1, E2, E3, E4, E5, E6> compose(Function<? super V, ? extends T> mapper) {
        return t -> apply(mapper.apply(t));
    }
}