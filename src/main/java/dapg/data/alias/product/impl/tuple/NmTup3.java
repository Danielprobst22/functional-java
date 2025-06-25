package dapg.data.alias.product.impl.tuple;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.NmProduct3;

public final class NmTup3<
        AliasT1 extends Alias<?>,
        AliasT2 extends Alias<?>,
        AliasT3 extends Alias<?>
        >
        extends NmProduct3<AliasT1, AliasT2, AliasT3>
{
    public static int ARITY = 3;

    // todo make package-private
    public NmTup3(Object[] values, AliasKey<?>[] keys, byte[] indices) {
        super(values, keys, indices);
    }
}
