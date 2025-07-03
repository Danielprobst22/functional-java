package dapg.data.alias.product.api.addons.key.internal;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.value.free.Vl;
import dapg.data.alias.value.indexed.Vl1;
import dapg.data.alias.value.indexed.Vl2;
import dapg.data.alias.value.indexed.Vl3;

// todo import org.jetbrains.annotations.ApiStatus;
//  @ApiStatus.Internal
//fmt:off
public interface AliasKeyWithValueAccessors<
    AliasT extends Alias<ValueT>,
    ValueT
>
    extends AliasKey<AliasT>
{ //fmt:on
    default ValueT vl(Vl<AliasT> vl) {
        return Vl.v(this, vl);
    }

    default ValueT v1(Vl1<AliasT> vl1) {
        return Vl1.v(this, vl1);
    }

    default ValueT v2(Vl2<AliasT> vl2) {
        return Vl2.v(this, vl2);
    }

    default ValueT v3(Vl3<AliasT> vl3) {
        return Vl3.v(this, vl3);
    }
}
