package dapg.control.scope;

import io.vavr.Tuple2;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ScopeStuff {

    interface Scope {}

    interface InScope<ScopeT extends Scope> {
//        <AliasT extends Alias<ScopeT>> AliasT get(AliasKey<ScopeT, AliasT> key);
    }


    interface Alias<ScopeT extends Scope, SelfT extends Alias<ScopeT, SelfT>> extends InScope<ScopeT> {
        <ValueT> ValueT value();

        AliasKey<ScopeT, SelfT> key();
    }

    interface AliasKey<ScopeT extends Scope, AliasT extends Alias<ScopeT, AliasT>> {
        static int randomSalt() {return ThreadLocalRandom.current().nextInt();}
    }

    //    interface InScope<ScopeT extends Scope, ValueT> {}
//    interface HasAlias<ScopeT extends Scope, AliasT> {
//        AliasKey<ScopeT, AliasT> key();
//    }


    // Global scope
    enum Gl implements Scope {INSTANCE}

    // Local scope defined where needed
    // Private inner class
    private static class Sc implements Scope {}



    //    class FooId extends Alias<>
    static class FooDomain {
//        private enum GlKey implements AliasKey<Gl, FooId> {INSTANCE}
//        private record ScKey<ScopeT extends Scope>(GlKey unique, int salt) implements AliasKey<ScopeT, FooId> {}
//
//        interface FooId<ScopeT extends Scope> {
//
//            static AliasKey<Gl, FooId> gl() {return GlKey.INSTANCE;}
//            static <ScopeT extends Scope> AliasKey<ScopeT, FooId> sc() {return new ScKey<>(GlKey.INSTANCE, AliasKey.randomSalt());}
//
//            default long value(ScopeT scope) {
//                scope.
//            }
//
//            AliasKey<ScopeT, FooId> key();
//
//
//        }
    }

    // --------------------------------------------------------


//    interface ToCtx2<
//            T1 extends HasAlias<Gl, T1>,
//            T2 extends HasAlias<Gl, T2>,
//            ProofT1 extends T1,
//            ProofT2 extends T2
//            > {
//
//    }


    //    <ValuesT extends Foo & Bar & Ctx2> void doStuff(Context<ValuesT> context) {
    <ValuesT extends Foo & Bar> void doStuff(Context<ValuesT> context) {
//        class FooBar implements Foo, Bar, ToCtx2<Foo, Bar, FooBar, FooBar> {}
//        class FooBar implements Foo, Baz, ToCtx2<Foo, Bar, FooBar, FooBar> {}

    }


    //region Helper classes
    interface Foo {
        default String foo() {
            return "I'm Foo";
        }
    }

    interface Bar {
        default int bar() {
            return 42;
        }
    }

    interface Baz {
        default double baz() {
            return 3.7;
        }
    }
    //endregion


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Context<ValuesT> {
        private final ValuesT values;

        public static <ValuesT> Context<ValuesT> of(ValuesT values) {
            return new Context<>(values);
        }

        ValuesT values() {
            return values;
        }

//        <T, AdditionalT> Context<? extends T & AdditionalT> add()
    }

    //    static final class Ctx2<T1, T2, SelfT extends T1 & T2> {
//    interface Ctx2<T1, T2, SelfT extends Has<T1> & Has<T2>> {
    interface Ctx2<T1, T2, ProofT1 extends T1, ProofT2 extends T2> {
        //        public static <T1, T2, ValueT extends T1> Ctx2<T1, T2> of(ValueT value) {
//        public static <T1, T2, ValueT extends Has<T1> & Has<T2>> void of(ValueT) {

//        default <T1, T2, AddT3> Ctx3<T1, T2, AddT3> add(Key<T1> key1, Key<T2> key2, AddT3 additionalValue) {
//
//        }

        Tuple2<T1, T2> types();
    }

    interface Ctx3<T1, T2, T3> {

        static <T1, T2, T3> Ctx3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
            Map<AliasKey<Gl, ?>, Object> state = new LinkedHashMap<>();
            // todo proper impl - Use global keys
            state.put(null, t1);
            state.put(null, t2);
            state.put(null, t3);

            // todo proper impl - Cast result
            Ctx3<T1, T2, T3> ctx3 = key -> state.get(key);

            return ctx3;
        }

        Object get(AliasKey<Gl, ?> key);
    }


//    static class UnsafeLogContext implements Foo, Bar, Baz {

//        <T1, T2> Context<? extends T1 & T2> aha() {
//        <T1, T2, ValuesT extends T1 & T2> Context<ValuesT> test() {
//        <ValuesT, T1 super ValuesT, T2> Context<? extends T1 & T2> aha() {

//        <T1, T2> Context<?> aha() {
//            (Foo & Bar) result = null;
//        }
//    }

    static class Test {
        class Key {}

        public Key newKey() {
            return new Key();
        }

        public String authoize(Key key) {
            return "OK";
        }
    }

    void aha() {
        Test test_1 = new Test();
        Test.Key key_1 = test_1.newKey();

        Test test_2 = new Test();
        Test.Key key_2 = test_2.newKey();

        test_1.authoize(key_1);
        test_1.authoize(key_2);
    }
}
