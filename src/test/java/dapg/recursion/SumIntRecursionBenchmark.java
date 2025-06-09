package dapg.recursion;

import io.vavr.Function2;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@SuppressWarnings("NewClassNamingConvention") // ignore "Test class name 'RecursionBenchmark' doesn't match regex '[A-Z][A-Za-z\d]*Test(s|Case)?|Test[A-Z][A-Za-z\d]*|IT(.*)|(.*)IT(Case)?'"
public class SumIntRecursionBenchmark {
    private static final int LIMIT = 5_000;
    //    private static final int ITERATIONS = 10;
    private static final int ITERATIONS = 25;
    private static final int TIME = 100;
//    private static final int TIME = 1_000;

    @Test
    void test() {
        int expected = (LIMIT + 1) * (LIMIT / 2);
        assertEquals(expected, sumIterative());
        assertEquals(expected, sumLambdaTailRecursive());
        assertEquals(expected, sumHelperFunctionTailRecursive());
        assertEquals(expected, sumRecursive());
        assertEquals(expected, sumUnoptimizedTailRecursive());
    }

    /*
        Benchmark                                                       Mode  Cnt      Score      Error  Units
        SumRecursionBenchmark._1_measureSumIterative                    avgt   25   1448.693 ±  157.815  ns/op
        SumRecursionBenchmark._2_measureSumLambdaTailRecursive          avgt   25  30402.430 ± 1320.264  ns/op
        SumRecursionBenchmark._3_measureSumHelperFunctionTailRecursive  avgt   25  31599.096 ± 2122.089  ns/op
        SumRecursionBenchmark._4_measureSumRecursive                    avgt   25  18607.253 ±  166.797  ns/op
        SumRecursionBenchmark._5_measureSumUnoptimizedTailRecursive     avgt   25  36611.836 ± 2865.110  ns/op
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(SumIntRecursionBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Benchmark
    @Warmup(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public int _1_measureSumIterative() {
        return sumIterative();
    }

    private int sumIterative() {
        int result = 0;
        for (int i = 1; i <= LIMIT; i++) {
            result += i;
        }
        return result;
    }

    @Benchmark
    @Warmup(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public int _2_measureSumLambdaTailRecursive() {
        return sumLambdaTailRecursive();
    }

    private int sumLambdaTailRecursive() {
        return TailRecursion.run(0, 1,
                (recursion, accumulatedResult, i) -> {
                    if (i == LIMIT) {
                        return recursion.yield(accumulatedResult + i);
                    } else {
                        return recursion.continue_(accumulatedResult + i, i + 1);
                    }
                }
        );
    }

    @Benchmark
    @Warmup(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public int _3_measureSumHelperFunctionTailRecursive() {
        return sumHelperFunctionTailRecursive();
    }

    private int sumHelperFunctionTailRecursive() {
        return TailRecursion.run(0, 1, SumIntRecursionBenchmark::addNextValue);
    }

    private static TailRecursion.Recursion2<Integer, Integer, Integer> addNextValue(
            TailRecursion.Recursion2<Integer, Integer, Integer> recursion,
            Integer accumulatedResult,
            Integer i
    ) {
        if (i == LIMIT) {
            return recursion.yield(accumulatedResult + i);
        } else {
            return recursion.continue_(accumulatedResult + i, i + 1);
        }
    }

    @Benchmark
    @Warmup(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public int _4_measureSumRecursive() {
        return sumRecursive();
    }

    private int sumRecursive() {
        return iterate(LIMIT, 0, 1);
    }

    private int iterate(int limit, int accumulatedResult, int i) {
        if (i == limit) {
            return accumulatedResult + i;
        } else {
            return iterate(limit, accumulatedResult + i, i + 1);
        }
    }

    @Benchmark
    @Warmup(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public int _5_measureSumUnoptimizedTailRecursive() {
        return sumUnoptimizedTailRecursive();
    }

    private int sumUnoptimizedTailRecursive() {
        return runUnoptimized(0, 1, (accumulatedResult, i) -> {
            if (i == LIMIT) {
                return new UnoptimizedYield2<>(accumulatedResult + i);
            } else {
                return new UnoptimizedContinue2<>(accumulatedResult + i, i + 1);
            }
        });
    }

    private static <T1, T2, R> R runUnoptimized(T1 t1, T2 t2, Function2<T1, T2, UnoptimizedRecursion2<T1, T2, R>> fn) {
        UnoptimizedContinue2<T1, T2, R> current = new UnoptimizedContinue2<>(t1, t2);
        while (true) {
            UnoptimizedRecursion2<T1, T2, R> next = fn.apply(current.t1, current.t2);
            switch (next) {
                case UnoptimizedContinue2<T1, T2, R> continue_ -> current = continue_;
                case UnoptimizedYield2(R r) -> {
                    return r;
                }
            }
        }
    }

    private sealed interface UnoptimizedRecursion2<T1, T2, R> {}

    private record UnoptimizedContinue2<T1, T2, R>(T1 t1, T2 t2) implements UnoptimizedRecursion2<T1, T2, R> {}

    private record UnoptimizedYield2<T1, T2, R>(R r) implements UnoptimizedRecursion2<T1, T2, R> {}
}
