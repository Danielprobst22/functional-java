package dapg.examples.domainmodelling;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

public class SimpleAliasTest {

    @Test
    void test() {
        FooId fooId = Alias.of(FooId.K, 42L);
        System.out.println("Alias: " + fooId);

        long fooValue = fooId.v();
        System.out.println("Value: " + fooValue);

        BarId barId = Alias.of(BarId.K, "Hallihallooo");
        System.out.println("Alias: " + barId);

        String barValue = barId.v();
        System.out.println("Value: " + barValue);
    }

    abstract static class Alias<ValueT> {
        protected ValueT value;

        public static <ValueT, AliasT extends Alias<ValueT>> AliasT of(AliasKey<AliasT> key, ValueT value) {
            AliasT empty = key.unsafeEmptyAlias();
            empty.value = value;
            return empty;
        }

        public ValueT v() {
            return value;
        }

        @Override
        public String toString() {
            String className = this.getClass().getSimpleName();
            return String.format("%s[value=%s]", className, value);
        }
    }

    abstract static class AliasKey<AliasT extends Alias<?>> {
        protected abstract AliasT unsafeEmptyAlias();
    }

    static class FooId extends Alias<Long> {
        public static AliasKey<FooId> K = new FooIdKey();

        private static class FooIdKey extends AliasKey<FooId> {
            @Override
            protected FooId unsafeEmptyAlias() {return new FooId();}
        }
    }

    static class BarId extends Alias<String> {
        public static AliasKey<BarId> K = new BarIdKey();

        private static class BarIdKey extends AliasKey<BarId> {
            @Override
            protected BarId unsafeEmptyAlias() {return new BarId();}
        }
    }

    static class BazId extends Alias<String> {
        public static AliasKey<BazId> K = new BazIdKey();

        private static class BazIdKey extends AliasKey<BazId> {
            @Override
            protected BazId unsafeEmptyAlias() {return new BazId();}
        }
    }


    // --------------------------------------------------------


    @Test
    void test2() {
        Nt2<FooId, BarId> nt2 = Nt2.of(
                FooId.K, 42L,
                BarId.K, "Hallihallooo"
        );
        System.out.println(nt2);

//        long fooValue = Nt2.v1(FooId.K, nt2);
        long fooValue = Vl1.v(FooId.K, nt2);
        System.out.println("Foo value Nt2: " + fooValue);

//        String barValue = Nt2.v2(BarId.K, nt2);
        String barValue = Vl2.v(BarId.K, nt2);
        System.out.println("Bar value Nt2: " + barValue);

        /*
         * java: method v2 in class Nt2<A1,A2> cannot be applied to given types;
         *   required: AliasKey<A2>,Nt2<?,A2>
         *   found:    AliasKey<BazId>,Nt2<FooId,BarId>
         *   reason: inference variable A2 has incompatible equality constraints BarId,BazId
         */
//        String bazValue = Nt2.v2(BazId.K, nt2);


        useFoo(nt2);
        useBar(nt2);
        useFooBar(nt2);
    }

    private void useFoo(Vl1<FooId> foo) {
        long fooValue = Vl1.v(FooId.K, foo);
        System.out.println("Foo value Vl1: " + fooValue);
    }

    private void useBar(Vl2<BarId> bar) {
        String barValue = Vl2.v(BarId.K, bar);
        System.out.println("Bar value Vl2: " + barValue);
    }

    private <V extends Vl1<FooId> & Vl2<BarId>> void useFooBar(V fooBar) {
        long fooValue = Vl1.v(FooId.K, fooBar);
        System.out.println("Foo value Vl1 & Vl2: " + fooValue);

        String barValue = Vl2.v(BarId.K, fooBar);
        System.out.println("Bar value Vl1 & Vl2: " + barValue);
    }

    //    @ToString // todo proper impl
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static class Nt2<
            A1 extends Alias<?>,
            A2 extends Alias<?>
            > implements
            Vl1<A1>,
            Vl2<A2> {
        private final Object v1;
        private final Object v2;

        public static <
                V1, A1 extends Alias<V1>,
                V2, A2 extends Alias<V2>
                > Nt2<A1, A2> of(
                AliasKey<A1> k1, V1 v1,
                AliasKey<A2> k2, V2 v2
        ) {
            return new Nt2<>(v1, v2);
        }

        // todo delete -> Use Vl1.v
        public static <V1, A1 extends Alias<V1>> V1 v1(AliasKey<A1> k1, Nt2<A1, ?> nt2) {
            //noinspection unchecked
            return (V1) nt2.v1;
        }

        // todo delete -> Use Vl2.v
        public static <V2, A2 extends Alias<V2>> V2 v2(AliasKey<A2> k2, Nt2<?, A2> nt2) {
            //noinspection unchecked
            return (V2) nt2.v2;
        }

        @Override
        public Object untyped1() {
            return v1;
        }

        @Override
        public Object untyped2() {
            return v2;
        }
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static class Nt12<
            A1 extends Alias<?>,
            A2 extends Alias<?>,
            // todo 3 to 11
            A12 extends Alias<?>
            > implements
            Vl1<A1>,
            Vl2<A2>,
            Vl12<A12> {
        private final Object[] values = new Object[12];
        private final byte[] indices = new byte[12];

        @Override
        public Object untyped1() {
            byte index = indices[0];
            return values[index];
        }

        @Override
        public Object untyped2() {
            byte index = indices[1];
            return values[index];
        }

        @Override
        public Object untyped12() {
            byte index = indices[11];
            return values[index];
        }
    }

    interface Vl1<A1> {
        static <V1, A1 extends Alias<V1>> V1 v(AliasKey<A1> k1, Vl1<A1> vl1) {
            //noinspection unchecked
            return (V1) vl1.untyped1();
        }

        Object untyped1();
    }

    interface Vl2<A2> {
        static <V2, A2 extends Alias<V2>> V2 v(AliasKey<A2> k2, Vl2<A2> vl2) {
            //noinspection unchecked
            return (V2) vl2.untyped2();
        }

        Object untyped2();
    }

    interface Vl12<A12> {
        static <V12, A12 extends Alias<V12>> V12 v(AliasKey<A12> k12, Vl12<A12> vl12) {
            //noinspection unchecked
            return (V12) vl12.untyped12();
        }

        Object untyped12();
    }

    // todo will this be exposed
    protected interface Has2{
        byte index1();
        byte index2();
    }
    protected interface Has3 extends Has2{
        byte index3();
    }
    protected interface Has4 extends Has3{
        byte index4();
    }

    // todo can this be done in a unified manner
    static class Select2{}
    static class Select3{}
    static class Select12{}
}
