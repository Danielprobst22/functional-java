package dapg.control.result.boundary;

import dapg.control.result.Result;
import io.vavr.CheckedFunction1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class Boundary<T, E> extends AbstractBoundary<T, E, Throwable> {
    @NonNull
    private final Function<Throwable, E> mapThrowable;

    public Result<T, E> attempt(@NonNull CheckedFunction1<Boundary<T, E>, T> fn) {
        return attempt(this, fn);
    }

    @Override
    public <PhantomT> PhantomT breakErr(@NonNull E err) {
        throw new ResultEarlyReturnException(err);
    }

    @Override
    public <PhantomT> PhantomT breakThrowable(@NonNull Throwable throwable) {
        throw new ResultEarlyReturnException(mapThrowable(throwable));
    }

    @Override
    protected E mapThrowable(Throwable throwable) {
        return mapThrowable.apply(throwable);
    }
}
