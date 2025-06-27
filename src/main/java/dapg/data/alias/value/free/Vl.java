package dapg.data.alias.value.free;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;

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
