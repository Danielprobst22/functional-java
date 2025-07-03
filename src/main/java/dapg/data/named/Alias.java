package dapg.data.named;

public interface Alias<ValueT> {

    // todo PlainAlias, PolymorphicAlias, NmStruct, NmList, NmMap, NmSet

    // todo just for testing
    public sealed interface Parent permits Mu, OpenChild {}

    public non-sealed interface OpenChild extends Parent {
        // other types can implement OpenChild freely
    }

    static non-sealed class Mu implements Parent {

    }
}
