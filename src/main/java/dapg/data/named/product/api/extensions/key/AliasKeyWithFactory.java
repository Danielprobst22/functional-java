package dapg.data.named.product.api.extensions.key;

import dapg.data.named.Alias;
import dapg.data.named.product.api.extensions.key.internal.AliasKeyWithValueAccessors;
import dapg.data.named.value.free.Vl;

//fmt:off
public interface AliasKeyWithFactory<
    AliasT extends Alias<ValueT>,
    ValueT
>
    extends AliasKeyWithValueAccessors<AliasT, ValueT>
{ //fmt:on
    default Vl<AliasT> make(ValueT value) {
        return Vl.of(this, value);
    }
}
