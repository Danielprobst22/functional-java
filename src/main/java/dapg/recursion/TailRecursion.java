package dapg.recursion;

import io.vavr.*;
import lombok.NonNull;

public class TailRecursion {

    public static <T1, R> R run(
            @NonNull T1 t1,
            @NonNull Function2<Recursion1<T1, R>, T1, Recursion1<T1, R>> fn
    ) {
        Recursion1<T1, R> recursion = new Recursion1<>(t1);
        do {
            recursion = fn.apply(recursion, recursion.t1);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static <T1, T2, R> R run(
            @NonNull T1 t1, @NonNull T2 t2,
            @NonNull Function3<Recursion2<T1, T2, R>, T1, T2, Recursion2<T1, T2, R>> fn
    ) {
        Recursion2<T1, T2, R> recursion = new Recursion2<>(t1, t2);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static <T1, T2, T3, R> R run(
            @NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3,
            @NonNull Function4<Recursion3<T1, T2, T3, R>, T1, T2, T3, Recursion3<T1, T2, T3, R>> fn
    ) {
        Recursion3<T1, T2, T3, R> recursion = new Recursion3<>(t1, t2, t3);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2, recursion.t3);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static <T1, T2, T3, T4, R> R run(
            @NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4,
            @NonNull Function5<Recursion4<T1, T2, T3, T4, R>, T1, T2, T3, T4, Recursion4<T1, T2, T3, T4, R>> fn
    ) {
        Recursion4<T1, T2, T3, T4, R> recursion = new Recursion4<>(t1, t2, t3, t4);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2, recursion.t3, recursion.t4);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static <T1, T2, T3, T4, T5, R> R run(
            @NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5,
            @NonNull Function6<Recursion5<T1, T2, T3, T4, T5, R>, T1, T2, T3, T4, T5, Recursion5<T1, T2, T3, T4, T5, R>> fn
    ) {
        Recursion5<T1, T2, T3, T4, T5, R> recursion = new Recursion5<>(t1, t2, t3, t4, t5);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2, recursion.t3, recursion.t4, recursion.t5);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static <T1, T2, T3, T4, T5, T6, R> R run(
            @NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5, @NonNull T6 t6,
            @NonNull Function7<Recursion6<T1, T2, T3, T4, T5, T6, R>, T1, T2, T3, T4, T5, T6, Recursion6<T1, T2, T3, T4, T5, T6, R>> fn
    ) {
        Recursion6<T1, T2, T3, T4, T5, T6, R> recursion = new Recursion6<>(t1, t2, t3, t4, t5, t6);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2, recursion.t3, recursion.t4, recursion.t5, recursion.t6);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> R run(
            @NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5, @NonNull T6 t6, @NonNull T7 t7,
            @NonNull Function8<Recursion7<T1, T2, T3, T4, T5, T6, T7, R>, T1, T2, T3, T4, T5, T6, T7, Recursion7<T1, T2, T3, T4, T5, T6, T7, R>> fn
    ) {
        Recursion7<T1, T2, T3, T4, T5, T6, T7, R> recursion = new Recursion7<>(t1, t2, t3, t4, t5, t6, t7);
        do {
            recursion = fn.apply(recursion, recursion.t1, recursion.t2, recursion.t3, recursion.t4, recursion.t5, recursion.t6, recursion.t7);
        } while (!recursion.isDone);
        return recursion.r;
    }

    public static final class Recursion1<T1, R> {
        private T1 t1;
        private R r;
        private boolean isDone;

        private Recursion1(@NonNull T1 t1) {
            this.t1 = t1;
            r = null;
            isDone = false;
        }

        public Recursion1<T1, R> continue_(@NonNull T1 t1) {
            this.t1 = t1;
            return this;
        }

        public Recursion1<T1, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    public static final class Recursion2<T1, T2, R> {
        private T1 t1;
        private T2 t2;
        private R r;
        private boolean isDone;

        private Recursion2(@NonNull T1 t1, @NonNull T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
            r = null;
            isDone = false;
        }

        public Recursion2<T1, T2, R> continue_(@NonNull T1 t1, @NonNull T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
            return this;
        }

        public Recursion2<T1, T2, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    public static final class Recursion3<T1, T2, T3, R> {
        private T1 t1;
        private T2 t2;
        private T3 t3;
        private R r;
        private boolean isDone;

        private Recursion3(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            r = null;
            isDone = false;
        }

        public Recursion3<T1, T2, T3, R> continue_(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            return this;
        }

        public Recursion3<T1, T2, T3, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    public static final class Recursion4<T1, T2, T3, T4, R> {
        private T1 t1;
        private T2 t2;
        private T3 t3;
        private T4 t4;
        private R r;
        private boolean isDone;

        private Recursion4(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            r = null;
            isDone = false;
        }

        public Recursion4<T1, T2, T3, T4, R> continue_(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            return this;
        }

        public Recursion4<T1, T2, T3, T4, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    public static final class Recursion5<T1, T2, T3, T4, T5, R> {
        private T1 t1;
        private T2 t2;
        private T3 t3;
        private T4 t4;
        private T5 t5;
        private R r;
        private boolean isDone;

        private Recursion5(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            r = null;
            isDone = false;
        }

        public Recursion5<T1, T2, T3, T4, T5, R> continue_(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            return this;
        }

        public Recursion5<T1, T2, T3, T4, T5, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    public static final class Recursion6<T1, T2, T3, T4, T5, T6, R> {
        private T1 t1;
        private T2 t2;
        private T3 t3;
        private T4 t4;
        private T5 t5;
        private T6 t6;
        private R r;
        private boolean isDone;

        private Recursion6(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5, @NonNull T6 t6) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            r = null;
            isDone = false;
        }

        public Recursion6<T1, T2, T3, T4, T5, T6, R> continue_(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5, @NonNull T6 t6) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            return this;
        }

        public Recursion6<T1, T2, T3, T4, T5, T6, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }

    public static final class Recursion7<T1, T2, T3, T4, T5, T6, T7, R> {
        private T1 t1;
        private T2 t2;
        private T3 t3;
        private T4 t4;
        private T5 t5;
        private T6 t6;
        private T7 t7;
        private R r;
        private boolean isDone;

        private Recursion7(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5, @NonNull T6 t6, @NonNull T7 t7) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            r = null;
            isDone = false;
        }

        public Recursion7<T1, T2, T3, T4, T5, T6, T7, R> continue_(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4, @NonNull T5 t5, @NonNull T6 t6, @NonNull T7 t7) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            return this;
        }

        public Recursion7<T1, T2, T3, T4, T5, T6, T7, R> yield(@NonNull R r) {
            this.r = r;
            this.isDone = true;
            return this;
        }
    }
}
