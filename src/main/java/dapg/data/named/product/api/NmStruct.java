package dapg.data.named.product.api;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;
import dapg.data.named.product.impl.struct.NmStruct2;
import dapg.data.named.product.impl.struct.NmStructAccessor;
import dapg.data.named.product.impl.struct.constructor.NmStruct2Constructor;
import dapg.data.named.product.impl.tuple.NmTup2;

//fmt:off
public interface NmStruct<
    SelfT extends NmStruct<SelfT>
>
    extends NmStructAccessor,
            Alias<SelfT>
{ //fmt:on

    @Override
    default Object untypedFetch(AliasKey<?> key) {
        Nm self = asNmInstance();
        for (int currentPosition = 0; currentPosition < self.keys().length; currentPosition++) {
            // todo explain
            if (key.equals(self.keys()[currentPosition])) {
                return self.values()[currentPosition];
            }
        }
        throw new IllegalArgumentException(); // todo proper impl -> add error helper method to NmProd
    }

    // todo move to Named if possible
    Nm asNmInstance();

    //region Factory methods

    //fmt:off
    static <
          ResultT extends Nm & NmStruct<NmStruct2<AliasT1, AliasT2>>,
          AliasT1 extends Alias<?>,
          AliasT2 extends Alias<?>
    > ResultT make(
          NmStruct2Constructor<ResultT, AliasT1, AliasT2> constructor,
          NmTup2<AliasT1, AliasT2> tup2
    ) { //fmt:on
        return constructor.apply(tup2.keys(), tup2.values(), ((Nm) tup2).copyIndices());
    }
    //endregion
}
