package dapg.data.alias.product.impl;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;

import static dapg.data.alias.product.util.internal.NmUtil.POSITION_V1;
import static dapg.data.alias.product.util.internal.NmUtil.POSITION_V2;

public abstract class NmProduct2<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?> & SuperLongCustomBullshitStuffThingy1 & SuperLongCustomBullshitStuffThingy2
>
    extends Nm
    implements Vl1<AliasT1>,
               Vl2<AliasT2>,
               SuperLongCustomBullshitStuffThingy1,
               SuperLongCustomBullshitStuffThingy2,
               SuperLongCustomBullshitStuffThingy3,
               SuperLongCustomBullshitStuffThingy4,
               SuperLongCustomBullshitStuffThingy5,
               SuperLongCustomBullshitStuffThingy6,
               SuperLongCustomBullshitStuffThingy7,
               SuperLongCustomBullshitStuffThingy8,
               SuperLongCustomBullshitStuffThingy9
{
    protected NmProduct2(Object[] values, AliasKey<?>[] keys, byte[] indices) {
        super(values, keys, indices);
    }

    @Override
    public Object untypedValue1(AliasKey<AliasT1> key) {
        return untypedValueAtPosition(key, POSITION_V1);
    }

    @Override
    public Object untypedValue2(AliasKey<AliasT2> key) {
        return untypedValueAtPosition(key, POSITION_V2);
    }

    static <
          AliasT1 extends Alias<ValueT1>,
          ValueT1,
          T1 extends SuperLongCustomBullshitStuffThingy1,
          T2 extends SuperLongCustomBullshitStuffThingy2,
          T3 extends SuperLongCustomBullshitStuffThingy3,
          T4 extends SuperLongCustomBullshitStuffThingy4,
          T5 extends SuperLongCustomBullshitStuffThingy5,
          T6 extends SuperLongCustomBullshitStuffThingy6,
          T7 extends SuperLongCustomBullshitStuffThingy7,
          T8 extends SuperLongCustomBullshitStuffThingy8,
          T9 extends SuperLongCustomBullshitStuffThingy9
    > ValueT1 v(
          AliasKey<AliasT1> key,
          Vl1<AliasT1> vl1
    ) {
        // noinspection unchecked
        return (ValueT1) vl1.untypedValue1(key);
    }
}

interface SuperLongCustomBullshitStuffThingy1 {}

interface SuperLongCustomBullshitStuffThingy2 {}

interface SuperLongCustomBullshitStuffThingy3 {}

interface SuperLongCustomBullshitStuffThingy4 {}

interface SuperLongCustomBullshitStuffThingy5 {}

interface SuperLongCustomBullshitStuffThingy6 {}

interface SuperLongCustomBullshitStuffThingy7 {}

interface SuperLongCustomBullshitStuffThingy8 {}

interface SuperLongCustomBullshitStuffThingy9 {}
