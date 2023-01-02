package util.checked;

import java.util.function.Function;

public interface CheckedFunc3<T, R,
        E1 extends Exception,
        E2 extends Exception,
        E3 extends Exception> extends Commutable._3<E1, E2, E3>{
    R apply(T val) throws E1, E2, E3;

    default <V> CheckedFunc3<T, V, E1, E2, E3> andThen(Function<? super R, ? extends V> mapper) {
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc3<V, R, E1, E2, E3> compose(Function<? super V, ? extends T> mapper) {
        return t -> apply(mapper.apply(t));
    }
}
