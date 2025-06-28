package dapg.data.alias.product.impl.tuple;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.NmProduct2;

//fmt:off
public final class NmTup2<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
>
    extends NmProduct2<AliasT1, AliasT2>
{ //fmt:on

    // package-private
    NmTup2(AliasKey<?>[] keys, Object[] values, byte[] indices) {
        super(keys, values, indices);
    }
}
