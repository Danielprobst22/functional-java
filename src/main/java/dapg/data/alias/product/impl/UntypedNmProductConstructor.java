package dapg.data.alias.product.impl;

import dapg.data.alias.AliasKey;
import dapg.data.alias.product.NmProduct;

@FunctionalInterface
public interface UntypedNmProductConstructor {
    NmProduct apply(AliasKey<?>[] keys, Object[] values, byte[] indices);
}
