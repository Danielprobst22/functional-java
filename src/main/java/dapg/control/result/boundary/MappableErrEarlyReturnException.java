package dapg.control.result.boundary;

import lombok.NonNull;

// package private
class MappableErrEarlyReturnException extends RuntimeException {
    protected final Object mappableErr;

    protected MappableErrEarlyReturnException(@NonNull Object mappableErr) {
        super(null, null, false, false); // disable creation of stack trace for better performance
        this.mappableErr = mappableErr;
    }
}
