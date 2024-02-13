package dapg.control.result;

import dapg.control.result.boundary.AbstractBoundary;
import lombok.NonNull;

import java.util.function.Function;

public record Err<T, E>(@NonNull E err) implements Result<T, E> {

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public <EE> Result<T, EE> mapErr(@NonNull Function<E, EE> mapErr) {
        return new Err<>(mapErr.apply(err));
    }

    @Override
    public T orBreak(@NonNull AbstractBoundary<?, ? super E> boundary) {
        return boundary.breakErr(err); // throws ErrEarlyReturnException
    }

    @Override
    public T orBreakThrowable(@NonNull AbstractBoundary<?, ?> boundary, @NonNull Function<E, Throwable> mapErr) {
        return boundary.breakThrowable(mapErr.apply(err)); // throws ThrowableEarlyReturnException
    }
}
