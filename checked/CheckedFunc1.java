package util.checked;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunc1<T, R, E1 extends Exception> {
    R apply(T val) throws E1;
    default <V> CheckedFunc1<T, V, E1> andThen(Function<? super R, ? extends V> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V> CheckedFunc1<V, R, E1> compose(Function<? super V, ? extends T> mapper){
        return t -> apply(mapper.apply(t));
    }

    default <V, E2 extends Exception> CheckedFunc2<T, V, E1, E2>
    flatMap(CheckedFunc1<? super R, ? extends V, ? extends E2> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V, E2 extends Exception,
            E3 extends Exception>
    CheckedFunc3<T, V, E1, E2, E3>
    flatMap(CheckedFunc2<? super R, ? extends V, ? extends E2, ? extends E3> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V, E2 extends Exception,
            E3 extends Exception,
            E4 extends Exception>
    CheckedFunc4<T, V, E1, E2, E3, E4>
    flatMap(CheckedFunc3<? super R, ? extends V, ? extends E2, ? extends E3, ? extends E4> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V, E2 extends Exception,
            E3 extends Exception,
            E4 extends Exception,
            E5 extends Exception>
    CheckedFunc5<T, V, E1, E2, E3, E4, E5>
    flatMap(CheckedFunc4<? super R, ? extends V, ? extends E2, ? extends E3, ? extends E4, ? extends E5> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V, E2 extends Exception,
            E3 extends Exception,
            E4 extends Exception,
            E5 extends Exception,
            E6 extends Exception>
    CheckedFunc6<T, V, E1, E2, E3, E4, E5, E6>
    flatMap(CheckedFunc5<? super R, ? extends V, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V, E2 extends Exception,
            E3 extends Exception,
            E4 extends Exception,
            E5 extends Exception,
            E6 extends Exception,
            E7 extends Exception>
    CheckedFunc7<T, V, E1, E2, E3, E4, E5, E6, E7>
    flatMap(CheckedFunc6<? super R, ? extends V, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6, ? extends E7> mapper){
        return t -> mapper.apply(apply(t));
    }

    default <V, E2 extends Exception,
            E3 extends Exception,
            E4 extends Exception,
            E5 extends Exception,
            E6 extends Exception,
            E7 extends Exception,
            E8 extends Exception>
    CheckedFunc8<T, V, E1, E2, E3, E4, E5, E6, E7, E8>
    flatMap(CheckedFunc7<? super R, ? extends V, ? extends E2, ? extends E3, ? extends E4, ? extends E5, ? extends E6, ? extends E7, ? extends E8> mapper){
        return t -> mapper.apply(apply(t));
    }
}
