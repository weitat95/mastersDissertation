/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.CompactOnLaunchCallback;
import io.realm.RealmConfiguration;
import io.realm.internal.Capabilities;
import io.realm.internal.Collection;
import io.realm.internal.IOException;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.PendingRow;
import io.realm.internal.RealmNotifier;
import io.realm.internal.Table;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import java.io.Closeable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SharedRealm
implements NativeObject,
Closeable {
    private static final long nativeFinalizerPtr = SharedRealm.nativeGetFinalizerPtr();
    private static volatile File temporaryDirectory;
    public final Capabilities capabilities;
    public final List<WeakReference<Collection>> collections;
    private final RealmConfiguration configuration;
    final NativeContext context;
    public final List<WeakReference<Collection.Iterator>> iterators;
    private long lastSchemaVersion;
    private final long nativePtr;
    private final List<WeakReference<PendingRow>> pendingRows = new CopyOnWriteArrayList<WeakReference<PendingRow>>();
    public final RealmNotifier realmNotifier;
    private final SchemaVersionListener schemaChangeListener;

    /*
     * Enabled aggressive block sorting
     */
    private SharedRealm(long l, RealmConfiguration realmConfiguration, SchemaVersionListener schemaVersionListener) {
        this.collections = new CopyOnWriteArrayList<WeakReference<Collection>>();
        this.iterators = new ArrayList<WeakReference<Collection.Iterator>>();
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        AndroidRealmNotifier androidRealmNotifier = new AndroidRealmNotifier(this, androidCapabilities);
        this.nativePtr = SharedRealm.nativeGetSharedRealm(l, androidRealmNotifier);
        this.configuration = realmConfiguration;
        this.capabilities = androidCapabilities;
        this.realmNotifier = androidRealmNotifier;
        this.schemaChangeListener = schemaVersionListener;
        this.context = new NativeContext();
        this.context.addReference(this);
        l = schemaVersionListener == null ? -1L : this.getSchemaVersion();
        this.lastSchemaVersion = l;
        SharedRealm.nativeSetAutoRefresh(this.nativePtr, androidCapabilities.canDeliverNotification());
    }

    private void executePendingRowQueries() {
        Iterator<WeakReference<PendingRow>> iterator = this.pendingRows.iterator();
        while (iterator.hasNext()) {
            PendingRow pendingRow = (PendingRow)iterator.next().get();
            if (pendingRow == null) continue;
            pendingRow.executeQuery();
        }
        this.pendingRows.clear();
    }

    public static SharedRealm getInstance(RealmConfiguration realmConfiguration) {
        return SharedRealm.getInstance(realmConfiguration, null, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SharedRealm getInstance(RealmConfiguration object, SchemaVersionListener schemaVersionListener, boolean bl) {
        Object[] arrobject = ObjectServerFacade.getSyncFacadeIfPossible().getUserAndServerUrl((RealmConfiguration)object);
        String string2 = (String)arrobject[0];
        String string3 = (String)arrobject[1];
        String string4 = (String)arrobject[2];
        String string5 = (String)arrobject[3];
        boolean bl2 = Boolean.TRUE.equals(arrobject[4]);
        String string6 = (String)arrobject[5];
        String string7 = ((RealmConfiguration)object).getPath();
        byte[] arrby = ((RealmConfiguration)object).getEncryptionKey();
        byte by = string3 != null ? SchemaMode.SCHEMA_MODE_ADDITIVE.getNativeValue() : SchemaMode.SCHEMA_MODE_MANUAL.getNativeValue();
        boolean bl3 = ((RealmConfiguration)object).getDurability() == Durability.MEM_ONLY;
        long l = SharedRealm.nativeCreateConfig(string7, arrby, by, bl3, false, ((RealmConfiguration)object).getSchemaVersion(), true, bl, ((RealmConfiguration)object).getCompactOnLaunchCallback(), string3, string4, string2, string5, bl2, string6);
        try {
            ObjectServerFacade.getSyncFacadeIfPossible().wrapObjectStoreSessionIfRequired((RealmConfiguration)object);
            object = new SharedRealm(l, (RealmConfiguration)object, schemaVersionListener);
            return object;
        }
        finally {
            SharedRealm.nativeCloseConfig(l);
        }
    }

    public static void initialize(File file) {
        if (temporaryDirectory != null) {
            return;
        }
        if (file == null) {
            throw new IllegalArgumentException("'tempDirectory' must not be null.");
        }
        String string2 = file.getAbsolutePath();
        if (!(file.isDirectory() || file.mkdirs() || file.isDirectory())) {
            throw new IOException("failed to create temporary directory: " + string2);
        }
        String string3 = string2;
        if (!string2.endsWith("/")) {
            string3 = string2 + "/";
        }
        SharedRealm.nativeInit(string3);
        temporaryDirectory = file;
    }

    private static native void nativeBeginTransaction(long var0);

    private static native void nativeCancelTransaction(long var0);

    private static native void nativeCloseConfig(long var0);

    private static native void nativeCloseSharedRealm(long var0);

    private static native void nativeCommitTransaction(long var0);

    private static native boolean nativeCompact(long var0);

    private static native long nativeCreateConfig(String var0, byte[] var1, byte var2, boolean var3, boolean var4, long var5, boolean var7, boolean var8, CompactOnLaunchCallback var9, String var10, String var11, String var12, String var13, boolean var14, String var15);

    private static native long nativeCreateTable(long var0, String var2);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetSharedRealm(long var0, RealmNotifier var2);

    private static native long nativeGetTable(long var0, String var2);

    private static native String nativeGetTableName(long var0, int var2);

    private static native long nativeGetVersion(long var0);

    private static native long[] nativeGetVersionID(long var0);

    private static native boolean nativeHasTable(long var0, String var2);

    private static native void nativeInit(String var0);

    private static native boolean nativeIsAutoRefresh(long var0);

    private static native boolean nativeIsClosed(long var0);

    private static native boolean nativeIsEmpty(long var0);

    private static native boolean nativeIsInTransaction(long var0);

    private static native long nativeReadGroup(long var0);

    private static native void nativeRefresh(long var0);

    private static native void nativeRemoveTable(long var0, String var2);

    private static native void nativeRenameTable(long var0, String var2, String var3);

    private static native boolean nativeRequiresMigration(long var0, long var2);

    private static native void nativeSetAutoRefresh(long var0, boolean var2);

    private static native void nativeSetVersion(long var0, long var2);

    private static native long nativeSize(long var0);

    private static native void nativeStopWaitForChange(long var0);

    private static native void nativeUpdateSchema(long var0, long var2, long var4);

    private static native boolean nativeWaitForChange(long var0);

    private static native void nativeWriteCopy(long var0, String var2, byte[] var3);

    void addIterator(Collection.Iterator iterator) {
        this.iterators.add(new WeakReference<Collection.Iterator>(iterator));
    }

    public void beginTransaction() {
        this.beginTransaction(false);
    }

    public void beginTransaction(boolean bl) {
        if (!bl && this.configuration.isReadOnly()) {
            throw new IllegalStateException("Write transactions cannot be used when a Realm is marked as read-only.");
        }
        this.detachIterators();
        this.executePendingRowQueries();
        SharedRealm.nativeBeginTransaction(this.nativePtr);
        this.invokeSchemaChangeListenerIfSchemaChanged();
    }

    public void cancelTransaction() {
        SharedRealm.nativeCancelTransaction(this.nativePtr);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        if (this.realmNotifier != null) {
            this.realmNotifier.close();
        }
        NativeContext nativeContext = this.context;
        synchronized (nativeContext) {
            SharedRealm.nativeCloseSharedRealm(this.nativePtr);
            return;
        }
    }

    public void commitTransaction() {
        SharedRealm.nativeCommitTransaction(this.nativePtr);
    }

    public Table createTable(String string2) {
        return new Table(this, SharedRealm.nativeCreateTable(this.nativePtr, string2));
    }

    void detachIterators() {
        Iterator<WeakReference<Collection.Iterator>> iterator = this.iterators.iterator();
        while (iterator.hasNext()) {
            Collection.Iterator iterator2 = (Collection.Iterator)iterator.next().get();
            if (iterator2 == null) continue;
            iterator2.detach();
        }
        this.iterators.clear();
    }

    long getGroupNative() {
        return SharedRealm.nativeReadGroup(this.nativePtr);
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public long getSchemaVersion() {
        return SharedRealm.nativeGetVersion(this.nativePtr);
    }

    public Table getTable(String string2) {
        return new Table(this, SharedRealm.nativeGetTable(this.nativePtr, string2));
    }

    public boolean hasTable(String string2) {
        return SharedRealm.nativeHasTable(this.nativePtr, string2);
    }

    void invalidateIterators() {
        Iterator<WeakReference<Collection.Iterator>> iterator = this.iterators.iterator();
        while (iterator.hasNext()) {
            Collection.Iterator iterator2 = (Collection.Iterator)iterator.next().get();
            if (iterator2 == null) continue;
            iterator2.invalidate();
        }
        this.iterators.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void invokeSchemaChangeListenerIfSchemaChanged() {
        long l;
        block3: {
            block2: {
                if (this.schemaChangeListener == null) break block2;
                long l2 = this.lastSchemaVersion;
                l = this.getSchemaVersion();
                if (l != l2) break block3;
            }
            return;
        }
        this.lastSchemaVersion = l;
        this.schemaChangeListener.onSchemaVersionChanged(l);
    }

    public boolean isClosed() {
        return SharedRealm.nativeIsClosed(this.nativePtr);
    }

    public boolean isInTransaction() {
        return SharedRealm.nativeIsInTransaction(this.nativePtr);
    }

    void removePendingRow(PendingRow pendingRow) {
        for (WeakReference<PendingRow> weakReference : this.pendingRows) {
            PendingRow pendingRow2 = (PendingRow)weakReference.get();
            if (pendingRow2 != null && pendingRow2 != pendingRow) continue;
            this.pendingRows.remove(weakReference);
        }
    }

    public void setSchemaVersion(long l) {
        SharedRealm.nativeSetVersion(this.nativePtr, l);
    }

    public void updateSchema(OsSchemaInfo osSchemaInfo, long l) {
        SharedRealm.nativeUpdateSchema(this.nativePtr, osSchemaInfo.getNativePtr(), l);
    }

    public static enum Durability {
        FULL(0),
        MEM_ONLY(1);

        final int value;

        private Durability(int n2) {
            this.value = n2;
        }
    }

    private static enum SchemaMode {
        SCHEMA_MODE_AUTOMATIC(0),
        SCHEMA_MODE_READONLY(1),
        SCHEMA_MODE_RESET_FILE(2),
        SCHEMA_MODE_ADDITIVE(3),
        SCHEMA_MODE_MANUAL(4);

        final byte value;

        private SchemaMode(byte by) {
            this.value = by;
        }

        public byte getNativeValue() {
            return this.value;
        }
    }

    public static interface SchemaVersionListener {
        public void onSchemaVersionChanged(long var1);
    }

}

