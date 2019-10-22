/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;
import io.realm.BaseRealm;
import io.realm.BpLastMeasurementRealmProxy;
import io.realm.IFollowUserRealmProxyInterface;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmSchema;
import io.realm.WeightLastMeasurementRealmProxy;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.log.RealmLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IFollowUserRealmProxy
extends IFollowUser
implements IFollowUserRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private IFollowUserColumnInfo columnInfo;
    private ProxyState<IFollowUser> proxyState;

    static {
        expectedObjectSchemaInfo = IFollowUserRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("userId");
        arrayList.add("watchingEmail");
        arrayList.add("userEmail");
        arrayList.add("accessStatus");
        arrayList.add("firstName");
        arrayList.add("lastName");
        arrayList.add("notificationsEnabled");
        arrayList.add("weightLastMeasurement");
        arrayList.add("bpLastMeasurement");
        arrayList.add("syncStatus");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    IFollowUserRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static IFollowUser copy(Realm realm, IFollowUser iFollowUserRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        BpLastMeasurement bpLastMeasurement;
        RealmObjectProxy realmObjectProxy = map.get(iFollowUserRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (IFollowUser)((Object)realmObjectProxy);
        }
        IFollowUser iFollowUser = realm.createObjectInternal(IFollowUser.class, iFollowUserRealmProxyInterface.realmGet$watchingEmail(), false, Collections.<String>emptyList());
        map.put((RealmModel)((Object)iFollowUserRealmProxyInterface), (RealmObjectProxy)((Object)iFollowUser));
        IFollowUserRealmProxyInterface iFollowUserRealmProxyInterface2 = iFollowUser;
        iFollowUserRealmProxyInterface2.realmSet$userId(iFollowUserRealmProxyInterface.realmGet$userId());
        iFollowUserRealmProxyInterface2.realmSet$userEmail(iFollowUserRealmProxyInterface.realmGet$userEmail());
        iFollowUserRealmProxyInterface2.realmSet$accessStatus(iFollowUserRealmProxyInterface.realmGet$accessStatus());
        iFollowUserRealmProxyInterface2.realmSet$firstName(iFollowUserRealmProxyInterface.realmGet$firstName());
        iFollowUserRealmProxyInterface2.realmSet$lastName(iFollowUserRealmProxyInterface.realmGet$lastName());
        iFollowUserRealmProxyInterface2.realmSet$notificationsEnabled(iFollowUserRealmProxyInterface.realmGet$notificationsEnabled());
        WeightLastMeasurement weightLastMeasurement = iFollowUserRealmProxyInterface.realmGet$weightLastMeasurement();
        if (weightLastMeasurement == null) {
            iFollowUserRealmProxyInterface2.realmSet$weightLastMeasurement(null);
        } else {
            WeightLastMeasurement weightLastMeasurement2 = (WeightLastMeasurement)((Object)map.get(weightLastMeasurement));
            if (weightLastMeasurement2 != null) {
                iFollowUserRealmProxyInterface2.realmSet$weightLastMeasurement(weightLastMeasurement2);
            } else {
                iFollowUserRealmProxyInterface2.realmSet$weightLastMeasurement(WeightLastMeasurementRealmProxy.copyOrUpdate(realm, weightLastMeasurement, bl, map));
            }
        }
        if ((bpLastMeasurement = iFollowUserRealmProxyInterface.realmGet$bpLastMeasurement()) == null) {
            iFollowUserRealmProxyInterface2.realmSet$bpLastMeasurement(null);
        } else {
            BpLastMeasurement bpLastMeasurement2 = (BpLastMeasurement)((Object)map.get(bpLastMeasurement));
            if (bpLastMeasurement2 != null) {
                iFollowUserRealmProxyInterface2.realmSet$bpLastMeasurement(bpLastMeasurement2);
            } else {
                iFollowUserRealmProxyInterface2.realmSet$bpLastMeasurement(BpLastMeasurementRealmProxy.copyOrUpdate(realm, bpLastMeasurement, bl, map));
            }
        }
        iFollowUserRealmProxyInterface2.realmSet$syncStatus(iFollowUserRealmProxyInterface.realmGet$syncStatus());
        return iFollowUser;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static IFollowUser copyOrUpdate(Realm var0, IFollowUser var1_4, boolean var2_5, Map<RealmModel, RealmObjectProxy> var3_6) {
        block7: {
            block8: {
                if (var1_4 instanceof RealmObjectProxy && ((RealmObjectProxy)var1_4).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)var1_4).realmGet$proxyState().getRealm$realm().threadId != var0.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (var1_4 instanceof RealmObjectProxy && ((RealmObjectProxy)var1_4).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)var1_4).realmGet$proxyState().getRealm$realm().getPath().equals(var0.getPath())) {
                    return var1_4;
                }
                var10_7 = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
                var8_8 = var3_6.get(var1_4);
                if (var8_8 != null) {
                    return (IFollowUser)var8_8;
                }
                var9_9 = null;
                var5_11 = var4_10 = var2_5;
                var8_8 = var9_9;
                if (!var4_10) break block7;
                var8_8 = var0.getTable(IFollowUser.class);
                var6_12 = var8_8.getPrimaryKey();
                var11_13 = ((IFollowUserRealmProxyInterface)var1_4).realmGet$watchingEmail();
                var6_12 = var11_13 == null ? var8_8.findFirstNull(var6_12) : var8_8.findFirstString(var6_12, var11_13);
                if (var6_12 == -1L) break block8;
                try {
                    var10_7.set(var0, var8_8.getUncheckedRow(var6_12), var0.schema.getColumnInfo(IFollowUser.class), false, Collections.<String>emptyList());
                    var8_8 = new IFollowUserRealmProxy();
                }
                catch (Throwable var0_1) {}
                try {
                    var3_6.put(var1_4, (RealmObjectProxy)var8_8);
                    var10_7.clear();
                    var5_11 = var4_10;
                    break block7;
                }
                catch (Throwable var0_3) {}
                ** GOTO lbl-1000
            }
            var5_11 = false;
            var8_8 = var9_9;
        }
        if (var5_11 == false) return IFollowUserRealmProxy.copy(var0, var1_4, var2_5, var3_6);
        return IFollowUserRealmProxy.update(var0, (IFollowUser)var8_8, var1_4, var3_6);
