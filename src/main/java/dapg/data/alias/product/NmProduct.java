package dapg.data.alias.product;

import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.UntypedNmProductConstructor;
import dapg.data.alias.product.util.valueprovider.AddAliasValue;
import dapg.data.alias.product.util.valueprovider.AliasValueProvider;
import dapg.data.alias.product.util.valueprovider.MapAliasValue;
import dapg.data.alias.product.util.valueprovider.SelectAliasValue;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

import static dapg.data.alias.product.util.internal.NmUtil.*;

// todo make sealed
public abstract class NmProduct {
    // Atomicity follows same design/implementation of AtomicReferenceArray#compareAndSet
    private static final VarHandle VALUES = MethodHandles.arrayElementVarHandle(Object[].class);
    // Always length 12 - can be shared between multiple product instances
    protected final Object[] values;
    // Always length 12 - can be shared between multiple product instances
    protected final AliasKey<?>[] keys;
    // Always length 12 - never shared with other product instances
    protected final byte[] indices; // todo replace with independent variables

    // todo delete if not needed
    protected NmProduct() {
        values = new Object[MAX_ARITY];
        Arrays.fill(values, EMPTY_VALUE_SLOT_PLACEHOLDER);

        keys = new AliasKey<?>[MAX_ARITY];
        Arrays.fill(keys, null); // todo proper impl

        indices = new byte[MAX_ARITY];
        Arrays.fill(indices, EMPTY_INDEX_SLOT_PLACEHOLDER);
    }

    protected NmProduct(Object[] values, AliasKey<?>[] keys, byte[] indices) {
        this.values = values;
        this.keys = keys;
        this.indices = indices;
    }

    // todo proper impl
    protected Object untypedValueAtIndex(AliasKey<?> key, int index) {
//    protected Object untypedValueAtIndex(AliasKey<?> key, byte indexInValuesArray, int positionInProduct) {
        if (keys[index] != key) {
//            String msg = mismatchedAliasKeyErrorMessage(key, positionInProduct); // todo uncomment
            String msg = mismatchedAliasKeyErrorMessage(key, 123);
            throw new IllegalArgumentException(msg);
        }
        return values[index];
    }

    // todo proper impl
//    protected abstract byte indexForPosition(int positionInProduct);
    protected byte indexForPosition(int positionInProduct) {
        return 0;
    }

    //region Copy helper methods
    protected NmProduct untypedCopy(
            UntypedNmProductConstructor nmProductConstructor,
            AliasValueProvider<?, ?>... valueProviders
    ) {
        if (copyInPlaceMightBePossible(valueProviders) && optimisticLockingSucceeded(valueProviders)) {
            return copyInPlace(nmProductConstructor, valueProviders);
        } else {
            return copyWithNewArrays(nmProductConstructor, valueProviders);
        }
    }

    private boolean copyInPlaceMightBePossible(AliasValueProvider<?, ?>[] valueProviders) {
        for (int currentPosition = 0; currentPosition < valueProviders.length; currentPosition++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[currentPosition];
            boolean copyInPlaceMightBePossible = switch (valueProvider) {
                // todo explain
                case AddAliasValue(_, _),
                     MapAliasValue(_) -> values[currentPosition] == EMPTY_VALUE_SLOT_PLACEHOLDER;
                // todo Reads the value
                case SelectAliasValue(_) -> true;
            };
            if (!copyInPlaceMightBePossible) {
                return false;
            }
        }
        return true;
    }

    private boolean optimisticLockingSucceeded(AliasValueProvider<?, ?>[] valueProviders) {
        for (int currentPosition = 0; currentPosition < valueProviders.length; currentPosition++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[currentPosition];
            boolean optimisticLockingSucceeded = switch (valueProvider) {
                // todo explain
                case AddAliasValue(_, _),
                     MapAliasValue(_) ->
                            VALUES.compareAndSet(values, currentPosition, EMPTY_VALUE_SLOT_PLACEHOLDER, RESERVED_VALUE_SLOT_PLACEHOLDER);
                // todo not necessary
                case SelectAliasValue(_) -> true;
            };
            if (!optimisticLockingSucceeded) {
                return false;
            }
        }
        return true;
    }

    private NmProduct copyInPlace(
            UntypedNmProductConstructor nmProductConstructor,
            AliasValueProvider<?, ?>[] valueProviders
    ) {
        byte[] newIndices = allocateEmptyIndicesArray();
        for (int currentPosition = 0; currentPosition < valueProviders.length; currentPosition++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[currentPosition];
            switch (valueProvider) {
                case AddAliasValue(AliasKey<?> key, Object value) -> {
                    boolean optimisticLockingUpheld = VALUES.compareAndSet(values, currentPosition, RESERVED_VALUE_SLOT_PLACEHOLDER, value);
                    if (!optimisticLockingUpheld) {
                        throw new IllegalStateException(optimisticLockingNotUpheldErrorMessage(currentPosition));
                    }
                    keys[currentPosition] = key;
                    newIndices[currentPosition] = (byte) currentPosition;
                }
                case MapAliasValue(int positionInProduct) -> {
                    // todo proper impl
                    newIndices[currentPosition] = (byte) currentPosition;
                }
                case SelectAliasValue(int positionInProduct) -> {
                    // todo proper impl
                    newIndices[currentPosition] = (byte) currentPosition;
                }
            }
        }
        // todo proper impl
        return null;
    }

    private NmProduct copyWithNewArrays(
            UntypedNmProductConstructor nmProductConstructor,
            AliasValueProvider<?, ?>[] valueProviders
    ) {
        // todo proper impl
        return null;
    }
    //endregion

    //region Error helper methods
    private String mismatchedAliasKeyErrorMessage(
            AliasKey<?> providedKey,
            int positionInProduct
    ) {
        byte index = indices[positionInProduct];
        AliasKey<?> actualKey = keys[index];
        Object value = values[index];
        String classOfProduct = this.getClass().getSimpleName();
        return String.format(
                "Provided AliasKey='%s' does not match AliasKey='%s' for value='%s' at position=%d of NmProduct='%s'",
                providedKey.displayName(), actualKey.displayName(), value, positionInProduct, classOfProduct
        );
    }

    private String optimisticLockingNotUpheldErrorMessage(int positionInProduct) {
        String classOfProduct = this.getClass().getSimpleName();
        Object value = values[positionInProduct];
        return String.format(
                "Copying NmProduct='%s' failed - optimistic locking was not upheld: " +
                        "value='%s' at position=%d did not match expected constant='RESERVED_VALUE_SLOT_PLACEHOLDER'",
                classOfProduct, value, positionInProduct
        );
    }
    //endregion
}
