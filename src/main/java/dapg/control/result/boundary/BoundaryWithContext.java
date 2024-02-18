package dapg.control.result.boundary;

import dapg.control.result.Result;
import dapg.control.result.boundary.context.ContextfulError;
import dapg.control.result.boundary.context.ErrorContext;
import io.vavr.CheckedFunction1;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;

public class BoundaryWithContext<T, E extends ContextfulError> extends AbstractBoundary<T, E, Throwable> {
    private final Function<Throwable, E> unadaptedMapThrowable;
    private ErrorContext context;

    @SafeVarargs
    public BoundaryWithContext(@NonNull Function<Throwable, E> mapThrowable, @NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        this(mapThrowable, ErrorContext.of(contextEntries));
    }

    private BoundaryWithContext(Function<Throwable, E> mapThrowable, ErrorContext errorContext) {
        super(adaptMapThrowable(mapThrowable, errorContext));
        unadaptedMapThrowable = mapThrowable;
        context = errorContext;
    }

    private static <E extends ContextfulError> Function<Throwable, E> adaptMapThrowable(Function<Throwable, E> mapThrowable, ErrorContext errorContext) {
        return throwable -> {
            E err = mapThrowable.apply(throwable);
            err.addContext(errorContext);
            return err;
        };
    }

    public Result<T, E> attempt(@NonNull CheckedFunction1<BoundaryWithContext<T, E>, T> fn) {
        return attempt(this, fn);
    }

    @SafeVarargs
    public final BoundaryWithContext<T, E> addContext(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        return new BoundaryWithContext<>(unadaptedMapThrowable, context.add(contextEntries));
    }

    @SafeVarargs
    public final void addToContext(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        context = context.add(contextEntries);
        mapThrowable = adaptMapThrowable(unadaptedMapThrowable, context);
    }

    public String getContextAsString() {
        return context.toString();
    }

    @Override
    public <PhantomT> PhantomT breakErr(@NonNull E err) {
        err.addContext(context);
        throw new ErrEarlyReturnException(err);
    }

    @Override
    public <PhantomT> PhantomT breakThrowable(@NonNull Throwable throwable) {
        throw new ThrowableEarlyReturnException(throwable);
    }
}
