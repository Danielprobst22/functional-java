package dapg.data.alias.product.impl.struct;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.product.api.NmStruct;
import dapg.data.alias.product.impl.NmProduct2;

//fmt:off
public abstract class NmStruct2<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
>
    extends NmProduct2<AliasT1, AliasT2>
    implements NmStruct<NmStruct2<AliasT1, AliasT2>>
{ //fmt:on

    protected NmStruct2(Object[] values, AliasKey<?>[] keys, byte[] indices) {
        super(values, keys, indices);
    }

    @Override
    public Nm asNmInstance() {
        return this;
    }
}
