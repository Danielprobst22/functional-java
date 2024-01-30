package dapg.recursion;

import cyclops.control.Trampoline;
import io.vavr.Function2;
import io.vavr.Function3;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
//@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@SuppressWarnings("NewClassNamingConvention") // ignore "Test class name 'RecursionBenchmark' doesn't match regex '[A-Z][A-Za-z\d]*Test(s|Case)?|Test[A-Z][A-Za-z\d]*|IT(.*)|(.*)IT(Case)?'"
public class SumBigIntRecursionBenchmark {
    private static final int LIMIT = 100;
    //        private static final int LIMIT = 1_000;
//    private static final int LIMIT = 10_000;
//    private static final int LIMIT = 100_000;
//        private static final int LIMIT = 1_000_000;
//        private static final int LIMIT = 5_000_000;
//    private static final int LIMIT = 10_000_000;
//    private static final int LIMIT = 50_000_000;
    //        private static final int LIMIT = 100_000_000;
//    private static final int LIMIT = Integer.MAX_VALUE;
//    private static final int WARMUP_ITERATIONS = 5;
    private static final int WARMUP_ITERATIONS = 25;
    //    private static final int ITERATIONS = 1;
//    private static final int ITERATIONS = 10;
    private static final int ITERATIONS = 25;
    //        private static final int TIME = 100;
    private static final int TIME = 1_000;

    /*
      LIMIT = Integer.MAX_VALUE;
        Benchmark                                                         Mode  Cnt   Score   Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative          avgt   10  41.650 ± 0.407   s/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive      avgt   10  68.956 ± 0.353   s/op
        SumBigIntRecursionBenchmark.measureSumMinimalBigIntTailRecursive  avgt   10  68.815 ± 0.734   s/op -> custom Function3 with only an apply method (maybe byte-size of class might change that way or some other side effects) -> makes no measurable difference
     */
    /*
      TIME = 100
      LIMIT = 100_000
        Benchmark                                                                Mode  Cnt        Score        Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1528813.034 ±  55501.223  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  2227109.579 ± 113079.722  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2055080.079 ±  47754.403  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  2139008.734 ± 110779.195  ns/op

        Benchmark                                                                Mode  Cnt        Score       Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1529247.793 ± 74213.539  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  2151129.738 ± 91936.792  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2147850.813 ± 87318.596  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  2058481.871 ± 85272.615  ns/op

        Benchmark                                                                Mode  Cnt        Score        Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1600049.265 ±  97973.220  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  2046681.207 ±  80610.306  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2149788.492 ± 130844.805  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  2041853.815 ±  61036.930  ns/op
     */
    /*
      TIME = 1_000
      LIMIT = 100_000

      ignoring return
        Benchmark                                                     Mode  Cnt        Score       Error  Units
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2099642.888 ± 41149.997  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  1962265.711 ± 42710.821  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2030806.105 ± 33357.204  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2087708.052 ± 60638.370  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2205772.216 ± 117712.095  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2089670.880 ± 40490.622  ns/op

      reassigning return
        Benchmark                                                     Mode  Cnt        Score       Error  Units
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2021908.312 ± 40956.694  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2019611.200 ± 83992.925  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  2059766.787 ± 58477.153  ns/op

      LIMIT = 1_000_000

      ignoring return
        Benchmark                                                     Mode  Cnt        Score       Error  Units
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  22450274.740 ± 428046.154  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  22554235.833 ± 513274.025  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  22630800.247 ± 497344.636  ns/op

      reassigning return
        Benchmark                                                     Mode  Cnt        Score       Error  Units
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  21567353.657 ± 425317.199  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  21391552.573 ± 494256.747  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive  avgt   25  21277757.471 ± 512770.890  ns/op
     */
    /*
      TIME = 1_000
      LIMIT = 100
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1320.433 ± 29.769  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  1795.377 ± 53.541  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2017.156 ± 40.765  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  1837.069 ± 56.912  ns/op

      LIMIT = 1_000
        Benchmark                                                                Mode  Cnt      Score     Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  12593.661 ± 279.058  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  18558.865 ± 334.012  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  19388.564 ± 319.818  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  18615.617 ± 381.998  ns/op

      LIMIT = 10_000
        Benchmark                                                                Mode  Cnt       Score      Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  123308.565 ± 2222.266  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  190818.427 ± 5762.470  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  208031.995 ± 6896.654  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  185251.800 ± 4219.113  ns/op

      LIMIT = 100_000
        Benchmark                                                                Mode  Cnt        Score       Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1436014.750 ± 22270.244  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  2099642.888 ± 41149.997  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2086162.516 ± 48847.005  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  2005559.483 ± 47336.615  ns/op

        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1447178.686 ± 19455.037  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  1962265.711 ± 42710.821  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2121956.086 ± 47595.462  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  2014384.518 ± 49815.981  ns/op

      LIMIT = 1_000_000
        Benchmark                                                                Mode  Cnt         Score        Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  16247639.118 ± 503274.125  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  22436805.793 ± 544247.024  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  23819774.358 ± 709755.053  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  20892891.789 ± 653516.127  ns/op

      LIMIT = 5_000_000
        Benchmark                                                                Mode  Cnt          Score         Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25   84053399.307 ± 3097571.546  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  112010269.660 ± 2824126.975  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  113015539.557 ± 1878266.902  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  104804323.953 ± 2382347.215  ns/op

      LIMIT = 10_000_000
        Benchmark                                                                Mode  Cnt          Score          Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  164177144.458 ±  3618041.397  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  221598193.512 ±  5567359.427  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  220603515.648 ±  5579221.747  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  208728893.478 ± 10662073.059  ns/op

      LIMIT = 50_000_000
        Benchmark                                                                Mode  Cnt           Score          Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25   839254045.000 ± 24222257.996  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  1137303493.400 ± 25165240.612  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  1188948071.960 ± 30235167.201  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  1085283978.960 ± 50433966.662  ns/op

      LIMIT = 100_000_000
        Benchmark                                                                Mode  Cnt           Score          Error  Units
        SumBigIntRecursionBenchmark._1_measureSumBigIntIterative                 avgt   25  1662325909.760 ± 37932287.496  ns/op
        SumBigIntRecursionBenchmark._2_measureSumBigIntTailRecursive             avgt   25  2731632445.960 ± 46310137.054  ns/op
        SumBigIntRecursionBenchmark._3_measureSumBigIntUnoptimizedTailRecursive  avgt   25  2318032886.880 ± 63780210.327  ns/op
        SumBigIntRecursionBenchmark._4_measureSumBigIntTrampoline                avgt   25  2165849258.680 ± 55796457.310  ns/op
     */
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(SumBigIntRecursionBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();

//        System.out.println(sumBigIntIterative());
//        System.out.println(sumBigIntTailRecursive());
//        System.out.println(sumBigIntUnoptimizedTailRecursive());
//        System.out.println(sumBigIntTrampoline());
    }

