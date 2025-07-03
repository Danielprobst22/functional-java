package dapg.data.named.product.api;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.NmProduct;
import dapg.data.named.product.impl.tuple.NmTup2;
import dapg.data.named.product.impl.tuple.NmTup3;
import dapg.data.named.product.impl.tuple.NmTupleUtil;
import dapg.data.named.product.util.valueprovider.AddAliasValue;
import dapg.data.named.product.util.valueprovider.AliasValueProvider;
import dapg.data.named.product.util.valueprovider.SelectAliasValue;
import dapg.data.named.value.indexed.Vl1;
import dapg.data.named.value.indexed.Vl2;
import dapg.data.named.value.indexed.Vl3;

import static dapg.data.named.product.util.internal.NmUtil.*;

public abstract class Nm extends NmProduct {

    protected Nm(AliasKey<?>[] keys, Object[] values) {
        super(keys, values);
    }

    protected final AliasKey<?>[] keys() {
        return keys;
    }

    protected final Object[] values() {
        return values;
    }

    protected abstract byte[] copyIndices();

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
        return (NmTup2<AliasT1, AliasT2>) nmInstance.untypedCopy(NmTupleUtil::makeNmTup2, avp1, avp2);
    }

    //fmt:off
    public static <
          NmInstanceT extends Nm,
          AliasT1 extends Alias<?>,
          AliasT2 extends Alias<?>,
          AliasT3 extends Alias<?>
    > NmTup3<AliasT1, AliasT2, AliasT3> copy(
          NmInstanceT nmInstance,
          AliasValueProvider<NmInstanceT, AliasT1> avp1,
          AliasValueProvider<NmInstanceT, AliasT2> avp2,
          AliasValueProvider<NmInstanceT, AliasT3> avp3
    ) { //fmt:on
        //noinspection unchecked
        return (NmTup3<AliasT1, AliasT2, AliasT3>) nmInstance.untypedCopy(NmTupleUtil::makeNmTup3, avp1, avp2, avp3);
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

    //fmt:off
    public static <
          NmInstanceT extends Nm & Vl3<AliasT3>,
          AliasT3 extends Alias<?>
    > AliasValueProvider<NmInstanceT, AliasT3> v3() { //fmt:on
        return new SelectAliasValue<>(POSITION_V3);
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
