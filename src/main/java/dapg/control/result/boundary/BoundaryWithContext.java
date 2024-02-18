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
    private final Function<Throwable, E> mapThrowable;
    private ErrorContext context;

    @SafeVarargs
    public BoundaryWithContext(@NonNull Function<Throwable, E> mapThrowable, @NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        this(mapThrowable, ErrorContext.of(contextEntries));
    }

    private BoundaryWithContext(Function<Throwable, E> mapThrowable, ErrorContext context) {
        this.mapThrowable = mapThrowable;
        this.context = context;
    }

    public Result<T, E> attempt(@NonNull CheckedFunction1<BoundaryWithContext<T, E>, T> fn) {
        return attempt(this, fn);
    }

    @SafeVarargs
    public final BoundaryWithContext<T, E> addContext(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        return new BoundaryWithContext<>(mapThrowable, context.add(contextEntries));
    }

    @SafeVarargs
    public final void addToContext(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        context = context.add(contextEntries);
    }

    public String getContextAsString() {
        return context.toString();
    }

    @Override
    public <PhantomT> PhantomT breakErr(@NonNull E err) {
        err.addContext(context);
        throw new ResultEarlyReturnException(err);
    }

    @Override
    public <PhantomT> PhantomT breakThrowable(@NonNull Throwable throwable) {
        throw new ResultEarlyReturnException(mapThrowable(throwable));
    }

    @Override
    protected E mapThrowable(Throwable throwable) {
        E err = mapThrowable.apply(throwable);
        err.addContext(context);
        return err;
    }
}
