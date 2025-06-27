package dapg.data.alias.product.util.valueprovider;

import dapg.data.alias.Alias;
import dapg.data.alias.product.api.Nm;

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
