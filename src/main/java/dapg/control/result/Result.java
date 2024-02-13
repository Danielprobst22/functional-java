package dapg.control.result;

import dapg.control.result.boundary.AbstractBoundary;
import dapg.control.result.boundary.Boundary;
import dapg.control.result.boundary.BoundaryWithContext;
import dapg.control.result.boundary.context.ContextfulError;
import io.vavr.CheckedFunction0;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;

public sealed interface Result<T, E> extends Serializable permits Ok, Err {

    static <T, E> Result<T, E> ok(@NonNull T value) {
        return new Ok<>(value);
    }

    static <T, E> Result<T, E> err(@NonNull E err) {
        return new Err<>(err);
    }

    static <T> Result<T, Throwable> attempt(
            @NonNull CheckedFunction0<T> fn
    ) {
        try {
            T value = fn.apply();
            return Result.ok(value);
        } catch (Throwable e) {
            return Result.err(e);
        }
    }

    static <T, E> Result<T, E> attempt(
            @NonNull Function<Throwable, E> mapErr,
            @NonNull CheckedFunction0<T> fn
    ) {
        try {
            T value = fn.apply();
            return Result.ok(value);
        } catch (Throwable e) {
            return Result.err(mapErr.apply(e));
        }
    }

    static <T, E> Boundary<T, E> boundary(@NonNull Function<Throwable, E> mapThrowable) {
        return new Boundary<>(mapThrowable);
    }

    @SafeVarargs
    static <T, E extends ContextfulError> BoundaryWithContext<T, E> boundaryWithContext(
            @NonNull Function<Throwable, E> mapThrowable,
            @NonNull Map.Entry<String, ? extends Serializable>... contextEntries
    ) {
        return new BoundaryWithContext<>(mapThrowable, contextEntries);
    }

    boolean isOk();

    boolean isErr();

    <EE> Result<T, EE> mapErr(@NonNull Function<E, EE> mapErr);

    T orBreak(@NonNull AbstractBoundary<?, ? super E> boundary);

    /**
     * todo write proper doc
     * In this scenario, dummyContextEntry will be ignored: <br>
     * dummyResult.orBreakThrowable(boundary.addContext(dummyContextEntry), err -> err) <br>
     * However, orBreakThrowable() is a function that might be nice to have in certain circumstances but is not expected to be used too extensively,
     * so further complicating the design of BoundaryWithContext to handle this edge case does not seem worth the trade-off <br>
     *
     * @param boundary
     * @param mapErr
     * @return
     */
    T orBreakThrowable(@NonNull AbstractBoundary<?, ?> boundary, @NonNull Function<E, Throwable> mapErr);
}
