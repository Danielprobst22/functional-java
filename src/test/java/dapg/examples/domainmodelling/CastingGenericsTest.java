package dapg.examples.domainmodelling;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CastingGenericsTest {

    public static void main(String[] args) {
        FooBar fooBar = new FooBar("Halihallooo", 42);
        System.out.println("selFoo with BAR" + fooBar.selFoo().value(FooBar.FooBarIdentifier.BAR));
    }

    @Test
    void test() {
        FooBar fooBar = new FooBar("Halihallooo", 42);

        String foo = fooBar.selFoo().value(FooBar.FooBarIdentifier.FOO);
        System.out.println("Foo: " + foo);

        long bar = fooBar.selBar().value(FooBar.FooBarIdentifier.BAR);
        System.out.println("Bar: " + bar);


//        String selFooWithBar = fooBar.selFoo().value(FooBar.FooBarIdentifier.BAR); // throws ClassCastException: class Long cannot be cast to class String
        Object selFooWithBar = fooBar.selFoo().value(FooBar.FooBarIdentifier.BAR);
        System.out.println("selFoo with BAR: " + selFooWithBar);

        // Calling value(FooBar.FooBarIdentifier.BAR) directly in println also throws ClassCastException: class Long cannot be cast to class String
        // -> Appending String value probably uses optimized string concatenation
        // -> Produces a ClassCastException for Long value believed to be a "String"
//        System.out.println("selFoo with BAR directly in println: " +  fooBar.selFoo().value(FooBar.FooBarIdentifier.BAR));

        assertThrows(ClassCastException.class, () -> {
            String trySelFooWithBar = fooBar.selFoo().value(FooBar.FooBarIdentifier.BAR);
        });


//        long selBarWithFoo = fooBar.selBar().value(FooBar.FooBarIdentifier.FOO); // throws ClassCastException: class String cannot be cast to class Long
        Object selBarWithFoo = fooBar.selBar().value(FooBar.FooBarIdentifier.FOO);
        System.out.println("selBar with FOO: " + selBarWithFoo);

        // Calling value(FooBar.FooBarIdentifier.FOO) directly in println does work
        // -> Appending Long value probably defaults to string concatenation with type Object
        // -> Works fine for String value believed to be a "Long"
        System.out.println("selBar with FOO directly in println: " + fooBar.selBar().value(FooBar.FooBarIdentifier.FOO));

        assertThrows(ClassCastException.class, () -> {
            long trySelBarWithFoo = fooBar.selBar().value(FooBar.FooBarIdentifier.FOO);
        });
    }

    private interface Property<IdentifierT, ValueT> {
        ValueT value(IdentifierT identifier);
    }

    @RequiredArgsConstructor
    private static class FooBar implements Property<FooBar.FooBarIdentifier, Object> {
        private final String foo;
        private final long bar;

        public enum FooBarIdentifier {FOO, BAR}

        public Property<FooBarIdentifier, String> selFoo() {
            //noinspection unchecked
            return (Property<FooBarIdentifier, String>) (Property<FooBarIdentifier, ?>) this;
        }

        public Property<FooBarIdentifier, Long> selBar() {
            //noinspection unchecked
            return (Property<FooBarIdentifier, Long>) (Property<FooBarIdentifier, ?>) this;
        }

        @Override
        public Object value(FooBarIdentifier identifier) {
            return switch (identifier) {
                case FooBarIdentifier.FOO -> foo;
                case FooBarIdentifier.BAR -> bar;
            };
        }
    }
}
