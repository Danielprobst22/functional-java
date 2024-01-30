package dapg.control.result;

import dapg.control.result.boundary.Boundary;
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
    public T orBreak(@NonNull Boundary<?, ? super E, ?> boundary) {
        return boundary.breakErr(err); // throws ErrEarlyReturnException
    }

    @Override
    public T orBreakMappable(@NonNull Boundary<?, ?, ? super E> boundary) {
        return boundary.breakMappable(err); // throws MappableErrEarlyReturnException
    }

    @Override
    public T orBreakThrowable(@NonNull Boundary<?, ?, ?> boundary, @NonNull Function<E, Throwable> mapErr) {
        return boundary.breakThrowable(mapErr.apply(err));
    }
}
