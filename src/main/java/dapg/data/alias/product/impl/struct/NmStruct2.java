package dapg.data.alias.product.impl.struct;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.product.api.NmStruct;
import dapg.data.alias.product.impl.NmProduct2;

//fmt:off
public abstract class NmStruct2<
    // todo add SelfT
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
>
    extends NmProduct2<AliasT1, AliasT2>
    implements NmStruct<NmStruct2<AliasT1, AliasT2>> // todo pass SelfT
{ //fmt:on

    protected NmStruct2(AliasKey<?>[] keys, Object[] values, byte[] indices) {
        super(keys, values, indices);
    }

    @Override
    public final Nm asNmInstance() {
        return this;
    }
}
