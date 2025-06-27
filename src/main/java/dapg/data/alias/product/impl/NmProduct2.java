package dapg.data.alias.product.impl;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;

import static dapg.data.alias.product.util.internal.NmUtil.POSITION_V1;
import static dapg.data.alias.product.util.internal.NmUtil.POSITION_V2;

//fmt:off
public abstract class NmProduct2<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
>
    extends Nm
    implements Vl1<AliasT1>,
               Vl2<AliasT2>
{ //fmt:on
    protected NmProduct2(Object[] values, AliasKey<?>[] keys, byte[] indices) {
        super(values, keys, indices);
    }

    @Override
    public Object untypedValue1(AliasKey<AliasT1> key) {
        return untypedValueAtIndex(key, POSITION_V1);
    }

    @Override
    public Object untypedValue2(AliasKey<AliasT2> key) {
        return untypedValueAtIndex(key, POSITION_V2);
    }
}
