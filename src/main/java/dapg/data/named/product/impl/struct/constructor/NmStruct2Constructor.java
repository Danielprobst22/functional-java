package dapg.data.named.product.impl.struct.constructor;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.api.Nm;
import dapg.data.named.product.api.NmStruct;
import dapg.data.named.product.impl.struct.NmStruct2;

//fmt:off
@FunctionalInterface
public interface NmStruct2Constructor<
    ResultT extends Nm & NmStruct<NmStruct2<AliasT1, AliasT2>>,
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
> { //fmt:on

    ResultT apply(AliasKey<?>[] keys, Object[] values, byte[] indices);
}
