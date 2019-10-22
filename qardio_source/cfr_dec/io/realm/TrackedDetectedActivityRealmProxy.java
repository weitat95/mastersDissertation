/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedDetectedActivity;
import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.TrackedDetectedActivityRealmProxyInterface;
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

public class TrackedDetectedActivityRealmProxy
extends TrackedDetectedActivity
implements TrackedDetectedActivityRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private TrackedDetectedActivityColumnInfo columnInfo;
    private ProxyState<TrackedDetectedActivity> proxyState;

    static {
        expectedObjectSchemaInfo = TrackedDetectedActivityRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("userId");
        arrayList.add("confidence");
        arrayList.add("type");
        arrayList.add("startTimestamp");
        arrayList.add("endTimestamp");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    TrackedDetectedActivityRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static TrackedDetectedActivity copy(Realm object, TrackedDetectedActivity trackedDetectedActivityRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(trackedDetectedActivityRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (TrackedDetectedActivity)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(TrackedDetectedActivity.class, false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)trackedDetectedActivityRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        trackedDetectedActivityRealmProxyInterface = trackedDetectedActivityRealmProxyInterface;
        object2 = (TrackedDetectedActivityRealmProxyInterface)object;
        object2.realmSet$userId(trackedDetectedActivityRealmProxyInterface.realmGet$userId());
        object2.realmSet$confidence(trackedDetectedActivityRealmProxyInterface.realmGet$confidence());
        object2.realmSet$type(trackedDetectedActivityRealmProxyInterface.realmGet$type());
        object2.realmSet$startTimestamp(trackedDetectedActivityRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$endTimestamp(trackedDetectedActivityRealmProxyInterface.realmGet$endTimestamp());
        return object;
    }

    public static TrackedDetectedActivity copyOrUpdate(Realm realm, TrackedDetectedActivity trackedDetectedActivity, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (trackedDetectedActivity instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)trackedDetectedActivity)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)trackedDetectedActivity).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (trackedDetectedActivity instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)trackedDetectedActivity)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)trackedDetectedActivity)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return trackedDetectedActivity;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(trackedDetectedActivity);
        if (object != null) {
            return (TrackedDetectedActivity)object;
        }
        return TrackedDetectedActivityRealmProxy.copy(realm, trackedDetectedActivity, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static TrackedDetectedActivity createDetachedCopy(TrackedDetectedActivity trackedDetectedActivityRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || trackedDetectedActivityRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(trackedDetectedActivityRealmProxyInterface);
        if (object2 == null) {
            object2 = new TrackedDetectedActivity();
            object.put(trackedDetectedActivityRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (TrackedDetectedActivity)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (TrackedDetectedActivity)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (TrackedDetectedActivityRealmProxyInterface)object;
        object2.realmSet$userId(trackedDetectedActivityRealmProxyInterface.realmGet$userId());
        object2.realmSet$confidence(trackedDetectedActivityRealmProxyInterface.realmGet$confidence());
        object2.realmSet$type(trackedDetectedActivityRealmProxyInterface.realmGet$type());
        object2.realmSet$startTimestamp(trackedDetectedActivityRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$endTimestamp(trackedDetectedActivityRealmProxyInterface.realmGet$endTimestamp());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("TrackedDetectedActivity");
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("confidence", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("type", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("startTimestamp", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("endTimestamp", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_TrackedDetectedActivity";
    }

    public static TrackedDetectedActivityColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_TrackedDetectedActivity")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'TrackedDetectedActivity' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_TrackedDetectedActivity");
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
        TrackedDetectedActivityColumnInfo trackedDetectedActivityColumnInfo = new TrackedDetectedActivityColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(trackedDetectedActivityColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("confidence")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'confidence' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("confidence") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'confidence' in existing Realm file.");
        }
        if (table.isColumnNullable(trackedDetectedActivityColumnInfo.confidenceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'confidence' does support null values in the existing Realm file. Use corresponding boxed type for field 'confidence' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("type")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'type' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("type") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'type' in existing Realm file.");
        }
        if (table.isColumnNullable(trackedDetectedActivityColumnInfo.typeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'type' does support null values in the existing Realm file. Use corresponding boxed type for field 'type' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("startTimestamp")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'startTimestamp' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("startTimestamp") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'startTimestamp' in existing Realm file.");
        }
        if (table.isColumnNullable(trackedDetectedActivityColumnInfo.startTimestampIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'startTimestamp' does support null values in the existing Realm file. Use corresponding boxed type for field 'startTimestamp' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("endTimestamp")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'endTimestamp' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("endTimestamp") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'endTimestamp' in existing Realm file.");
        }
        if (table.isColumnNullable(trackedDetectedActivityColumnInfo.endTimestampIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'endTimestamp' does support null values in the existing Realm file. Use corresponding boxed type for field 'endTimestamp' or migrate using RealmObjectSchema.setNullable().");
        }
        return trackedDetectedActivityColumnInfo;
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
        this.columnInfo = (TrackedDetectedActivityColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<TrackedDetectedActivityRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public int realmGet$confidence() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.confidenceIndex);
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
    public int realmGet$type() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.typeIndex);
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public void realmSet$confidence(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.confidenceIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.confidenceIndex, n);
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
    public void realmSet$type(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.typeIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.typeIndex, n);
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
        StringBuilder stringBuilder = new StringBuilder("TrackedDetectedActivity = proxy[");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{confidence:");
        stringBuilder.append(this.realmGet$confidence());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(this.realmGet$type());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startTimestamp:");
        stringBuilder.append(this.realmGet$startTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{endTimestamp:");
        stringBuilder.append(this.realmGet$endTimestamp());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class TrackedDetectedActivityColumnInfo
    extends ColumnInfo {
        long confidenceIndex;
        long endTimestampIndex;
        long startTimestampIndex;
        long typeIndex;
        long userIdIndex;

        TrackedDetectedActivityColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        TrackedDetectedActivityColumnInfo(SharedRealm sharedRealm, Table table) {
            super(5);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.confidenceIndex = this.addColumnDetails(table, "confidence", RealmFieldType.INTEGER);
            this.typeIndex = this.addColumnDetails(table, "type", RealmFieldType.INTEGER);
            this.startTimestampIndex = this.addColumnDetails(table, "startTimestamp", RealmFieldType.INTEGER);
            this.endTimestampIndex = this.addColumnDetails(table, "endTimestamp", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new TrackedDetectedActivityColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (TrackedDetectedActivityColumnInfo)columnInfo;
            columnInfo2 = (TrackedDetectedActivityColumnInfo)columnInfo2;
            ((TrackedDetectedActivityColumnInfo)columnInfo2).userIdIndex = ((TrackedDetectedActivityColumnInfo)columnInfo).userIdIndex;
            ((TrackedDetectedActivityColumnInfo)columnInfo2).confidenceIndex = ((TrackedDetectedActivityColumnInfo)columnInfo).confidenceIndex;
            ((TrackedDetectedActivityColumnInfo)columnInfo2).typeIndex = ((TrackedDetectedActivityColumnInfo)columnInfo).typeIndex;
            ((TrackedDetectedActivityColumnInfo)columnInfo2).startTimestampIndex = ((TrackedDetectedActivityColumnInfo)columnInfo).startTimestampIndex;
            ((TrackedDetectedActivityColumnInfo)columnInfo2).endTimestampIndex = ((TrackedDetectedActivityColumnInfo)columnInfo).endTimestampIndex;
        }
    }

}

