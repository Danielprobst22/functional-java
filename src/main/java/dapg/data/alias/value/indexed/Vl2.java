package dapg.data.alias.value.indexed;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.value.free.Vl;

public interface Vl2<AliasT2 extends Alias<?>> {

    static <
            AliasT2 extends Alias<ValueT2>,
            ValueT2
            > ValueT2 v(
            AliasKey<AliasT2> key,
            Vl2<AliasT2> vl2
    ) {
        //noinspection unchecked
        return (ValueT2) vl2.untypedValue2(key);
    }

    Object untypedValue2(AliasKey<AliasT2> key);

    default Vl<AliasT2> v2ToVl() {
        return this::untypedValue2;
    }
}
