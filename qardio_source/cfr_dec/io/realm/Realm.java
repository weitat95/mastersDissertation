/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.SystemClock
 */
package io.realm;

import android.content.Context;
import android.os.SystemClock;
import io.realm.BaseRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmCache;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.util.Pair;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Realm
extends BaseRealm {
    private static RealmConfiguration defaultConfiguration;
    private static final Object defaultConfigurationLock;

    static {
        defaultConfigurationLock = new Object();
    }

    private Realm(RealmCache realmCache) {
        super(realmCache);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void checkFilesDirAvailable(Context context) {
        block9: {
            block8: {
                long[] arrl;
                block7: {
                    arrl = context.getFilesDir();
                    if (arrl == null) break block7;
                    if (arrl.exists()) break block8;
                    try {
                        arrl.mkdirs();
                    }
                    catch (SecurityException securityException) {}
                }
                if (arrl == null || !arrl.exists()) {
                    long[] arrl2 = arrl = new long[5];
                    arrl[0] = 1L;
                    arrl2[1] = 2L;
                    arrl2[2] = 5L;
                    arrl2[3] = 10L;
                    arrl2[4] = 16L;
                    long l = 0L;
                    int n = -1;
                    while (context.getFilesDir() == null || !context.getFilesDir().exists()) {
                        long l2 = arrl[Math.min(++n, arrl.length - 1)];
                        SystemClock.sleep((long)l2);
                        l = l2 = l + l2;
                        if (l2 <= 200L) continue;
                    }
                }
                if (context.getFilesDir() == null || !context.getFilesDir().exists()) break block9;
            }
            return;
        }
        throw new IllegalStateException("Context.getFilesDir() returns " + context.getFilesDir() + " which is not an existing directory. See https://issuetracker.google.com/issues/36918154");
    }

    private void checkHasPrimaryKey(Class<? extends RealmModel> class_) {
        if (!this.schema.getTable(class_).hasPrimaryKey()) {
            throw new IllegalArgumentException("A RealmObject with no @PrimaryKey cannot be updated: " + class_.toString());
        }
    }

    private void checkMaxDepth(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("maxDepth must be > 0. It was: " + n);
        }
    }

    private <E extends RealmModel> void checkNotNullObject(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied into Realm.");
        }
    }

    private <E extends RealmModel> void checkValidObjectForDetach(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied from Realm.");
        }
        if (!RealmObject.isManaged(e) || !RealmObject.isValid(e)) {
            throw new IllegalArgumentException("Only valid managed objects can be copied from Realm.");
        }
        if (e instanceof DynamicRealmObject) {
            throw new IllegalArgumentException("DynamicRealmObject cannot be copied from Realm.");
        }
    }

    private <E extends RealmModel> E copyOrUpdate(E e, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        this.checkIfValid();
        return this.configuration.getSchemaMediator().copyOrUpdate(this, e, bl, map);
    }

    private static Realm createAndValidateFromCache(RealmCache object) {
        Realm realm = new Realm((RealmCache)object);
        RealmConfiguration realmConfiguration = realm.configuration;
        long l = realm.getVersion();
        long l2 = realmConfiguration.getSchemaVersion();
        if ((object = RealmCache.findColumnIndices(((RealmCache)object).getTypedColumnIndicesArray(), l2)) != null) {
            realm.schema.setInitialColumnIndices((ColumnIndices)object);
            return realm;
        }
        if (!realmConfiguration.isSyncConfiguration() && l != -1L) {
            if (l < l2) {
                realm.doClose();
                throw new RealmMigrationNeededException(realmConfiguration.getPath(), String.format(Locale.US, "Realm on disk need to migrate from v%s to v%s", l, l2));
            }
            if (l2 < l) {
                realm.doClose();
                throw new IllegalArgumentException(String.format(Locale.US, "Realm on disk is newer than the one specified: v%s vs. v%s", l, l2));
            }
        }
        try {
            Realm.initializeRealm(realm);
            return realm;
        }
        catch (RuntimeException runtimeException) {
            realm.doClose();
            throw runtimeException;
        }
    }

    private <E extends RealmModel> E createDetachedCopy(E e, int n, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        this.checkIfValid();
        return this.configuration.getSchemaMediator().createDetachedCopy(e, n, map);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Realm createInstance(RealmCache realmCache) {
        RealmConfiguration realmConfiguration = realmCache.getConfiguration();
        try {
            return Realm.createAndValidateFromCache(realmCache);
        }
        catch (RealmMigrationNeededException realmMigrationNeededException) {
            if (realmConfiguration.shouldDeleteRealmIfMigrationNeeded()) {
                Realm.deleteRealm(realmConfiguration);
                return Realm.createAndValidateFromCache(realmCache);
            }
            try {
                if (realmConfiguration.getMigration() == null) return Realm.createAndValidateFromCache(realmCache);
                Realm.migrateRealm(realmConfiguration, realmMigrationNeededException);
                return Realm.createAndValidateFromCache(realmCache);
            }
            catch (FileNotFoundException fileNotFoundException) {
                throw new RealmFileException(RealmFileException.Kind.NOT_FOUND, fileNotFoundException);
            }
        }
    }

    public static boolean deleteRealm(RealmConfiguration realmConfiguration) {
        return BaseRealm.deleteRealm(realmConfiguration);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static RealmConfiguration getDefaultConfiguration() {
        Object object = defaultConfigurationLock;
        synchronized (object) {
            return defaultConfiguration;
        }
    }

    public static Realm getDefaultInstance() {
        RealmConfiguration realmConfiguration = Realm.getDefaultConfiguration();
        if (realmConfiguration == null) {
            if (BaseRealm.applicationContext == null) {
                throw new IllegalStateException("Call `Realm.init(Context)` before calling this method.");
            }
            throw new IllegalStateException("Set default configuration by using `Realm.setDefaultConfiguration(RealmConfiguration)`.");
        }
        return RealmCache.createRealmOrGetFromCache(realmConfiguration, Realm.class);
    }

    public static Object getDefaultModule() {
        try {
            Constructor<?> constructor = Class.forName("io.realm.DefaultRealmModule").getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            constructor = constructor.newInstance(new Object[0]);
            return constructor;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RealmException("Could not create an instance of " + "io.realm.DefaultRealmModule", invocationTargetException);
        }
        catch (InstantiationException instantiationException) {
            throw new RealmException("Could not create an instance of " + "io.realm.DefaultRealmModule", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RealmException("Could not create an instance of " + "io.realm.DefaultRealmModule", illegalAccessException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void init(Context context) {
        synchronized (Realm.class) {
            if (BaseRealm.applicationContext == null) {
                if (context == null) {
                    throw new IllegalArgumentException("Non-null context required.");
                }
                Realm.checkFilesDirAvailable(context);
                RealmCore.loadLibrary(context);
                Realm.setDefaultConfiguration(new RealmConfiguration.Builder(context).build());
                ObjectServerFacade.getSyncFacadeIfPossible().init(context);
                BaseRealm.applicationContext = context.getApplicationContext();
                SharedRealm.initialize(new File(context.getFilesDir(), ".realm.temp"));
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void initializeRealm(Realm realm) {
        Throwable throwable2;
        block15: {
            Object object;
            boolean bl;
            Object object2;
            long l;
            Object object3;
            Object object4;
            boolean bl2;
            long l2;
            boolean bl3;
            block13: {
                boolean bl4 = false;
                bl3 = false;
                bl = bl4;
                try {
                    try {
                        realm.beginTransaction(true);
                        bl = bl4;
                        object2 = realm.getConfiguration();
                        bl = bl4;
                        l2 = realm.getVersion();
                        bl2 = l2 == -1L;
                        bl = bl4;
                        l = ((RealmConfiguration)object2).getSchemaVersion();
                        bl = bl4;
                        object4 = ((RealmConfiguration)object2).getSchemaMediator();
                        bl = bl4;
                        object = ((RealmProxyMediator)object4).getModelClasses();
                        bl = bl4;
                        if (((RealmConfiguration)object2).isSyncConfiguration()) {
                            bl = bl4;
                            if (!((RealmConfiguration)object2).isReadOnly()) {
                                bl = bl4;
                                object3 = new OsSchemaInfo(((RealmProxyMediator)object4).getExpectedObjectSchemaInfoMap().values());
                                bl = bl4;
                                realm.sharedRealm.updateSchema((OsSchemaInfo)object3, l);
                                bl3 = true;
                            }
                            break block13;
                        }
                        if (!bl2) break block13;
                        bl = bl4;
                        if (((RealmConfiguration)object2).isReadOnly()) {
                            bl = bl4;
                            throw new IllegalArgumentException("Cannot create the Realm schema in a read-only file.");
                        }
                    }
                    catch (Exception exception) {
                        bl = false;
                        throw exception;
                    }
                }
                catch (Throwable throwable2) {
                    if (bl) {
                        realm.commitTransaction();
                        throw throwable2;
                    }
                    break block15;
                }
                bl = bl4;
                object3 = new OsSchemaInfo(((RealmProxyMediator)object4).getExpectedObjectSchemaInfoMap().values());
                bl = bl4;
                realm.sharedRealm.updateSchema((OsSchemaInfo)object3, l);
                bl3 = true;
            }
            bl = bl3;
            object3 = new HashMap(object.size());
            bl = bl3;
            object = object.iterator();
            do {
                bl = bl3;
                if (!object.hasNext()) break;
                bl = bl3;
                Class class_ = (Class)object.next();
                bl = bl3;
                object3.put(Pair.create(class_, Table.getClassNameForTable(((RealmProxyMediator)object4).getTableName(class_))), ((RealmProxyMediator)object4).validateTable(class_, realm.sharedRealm, ((RealmConfiguration)object2).isSyncConfiguration()));
            } while (true);
            bl = bl3;
            object4 = realm.getSchema();
            if (bl2) {
                l2 = l;
            }
            bl = bl3;
            ((RealmSchema)object4).setInitialColumnIndices(l2, (Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo>)object3);
            bl = bl3;
            object2 = ((RealmConfiguration)object2).getInitialDataTransaction();
            if (object2 != null && bl2) {
                bl = bl3;
                object2.execute(realm);
            }
            if (bl3) {
                realm.commitTransaction();
                return;
            }
            if (!realm.isInTransaction()) return;
            realm.cancelTransaction();
            return;
        }
        if (!realm.isInTransaction()) throw throwable2;
        realm.cancelTransaction();
        throw throwable2;
    }

    private static void migrateRealm(RealmConfiguration realmConfiguration, RealmMigrationNeededException realmMigrationNeededException) throws FileNotFoundException {
        BaseRealm.migrateRealm(realmConfiguration, null, new BaseRealm.MigrationCallback(){

            @Override
            public void migrationComplete() {
            }
        }, realmMigrationNeededException);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefaultConfiguration(RealmConfiguration realmConfiguration) {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
        }
        Object object = defaultConfigurationLock;
        synchronized (object) {
            defaultConfiguration = realmConfiguration;
            return;
        }
    }

    public <E extends RealmModel> E copyFromRealm(E e) {
        return this.copyFromRealm(e, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> E copyFromRealm(E e, int n) {
        this.checkMaxDepth(n);
        this.checkValidObjectForDetach(e);
        return this.createDetachedCopy(e, n, new HashMap<RealmModel, RealmObjectProxy.CacheData<RealmModel>>());
    }

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable) {
        return this.copyFromRealm(iterable, Integer.MAX_VALUE);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> object, int n) {
        this.checkMaxDepth(n);
        if (object == null) {
            return new ArrayList(0);
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        HashMap<RealmModel, RealmObjectProxy.CacheData<RealmModel>> hashMap = new HashMap<RealmModel, RealmObjectProxy.CacheData<RealmModel>>();
        Iterator iterator = object.iterator();
        do {
            object = arrayList;
            if (!iterator.hasNext()) return object;
            object = (RealmModel)iterator.next();
            this.checkValidObjectForDetach((E)object);
            arrayList.add(this.createDetachedCopy((E)object, n, (Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>>)hashMap));
        } while (true);
    }

    public <E extends RealmModel> E copyToRealm(E e) {
        this.checkNotNullObject(e);
        return this.copyOrUpdate(e, false, new HashMap<RealmModel, RealmObjectProxy>());
    }

    public <E extends RealmModel> E copyToRealmOrUpdate(E e) {
        this.checkNotNullObject(e);
        this.checkHasPrimaryKey(e.getClass());
        return this.copyOrUpdate(e, true, new HashMap<RealmModel, RealmObjectProxy>());
    }

    <E extends RealmModel> E createObjectInternal(Class<E> class_, Object object, boolean bl, List<String> list) {
        Table table = this.schema.getTable(class_);
        return this.configuration.getSchemaMediator().newInstance(class_, this, OsObject.createWithPrimaryKey(table, object), this.schema.getColumnInfo(class_), bl, list);
    }

    <E extends RealmModel> E createObjectInternal(Class<E> class_, boolean bl, List<String> list) {
        Table table = this.schema.getTable(class_);
        if (table.hasPrimaryKey()) {
            throw new RealmException(String.format(Locale.US, "'%s' has a primary key, use 'createObject(Class<E>, Object)' instead.", table.getClassName()));
        }
        return this.configuration.getSchemaMediator().newInstance(class_, this, OsObject.create(table), this.schema.getColumnInfo(class_), bl, list);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void executeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        this.beginTransaction();
        try {
            transaction.execute(this);
            this.commitTransaction();
            return;
        }
        catch (Throwable throwable) {
            if (this.isInTransaction()) {
                this.cancelTransaction();
                do {
                    throw throwable;
                    break;
                } while (true);
            }
            RealmLog.warn("Could not cancel transaction, not currently in a transaction.", new Object[0]);
            throw throwable;
        }
    }

    Table getTable(Class<? extends RealmModel> class_) {
        return this.schema.getTable(class_);
    }

    ColumnIndices updateSchemaCache(ColumnIndices[] object) {
        long l = this.sharedRealm.getSchemaVersion();
        if (l == this.schema.getSchemaVersion()) {
            return null;
        }
        Object object2 = null;
        Iterator iterator = RealmCache.findColumnIndices((ColumnIndices[])object, l);
        object = iterator;
        if (iterator == null) {
            object = this.getConfiguration().getSchemaMediator();
            iterator = ((RealmProxyMediator)object).getModelClasses();
            object2 = new HashMap<Pair<Class<? extends RealmModel>, String>, ColumnInfo>(iterator.size());
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                Class class_ = (Class)iterator.next();
                ColumnInfo columnInfo = ((RealmProxyMediator)object).validateTable(class_, this.sharedRealm, true);
                object2.put(Pair.create(class_, Table.getClassNameForTable(((RealmProxyMediator)object).getTableName(class_))), columnInfo);
            }
            object = object2 = new ColumnIndices(l, (Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo>)object2);
        }
        this.schema.updateColumnIndices((ColumnIndices)object);
        return object2;
    }

    public <E extends RealmModel> RealmQuery<E> where(Class<E> class_) {
        this.checkIfValid();
        return RealmQuery.createQuery(this, class_);
    }

    public static interface Transaction {
        public void execute(Realm var1);
    }

}

