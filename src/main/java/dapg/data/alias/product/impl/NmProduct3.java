package dapg.data.alias.product.impl;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.api.Nm;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;
import dapg.data.alias.value.indexed.Vl3;

import static dapg.data.alias.product.util.internal.NmUtil.*;

//public abstract class NmProduct3<

/*
public class NmProduct3<
             AliasT1 extends Alias<?>,
             AliasT2 extends Alias<?>,
             AliasT3 extends Alias<?>
>
  extends Nm
  implements Vl1<AliasT1>,
             Vl2<AliasT2>,
             Vl3<AliasT3>
{
*/


/*
public class NmProduct3<
        AliasT1 extends Alias<?>,
        AliasT2 extends Alias<?>,
        AliasT3 extends Alias<?>
>
    extends Nm
    implements Vl1<AliasT1>,
               Vl2<AliasT2>,
               Vl3<AliasT3>
{
*/



public class NmProduct3<
    AliasT1 extends Alias<?>,
    AliasT2 extends Alias<?>,
    AliasT3 extends Alias<?>
>
    extends Nm
    implements Vl1<AliasT1>,
               Vl2<AliasT2>,
               Vl3<AliasT3>
{
    protected NmProduct3(Object[] values, AliasKey<?>[] keys, byte[] indices) {
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

    @Override
    public Object untypedValue3(AliasKey<AliasT3> key) {
        return untypedValueAtPosition(key, POSITION_V3);
    }
}
