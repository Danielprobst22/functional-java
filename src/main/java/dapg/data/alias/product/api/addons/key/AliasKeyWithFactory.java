package dapg.data.alias.product.api.addons.key;

import dapg.data.alias.Alias;
import dapg.data.alias.product.api.addons.key.internal.AliasKeyWithValueAccessors;
import dapg.data.alias.value.free.Vl;

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
