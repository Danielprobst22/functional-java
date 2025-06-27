package dapg.data.alias.product.api;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.NmProduct;
import dapg.data.alias.product.impl.tuple.NmTup2;
import dapg.data.alias.product.util.valueprovider.AddAliasValue;
import dapg.data.alias.product.util.valueprovider.AliasValueProvider;
import dapg.data.alias.product.util.valueprovider.SelectAliasValue;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;

import static dapg.data.alias.product.util.internal.NmUtil.POSITION_V1;
import static dapg.data.alias.product.util.internal.NmUtil.POSITION_V2;

public abstract class Nm extends NmProduct {

    protected Nm(Object[] values, AliasKey<?>[] keys, byte[] indices) {
        super(values, keys, indices);
    }

    protected Object[] values() {
        return values;
    }

    protected AliasKey<?>[] keys() {
        return keys;
    }

    protected byte[] indices() {
        return indices;
    }

    //region Copy methods

    //fmt:off
    public static <
          NmInstanceT extends Nm,
          AliasT1 extends Alias<?>,
          AliasT2 extends Alias<?>
    > NmTup2<AliasT1, AliasT2> copy(
          NmInstanceT nmInstance,
          AliasValueProvider<NmInstanceT, AliasT1> avp1,
          AliasValueProvider<NmInstanceT, AliasT2> avp2
    ) { //fmt:on
        //noinspection unchecked
        return (NmTup2<AliasT1, AliasT2>) nmInstance.untypedCopy(NmTup2::new, avp1, avp2);
    }
    //endregion

    //--------------------- AliasValueProvider helper methods ---------------------//

    //region SelectAliasValue

    //fmt:off
    public static <
          NmInstanceT extends Nm & Vl1<AliasT1>,
          AliasT1 extends Alias<?>
    > AliasValueProvider<NmInstanceT, AliasT1> v1() { //fmt:on
        return new SelectAliasValue<>(POSITION_V1);
    }

    //fmt:off
    public static <
          NmInstanceT extends Nm & Vl2<AliasT2>,
          AliasT2 extends Alias<?>
    > AliasValueProvider<NmInstanceT, AliasT2> v2() { //fmt:on
        return new SelectAliasValue<>(POSITION_V2);
    }
    //endregion

    //region AddAliasValue

    //fmt:off
    public static <
          NmInstanceT extends Nm,
          AddAliasT extends Alias<AddValueT>,
          AddValueT
    > AliasValueProvider<NmInstanceT, AddAliasT> add(
          AliasKey<AddAliasT> key,
          AddValueT value
    ) { //fmt:on
        return new AddAliasValue<>(key, value);
    }
    //endregion
}
