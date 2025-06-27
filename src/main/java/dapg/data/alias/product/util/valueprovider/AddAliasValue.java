package dapg.data.alias.product.util.valueprovider;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
//fmt:off
public record AddAliasValue<
    NmInstanceT extends Nm,
    AliasT extends Alias<?>
> (
    AliasKey<AliasT> key,
    Object value
)
    implements AliasValueProvider<NmInstanceT, AliasT>
{ //fmt:on

}
