package dapg.data.alias.value.indexed;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.value.free.Vl;

public interface Vl1<AliasT1 extends Alias<?>> {

    static <
        AliasT1 extends Alias<ValueT1>,
        ValueT1
    > ValueT1 test(
        AliasKey<AliasT1> key,
        Vl1<AliasT1> vl1
    ) {
        //noinspection unchecked
        return (ValueT1) vl1.untypedValue1(key);
    }

//    private <
//    public <
//    static <
    static <
          AliasT1 extends Alias<ValueT1>,
          ValueT1
    > ValueT1 test2(
          AliasKey<AliasT1> key,
          Vl1<AliasT1> vl1
    ) {
        //noinspection unchecked
        return (ValueT1) vl1.untypedValue1(key);
    }


    static <
            AliasT1 extends Alias<ValueT1>,
            ValueT1
    > ValueT1 v(
            AliasKey<AliasT1> key,
            Vl1<AliasT1> vl1
    ) {
        //noinspection unchecked
        return (ValueT1) vl1.untypedValue1(key);
    }

    Object untypedValue1(AliasKey<AliasT1> key);

    default Vl<AliasT1> v1ToVl() {
        return this::untypedValue1;
    }
}
