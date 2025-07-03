package dapg.data.named.value.free;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;

public interface Vl<AliasT extends Alias<?>> {

    //fmt:off
    static <
          AliasT extends Alias<ValueT>,
          ValueT
    > Vl<AliasT> of(
          AliasKey<AliasT> key,
          ValueT value
    ) { //fmt:on
        return new AliasValue<>(key, value);
    }

    //fmt:off
    static <
          AliasT extends Alias<ValueT>,
          ValueT
    > ValueT v(
          AliasKey<AliasT> key,
          Vl<AliasT> vl
    ) { //fmt:on
        //noinspection unchecked
        return (ValueT) vl.untypedValue(key);
    }

    Object untypedValue(AliasKey<AliasT> key);
}
