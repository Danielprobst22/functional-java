package dapg.data.named.product.api.addons.alias;

import dapg.data.named.Alias;
import dapg.data.named.product.impl.struct.NmStructAccessor;

//fmt:off
public interface AliasWithStructAccessor<
    AccessorT extends NmStructAccessor,
    ValueT
>
    extends Alias<ValueT>
{ //fmt:on

}
