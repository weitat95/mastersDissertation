/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.realm.internal.async;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RealmThreadPoolExecutor
extends ThreadPoolExecutor {
    private static final int CORE_POOL_SIZE = RealmThreadPoolExecutor.calculateCorePoolSize();
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = this.pauseLock.newCondition();

    private RealmThreadPoolExecutor(int n, int n2) {
        super(n, n2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
    }

    @SuppressFBWarnings(value={"DMI_HARDCODED_ABSOLUTE_FILENAME"})
    private static int calculateCorePoolSize() {
        int n;
        int n2 = n = RealmThreadPoolExecutor.countFilesInDir("/sys/devices/system/cpu/", "cpu[0-9]+");
        if (n <= 0) {
            n2 = Runtime.getRuntime().availableProcessors();
        }
        if (n2 <= 0) {
            return 1;
        }
        return n2 * 2 + 1;
    }

    private static int countFilesInDir(String arrfile, String object) {
        block3: {
            object = Pattern.compile((String)object);
            try {
                arrfile = new File((String)arrfile).listFiles(new FileFilter(){

                    @Override
                    public boolean accept(File file) {
                        return Pattern.this.matcher(file.getName()).matches();
                    }
                });
                if (arrfile != null) break block3;
                return 0;
            }
            catch (SecurityException securityException) {
                return 0;
            }
        }
        int n = arrfile.length;
        return n;
    }

    public static RealmThreadPoolExecutor newDefaultExecutor() {
        return new RealmThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE);
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        this.pauseLock.lock();
        try {
            while (this.isPaused) {
                this.unpaused.await();
            }
        }
        catch (InterruptedException interruptedException) {
            thread.interrupt();
            return;
        }
        return;
        finally {
            this.pauseLock.unlock();
        }
    }

}

