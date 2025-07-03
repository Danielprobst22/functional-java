package dapg.data.named.value.free;

import dapg.data.named.Alias;
import dapg.data.named.AliasKey;

// package-private
final class AliasValue<AliasT extends Alias<?>> implements Vl<AliasT> { // todo proper impl
    private final AliasKey<AliasT> key;
    private final Object value;

    // package-private
    AliasValue(AliasKey<AliasT> key, Object value) {
        this.key = key;
        this.value = value;
    }

    // todo delete
    // package-private
    AliasKey<AliasT> key() {
        return key;
    }

    @Override
//    public Object untypedValue(AliasKey<?> key) {
    public Object untypedValue(AliasKey<AliasT> key) {
        return value;
    }

//    @Override
//    public String toString() { // todo proper impl
//        String className = this.getClass().getSimpleName();
//        return String.format("%s[value=%s]", className, value);
//    }

    // todo implement equals & hashCode
//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
}
