package dapg.control.result.boundary.context;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorContext implements Serializable {
    private final ImmutableMap<String, Serializable> context;

    @SafeVarargs
    public static ErrorContext of(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        return makeContext(varargsToEntryStream(contextEntries));
    }

    @SafeVarargs
    public final ErrorContext add(@NonNull Map.Entry<String, ? extends Serializable>... contextEntries) {
        return makeContext(EntryStream.of(context).append(varargsToEntryStream(contextEntries)));
    }

    public ErrorContext merge(@NonNull ErrorContext other) {
        if (context.isEmpty()) {
            return other;
        } else if (other.context.isEmpty()) {
            return this;
        } else {
            return makeContext(EntryStream.of(context).append(other.context));
        }
    }

    @Override
    public String toString() {
        return EntryStream
                .of(context)
                .mapKeyValue((key, value) -> key + "='" + value + "'")
                .joining(", ", "Context: ", "");
    }

    //region helper functions
    private static EntryStream<String, Serializable> varargsToEntryStream(Map.Entry<String, ? extends Serializable>[] contextEntries) {
        // cannot directly create EntryStream due to wildcard "? extends Serializable"
        return StreamEx.of(contextEntries).mapToEntry(Map.Entry::getKey, Map.Entry::getValue);
    }

    private static ErrorContext makeContext(EntryStream<String, Serializable> contextEntries) {
        LinkedHashMap<String, Serializable> context = new LinkedHashMap<>();
        contextEntries.forKeyValue((key, value) ->
                context.merge(
                        key,
                        value,
                        (firstOccurrence, secondOccurrence) -> {
                            logDuplicate(key, firstOccurrence, secondOccurrence);
                            return secondOccurrence;
                        }
                )
        );
        return new ErrorContext(ImmutableMap.copyOf(context));
    }

    private static void logDuplicate(String key, Serializable firstOccurrence, Serializable secondOccurrence) {
        if (firstOccurrence != secondOccurrence) {
            log.warn("ErrorContext: Duplicate entry with different values - key='{}', overriding value='{}' with value='{}'", key, firstOccurrence, secondOccurrence);
        } else {
            log.debug("ErrorContext: Duplicate entry with same value - key='{}', value='{}'", key, firstOccurrence);
        }
    }
    //endregion
}
