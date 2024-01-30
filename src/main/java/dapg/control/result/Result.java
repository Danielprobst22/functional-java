package dapg.control.result;

import dapg.control.result.boundary.Boundary;
import dapg.control.result.boundary.ErrorHandler;
import io.vavr.CheckedFunction0;
import lombok.NonNull;

import java.util.function.Function;

public sealed interface Result<T, E> permits Ok, Err {

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

    static <T, E, MappableE> Boundary<T, E, MappableE> boundary(@NonNull ErrorHandler<E, MappableE> errorHandler) {
        return new Boundary<>(errorHandler);
    }

    boolean isOk();

    boolean isErr();

    <EE> Result<T, EE> mapErr(@NonNull Function<E, EE> mapErr);

    T orBreak(@NonNull Boundary<?, ? super E, ?> boundary);

    T orBreakMappable(@NonNull Boundary<?, ?, ? super E> boundary);

    T orBreakThrowable(@NonNull Boundary<?, ?, ?> boundary, @NonNull Function<E, Throwable> mapErr);
}
