/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.realm;

import android.content.Context;
import io.realm.BaseRealm;
import io.realm.CompactOnLaunchCallback;
import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.SharedRealm;
import io.realm.internal.Util;
import io.realm.internal.modules.CompositeMediator;
import io.realm.internal.modules.FilterableMediator;
import io.realm.rx.RealmObservableFactory;
import io.realm.rx.RxObservableFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class RealmConfiguration {
    private static final Object DEFAULT_MODULE = Realm.getDefaultModule();
    protected static final RealmProxyMediator DEFAULT_MODULE_MEDIATOR;
    private static Boolean rxJavaAvailable;
    private final String assetFilePath;
    private final String canonicalPath;
    private final CompactOnLaunchCallback compactOnLaunch;
    private final boolean deleteRealmIfMigrationNeeded;
    private final SharedRealm.Durability durability;
    private final Realm.Transaction initialDataTransaction;
    private final byte[] key;
    private final RealmMigration migration;
    private final boolean readOnly;
    private final File realmDirectory;
    private final String realmFileName;
    private final RxObservableFactory rxObservableFactory;
    private final RealmProxyMediator schemaMediator;
    private final long schemaVersion;

    static {
        if (DEFAULT_MODULE != null) {
            RealmProxyMediator realmProxyMediator = RealmConfiguration.getModuleMediator(DEFAULT_MODULE.getClass().getCanonicalName());
            if (!realmProxyMediator.transformerApplied()) {
                throw new ExceptionInInitializerError("RealmTransformer doesn't seem to be applied. Please update the project configuration to use the Realm Gradle plugin. See https://realm.io/news/android-installation-change/");
            }
            DEFAULT_MODULE_MEDIATOR = realmProxyMediator;
        } else {
            DEFAULT_MODULE_MEDIATOR = null;
        }
    }

    protected RealmConfiguration(File file, String string2, String string3, String string4, byte[] arrby, long l, RealmMigration realmMigration, boolean bl, SharedRealm.Durability durability, RealmProxyMediator realmProxyMediator, RxObservableFactory rxObservableFactory, Realm.Transaction transaction, boolean bl2, CompactOnLaunchCallback compactOnLaunchCallback) {
        this.realmDirectory = file;
        this.realmFileName = string2;
        this.canonicalPath = string3;
        this.assetFilePath = string4;
        this.key = arrby;
        this.schemaVersion = l;
        this.migration = realmMigration;
        this.deleteRealmIfMigrationNeeded = bl;
        this.durability = durability;
        this.schemaMediator = realmProxyMediator;
        this.rxObservableFactory = rxObservableFactory;
        this.initialDataTransaction = transaction;
        this.readOnly = bl2;
        this.compactOnLaunch = compactOnLaunchCallback;
    }

    protected static RealmProxyMediator createSchemaMediator(Set<Object> object, Set<Class<? extends RealmModel>> arrrealmProxyMediator) {
        if (arrrealmProxyMediator.size() > 0) {
            return new FilterableMediator(DEFAULT_MODULE_MEDIATOR, (Collection<Class<? extends RealmModel>>)arrrealmProxyMediator);
        }
        if (object.size() == 1) {
            return RealmConfiguration.getModuleMediator(object.iterator().next().getClass().getCanonicalName());
        }
        arrrealmProxyMediator = new RealmProxyMediator[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrrealmProxyMediator[n] = RealmConfiguration.getModuleMediator(object.next().getClass().getCanonicalName());
            ++n;
        }
        return new CompositeMediator(arrrealmProxyMediator);
    }

    protected static String getCanonicalPath(File file) {
        try {
            String string2 = file.getCanonicalPath();
            return string2;
        }
        catch (IOException iOException) {
            throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Could not resolve the canonical path to the Realm file: " + file.getAbsolutePath(), iOException);
        }
    }

    private static RealmProxyMediator getModuleMediator(String object) {
        object = object.split("\\.");
        object = object[((String[])object).length - 1];
        object = String.format(Locale.US, "io.realm.%s%s", object, "Mediator");
        try {
            Object object2 = Class.forName((String)object).getDeclaredConstructors()[0];
            ((AccessibleObject)object2).setAccessible(true);
            object2 = (RealmProxyMediator)((Constructor)object2).newInstance(new Object[0]);
            return object2;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RealmException("Could not find " + (String)object, classNotFoundException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RealmException("Could not create an instance of " + (String)object, invocationTargetException);
        }
        catch (InstantiationException instantiationException) {
            throw new RealmException("Could not create an instance of " + (String)object, instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RealmException("Could not create an instance of " + (String)object, illegalAccessException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static boolean isRxJavaAvailable() {
        synchronized (RealmConfiguration.class) {
            Boolean bl = rxJavaAvailable;
            if (bl != null) return rxJavaAvailable;
            try {
                Class.forName("rx.Observable");
                rxJavaAvailable = true;
                return rxJavaAvailable;
            }
            catch (ClassNotFoundException classNotFoundException) {
                rxJavaAvailable = false;
            }
            return rxJavaAvailable;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) {
            return true;
        }
        boolean bl2 = bl;
        if (object == null) return bl2;
        bl2 = bl;
        if (this.getClass() != object.getClass()) return bl2;
        object = (RealmConfiguration)object;
        bl2 = bl;
        if (this.schemaVersion != ((RealmConfiguration)object).schemaVersion) return bl2;
        bl2 = bl;
        if (this.deleteRealmIfMigrationNeeded != ((RealmConfiguration)object).deleteRealmIfMigrationNeeded) return bl2;
        bl2 = bl;
        if (!this.realmDirectory.equals(((RealmConfiguration)object).realmDirectory)) return bl2;
        bl2 = bl;
        if (!this.realmFileName.equals(((RealmConfiguration)object).realmFileName)) return bl2;
        bl2 = bl;
        if (!this.canonicalPath.equals(((RealmConfiguration)object).canonicalPath)) return bl2;
        bl2 = bl;
        if (!Arrays.equals(this.key, ((RealmConfiguration)object).key)) return bl2;
        bl2 = bl;
        if (!this.durability.equals((Object)((RealmConfiguration)object).durability)) return bl2;
        if (this.migration != null) {
            bl2 = bl;
            if (!this.migration.equals(((RealmConfiguration)object).migration)) return bl2;
        } else if (((RealmConfiguration)object).migration != null) {
            return false;
        }
        if (this.rxObservableFactory != null) {
            bl2 = bl;
            if (!this.rxObservableFactory.equals(((RealmConfiguration)object).rxObservableFactory)) return bl2;
        } else if (((RealmConfiguration)object).rxObservableFactory != null) {
            return false;
        }
        if (this.initialDataTransaction != null) {
            bl2 = bl;
            if (!this.initialDataTransaction.equals(((RealmConfiguration)object).initialDataTransaction)) return bl2;
        } else if (((RealmConfiguration)object).initialDataTransaction != null) {
            return false;
        }
        bl2 = bl;
        if (this.readOnly != ((RealmConfiguration)object).readOnly) return bl2;
        if (this.compactOnLaunch != null) {
            bl2 = bl;
            if (this.compactOnLaunch.equals(((RealmConfiguration)object).compactOnLaunch)) return this.schemaMediator.equals(((RealmConfiguration)object).schemaMediator);
            return bl2;
        }
        if (((RealmConfiguration)object).compactOnLaunch != null) return false;
        return this.schemaMediator.equals(((RealmConfiguration)object).schemaMediator);
    }

    String getAssetFilePath() {
        return this.assetFilePath;
    }

    public CompactOnLaunchCallback getCompactOnLaunchCallback() {
        return this.compactOnLaunch;
    }

    public SharedRealm.Durability getDurability() {
        return this.durability;
    }

    public byte[] getEncryptionKey() {
        if (this.key == null) {
            return null;
        }
        return Arrays.copyOf(this.key, this.key.length);
    }

    Realm.Transaction getInitialDataTransaction() {
        return this.initialDataTransaction;
    }

    public RealmMigration getMigration() {
        return this.migration;
    }

    public String getPath() {
        return this.canonicalPath;
    }

    public File getRealmDirectory() {
        return this.realmDirectory;
    }

    public String getRealmFileName() {
        return this.realmFileName;
    }

    RealmProxyMediator getSchemaMediator() {
        return this.schemaMediator;
    }

    public long getSchemaVersion() {
        return this.schemaVersion;
    }

    boolean hasAssetFile() {
        return !Util.isEmptyString(this.assetFilePath);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 1;
        int n2 = 0;
        int n3 = this.realmDirectory.hashCode();
        int n4 = this.realmFileName.hashCode();
        int n5 = this.canonicalPath.hashCode();
        int n6 = this.key != null ? Arrays.hashCode(this.key) : 0;
        int n7 = (int)this.schemaVersion;
        int n8 = this.migration != null ? this.migration.hashCode() : 0;
        int n9 = this.deleteRealmIfMigrationNeeded ? 1 : 0;
        int n10 = this.schemaMediator.hashCode();
        int n11 = this.durability.hashCode();
        int n12 = this.rxObservableFactory != null ? this.rxObservableFactory.hashCode() : 0;
        int n13 = this.initialDataTransaction != null ? this.initialDataTransaction.hashCode() : 0;
        if (!this.readOnly) {
            n = 0;
        }
        if (this.compactOnLaunch != null) {
            n2 = this.compactOnLaunch.hashCode();
        }
        return (((((((((((n3 * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n) * 31 + n2;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    boolean isSyncConfiguration() {
        return false;
    }

    boolean realmExists() {
        return new File(this.canonicalPath).exists();
    }

    public boolean shouldDeleteRealmIfMigrationNeeded() {
        return this.deleteRealmIfMigrationNeeded;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("realmDirectory: ").append(this.realmDirectory.toString());
        stringBuilder.append("\n");
        stringBuilder.append("realmFileName : ").append(this.realmFileName);
        stringBuilder.append("\n");
        stringBuilder.append("canonicalPath: ").append(this.canonicalPath);
        stringBuilder.append("\n");
        StringBuilder stringBuilder2 = stringBuilder.append("key: ").append("[length: ");
        int n = this.key == null ? 0 : 64;
        stringBuilder2.append(n).append("]");
        stringBuilder.append("\n");
        stringBuilder.append("schemaVersion: ").append(Long.toString(this.schemaVersion));
        stringBuilder.append("\n");
        stringBuilder.append("migration: ").append(this.migration);
        stringBuilder.append("\n");
        stringBuilder.append("deleteRealmIfMigrationNeeded: ").append(this.deleteRealmIfMigrationNeeded);
        stringBuilder.append("\n");
        stringBuilder.append("durability: ").append((Object)this.durability);
        stringBuilder.append("\n");
        stringBuilder.append("schemaMediator: ").append(this.schemaMediator);
        stringBuilder.append("\n");
        stringBuilder.append("readOnly: ").append(this.readOnly);
        stringBuilder.append("\n");
        stringBuilder.append("compactOnLaunch: ").append(this.compactOnLaunch);
        return stringBuilder.toString();
    }

    public static class Builder {
        private String assetFilePath;
        private CompactOnLaunchCallback compactOnLaunch;
        private HashSet<Class<? extends RealmModel>> debugSchema;
        private boolean deleteRealmIfMigrationNeeded;
        private File directory;
        private SharedRealm.Durability durability;
        private String fileName;
        private Realm.Transaction initialDataTransaction;
        private byte[] key;
        private RealmMigration migration;
        private HashSet<Object> modules = new HashSet();
        private boolean readOnly;
        private RxObservableFactory rxFactory;
        private long schemaVersion;

        public Builder() {
            this(BaseRealm.applicationContext);
        }

        Builder(Context context) {
            this.debugSchema = new HashSet();
            if (context == null) {
                throw new IllegalStateException("Call `Realm.init(Context)` before creating a RealmConfiguration");
            }
            RealmCore.loadLibrary(context);
            this.initializeBuilder(context);
        }

        private void initializeBuilder(Context context) {
            this.directory = context.getFilesDir();
            this.fileName = "default.realm";
            this.key = null;
            this.schemaVersion = 0L;
            this.migration = null;
            this.deleteRealmIfMigrationNeeded = false;
            this.durability = SharedRealm.Durability.FULL;
            this.readOnly = false;
            this.compactOnLaunch = null;
            if (DEFAULT_MODULE != null) {
                this.modules.add(DEFAULT_MODULE);
            }
        }

        public RealmConfiguration build() {
            if (this.readOnly) {
                if (this.initialDataTransaction != null) {
                    throw new IllegalStateException("This Realm is marked as read-only. Read-only Realms cannot use initialData(Realm.Transaction).");
                }
                if (this.assetFilePath == null) {
                    throw new IllegalStateException("Only Realms provided using 'assetFile(path)' can be marked read-only. No such Realm was provided.");
                }
                if (this.deleteRealmIfMigrationNeeded) {
                    throw new IllegalStateException("'deleteRealmIfMigrationNeeded()' and read-only Realms cannot be combined");
                }
                if (this.compactOnLaunch != null) {
                    throw new IllegalStateException("'compactOnLaunch()' and read-only Realms cannot be combined");
                }
            }
            if (this.rxFactory == null && RealmConfiguration.isRxJavaAvailable()) {
                this.rxFactory = new RealmObservableFactory();
            }
            return new RealmConfiguration(this.directory, this.fileName, RealmConfiguration.getCanonicalPath(new File(this.directory, this.fileName)), this.assetFilePath, this.key, this.schemaVersion, this.migration, this.deleteRealmIfMigrationNeeded, this.durability, RealmConfiguration.createSchemaMediator(this.modules, this.debugSchema), this.rxFactory, this.initialDataTransaction, this.readOnly, this.compactOnLaunch);
        }

        public Builder deleteRealmIfMigrationNeeded() {
            if (this.assetFilePath != null && this.assetFilePath.length() != 0) {
                throw new IllegalStateException("Realm cannot clear its schema when previously configured to use an asset file by calling assetFile().");
            }
            this.deleteRealmIfMigrationNeeded = true;
            return this;
        }

        public Builder schemaVersion(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Realm schema version numbers must be 0 (zero) or higher. Yours was: " + l);
            }
            this.schemaVersion = l;
            return this;
        }
    }

}

