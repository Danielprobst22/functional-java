package dapg.data.named.product.api.markertraits.nm;

import dapg.data.named.Alias;
import dapg.data.named.product.Named;
import dapg.data.named.value.indexed.Vl1;
import dapg.data.named.value.indexed.Vl2;

//fmt:off
public interface Nm2<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>
>
    extends Named,
            Vl1<AliasT1>,
            Vl2<AliasT2>
{ //fmt:on
    int ARITY = 2;

    @Override
    default int arity() {
        return ARITY;
    }
}
