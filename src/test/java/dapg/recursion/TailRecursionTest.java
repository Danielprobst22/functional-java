package dapg.recursion;

import org.junit.jupiter.api.Test;

class TailRecursionTest {

    // todo remove demo code and create proper test cases
    @Test
    void test() {
        int limit = 5_000;
        System.out.println(sumIterative(limit));
        System.out.println(sumTailRecursive(limit));
        System.out.println(sumRecursive(limit));

//        try {
////            System.out.println(sumRecursive(8_000)); // Stack depth: 7783
////            System.out.println(sumRecursive(7783)); // Result: 30291436
////            System.out.println(sumRecursive(7784)); // Stack depth: 7783
//
////            int limit = 10_000;
////            for (int i = limit; i < limit + 1; i++) {
////                int result = sumRecursive(i);
////                System.out.println(result); // Stack depth: 7783
////            }
//
////            for (int limit = 1_000; limit < 100_000; limit++) { // Stack depth: 58382
//                int result = sumRecursive(limit); // limit: 58382 - result: 1704258153
////            int result = sumTailRecursive(limit);
//                System.out.println("limit: %d - result: %d".formatted(limit, result));
//            }
//
//        } catch (Throwable e) {
//            System.out.println("Stack depth: %d".formatted(stackDepth));
//        }
    }

    private int factorialIterative(int value) {
        int result = 1;
        for (int i = value; i > 0; i--) {
            result *= i;
        }
        return result;
    }

    private int factorialTailRecursive(int value) {
        return TailRecursion.run(1, value, (recursion, accumulatedResult, v) -> {
            if (v == 1) {
                return recursion.yield(accumulatedResult);
            } else {
                return recursion.continue_(accumulatedResult * v, v - 1);
            }
        });
    }

    private int sumIterative(int limit) {
        int result = 0;
        for (int i = 1; i <= limit; i++) {
            result += i;
        }
        return result;
    }

    private int sumTailRecursive(int limit) {
        return TailRecursion.run(0, 1, (recursion, accumulatedResult, v) -> {
            if (v == limit) {
                return recursion.yield(accumulatedResult + v);
            } else {
                return recursion.continue_(accumulatedResult + v, v + 1);
            }
        });
    }

    private int sumRecursive(int limit) {
        return iterate(limit, 0, 1);
    }

    private int iterate(int limit, int accumulatedResult, int v) {
        if (v == limit) {
            return accumulatedResult + v;
        } else {
            return iterate(limit, accumulatedResult + v, v + 1);
        }
    }
}
