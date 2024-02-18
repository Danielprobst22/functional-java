package dapg.control.result;

import dapg.control.result.boundary.AbstractBoundary;
import lombok.NonNull;

import java.util.function.Function;

public record Ok<T, E>(@NonNull T value) implements Result<T, E> {

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
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
