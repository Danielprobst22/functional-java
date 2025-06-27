package dapg.data.alias.product.api;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.tuple.NmTup2;
import dapg.data.alias.product.impl.tuple.NmTup3;
import dapg.data.alias.product.util.internal.NmUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NmTuple {

    //region Factory methods
    //fmt:off
    public static <
          AliasT1 extends Alias<ValueT1>, ValueT1,
          AliasT2 extends Alias<ValueT2>, ValueT2
    > NmTup2<AliasT1, AliasT2> of(
          AliasKey<AliasT1> k1, ValueT1 v1,
          AliasKey<AliasT2> k2, ValueT2 v2
    ) { //fmt:on
        return new NmTup2<>(
                NmUtil.preallocateValuesArrayAndPut(v1, v2),
                NmUtil.preallocateKeysArrayAndPut(k1, k2),
                NmUtil.preallocateIndicesArrayAndFillForArity(NmTup2.ARITY)
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
        return new NmTup3<>(
                NmUtil.preallocateValuesArrayAndPut(v1, v2, v3),
                NmUtil.preallocateKeysArrayAndPut(k1, k2, k3),
                NmUtil.preallocateIndicesArrayAndFillForArity(NmTup3.ARITY)
        );
    }
    //endregion
}
