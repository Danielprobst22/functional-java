package dapg.data.alias.product.api;

import dapg.data.alias.Alias;
import dapg.data.alias.AliasKey;
import dapg.data.alias.product.impl.struct.NmStruct2;
import dapg.data.alias.product.impl.struct.NmStructAccessor;
import dapg.data.alias.product.impl.struct.constructor.NmStruct2Constructor;
import dapg.data.alias.product.impl.tuple.NmTup2;


//public interface NmStruct<SelfT extends NmStruct<SelfT>> extends NmStructAccessor, Alias<SelfT>{


//public interface NmStruct<SelfT extends NmStruct<SelfT>>
//    extends NmStructAccessor,
//            Alias<SelfT> {



//public interface NmStruct<SelfT extends NmStruct<SelfT>>
//    extends NmStructAccessor, Alias<SelfT> {


public interface NmStruct<
    SelfT extends NmStruct<SelfT>
>
    extends NmStructAccessor,
            Alias<SelfT>
{
    //region Factory methods
    static <
          ResultT extends Nm & NmStruct<NmStruct2<AliasT1, AliasT2>>,
          AliasT1 extends Alias<?>,
          AliasT2 extends Alias<?>
    > ResultT make(
          NmStruct2Constructor<ResultT, AliasT1, AliasT2> constructor,
          NmTup2<AliasT1, AliasT2> tup2
    ) {
        return constructor.apply(tup2.values(), tup2.keys(), tup2.indices());
    }
    //endregion

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

    Nm asNmInstance();
}
