package dapg.control.result.boundary.context;

import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Map;

@Getter
public class ContextfulException extends Exception implements ContextfulError {
    private ErrorContext context;

    @SafeVarargs
    public ContextfulException(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        context = ErrorContext.of(contextEntries);
    }

    @SafeVarargs
    public ContextfulException(@NonNull String message, @NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        super(message);
        context = ErrorContext.of(contextEntries);
    }

    @SafeVarargs
    public ContextfulException(@NonNull String message, @NonNull Throwable cause, @NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        super(message, cause);
        context = makeContext(cause, contextEntries);
    }

    @SafeVarargs
    public ContextfulException(@NonNull Throwable cause, @NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        super(cause);
        context = makeContext(cause, contextEntries);
    }

    @Override
    public void addContext(@NonNull ErrorContext errorContext) {
        context = context.merge(errorContext);
    }

    @Override
    public String getMessage() {
        return switch (super.getMessage()) {
            case String message -> message + " - " + context.toString();
            case null -> context.toString();
        };
    }

    @SafeVarargs
    private ErrorContext makeContext(Throwable cause, Map.Entry<String, ? extends Serializable>... contextEntries) {
        return switch (cause) {
            case ContextfulError contextfulError -> contextfulError.getContext().add(contextEntries);
            default -> ErrorContext.of(contextEntries);
        };
    }
}
