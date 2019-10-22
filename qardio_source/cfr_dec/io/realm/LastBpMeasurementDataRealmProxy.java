/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;
import io.realm.BaseRealm;
import io.realm.LastBpMeasurementDataRealmProxyInterface;
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

public class LastBpMeasurementDataRealmProxy
extends LastBpMeasurementData
implements LastBpMeasurementDataRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private LastBpMeasurementDataColumnInfo columnInfo;
    private ProxyState<LastBpMeasurementData> proxyState;

    static {
        expectedObjectSchemaInfo = LastBpMeasurementDataRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("data1");
        arrayList.add("data2");
        arrayList.add("data3");
        arrayList.add("userId");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    LastBpMeasurementDataRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static LastBpMeasurementData copy(Realm object, LastBpMeasurementData lastBpMeasurementDataRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(lastBpMeasurementDataRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (LastBpMeasurementData)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(LastBpMeasurementData.class, false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)lastBpMeasurementDataRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        lastBpMeasurementDataRealmProxyInterface = lastBpMeasurementDataRealmProxyInterface;
        object2 = (LastBpMeasurementDataRealmProxyInterface)object;
        object2.realmSet$data1(lastBpMeasurementDataRealmProxyInterface.realmGet$data1());
        object2.realmSet$data2(lastBpMeasurementDataRealmProxyInterface.realmGet$data2());
        object2.realmSet$data3(lastBpMeasurementDataRealmProxyInterface.realmGet$data3());
        object2.realmSet$userId(lastBpMeasurementDataRealmProxyInterface.realmGet$userId());
        return object;
    }

    public static LastBpMeasurementData copyOrUpdate(Realm realm, LastBpMeasurementData lastBpMeasurementData, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (lastBpMeasurementData instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)lastBpMeasurementData)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)lastBpMeasurementData).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (lastBpMeasurementData instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)lastBpMeasurementData)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)lastBpMeasurementData)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return lastBpMeasurementData;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(lastBpMeasurementData);
        if (object != null) {
            return (LastBpMeasurementData)object;
        }
        return LastBpMeasurementDataRealmProxy.copy(realm, lastBpMeasurementData, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static LastBpMeasurementData createDetachedCopy(LastBpMeasurementData lastBpMeasurementDataRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || lastBpMeasurementDataRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(lastBpMeasurementDataRealmProxyInterface);
        if (object2 == null) {
            object2 = new LastBpMeasurementData();
            object.put(lastBpMeasurementDataRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (LastBpMeasurementData)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (LastBpMeasurementData)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (LastBpMeasurementDataRealmProxyInterface)object;
        object2.realmSet$data1(lastBpMeasurementDataRealmProxyInterface.realmGet$data1());
        object2.realmSet$data2(lastBpMeasurementDataRealmProxyInterface.realmGet$data2());
        object2.realmSet$data3(lastBpMeasurementDataRealmProxyInterface.realmGet$data3());
        object2.realmSet$userId(lastBpMeasurementDataRealmProxyInterface.realmGet$userId());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("LastBpMeasurementData");
        builder.addProperty("data1", RealmFieldType.STRING, false, false, false);
        builder.addProperty("data2", RealmFieldType.STRING, false, false, false);
        builder.addProperty("data3", RealmFieldType.STRING, false, false, false);
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_LastBpMeasurementData";
    }

    public static LastBpMeasurementDataColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_LastBpMeasurementData")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'LastBpMeasurementData' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_LastBpMeasurementData");
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
        LastBpMeasurementDataColumnInfo lastBpMeasurementDataColumnInfo = new LastBpMeasurementDataColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("data1")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'data1' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("data1") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'data1' in existing Realm file.");
        }
        if (!table.isColumnNullable(lastBpMeasurementDataColumnInfo.data1Index)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'data1' is required. Either set @Required to field 'data1' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("data2")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'data2' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("data2") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'data2' in existing Realm file.");
        }
        if (!table.isColumnNullable(lastBpMeasurementDataColumnInfo.data2Index)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'data2' is required. Either set @Required to field 'data2' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("data3")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'data3' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("data3") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'data3' in existing Realm file.");
        }
        if (!table.isColumnNullable(lastBpMeasurementDataColumnInfo.data3Index)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'data3' is required. Either set @Required to field 'data3' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(lastBpMeasurementDataColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        return lastBpMeasurementDataColumnInfo;
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
                object = (LastBpMeasurementDataRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((LastBpMeasurementDataRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((LastBpMeasurementDataRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((LastBpMeasurementDataRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (LastBpMeasurementDataColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<LastBpMeasurementDataRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public String realmGet$data1() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.data1Index);
    }

    @Override
    public String realmGet$data2() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.data2Index);
    }

    @Override
    public String realmGet$data3() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.data3Index);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public void realmSet$data1(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.data1Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.data1Index, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.data1Index);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.data1Index, string2);
    }

    @Override
    public void realmSet$data2(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.data2Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.data2Index, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.data2Index);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.data2Index, string2);
    }

    @Override
    public void realmSet$data3(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.data3Index, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.data3Index, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.data3Index);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.data3Index, string2);
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
        StringBuilder stringBuilder = new StringBuilder("LastBpMeasurementData = proxy[");
        stringBuilder.append("{data1:");
        String string2 = this.realmGet$data1() != null ? this.realmGet$data1() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{data2:");
        string2 = this.realmGet$data2() != null ? this.realmGet$data2() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{data3:");
        string2 = this.realmGet$data3() != null ? this.realmGet$data3() : "null";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class LastBpMeasurementDataColumnInfo
    extends ColumnInfo {
        long data1Index;
        long data2Index;
        long data3Index;
        long userIdIndex;

        LastBpMeasurementDataColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        LastBpMeasurementDataColumnInfo(SharedRealm sharedRealm, Table table) {
            super(4);
            this.data1Index = this.addColumnDetails(table, "data1", RealmFieldType.STRING);
            this.data2Index = this.addColumnDetails(table, "data2", RealmFieldType.STRING);
            this.data3Index = this.addColumnDetails(table, "data3", RealmFieldType.STRING);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new LastBpMeasurementDataColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (LastBpMeasurementDataColumnInfo)columnInfo;
            columnInfo2 = (LastBpMeasurementDataColumnInfo)columnInfo2;
            ((LastBpMeasurementDataColumnInfo)columnInfo2).data1Index = ((LastBpMeasurementDataColumnInfo)columnInfo).data1Index;
            ((LastBpMeasurementDataColumnInfo)columnInfo2).data2Index = ((LastBpMeasurementDataColumnInfo)columnInfo).data2Index;
            ((LastBpMeasurementDataColumnInfo)columnInfo2).data3Index = ((LastBpMeasurementDataColumnInfo)columnInfo).data3Index;
            ((LastBpMeasurementDataColumnInfo)columnInfo2).userIdIndex = ((LastBpMeasurementDataColumnInfo)columnInfo).userIdIndex;
        }
    }

}

