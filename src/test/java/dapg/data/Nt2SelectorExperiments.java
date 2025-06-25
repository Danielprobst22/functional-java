package dapg.data;

import java.util.function.Function;

//import static dapg.data.Nt2SelectorExperiments.AliasValueProvider.*;

// todo delete
public class Nt2SelectorExperiments<A1, A2> {

//    //    private static void test(Dummy1<String> dummy) {
//    private static void test(Dummy2<String, Long> dummy) {
//
//        Dummy1<String> copy1 = copy(dummy, v1());
//        Dummy1<Long> copy2 = copy(dummy, v2());
//
//        Dummy2<String, Long> copy3 = copy(dummy, v1(), v2());
//        Dummy2<Long, String> copy4 = copy(dummy, v2(), v1());
//
//        Dummy2<String, Integer> copy5 = copy(dummy, v1(), add(42));
//        Dummy2<Long, Integer> copy6 = copy(dummy, v2(), add(42));
//
//        Dummy2<String, String> copy7 = copy(dummy, v1(), mapV2(v -> "Value: " + v));
//    }
//
//
//    static <D extends Dummy, A1> Dummy1<A1> copy(
//            D dummy,
//            AliasValueProvider<D, A1> s1
//    ) {
////        return Dummy.of(
////                s1.apply(dummy)
////        );
//        return null;
//    }
//
//    static <D extends Dummy, A1, A2> Dummy2<A1, A2> copy(
//            D dummy,
//            AliasValueProvider<D, A1> s1,
//            AliasValueProvider<D, A2> s2
//    ) {
////        return Dummy.of(
////                s1.apply(dummy),
////                s2.apply(dummy)
////        );
//        return null;
//    }
//
//    private static void test(Nt2SelectorExperiments<String, Long> s) {
//        Dummy1<String> r1 = s.copy().v1().r();
//        Dummy1<Long> r2 = s.copy().v2().r();
//
//        Dummy2<String, Long> r3 = s.copy().v1().v2().r();
//        Dummy2<Long, String> r4 = s.copy().v2().v1().r();
//
//        Dummy2<String, String> r5 = s.copy().v1().v1().r();
//        Dummy2<Long, Long> r6 = s.copy().v2().v2().r();
//
//        Dummy1<Integer> r7 = s.copy().add(123).r();
//        Dummy2<Integer, String> r8 = s.copy().add(123).v1().r();
//        Dummy2<Integer, Long> r9 = s.copy().add(123).v2().r();
//    }
//
//    private Nt2Sel0<A1, A2> copy() {
//        return new Nt2Sel0<>();
//    }
//
//    static class Nt2Sel0<A1, A2> {
//
//        public Nt2Sel1<A1, A2, A1> v1() {
//            return null;
//        }
//
//        public Nt2Sel1<A1, A2, A2> v2() {
//            return null;
//        }
//
//        public <NewA> Nt2Sel1<A1, A2, NewA> add(NewA a) {
//            return null;
//        }
//    }
//
//    static class Nt2Sel1<A1, A2, S1> {
//
//        public Nt2Sel2<A1, A2, S1, A1> v1() {
//            return null;
//        }
//
//        public Nt2Sel2<A1, A2, S1, A2> v2() {
//            return null;
//        }
//
//        Dummy1<S1> r() {
//            return null;
//        }
//    }
//
//    static class Nt2Sel2<A1, A2, S1, S2> {
//
//        Dummy2<S1, S2> r() {
//            return null;
//        }
//    }
//
//
//    //region Stuff
////    interface Sel<NextValueT, NextSelectorT extends Sel<?>>
//
////    interface Sel<A1, A2, NextSel> {
////        Object untypedAdd(Object alias);
////
////        NextSel
////    }
////
//////    interface Nt2Sel0<A1, A2>{
//////        <SelA> addToSel0
//////    }
////
////
////    static class Selector1<S1, ResultT> {
////
//////        <S2> Selector2<S1, S2, Dummy2<S1, S2>> add(S2 s2) {
////        <S2> Selector2<S1, S2, Dummy2<S1, S2>> add(S2 s2) {
////            return null;
////        }
////    }
////
////    static class Selector2<S1, S2, ResultT> {
//////        Supplier<ResultT> complete;
////
////    /// /        Selector<A1, A2, ResultT> v1() {
////    /// /
////    /// /        }
////
////        <S3> Selector3<S1, S2, S3, Dummy3<S1, S2, S3>> add(S3 s3) {
////            return null;
////        }
////    }
////
////    static class Selector3<S1, S2, S3, ResultT> {
////
////    }
//    //endregion
//
//
//    //region AliasValueProvider
//    sealed interface AliasValueProvider<D extends Dummy, A> {
//        static <A1, D extends Dummy & Has1<A1>> AliasValueProvider<D, A1> v1() {
//            return new SelectAliasValue<>(1);
//        }
//        static <A2, D extends Dummy & Has2<A2>> AliasValueProvider<D, A2> v2() {
//            return new SelectAliasValue<>(2);
//        }
//
//
//        static <A1, NewA, D extends Dummy & Has1<A1>> AliasValueProvider<D, NewA> mapV1(Function<A1, NewA> mapper) {
//            return new MapAliasValue<>(1, mapper);
//        }
//        static <A2, NewA, D extends Dummy & Has2<A2>> AliasValueProvider<D, NewA> mapV2(Function<A2, NewA> mapper) {
//            return new MapAliasValue<>(2, mapper);
//        }
//
//
//        static <NewA, D extends Dummy> AliasValueProvider<D, NewA> add(NewA a) {
//            return new AddAliasValue<>(a);
//        }
//    }
//
//    record SelectAliasValue<D extends Dummy, A>(int index) implements AliasValueProvider<D, A> {
//        Object value(D dummy) {
//            return null;
//        }
//    }
//
//    record MapAliasValue<D extends Dummy, A>(int index, Function<?, A> mapper) implements AliasValueProvider<D, A> {
//        Object value(D dummy) {
//            return null;
//        }
//    }
//
//    record AddAliasValue<D extends Dummy, A>(Object value) implements AliasValueProvider<D, A> {}
//    //endregion
//
//    interface Dummy {
//        static <A1> Dummy1<A1> of(A1 a1) {
//            return null;
//        }
//
//        static <A1, A2> Dummy2<A1, A2> of(A1 a1, A2 a2) {
//            return null;
//        }
//    }
//    interface Dummy1<A1> extends Dummy, Has1<A1> {}
//    interface Dummy2<A1, A2> extends Dummy, Has1<A1>, Has2<A2> {}
//    interface Dummy3<A1, A2, A3> extends Dummy {}
//
//    interface Has1<A1> {
//        byte indexValue1();
//    }
//    interface Has2<A2> {
//        byte indexValue2();
//    }
}
