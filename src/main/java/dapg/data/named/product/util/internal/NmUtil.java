package dapg.data.named.product.util.internal;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.NmProduct;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NmUtil {
    public static int MAX_ARITY = 12;

    //region Keys
    public static AliasKey<?> EMPTY_KEY_SLOT_PLACEHOLDER = EmptyKeySlot.INSTANCE;
    private enum EmptyKeySlot implements AliasKey<Alias<Object>> { INSTANCE }

    public static AliasKey<?> RESERVED_KEY_SLOT_PLACEHOLDER = ReservedKeySlot.INSTANCE;
    private enum ReservedKeySlot implements AliasKey<Alias<Object>> { INSTANCE }

    public static AliasKey<?>[] preallocateKeysArrayAndPut(AliasKey<?>... varargsKeys) {
        if (varargsKeys.length < MAX_ARITY) {
            AliasKey<?>[] keys = Arrays.copyOf(varargsKeys, MAX_ARITY);
            Arrays.fill(keys, varargsKeys.length, MAX_ARITY, EMPTY_KEY_SLOT_PLACEHOLDER);
            return keys;
        } else {
            return varargsKeys;
        }
    }

    public static AliasKey<?>[] allocateEmptyKeysArray() {
        AliasKey<?>[] keys = new AliasKey<?>[MAX_ARITY];
        Arrays.fill(keys, EMPTY_KEY_SLOT_PLACEHOLDER);
        return keys;
    }
    //endregion

    //region Values
    public static Object EMPTY_VALUE_SLOT_PLACEHOLDER = EmptyValueSlot.INSTANCE;
    private enum EmptyValueSlot { INSTANCE }

    public static Object[] preallocateValuesArrayAndPut(Object... varargsValues) {
        if (varargsValues.length < MAX_ARITY) {
            Object[] values = Arrays.copyOf(varargsValues, MAX_ARITY);
            Arrays.fill(values, varargsValues.length, MAX_ARITY, EMPTY_VALUE_SLOT_PLACEHOLDER);
            return values;
        } else {
            return varargsValues;
        }
    }

    public static Object[] allocateEmptyValuesArray() {
        Object[] values = new Object[MAX_ARITY];
        Arrays.fill(values, EMPTY_VALUE_SLOT_PLACEHOLDER);
        return values;
    }
    //endregion

    //region Position & Indices
    public static int POSITION_V1 = 0;
    public static int POSITION_V2 = 1;
    public static int POSITION_V3 = 2;

    public static byte[] makeIndicesArray(byte... indices) {
        return indices;
    }

    public static byte[] defaultIndicesForArity(int arity) {
        byte[] indices = new byte[arity];
        for (byte i = 0; i < arity; i++) {
            indices[i] = i;
        }
        return indices;
    }

    public static byte[] emptyIndicesForArity(int arity) {
        byte[] indices = new byte[arity];
        Arrays.fill(indices, Byte.MIN_VALUE); // caller must overwrite all values before the array is safe to use
        return indices;
    }

    /// Adds 1 to zero-based `positionInProduct` to make the value match the numbering used for elements in subtypes of [NmProduct]
    ///
    /// @param positionInProduct zero-based position
    /// @return `positionInProduct + 1`
    public static int displayPosition(int positionInProduct) {
        return positionInProduct + 1;
    }
    //endregion
}
