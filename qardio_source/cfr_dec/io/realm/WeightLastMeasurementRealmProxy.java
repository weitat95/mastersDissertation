/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;
import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.WeightExtraInfoRealmProxy;
import io.realm.WeightLastMeasurementRealmProxyInterface;
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

public class WeightLastMeasurementRealmProxy
extends WeightLastMeasurement
implements WeightLastMeasurementRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private WeightLastMeasurementColumnInfo columnInfo;
    private ProxyState<WeightLastMeasurement> proxyState;

    static {
        expectedObjectSchemaInfo = WeightLastMeasurementRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("userId");
        arrayList.add("time");
        arrayList.add("weight");
        arrayList.add("impedance");
        arrayList.add("extraInfo");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    WeightLastMeasurementRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static WeightLastMeasurement copy(Realm realm, WeightLastMeasurement weightLastMeasurementRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        RealmObjectProxy realmObjectProxy = map.get(weightLastMeasurementRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (WeightLastMeasurement)((Object)realmObjectProxy);
        }
        WeightLastMeasurement weightLastMeasurement = realm.createObjectInternal(WeightLastMeasurement.class, false, Collections.<String>emptyList());
        map.put((RealmModel)((Object)weightLastMeasurementRealmProxyInterface), (RealmObjectProxy)((Object)weightLastMeasurement));
        Object object = weightLastMeasurementRealmProxyInterface;
        weightLastMeasurementRealmProxyInterface = weightLastMeasurement;
        weightLastMeasurementRealmProxyInterface.realmSet$userId(object.realmGet$userId());
        weightLastMeasurementRealmProxyInterface.realmSet$time(object.realmGet$time());
        weightLastMeasurementRealmProxyInterface.realmSet$weight(object.realmGet$weight());
        weightLastMeasurementRealmProxyInterface.realmSet$impedance(object.realmGet$impedance());
        object = object.realmGet$extraInfo();
        if (object == null) {
            weightLastMeasurementRealmProxyInterface.realmSet$extraInfo(null);
            do {
                return weightLastMeasurement;
                break;
            } while (true);
        }
        WeightExtraInfo weightExtraInfo = (WeightExtraInfo)((Object)map.get(object));
        if (weightExtraInfo != null) {
            weightLastMeasurementRealmProxyInterface.realmSet$extraInfo(weightExtraInfo);
            return weightLastMeasurement;
        }
        weightLastMeasurementRealmProxyInterface.realmSet$extraInfo(WeightExtraInfoRealmProxy.copyOrUpdate(realm, (WeightExtraInfo)object, bl, map));
        return weightLastMeasurement;
    }

    public static WeightLastMeasurement copyOrUpdate(Realm realm, WeightLastMeasurement weightLastMeasurement, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (weightLastMeasurement instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)weightLastMeasurement)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)weightLastMeasurement).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (weightLastMeasurement instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)weightLastMeasurement)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)weightLastMeasurement)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return weightLastMeasurement;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(weightLastMeasurement);
        if (object != null) {
            return (WeightLastMeasurement)object;
        }
        return WeightLastMeasurementRealmProxy.copy(realm, weightLastMeasurement, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static WeightLastMeasurement createDetachedCopy(WeightLastMeasurement weightLastMeasurementRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        WeightLastMeasurement weightLastMeasurement;
        if (n > n2 || weightLastMeasurementRealmProxyInterface == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(weightLastMeasurementRealmProxyInterface);
        if (cacheData == null) {
            weightLastMeasurement = new WeightLastMeasurement();
            map.put((RealmModel)((Object)weightLastMeasurementRealmProxyInterface), new RealmObjectProxy.CacheData<WeightLastMeasurement>(n, weightLastMeasurement));
        } else {
            if (n >= cacheData.minDepth) {
                return (WeightLastMeasurement)cacheData.object;
            }
            weightLastMeasurement = (WeightLastMeasurement)cacheData.object;
            cacheData.minDepth = n;
        }
        cacheData = weightLastMeasurement;
        cacheData.realmSet$userId(weightLastMeasurementRealmProxyInterface.realmGet$userId());
        cacheData.realmSet$time(weightLastMeasurementRealmProxyInterface.realmGet$time());
        cacheData.realmSet$weight(weightLastMeasurementRealmProxyInterface.realmGet$weight());
        cacheData.realmSet$impedance(weightLastMeasurementRealmProxyInterface.realmGet$impedance());
        cacheData.realmSet$extraInfo(WeightExtraInfoRealmProxy.createDetachedCopy(weightLastMeasurementRealmProxyInterface.realmGet$extraInfo(), n + 1, n2, map));
        return weightLastMeasurement;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("WeightLastMeasurement");
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("time", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("weight", RealmFieldType.FLOAT, false, false, false);
        builder.addProperty("impedance", RealmFieldType.INTEGER, false, false, false);
        builder.addLinkedProperty("extraInfo", RealmFieldType.OBJECT, "WeightExtraInfo");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_WeightLastMeasurement";
    }

    public static WeightLastMeasurementColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_WeightLastMeasurement")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'WeightLastMeasurement' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_WeightLastMeasurement");
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
        Object object = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            object.put(table.getColumnName(i), table.getColumnType(i));
        }
        WeightLastMeasurementColumnInfo weightLastMeasurementColumnInfo = new WeightLastMeasurementColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!object.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (object.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(weightLastMeasurementColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!object.containsKey("time")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'time' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (object.get("time") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Long' for field 'time' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightLastMeasurementColumnInfo.timeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'time' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'time' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!object.containsKey("weight")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'weight' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (object.get("weight") != RealmFieldType.FLOAT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Float' for field 'weight' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightLastMeasurementColumnInfo.weightIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'weight' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'weight' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!object.containsKey("impedance")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'impedance' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (object.get("impedance") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'impedance' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightLastMeasurementColumnInfo.impedanceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'impedance' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'impedance' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!object.containsKey("extraInfo")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'extraInfo' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (object.get("extraInfo") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'WeightExtraInfo' for field 'extraInfo'");
        }
        if (!sharedRealm.hasTable("class_WeightExtraInfo")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_WeightExtraInfo' for field 'extraInfo'");
        }
        object = sharedRealm.getTable("class_WeightExtraInfo");
        if (!table.getLinkTarget(weightLastMeasurementColumnInfo.extraInfoIndex).hasSameSchema((Table)object)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'extraInfo': '" + table.getLinkTarget(weightLastMeasurementColumnInfo.extraInfoIndex).getName() + "' expected - was '" + ((Table)object).getName() + "'");
        }
        return weightLastMeasurementColumnInfo;
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
                object = (WeightLastMeasurementRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((WeightLastMeasurementRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((WeightLastMeasurementRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((WeightLastMeasurementRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (WeightLastMeasurementColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<WeightLastMeasurementRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public WeightExtraInfo realmGet$extraInfo() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNullLink(this.columnInfo.extraInfoIndex)) {
            return null;
        }
        return this.proxyState.getRealm$realm().get(WeightExtraInfo.class, this.proxyState.getRow$realm().getLink(this.columnInfo.extraInfoIndex), false, Collections.<String>emptyList());
    }

    @Override
    public Integer realmGet$impedance() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.impedanceIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.impedanceIndex);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public Long realmGet$time() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.timeIndex)) {
            return null;
        }
        return this.proxyState.getRow$realm().getLong(this.columnInfo.timeIndex);
    }

    @Override
    public long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public Float realmGet$weight() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.weightIndex)) {
            return null;
        }
        return Float.valueOf(this.proxyState.getRow$realm().getFloat(this.columnInfo.weightIndex));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void realmSet$extraInfo(WeightExtraInfo object) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm() || this.proxyState.getExcludeFields$realm().contains("extraInfo")) {
                return;
            }
            Object object2 = object;
            if (object != null) {
                object2 = object;
                if (!RealmObject.isManaged(object)) {
                    object2 = (WeightExtraInfo)((Realm)this.proxyState.getRealm$realm()).copyToRealm(object);
                }
            }
            object = this.proxyState.getRow$realm();
            if (object2 == null) {
                object.nullifyLink(this.columnInfo.extraInfoIndex);
                return;
            }
            if (!RealmObject.isValid(object2)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy)object2).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            object.getTable().setLink(this.columnInfo.extraInfoIndex, object.getIndex(), ((RealmObjectProxy)object2).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (object == null) {
            this.proxyState.getRow$realm().nullifyLink(this.columnInfo.extraInfoIndex);
            return;
        }
        if (!RealmObject.isManaged(object) || !RealmObject.isValid(object)) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        this.proxyState.getRow$realm().setLink(this.columnInfo.extraInfoIndex, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public void realmSet$impedance(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.impedanceIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.impedanceIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.impedanceIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.impedanceIndex, n.intValue());
    }

    @Override
    public void realmSet$time(Long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (l == null) {
                row.getTable().setNull(this.columnInfo.timeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.timeIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (l == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.timeIndex);
            return;
        }
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

    @Override
    public void realmSet$weight(Float f) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (f == null) {
                row.getTable().setNull(this.columnInfo.weightIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setFloat(this.columnInfo.weightIndex, row.getIndex(), f.floatValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (f == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.weightIndex);
            return;
        }
        this.proxyState.getRow$realm().setFloat(this.columnInfo.weightIndex, f.floatValue());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("WeightLastMeasurement = proxy[");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        Object object = this.realmGet$time() != null ? this.realmGet$time() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{weight:");
        object = this.realmGet$weight() != null ? this.realmGet$weight() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{impedance:");
        object = this.realmGet$impedance() != null ? this.realmGet$impedance() : "null";
        stringBuilder.append(object);
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{extraInfo:");
        object = this.realmGet$extraInfo() != null ? "WeightExtraInfo" : "null";
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class WeightLastMeasurementColumnInfo
    extends ColumnInfo {
        long extraInfoIndex;
        long impedanceIndex;
        long timeIndex;
        long userIdIndex;
        long weightIndex;

        WeightLastMeasurementColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        WeightLastMeasurementColumnInfo(SharedRealm sharedRealm, Table table) {
            super(5);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.timeIndex = this.addColumnDetails(table, "time", RealmFieldType.INTEGER);
            this.weightIndex = this.addColumnDetails(table, "weight", RealmFieldType.FLOAT);
            this.impedanceIndex = this.addColumnDetails(table, "impedance", RealmFieldType.INTEGER);
            this.extraInfoIndex = this.addColumnDetails(table, "extraInfo", RealmFieldType.OBJECT);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new WeightLastMeasurementColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (WeightLastMeasurementColumnInfo)columnInfo;
            columnInfo2 = (WeightLastMeasurementColumnInfo)columnInfo2;
            ((WeightLastMeasurementColumnInfo)columnInfo2).userIdIndex = ((WeightLastMeasurementColumnInfo)columnInfo).userIdIndex;
            ((WeightLastMeasurementColumnInfo)columnInfo2).timeIndex = ((WeightLastMeasurementColumnInfo)columnInfo).timeIndex;
            ((WeightLastMeasurementColumnInfo)columnInfo2).weightIndex = ((WeightLastMeasurementColumnInfo)columnInfo).weightIndex;
            ((WeightLastMeasurementColumnInfo)columnInfo2).impedanceIndex = ((WeightLastMeasurementColumnInfo)columnInfo).impedanceIndex;
            ((WeightLastMeasurementColumnInfo)columnInfo2).extraInfoIndex = ((WeightLastMeasurementColumnInfo)columnInfo).extraInfoIndex;
        }
    }

}

