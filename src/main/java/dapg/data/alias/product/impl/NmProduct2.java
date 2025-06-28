package dapg.data.alias.product.impl;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;

import static dapg.data.alias.product.util.internal.NmUtil.*;

//fmt:off
public abstract class NmProduct2<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
>
    extends Nm
    implements Vl1<AliasT1>,
               Vl2<AliasT2>
{ //fmt:on
    public static int ARITY = 2;
    protected final byte indexV1;
    protected final byte indexV2;

    protected NmProduct2(AliasKey<?>[] keys, Object[] values, byte[] indices) {
        super(keys, values);
        if (indices.length != ARITY) {
            String msg = wrongNumberOfIndicesErrorMessage(indices.length);
            throw new IllegalArgumentException(msg);
        }
        indexV1 = indices[POSITION_V1];
        indexV2 = indices[POSITION_V2];
    }

    @Override
    protected final byte[] copyIndices() {
        return makeIndicesArray(indexV1, indexV2);
    }

    @Override
    protected final byte indexForPosition(int positionInProduct) {
        return switch (positionInProduct) {
            case 0 -> indexV1;
            case 1 -> indexV2;
            default -> {
                String msg = positionOutOfBoundsErrorMessage(positionInProduct);
                throw new IllegalArgumentException(msg);
            }
        };
    }

    @Override
    protected final int arity() {
        return ARITY;
    }

    @Override
    public final Object untypedValue1(AliasKey<AliasT1> key) {
        return untypedValueAtIndex(key, indexV1, POSITION_V1);
    }

    @Override
    public final Object untypedValue2(AliasKey<AliasT2> key) {
        return untypedValueAtIndex(key, indexV2, POSITION_V2);
    }
}
