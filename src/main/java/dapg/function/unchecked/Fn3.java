package dapg.function.unchecked;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a function that accepts three arguments and produces a result.
 *
 * @param <T1> the type of the 1st argument to the function
 * @param <T2> the type of the 2nd argument to the function
 * @param <T3> the type of the 3rd argument to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Fn3<T1, T2, T3, R> extends Serializable {
    @Serial
    long serialVersionUID = 1L;

    /**
     * Applies this function to the given arguments.
     *
     * @param t1 argument 1
     * @param t2 argument 2
     * @param t3 argument 3
     * @return the function result
     */
    R apply(T1 t1, T2 t2, T3 t3);
}
