/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import io.realm.BaseRealm;
import io.realm.FollowMeUserRealmProxyInterface;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmSchema;
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

public class FollowMeUserRealmProxy
extends FollowMeUser
implements FollowMeUserRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private FollowMeUserColumnInfo columnInfo;
    private ProxyState<FollowMeUser> proxyState;

    static {
        expectedObjectSchemaInfo = FollowMeUserRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("watcherEmail");
        arrayList.add("userEmail");
        arrayList.add("accessStatus");
        arrayList.add("notificationsEnabled");
        arrayList.add("firstName");
        arrayList.add("lastName");
        arrayList.add("syncStatus");
        arrayList.add("userId");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    FollowMeUserRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static FollowMeUser copy(Realm object, FollowMeUser followMeUserRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(followMeUserRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (FollowMeUser)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(FollowMeUser.class, ((FollowMeUserRealmProxyInterface)followMeUserRealmProxyInterface).realmGet$watcherEmail(), false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)followMeUserRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        followMeUserRealmProxyInterface = followMeUserRealmProxyInterface;
        object2 = (FollowMeUserRealmProxyInterface)object;
        object2.realmSet$userEmail(followMeUserRealmProxyInterface.realmGet$userEmail());
        object2.realmSet$accessStatus(followMeUserRealmProxyInterface.realmGet$accessStatus());
        object2.realmSet$notificationsEnabled(followMeUserRealmProxyInterface.realmGet$notificationsEnabled());
        object2.realmSet$firstName(followMeUserRealmProxyInterface.realmGet$firstName());
        object2.realmSet$lastName(followMeUserRealmProxyInterface.realmGet$lastName());
        object2.realmSet$syncStatus(followMeUserRealmProxyInterface.realmGet$syncStatus());
        object2.realmSet$userId(followMeUserRealmProxyInterface.realmGet$userId());
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static FollowMeUser copyOrUpdate(Realm var0, FollowMeUser var1_4, boolean var2_5, Map<RealmModel, RealmObjectProxy> var3_6) {
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
                    return (FollowMeUser)var8_8;
                }
                var9_9 = null;
                var5_11 = var4_10 = var2_5;
                var8_8 = var9_9;
                if (!var4_10) break block7;
                var8_8 = var0.getTable(FollowMeUser.class);
                var6_12 = var8_8.getPrimaryKey();
                var11_13 = ((FollowMeUserRealmProxyInterface)var1_4).realmGet$watcherEmail();
                var6_12 = var11_13 == null ? var8_8.findFirstNull(var6_12) : var8_8.findFirstString(var6_12, var11_13);
                if (var6_12 == -1L) break block8;
                try {
                    var10_7.set(var0, var8_8.getUncheckedRow(var6_12), var0.schema.getColumnInfo(FollowMeUser.class), false, Collections.<String>emptyList());
                    var8_8 = new FollowMeUserRealmProxy();
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
        if (var5_11 == false) return FollowMeUserRealmProxy.copy(var0, var1_4, var2_5, var3_6);
        return FollowMeUserRealmProxy.update(var0, (FollowMeUser)var8_8, var1_4, var3_6);
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
    public static FollowMeUser createDetachedCopy(FollowMeUser followMeUserRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || followMeUserRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(followMeUserRealmProxyInterface);
        if (object2 == null) {
            object2 = new FollowMeUser();
            object.put(followMeUserRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (FollowMeUser)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (FollowMeUser)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (FollowMeUserRealmProxyInterface)object;
        object2.realmSet$watcherEmail(followMeUserRealmProxyInterface.realmGet$watcherEmail());
        object2.realmSet$userEmail(followMeUserRealmProxyInterface.realmGet$userEmail());
        object2.realmSet$accessStatus(followMeUserRealmProxyInterface.realmGet$accessStatus());
        object2.realmSet$notificationsEnabled(followMeUserRealmProxyInterface.realmGet$notificationsEnabled());
        object2.realmSet$firstName(followMeUserRealmProxyInterface.realmGet$firstName());
        object2.realmSet$lastName(followMeUserRealmProxyInterface.realmGet$lastName());
        object2.realmSet$syncStatus(followMeUserRealmProxyInterface.realmGet$syncStatus());
        object2.realmSet$userId(followMeUserRealmProxyInterface.realmGet$userId());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("FollowMeUser");
        builder.addProperty("watcherEmail", RealmFieldType.STRING, true, true, false);
        builder.addProperty("userEmail", RealmFieldType.STRING, false, true, false);
        builder.addProperty("accessStatus", RealmFieldType.STRING, false, false, false);
        builder.addProperty("notificationsEnabled", RealmFieldType.BOOLEAN, false, false, true);
        builder.addProperty("firstName", RealmFieldType.STRING, false, false, false);
        builder.addProperty("lastName", RealmFieldType.STRING, false, false, false);
        builder.addProperty("syncStatus", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_FollowMeUser";
    }

    static FollowMeUser update(Realm object, FollowMeUser followMeUser, FollowMeUser followMeUserRealmProxyInterface, Map<RealmModel, RealmObjectProxy> map) {
        object = followMeUser;
        followMeUserRealmProxyInterface = followMeUserRealmProxyInterface;
        object.realmSet$userEmail(followMeUserRealmProxyInterface.realmGet$userEmail());
        object.realmSet$accessStatus(followMeUserRealmProxyInterface.realmGet$accessStatus());
        object.realmSet$notificationsEnabled(followMeUserRealmProxyInterface.realmGet$notificationsEnabled());
        object.realmSet$firstName(followMeUserRealmProxyInterface.realmGet$firstName());
        object.realmSet$lastName(followMeUserRealmProxyInterface.realmGet$lastName());
        object.realmSet$syncStatus(followMeUserRealmProxyInterface.realmGet$syncStatus());
        object.realmSet$userId(followMeUserRealmProxyInterface.realmGet$userId());
        return followMeUser;
    }

    public static FollowMeUserColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_FollowMeUser")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'FollowMeUser' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_FollowMeUser");
        long l = table.getColumnCount();
        if (l != 8L) {
            if (l < 8L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 8 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 8 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 8 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        FollowMeUserColumnInfo followMeUserColumnInfo = new FollowMeUserColumnInfo(sharedRealm, table);
        if (!table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'watcherEmail' in existing Realm file. @PrimaryKey was added.");
        }
        if (table.getPrimaryKey() != followMeUserColumnInfo.watcherEmailIndex) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field watcherEmail");
        }
        if (!hashMap.containsKey("watcherEmail")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'watcherEmail' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("watcherEmail") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'watcherEmail' in existing Realm file.");
        }
        if (!table.isColumnNullable(followMeUserColumnInfo.watcherEmailIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "@PrimaryKey field 'watcherEmail' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("watcherEmail"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'watcherEmail' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!hashMap.containsKey("userEmail")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userEmail' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userEmail") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'userEmail' in existing Realm file.");
        }
        if (!table.isColumnNullable(followMeUserColumnInfo.userEmailIndex)) {
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
        if (!table.isColumnNullable(followMeUserColumnInfo.accessStatusIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'accessStatus' is required. Either set @Required to field 'accessStatus' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("notificationsEnabled")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'notificationsEnabled' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("notificationsEnabled") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'notificationsEnabled' in existing Realm file.");
        }
        if (table.isColumnNullable(followMeUserColumnInfo.notificationsEnabledIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'notificationsEnabled' does support null values in the existing Realm file. Use corresponding boxed type for field 'notificationsEnabled' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("firstName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'firstName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("firstName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'firstName' in existing Realm file.");
        }
        if (!table.isColumnNullable(followMeUserColumnInfo.firstNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'firstName' is required. Either set @Required to field 'firstName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("lastName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'lastName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("lastName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'lastName' in existing Realm file.");
        }
        if (!table.isColumnNullable(followMeUserColumnInfo.lastNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'lastName' is required. Either set @Required to field 'lastName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("syncStatus")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'syncStatus' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("syncStatus") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'syncStatus' in existing Realm file.");
        }
        if (table.isColumnNullable(followMeUserColumnInfo.syncStatusIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'syncStatus' does support null values in the existing Realm file. Use corresponding boxed type for field 'syncStatus' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(followMeUserColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        return followMeUserColumnInfo;
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        this.columnInfo = (FollowMeUserColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<FollowMeUserRealmProxy>(this);
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
    public String realmGet$watcherEmail() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.watcherEmailIndex);
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
    public void realmSet$watcherEmail(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        throw new RealmException("Primary key field 'watcherEmail' cannot be changed after object was created.");
    }

    static final class FollowMeUserColumnInfo
    extends ColumnInfo {
        long accessStatusIndex;
        long firstNameIndex;
        long lastNameIndex;
        long notificationsEnabledIndex;
        long syncStatusIndex;
        long userEmailIndex;
        long userIdIndex;
        long watcherEmailIndex;

        FollowMeUserColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        FollowMeUserColumnInfo(SharedRealm sharedRealm, Table table) {
            super(8);
            this.watcherEmailIndex = this.addColumnDetails(table, "watcherEmail", RealmFieldType.STRING);
            this.userEmailIndex = this.addColumnDetails(table, "userEmail", RealmFieldType.STRING);
            this.accessStatusIndex = this.addColumnDetails(table, "accessStatus", RealmFieldType.STRING);
            this.notificationsEnabledIndex = this.addColumnDetails(table, "notificationsEnabled", RealmFieldType.BOOLEAN);
            this.firstNameIndex = this.addColumnDetails(table, "firstName", RealmFieldType.STRING);
            this.lastNameIndex = this.addColumnDetails(table, "lastName", RealmFieldType.STRING);
            this.syncStatusIndex = this.addColumnDetails(table, "syncStatus", RealmFieldType.INTEGER);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new FollowMeUserColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (FollowMeUserColumnInfo)columnInfo;
            columnInfo2 = (FollowMeUserColumnInfo)columnInfo2;
            ((FollowMeUserColumnInfo)columnInfo2).watcherEmailIndex = ((FollowMeUserColumnInfo)columnInfo).watcherEmailIndex;
            ((FollowMeUserColumnInfo)columnInfo2).userEmailIndex = ((FollowMeUserColumnInfo)columnInfo).userEmailIndex;
            ((FollowMeUserColumnInfo)columnInfo2).accessStatusIndex = ((FollowMeUserColumnInfo)columnInfo).accessStatusIndex;
            ((FollowMeUserColumnInfo)columnInfo2).notificationsEnabledIndex = ((FollowMeUserColumnInfo)columnInfo).notificationsEnabledIndex;
            ((FollowMeUserColumnInfo)columnInfo2).firstNameIndex = ((FollowMeUserColumnInfo)columnInfo).firstNameIndex;
            ((FollowMeUserColumnInfo)columnInfo2).lastNameIndex = ((FollowMeUserColumnInfo)columnInfo).lastNameIndex;
            ((FollowMeUserColumnInfo)columnInfo2).syncStatusIndex = ((FollowMeUserColumnInfo)columnInfo).syncStatusIndex;
            ((FollowMeUserColumnInfo)columnInfo2).userIdIndex = ((FollowMeUserColumnInfo)columnInfo).userIdIndex;
        }
    }

}

