package dapg.examples.domainmodelling;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BuilderTest {

    @Test
    void test() {
//        FooBar
//                .builder()
//                .putFoo("Hallihallooo")
//                .putBar(42)
//                .build()
    }

    //    static class FooBarBuilder<CurrentStageT> implements
//            FooStage<BarStage<?>>,
//            BarStage<CompletionStage<?>>,
//            CompletionStage<FooBar> {
//    static class FooBarBuilder<CurrentStageT> implements
//            FooStage<FooBarBuilder<BarStage>>,
//            BarStage<FooBarBuilder<CompletionStage>>,
//            CompletionStage<FooBar>,
//            BuilderStage {
//        // todo Map
//
//        <NextStageT> FooBarBuilder<NextStageT> s(Function<CurrentStageT, NextStageT> putValue) {
//            return putValue.apply()
//        }
//
//        //        @Override
//        public FooBarBuilder<BarStage> putFoo(String foo) {
//            System.out.println("Put foo: " + foo);
//            return (FooBarBuilder<BarStage>) this;
//        }
//
////        @Override
////        public BarStage<CompletionStage<?>> putFoo(String foo) {
////            System.out.println("Put foo: " + foo);
////            return this;
////        }
//
//        //        @Override
//        public FooBarBuilder<CompletionStage> putBar(int bar) {
//            System.out.println("Put bar: " + bar);
//            return this;
//        }
//
    /// /        @Override
    /// /        public CompletionStage<FooBar> putBar(int bar) {
    /// /            System.out.println("Put bar: " + bar);
    /// /            return this;
    /// /        }
//
//        @Override
//        public FooBar build() {
//            System.out.println("Building FooBar");
//            return new FooBar();
//        }
//    }

//    static class FooBar implements Builder<FooBarBuilder> {
//        public static FooBarBuilder builder() {
//            return new FooBarBuilder();
//        }
//    }


    // --------------------------------------------------------


    static class FooBar implements HasFoo<FooBar>, HasBar<FooBar> {

        private static class FooBarBuilder
                extends Builder2<FooBar, FooBarBuilder, FooStage, BarStage>
                implements Builder<FooBar>,
                FooStage<FooBar, FooBarBuilder>,
                BarStage<FooBar, FooBarBuilder>
        {
            private final Map<AliasKey<?>, Object> values = new LinkedHashMap<>();

            @Override
            public void put(Alias<?, ?> alias){
                values.put(alias.key(), alias.v());
            }

            @Override
            protected FooBar complete() {
                return null;
            }
        }
    }


    // --------------------------------------------------------


    interface Builder<ResultT> {
//        <ValueT, AliasT extends Alias<AliasT, ValueT>> void put(AliasKey<AliasT> key, ValueT value);
        void put(Alias<?, ?> alias);
    }

//    interface BuilderStage {}

//    interface CompletionStage<ResultT> extends BuilderStage {
//        ResultT build();
//    }

    abstract static class Builder2<
            ResultT,
            SelfT extends Builder<ResultT>,
//            Stage1 extends SelfT,
            Stage1,
//            Stage2 extends SelfT
            Stage2
            > {
        public ResultT build(
                Function<? super Stage1, SelfT> putV1,
                Function<? super Stage2, SelfT> putV2
        ) {
            putV1.apply((Stage1) this); // todo check cast
            putV2.apply((Stage2) this);
            return complete();
        }

        protected abstract ResultT complete();
    }


//    interface FooStage<NextStageT extends BuilderStage> extends BuilderStage {
//        NextStageT putFoo(String foo);
//    }
//    interface HasFoo<SelfT extends HasFoo<SelfT> & Builder<? extends FooStage<?>>> {}
//
//
//    interface BarStage<NextStageT extends BuilderStage> extends BuilderStage {
//        NextStageT putBar(int bar);
//    }
//    interface HasBar<SelfT extends HasBar<SelfT> & Builder<? extends BarStage<?>>> {}


    interface FooStage<ResultT extends HasFoo<ResultT>, SelfT extends Builder<ResultT>> extends Builder<ResultT> {
        default SelfT putFoo(FooId foo) {
            put(foo);
            return (SelfT) this; // todo proper impl - return different ProofT
        }
    }
    //    interface HasFoo<SelfT extends HasFoo<SelfT> & Builder<? extends FooStage<SelfT, ?>>> {}
    interface HasFoo<SelfT extends HasFoo<SelfT>> {}


    interface BarStage<ResultT extends HasBar<ResultT>, SelfT extends Builder<ResultT>> extends Builder<ResultT> {
        default SelfT putBar(BarId bar) {
            put(bar);
            return (SelfT) this; // todo proper impl - return different ProofT
        }
    }
    //    interface HasBar<SelfT extends HasBar<SelfT> & Builder<? extends BarStage<?>>> {}
    interface HasBar<SelfT extends HasBar<SelfT>> {}


    // --------------------------------------------------------


    // todo is SelfT really required
    abstract static class Alias<SelfT extends Alias<SelfT, ?>, ValueT> {
        protected ValueT value;

        public static <ValueT, AliasT extends Alias<AliasT, ValueT>> AliasT of(AliasKey<AliasT> key, ValueT value) {
            AliasT empty = key.unsafeEmptyAlias();
            empty.value = value;
            return empty;
        }

        public abstract AliasKey<SelfT> key();

        public ValueT v() {
            return value;
        }

        @Override
        public String toString() {
            String className = this.getClass().getSimpleName();
            return String.format("%s[value=%s]", className, value);
        }
    }

    abstract static class AliasKey<AliasT extends Alias<AliasT, ?>> {
        protected abstract AliasT unsafeEmptyAlias();
    }


    // --------------------------------------------------------


    static class FooId extends Alias<FooId, Long> {
        public static AliasKey<FooId> K = new FooIdKey();

        @Override
        public AliasKey<FooId> key() {
            return K;
        }

        private static class FooIdKey extends AliasKey<FooId> {
            @Override
            protected FooId unsafeEmptyAlias() {return new FooId();}
        }
    }

    static class BarId extends Alias<BarId, String> {
        public static AliasKey<BarId> K = new BarIdKey();

        @Override
        public AliasKey<BarId> key() {
            return K;
        }

        private static class BarIdKey extends AliasKey<BarId> {
            @Override
            protected BarId unsafeEmptyAlias() {return new BarId();}
        }
    }

    static class BazId extends Alias<BazId, String> {
        public static AliasKey<BazId> K = new BazIdKey();

        @Override
        public AliasKey<BazId> key() {
            return K;
        }

        private static class BazIdKey extends AliasKey<BazId> {
            @Override
            protected BazId unsafeEmptyAlias() {return new BazId();}
        }
    }
}
