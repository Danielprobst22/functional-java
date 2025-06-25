package dapg.data.alias.value.free;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;

public interface Vl<AliasT extends Alias<?>> {

    static <
          AliasT extends Alias<ValueT>,
          ValueT
    > Vl<AliasT> of(
          AliasKey<AliasT> key,
          ValueT value
    ) {
        return new AliasValue<>(key, value);
    }

    static <
            AliasT extends Alias<ValueT>,
            ValueT
    > ValueT v(
            AliasKey<AliasT> key,
            Vl<AliasT> vl
    ) {
        //noinspection unchecked
        return (ValueT) vl.untypedValue(key);
    }

    Object untypedValue(AliasKey<AliasT> key);
}
