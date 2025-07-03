package dapg.data;

import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.addons.alias.AliasWithStructAccessor;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.product.api.NmStruct;
import dapg.data.alias.product.api.NmTuple;
import dapg.data.alias.product.api.addons.key.AliasKeyWithFactory;
import dapg.data.alias.product.api.markertraits.nm.Nm2;
import dapg.data.alias.product.api.markertraits.nm.Nm3;
import dapg.data.alias.product.impl.struct.NmStruct2;
import dapg.data.alias.product.impl.struct.NmStructAccessor;
import dapg.data.alias.product.impl.struct.constructor.NmStruct2Constructor;
import dapg.data.alias.product.impl.tuple.NmTup2;
import dapg.data.alias.product.impl.tuple.NmTup3;
import dapg.data.alias.value.free.Vl;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;
import dapg.data.alias.value.indexed.Vl3;
import org.junit.jupiter.api.Test;

import static dapg.data.alias.product.api.Nm.*;

class AliasAndTupleStuffTest {

    // todo just for testing

    @Test
    void test() {
        useFooBarValues(FooId.K.make(42L), BarId.K.make("bar-37"));
        useFooBarValuesExtensionMethods(FooId.K.make(42L), BarId.K.make("bar-37"));
    }

    @Test
    void test2() {
        NmTup2<FooId, BarId> fooBar = NmTuple.of(FooId.K, 42L, BarId.K, "bar-37");

        useFooVl1(fooBar);
        useBarVl2(fooBar);
        useFooBarTuple(fooBar);
        useFooBarValues(fooBar.v1ToVl(), fooBar.v2ToVl());

        useFooBarValuesExtensionMethods(fooBar.v1ToVl(), fooBar.v2ToVl());
        useFooBarNm2WithExtensionMethods(fooBar);

        NmTup2<BarId, FooId> barFoo = Nm.copy(fooBar, v2(), v1());
        useFooBarValues(barFoo.v2ToVl(), barFoo.v1ToVl());

        NmTup3<FooId, BarId, BazId> fooBarBaz = copy(fooBar, v1(), v2(), add(BazId.K, 37));
        useFooBarBazValues(fooBarBaz.v1ToVl(), fooBarBaz.v2ToVl(), fooBarBaz.v3ToVl());

        NmTup3<BazId, FooId, BarId> bazFooBar = copy(fooBar, add(BazId.K, 37), v1(), v2());
        useFooBarBazValues(bazFooBar.v2ToVl(), bazFooBar.v3ToVl(), bazFooBar.v1ToVl());

        useFooBarNm2(fooBar);
        useFooBarNm2(fooBarBaz);
        useFooBarBazNm3(fooBarBaz);

        System.out.println("NmTup2 arity: " + fooBar.arity());
        System.out.println("NmTup3 arity: " + fooBarBaz.arity());
    }

    @Test
    void test3() {
        FooBar fooBar = NmStruct.make(FooBar.C, NmTuple.of(FooId.K, 42L, BarId.K, "bar-37"));

        useFooVl1(fooBar);
        useBarVl2(fooBar);
        useFooBarTuple(fooBar);

        useFooBarNm2(fooBar);
        useFooBarValues(fooBar.v1ToVl(), fooBar.v2ToVl());

        useHasFoo(fooBar);
        useHasBar(fooBar);
        useFooBarStruct(fooBar);

        System.out.println("NmStruct2 arity: " + fooBar.arity());
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

    private void useFooBarNm2(Nm2<FooId, BarId> fooBar) {
        long fooValue = Vl1.v(FooId.K, fooBar);
        System.out.println("Foo value Nm2: " + fooValue);

        String barValue = Vl2.v(BarId.K, fooBar);
        System.out.println("Bar value Nm2: " + barValue);
    }

    private void useFooBarNm2WithExtensionMethods(Nm2<FooId, BarId> fooBar) {
        long fooValue = FooId.K.v1(fooBar);
        System.out.println("Foo value Nm2 ext: " + fooValue);

        String barValue = BarId.K.v2(fooBar);
        System.out.println("Bar value Nm2 ext: " + barValue);
    }

    private void useFooBarBazNm3(Nm3<FooId, BarId, BazId> fooBarBaz) {
        long fooValue = Vl1.v(FooId.K, fooBarBaz);
        System.out.println("Foo value Nm3: " + fooValue);

        String barValue = Vl2.v(BarId.K, fooBarBaz);
        System.out.println("Bar value Nm3: " + barValue);

        int bazValue = Vl3.v(BazId.K, fooBarBaz);
        System.out.println("Baz value Nm3: " + bazValue);
    }

    private void useFooBarValues(Vl<FooId> foo, Vl<BarId> bar) {
        long fooValue = Vl.v(FooId.K, foo);
        System.out.println("Foo value Vl: " + fooValue);

        String barValue = Vl.v(BarId.K, bar);
        System.out.println("Bar value Vl: " + barValue);
    }

    private void useFooBarValuesExtensionMethods(Vl<FooId> foo, Vl<BarId> bar) {
        long fooValue = FooId.K.vl(foo);
        System.out.println("Foo value Vl ext: " + fooValue);

        String barValue = BarId.K.vl(bar);
        System.out.println("Bar value Vl ext: " + barValue);
    }

    private void useFooBarBazValues(Vl<FooId> foo, Vl<BarId> bar, Vl<BazId> baz) {
        long fooValue = Vl.v(FooId.K, foo);
        System.out.println("Foo value Vl: " + fooValue);

        String barValue = Vl.v(BarId.K, bar);
        System.out.println("Bar value Vl: " + barValue);

        int bazValue = Vl.v(BazId.K, baz);
        System.out.println("Baz value Vl: " + bazValue);
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


    // --------------------------------------------------------


    public static class FooId implements AliasWithStructAccessor<HasFooId, Long> {
        public static FooIdKey K = FooIdKey.INSTANCE;
        public enum FooIdKey implements AliasKeyWithFactory<FooId, Long> { INSTANCE }
    }
    public interface HasFooId extends NmStructAccessor {
        default long fooId() { return NmStructAccessor.v(FooId.K, this); }
    }

    public static class BarId implements AliasWithStructAccessor<HasBarId, String> {
        public static BarIdKey K = BarIdKey.INSTANCE;
        public enum BarIdKey implements AliasKeyWithFactory<BarId, String> { INSTANCE }
    }
    public interface HasBarId extends NmStructAccessor {
        default String barId() { return NmStructAccessor.v(BarId.K, this); }
    }

    public static class BazId implements AliasWithStructAccessor<HasBazId, Integer> {
        public static AliasKey<BazId> K = BazIdKey.INSTANCE;
        private enum BazIdKey implements AliasKey<BazId> { INSTANCE }
    }
    public interface HasBazId extends NmStructAccessor {
        default int bazId() { return NmStructAccessor.v(BazId.K, this); }
    }


    public static final class FooBar
            extends NmStruct2<FooId, BarId>
            implements HasFooId,
                       HasBarId {
        public static NmStruct2Constructor<FooBar, FooId, BarId> C = FooBar::new;
        public static AliasKey<FooBar> K = FooBarKey.INSTANCE;

        private enum FooBarKey implements AliasKey<FooBar> { INSTANCE } // todo extend StructAliasKey

        private FooBar(AliasKey<?>[] keys, Object[] values, byte[] indices) {
            super(keys, values, indices);
        }
    }
}
