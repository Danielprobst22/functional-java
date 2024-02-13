package dapg.control.result.boundary;

import dapg.control.result.Err;
import dapg.control.result.Ok;
import dapg.control.result.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoundaryTest {
    private static final List<String> VALUES = List.of("3", "7", "12", "37", "42");
    private static final List<String> VALUES_WITH_INVALID_ELEMENT = List.of("3", "7", "12", "INVALID", "37", "42");

    @Test
    void shouldThrowException_whenPassingNullToConstructor() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new Boundary<>(null));
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

    private void print(Result<List<Integer>, Exception> result) {
        switch (result) {
            case Ok(List<Integer> values) -> System.out.println("Ok: " + values);
            case Err(Exception err) -> log.error("Ooops ¯\\_(°ペ)_/¯", err);
        }
    }

    //region parseOrBreak
    private Result<List<Integer>, Exception> parseOrBreak(List<String> values) {
        return Result.<List<Integer>, Exception>boundary(
                throwable -> new IllegalArgumentException("Cannot parse values", throwable)
        ).attempt(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithException(value).orBreak(boundary))
                .toList()
        );
    }

    private Result<Integer, IllegalArgumentException> parseWithException(String value) {
        return Result.attempt(IllegalArgumentException::new, () -> Integer.parseInt(value));
    }
    //endregion

    //region parseOrBreakThrowable
    private Result<List<Integer>, Exception> parseOrBreakThrowable(List<String> values) {
        return Result.<List<Integer>, Exception>boundary(
                throwable -> new IllegalArgumentException("Cannot parse values", throwable)
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
    private Result<List<Integer>, Exception> parseOrMapAndBreak(List<String> values) {
        return Result.<List<Integer>, Exception>boundary(
                throwable -> new IllegalArgumentException("Cannot parse values", throwable)
        ).attempt(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithString(value)
                        .mapErr(err -> new IllegalArgumentException("Values contain element that cannot be parsed - %s".formatted(err)))
                        .orBreak(boundary)
                )
                .toList()
        );
    }

    private Result<Integer, String> parseWithString(String value) {
        return Result.attempt(Throwable::toString, () -> Integer.parseInt(value));
    }
    //endregion

    //region DummyError
    sealed interface DummyError {
        @Getter
        @RequiredArgsConstructor
        sealed class ParentError implements DummyError {
            protected final String message;

            @Override
            public String toString() {
                return "ParentError: " + message;
            }
        }

        final class ChildError extends ParentError {
            public ChildError(String message) {
                super(message);
            }

            @Override
            public String toString() {
                return "ChildError: " + message;
            }
        }
    }
    //endregion
}
