package dapg.data.named.product.impl.tuple;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.impl.NmProduct3;

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
