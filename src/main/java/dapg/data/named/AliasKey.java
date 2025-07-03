package dapg.data.named;

public interface AliasKey<AliasT extends Alias<?>> {
    default String displayName() {
        return this.getClass().getSimpleName();
    }
}
