package dapg.examples.domainmodelling;

import dapg.control.result.Result;
import org.junit.jupiter.api.Test;

public class StuffTest {

    @Test
    void test() {
//        Data validData = new Data(new AwesomeValue());
//        Data validData2 = new Data(new DifferentAwesomeValue());
////        Data invalidData = new Data(new InvalidValue());
//        System.out.println("AwesomeValue: " + validData);
//        System.out.println("DifferentAwesomeValue: " + validData2);
    }

    public interface Valid<T> {
        Result<Valid<T>, Throwable> validate();
    }

    record Foo(String value) implements Valid<Foo> {
        @Override
        public Result<Valid<Foo>, Throwable> validate() {
            return Result.when(
                    value.length() < 42,
                    () -> this,
                    () -> new IllegalArgumentException("Value is longer than 42 characters")
            );
        }
    }

    record Bar(int value) implements Valid<Bar> {
        @Override
        public Result<Valid<Bar>, Throwable> validate() {
            return Result.when(
                    value >= 0,
                    () -> this,
                    () -> new IllegalArgumentException("Value is less than 0")
            );
        }
    }

    record Data(
            Valid<Foo> foo,
            Valid<Bar> bar
    ) {}
}
