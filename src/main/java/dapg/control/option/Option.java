package dapg.control.option;

import lombok.NonNull;

import java.io.Serializable;
import java.util.function.Function;

public sealed interface Option<T> extends Serializable permits Some, None {

    static <T> Option<T> ofNullable(T nullableValue) {
        return nullableValue != null
                ? some(nullableValue)
                : none();
    }

    static <T> Option<T> some(@NonNull T value) {
        return new Some<>(value);
    }

    static <T> Option<T> none() {
        //noinspection unchecked
        return (Option<T>) None.INSTANCE;
    }

    boolean isPresent();

    boolean isEmpty();

    T getOrElse(@NonNull T other);

    <TT> Option<TT> map(@NonNull Function<T, TT> map);
}
