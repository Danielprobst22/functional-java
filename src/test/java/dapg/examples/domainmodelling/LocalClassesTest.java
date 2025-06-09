package dapg.examples.domainmodelling;

import org.junit.jupiter.api.Test;

public class LocalClassesTest {

    @Test
    void test() {
        doStuff();
    }

    void doStuff() {
        class FooBar implements Foo, Bar {}
        FooBar fooBar = new FooBar();
        doStuff(Context.of(fooBar));
    }

    <ValuesT extends Foo & Bar> void doStuff(Context<ValuesT> context) {
        System.out.println("Foo: " + context.values().foo());
        System.out.println("Bar: " + context.values().bar());

        class FooBarBaz implements Foo, Bar, Baz {}
        doAdditionalStuff(Context.of(new FooBarBaz()));
    }

    <ValuesT extends Foo & Bar & Baz> void doAdditionalStuff(Context<ValuesT> context) {
        System.out.println("Baz: " + context.values().baz());
    }


    //region Helper classes
    record Context<ValuesT>(ValuesT values) {
        public static <ValuesT> Context<ValuesT> of(ValuesT values) {
            return new Context<>(values);
        }
    }

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
}
