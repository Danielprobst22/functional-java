package dapg.data.named.value.indexed;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.value.free.Vl;

public interface Vl3<AliasT3 extends Alias<?>> {

    //fmt:off
    static <
          AliasT3 extends Alias<ValueT3>,
          ValueT3
    > ValueT3 v(
          AliasKey<AliasT3> key,
          Vl3<AliasT3> vl3
    ) { //fmt:on
        //noinspection unchecked
        return (ValueT3) vl3.untypedValue3(key);
    }

    Object untypedValue3(AliasKey<AliasT3> key);

    default Vl<AliasT3> v3ToVl() {
        return this::untypedValue3;
    }
}
