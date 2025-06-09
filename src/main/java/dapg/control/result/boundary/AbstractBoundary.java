package dapg.control.result.boundary;

import dapg.control.result.Result;
import io.vavr.CheckedFunction1;
import lombok.NonNull;
import lombok.SneakyThrows;

public abstract class AbstractBoundary<OkT, ErrT, ThrowableProofT extends Throwable> {

    @SneakyThrows
    protected <SelfT extends AbstractBoundary<OkT, ErrT, ThrowableProofT>> Result<OkT, ErrT> attempt(SelfT boundary, CheckedFunction1<SelfT, OkT> fn) {
        try {
            OkT value = fn.apply(boundary);
            return Result.ok(value);
        } catch (InterruptedException | LinkageError | VirtualMachineError e) {
            throw e; // these fatal exceptions should not be caught
        } catch (ResultEarlyReturnException e) {
            //noinspection unchecked
            return Result.err((ErrT) e.err);
        } catch (Throwable e) {
            return Result.err(mapThrowable(e));
        }
    }

    public abstract <PhantomT> PhantomT breakErr(@NonNull ErrT err);

    public abstract <PhantomT> PhantomT breakThrowable(@NonNull ThrowableProofT throwable);

    protected abstract ErrT mapThrowable(Throwable throwable);
}
