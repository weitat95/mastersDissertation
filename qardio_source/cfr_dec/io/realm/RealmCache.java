/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 */
package io.realm;

import android.content.Context;
import android.content.res.AssetManager;
import io.realm.BaseRealm;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.exceptions.RealmFileException;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.Util;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

final class RealmCache {
    private static final List<WeakReference<RealmCache>> cachesList = new LinkedList<WeakReference<RealmCache>>();
    private static final Collection<RealmCache> leakedCaches = new ConcurrentLinkedQueue<RealmCache>();
    private RealmConfiguration configuration;
    private final AtomicBoolean isLeaked;
    private final String realmPath;
    private final EnumMap<RealmCacheType, RefAndCount> refAndCountMap;
    private final ColumnIndices[] typedColumnIndicesArray = new ColumnIndices[4];

    private RealmCache(String arrrealmCacheType) {
        this.isLeaked = new AtomicBoolean(false);
        this.realmPath = arrrealmCacheType;
        this.refAndCountMap = new EnumMap(RealmCacheType.class);
        for (RealmCacheType realmCacheType : RealmCacheType.values()) {
            this.refAndCountMap.put(realmCacheType, new RefAndCount());
        }
    }

    private static void copyAssetFileIfNeeded(RealmConfiguration realmConfiguration) {
        Object object;
        if (realmConfiguration.hasAssetFile()) {
            object = new File(realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName());
            RealmCache.copyFileIfNeeded(realmConfiguration.getAssetFilePath(), (File)object);
        }
        if (!Util.isEmptyString((String)(object = ObjectServerFacade.getFacade(realmConfiguration.isSyncConfiguration()).getSyncServerCertificateAssetName(realmConfiguration)))) {
            RealmCache.copyFileIfNeeded((String)object, new File(ObjectServerFacade.getFacade(realmConfiguration.isSyncConfiguration()).getSyncServerCertificateFilePath(realmConfiguration)));
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void copyFileIfNeeded(String var0, File var1_5) {
        if (var1_5.exists()) {
            return;
        }
        var8_9 = null;
        var7_10 = null;
        var4_11 = null;
        var5_13 = null;
        var10_14 = null;
        var9_15 = null;
        var6_16 = var10_14;
        try {
            var3_17 /* !! */  = BaseRealm.applicationContext.getAssets().open(var0);
            if (var3_17 /* !! */  != null) ** GOTO lbl-1000
            var5_13 = var3_17 /* !! */ ;
            var4_11 = var3_17 /* !! */ ;
            var6_16 = var10_14;
            throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Invalid input stream to the asset file: " + var0);
lbl-1000:
            // 1 sources
            {
                block25: {
                    block21: {
                        var5_13 = var3_17 /* !! */ ;
                        var4_11 = var3_17 /* !! */ ;
                        var6_16 = var10_14;
                        try {
                            var1_5 = new FileOutputStream((File)var1_5);
                            break block21;
                        }
                        catch (IOException var1_6) {}
                        var6_16 = var9_15;
                        var3_17 /* !! */  = var5_13;
                        break block25;
                    }
                    try {
                        var4_11 = new byte[4096];
                        while ((var2_19 = var3_17 /* !! */ .read(var4_11)) > -1) {
                            var1_5.write(var4_11, 0, var2_19);
                        }
                        ** GOTO lbl44
                    }
                    catch (IOException var4_12) {
                        var6_16 = var1_5;
                        var1_5 = var4_12;
                    }
                }
                var4_11 = var3_17 /* !! */ ;
                throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Could not resolve the path to the asset file: " + var0, (Throwable)var1_5);
            }
        }
        catch (Throwable var0_1) {
            block26: {
                block23: {
                    var3_17 /* !! */  = var4_11;
                    break block26;
lbl44:
                    // 1 sources
                    var0 = var8_9;
                    if (var3_17 /* !! */  != null) {
                        try {
                            var3_17 /* !! */ .close();
                            var0 = var8_9;
                        }
                        catch (IOException var0_3) {}
                    }
                    var3_17 /* !! */  = var0;
                    if (var1_5 != null) {
                        try {
                            var1_5.close();
                            var3_17 /* !! */  = var0;
                        }
                        catch (IOException var1_7) {
                            var3_17 /* !! */  = var0;
                            if (var0 != null) break block23;
                            var3_17 /* !! */  = var1_7;
                        }
                    }
                }
                if (var3_17 /* !! */  == null) return;
                throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, (Throwable)var3_17 /* !! */ );
                catch (Throwable var0_4) {
                    var6_16 = var1_5;
                }
            }
            var1_5 = var7_10;
            if (var3_17 /* !! */  != null) {
                try {
                    var3_17 /* !! */ .close();
                    var1_5 = var7_10;
                }
                catch (IOException var1_8) {}
            }
            if (var6_16 == null) throw var0_2;
            try {
                var6_16.close();
            }
            catch (IOException var3_18) {
                if (var1_5 != null) throw var0_2;
                throw var0_2;
            }
            throw var0_2;
        }
    }

    static <E extends BaseRealm> E createRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> class_) {
        return RealmCache.getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCache(realmConfiguration, class_);
    }

