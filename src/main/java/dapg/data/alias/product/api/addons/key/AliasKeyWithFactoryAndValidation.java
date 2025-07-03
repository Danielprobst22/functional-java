package dapg.data.alias.product.api.addons.key;

import dapg.control.option.None;
import dapg.control.option.Option;
import dapg.control.option.Some;
import dapg.control.result.Result;
import dapg.data.alias.Alias;
import dapg.data.alias.product.api.addons.key.internal.AliasKeyWithValueAccessors;
import dapg.data.alias.value.free.Vl;

//fmt:off
public interface AliasKeyWithFactoryAndValidation<
    AliasT extends Alias<ValueT>,
    ValueT,
    ErrorT
>
    extends AliasKeyWithValueAccessors<AliasT, ValueT>
{ //fmt:on
    default Result<Vl<AliasT>, ErrorT> tryMake(ValueT value) {
        return switch (validate(value)) {
            case None() -> Result.ok(Vl.of(this, value));
            case Some(ErrorT error) -> Result.err(error);
        };
    }

    Option<ErrorT> validate(ValueT value);
}
