package dapg.data.alias.product.api.addons.alias;

import dapg.data.alias.Alias;
import dapg.data.alias.product.impl.struct.NmStructAccessor;

//fmt:off
public interface AliasWithStructAccessor<
    AccessorT extends NmStructAccessor,
    ValueT
>
    extends Alias<ValueT>
{ //fmt:on

}
