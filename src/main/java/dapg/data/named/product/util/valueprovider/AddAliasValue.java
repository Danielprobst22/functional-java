package dapg.data.named.product.util.valueprovider;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.api.Nm;

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
