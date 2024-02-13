package dapg.control.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OkTest {

    @Test
    void shouldThrowException_whenPassingNullToConstructor() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new Ok<>(null));
    }
}
