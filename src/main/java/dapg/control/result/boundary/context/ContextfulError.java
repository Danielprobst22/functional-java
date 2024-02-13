package dapg.control.result.boundary.context;

import lombok.NonNull;

public interface ContextfulError {

    void addContext(@NonNull ErrorContext errorContext);

    ErrorContext getContext();
}
