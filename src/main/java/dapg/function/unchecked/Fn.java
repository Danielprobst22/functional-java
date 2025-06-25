package dapg.function.unchecked;

import java.io.Serial;
import java.io.Serializable;
import java.util.function.Function;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @param <T1> the type of the 1st argument to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Fn<T1, R> extends Serializable, Function<T1, R> {
    @Serial
    long serialVersionUID = 1L;

    /**
     * Applies this function to the given argument.
     *
     * @param t1 argument 1
     * @return the function result
     */
    R apply(T1 t1);
}
