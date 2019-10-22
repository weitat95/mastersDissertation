/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.os.StatFs
 *  android.os.SystemClock
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.common.statfs;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import com.facebook.common.internal.Throwables;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class StatFsHelper {
    private static final long RESTAT_INTERVAL_MS = TimeUnit.MINUTES.toMillis(2L);
    private static StatFsHelper sStatsFsHelper;
    private final Lock lock = new ReentrantLock();
    private volatile File mExternalPath;
    private volatile StatFs mExternalStatFs = null;
    private volatile boolean mInitialized = false;
    private volatile File mInternalPath;
    private volatile StatFs mInternalStatFs = null;
    @GuardedBy(value="lock")
    private long mLastRestatTime;

    protected StatFsHelper() {
    }

    protected static StatFs createStatFs(String string2) {
        return new StatFs(string2);
    }

    private void ensureInitialized() {
        block4: {
            if (!this.mInitialized) {
                this.lock.lock();
                if (this.mInitialized) break block4;
                this.mInternalPath = Environment.getDataDirectory();
                this.mExternalPath = Environment.getExternalStorageDirectory();
                this.updateStats();
                this.mInitialized = true;
            }
        }
        return;
        finally {
            this.lock.unlock();
        }
    }

    public static StatFsHelper getInstance() {
        synchronized (StatFsHelper.class) {
            if (sStatsFsHelper == null) {
                sStatsFsHelper = new StatFsHelper();
            }
            StatFsHelper statFsHelper = sStatsFsHelper;
            return statFsHelper;
        }
    }

    private void maybeUpdateStats() {
        block4: {
            if (this.lock.tryLock()) {
                if (SystemClock.uptimeMillis() - this.mLastRestatTime <= RESTAT_INTERVAL_MS) break block4;
                this.updateStats();
            }
        }
        return;
        finally {
            this.lock.unlock();
        }
    }

    @GuardedBy(value="lock")
    private void updateStats() {
        this.mInternalStatFs = this.updateStatsHelper(this.mInternalStatFs, this.mInternalPath);
        this.mExternalStatFs = this.updateStatsHelper(this.mExternalStatFs, this.mExternalPath);
        this.mLastRestatTime = SystemClock.uptimeMillis();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private StatFs updateStatsHelper(@Nullable StatFs var1_1, @Nullable File var2_4) {
        if (var2_4 == null) return null;
        if (!var2_4.exists()) {
            return null;
        }
        if (var1_1 != null) ** GOTO lbl7
        try {
            return StatFsHelper.createStatFs(var2_4.getAbsolutePath());
lbl7:
            // 1 sources
            var1_1.restat(var2_4.getAbsolutePath());
            return var1_1;
        }
        catch (IllegalArgumentException var1_2) {
            return null;
        }
        catch (Throwable var1_3) {
            throw Throwables.propagate(var1_3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"DeprecatedMethod"})
    public long getAvailableStorageSpace(StorageType storageType) {
        long l;
        long l2;
        this.ensureInitialized();
        this.maybeUpdateStats();
        storageType = storageType == StorageType.INTERNAL ? this.mInternalStatFs : this.mExternalStatFs;
        if (storageType == null) return 0L;
        if (Build.VERSION.SDK_INT >= 18) {
            l = storageType.getBlockSizeLong();
            l2 = storageType.getAvailableBlocksLong();
            return l * l2;
        }
        l = storageType.getBlockSize();
        l2 = storageType.getAvailableBlocks();
        return l * l2;
    }

    public boolean testLowDiskSpace(StorageType storageType, long l) {
        this.ensureInitialized();
        long l2 = this.getAvailableStorageSpace(storageType);
        return l2 <= 0L || l2 < l;
    }

    public static enum StorageType {
        INTERNAL,
        EXTERNAL;

    }

}

