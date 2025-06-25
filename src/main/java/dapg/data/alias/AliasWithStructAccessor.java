package dapg.data.alias;

import dapg.data.alias.product.impl.struct.NmStructAccessor;

public interface AliasWithStructAccessor<AccessorT extends NmStructAccessor, ValueT> extends Alias<ValueT> {
}
