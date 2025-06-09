package dapg.examples.domainmodelling;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

public class UnionTest {

    @Test
    void test() {
        Un2<String, Integer> v1 = Un2.t1("Hallihallooo");
        matchStringInteger(v1);
        matchStringInteger(Un2.t1("Hallihallooo"));

        Un2<String, Integer> v2 = Un2.t2(42);
        matchStringInteger(v2);
        matchStringInteger(Un2.t2(42));


        Un2T1<String, String> v3 = Un2.t1("Watt");
        matchStringInteger(Un2.safeCastT1(v3));

        Un2T1<String, Object> v4 = Un2.t1("Watt");
        matchStringInteger(Un2.safeCastT1(v4));

        // ------------------------------------------------------------

        Child_1 julia = new Child_1("Julia");
        Un2<Child_1, String> juliaTuple = Un2.t1(julia);
//        matchParentString(juliaTuple); // java: incompatible types: Un2<Child_1,String> cannot be converted to Un2<Parent,String>
        matchParentString(Un2.safeCast(juliaTuple));
    }

    private void matchStringInteger(Un2<String, Integer> union) {
        switch (union) {
            case Un2T1(String value) -> System.out.println("String value: " + value);
            case Un2T2(Integer value) -> System.out.println("Integer value: " + value);
        }
    }

    private void matchParentString(Un2<Parent, String> union) {
        switch (union) {
//            case Un2T1(Parent value) -> System.out.println("Parent value: " + value);
            case Un2T1(Child_1(String value)) -> System.out.println("Child_1 value: " + value);
            case Un2T1(Child_2(Integer value)) -> System.out.println("Child_2 value: " + value);
            case Un2T2(String value) -> System.out.println("String value: " + value);
        }
    }


    private void make() {
        Aha<String> v1 = Variance.aha("Jo");
        take(v1);

//        Variance<String> v2 = Variance.aha("Jo");
        Variance<String> v2 = v1;
        take(v2);


        Aha<Child_1> v3 = Variance.aha(new Child_1("Julia"));
        Variance<Child_1> v4 = v3;

//        Aha<Parent> v5 = v3; // java: incompatible types: Aha<Child> cannot be converted to Aha<Parent>
//        Aha<? extends Parent> v5 = v3;
//        Variance<Parent> v5 = v3;
    }

    private void take(Variance<String> v) {
        System.out.println("Received: " + v);
    }

    sealed interface Parent {}

    record Child_1(String value) implements Parent {}

    record Child_2(Integer value) implements Parent {}


    private sealed interface Variance<T> {
        static <T> Aha<T> aha(T value) {
            return new Aha<>(value);
        }
    }

    private record Aha<T>(T value) implements Variance<T> {}

//    @Value
//    private static class Aha<T> implements Variance<T> {
//        private final T value;
//    }


    private sealed interface Un2<T1, T2> {

        static <T1, T2> Un2<T1, T2> safeCast(Un2<? extends T1, ? extends T2> un2) {
            //noinspection unchecked
            return (Un2<T1, T2>) un2;
        }

        static <T1, T2> Un2T1<T1, T2> t1(T1 t1) {
            return new Un2T1<>(t1);
        }

        static <T1, T2> Un2T1<T1, T2> safeCastT1(Un2T1<T1, ?> t1) {
            //noinspection unchecked
            return (Un2T1<T1, T2>) t1;
        }

        static <T1, T2> Un2T2<T1, T2> t2(T2 t2) {
            return new Un2T2<>(t2);
        }

        static <T1, T2> Un2T2<T1, T2> safeCastT2(Un2T2<?, T2> t2) {
            //noinspection unchecked
            return (Un2T2<T1, T2>) t2;
        }


        default <R> R mapMatch(
                Function<T1, R> mapIfT1,
                Function<T2, R> mapIfT2
        ) {
            return switch (this) {
                case Un2T1(T1 value) -> mapIfT1.apply(value);
                case Un2T2(T2 value) -> mapIfT2.apply(value);
            };
        }

        default <NewT1> Un2<NewT1, T2> mapIfT1(
                Function<T1, NewT1> mapIfT1
        ) {
            return switch (this) {
                case Un2T1(T1 value) -> new Un2T1<>(mapIfT1.apply(value));
                case Un2T2<T1, T2> t2 -> safeCastT2(t2);
            };
        }

        default <NewT2> Un2<T1, NewT2> mapIfT2(
                Function<T2, NewT2> mapIfT2
        ) {
            return switch (this) {
                case Un2T1<T1, T2> t1 -> safeCastT1(t1);
                case Un2T2(T2 value) -> new Un2T2<>(mapIfT2.apply(value));
            };
        }


        default void consumeMatch(
                Consumer<T1> consumeIfT1,
                Consumer<T2> consumeIfT2
        ) {
            switch (this) {
                case Un2T1(T1 value) -> consumeIfT1.accept(value);
                case Un2T2(T2 value) -> consumeIfT2.accept(value);
            };
        }
    }

    private record Un2T1<T1, T2>(T1 value) implements Un2<T1, T2> {}

    private record Un2T2<T1, T2>(T2 value) implements Un2<T1, T2> {}
}
