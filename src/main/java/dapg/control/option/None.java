package dapg.control.option;

import lombok.NonNull;

import java.util.function.Function;

public record None<T>() implements Option<T> {
    static final None<?> INSTANCE = new None<>();

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T getOrElse(@NonNull T other) {
        return other;
    }

    @Override
    public <TT> Option<TT> map(@NonNull Function<T, TT> map) {
        //noinspection unchecked
        return (Option<TT>) this;
    }
}
