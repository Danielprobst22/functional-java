package dapg.examples.domainmodelling.union;

import org.junit.jupiter.api.Test;

public class OverlappingSealedHierarchiesTest {

    @Test
    void test() {

    }

    sealed interface Un2<PrimaryT, T2> permits UnionPrimaryValue, UnionSecondValue {}
    sealed interface Un3<PrimaryT, T2, T3> permits UnionPrimaryValue, UnionSecondValue, UnionThirdValue {}

    private record UnionPrimaryValue<PrimaryT>(PrimaryT value) implements Un2<PrimaryT, Object>, Un3<PrimaryT, Object, Object> {}
    private record UnionSecondValue<T2>(T2 value) implements Un2<Object, T2>, Un3<Object, T2, Object> {}
    private record UnionThirdValue<T3>(T3 value) implements Un3<Object, Object, T3> {}
}