    @Benchmark
    @Warmup(iterations = WARMUP_ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public BigInteger _1_measureSumBigIntIterative() {
        return sumBigIntIterative();
    }

    private static BigInteger sumBigIntIterative() {
        BigInteger result = BigInteger.valueOf(0);
        for (int i = 1; i <= LIMIT && i > 0; i++) {
            result = result.add(BigInteger.valueOf(i));
        }
        return result;
    }

    @Benchmark
    @Warmup(iterations = WARMUP_ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public BigInteger _2_measureSumBigIntTailRecursive() {
        return sumBigIntTailRecursive();
    }

    private static BigInteger sumBigIntTailRecursive() {
        return runOptimized(BigInteger.valueOf(0), 1,
                (recursion, accumulatedResult, i) -> {
                    BigInteger newResult = accumulatedResult.add(BigInteger.valueOf(i));
                    if (i == LIMIT) {
                        return recursion.yield(newResult);
                    } else {
                        return recursion.continue_(newResult, i + 1);
                    }
                }
        );
    }

    private static <T1, T2, R> R runOptimized(T1 t1, T2 t2, Function3<TailRecursion<T1, T2, R>, T1, T2, TailRecursion<T1, T2, R>> fn) {
        TailRecursion<T1, T2, R> recursion = new TailRecursion<>(t1, t2);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2);
        } while (!recursion.isDone);
        return recursion.r;
    }

    private static final class TailRecursion<T1, T2, R> {
        private T1 t1;
        private T2 t2;
        private R r;
        private boolean isDone;

        public TailRecursion(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
            r = null;
            isDone = false;
        }

        public TailRecursion<T1, T2, R> continue_(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
            return this;
        }

        public TailRecursion<T1, T2, R> yield(R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    @Benchmark
    @Warmup(iterations = WARMUP_ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public static BigInteger _3_measureSumBigIntUnoptimizedTailRecursive() {
        return sumBigIntUnoptimizedTailRecursive();
    }

    private static BigInteger sumBigIntUnoptimizedTailRecursive() {
        return runUnoptimized(BigInteger.valueOf(0), 1, (accumulatedResult, i) -> {
            BigInteger newResult = accumulatedResult.add(BigInteger.valueOf(i));
            if (i == LIMIT) {
                return new UnoptimizedYield2<>(newResult);
            } else {
                return new UnoptimizedContinue2<>(newResult, i + 1);
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

    @Benchmark
    @Warmup(iterations = WARMUP_ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = ITERATIONS, time = TIME, timeUnit = TimeUnit.MILLISECONDS)
    public BigInteger _4_measureSumBigIntTrampoline() {
        return sumBigIntTrampoline();
    }

    private static BigInteger sumBigIntTrampoline() {
        return addNextValue(BigInteger.valueOf(0), 1).get();
    }

    private static Trampoline<BigInteger> addNextValue(BigInteger accumulatedResult, int i) {
        BigInteger newResult = accumulatedResult.add(BigInteger.valueOf(i));
        if (i == LIMIT) {
            return Trampoline.done(newResult);
        } else {
            return Trampoline.more(() -> addNextValue(newResult, i + 1));
        }
    }
}
