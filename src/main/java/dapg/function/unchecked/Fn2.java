package dapg.function.unchecked;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.BiFunction;

/**
 * Represents a function that accepts two arguments and produces a result.
 *
 * @param <T1> the type of the 1st argument to the function
 * @param <T2> the type of the 2nd argument to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Fn2<T1, T2, R> extends Serializable, BiFunction<T1, T2, R> {
    @Serial
    long serialVersionUID = 1L;

    /**
     * Applies this function to the given arguments.
     *
     * @param t1 argument 1
     * @param t2 argument 2
     * @return the function result
     */
    R apply(T1 t1, T2 t2);
}
