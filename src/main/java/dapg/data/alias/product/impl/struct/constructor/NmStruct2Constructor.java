package dapg.data.alias.product.impl.struct.constructor;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.product.api.NmStruct;
import dapg.data.alias.product.impl.struct.NmStruct2;

public interface NmStruct2Constructor<
        ResultT extends Nm & NmStruct<NmStruct2<AliasT1, AliasT2>>,
        AliasT1 extends Alias<?>,
        AliasT2 extends Alias<?>
        >
{
    ResultT apply(Object[] values, AliasKey<?>[] keys, byte[] indices);
}
