package util.checked;

import java.util.function.Function;

public interface CheckedFunc5<T, R,
        E1 extends Exception,
        E2 extends Exception,
        E3 extends Exception,
        E4 extends Exception,
        E5 extends Exception> extends Commutable._5<E1, E2, E3, E4, E5>{
    R apply(T val) throws E1, E2, E3, E4, E5;

    default <V> CheckedFunc5<T, V, E1, E2, E3, E4, E5> andThen(Function<? super R, ? extends V> mapper) {
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc5<V, R, E1, E2, E3, E4, E5> compose(Function<? super V, ? extends T> mapper) {
        return t -> apply(mapper.apply(t));
    }
}