    /*
     * Exception decompiling
     */
    private <E extends BaseRealm> E doCreateRealmOrGetFromCache(RealmConfiguration var1_1, Class<E> var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 12[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void doInvokeWithGlobalRefCount(Callback callback) {
        synchronized (this) {
            callback.onResult(this.getTotalGlobalRefCount());
            return;
        }
    }

    static ColumnIndices findColumnIndices(ColumnIndices[] arrcolumnIndices, long l) {
        for (int i = arrcolumnIndices.length - 1; i >= 0; --i) {
            ColumnIndices columnIndices = arrcolumnIndices[i];
            if (columnIndices == null || columnIndices.getSchemaVersion() != l) continue;
            return columnIndices;
        }
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static RealmCache getCache(String object, boolean bl) {
        void var0_1;
        List<WeakReference<RealmCache>> list = cachesList;
        // MONITORENTER : list
        Iterator<WeakReference<RealmCache>> iterator = cachesList.iterator();
        RealmCache realmCache = null;
        try {
            void var1_4;
            while (iterator.hasNext()) {
                RealmCache realmCache2 = (RealmCache)iterator.next().get();
                if (realmCache2 == null) {
                    iterator.remove();
                    continue;
                }
                if (!realmCache2.realmPath.equals(object)) continue;
                realmCache = realmCache2;
            }
            if (realmCache != null) return realmCache;
            if (var1_4 == false) return realmCache;
            object = new RealmCache((String)object);
        }
        catch (Throwable throwable) {
            throw var0_1;
        }
        try {
            cachesList.add(new WeakReference<Object>(object));
            // MONITOREXIT : list
            return object;
        }
        catch (Throwable throwable) {
            throw var0_1;
        }
    }

    private int getTotalGlobalRefCount() {
        int n = 0;
        Iterator<RefAndCount> iterator = this.refAndCountMap.values().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().globalCount;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void invokeWithGlobalRefCount(RealmConfiguration object, Callback callback) {
        List<WeakReference<RealmCache>> list = cachesList;
        synchronized (list) {
            object = RealmCache.getCache(((RealmConfiguration)object).getPath(), false);
            if (object == null) {
                callback.onResult(0);
                return;
            }
            super.doInvokeWithGlobalRefCount(callback);
            return;
        }
    }

    private static int storeColumnIndices(ColumnIndices[] arrcolumnIndices, ColumnIndices columnIndices) {
        long l = Long.MAX_VALUE;
        int n = -1;
        for (int i = arrcolumnIndices.length - 1; i >= 0; --i) {
            if (arrcolumnIndices[i] == null) {
                arrcolumnIndices[i] = columnIndices;
                return i;
            }
            ColumnIndices columnIndices2 = arrcolumnIndices[i];
            long l2 = l;
            if (columnIndices2.getSchemaVersion() <= l) {
                l2 = columnIndices2.getSchemaVersion();
                n = i;
            }
            l = l2;
        }
        arrcolumnIndices[n] = columnIndices;
        return n;
    }

    private void validateConfiguration(RealmConfiguration realmConfiguration) {
        if (this.configuration.equals(realmConfiguration)) {
            return;
        }
        if (!Arrays.equals(this.configuration.getEncryptionKey(), realmConfiguration.getEncryptionKey())) {
            throw new IllegalArgumentException("Wrong key used to decrypt Realm.");
        }
        RealmMigration realmMigration = realmConfiguration.getMigration();
        RealmMigration realmMigration2 = this.configuration.getMigration();
        if (realmMigration2 != null && realmMigration != null && realmMigration2.getClass().equals(realmMigration.getClass()) && !realmMigration.equals(realmMigration2)) {
            throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. The most likely cause is that equals() and hashCode() are not overridden in the migration class: " + realmConfiguration.getMigration().getClass().getCanonicalName());
        }
        throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. \nCached configuration: \n" + this.configuration + "\n\nNew configuration: \n" + realmConfiguration);
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public ColumnIndices[] getTypedColumnIndicesArray() {
        return this.typedColumnIndicesArray;
    }

    void leak() {
        if (!this.isLeaked.getAndSet(true)) {
            leakedCaches.add(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void release(BaseRealm baseRealm) {
        synchronized (this) {
            Integer n;
            String string2 = baseRealm.getPath();
            RefAndCount refAndCount = this.refAndCountMap.get((Object)RealmCacheType.valueOf(baseRealm.getClass()));
            Integer n2 = n = (Integer)refAndCount.localCount.get();
            if (n == null) {
                n2 = 0;
            }
            if (n2 <= 0) {
                RealmLog.warn("%s has been closed already. refCount is %s", string2, n2);
            } else if ((n2 = Integer.valueOf(n2 - 1)) == 0) {
                refAndCount.localCount.set(null);
                refAndCount.localRealm.set(null);
                RefAndCount.access$810(refAndCount);
                if (refAndCount.globalCount < 0) {
                    throw new IllegalStateException("Global reference counter of Realm" + string2 + " got corrupted.");
                }
                if (baseRealm instanceof Realm && refAndCount.globalCount == 0) {
                    Arrays.fill(this.typedColumnIndicesArray, null);
                }
                baseRealm.doClose();
                if (this.getTotalGlobalRefCount() == 0) {
                    this.configuration = null;
                    ObjectServerFacade.getFacade(baseRealm.getConfiguration().isSyncConfiguration()).realmClosed(baseRealm.getConfiguration());
                }
            } else {
                refAndCount.localCount.set(n2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void updateSchemaCache(Realm object) {
        synchronized (this) {
            ColumnIndices[] arrcolumnIndices;
            Object t = this.refAndCountMap.get((Object)RealmCacheType.TYPED_REALM).localRealm.get();
            if (t != null && (object = ((Realm)object).updateSchemaCache(arrcolumnIndices = this.typedColumnIndicesArray)) != null) {
                RealmCache.storeColumnIndices(arrcolumnIndices, (ColumnIndices)object);
            }
            return;
        }
    }

    static interface Callback {
        public void onResult(int var1);
    }

    private static enum RealmCacheType {
        TYPED_REALM,
        DYNAMIC_REALM;


        public static RealmCacheType valueOf(String string2) {
            return Enum.valueOf(RealmCacheType.class, string2);
        }
    }

    private static class RefAndCount {
        private int globalCount = 0;
        private final ThreadLocal<Integer> localCount;
        private final ThreadLocal<BaseRealm> localRealm = new ThreadLocal();

        private RefAndCount() {
            this.localCount = new ThreadLocal();
        }

        static /* synthetic */ int access$808(RefAndCount refAndCount) {
            int n = refAndCount.globalCount;
            refAndCount.globalCount = n + 1;
            return n;
        }

        static /* synthetic */ int access$810(RefAndCount refAndCount) {
            int n = refAndCount.globalCount;
            refAndCount.globalCount = n - 1;
            return n;
        }
    }

}

