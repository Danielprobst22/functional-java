package dapg.control.result.boundary;

import lombok.NonNull;

// package private
class ErrEarlyReturnException extends RuntimeException {
    protected final Object err;

    protected ErrEarlyReturnException(@NonNull Object err) {
        super(null, null, false, false); // disable creation of stack trace for better performance
        this.err = err;
    }
}
