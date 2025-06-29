package dapg.data.alias.product.impl;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.product.api.markertraits.nm.Nm3;

import static dapg.data.alias.product.util.internal.NmUtil.*;

//fmt:off
public abstract class NmProduct3<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>,
    AliasT3 extends Alias<?>
>
    extends Nm
    implements Nm3<AliasT1, AliasT2, AliasT3>
{ //fmt:on
    protected final byte indexV1;
    protected final byte indexV2;
    protected final byte indexV3;

    protected NmProduct3(AliasKey<?>[] keys, Object[] values, byte[] indices) {
        super(keys, values);
        if (indices.length != arity()) {
            String msg = wrongNumberOfIndicesErrorMessage(indices.length);
            throw new IllegalArgumentException(msg);
        }
        indexV1 = indices[POSITION_V1];
        indexV2 = indices[POSITION_V2];
        indexV3 = indices[POSITION_V3];
    }

    @Override
    protected final byte[] copyIndices() {
        return makeIndicesArray(indexV1, indexV2, indexV3);
    }

    @Override
    protected final byte indexForPosition(int positionInProduct) {
        return switch (positionInProduct) {
            case 0 -> indexV1;
            case 1 -> indexV2;
            case 2 -> indexV3;
            default -> {
                String msg = positionOutOfBoundsErrorMessage(positionInProduct);
                throw new IllegalArgumentException(msg);
            }
        };
    }

    @Override
    public final Object untypedValue1(AliasKey<AliasT1> key) {
        return untypedValueAtIndex(key, indexV1, POSITION_V1);
    }

    @Override
    public final Object untypedValue2(AliasKey<AliasT2> key) {
        return untypedValueAtIndex(key, indexV2, POSITION_V2);
    }

    @Override
    public final Object untypedValue3(AliasKey<AliasT3> key) {
        return untypedValueAtIndex(key, indexV3, POSITION_V3);
    }
}
