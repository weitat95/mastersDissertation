/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByHour;
import io.realm.ActivityTrackedGroupedByHourRealmProxyInterface;
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

public class ActivityTrackedGroupedByHourRealmProxy
extends ActivityTrackedGroupedByHour
implements ActivityTrackedGroupedByHourRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private ActivityTrackedGroupedByHourColumnInfo columnInfo;
    private ProxyState<ActivityTrackedGroupedByHour> proxyState;

    static {
        expectedObjectSchemaInfo = ActivityTrackedGroupedByHourRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("startTimestamp");
        arrayList.add("endTimestamp");
        arrayList.add("steps");
        arrayList.add("userId");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    ActivityTrackedGroupedByHourRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static ActivityTrackedGroupedByHour copy(Realm object, ActivityTrackedGroupedByHour activityTrackedGroupedByHourRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(activityTrackedGroupedByHourRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (ActivityTrackedGroupedByHour)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(ActivityTrackedGroupedByHour.class, false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)activityTrackedGroupedByHourRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        activityTrackedGroupedByHourRealmProxyInterface = activityTrackedGroupedByHourRealmProxyInterface;
        object2 = (ActivityTrackedGroupedByHourRealmProxyInterface)object;
        object2.realmSet$startTimestamp(activityTrackedGroupedByHourRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$endTimestamp(activityTrackedGroupedByHourRealmProxyInterface.realmGet$endTimestamp());
        object2.realmSet$steps(activityTrackedGroupedByHourRealmProxyInterface.realmGet$steps());
        object2.realmSet$userId(activityTrackedGroupedByHourRealmProxyInterface.realmGet$userId());
        return object;
    }

    public static ActivityTrackedGroupedByHour copyOrUpdate(Realm realm, ActivityTrackedGroupedByHour activityTrackedGroupedByHour, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (activityTrackedGroupedByHour instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)activityTrackedGroupedByHour)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)activityTrackedGroupedByHour).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (activityTrackedGroupedByHour instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)activityTrackedGroupedByHour)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)activityTrackedGroupedByHour)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return activityTrackedGroupedByHour;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(activityTrackedGroupedByHour);
        if (object != null) {
            return (ActivityTrackedGroupedByHour)object;
        }
        return ActivityTrackedGroupedByHourRealmProxy.copy(realm, activityTrackedGroupedByHour, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ActivityTrackedGroupedByHour createDetachedCopy(ActivityTrackedGroupedByHour activityTrackedGroupedByHourRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || activityTrackedGroupedByHourRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(activityTrackedGroupedByHourRealmProxyInterface);
        if (object2 == null) {
            object2 = new ActivityTrackedGroupedByHour();
            object.put(activityTrackedGroupedByHourRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (ActivityTrackedGroupedByHour)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (ActivityTrackedGroupedByHour)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (ActivityTrackedGroupedByHourRealmProxyInterface)object;
        object2.realmSet$startTimestamp(activityTrackedGroupedByHourRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$endTimestamp(activityTrackedGroupedByHourRealmProxyInterface.realmGet$endTimestamp());
        object2.realmSet$steps(activityTrackedGroupedByHourRealmProxyInterface.realmGet$steps());
        object2.realmSet$userId(activityTrackedGroupedByHourRealmProxyInterface.realmGet$userId());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ActivityTrackedGroupedByHour");
        builder.addProperty("startTimestamp", RealmFieldType.INTEGER, false, true, true);
        builder.addProperty("endTimestamp", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("steps", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_ActivityTrackedGroupedByHour";
    }

    public static ActivityTrackedGroupedByHourColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_ActivityTrackedGroupedByHour")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'ActivityTrackedGroupedByHour' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_ActivityTrackedGroupedByHour");
        long l = table.getColumnCount();
        if (l != 4L) {
            if (l < 4L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 4 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 4 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 4 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        ActivityTrackedGroupedByHourColumnInfo activityTrackedGroupedByHourColumnInfo = new ActivityTrackedGroupedByHourColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("startTimestamp")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'startTimestamp' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("startTimestamp") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'startTimestamp' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByHourColumnInfo.startTimestampIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'startTimestamp' does support null values in the existing Realm file. Use corresponding boxed type for field 'startTimestamp' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("startTimestamp"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'startTimestamp' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!hashMap.containsKey("endTimestamp")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'endTimestamp' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("endTimestamp") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'endTimestamp' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByHourColumnInfo.endTimestampIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'endTimestamp' does support null values in the existing Realm file. Use corresponding boxed type for field 'endTimestamp' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("steps")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'steps' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("steps") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'steps' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByHourColumnInfo.stepsIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'steps' does support null values in the existing Realm file. Use corresponding boxed type for field 'steps' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByHourColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        return activityTrackedGroupedByHourColumnInfo;
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
                object = (ActivityTrackedGroupedByHourRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((ActivityTrackedGroupedByHourRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((ActivityTrackedGroupedByHourRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((ActivityTrackedGroupedByHourRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (ActivityTrackedGroupedByHourColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<ActivityTrackedGroupedByHourRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public long realmGet$endTimestamp() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.endTimestampIndex);
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
    public void realmSet$endTimestamp(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.endTimestampIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.endTimestampIndex, l);
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

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("ActivityTrackedGroupedByHour = proxy[");
        stringBuilder.append("{startTimestamp:");
        stringBuilder.append(this.realmGet$startTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{endTimestamp:");
        stringBuilder.append(this.realmGet$endTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{steps:");
        stringBuilder.append(this.realmGet$steps());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class ActivityTrackedGroupedByHourColumnInfo
    extends ColumnInfo {
        long endTimestampIndex;
        long startTimestampIndex;
        long stepsIndex;
        long userIdIndex;

        ActivityTrackedGroupedByHourColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        ActivityTrackedGroupedByHourColumnInfo(SharedRealm sharedRealm, Table table) {
            super(4);
            this.startTimestampIndex = this.addColumnDetails(table, "startTimestamp", RealmFieldType.INTEGER);
            this.endTimestampIndex = this.addColumnDetails(table, "endTimestamp", RealmFieldType.INTEGER);
            this.stepsIndex = this.addColumnDetails(table, "steps", RealmFieldType.INTEGER);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new ActivityTrackedGroupedByHourColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (ActivityTrackedGroupedByHourColumnInfo)columnInfo;
            columnInfo2 = (ActivityTrackedGroupedByHourColumnInfo)columnInfo2;
            ((ActivityTrackedGroupedByHourColumnInfo)columnInfo2).startTimestampIndex = ((ActivityTrackedGroupedByHourColumnInfo)columnInfo).startTimestampIndex;
            ((ActivityTrackedGroupedByHourColumnInfo)columnInfo2).endTimestampIndex = ((ActivityTrackedGroupedByHourColumnInfo)columnInfo).endTimestampIndex;
            ((ActivityTrackedGroupedByHourColumnInfo)columnInfo2).stepsIndex = ((ActivityTrackedGroupedByHourColumnInfo)columnInfo).stepsIndex;
            ((ActivityTrackedGroupedByHourColumnInfo)columnInfo2).userIdIndex = ((ActivityTrackedGroupedByHourColumnInfo)columnInfo).userIdIndex;
        }
    }

}

