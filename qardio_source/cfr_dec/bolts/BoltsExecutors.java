/*
 * Decompiled with CFR 0.147.
 */
package bolts;

import bolts.AndroidExecutors;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

final class BoltsExecutors {
    private static final BoltsExecutors INSTANCE = new BoltsExecutors();
    private final ExecutorService background;
    private final Executor immediate;
    private final ScheduledExecutorService scheduled;

    /*
     * Enabled aggressive block sorting
     */
    private BoltsExecutors() {
        ExecutorService executorService = !BoltsExecutors.isAndroidRuntime() ? Executors.newCachedThreadPool() : AndroidExecutors.newCachedThreadPool();
        this.background = executorService;
        this.scheduled = Executors.newSingleThreadScheduledExecutor();
        this.immediate = new ImmediateExecutor();
    }

    public static ExecutorService background() {
        return BoltsExecutors.INSTANCE.background;
    }

    static Executor immediate() {
        return BoltsExecutors.INSTANCE.immediate;
    }

    private static boolean isAndroidRuntime() {
        String string2 = System.getProperty("java.runtime.name");
        if (string2 == null) {
            return false;
        }
        return string2.toLowerCase(Locale.US).contains("android");
    }

    private static class ImmediateExecutor
    implements Executor {
        private ThreadLocal<Integer> executionDepth = new ThreadLocal();

        private ImmediateExecutor() {
        }

        private int decrementDepth() {
            int n;
            Integer n2;
            Integer n3 = n2 = this.executionDepth.get();
            if (n2 == null) {
                n3 = 0;
            }
            if ((n = n3 - 1) == 0) {
                this.executionDepth.remove();
                return n;
            }
            this.executionDepth.set(n);
            return n;
        }

        private int incrementDepth() {
            Integer n;
            Integer n2 = n = this.executionDepth.get();
            if (n == null) {
                n2 = 0;
            }
            int n3 = n2 + 1;
            this.executionDepth.set(n3);
            return n3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void execute(Runnable runnable) {
            if (this.incrementDepth() <= 15) {
                runnable.run();
                do {
                    return;
                    break;
                } while (true);
            }
            try {
                BoltsExecutors.background().execute(runnable);
                return;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                this.decrementDepth();
            }
        }
    }

}

