package dapg.data.alias.value.indexed;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.value.free.Vl;

public interface Vl3<AliasT3 extends Alias<?>> {

    static <
            AliasT3 extends Alias<ValueT3>,
            ValueT3
            > ValueT3 v(
            AliasKey<AliasT3> key,
            Vl3<AliasT3> vl3
    ) {
        //noinspection unchecked
        return (ValueT3) vl3.untypedValue3(key);
    }

    Object untypedValue3(AliasKey<AliasT3> key);

    default Vl<AliasT3> v3ToVl() {
        return this::untypedValue3;
    }
}
