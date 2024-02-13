package dapg.control.result.boundary;

import dapg.control.result.Result;
import io.vavr.CheckedFunction1;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.function.Function;

@AllArgsConstructor
public abstract class AbstractBoundary<T, E> {
    @NonNull
    protected Function<Throwable, E> mapThrowable;

    @SneakyThrows
    protected <SelfT extends AbstractBoundary<T, E>> Result<T, E> attempt(SelfT boundary, CheckedFunction1<SelfT, T> fn) {
        try {
            T value = fn.apply(boundary);
            return Result.ok(value);
        } catch (InterruptedException | LinkageError | VirtualMachineError e) {
            throw e; // these fatal exceptions should not be caught
        } catch (ErrEarlyReturnException e) {
            //noinspection unchecked
            return Result.err((E) e.err);
        } catch (ThrowableEarlyReturnException e) {
            return Result.err(mapThrowable.apply(e.throwable));
        } catch (Throwable e) {
            return Result.err(mapThrowable.apply(e));
        }
    }

    public abstract  <PhantomT> PhantomT breakErr(@NonNull E err);

    public abstract  <PhantomT> PhantomT breakThrowable(@NonNull Throwable throwable);
}
