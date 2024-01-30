package dapg.control.result.boundary;

import dapg.control.result.Result;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoundaryTest {
    private static final List<String> VALUES = List.of("3", "7", "12", "37", "42");
    private static final List<String> VALUES_WITH_INVALID_ELEMENT = List.of("3", "7", "12", "INVALID", "37", "42");

    @Test
    void shouldThrowException_whenPassingNullToConstructor() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new Boundary<>(null));
    }

    // todo split into individual tests with appropriate assertions
    @Test
    void test() {
        System.out.println(parseOrBreak(VALUES));
        System.out.println(parseOrBreak(VALUES_WITH_INVALID_ELEMENT));

        System.out.println();

        System.out.println(parseOrBreakMappable(VALUES));
        System.out.println(parseOrBreakMappable(VALUES_WITH_INVALID_ELEMENT));

        System.out.println();

        System.out.println(parseOrBreakThrowable(VALUES));
        System.out.println(parseOrBreakThrowable(VALUES_WITH_INVALID_ELEMENT));

        System.out.println();

        System.out.println(parseOrMapAndBreak(VALUES));
        System.out.println(parseOrMapAndBreak(VALUES_WITH_INVALID_ELEMENT));
    }

    //region parseOrBreak
    private Result<List<Integer>, Exception> parseOrBreak(List<String> values) {
        return Result.<List<Integer>, Exception, DummyError>boundary(new DummyErrorHandler()).run(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithException(value).orBreak(boundary))
                .toList()
        );
    }

    private Result<Integer, IllegalArgumentException> parseWithException(String value) {
        return Result.attempt(IllegalArgumentException::new, () -> Integer.parseInt(value));
    }
    //endregion

    //region parseOrBreakMappable
    private Result<List<Integer>, Exception> parseOrBreakMappable(List<String> values) {
        return Result.<List<Integer>, Exception, DummyError>boundary(new DummyErrorHandler()).run(boundary -> StreamEx
                .of(values)
                .map(value -> parseWithDummyError(value).orBreakMappable(boundary))
                .toList()
        );
    }

    private Result<Integer, DummyError.ChildError> parseWithDummyError(String value) {
        return Result.attempt(
                err -> new DummyError.ChildError(err.toString()),
                () -> Integer.parseInt(value)
        );
    }
    //endregion

    //region parseOrBreakThrowable
    private Result<List<Integer>, Exception> parseOrBreakThrowable(List<String> values) {
        return Result.<List<Integer>, Exception, DummyError>boundary(new DummyErrorHandler()).run(boundary -> StreamEx
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
        return Result.<List<Integer>, Exception, DummyError>boundary(new DummyErrorHandler()).run(boundary -> StreamEx
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

    //region DummyError & Handler
    private static class DummyErrorHandler implements ErrorHandler<Exception, DummyError> {
        @Override
        public Exception mapErr(@NonNull BoundaryTest.DummyError dummyError) {
            return new IllegalArgumentException("Values contain element that cannot be parsed - %s".formatted(dummyError.toString()));
        }

        @Override
        public Exception mapThrowable(@NonNull Throwable throwable) {
            return new IllegalArgumentException("Values contain element that cannot be parsed", throwable);
        }
    }

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