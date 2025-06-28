package dapg.data.alias.product;

import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.UntypedNmProductConstructor;
import dapg.data.alias.product.util.valueprovider.AddAliasValue;
import dapg.data.alias.product.util.valueprovider.AliasValueProvider;
import dapg.data.alias.product.util.valueprovider.MapAliasValue;
import dapg.data.alias.product.util.valueprovider.SelectAliasValue;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

import static dapg.data.alias.product.util.internal.NmUtil.*;

public abstract class NmProduct {
    // Atomicity follows same design/implementation of AtomicReferenceArray#compareAndSet
    private static final VarHandle KEYS = MethodHandles.arrayElementVarHandle(AliasKey[].class);
    // Always length 12 - can be shared between multiple product instances
    protected final AliasKey<?>[] keys;
    // Always length 12 - can be shared between multiple product instances
    protected final Object[] values;

    protected NmProduct(AliasKey<?>[] keys, Object[] values) {
        this.keys = keys;
        this.values = values;
    }

    protected Object untypedValueAtIndex(
            AliasKey<?> key,
            byte index,
            int positionInProductForErrorReporting
    ) {
        if (keys[index] != key) {
            String msg = mismatchedAliasKeyErrorMessage(key, keys[index], values[index], positionInProductForErrorReporting);
            throw new IllegalArgumentException(msg);
        }
        return values[index];
    }

    protected abstract byte indexForPosition(int positionInProduct);

