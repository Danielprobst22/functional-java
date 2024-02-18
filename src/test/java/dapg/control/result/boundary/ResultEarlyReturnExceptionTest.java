package dapg.control.result.boundary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultEarlyReturnExceptionTest {

    @Test
    void shouldThrowException_whenPassingNullToConstructor() {
        //noinspection DataFlowIssue,ThrowableNotThrown
        assertThrows(NullPointerException.class, () -> new ResultEarlyReturnException(null));
    }
}
