package dapg.control.result;

import dapg.control.result.boundary.AbstractBoundary;
import lombok.NonNull;

import java.util.function.Function;

public record Ok<T, E>(@NonNull T value) implements Result<T, E> {
    protected enum VoidPlaceholder { INSTANCE }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public <TT> Result<TT, E> map(@NonNull Function<T, TT> map) {
        return new Ok<>(map.apply(value));
    }

    @Override
    public <EE> Result<T, EE> mapErr(@NonNull Function<E, EE> mapErr) {
        //noinspection unchecked
        return (Result<T, EE>) this;
    }

    @Override
    public T orBreak(@NonNull AbstractBoundary<?, ? super E, ?> boundary) {
        return value;
    }

    @Override
    public T orBreakThrowable(@NonNull AbstractBoundary<?, ?, ? super E> boundary) {
        return value;
    }
}
