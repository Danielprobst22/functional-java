package dapg.control.option;

import lombok.NonNull;

import java.util.function.Function;
import java.util.function.Supplier;

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
    public T getOrElse(@NonNull Supplier<? extends T> supplier) {
        return supplier.get();
    }

    @Override
    public Option<T> orElse(@NonNull Option<? extends T> other) {
        //noinspection unchecked
        return (Option<T>) other;
    }

    @Override
    public Option<T> orElse(@NonNull Supplier<? extends Option<? extends T>> supplier) {
        //noinspection unchecked
        return (Option<T>) supplier.get();
    }

    @Override
    public <TT> Option<TT> map(@NonNull Function<T, TT> map) {
        //noinspection unchecked
        return (Option<TT>) this;
    }
}
