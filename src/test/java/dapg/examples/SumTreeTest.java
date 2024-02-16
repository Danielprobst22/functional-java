package dapg.examples;

import dapg.control.option.Option;
import dapg.control.result.Result;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SumTreeTest {

    @Nested
    class MonomorphicTree {
        private static final SumTreeNode EMPTY_TREE = SumTreeNode.leaf(42);
        /*
              26
             /  \
           10    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode VALID_TREE_1 = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );
        /*
               26
             /    \
           10      3
          /  \    /
         4    6  3
         */
        private static final SumTreeNode VALID_TREE_2 = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.leftFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );
        /*
               26
             /    \
            3     10
            \    /  \
             3  4    6
         */
        private static final SumTreeNode VALID_TREE_3 = SumTreeNode.full(
                26,
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                ),
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                )
        );
        /*
               26
             /    \
            3     10
           /     /  \
          3     4    6
         */
        private static final SumTreeNode VALID_TREE_4 = SumTreeNode.full(
                26,
                SumTreeNode.leftFull(
                        3,
                        SumTreeNode.leaf(3)
                ),
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                )
        );
        /*
              26
             /  \
           11    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode TREE_WITH_INVALID_LEFT_CHILD = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        11,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );
        /*
              26
             /  \
           10    4
          /  \    \
         4    6    3
         */
        private static final SumTreeNode TREE_WITH_INVALID_RIGHT_CHILD = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        4,
                        SumTreeNode.leaf(3)
                )
        );
        /*
              27
             /  \
           10    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode TREE_WITH_INVALID_ROOT = SumTreeNode.full(
                27,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );

        @Test
        void shouldReturnTrue_whenEvaluatingEmptyTree() {
            System.out.println(calculateSumRecursively(EMPTY_TREE)); // todo just for illustration
            assertTrue(isValidSumTree(EMPTY_TREE));
        }

        @ParameterizedTest
        @MethodSource("validTrees")
        void shouldReturnTrue_whenEvaluatingValidTree(SumTreeNode validTree) {
            System.out.println(calculateSumRecursively(validTree)); // todo just for illustration
            assertTrue(isValidSumTree(validTree));
        }

        static Stream<SumTreeNode> validTrees() {
            return Stream.of(VALID_TREE_1, VALID_TREE_2, VALID_TREE_3, VALID_TREE_4);
        }

        @Test
        void shouldReturnFalse_whenEvaluatingTreeWithInvalidLeftChild() {
            System.out.println(calculateSumRecursively(TREE_WITH_INVALID_LEFT_CHILD)); // todo just for illustration
            assertFalse(isValidSumTree(TREE_WITH_INVALID_LEFT_CHILD));
        }

        @Test
        void shouldReturnFalse_whenEvaluatingTreeWithInvalidRightChild() {
            System.out.println(calculateSumRecursively(TREE_WITH_INVALID_RIGHT_CHILD)); // todo just for illustration
            assertFalse(isValidSumTree(TREE_WITH_INVALID_RIGHT_CHILD));
        }

        @Test
        void shouldReturnFalse_whenEvaluatingTreeWithInvalidRoot() {
            System.out.println(calculateSumRecursively(TREE_WITH_INVALID_ROOT)); // todo just for illustration
            assertFalse(isValidSumTree(TREE_WITH_INVALID_ROOT));
        }

        private boolean isValidSumTree(SumTreeNode root) {
            return calculateSumRecursively(root).isOk();
        }

        private Result<Integer, IllegalArgumentException> calculateSumRecursively(SumTreeNode currentNode) {
            return Result.<Integer, IllegalArgumentException>boundary(
                    IllegalArgumentException::new
            ).attempt(boundary -> {
                if (currentNode.leftChild().isEmpty() && currentNode.rightChild().isEmpty()) {
                    return currentNode.value();
                }

                int leftChildSum = currentNode
                        .leftChild()
                        .map(child -> calculateSumRecursively(child).orBreak(boundary))
                        .getOrElse(0);

                int rightChildSum = currentNode
                        .rightChild()
                        .map(child -> calculateSumRecursively(child).orBreak(boundary))
                        .getOrElse(0);

                int totalChildSum = leftChildSum + rightChildSum;
                if (currentNode.value() != totalChildSum) {
                    return boundary.breakErr(new IllegalArgumentException("Node has value = %d, but children have sum = %d".formatted(currentNode.value(), totalChildSum)));
                } else {
                    return currentNode.value() + totalChildSum;
                }
            });
        }

        private record SumTreeNode(
                int value,
                Option<SumTreeNode> leftChild,
                Option<SumTreeNode> rightChild
        ) {

            public static SumTreeNode full(int value, SumTreeNode leftChild, SumTreeNode rightChild) {
                return new SumTreeNode(value, Option.some(leftChild), Option.some(rightChild));
            }

            public static SumTreeNode leftFull(int value, SumTreeNode leftChild) {
                return new SumTreeNode(value, Option.some(leftChild), Option.none());
            }

            public static SumTreeNode rightFull(int value, SumTreeNode rightChild) {
                return new SumTreeNode(value, Option.none(), Option.some(rightChild));
            }

            public static SumTreeNode leaf(int value) {
                return new SumTreeNode(value, Option.none(), Option.none());
            }
        }
    }

    @Nested
    class PolymorphicTree {
        private static final SumTreeNode EMPTY_TREE = SumTreeNode.leaf(42);
        /*
              26
             /  \
           10    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode VALID_TREE_1 = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );
        /*
               26
             /    \
           10      3
          /  \    /
         4    6  3
         */
        private static final SumTreeNode VALID_TREE_2 = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.leftFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );
        /*
               26
             /    \
            3     10
            \    /  \
             3  4    6
         */
        private static final SumTreeNode VALID_TREE_3 = SumTreeNode.full(
                26,
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                ),
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                )
        );
        /*
               26
             /    \
            3     10
           /     /  \
          3     4    6
         */
        private static final SumTreeNode VALID_TREE_4 = SumTreeNode.full(
                26,
                SumTreeNode.leftFull(
                        3,
                        SumTreeNode.leaf(3)
                ),
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                )
        );
        /*
              26
             /  \
           11    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode TREE_WITH_INVALID_LEFT_CHILD = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        11,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );
        /*
              26
             /  \
           10    4
          /  \    \
         4    6    3
         */
        private static final SumTreeNode TREE_WITH_INVALID_RIGHT_CHILD = SumTreeNode.full(
                26,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        4,
                        SumTreeNode.leaf(3)
                )
        );
        /*
              27
             /  \
           10    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode TREE_WITH_INVALID_ROOT = SumTreeNode.full(
                27,
                SumTreeNode.full(
                        10,
                        SumTreeNode.leaf(4),
                        SumTreeNode.leaf(6)
                ),
                SumTreeNode.rightFull(
                        3,
                        SumTreeNode.leaf(3)
                )
        );

        @Test
        void shouldReturnTrue_whenEvaluatingEmptyTree() {
            assertTrue(isValidSumTree(EMPTY_TREE));
        }

        @ParameterizedTest
        @MethodSource("validTrees")
        void shouldReturnTrue_whenEvaluatingValidTree(SumTreeNode validTree) {
            assertTrue(isValidSumTree(validTree));
        }

        static Stream<SumTreeNode> validTrees() {
            return Stream.of(VALID_TREE_1, VALID_TREE_2, VALID_TREE_3, VALID_TREE_4);
        }

        @Test
        void shouldReturnFalse_whenEvaluatingTreeWithInvalidLeftChild() {
            assertFalse(isValidSumTree(TREE_WITH_INVALID_LEFT_CHILD));
        }

        @Test
        void shouldReturnFalse_whenEvaluatingTreeWithInvalidRightChild() {
            assertFalse(isValidSumTree(TREE_WITH_INVALID_RIGHT_CHILD));
        }

        @Test
        void shouldReturnFalse_whenEvaluatingTreeWithInvalidRoot() {
            assertFalse(isValidSumTree(TREE_WITH_INVALID_ROOT));
        }

        private boolean isValidSumTree(SumTreeNode node) {
            return switch (node) {
                case SumTreeNode.Branch(int value, Option<SumTreeNode> leftChild, Option<SumTreeNode> rightChild) -> {
                    if (!leftChild.map(this::isValidSumTree).getOrElse(true)) {
                        yield false;
                    }

                    if (!rightChild.map(this::isValidSumTree).getOrElse(true)) {
                        yield false;
                    }

                    int totalChildSum = leftChild.map(this::getTotalValueOfNode).getOrElse(0)
                            + rightChild.map(this::getTotalValueOfNode).getOrElse(0);
                    yield value == totalChildSum;
                }
                case SumTreeNode.Leaf(int value) -> true;
            };
        }

        private int getTotalValueOfNode(SumTreeNode node) {
            return switch (node) {
                case SumTreeNode.Branch branch -> branch.value() * 2;
                case SumTreeNode.Leaf leaf -> leaf.value();
            };
        }

        private sealed interface SumTreeNode {
            static SumTreeNode full(int value, SumTreeNode leftChild, SumTreeNode rightChild) {
                return new SumTreeNode.Branch(value, Option.some(leftChild), Option.some(rightChild));
            }

            static SumTreeNode leftFull(int value, SumTreeNode leftChild) {
                return new SumTreeNode.Branch(value, Option.some(leftChild), Option.none());
            }

            static SumTreeNode rightFull(int value, SumTreeNode rightChild) {
                return new SumTreeNode.Branch(value, Option.none(), Option.some(rightChild));
            }

            static SumTreeNode leaf(int value) {
                return new SumTreeNode.Leaf(value);
            }

            record Branch(
                    int value,
                    Option<SumTreeNode> leftChild,
                    Option<SumTreeNode> rightChild
            ) implements SumTreeNode {}

            record Leaf(int value) implements SumTreeNode {}
        }
    }
}
