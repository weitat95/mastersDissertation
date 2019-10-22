/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;
import io.realm.BaseRealm;
import io.realm.BpLastMeasurementRealmProxyInterface;
import io.realm.LastBpMeasurementDataRealmProxy;
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

public class BpLastMeasurementRealmProxy
extends BpLastMeasurement
implements BpLastMeasurementRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private BpLastMeasurementColumnInfo columnInfo;
    private ProxyState<BpLastMeasurement> proxyState;

    static {
        expectedObjectSchemaInfo = BpLastMeasurementRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("sys");
        arrayList.add("dia");
        arrayList.add("pulse");
        arrayList.add("time");
        arrayList.add("averageLastDay");
        arrayList.add("averageLastWeek");
        arrayList.add("averageLastMonth");
        arrayList.add("userId");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    BpLastMeasurementRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static BpLastMeasurement copy(Realm realm, BpLastMeasurement bpLastMeasurementRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        LastBpMeasurementData lastBpMeasurementData;
        RealmObjectProxy realmObjectProxy = map.get(bpLastMeasurementRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (BpLastMeasurement)((Object)realmObjectProxy);
        }
        BpLastMeasurement bpLastMeasurement = realm.createObjectInternal(BpLastMeasurement.class, false, Collections.<String>emptyList());
        map.put((RealmModel)((Object)bpLastMeasurementRealmProxyInterface), (RealmObjectProxy)((Object)bpLastMeasurement));
        BpLastMeasurementRealmProxyInterface bpLastMeasurementRealmProxyInterface2 = bpLastMeasurement;
        bpLastMeasurementRealmProxyInterface2.realmSet$sys(bpLastMeasurementRealmProxyInterface.realmGet$sys());
        bpLastMeasurementRealmProxyInterface2.realmSet$dia(bpLastMeasurementRealmProxyInterface.realmGet$dia());
        bpLastMeasurementRealmProxyInterface2.realmSet$pulse(bpLastMeasurementRealmProxyInterface.realmGet$pulse());
        bpLastMeasurementRealmProxyInterface2.realmSet$time(bpLastMeasurementRealmProxyInterface.realmGet$time());
        LastBpMeasurementData lastBpMeasurementData2 = bpLastMeasurementRealmProxyInterface.realmGet$averageLastDay();
        if (lastBpMeasurementData2 == null) {
            bpLastMeasurementRealmProxyInterface2.realmSet$averageLastDay(null);
        } else {
            lastBpMeasurementData = (LastBpMeasurementData)((Object)map.get(lastBpMeasurementData2));
            if (lastBpMeasurementData != null) {
                bpLastMeasurementRealmProxyInterface2.realmSet$averageLastDay(lastBpMeasurementData);
            } else {
                bpLastMeasurementRealmProxyInterface2.realmSet$averageLastDay(LastBpMeasurementDataRealmProxy.copyOrUpdate(realm, lastBpMeasurementData2, bl, map));
            }
        }
        if ((lastBpMeasurementData2 = bpLastMeasurementRealmProxyInterface.realmGet$averageLastWeek()) == null) {
            bpLastMeasurementRealmProxyInterface2.realmSet$averageLastWeek(null);
        } else {
            lastBpMeasurementData = (LastBpMeasurementData)((Object)map.get(lastBpMeasurementData2));
            if (lastBpMeasurementData != null) {
                bpLastMeasurementRealmProxyInterface2.realmSet$averageLastWeek(lastBpMeasurementData);
            } else {
                bpLastMeasurementRealmProxyInterface2.realmSet$averageLastWeek(LastBpMeasurementDataRealmProxy.copyOrUpdate(realm, lastBpMeasurementData2, bl, map));
            }
        }
        if ((lastBpMeasurementData2 = bpLastMeasurementRealmProxyInterface.realmGet$averageLastMonth()) == null) {
            bpLastMeasurementRealmProxyInterface2.realmSet$averageLastMonth(null);
        } else {
            lastBpMeasurementData = (LastBpMeasurementData)((Object)map.get(lastBpMeasurementData2));
            if (lastBpMeasurementData != null) {
                bpLastMeasurementRealmProxyInterface2.realmSet$averageLastMonth(lastBpMeasurementData);
            } else {
                bpLastMeasurementRealmProxyInterface2.realmSet$averageLastMonth(LastBpMeasurementDataRealmProxy.copyOrUpdate(realm, lastBpMeasurementData2, bl, map));
            }
        }
        bpLastMeasurementRealmProxyInterface2.realmSet$userId(bpLastMeasurementRealmProxyInterface.realmGet$userId());
        return bpLastMeasurement;
    }

    public static BpLastMeasurement copyOrUpdate(Realm realm, BpLastMeasurement bpLastMeasurement, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (bpLastMeasurement instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)bpLastMeasurement)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)bpLastMeasurement).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (bpLastMeasurement instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)bpLastMeasurement)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)bpLastMeasurement)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return bpLastMeasurement;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(bpLastMeasurement);
        if (object != null) {
            return (BpLastMeasurement)object;
        }
        return BpLastMeasurementRealmProxy.copy(realm, bpLastMeasurement, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static BpLastMeasurement createDetachedCopy(BpLastMeasurement bpLastMeasurementRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        BpLastMeasurement bpLastMeasurement;
        if (n > n2 || bpLastMeasurementRealmProxyInterface == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(bpLastMeasurementRealmProxyInterface);
        if (cacheData == null) {
            bpLastMeasurement = new BpLastMeasurement();
            map.put((RealmModel)((Object)bpLastMeasurementRealmProxyInterface), new RealmObjectProxy.CacheData<BpLastMeasurement>(n, bpLastMeasurement));
        } else {
            if (n >= cacheData.minDepth) {
                return (BpLastMeasurement)cacheData.object;
            }
            bpLastMeasurement = (BpLastMeasurement)cacheData.object;
            cacheData.minDepth = n;
        }
        cacheData = bpLastMeasurement;
        cacheData.realmSet$sys(bpLastMeasurementRealmProxyInterface.realmGet$sys());
        cacheData.realmSet$dia(bpLastMeasurementRealmProxyInterface.realmGet$dia());
        cacheData.realmSet$pulse(bpLastMeasurementRealmProxyInterface.realmGet$pulse());
        cacheData.realmSet$time(bpLastMeasurementRealmProxyInterface.realmGet$time());
        cacheData.realmSet$averageLastDay(LastBpMeasurementDataRealmProxy.createDetachedCopy(bpLastMeasurementRealmProxyInterface.realmGet$averageLastDay(), n + 1, n2, map));
        cacheData.realmSet$averageLastWeek(LastBpMeasurementDataRealmProxy.createDetachedCopy(bpLastMeasurementRealmProxyInterface.realmGet$averageLastWeek(), n + 1, n2, map));
        cacheData.realmSet$averageLastMonth(LastBpMeasurementDataRealmProxy.createDetachedCopy(bpLastMeasurementRealmProxyInterface.realmGet$averageLastMonth(), n + 1, n2, map));
        cacheData.realmSet$userId(bpLastMeasurementRealmProxyInterface.realmGet$userId());
        return bpLastMeasurement;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("BpLastMeasurement");
        builder.addProperty("sys", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("dia", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("pulse", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("time", RealmFieldType.INTEGER, false, false, true);
        builder.addLinkedProperty("averageLastDay", RealmFieldType.OBJECT, "LastBpMeasurementData");
        builder.addLinkedProperty("averageLastWeek", RealmFieldType.OBJECT, "LastBpMeasurementData");
        builder.addLinkedProperty("averageLastMonth", RealmFieldType.OBJECT, "LastBpMeasurementData");
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_BpLastMeasurement";
    }

    public static BpLastMeasurementColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_BpLastMeasurement")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'BpLastMeasurement' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_BpLastMeasurement");
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
        BpLastMeasurementColumnInfo bpLastMeasurementColumnInfo = new BpLastMeasurementColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("sys")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sys' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("sys") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'sys' in existing Realm file.");
        }
        if (!table.isColumnNullable(bpLastMeasurementColumnInfo.sysIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sys' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'sys' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("dia")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'dia' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("dia") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'dia' in existing Realm file.");
        }
        if (!table.isColumnNullable(bpLastMeasurementColumnInfo.diaIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'dia' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'dia' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("pulse")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'pulse' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("pulse") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'pulse' in existing Realm file.");
        }
        if (!table.isColumnNullable(bpLastMeasurementColumnInfo.pulseIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'pulse' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'pulse' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("time")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'time' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("time") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'time' in existing Realm file.");
        }
        if (table.isColumnNullable(bpLastMeasurementColumnInfo.timeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'time' does support null values in the existing Realm file. Use corresponding boxed type for field 'time' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("averageLastDay")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'averageLastDay' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("averageLastDay") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'LastBpMeasurementData' for field 'averageLastDay'");
        }
        if (!sharedRealm.hasTable("class_LastBpMeasurementData")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_LastBpMeasurementData' for field 'averageLastDay'");
        }
        Table table2 = sharedRealm.getTable("class_LastBpMeasurementData");
        if (!table.getLinkTarget(bpLastMeasurementColumnInfo.averageLastDayIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'averageLastDay': '" + table.getLinkTarget(bpLastMeasurementColumnInfo.averageLastDayIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!hashMap.containsKey("averageLastWeek")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'averageLastWeek' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("averageLastWeek") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'LastBpMeasurementData' for field 'averageLastWeek'");
        }
        if (!sharedRealm.hasTable("class_LastBpMeasurementData")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_LastBpMeasurementData' for field 'averageLastWeek'");
        }
        table2 = sharedRealm.getTable("class_LastBpMeasurementData");
        if (!table.getLinkTarget(bpLastMeasurementColumnInfo.averageLastWeekIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'averageLastWeek': '" + table.getLinkTarget(bpLastMeasurementColumnInfo.averageLastWeekIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!hashMap.containsKey("averageLastMonth")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'averageLastMonth' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("averageLastMonth") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'LastBpMeasurementData' for field 'averageLastMonth'");
        }
        if (!sharedRealm.hasTable("class_LastBpMeasurementData")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_LastBpMeasurementData' for field 'averageLastMonth'");
        }
        table2 = sharedRealm.getTable("class_LastBpMeasurementData");
        if (!table.getLinkTarget(bpLastMeasurementColumnInfo.averageLastMonthIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'averageLastMonth': '" + table.getLinkTarget(bpLastMeasurementColumnInfo.averageLastMonthIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(bpLastMeasurementColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        return bpLastMeasurementColumnInfo;
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
                object = (BpLastMeasurementRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((BpLastMeasurementRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((BpLastMeasurementRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((BpLastMeasurementRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (BpLastMeasurementColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<BpLastMeasurementRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public LastBpMeasurementData realmGet$averageLastDay() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.averageLastDayIndex)) {
            return null;
        }
        return this.proxyState.getRealm$realm().get(LastBpMeasurementData.class, this.proxyState.getRow$realm().getLink(this.columnInfo.averageLastDayIndex), false, Collections.<String>emptyList());
    }

    @Override
    public LastBpMeasurementData realmGet$averageLastMonth() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.averageLastMonthIndex)) {
            return null;
        }
        return this.proxyState.getRealm$realm().get(LastBpMeasurementData.class, this.proxyState.getRow$realm().getLink(this.columnInfo.averageLastMonthIndex), false, Collections.<String>emptyList());
    }

    @Override
    public LastBpMeasurementData realmGet$averageLastWeek() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.averageLastWeekIndex)) {
            return null;
        }
        return this.proxyState.getRealm$realm().get(LastBpMeasurementData.class, this.proxyState.getRow$realm().getLink(this.columnInfo.averageLastWeekIndex), false, Collections.<String>emptyList());
    }

    @Override
    public Integer realmGet$dia() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.diaIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.diaIndex);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public Integer realmGet$pulse() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.pulseIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.pulseIndex);
    }

    @Override
    public Integer realmGet$sys() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.sysIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.sysIndex);
    }

    @Override
    public long realmGet$time() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.timeIndex);
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void realmSet$averageLastDay(LastBpMeasurementData object) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("averageLastDay")) {
                return;
            }
            Object object2 = object;
            if (object != null) {
                object2 = object;
                if (!RealmObject.isManaged(object)) {
                    object2 = (LastBpMeasurementData)((Realm)this.proxyState.getRealm$realm()).copyToRealm(object);
                }
            }
            object = this.proxyState.getRow$realm();
            if (object2 == null) {
                object.nullifyLink(this.columnInfo.averageLastDayIndex);
                return;
            }
            if (!RealmObject.isValid(object2)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy)object2).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            object.getTable().setLink(this.columnInfo.averageLastDayIndex, object.getIndex(), ((RealmObjectProxy)object2).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (object == null) {
            this.proxyState.getRow$realm().nullifyLink(this.columnInfo.averageLastDayIndex);
            return;
        }
        if (!RealmObject.isManaged(object) || !RealmObject.isValid(object)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        this.proxyState.getRow$realm().setLink(this.columnInfo.averageLastDayIndex, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void realmSet$averageLastMonth(LastBpMeasurementData object) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("averageLastMonth")) {
                return;
            }
            Object object2 = object;
            if (object != null) {
                object2 = object;
                if (!RealmObject.isManaged(object)) {
                    object2 = (LastBpMeasurementData)((Realm)this.proxyState.getRealm$realm()).copyToRealm(object);
                }
            }
            object = this.proxyState.getRow$realm();
            if (object2 == null) {
                object.nullifyLink(this.columnInfo.averageLastMonthIndex);
                return;
            }
            if (!RealmObject.isValid(object2)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy)object2).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            object.getTable().setLink(this.columnInfo.averageLastMonthIndex, object.getIndex(), ((RealmObjectProxy)object2).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (object == null) {
            this.proxyState.getRow$realm().nullifyLink(this.columnInfo.averageLastMonthIndex);
            return;
        }
        if (!RealmObject.isManaged(object) || !RealmObject.isValid(object)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        this.proxyState.getRow$realm().setLink(this.columnInfo.averageLastMonthIndex, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void realmSet$averageLastWeek(LastBpMeasurementData object) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("averageLastWeek")) {
                return;
            }
            Object object2 = object;
            if (object != null) {
                object2 = object;
                if (!RealmObject.isManaged(object)) {
                    object2 = (LastBpMeasurementData)((Realm)this.proxyState.getRealm$realm()).copyToRealm(object);
                }
            }
            object = this.proxyState.getRow$realm();
            if (object2 == null) {
                object.nullifyLink(this.columnInfo.averageLastWeekIndex);
                return;
            }
            if (!RealmObject.isValid(object2)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy)object2).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            object.getTable().setLink(this.columnInfo.averageLastWeekIndex, object.getIndex(), ((RealmObjectProxy)object2).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (object == null) {
            this.proxyState.getRow$realm().nullifyLink(this.columnInfo.averageLastWeekIndex);
            return;
        }
        if (!RealmObject.isManaged(object) || !RealmObject.isValid(object)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        this.proxyState.getRow$realm().setLink(this.columnInfo.averageLastWeekIndex, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public void realmSet$dia(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.diaIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.diaIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.diaIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.diaIndex, n.intValue());
    }

    @Override
    public void realmSet$pulse(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.pulseIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.pulseIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.pulseIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.pulseIndex, n.intValue());
    }

    @Override
    public void realmSet$sys(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.sysIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.sysIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.sysIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.sysIndex, n.intValue());
    }

    @Override
    public void realmSet$time(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.timeIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.timeIndex, l);
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
        StringBuilder stringBuilder = new StringBuilder("BpLastMeasurement = proxy[");
        stringBuilder.append("{sys:");
        Object object = this.realmGet$sys() != null ? this.realmGet$sys() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dia:");
        object = this.realmGet$dia() != null ? this.realmGet$dia() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{pulse:");
        object = this.realmGet$pulse() != null ? this.realmGet$pulse() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(this.realmGet$time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{averageLastDay:");
        object = this.realmGet$averageLastDay() != null ? "LastBpMeasurementData" : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{averageLastWeek:");
        object = this.realmGet$averageLastWeek() != null ? "LastBpMeasurementData" : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{averageLastMonth:");
        object = this.realmGet$averageLastMonth() != null ? "LastBpMeasurementData" : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class BpLastMeasurementColumnInfo
    extends ColumnInfo {
        long averageLastDayIndex;
        long averageLastMonthIndex;
        long averageLastWeekIndex;
        long diaIndex;
        long pulseIndex;
        long sysIndex;
        long timeIndex;
        long userIdIndex;

        BpLastMeasurementColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        BpLastMeasurementColumnInfo(SharedRealm sharedRealm, Table table) {
            super(8);
            this.sysIndex = this.addColumnDetails(table, "sys", RealmFieldType.INTEGER);
            this.diaIndex = this.addColumnDetails(table, "dia", RealmFieldType.INTEGER);
            this.pulseIndex = this.addColumnDetails(table, "pulse", RealmFieldType.INTEGER);
            this.timeIndex = this.addColumnDetails(table, "time", RealmFieldType.INTEGER);
            this.averageLastDayIndex = this.addColumnDetails(table, "averageLastDay", RealmFieldType.OBJECT);
            this.averageLastWeekIndex = this.addColumnDetails(table, "averageLastWeek", RealmFieldType.OBJECT);
            this.averageLastMonthIndex = this.addColumnDetails(table, "averageLastMonth", RealmFieldType.OBJECT);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new BpLastMeasurementColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (BpLastMeasurementColumnInfo)columnInfo;
            columnInfo2 = (BpLastMeasurementColumnInfo)columnInfo2;
            ((BpLastMeasurementColumnInfo)columnInfo2).sysIndex = ((BpLastMeasurementColumnInfo)columnInfo).sysIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).diaIndex = ((BpLastMeasurementColumnInfo)columnInfo).diaIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).pulseIndex = ((BpLastMeasurementColumnInfo)columnInfo).pulseIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).timeIndex = ((BpLastMeasurementColumnInfo)columnInfo).timeIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).averageLastDayIndex = ((BpLastMeasurementColumnInfo)columnInfo).averageLastDayIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).averageLastWeekIndex = ((BpLastMeasurementColumnInfo)columnInfo).averageLastWeekIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).averageLastMonthIndex = ((BpLastMeasurementColumnInfo)columnInfo).averageLastMonthIndex;
            ((BpLastMeasurementColumnInfo)columnInfo2).userIdIndex = ((BpLastMeasurementColumnInfo)columnInfo).userIdIndex;
        }
    }

}

