package dapg.control.result.boundary;

import dapg.control.result.Result;
import io.vavr.CheckedFunction1;
import lombok.NonNull;

import java.util.function.Function;

public class Boundary<T, E> extends AbstractBoundary<T, E> {

    public Boundary(@NonNull Function<Throwable, E> mapThrowable) {
        super(mapThrowable);
    }

    public Result<T, E> attempt(@NonNull CheckedFunction1<Boundary<T, E>, T> fn) {
        return attempt(this, fn);
    }

    @Override
    public <PhantomT> PhantomT breakErr(@NonNull E err) {
        throw new ErrEarlyReturnException(err);
    }

    @Override
    public <PhantomT> PhantomT breakThrowable(@NonNull Throwable throwable) {
        throw new ThrowableEarlyReturnException(throwable);
    }
}
