package dapg.control.option;

import io.vavr.Function2;
import lombok.NonNull;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

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

    static <T1, T2, R> Option<R> and(
            @NonNull Option<T1> o1,
            @NonNull Option<T2> o2,
            @NonNull Function2<T1, T2, R> combine
    ) {
        if (o1 instanceof Some(T1 t1) && o2 instanceof Some(T2 t2)) {
            return Option.some(combine.apply(t1, t2));
        } else {
            return Option.none();
        }
    }

    boolean isPresent();

    boolean isEmpty();

    T getOrElse(@NonNull T other);

    T getOrElse(@NonNull Supplier<? extends T> supplier);

    Option<T> orElse(@NonNull Option<? extends T> other);

    Option<T> orElse(@NonNull Supplier<? extends Option<? extends T>> supplier);

    <TT> Option<TT> map(@NonNull Function<T, TT> map);
}
