package dapg.control.option;

import lombok.NonNull;

import java.util.function.Function;

public record Some<T>(@NonNull T value) implements Option<T> {
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T getOrElse(@NonNull T other) {
        return value;
    }

    @Override
    public <TT> Option<TT> map(@NonNull Function<T, TT> map) {
        return new Some<>(map.apply(value));
    }
}
