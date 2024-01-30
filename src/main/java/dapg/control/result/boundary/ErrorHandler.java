package dapg.control.result.boundary;

import lombok.NonNull;

public interface ErrorHandler<E, MappableE> {

    E mapErr(@NonNull MappableE mappableErr);

    E mapThrowable(@NonNull Throwable throwable);
}
