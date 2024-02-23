package dapg.examples;

import dapg.control.option.None;
import dapg.control.option.Option;
import dapg.control.option.Some;
import dapg.control.result.Result;
import dapg.recursion.TailRecursion;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SumTreeTest {

    @Nested
    class MonomorphicTree {
        private static final SumTreeNode EMPTY_TREE = SumTreeNode.leaf(BigInteger.valueOf(42));
        /*
              26
             /  \
           10    3
          /  \    \
         4    6    3
         */
        private static final SumTreeNode VALID_TREE_1 = SumTreeNode.full(
                BigInteger.valueOf(26),
                SumTreeNode.full(
                        BigInteger.valueOf(10),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
                ),
                SumTreeNode.rightFull(
                        BigInteger.valueOf(3),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
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
                BigInteger.valueOf(26),
                SumTreeNode.full(
                        BigInteger.valueOf(10),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
                ),
                SumTreeNode.leftFull(
                        BigInteger.valueOf(3),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
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
                BigInteger.valueOf(26),
                SumTreeNode.rightFull(
                        BigInteger.valueOf(3),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
                ),
                SumTreeNode.full(
                        BigInteger.valueOf(10),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
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
                BigInteger.valueOf(26),
                SumTreeNode.leftFull(
                        BigInteger.valueOf(3),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
                ),
                SumTreeNode.full(
                        BigInteger.valueOf(10),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
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
                BigInteger.valueOf(26),
                SumTreeNode.full(
                        BigInteger.valueOf(11),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
                ),
                SumTreeNode.rightFull(
                        BigInteger.valueOf(3),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
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
                BigInteger.valueOf(26),
                SumTreeNode.full(
                        BigInteger.valueOf(10),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
                ),
                SumTreeNode.rightFull(
                        BigInteger.valueOf(4),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
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
                BigInteger.valueOf(27),
                SumTreeNode.full(
                        BigInteger.valueOf(10),
                        SumTreeNode.leaf(BigInteger.valueOf(4)),
                        SumTreeNode.leaf(BigInteger.valueOf(6))
                ),
                SumTreeNode.rightFull(
                        BigInteger.valueOf(3),
                        SumTreeNode.leaf(BigInteger.valueOf(3))
                )
        );
        private static final SumTreeNode UNBALANCED_TREE_WITH_DEPTH_TEN_THOUSAND = makeUnbalancedSumTree(10_000);

        @Test
        void shouldThrowStackOverflowError_whenEvaluatingUnbalancedTreeWithDepthTenThousandRecursively() {
            assertThrows(StackOverflowError.class, () -> RecursiveEvaluation.isValidSumTree(UNBALANCED_TREE_WITH_DEPTH_TEN_THOUSAND));
        }

        @Test
        void shouldReturnTrue_whenEvaluatingUnbalancedTreeWithDepthTenThousandTailRecursively() {
            assertTrue(TailRecursiveEvaluation.isValidSumTree(UNBALANCED_TREE_WITH_DEPTH_TEN_THOUSAND));
        }

        @Nested
        class RecursiveEvaluation {
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

            private static boolean isValidSumTree(SumTreeNode root) {
                return calculateSumRecursively(root).isOk();
            }

            private static Result<BigInteger, IllegalArgumentException> calculateSumRecursively(SumTreeNode currentNode) {
                return Result.<BigInteger, IllegalArgumentException>boundary(
                        IllegalArgumentException::new
                ).attempt(boundary -> {
                    if (currentNode.leftChild().isEmpty() && currentNode.rightChild().isEmpty()) {
                        return currentNode.value();
                    }

                    BigInteger leftChildSum = currentNode
                            .leftChild()
                            .map(child -> calculateSumRecursively(child).orBreak(boundary))
                            .getOrElse(BigInteger.valueOf(0));

                    BigInteger rightChildSum = currentNode
                            .rightChild()
                            .map(child -> calculateSumRecursively(child).orBreak(boundary))
                            .getOrElse(BigInteger.valueOf(0));

                    BigInteger totalChildSum = leftChildSum.add(rightChildSum);
                    if (!currentNode.value().equals(totalChildSum)) {
                        return boundary.breakErr(new IllegalArgumentException("Node has value = %d, but children have sum = %d".formatted(currentNode.value(), totalChildSum)));
                    } else {
                        return currentNode.value().add(totalChildSum);
                    }
                });
            }
        }

        @Nested
        class TailRecursiveEvaluation {
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

            private static boolean isValidSumTree(SumTreeNode root) {
                return TailRecursion.run(CurrentStep.initial(root),
                        (recursion, currentStep) -> switch (currentStep.next()) {
                            case NextStep.Continue(CurrentStep next) -> recursion.continue_(next);
                            case NextStep.Yield(boolean result) -> recursion.yield(result);
                        }
                );
            }

            private sealed interface ParentStep {
                NextStep provideSum(Option<BigInteger> childSum);

                record AwaitingLeftChild(
                        Option<ParentStep> parentStep,
                        SumTreeNode currentNode
                ) implements ParentStep {
                    @Override
                    public NextStep provideSum(Option<BigInteger> leftChildSum) {
                        AwaitingRightChild awaitingRightChildOfCurrent = new AwaitingRightChild(parentStep, currentNode, leftChildSum);
                        CurrentStep.VisitRightChild visitRightChildOfCurrent = new CurrentStep.VisitRightChild(awaitingRightChildOfCurrent, currentNode);
                        return new NextStep.Continue(visitRightChildOfCurrent);
                    }
                }

                record AwaitingRightChild(
                        Option<ParentStep> parentStep,
                        SumTreeNode currentNode,
                        Option<BigInteger> leftChildSum
                ) implements ParentStep {
                    @Override
                    public NextStep provideSum(Option<BigInteger> rightChildSum) {
                        CurrentStep.ValidateChildSum validateChildSum = new CurrentStep.ValidateChildSum(parentStep, currentNode, getTotalChildSum(leftChildSum, rightChildSum));
                        return new NextStep.Continue(validateChildSum);
                    }

                    private Option<BigInteger> getTotalChildSum(Option<BigInteger> leftChildSum, Option<BigInteger> rightChildSum) {
                        return Option
                                .and(leftChildSum, rightChildSum, BigInteger::add)
                                .orElse(leftChildSum)
                                .orElse(rightChildSum);
                    }
                }
            }

            private sealed interface CurrentStep {
                static CurrentStep initial(SumTreeNode root) {
                    ParentStep.AwaitingLeftChild awaitingLeftChildOfRoot = new ParentStep.AwaitingLeftChild(Option.none(), root);
                    return new CurrentStep.VisitLeftChild(awaitingLeftChildOfRoot, root);
                }

                NextStep next();

                record VisitLeftChild(
                        ParentStep parentStep,
                        SumTreeNode currentNode
                ) implements CurrentStep {
                    @Override
                    public NextStep next() {
                        return switch (currentNode.leftChild()) {
                            case Some(SumTreeNode leftChild) -> {
                                ParentStep.AwaitingLeftChild awaitingLeftChildOfLeftChild = new ParentStep.AwaitingLeftChild(Option.some(parentStep), leftChild);
                                CurrentStep.VisitLeftChild visitLeftChildOfLeftChild = new CurrentStep.VisitLeftChild(awaitingLeftChildOfLeftChild, leftChild);
                                yield new NextStep.Continue(visitLeftChildOfLeftChild);
                            }
                            case None() -> parentStep.provideSum(Option.none());
                        };
                    }
                }

                record VisitRightChild(
                        ParentStep parentStep,
                        SumTreeNode currentNode
                ) implements CurrentStep {
                    @Override
                    public NextStep next() {
                        return switch (currentNode.rightChild()) {
                            case Some(SumTreeNode rightChild) -> {
                                ParentStep.AwaitingLeftChild awaitingLeftChildOfRightChild = new ParentStep.AwaitingLeftChild(Option.some(parentStep), rightChild);
                                CurrentStep.VisitLeftChild visitLeftChildOfRightChild = new CurrentStep.VisitLeftChild(awaitingLeftChildOfRightChild, rightChild);
                                yield new NextStep.Continue(visitLeftChildOfRightChild);
                            }
                            case None() -> parentStep.provideSum(Option.none());
                        };
                    }
                }

                record ValidateChildSum(
                        Option<ParentStep> parentStep,
                        SumTreeNode currentNode,
                        Option<BigInteger> totalChildSum
                ) implements CurrentStep {
                    @Override
                    public NextStep next() {
                        return switch (totalChildSum) {
                            case Some(BigInteger totalChildSum) -> {
                                if (!currentNode.value().equals(totalChildSum)) {
                                    yield new NextStep.Yield(false);
                                } else {
                                    yield provideSumToParent(currentNode.value().add(totalChildSum));
                                }
                            }
                            case None() -> provideSumToParent(currentNode.value());
                        };
                    }

                    private NextStep provideSumToParent(BigInteger totalSum) {
                        return switch (parentStep) {
                            case Some(ParentStep parent) -> parent.provideSum(Option.some(totalSum));
                            case None() -> new NextStep.Yield(true);
                        };
                    }
                }
            }

            sealed interface NextStep {
                record Continue(CurrentStep next) implements NextStep {}

                record Yield(boolean result) implements NextStep {}
            }
        }

        private record SumTreeNode(
                BigInteger value,
                Option<SumTreeNode> leftChild,
                Option<SumTreeNode> rightChild
        ) {

            public static SumTreeNode full(BigInteger value, SumTreeNode leftChild, SumTreeNode rightChild) {
                return new SumTreeNode(value, Option.some(leftChild), Option.some(rightChild));
            }

            public static SumTreeNode leftFull(BigInteger value, SumTreeNode leftChild) {
                return new SumTreeNode(value, Option.some(leftChild), Option.none());
            }

            public static SumTreeNode rightFull(BigInteger value, SumTreeNode rightChild) {
                return new SumTreeNode(value, Option.none(), Option.some(rightChild));
            }

            public static SumTreeNode leaf(BigInteger value) {
                return new SumTreeNode(value, Option.none(), Option.none());
            }
        }

        //region makeUnbalanceSumTree
        private static SumTreeNode makeUnbalancedSumTree(int depth) {
            Iterator<BigInteger> valueIterator = getSumTreeNodeValueIterator();
            return TailRecursion.run(SumTreeNode.leaf(valueIterator.next()), depth,
                    (recursion, childNode, currentDepth) -> {
                        SumTreeNode currentNode = ThreadLocalRandom.current().nextBoolean() // randomly alternate between creating a left or a right child
                                ? SumTreeNode.leftFull(valueIterator.next(), childNode)
                                : SumTreeNode.rightFull(valueIterator.next(), childNode);
                        if (currentDepth == 0) {
                            return recursion.yield(currentNode);
                        } else {
                            return recursion.continue_(currentNode, currentDepth - 1);
                        }
                    }
            );
        }

        private static Iterator<BigInteger> getSumTreeNodeValueIterator() {
            return StreamEx
                    .iterate(BigInteger.valueOf(1), bigInteger -> bigInteger.multiply(BigInteger.valueOf(2)))
                    .prepend(BigInteger.valueOf(1)) // values should be: 1, 1, 2, 4, 8, 16, etc
                    .iterator();
        }
        //endregion
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
