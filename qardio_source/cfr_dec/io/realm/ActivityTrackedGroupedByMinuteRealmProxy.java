/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByMinute;
import io.realm.ActivityTrackedGroupedByMinuteRealmProxyInterface;
import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTrackedGroupedByMinuteRealmProxy
extends ActivityTrackedGroupedByMinute
implements ActivityTrackedGroupedByMinuteRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private ActivityTrackedGroupedByMinuteColumnInfo columnInfo;
    private ProxyState<ActivityTrackedGroupedByMinute> proxyState;

    static {
        expectedObjectSchemaInfo = ActivityTrackedGroupedByMinuteRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("startTimestamp");
        arrayList.add("duration");
        arrayList.add("steps");
        arrayList.add("activityType");
        arrayList.add("userId");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    ActivityTrackedGroupedByMinuteRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static ActivityTrackedGroupedByMinute copy(Realm object, ActivityTrackedGroupedByMinute activityTrackedGroupedByMinuteRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(activityTrackedGroupedByMinuteRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (ActivityTrackedGroupedByMinute)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(ActivityTrackedGroupedByMinute.class, false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)activityTrackedGroupedByMinuteRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        activityTrackedGroupedByMinuteRealmProxyInterface = activityTrackedGroupedByMinuteRealmProxyInterface;
        object2 = (ActivityTrackedGroupedByMinuteRealmProxyInterface)object;
        object2.realmSet$startTimestamp(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$duration(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$duration());
        object2.realmSet$steps(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$steps());
        object2.realmSet$activityType(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$activityType());
        object2.realmSet$userId(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$userId());
        return object;
    }

    public static ActivityTrackedGroupedByMinute copyOrUpdate(Realm realm, ActivityTrackedGroupedByMinute activityTrackedGroupedByMinute, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (activityTrackedGroupedByMinute instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)activityTrackedGroupedByMinute)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)activityTrackedGroupedByMinute).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (activityTrackedGroupedByMinute instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)activityTrackedGroupedByMinute)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)activityTrackedGroupedByMinute)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return activityTrackedGroupedByMinute;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(activityTrackedGroupedByMinute);
        if (object != null) {
            return (ActivityTrackedGroupedByMinute)object;
        }
        return ActivityTrackedGroupedByMinuteRealmProxy.copy(realm, activityTrackedGroupedByMinute, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ActivityTrackedGroupedByMinute createDetachedCopy(ActivityTrackedGroupedByMinute activityTrackedGroupedByMinuteRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || activityTrackedGroupedByMinuteRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(activityTrackedGroupedByMinuteRealmProxyInterface);
        if (object2 == null) {
            object2 = new ActivityTrackedGroupedByMinute();
            object.put(activityTrackedGroupedByMinuteRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (ActivityTrackedGroupedByMinute)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (ActivityTrackedGroupedByMinute)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (ActivityTrackedGroupedByMinuteRealmProxyInterface)object;
        object2.realmSet$startTimestamp(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$duration(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$duration());
        object2.realmSet$steps(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$steps());
        object2.realmSet$activityType(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$activityType());
        object2.realmSet$userId(activityTrackedGroupedByMinuteRealmProxyInterface.realmGet$userId());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ActivityTrackedGroupedByMinute");
        builder.addProperty("startTimestamp", RealmFieldType.INTEGER, false, true, true);
        builder.addProperty("duration", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("steps", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("activityType", RealmFieldType.STRING, false, false, false);
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_ActivityTrackedGroupedByMinute";
    }

    public static ActivityTrackedGroupedByMinuteColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_ActivityTrackedGroupedByMinute")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'ActivityTrackedGroupedByMinute' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_ActivityTrackedGroupedByMinute");
        long l = table.getColumnCount();
        if (l != 5L) {
            if (l < 5L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 5 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 5 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 5 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        ActivityTrackedGroupedByMinuteColumnInfo activityTrackedGroupedByMinuteColumnInfo = new ActivityTrackedGroupedByMinuteColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("startTimestamp")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'startTimestamp' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("startTimestamp") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'startTimestamp' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByMinuteColumnInfo.startTimestampIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'startTimestamp' does support null values in the existing Realm file. Use corresponding boxed type for field 'startTimestamp' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("startTimestamp"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'startTimestamp' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!hashMap.containsKey("duration")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'duration' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("duration") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'duration' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByMinuteColumnInfo.durationIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'duration' does support null values in the existing Realm file. Use corresponding boxed type for field 'duration' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("steps")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'steps' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("steps") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'steps' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByMinuteColumnInfo.stepsIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'steps' does support null values in the existing Realm file. Use corresponding boxed type for field 'steps' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("activityType")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'activityType' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("activityType") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'activityType' in existing Realm file.");
        }
        if (!table.isColumnNullable(activityTrackedGroupedByMinuteColumnInfo.activityTypeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'activityType' is required. Either set @Required to field 'activityType' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByMinuteColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        return activityTrackedGroupedByMinuteColumnInfo;
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
                object = (ActivityTrackedGroupedByMinuteRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((ActivityTrackedGroupedByMinuteRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((ActivityTrackedGroupedByMinuteRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((ActivityTrackedGroupedByMinuteRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (ActivityTrackedGroupedByMinuteColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<ActivityTrackedGroupedByMinuteRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public String realmGet$activityType() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.activityTypeIndex);
    }

    @Override
    public long realmGet$duration() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.durationIndex);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public long realmGet$startTimestamp() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.startTimestampIndex);
    }

    @Override
    public long realmGet$steps() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.stepsIndex);
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public void realmSet$activityType(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.activityTypeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.activityTypeIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.activityTypeIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.activityTypeIndex, string2);
    }

    @Override
    public void realmSet$duration(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.durationIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.durationIndex, l);
    }

    @Override
    public void realmSet$startTimestamp(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.startTimestampIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.startTimestampIndex, l);
    }

    @Override
    public void realmSet$steps(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.stepsIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.stepsIndex, l);
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
        StringBuilder stringBuilder = new StringBuilder("ActivityTrackedGroupedByMinute = proxy[");
        stringBuilder.append("{startTimestamp:");
        stringBuilder.append(this.realmGet$startTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{duration:");
        stringBuilder.append(this.realmGet$duration());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{steps:");
        stringBuilder.append(this.realmGet$steps());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{activityType:");
        String string2 = this.realmGet$activityType() != null ? this.realmGet$activityType() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class ActivityTrackedGroupedByMinuteColumnInfo
    extends ColumnInfo {
        long activityTypeIndex;
        long durationIndex;
        long startTimestampIndex;
        long stepsIndex;
        long userIdIndex;

        ActivityTrackedGroupedByMinuteColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        ActivityTrackedGroupedByMinuteColumnInfo(SharedRealm sharedRealm, Table table) {
            super(5);
            this.startTimestampIndex = this.addColumnDetails(table, "startTimestamp", RealmFieldType.INTEGER);
            this.durationIndex = this.addColumnDetails(table, "duration", RealmFieldType.INTEGER);
            this.stepsIndex = this.addColumnDetails(table, "steps", RealmFieldType.INTEGER);
            this.activityTypeIndex = this.addColumnDetails(table, "activityType", RealmFieldType.STRING);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new ActivityTrackedGroupedByMinuteColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (ActivityTrackedGroupedByMinuteColumnInfo)columnInfo;
            columnInfo2 = (ActivityTrackedGroupedByMinuteColumnInfo)columnInfo2;
            ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo2).startTimestampIndex = ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo).startTimestampIndex;
            ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo2).durationIndex = ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo).durationIndex;
            ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo2).stepsIndex = ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo).stepsIndex;
            ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo2).activityTypeIndex = ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo).activityTypeIndex;
            ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo2).userIdIndex = ((ActivityTrackedGroupedByMinuteColumnInfo)columnInfo).userIdIndex;
        }
    }

}

