package dapg.data.alias.product.impl;

import dapg.data.alias.AliasKey;
import dapg.data.alias.product.NmProduct;

@FunctionalInterface
public interface UntypedNmProductConstructor {
    NmProduct apply(Object[] values, AliasKey<?>[] keys, byte[] indices);
}
