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
    public <TT> Result<TT, E> map(@NonNull Function<T, TT> map) {
        //noinspection unchecked
        return (Result<TT, E>) this;
    }

    @Override
    public <EE> Result<T, EE> mapErr(@NonNull Function<E, EE> mapErr) {
        return new Err<>(mapErr.apply(err));
    }

    @Override
    public T orBreak(@NonNull AbstractBoundary<?, ? super E, ?> boundary) {
        return boundary.breakErr(err); // throws ErrEarlyReturnException
    }

    @Override
    public T orBreakThrowable(@NonNull AbstractBoundary<?, ?, ? super E> boundary) {
        return boundary.breakThrowable(err); // throws ThrowableEarlyReturnException
    }
}
