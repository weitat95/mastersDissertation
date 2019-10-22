/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.common.local.model.User;
import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmSchema;
import io.realm.UserRealmProxyInterface;
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

public class UserRealmProxy
extends User
implements UserRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private UserColumnInfo columnInfo;
    private ProxyState<User> proxyState;

    static {
        expectedObjectSchemaInfo = UserRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("userId");
        arrayList.add("email");
        arrayList.add("password");
        arrayList.add("token");
        arrayList.add("tokenExpired");
        arrayList.add("trackingId");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    UserRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static User copy(Realm object, User userRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(userRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (User)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(User.class, ((UserRealmProxyInterface)userRealmProxyInterface).realmGet$email(), false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)userRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        userRealmProxyInterface = userRealmProxyInterface;
        object2 = (UserRealmProxyInterface)object;
        object2.realmSet$userId(userRealmProxyInterface.realmGet$userId());
        object2.realmSet$password(userRealmProxyInterface.realmGet$password());
        object2.realmSet$token(userRealmProxyInterface.realmGet$token());
        object2.realmSet$tokenExpired(userRealmProxyInterface.realmGet$tokenExpired());
        object2.realmSet$trackingId(userRealmProxyInterface.realmGet$trackingId());
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static User copyOrUpdate(Realm var0, User var1_4, boolean var2_5, Map<RealmModel, RealmObjectProxy> var3_6) {
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
                    return (User)var8_8;
                }
                var9_9 = null;
                var5_11 = var4_10 = var2_5;
                var8_8 = var9_9;
                if (!var4_10) break block7;
                var8_8 = var0.getTable(User.class);
                var6_12 = var8_8.getPrimaryKey();
                var11_13 = ((UserRealmProxyInterface)var1_4).realmGet$email();
                var6_12 = var11_13 == null ? var8_8.findFirstNull(var6_12) : var8_8.findFirstString(var6_12, var11_13);
                if (var6_12 == -1L) break block8;
                try {
                    var10_7.set(var0, var8_8.getUncheckedRow(var6_12), var0.schema.getColumnInfo(User.class), false, Collections.<String>emptyList());
                    var8_8 = new UserRealmProxy();
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
        if (var5_11 == false) return UserRealmProxy.copy(var0, var1_4, var2_5, var3_6);
        return UserRealmProxy.update(var0, (User)var8_8, var1_4, var3_6);
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
    public static User createDetachedCopy(User userRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || userRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(userRealmProxyInterface);
        if (object2 == null) {
            object2 = new User();
            object.put(userRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (User)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (User)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (UserRealmProxyInterface)object;
        object2.realmSet$userId(userRealmProxyInterface.realmGet$userId());
        object2.realmSet$email(userRealmProxyInterface.realmGet$email());
        object2.realmSet$password(userRealmProxyInterface.realmGet$password());
        object2.realmSet$token(userRealmProxyInterface.realmGet$token());
        object2.realmSet$tokenExpired(userRealmProxyInterface.realmGet$tokenExpired());
        object2.realmSet$trackingId(userRealmProxyInterface.realmGet$trackingId());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("User");
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("email", RealmFieldType.STRING, true, true, false);
        builder.addProperty("password", RealmFieldType.STRING, false, false, false);
        builder.addProperty("token", RealmFieldType.STRING, false, false, false);
        builder.addProperty("tokenExpired", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("trackingId", RealmFieldType.STRING, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_User";
    }

    static User update(Realm object, User user, User userRealmProxyInterface, Map<RealmModel, RealmObjectProxy> map) {
        object = user;
        userRealmProxyInterface = userRealmProxyInterface;
        object.realmSet$userId(userRealmProxyInterface.realmGet$userId());
        object.realmSet$password(userRealmProxyInterface.realmGet$password());
        object.realmSet$token(userRealmProxyInterface.realmGet$token());
        object.realmSet$tokenExpired(userRealmProxyInterface.realmGet$tokenExpired());
        object.realmSet$trackingId(userRealmProxyInterface.realmGet$trackingId());
        return user;
    }

    public static UserColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_User")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'User' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_User");
        long l = table.getColumnCount();
        if (l != 6L) {
            if (l < 6L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 6 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 6 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 6 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        UserColumnInfo userColumnInfo = new UserColumnInfo(sharedRealm, table);
        if (!table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'email' in existing Realm file. @PrimaryKey was added.");
        }
        if (table.getPrimaryKey() != userColumnInfo.emailIndex) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field email");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(userColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("email")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'email' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("email") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'email' in existing Realm file.");
        }
        if (!table.isColumnNullable(userColumnInfo.emailIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "@PrimaryKey field 'email' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("email"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'email' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!hashMap.containsKey("password")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'password' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("password") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'password' in existing Realm file.");
        }
        if (!table.isColumnNullable(userColumnInfo.passwordIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'password' is required. Either set @Required to field 'password' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("token")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'token' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("token") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'token' in existing Realm file.");
        }
        if (!table.isColumnNullable(userColumnInfo.tokenIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'token' is required. Either set @Required to field 'token' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("tokenExpired")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'tokenExpired' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("tokenExpired") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Long' for field 'tokenExpired' in existing Realm file.");
        }
        if (!table.isColumnNullable(userColumnInfo.tokenExpiredIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'tokenExpired' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'tokenExpired' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("trackingId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'trackingId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("trackingId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'trackingId' in existing Realm file.");
        }
        if (!table.isColumnNullable(userColumnInfo.trackingIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'trackingId' is required. Either set @Required to field 'trackingId' or migrate using RealmObjectSchema.setNullable().");
        }
        return userColumnInfo;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7: {
            block6: {
                if (this == object) break block6;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (UserRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((UserRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((UserRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((UserRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        String string2 = this.proxyState.getRealm$realm().getPath();
        String string3 = this.proxyState.getRow$realm().getTable().getName();
        long l = this.proxyState.getRow$realm().getIndex();
        int n2 = string2 != null ? string2.hashCode() : 0;
        if (string3 != null) {
            n = string3.hashCode();
        }
        return ((n2 + 527) * 31 + n) * 31 + (int)(l >>> 32 ^ l);
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        this.columnInfo = (UserColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<UserRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public String realmGet$email() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.emailIndex);
    }

    @Override
    public String realmGet$password() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.passwordIndex);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public String realmGet$token() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.tokenIndex);
    }

    @Override
    public Long realmGet$tokenExpired() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.tokenExpiredIndex)) {
            return null;
        }
        return this.proxyState.getRow$realm().getLong(this.columnInfo.tokenExpiredIndex);
    }

    @Override
    public String realmGet$trackingId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.trackingIdIndex);
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public void realmSet$email(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        throw new RealmException("Primary key field 'email' cannot be changed after object was created.");
    }

    @Override
    public void realmSet$password(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.passwordIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.passwordIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.passwordIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.passwordIndex, string2);
    }

    @Override
    public void realmSet$token(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.tokenIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.tokenIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.tokenIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.tokenIndex, string2);
    }

    @Override
    public void realmSet$tokenExpired(Long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (l == null) {
                row.getTable().setNull(this.columnInfo.tokenExpiredIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.tokenExpiredIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (l == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.tokenExpiredIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.tokenExpiredIndex, l);
    }

    @Override
    public void realmSet$trackingId(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.trackingIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.trackingIdIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.trackingIdIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.trackingIdIndex, string2);
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

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("User = proxy[");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        Object object = this.realmGet$email() != null ? this.realmGet$email() : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{password:");
        object = this.realmGet$password() != null ? this.realmGet$password() : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{token:");
        object = this.realmGet$token() != null ? this.realmGet$token() : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tokenExpired:");
        object = this.realmGet$tokenExpired() != null ? this.realmGet$tokenExpired() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{trackingId:");
        object = this.realmGet$trackingId() != null ? this.realmGet$trackingId() : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class UserColumnInfo
    extends ColumnInfo {
        long emailIndex;
        long passwordIndex;
        long tokenExpiredIndex;
        long tokenIndex;
        long trackingIdIndex;
        long userIdIndex;

        UserColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        UserColumnInfo(SharedRealm sharedRealm, Table table) {
            super(6);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.emailIndex = this.addColumnDetails(table, "email", RealmFieldType.STRING);
            this.passwordIndex = this.addColumnDetails(table, "password", RealmFieldType.STRING);
            this.tokenIndex = this.addColumnDetails(table, "token", RealmFieldType.STRING);
            this.tokenExpiredIndex = this.addColumnDetails(table, "tokenExpired", RealmFieldType.INTEGER);
            this.trackingIdIndex = this.addColumnDetails(table, "trackingId", RealmFieldType.STRING);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new UserColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (UserColumnInfo)columnInfo;
            columnInfo2 = (UserColumnInfo)columnInfo2;
            ((UserColumnInfo)columnInfo2).userIdIndex = ((UserColumnInfo)columnInfo).userIdIndex;
            ((UserColumnInfo)columnInfo2).emailIndex = ((UserColumnInfo)columnInfo).emailIndex;
            ((UserColumnInfo)columnInfo2).passwordIndex = ((UserColumnInfo)columnInfo).passwordIndex;
            ((UserColumnInfo)columnInfo2).tokenIndex = ((UserColumnInfo)columnInfo).tokenIndex;
            ((UserColumnInfo)columnInfo2).tokenExpiredIndex = ((UserColumnInfo)columnInfo).tokenExpiredIndex;
            ((UserColumnInfo)columnInfo2).trackingIdIndex = ((UserColumnInfo)columnInfo).trackingIdIndex;
        }
    }

}

