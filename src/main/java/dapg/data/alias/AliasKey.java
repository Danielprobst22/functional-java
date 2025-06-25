package dapg.data.alias;

public interface AliasKey<AliasT extends Alias<?>> {
    default String displayName() {
        return this.getClass().getSimpleName();
    }
}
