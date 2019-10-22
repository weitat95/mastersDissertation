/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.realm;

import android.content.Context;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmCache;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmModel;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.CheckedRow;
import io.realm.internal.ColumnInfo;
import io.realm.internal.InvalidRow;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.Util;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class BaseRealm
implements Closeable {
    static volatile Context applicationContext;
    static final RealmThreadPoolExecutor asyncTaskExecutor;
    public static final ThreadLocalRealmObjectContext objectContext;
    protected final RealmConfiguration configuration;
    private RealmCache realmCache;
    protected final RealmSchema schema;
    protected SharedRealm sharedRealm;
    final long threadId;

    static {
        asyncTaskExecutor = RealmThreadPoolExecutor.newDefaultExecutor();
        objectContext = new ThreadLocalRealmObjectContext();
    }

    BaseRealm(RealmCache realmCache) {
        this(realmCache.getConfiguration());
        this.realmCache = realmCache;
    }

    /*
     * Enabled aggressive block sorting
     */
    BaseRealm(RealmConfiguration realmConfiguration) {
        SharedRealm.SchemaVersionListener schemaVersionListener = null;
        this.threadId = Thread.currentThread().getId();
        this.configuration = realmConfiguration;
        this.realmCache = null;
        if (this instanceof Realm) {
            schemaVersionListener = new SharedRealm.SchemaVersionListener(){

                @Override
                public void onSchemaVersionChanged(long l) {
                    if (BaseRealm.this.realmCache != null) {
                        BaseRealm.this.realmCache.updateSchemaCache((Realm)BaseRealm.this);
                    }
                }
            };
        }
        this.sharedRealm = SharedRealm.getInstance(realmConfiguration, schemaVersionListener, true);
        this.schema = new RealmSchema(this);
    }

    static boolean deleteRealm(RealmConfiguration realmConfiguration) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback(){

            @Override
            public void onResult(int n) {
                if (n != 0) {
                    throw new IllegalStateException("It's not allowed to delete the file associated with an open Realm. Remember to close() all the instances of the Realm before deleting its file: " + RealmConfiguration.this.getPath());
                }
                String string2 = RealmConfiguration.this.getPath();
                File file = RealmConfiguration.this.getRealmDirectory();
                String string3 = RealmConfiguration.this.getRealmFileName();
                atomicBoolean.set(Util.deleteRealm(string2, file, string3));
            }
        });
        return atomicBoolean.get();
    }

    protected static void migrateRealm(RealmConfiguration realmConfiguration, RealmMigration realmMigration, MigrationCallback migrationCallback, RealmMigrationNeededException serializable) throws FileNotFoundException {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException("RealmConfiguration must be provided");
        }
        if (realmConfiguration.isSyncConfiguration()) {
            throw new IllegalArgumentException("Manual migrations are not supported for synced Realms");
        }
        if (realmMigration == null && realmConfiguration.getMigration() == null) {
            throw new RealmMigrationNeededException(realmConfiguration.getPath(), "RealmMigration must be provided", (Throwable)serializable);
        }
        serializable = new AtomicBoolean(false);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback((AtomicBoolean)serializable, realmMigration, migrationCallback){
            final /* synthetic */ MigrationCallback val$callback;
            final /* synthetic */ AtomicBoolean val$fileNotFound;
            final /* synthetic */ RealmMigration val$migration;
            {
                this.val$fileNotFound = atomicBoolean;
                this.val$migration = realmMigration;
                this.val$callback = migrationCallback;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onResult(int var1_1) {
                if (var1_1 != 0) {
                    throw new IllegalStateException("Cannot migrate a Realm file that is already open: " + RealmConfiguration.this.getPath());
                }
                if (!new File(RealmConfiguration.this.getPath()).exists()) {
                    this.val$fileNotFound.set(true);
                    return;
                }
                var4_2 = this.val$migration == null ? RealmConfiguration.this.getMigration() : this.val$migration;
                var3_4 = null;
                var2_5 = null;
                try {
                    var2_5 = var5_7 = DynamicRealm.createInstance(RealmConfiguration.this);
                    var3_4 = var5_7;
                    var5_7.beginTransaction();
                    var2_5 = var5_7;
                    var3_4 = var5_7;
                    var4_2.migrate(var5_7, var5_7.getVersion(), RealmConfiguration.this.getSchemaVersion());
                    var2_5 = var5_7;
                    var3_4 = var5_7;
                    var5_7.setVersion(RealmConfiguration.this.getSchemaVersion());
                    var2_5 = var5_7;
                    var3_4 = var5_7;
                    var5_7.commitTransaction();
                    if (var5_7 == null) return;
                }
                catch (RuntimeException var4_3) {
                    if (var2_5 == null) ** GOTO lbl31
                    var3_4 = var2_5;
                    try {
                        var2_5.cancelTransaction();
lbl31:
                        // 2 sources
                        var3_4 = var2_5;
                        throw var4_3;
                    }
                    catch (Throwable var2_6) {
                        if (var3_4 == null) throw var2_6;
                        var3_4.close();
                        this.val$callback.migrationComplete();
                        throw var2_6;
                    }
                }
                var5_7.close();
                this.val$callback.migrationComplete();
                return;
            }
        });
        if (((AtomicBoolean)serializable).get()) {
            throw new FileNotFoundException("Cannot migrate a Realm file which doesn't exist: " + realmConfiguration.getPath());
        }
    }

    public void beginTransaction() {
        this.beginTransaction(false);
    }

    void beginTransaction(boolean bl) {
        this.checkIfValid();
        this.sharedRealm.beginTransaction(bl);
    }

    public void cancelTransaction() {
        this.checkIfValid();
        this.sharedRealm.cancelTransaction();
    }

    protected void checkIfValid() {
        if (this.sharedRealm == null || this.sharedRealm.isClosed()) {
            throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
        }
        if (this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
        }
    }

    @Override
    public void close() {
        if (this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm instance can only be closed on the thread it was created.");
        }
        if (this.realmCache != null) {
            this.realmCache.release(this);
            return;
        }
        this.doClose();
    }

    public void commitTransaction() {
        this.checkIfValid();
        this.sharedRealm.commitTransaction();
    }

    void doClose() {
        this.realmCache = null;
        if (this.sharedRealm != null) {
            this.sharedRealm.close();
            this.sharedRealm = null;
        }
        if (this.schema != null) {
            this.schema.close();
        }
    }

    protected void finalize() throws Throwable {
        if (this.sharedRealm != null && !this.sharedRealm.isClosed()) {
            RealmLog.warn("Remember to call close() on all Realm instances. Realm %s is being finalized without being closed, this can lead to running out of native memory.", this.configuration.getPath());
            if (this.realmCache != null) {
                this.realmCache.leak();
            }
        }
        super.finalize();
    }

    <E extends RealmModel> E get(Class<E> class_, long l, boolean bl, List<String> list) {
        UncheckedRow uncheckedRow = this.schema.getTable(class_).getUncheckedRow(l);
        return this.configuration.getSchemaMediator().newInstance(class_, this, uncheckedRow, this.schema.getColumnInfo(class_), bl, list);
    }

    /*
     * Enabled aggressive block sorting
     */
    <E extends RealmModel> E get(Class<E> object, String object2, long l) {
        boolean bl = object2 != null;
        object2 = bl ? this.schema.getTable((String)object2) : this.schema.getTable((Class<? extends RealmModel>)object);
        if (bl) {
            if (l != -1L) {
                object = ((Table)object2).getCheckedRow(l);
                return (E)new DynamicRealmObject(this, (Row)object);
            }
            object = InvalidRow.INSTANCE;
            return (E)new DynamicRealmObject(this, (Row)object);
        }
        RealmProxyMediator realmProxyMediator = this.configuration.getSchemaMediator();
        if (l != -1L) {
            object2 = ((Table)object2).getUncheckedRow(l);
            return realmProxyMediator.newInstance(object, this, (Row)object2, this.schema.getColumnInfo((Class<? extends RealmModel>)object), false, Collections.<String>emptyList());
        }
        object2 = InvalidRow.INSTANCE;
        return realmProxyMediator.newInstance(object, this, (Row)object2, this.schema.getColumnInfo((Class<? extends RealmModel>)object), false, Collections.emptyList());
    }

    /*
     * Enabled aggressive block sorting
     */
    <E extends RealmModel> E get(Class<E> class_, String string2, UncheckedRow uncheckedRow) {
        boolean bl = string2 != null;
        if (bl) {
            return (E)new DynamicRealmObject(this, CheckedRow.getFromRow(uncheckedRow));
        }
        return this.configuration.getSchemaMediator().newInstance(class_, this, uncheckedRow, this.schema.getColumnInfo(class_), false, Collections.<String>emptyList());
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public RealmSchema getSchema() {
        return this.schema;
    }

    SharedRealm getSharedRealm() {
        return this.sharedRealm;
    }

    public long getVersion() {
        return this.sharedRealm.getSchemaVersion();
    }

    public boolean isInTransaction() {
        this.checkIfValid();
        return this.sharedRealm.isInTransaction();
    }

    void setVersion(long l) {
        this.sharedRealm.setSchemaVersion(l);
    }

    protected static interface MigrationCallback {
        public void migrationComplete();
    }

    public static final class RealmObjectContext {
        private boolean acceptDefaultValue;
        private ColumnInfo columnInfo;
        private List<String> excludeFields;
        private BaseRealm realm;
        private Row row;

        public void clear() {
            this.realm = null;
            this.row = null;
            this.columnInfo = null;
            this.acceptDefaultValue = false;
            this.excludeFields = null;
        }

        public boolean getAcceptDefaultValue() {
            return this.acceptDefaultValue;
        }

        public ColumnInfo getColumnInfo() {
            return this.columnInfo;
        }

        public List<String> getExcludeFields() {
            return this.excludeFields;
        }

        BaseRealm getRealm() {
            return this.realm;
        }

        public Row getRow() {
            return this.row;
        }

        public void set(BaseRealm baseRealm, Row row, ColumnInfo columnInfo, boolean bl, List<String> list) {
            this.realm = baseRealm;
            this.row = row;
            this.columnInfo = columnInfo;
            this.acceptDefaultValue = bl;
            this.excludeFields = list;
        }
    }

    static final class ThreadLocalRealmObjectContext
    extends ThreadLocal<RealmObjectContext> {
        ThreadLocalRealmObjectContext() {
        }

        @Override
        protected RealmObjectContext initialValue() {
            return new RealmObjectContext();
        }
    }

}