lbl-1000:
        // 2 sources
        {
            var10_7.clear();
            throw var0_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static IFollowUser createDetachedCopy(IFollowUser iFollowUserRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        IFollowUser iFollowUser;
        if (n > n2 || iFollowUserRealmProxyInterface == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(iFollowUserRealmProxyInterface);
        if (cacheData == null) {
            iFollowUser = new IFollowUser();
            map.put((RealmModel)((Object)iFollowUserRealmProxyInterface), new RealmObjectProxy.CacheData<IFollowUser>(n, iFollowUser));
        } else {
            if (n >= cacheData.minDepth) {
                return (IFollowUser)cacheData.object;
            }
            iFollowUser = (IFollowUser)cacheData.object;
            cacheData.minDepth = n;
        }
        cacheData = iFollowUser;
        cacheData.realmSet$userId(iFollowUserRealmProxyInterface.realmGet$userId());
        cacheData.realmSet$watchingEmail(iFollowUserRealmProxyInterface.realmGet$watchingEmail());
        cacheData.realmSet$userEmail(iFollowUserRealmProxyInterface.realmGet$userEmail());
        cacheData.realmSet$accessStatus(iFollowUserRealmProxyInterface.realmGet$accessStatus());
        cacheData.realmSet$firstName(iFollowUserRealmProxyInterface.realmGet$firstName());
        cacheData.realmSet$lastName(iFollowUserRealmProxyInterface.realmGet$lastName());
        cacheData.realmSet$notificationsEnabled(iFollowUserRealmProxyInterface.realmGet$notificationsEnabled());
        cacheData.realmSet$weightLastMeasurement(WeightLastMeasurementRealmProxy.createDetachedCopy(iFollowUserRealmProxyInterface.realmGet$weightLastMeasurement(), n + 1, n2, map));
        cacheData.realmSet$bpLastMeasurement(BpLastMeasurementRealmProxy.createDetachedCopy(iFollowUserRealmProxyInterface.realmGet$bpLastMeasurement(), n + 1, n2, map));
        cacheData.realmSet$syncStatus(iFollowUserRealmProxyInterface.realmGet$syncStatus());
        return iFollowUser;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("IFollowUser");
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("watchingEmail", RealmFieldType.STRING, true, true, false);
        builder.addProperty("userEmail", RealmFieldType.STRING, false, true, false);
        builder.addProperty("accessStatus", RealmFieldType.STRING, false, false, false);
        builder.addProperty("firstName", RealmFieldType.STRING, false, false, false);
        builder.addProperty("lastName", RealmFieldType.STRING, false, false, false);
        builder.addProperty("notificationsEnabled", RealmFieldType.BOOLEAN, false, false, true);
        builder.addLinkedProperty("weightLastMeasurement", RealmFieldType.OBJECT, "WeightLastMeasurement");
        builder.addLinkedProperty("bpLastMeasurement", RealmFieldType.OBJECT, "BpLastMeasurement");
        builder.addProperty("syncStatus", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_IFollowUser";
    }

    /*
     * Enabled aggressive block sorting
     */
    static IFollowUser update(Realm realm, IFollowUser iFollowUser, IFollowUser iFollowUserRealmProxyInterface, Map<RealmModel, RealmObjectProxy> map) {
        BpLastMeasurement bpLastMeasurement;
        IFollowUserRealmProxyInterface iFollowUserRealmProxyInterface2 = iFollowUser;
        iFollowUserRealmProxyInterface2.realmSet$userId(iFollowUserRealmProxyInterface.realmGet$userId());
        iFollowUserRealmProxyInterface2.realmSet$userEmail(iFollowUserRealmProxyInterface.realmGet$userEmail());
        iFollowUserRealmProxyInterface2.realmSet$accessStatus(iFollowUserRealmProxyInterface.realmGet$accessStatus());
        iFollowUserRealmProxyInterface2.realmSet$firstName(iFollowUserRealmProxyInterface.realmGet$firstName());
        iFollowUserRealmProxyInterface2.realmSet$lastName(iFollowUserRealmProxyInterface.realmGet$lastName());
        iFollowUserRealmProxyInterface2.realmSet$notificationsEnabled(iFollowUserRealmProxyInterface.realmGet$notificationsEnabled());
        WeightLastMeasurement weightLastMeasurement = iFollowUserRealmProxyInterface.realmGet$weightLastMeasurement();
        if (weightLastMeasurement == null) {
            iFollowUserRealmProxyInterface2.realmSet$weightLastMeasurement(null);
        } else {
            WeightLastMeasurement weightLastMeasurement2 = (WeightLastMeasurement)((Object)map.get(weightLastMeasurement));
            if (weightLastMeasurement2 != null) {
                iFollowUserRealmProxyInterface2.realmSet$weightLastMeasurement(weightLastMeasurement2);
            } else {
                iFollowUserRealmProxyInterface2.realmSet$weightLastMeasurement(WeightLastMeasurementRealmProxy.copyOrUpdate(realm, weightLastMeasurement, true, map));
            }
        }
        if ((bpLastMeasurement = iFollowUserRealmProxyInterface.realmGet$bpLastMeasurement()) == null) {
            iFollowUserRealmProxyInterface2.realmSet$bpLastMeasurement(null);
        } else {
            BpLastMeasurement bpLastMeasurement2 = (BpLastMeasurement)((Object)map.get(bpLastMeasurement));
            if (bpLastMeasurement2 != null) {
                iFollowUserRealmProxyInterface2.realmSet$bpLastMeasurement(bpLastMeasurement2);
            } else {
                iFollowUserRealmProxyInterface2.realmSet$bpLastMeasurement(BpLastMeasurementRealmProxy.copyOrUpdate(realm, bpLastMeasurement, true, map));
            }
        }
        iFollowUserRealmProxyInterface2.realmSet$syncStatus(iFollowUserRealmProxyInterface.realmGet$syncStatus());
        return iFollowUser;
    }

    public static IFollowUserColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_IFollowUser")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'IFollowUser' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_IFollowUser");
        long l = table.getColumnCount();
        if (l != 10L) {
            if (l < 10L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 10 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 10 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 10 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        IFollowUserColumnInfo iFollowUserColumnInfo = new IFollowUserColumnInfo(sharedRealm, table);
        if (!table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'watchingEmail' in existing Realm file. @PrimaryKey was added.");
        }
        if (table.getPrimaryKey() != iFollowUserColumnInfo.watchingEmailIndex) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field watchingEmail");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(iFollowUserColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("watchingEmail")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'watchingEmail' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("watchingEmail") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'watchingEmail' in existing Realm file.");
        }
        if (!table.isColumnNullable(iFollowUserColumnInfo.watchingEmailIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "@PrimaryKey field 'watchingEmail' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("watchingEmail"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'watchingEmail' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!hashMap.containsKey("userEmail")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userEmail' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userEmail") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'userEmail' in existing Realm file.");
        }
        if (!table.isColumnNullable(iFollowUserColumnInfo.userEmailIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userEmail' is required. Either set @Required to field 'userEmail' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("userEmail"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'userEmail' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!hashMap.containsKey("accessStatus")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'accessStatus' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("accessStatus") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'accessStatus' in existing Realm file.");
        }
        if (!table.isColumnNullable(iFollowUserColumnInfo.accessStatusIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'accessStatus' is required. Either set @Required to field 'accessStatus' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("firstName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'firstName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("firstName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'firstName' in existing Realm file.");
        }
        if (!table.isColumnNullable(iFollowUserColumnInfo.firstNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'firstName' is required. Either set @Required to field 'firstName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("lastName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'lastName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("lastName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'lastName' in existing Realm file.");
        }
        if (!table.isColumnNullable(iFollowUserColumnInfo.lastNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'lastName' is required. Either set @Required to field 'lastName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("notificationsEnabled")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'notificationsEnabled' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("notificationsEnabled") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'notificationsEnabled' in existing Realm file.");
        }
        if (table.isColumnNullable(iFollowUserColumnInfo.notificationsEnabledIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'notificationsEnabled' does support null values in the existing Realm file. Use corresponding boxed type for field 'notificationsEnabled' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("weightLastMeasurement")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'weightLastMeasurement' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("weightLastMeasurement") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'WeightLastMeasurement' for field 'weightLastMeasurement'");
        }
        if (!sharedRealm.hasTable("class_WeightLastMeasurement")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_WeightLastMeasurement' for field 'weightLastMeasurement'");
        }
        Table table2 = sharedRealm.getTable("class_WeightLastMeasurement");
        if (!table.getLinkTarget(iFollowUserColumnInfo.weightLastMeasurementIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'weightLastMeasurement': '" + table.getLinkTarget(iFollowUserColumnInfo.weightLastMeasurementIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!hashMap.containsKey("bpLastMeasurement")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'bpLastMeasurement' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("bpLastMeasurement") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'BpLastMeasurement' for field 'bpLastMeasurement'");
        }
        if (!sharedRealm.hasTable("class_BpLastMeasurement")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_BpLastMeasurement' for field 'bpLastMeasurement'");
        }
        table2 = sharedRealm.getTable("class_BpLastMeasurement");
        if (!table.getLinkTarget(iFollowUserColumnInfo.bpLastMeasurementIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'bpLastMeasurement': '" + table.getLinkTarget(iFollowUserColumnInfo.bpLastMeasurementIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!hashMap.containsKey("syncStatus")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'syncStatus' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("syncStatus") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'syncStatus' in existing Realm file.");
        }
        if (table.isColumnNullable(iFollowUserColumnInfo.syncStatusIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'syncStatus' does support null values in the existing Realm file. Use corresponding boxed type for field 'syncStatus' or migrate using RealmObjectSchema.setNullable().");
        }
        return iFollowUserColumnInfo;
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        this.columnInfo = (IFollowUserColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<IFollowUserRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public String realmGet$accessStatus() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.accessStatusIndex);
    }

    @Override
    public BpLastMeasurement realmGet$bpLastMeasurement() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.bpLastMeasurementIndex)) {
            return null;
        }
        return this.proxyState.getRealm$realm().get(BpLastMeasurement.class, this.proxyState.getRow$realm().getLink(this.columnInfo.bpLastMeasurementIndex), false, Collections.<String>emptyList());
    }

    @Override
    public String realmGet$firstName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.firstNameIndex);
    }

    @Override
    public String realmGet$lastName() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.lastNameIndex);
    }

    @Override
    public boolean realmGet$notificationsEnabled() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getBoolean(this.columnInfo.notificationsEnabledIndex);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public int realmGet$syncStatus() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.syncStatusIndex);
    }

    @Override
    public String realmGet$userEmail() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.userEmailIndex);
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public String realmGet$watchingEmail() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.watchingEmailIndex);
    }

    @Override
    public WeightLastMeasurement realmGet$weightLastMeasurement() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.weightLastMeasurementIndex)) {
            return null;
        }
        return this.proxyState.getRealm$realm().get(WeightLastMeasurement.class, this.proxyState.getRow$realm().getLink(this.columnInfo.weightLastMeasurementIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$accessStatus(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.accessStatusIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.accessStatusIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.accessStatusIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.accessStatusIndex, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void realmSet$bpLastMeasurement(BpLastMeasurement object) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("bpLastMeasurement")) {
                return;
            }
            Object object2 = object;
            if (object != null) {
                object2 = object;
                if (!RealmObject.isManaged(object)) {
                    object2 = (BpLastMeasurement)((Realm)this.proxyState.getRealm$realm()).copyToRealm(object);
                }
            }
            object = this.proxyState.getRow$realm();
            if (object2 == null) {
                object.nullifyLink(this.columnInfo.bpLastMeasurementIndex);
                return;
            }
            if (!RealmObject.isValid(object2)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy)object2).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            object.getTable().setLink(this.columnInfo.bpLastMeasurementIndex, object.getIndex(), ((RealmObjectProxy)object2).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (object == null) {
            this.proxyState.getRow$realm().nullifyLink(this.columnInfo.bpLastMeasurementIndex);
            return;
        }
        if (!RealmObject.isManaged(object) || !RealmObject.isValid(object)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        this.proxyState.getRow$realm().setLink(this.columnInfo.bpLastMeasurementIndex, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public void realmSet$firstName(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.firstNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.firstNameIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.firstNameIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.firstNameIndex, string2);
    }

    @Override
    public void realmSet$lastName(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.lastNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.lastNameIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.lastNameIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.lastNameIndex, string2);
    }

    @Override
    public void realmSet$notificationsEnabled(boolean bl) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setBoolean(this.columnInfo.notificationsEnabledIndex, row.getIndex(), bl, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setBoolean(this.columnInfo.notificationsEnabledIndex, bl);
    }

    @Override
    public void realmSet$syncStatus(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.syncStatusIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.syncStatusIndex, n);
    }

    @Override
    public void realmSet$userEmail(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.userEmailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.userEmailIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.userEmailIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.userEmailIndex, string2);
    }

    @Override
    public void realmSet$userId(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.userIdIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.userIdIndex, l);
    }

    @Override
    public void realmSet$watchingEmail(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        throw new RealmException("Primary key field 'watchingEmail' cannot be changed after object was created.");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void realmSet$weightLastMeasurement(WeightLastMeasurement object) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("weightLastMeasurement")) {
                return;
            }
            Object object2 = object;
            if (object != null) {
                object2 = object;
                if (!RealmObject.isManaged(object)) {
                    object2 = (WeightLastMeasurement)((Realm)this.proxyState.getRealm$realm()).copyToRealm(object);
                }
            }
            object = this.proxyState.getRow$realm();
            if (object2 == null) {
                object.nullifyLink(this.columnInfo.weightLastMeasurementIndex);
                return;
            }
            if (!RealmObject.isValid(object2)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy)object2).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            object.getTable().setLink(this.columnInfo.weightLastMeasurementIndex, object.getIndex(), ((RealmObjectProxy)object2).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (object == null) {
            this.proxyState.getRow$realm().nullifyLink(this.columnInfo.weightLastMeasurementIndex);
            return;
        }
        if (!RealmObject.isManaged(object) || !RealmObject.isValid(object)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        this.proxyState.getRow$realm().setLink(this.columnInfo.weightLastMeasurementIndex, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("IFollowUser = proxy[");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{watchingEmail:");
        String string2 = this.realmGet$watchingEmail() != null ? this.realmGet$watchingEmail() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userEmail:");
        string2 = this.realmGet$userEmail() != null ? this.realmGet$userEmail() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{accessStatus:");
        string2 = this.realmGet$accessStatus() != null ? this.realmGet$accessStatus() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{firstName:");
        string2 = this.realmGet$firstName() != null ? this.realmGet$firstName() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lastName:");
        string2 = this.realmGet$lastName() != null ? this.realmGet$lastName() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notificationsEnabled:");
        stringBuilder.append(this.realmGet$notificationsEnabled());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{weightLastMeasurement:");
        string2 = this.realmGet$weightLastMeasurement() != null ? "WeightLastMeasurement" : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{bpLastMeasurement:");
        string2 = this.realmGet$bpLastMeasurement() != null ? "BpLastMeasurement" : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{syncStatus:");
        stringBuilder.append(this.realmGet$syncStatus());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class IFollowUserColumnInfo
    extends ColumnInfo {
        long accessStatusIndex;
        long bpLastMeasurementIndex;
        long firstNameIndex;
        long lastNameIndex;
        long notificationsEnabledIndex;
        long syncStatusIndex;
        long userEmailIndex;
        long userIdIndex;
        long watchingEmailIndex;
        long weightLastMeasurementIndex;

        IFollowUserColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        IFollowUserColumnInfo(SharedRealm sharedRealm, Table table) {
            super(10);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.watchingEmailIndex = this.addColumnDetails(table, "watchingEmail", RealmFieldType.STRING);
            this.userEmailIndex = this.addColumnDetails(table, "userEmail", RealmFieldType.STRING);
            this.accessStatusIndex = this.addColumnDetails(table, "accessStatus", RealmFieldType.STRING);
            this.firstNameIndex = this.addColumnDetails(table, "firstName", RealmFieldType.STRING);
            this.lastNameIndex = this.addColumnDetails(table, "lastName", RealmFieldType.STRING);
            this.notificationsEnabledIndex = this.addColumnDetails(table, "notificationsEnabled", RealmFieldType.BOOLEAN);
            this.weightLastMeasurementIndex = this.addColumnDetails(table, "weightLastMeasurement", RealmFieldType.OBJECT);
            this.bpLastMeasurementIndex = this.addColumnDetails(table, "bpLastMeasurement", RealmFieldType.OBJECT);
            this.syncStatusIndex = this.addColumnDetails(table, "syncStatus", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new IFollowUserColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (IFollowUserColumnInfo)columnInfo;
            columnInfo2 = (IFollowUserColumnInfo)columnInfo2;
            ((IFollowUserColumnInfo)columnInfo2).userIdIndex = ((IFollowUserColumnInfo)columnInfo).userIdIndex;
            ((IFollowUserColumnInfo)columnInfo2).watchingEmailIndex = ((IFollowUserColumnInfo)columnInfo).watchingEmailIndex;
            ((IFollowUserColumnInfo)columnInfo2).userEmailIndex = ((IFollowUserColumnInfo)columnInfo).userEmailIndex;
            ((IFollowUserColumnInfo)columnInfo2).accessStatusIndex = ((IFollowUserColumnInfo)columnInfo).accessStatusIndex;
            ((IFollowUserColumnInfo)columnInfo2).firstNameIndex = ((IFollowUserColumnInfo)columnInfo).firstNameIndex;
            ((IFollowUserColumnInfo)columnInfo2).lastNameIndex = ((IFollowUserColumnInfo)columnInfo).lastNameIndex;
            ((IFollowUserColumnInfo)columnInfo2).notificationsEnabledIndex = ((IFollowUserColumnInfo)columnInfo).notificationsEnabledIndex;
            ((IFollowUserColumnInfo)columnInfo2).weightLastMeasurementIndex = ((IFollowUserColumnInfo)columnInfo).weightLastMeasurementIndex;
            ((IFollowUserColumnInfo)columnInfo2).bpLastMeasurementIndex = ((IFollowUserColumnInfo)columnInfo).bpLastMeasurementIndex;
            ((IFollowUserColumnInfo)columnInfo2).syncStatusIndex = ((IFollowUserColumnInfo)columnInfo).syncStatusIndex;
        }
    }

}

