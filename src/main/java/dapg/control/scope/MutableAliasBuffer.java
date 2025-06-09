package dapg.control.scope;

import java.util.concurrent.ThreadLocalRandom;

public class MutableAliasBuffer {

    interface Scope {}

//    enum Gl implements Scope {INSTANCE}

    // todo just for testing
    enum Gl implements ScopeWith<IdDomain.CustomId, Gl> {
        INSTANCE;

        @Override
        public Object get(AliasKey<IdDomain.CustomId, Gl> key) {
            return null;
        }
    }

    static class ScopeLabel {}
//    private record SomeDomain.ScKey<ScopeT extends Scope>(SomeDomain.GlKey unique, ScopeLabel label, int index) implements AliasKey<ScopeT, SomeDomain.SomeAlias> {}

    interface ScopeWith<AliasT extends Alias<AliasT>, SelfT extends ScopeWith<AliasT, SelfT>> {
//        <AliasT extends Alias<AliasT>> AliasKey<Sc, AliasT> put()

//        SelfT self();

        //        <ValueT> ValueT get(AliasKey<AliasT, SelfT> key);
        Object get(AliasKey<AliasT, SelfT> key);

//        default <ValueT> ValueT get(AliasKey<AliasT, SelfT> key) {
//            return fetch.apply(self());
//        }
    }

//    interface InScope<ScopeT extends Scope, SelfT extends Alias<SelfT> & InScope<ScopeT, SelfT>> {
//        AliasKey<SelfT, ScopeT> key();
//    }

    //    interface HasAlias<SelfT extends Alias<SelfT>> {
    interface Alias<SelfT extends Alias<SelfT>> {
//        Object value();
    }

    interface AliasKey<AliasT extends Alias<AliasT>, ScopeT extends ScopeWith<AliasT, ScopeT>> {
        static int randomSalt() {return ThreadLocalRandom.current().nextInt();}
    }

    static class IdDomain {
        private enum GlKey implements AliasKey<CustomId, Gl> {INSTANCE}
        private record ScKey<ScopeT extends ScopeWith<CustomId, ScopeT>>(GlKey unique, int salt) implements AliasKey<CustomId, ScopeT> {}

        // todo
//        interface CanHaveCustomId extends Alias<CustomId> {

        interface HasCustomId extends Alias<CustomId> {
            static AliasKey<CustomId, Gl> gl() {return GlKey.INSTANCE;}
            static <ScopeT extends ScopeWith<CustomId, ScopeT>> AliasKey<CustomId, ScopeT> sc() {return new ScKey<>(GlKey.INSTANCE, AliasKey.randomSalt());}

            //            default <ScopeT extends Scope & InScope<ScopeT, CustomId>> long inferType(ScopeT scope) {
//            default <ScopeT extends ScopeWith<CustomId>> long fetch(ScopeT scope) {
//            default <ScopeT extends ScopeWith<CustomId, ScopeT>> long fetch(ScopeT scope) {
//                return (long) scope.get(key());
//            }

        }

        interface CustomId extends Alias<CustomId> {

            <ScopeT extends ScopeWith<CustomId, ScopeT>> AliasKey<CustomId, ScopeT> key();
        }
    }

    // --------------------------------------------------------

    private static class Sc implements Scope {
//    private static class Sc implements IdDomain.CustomId {
//        private final Map<AliasKey<?, Sc>, Object> state = new LinkedHashMap<>();

//        <AliasT extends Alias<AliasT>> AliasKey<Sc, AliasT> put()
    }
}
