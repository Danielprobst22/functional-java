package dapg.data.alias.product.api.markertraits.nm;

import dapg.data.alias.Alias;
import dapg.data.alias.value.indexed.Vl3;

//fmt:off
public interface Nm3<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>,
    AliasT3 extends Alias<?>
>
    extends Nm2<AliasT1, AliasT2>,
            Vl3<AliasT3>
{ //fmt:on
    int ARITY = 3;

    @Override
    default int arity() {
        return ARITY;
    }
}
