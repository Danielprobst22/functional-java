package dapg.data.named.product.impl;

import dapg.data.named.AliasKey;
import dapg.data.named.product.NmProduct;

@FunctionalInterface
public interface UntypedNmProductConstructor {
    NmProduct apply(AliasKey<?>[] keys, Object[] values, byte[] indices);
}
