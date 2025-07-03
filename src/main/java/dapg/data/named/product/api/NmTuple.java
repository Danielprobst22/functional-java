package dapg.data.named.product.api;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.api.markertraits.nm.Nm2;
import dapg.data.named.product.api.markertraits.nm.Nm3;
import dapg.data.named.product.impl.tuple.NmTup2;
import dapg.data.named.product.impl.tuple.NmTup3;
import dapg.data.named.product.impl.tuple.NmTupleUtil;
import dapg.data.named.product.util.internal.NmUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NmTuple { // todo add SelfT and extend Alias<SelfT>

    //region Factory methods
    //fmt:off
    public static <
          AliasT1 extends Alias<ValueT1>, ValueT1,
          AliasT2 extends Alias<ValueT2>, ValueT2
    > NmTup2<AliasT1, AliasT2> of(
          AliasKey<AliasT1> k1, ValueT1 v1,
          AliasKey<AliasT2> k2, ValueT2 v2
    ) { //fmt:on
        return NmTupleUtil.makeNmTup2(
                NmUtil.preallocateKeysArrayAndPut(k1, k2),
                NmUtil.preallocateValuesArrayAndPut(v1, v2),
                NmUtil.defaultIndicesForArity(Nm2.ARITY)
        );
    }

    //fmt:off
    public static <
          AliasT1 extends Alias<ValueT1>, ValueT1,
          AliasT2 extends Alias<ValueT2>, ValueT2,
          AliasT3 extends Alias<ValueT3>, ValueT3
    > NmTup3<AliasT1, AliasT2, AliasT3> of(
          AliasKey<AliasT1> k1, ValueT1 v1,
          AliasKey<AliasT2> k2, ValueT2 v2,
          AliasKey<AliasT3> k3, ValueT3 v3
    ) { //fmt:on
        return NmTupleUtil.makeNmTup3(
                NmUtil.preallocateKeysArrayAndPut(k1, k2, k3),
                NmUtil.preallocateValuesArrayAndPut(v1, v2, v3),
                NmUtil.defaultIndicesForArity(Nm3.ARITY)
        );
    }
    //endregion
}
