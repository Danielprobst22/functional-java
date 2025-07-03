package dapg.data.named.product.impl.tuple;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NmTupleUtil {

    //fmt:off
    public static <
          AliasT1 extends Alias<?>,
          AliasT2 extends Alias<?>
    > NmTup2<AliasT1, AliasT2> makeNmTup2(
            AliasKey<?>[] keys,
            Object[] values,
            byte[] indices
    ) { //fmt:on
        return new NmTup2<>(keys, values, indices);
    }

    //fmt:off
    public static <
          AliasT1 extends Alias<?>,
          AliasT2 extends Alias<?>,
          AliasT3 extends Alias<?>
    > NmTup3<AliasT1, AliasT2, AliasT3> makeNmTup3(
            AliasKey<?>[] keys,
            Object[] values,
            byte[] indices
    ) { //fmt:on
        return new NmTup3<>(keys, values, indices);
    }
}
