package util.checked;

import java.util.function.Function;

public interface CheckedFunc8<T, R,
        E1 extends Exception,
        E2 extends Exception,
        E3 extends Exception,
        E4 extends Exception,
        E5 extends Exception,
        E6 extends Exception,
        E7 extends Exception,
        E8 extends Exception> extends Commutable._8<E1, E2, E3, E4, E5, E6, E7, E8> {
    R apply(T val) throws E1, E2, E3, E4, E5, E6, E7, E8;
    default <V> CheckedFunc8<T, V, E1, E2, E3, E4, E5, E6, E7, E8> andThen(Function<? super R, ? extends V> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc8<V, R, E1, E2, E3, E4, E5, E6, E7, E8> compose(Function<? super V, ? extends T> mapper){
        return t -> apply(mapper.apply(t));
    }
}