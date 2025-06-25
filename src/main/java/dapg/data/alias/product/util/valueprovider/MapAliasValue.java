package dapg.data.alias.product.util.valueprovider;

import dapg.data.alias.Alias;
import dapg.data.alias.product.api.Nm;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
public record MapAliasValue<
        NmInstanceT extends Nm,
        AliasT extends Alias<?>
        // todo proper impl
        >(int positionInProduct) implements AliasValueProvider<NmInstanceT, AliasT> {
}
