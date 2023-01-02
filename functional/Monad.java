package util.functional;

import java.util.function.Function;

public interface Monad<M extends Monad<M, ? extends T>, T> {
    <R, M2 extends Monad<M2, R>> Monad<M2, R> map(Function<? super T, ? extends R> mapper);
    <R, M2 extends Monad<M2, ? extends R>> Monad<M2, R> flatMap(Function<? super T, M2> flatMapper);
}