    protected abstract int arity();

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
        for (int position = 0; position < valueProviders.length; position++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[position];
            boolean copyInPlaceMightBePossible = switch (valueProvider) {
                // New elements are only added in place if the index matching their position is still empty
                case AddAliasValue(_, _),
                     MapAliasValue(_) -> keys[position] == EMPTY_KEY_SLOT_PLACEHOLDER;
                // Reads already present value, no mutation necessary
                case SelectAliasValue(_) -> true;
            };
            if (!copyInPlaceMightBePossible) {
                return false;
            }
        }
        return true;
    }

    private boolean optimisticLockingSucceeded(AliasValueProvider<?, ?>[] valueProviders) {
        for (int position = 0; position < valueProviders.length; position++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[position];
            boolean optimisticLockingSucceeded = switch (valueProvider) {
                // Performs atomic 'compareAndSet' to reserve the position in the 'keys' array
                case AddAliasValue(_, _),
                     MapAliasValue(_) -> KEYS.compareAndSet(keys, position, EMPTY_KEY_SLOT_PLACEHOLDER, RESERVED_KEY_SLOT_PLACEHOLDER);
                // Reads already present value, no mutation necessary
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
        byte[] newIndices = emptyIndicesForArity(valueProviders.length);

        for (int newPosition = 0; newPosition < valueProviders.length; newPosition++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[newPosition];

            switch (valueProvider) {
                case AddAliasValue(AliasKey<?> key, Object value) -> {
                    boolean optimisticLockingUpheld = KEYS.compareAndSet(keys, newPosition, RESERVED_KEY_SLOT_PLACEHOLDER, key);
                    if (!optimisticLockingUpheld) {
                        String msg = optimisticLockingNotUpheldErrorMessage(newPosition);
                        throw new IllegalStateException(msg);
                    }
                    values[newPosition] = value;
                    newIndices[newPosition] = (byte) newPosition;
                }
                case MapAliasValue(int positionInProduct) -> {
                    byte originalIndex = indexForPosition(positionInProduct);
                    AliasKey<?> keyToMap = keys[originalIndex];
                    Object valueToMap = values[originalIndex];

                    boolean optimisticLockingUpheld = KEYS.compareAndSet(keys, newPosition, RESERVED_KEY_SLOT_PLACEHOLDER, keyToMap); // todo use mapped key
                    if (!optimisticLockingUpheld) {
                        String msg = optimisticLockingNotUpheldErrorMessage(newPosition);
                        throw new IllegalStateException(msg);
                    }
                    values[newPosition] = valueToMap; // todo proper impl -> call mapping function
                    newIndices[newPosition] = (byte) newPosition;
                }
                case SelectAliasValue(int positionInProduct) -> {
                    // Sets the index at 'newPosition' to reference a value already present in the 'values' array
                    // -> 'keys' and 'values' arrays at 'newPosition' remain empty (or might even already be occupied)
                    // -> could be populated in a later/separate call to 'untypedCopy' where a new value might actually have to be written
                    newIndices[newPosition] = indexForPosition(positionInProduct);
                }
            }
        }

        return nmProductConstructor.apply(keys, values, newIndices);
    }

    private NmProduct copyWithNewArrays(
            UntypedNmProductConstructor nmProductConstructor,
            AliasValueProvider<?, ?>[] valueProviders
    ) {
        AliasKey<?>[] newKeys = allocateEmptyKeysArray();
        Object[] newValues = allocateEmptyValuesArray();
        byte[] newIndices = defaultIndicesForArity(valueProviders.length); // already correctly populated -> no need to be overwritten

        for (int newPosition = 0; newPosition < valueProviders.length; newPosition++) {
            AliasValueProvider<?, ?> valueProvider = valueProviders[newPosition];

            switch (valueProvider) {
                case AddAliasValue(AliasKey<?> key, Object value) -> {
                    newKeys[newPosition] = key;
                    newValues[newPosition] = value;
                }
                case MapAliasValue(int positionInProduct) -> {
                    byte originalIndex = indexForPosition(positionInProduct);
                    AliasKey<?> keyToMap = keys[originalIndex];
                    Object valueToMap = values[originalIndex];

                    newKeys[newPosition] = keyToMap; // todo use mapped key
                    newValues[newPosition] = valueToMap; // todo proper impl -> call mapping function
                }
                case SelectAliasValue(int positionInProduct) -> {
                    byte originalIndex = indexForPosition(positionInProduct);
                    AliasKey<?> selectedKey = keys[originalIndex];
                    Object selectedValue = values[originalIndex];

                    newKeys[newPosition] = selectedKey;
                    newValues[newPosition] = selectedValue;
                }
            }
        }

        return nmProductConstructor.apply(newKeys, newValues, newIndices);
    }
    //endregion

    //region Error helper methods
    protected final String wrongNumberOfIndicesErrorMessage(int providedIndicesLength) {
        String classOfProduct = this.getClass().getSimpleName();
        return String.format(
                "NmProduct='%s' cannot be instantiated with indices array with length=%d - length must be equal to arity=%d",
                classOfProduct, providedIndicesLength, arity()
        );
    }

    protected final String positionOutOfBoundsErrorMessage(int positionInProduct) {
        String classOfProduct = this.getClass().getSimpleName();
        return String.format(
                "Position=%d is out of bounds for NmProduct='%s' with arity=%d",
                displayPosition(positionInProduct), classOfProduct, arity()
        );
    }

    private String mismatchedAliasKeyErrorMessage(
            AliasKey<?> providedKey,
            AliasKey<?> actualKey,
            Object value,
            int positionInProduct
    ) {
        String classOfProduct = this.getClass().getSimpleName();
        return String.format(
                "Provided AliasKey='%s' does not match AliasKey='%s' for value='%s' at position=%d of NmProduct='%s'",
                providedKey.displayName(), actualKey.displayName(), value, displayPosition(positionInProduct), classOfProduct
        );
    }

    private String optimisticLockingNotUpheldErrorMessage(int positionInProduct) {
        String classOfProduct = this.getClass().getSimpleName();
        AliasKey<?> conflictingKey = keys[positionInProduct];
        return String.format(
                "Copying NmProduct='%s' failed - optimistic locking was not upheld: " +
                        "key='%s' at position=%d did not match expected constant='%s'",
                classOfProduct, conflictingKey.displayName(), displayPosition(positionInProduct), RESERVED_KEY_SLOT_PLACEHOLDER.displayName()
        );
    }
    //endregion
}
