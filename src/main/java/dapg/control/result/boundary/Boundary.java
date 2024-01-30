package dapg.control.result.boundary;

import dapg.control.result.Result;
import io.vavr.CheckedFunction1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Boundary<T, E, MappableE> {
    @NonNull
    private final ErrorHandler<E, MappableE> errorHandler;

    public Result<T, E> run(@NonNull CheckedFunction1<Boundary<T, E, MappableE>, T> fn) {
        try {
            T value = fn.apply(this);
            return Result.ok(value);
        } catch (ErrEarlyReturnException e) {
            //noinspection unchecked
            return Result.err((E) e.err);
        } catch (MappableErrEarlyReturnException e) {
            //noinspection unchecked
            return Result.err(errorHandler.mapErr((MappableE) e.mappableErr));
        } catch (ThrowableEarlyReturnException e) {
            return Result.err(errorHandler.mapThrowable(e.throwable));
        } catch (Throwable e) {
            return Result.err(errorHandler.mapThrowable(e));
        }
    }

    public <PhantomT> PhantomT breakErr(@NonNull E err) {
        throw new ErrEarlyReturnException(err);
    }

    public <PhantomT> PhantomT breakMappable(@NonNull MappableE mappableErr) {
        throw new MappableErrEarlyReturnException(mappableErr);
    }

    public <PhantomT> PhantomT breakThrowable(@NonNull Throwable throwable) {
        throw new ThrowableEarlyReturnException(throwable);
    }
}
