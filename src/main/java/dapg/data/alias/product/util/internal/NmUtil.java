package dapg.data.alias.product.util.internal;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NmUtil {
    public static int MAX_ARITY = 12;

    //region Values
    public static Object EMPTY_VALUE_SLOT_PLACEHOLDER = EmptyValueSlot.INSTANCE;
    private enum EmptyValueSlot {INSTANCE}

    public static Object RESERVED_VALUE_SLOT_PLACEHOLDER = ReservedValueSlot.INSTANCE;
    private enum ReservedValueSlot {INSTANCE}

    public static Object[] preallocateValuesArrayAndPut(Object... varargsValues) {
        if (varargsValues.length < MAX_ARITY) {
            Object[] values = Arrays.copyOf(varargsValues, MAX_ARITY);
            Arrays.fill(values, varargsValues.length, MAX_ARITY, EMPTY_VALUE_SLOT_PLACEHOLDER);
            return values;
        } else {
            return varargsValues;
        }
    }

    // todo delete if not used
    public static Object[] allocateEmptyValuesArray() {
        Object[] values = new Object[MAX_ARITY];
        Arrays.fill(values, EMPTY_VALUE_SLOT_PLACEHOLDER);
        return values;
    }
    //endregion

    //region Keys
    public static AliasKey<?> EMPTY_KEY_SLOT_PLACEHOLDER = EmptyKeySlot.INSTANCE;
    private enum EmptyKeySlot implements AliasKey<Alias<Object>> {INSTANCE}

    public static AliasKey<?>[] preallocateKeysArrayAndPut(AliasKey<?>... varargsKeys) {
        if (varargsKeys.length < MAX_ARITY) {
            AliasKey<?>[] keys = Arrays.copyOf(varargsKeys, MAX_ARITY);
            Arrays.fill(keys, varargsKeys.length, MAX_ARITY, EMPTY_KEY_SLOT_PLACEHOLDER);
            return keys;
        } else {
            return varargsKeys;
        }
    }
    //endregion

    //region Indices
    public static int POSITION_V1 = 0;
    public static int POSITION_V2 = 1;
    public static int POSITION_V3 = 2;

    public static byte EMPTY_INDEX_SLOT_PLACEHOLDER = Byte.MIN_VALUE;

    // todo delete if not used
    public static byte[] preallocateIndicesArrayAndPut(byte... varargsIndices) {
        if (varargsIndices.length < MAX_ARITY) {
            byte[] indices = Arrays.copyOf(varargsIndices, MAX_ARITY);
            Arrays.fill(indices, varargsIndices.length, MAX_ARITY, EMPTY_INDEX_SLOT_PLACEHOLDER);
            return indices;
        } else {
            return varargsIndices;
        }
    }

    public static byte[] preallocateIndicesArrayAndFillForArity(int arity) {
        byte[] indices = allocateEmptyIndicesArray();
        for (byte i = 0; i < arity; i++) {
            indices[i] = i;
        }
        return indices;
    }

    public static byte[] allocateEmptyIndicesArray() {
        byte[] indices = new byte[MAX_ARITY];
        Arrays.fill(indices, EMPTY_INDEX_SLOT_PLACEHOLDER);
        return indices;
    }
    //endregion
}
