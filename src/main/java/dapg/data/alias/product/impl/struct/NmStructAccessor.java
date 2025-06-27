package dapg.data.alias.product.impl.struct;

import dapg.data.alias.AliasKey;
import dapg.data.alias.AliasWithStructAccessor;

public interface NmStructAccessor {

    //fmt:off
    static <
          AliasT extends AliasWithStructAccessor<AccessorT, ValueT>,
          AccessorT extends NmStructAccessor,
          ValueT
    > ValueT v(
          AliasKey<AliasT> key,
          AccessorT structWithAccessor
    ) { //fmt:on
        //noinspection unchecked
        return (ValueT) structWithAccessor.untypedFetch(key);
    }

    Object untypedFetch(AliasKey<?> key);
}
