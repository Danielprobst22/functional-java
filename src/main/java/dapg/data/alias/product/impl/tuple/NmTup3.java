package dapg.data.alias.product.impl.tuple;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.NmProduct3;

//fmt:off
public final class NmTup3<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>,
    AliasT3 extends Alias<?>
>
    extends NmProduct3<AliasT1, AliasT2, AliasT3>
{ //fmt:on

    // package-private
    NmTup3(AliasKey<?>[] keys, Object[] values, byte[] indices) {
        super(keys, values, indices);
    }
}
