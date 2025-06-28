package dapg.data.alias.product.util.valueprovider;

import dapg.data.alias.Alias;
import dapg.data.alias.product.api.Nm;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
//fmt:off
public record MapAliasValue<
    NmInstanceT extends Nm,
    AliasT extends Alias<?>
> (
    int positionInProduct
    // todo proper impl -> add mapping function
)
    implements AliasValueProvider<NmInstanceT, AliasT>
{ //fmt:on

}
