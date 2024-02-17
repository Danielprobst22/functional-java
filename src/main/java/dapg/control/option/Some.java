package dapg.control.option;

import lombok.NonNull;

import java.util.function.Function;
import java.util.function.Supplier;

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
    public T getOrElse(@NonNull Supplier<? extends T> supplier) {
        return value;
    }

    @Override
    public Option<T> orElse(@NonNull Option<? extends T> other) {
        return this;
    }

    @Override
    public Option<T> orElse(@NonNull Supplier<? extends Option<? extends T>> supplier) {
        return this;
    }

    @Override
    public <TT> Option<TT> map(@NonNull Function<T, TT> map) {
        return new Some<>(map.apply(value));
    }
}
