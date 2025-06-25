package dapg.data;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.AliasWithStructAccessor;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.product.api.NmStruct;
import dapg.data.alias.product.api.NmTuple;
import dapg.data.alias.product.impl.struct.NmStruct2;
import dapg.data.alias.product.impl.struct.NmStructAccessor;
import dapg.data.alias.product.impl.struct.constructor.NmStruct2Constructor;
import dapg.data.alias.product.impl.tuple.NmTup2;
import dapg.data.alias.value.free.Vl;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static dapg.data.alias.product.api.Nm.v1;
import static dapg.data.alias.product.api.Nm.v2;

class AliasAndTupleStuffTest {

    // todo just for testing

    @Test
    void test() {
        NmTup2<FooId, BarId> fooBar = NmTuple.of(FooId.K, 42L, BarId.K, "bar-37");

        useFooVl1(fooBar);
        useBarVl2(fooBar);
        useFooBarTuple(fooBar);
        useFooBarValues(fooBar.v1ToVl(), fooBar.v2ToVl());

        NmTup2<BarId, FooId> barFoo = Nm.copy(fooBar, v2(), v1());

        // todo just for testing
        fooBar.untypedValue1(FooId.K);
        fooBar.untypedValue2(BarId.K);
    }

    @Test
    void test2() {
        FooBar fooBar = NmStruct.make(FooBar.C, NmTuple.of(FooId.K, 42L, BarId.K, "bar-37"));

        useFooVl1(fooBar);
        useBarVl2(fooBar);
        useFooBarTuple(fooBar);

        useFooBarValues(fooBar.v1ToVl(), fooBar.v2ToVl());

        useHasFoo(fooBar);
        useHasBar(fooBar);
        useFooBarStruct(fooBar);
    }

    private void useFooVl1(Vl1<FooId> foo) {
        long fooValue = Vl1.v(FooId.K, foo);
        System.out.println("Foo value Vl1: " + fooValue);
    }

    private void useBarVl2(Vl2<BarId> bar) {
        String barValue = Vl2.v(BarId.K, bar);
        System.out.println("Bar value Vl2: " + barValue);
    }

    private <
            FooBarTupleT extends Vl1<FooId> & Vl2<BarId>
            > void useFooBarTuple(FooBarTupleT fooBar) {
        long fooValue = Vl1.v(FooId.K, fooBar);
        System.out.println("Foo value Vl1 & Vl2: " + fooValue);

        String barValue = Vl2.v(BarId.K, fooBar);
        System.out.println("Bar value Vl1 & Vl2: " + barValue);
    }

    private void useFooBarValues(Vl<FooId> foo, Vl<BarId> bar) {
        long fooValue = Vl.v(FooId.K, foo);
        System.out.println("Foo value Vl: " + fooValue);

        String barValue = Vl.v(BarId.K, bar);
        System.out.println("Bar value Vl: " + barValue);
    }

    private void useHasFoo(HasFooId foo) {
        System.out.println("Foo value HasFooId: " + foo.fooId());
    }

    private void useHasBar(HasBarId bar) {
        System.out.println("Bar value HasBarId: " + bar.barId());
    }

    private <
            FooBarStructT extends HasFooId & HasBarId
            > void useFooBarStruct(FooBarStructT fooBar) {
        System.out.println("Foo value in struct: " + fooBar.fooId());
        System.out.println("Bar value in struct: " + fooBar.barId());
    }


    @RequiredArgsConstructor
    static class Aha implements Vl1<Aha>, Alias<String> {
        private final String value;

        @Override
//        public Object untypedValue1(AliasKey<?> key) {
        public Object untypedValue1(AliasKey<Aha> key) {
            return value;
        }
    }

    // --------------------------------------------------------


    public static class FooId implements AliasWithStructAccessor<HasFooId, Long> {
        public static AliasKey<FooId> K = FooIdKey.INSTANCE;

        private enum FooIdKey implements AliasKey<FooId> { INSTANCE }
    }
    public interface HasFooId extends NmStructAccessor {
        default long fooId() { return NmStructAccessor.v(FooId.K, this); }
    }

    public static class BarId implements AliasWithStructAccessor<HasBarId, String> {
        public static AliasKey<BarId> K = BarIdKey.INSTANCE;

        private enum BarIdKey implements AliasKey<BarId> { INSTANCE }
    }
    public interface HasBarId extends NmStructAccessor {
        default String barId() { return NmStructAccessor.v(BarId.K, this); }
    }


    public static final class FooBar
            extends NmStruct2<FooId, BarId>
            implements HasFooId,
                       HasBarId
    {
        public static NmStruct2Constructor<FooBar, FooId, BarId> C = FooBar::new;
        public static AliasKey<FooBar> K = FooBarKey.INSTANCE;

        private enum FooBarKey implements AliasKey<FooBar> { INSTANCE } // todo extend StructAliasKey

        private FooBar(Object[] values, AliasKey<?>[] keys, byte[] indices) {
            super(values, keys, indices);
        }
    }
}
