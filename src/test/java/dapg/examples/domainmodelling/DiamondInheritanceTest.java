package dapg.examples.domainmodelling;

import dapg.examples.domainmodelling.diamond.Logic;
import dapg.examples.domainmodelling.diamond.Logic.UserVerificationServiceImpl;
import org.junit.jupiter.api.Test;

import static dapg.examples.domainmodelling.diamond.UserDomain.UnverifiedUser;

public final class DiamondInheritanceTest {

    @Test
    void test() {
        UnverifiedUser unverifiedUser = UnverifiedUser.of("Julia Müller", 42);

        Logic.doStuffWithUnverifiedUser(unverifiedUser);

        // java: incompatible types: UnverifiedUser cannot be converted to VerifiedUser
//        Logic.doStuffWithVerifiedUser(unverifiedUser);

        UserVerificationServiceImpl verificationService = new UserVerificationServiceImpl();
        Logic.doStuffWithVerifiedUser(verificationService.verifyUser(unverifiedUser));

        /*
         * Value: UserImpl[name=Julia Müller, age=42]
         * Verifying user
         * Value: UserImpl[name=Julia Müller, age=42]
         *
         * Process finished with exit code 0
         */
    }


    // --------------------------------------------------------


    @Test
    void test2() {
        Aha aha = new Aha();
        System.out.println(aha.aha(FooKey.INSTANCE));
        System.out.println(aha.aha(BarKey.INSTANCE));
        System.out.println(aha.mu());
        aha.jo();
    }

    //    interface Duplicate<SelfT, KeyT> {
    interface Duplicate<SelfT> {
//    interface Duplicate {
//        SelfT aha(KeyT key);

        default void jo() {
            System.out.println("Wazzuuup");
        }
    }

    enum FooKey {INSTANCE}
    //    interface Foo extends Duplicate<Foo, FooKey> {
    interface Foo<SelfT extends Foo<SelfT>> extends Duplicate<SelfT> {
//    interface Foo extends Duplicate {
//        @Override
//        default Foo aha(FooKey key) {
//            return new Foo() {};
//        }

        default String aha(FooKey key) {
            return "Foo";
        }

        default int mu() {
            return 42;
        }

        /*
         * Prints: Gotcha
         * Otherwise (when commented out): Wazzuuup
         */
        @Override
        default void jo() {
            System.out.println("Gotcha");
        }
    }

    enum BarKey {INSTANCE}
    //    interface Bar extends Duplicate<Bar> {
    interface Bar<SelfT extends Bar<SelfT>> extends Duplicate<SelfT> {
//    interface Bar extends Duplicate {
//        @Override
//        default Bar aha(BarKey key) {
//            return new Bar() {};
//        }

        default String aha(BarKey key) {
            return "Bar";
        }

        /*
         * java: types Foo<SelfT> and Bar<SelfT> are incompatible;
         *   class Aha inherits unrelated defaults for mu() from types Foo and Bar
         */
//        default int mu() {
//            return 37;
//        }

        /*
         * java: types Foo<SelfT> and Bar<SelfT> are incompatible;
         *   class Aha inherits unrelated defaults for jo() from types Foo and Bar
         */
//        @Override
//        default void jo() {
//            System.out.println("WTF");
//        }
    }

    /*
     * interface Duplicate<SelfT>
     *
     * java: types Duplicate<Bar> and Foo are incompatible;
     *   both define aha(), but with unrelated return types
     */
    static class Aha implements Foo<Aha>, Bar<Aha> {}
}
