package dapg.control.result.boundary;

import lombok.NonNull;

// package private
class ResultEarlyReturnException extends RuntimeException {
    // ignore "Make "err" transient or serializable."
    // -> ResultEarlyReturnException is immediately caught by its enclosing boundary so there shouldn't arise the need to serialize it
    @SuppressWarnings("squid:S1948")
    protected final Object err;

    protected ResultEarlyReturnException(@NonNull Object err) {
        super(null, null, false, false); // disable creation of stack trace for better performance
        this.err = err;
    }
}
