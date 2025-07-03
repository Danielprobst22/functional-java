package dapg.data.named.product.util.valueprovider;

import dapg.data.named.Alias;
import dapg.data.named.product.api.Nm;

//fmt:off
public sealed interface AliasValueProvider<
    NmInstanceT extends Nm,
    AliasT extends Alias<?>
>
    permits AddAliasValue,
            MapAliasValue,
            SelectAliasValue
{ //fmt:on

}
