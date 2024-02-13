package dapg.control.result.boundary;

import dapg.control.result.Err;
import dapg.control.result.Ok;
import dapg.control.result.Result;
import dapg.control.result.boundary.context.ContextfulException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoundaryWithContextTest {
    private static final List<String> VALUES = List.of("3", "7", "12", "37", "42");
    private static final List<String> VALUES_WITH_INVALID_ELEMENT = List.of("3", "7", "12", "INVALID", "37", "42");

    @Test
    void shouldThrowException_whenPassingNullToConstructor() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new BoundaryWithContext<>(null));
    }

    // todo create proper tests with assertions

    @Test
    @Order(1)
    void testParseOrBreak() {
        print(parseOrBreak(VALUES));
        print(parseOrBreak(VALUES_WITH_INVALID_ELEMENT));
    }

    @Test
    @Order(2)
    void testParseOrBreakThrowable() {
        print(parseOrBreakThrowable(VALUES));
        print(parseOrBreakThrowable(VALUES_WITH_INVALID_ELEMENT));
    }

    @Test
    @Order(3)
    void testParseOrMapAndBreak() {
        print(parseOrMapAndBreak(VALUES));
        print(parseOrMapAndBreak(VALUES_WITH_INVALID_ELEMENT));
    }

    @Test
    @Order(4)
    void testParseFooBar() {
        print(parseFooBar(42));
        print(parseFooBar(123));
    }

    @Test
    @Order(5)
    void testContextfulExceptionWithMessage() {
        ContextfulException contextfulExceptionWithMessage = new ContextfulException("Test exception", C.id(42), C.state(DummyState.INITIAL), C.foo("dummy foo"), C.bar("dummy bar"));
        System.out.println(contextfulExceptionWithMessage.toString());
        System.out.println(contextfulExceptionWithMessage.getMessage());
        log.error("Test log ContextfulException", contextfulExceptionWithMessage);
    }

    @Test
    @Order(6)
    void testContextfulExceptionWithoutMessage() {
        ContextfulException contextfulExceptionWithoutMessage = new ContextfulException(C.id(42), C.state(DummyState.INITIAL), C.foo("dummy foo"), C.bar("dummy bar"));
        System.out.println(contextfulExceptionWithoutMessage.toString());
        System.out.println(contextfulExceptionWithoutMessage.getMessage());
        log.error("Test log ContextfulException", contextfulExceptionWithoutMessage);
    }

    private void print(Result<List<Integer>, ContextfulException> result) {
        switch (result) {
            case Ok(List<Integer> values) -> System.out.println("Ok: " + values);
            case Err(ContextfulException err) -> log.error("Ooops ¯\\_(°ペ)_/¯", err);
        }
    }

    //region parseOrBreak
    private Result<List<Integer>, ContextfulException> parseOrBreak(List<String> values) {
        return Result.<List<Integer>, ContextfulException>boundaryWithContext(
                throwable -> new ContextfulException("Cannot parse values", throwable),
                C.id(42), C.state(DummyState.INITIAL), C.foo("dummy foo"), C.bar("dummy bar")
        ).attempt(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithContextfulException(value).orBreak(boundary))
                .toList()
        );
    }

    private Result<Integer, ContextfulException> parseWithContextfulException(String value) {
        return Result.attempt(ContextfulException::new, () -> Integer.parseInt(value));
    }
    //endregion

    //region parseOrBreakThrowable
    private Result<List<Integer>, ContextfulException> parseOrBreakThrowable(List<String> values) {
        return Result.<List<Integer>, ContextfulException>boundaryWithContext(
                throwable -> new ContextfulException("Cannot parse values", throwable),
                C.id(42), C.state(DummyState.INITIAL), C.foo("dummy foo"), C.bar("dummy bar")
        ).attempt(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithThrowable(value).orBreakThrowable(boundary, err -> err))
                .toList()
        );
    }

    private Result<Integer, Throwable> parseWithThrowable(String value) {
        return Result.attempt(err -> err, () -> Integer.parseInt(value));
    }
    //endregion

    //region parseOrMapAndBreak
    private Result<List<Integer>, ContextfulException> parseOrMapAndBreak(List<String> values) {
        return Result.<List<Integer>, ContextfulException>boundaryWithContext(
                throwable -> new ContextfulException("Cannot parse values", throwable),
                C.id(42), C.state(DummyState.INITIAL), C.foo("dummy foo"), C.bar("dummy bar")
        ).attempt(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithString(value)
                        .mapErr(err -> new ContextfulException("Values contain element that cannot be parsed - %s".formatted(err)))
                        .orBreak(boundary)
                )
                .toList()
        );
    }

    private Result<Integer, String> parseWithString(String value) {
        return Result.attempt(Throwable::toString, () -> Integer.parseInt(value));
    }
    //endregion

    //region parseFooBar
    private Result<List<Integer>, ContextfulException> parseFooBar(int id) {
        return Result.<List<Integer>, ContextfulException>boundaryWithContext(
                throwable -> new ContextfulException("Cannot parse values", throwable),
                C.id(id), C.state(DummyState.INITIAL)
        ).attempt(boundary -> {
            log.info("Getting foo - {}", boundary.getContextAsString());
            Foo foo = getFoo(id);
            boundary.addToContext(C.foo(foo.foo()));

            log.info("Parsing bars - {}", boundary.getContextAsString());
            return StreamEx
                    .of(foo.bars())
                    .map(bar -> parseWithContextfulException(bar.value())
                            .orBreak(boundary.addContext(C.bar(bar.bar())))
                    )
                    .toList();
        });
    }

    private Foo getFoo(int id) {
        return switch (id) {
            case 42 -> new Foo("Awesome Foo", List.of(new Bar("Bar 1", "1"), new Bar("Bar 2", "2"), new Bar("Bar 3", "3")));
            default -> new Foo("Boring foo", List.of(new Bar("Bar 1", "1"), new Bar("Invalid Bar", "INVALID"), new Bar("Bar 3", "3")));
        };
    }
    //endregion

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class C { // context helper

        public static Map.Entry<String, Integer> id(int id) {
            return entry("id", id);
        }

        public static Map.Entry<String, DummyState> state(DummyState state) {
            return entry("state", state);
        }

        public static Map.Entry<String, String> foo(String foo) {
            return entry("foo", foo);
        }

        public static Map.Entry<String, String> bar(String bar) {
            return entry("bar", bar);
        }

        private static <V> Map.Entry<String, V> entry(String key, V value) {
            return new AbstractMap.SimpleEntry<>(key, value);
        }
    }

    private enum DummyState {
        INITIAL, PROCESSING, SUCCESS, FAILURE
    }

    private record Foo(
            String foo,
            List<Bar> bars
    ) {}

    private record Bar(
            String bar,
            String value
    ) {}
}