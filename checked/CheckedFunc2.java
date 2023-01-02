package util.checked;

import java.util.function.Function;

public interface CheckedFunc2<T, R, E1 extends Exception, E2 extends Exception> extends Commutable._2<E1, E2> {
    R apply(T val) throws E1, E2;

    default <V> CheckedFunc2<T, V, E1, E2> andThen(Function<? super R, ? extends V> mapper) {
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc2<V, R, E1, E2> compose(Function<? super V, ? extends T> mapper) {
        return t -> apply(mapper.apply(t));
    }
}
