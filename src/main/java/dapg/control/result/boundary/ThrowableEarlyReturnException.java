package dapg.control.result.boundary;

import lombok.NonNull;

// package private
class ThrowableEarlyReturnException extends RuntimeException {
    protected final Throwable throwable;

    protected ThrowableEarlyReturnException(@NonNull Throwable throwable) {
        super(null, null, false, false); // disable creation of stack trace for better performance
        this.throwable = throwable;
    }
}
