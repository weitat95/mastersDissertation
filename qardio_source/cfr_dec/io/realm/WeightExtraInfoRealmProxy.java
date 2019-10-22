/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;
import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.WeightExtraInfoRealmProxyInterface;
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

public class WeightExtraInfoRealmProxy
extends WeightExtraInfo
implements WeightExtraInfoRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private WeightExtraInfoColumnInfo columnInfo;
    private ProxyState<WeightExtraInfo> proxyState;

    static {
        expectedObjectSchemaInfo = WeightExtraInfoRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("measurementId");
        arrayList.add("height");
        arrayList.add("battery");
        arrayList.add("sex");
        arrayList.add("age");
        arrayList.add("userId");
        arrayList.add("user");
        arrayList.add("fat");
        arrayList.add("bone");
        arrayList.add("water");
        arrayList.add("muscle");
        arrayList.add("source");
        arrayList.add("impedance");
        arrayList.add("bmi");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    WeightExtraInfoRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static WeightExtraInfo copy(Realm object, WeightExtraInfo weightExtraInfoRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(weightExtraInfoRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (WeightExtraInfo)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(WeightExtraInfo.class, false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)weightExtraInfoRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        weightExtraInfoRealmProxyInterface = weightExtraInfoRealmProxyInterface;
        object2 = (WeightExtraInfoRealmProxyInterface)object;
        object2.realmSet$measurementId(weightExtraInfoRealmProxyInterface.realmGet$measurementId());
        object2.realmSet$height(weightExtraInfoRealmProxyInterface.realmGet$height());
        object2.realmSet$battery(weightExtraInfoRealmProxyInterface.realmGet$battery());
        object2.realmSet$sex(weightExtraInfoRealmProxyInterface.realmGet$sex());
        object2.realmSet$age(weightExtraInfoRealmProxyInterface.realmGet$age());
        object2.realmSet$userId(weightExtraInfoRealmProxyInterface.realmGet$userId());
        object2.realmSet$user(weightExtraInfoRealmProxyInterface.realmGet$user());
        object2.realmSet$fat(weightExtraInfoRealmProxyInterface.realmGet$fat());
        object2.realmSet$bone(weightExtraInfoRealmProxyInterface.realmGet$bone());
        object2.realmSet$water(weightExtraInfoRealmProxyInterface.realmGet$water());
        object2.realmSet$muscle(weightExtraInfoRealmProxyInterface.realmGet$muscle());
        object2.realmSet$source(weightExtraInfoRealmProxyInterface.realmGet$source());
        object2.realmSet$impedance(weightExtraInfoRealmProxyInterface.realmGet$impedance());
        object2.realmSet$bmi(weightExtraInfoRealmProxyInterface.realmGet$bmi());
        return object;
    }

    public static WeightExtraInfo copyOrUpdate(Realm realm, WeightExtraInfo weightExtraInfo, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (weightExtraInfo instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)weightExtraInfo)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)weightExtraInfo).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (weightExtraInfo instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)weightExtraInfo)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)weightExtraInfo)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return weightExtraInfo;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(weightExtraInfo);
        if (object != null) {
            return (WeightExtraInfo)object;
        }
        return WeightExtraInfoRealmProxy.copy(realm, weightExtraInfo, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static WeightExtraInfo createDetachedCopy(WeightExtraInfo weightExtraInfoRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || weightExtraInfoRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(weightExtraInfoRealmProxyInterface);
        if (object2 == null) {
            object2 = new WeightExtraInfo();
            object.put(weightExtraInfoRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (WeightExtraInfo)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (WeightExtraInfo)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (WeightExtraInfoRealmProxyInterface)object;
        object2.realmSet$measurementId(weightExtraInfoRealmProxyInterface.realmGet$measurementId());
        object2.realmSet$height(weightExtraInfoRealmProxyInterface.realmGet$height());
        object2.realmSet$battery(weightExtraInfoRealmProxyInterface.realmGet$battery());
        object2.realmSet$sex(weightExtraInfoRealmProxyInterface.realmGet$sex());
        object2.realmSet$age(weightExtraInfoRealmProxyInterface.realmGet$age());
        object2.realmSet$userId(weightExtraInfoRealmProxyInterface.realmGet$userId());
        object2.realmSet$user(weightExtraInfoRealmProxyInterface.realmGet$user());
        object2.realmSet$fat(weightExtraInfoRealmProxyInterface.realmGet$fat());
        object2.realmSet$bone(weightExtraInfoRealmProxyInterface.realmGet$bone());
        object2.realmSet$water(weightExtraInfoRealmProxyInterface.realmGet$water());
        object2.realmSet$muscle(weightExtraInfoRealmProxyInterface.realmGet$muscle());
        object2.realmSet$source(weightExtraInfoRealmProxyInterface.realmGet$source());
        object2.realmSet$impedance(weightExtraInfoRealmProxyInterface.realmGet$impedance());
        object2.realmSet$bmi(weightExtraInfoRealmProxyInterface.realmGet$bmi());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("WeightExtraInfo");
        builder.addProperty("measurementId", RealmFieldType.STRING, false, false, false);
        builder.addProperty("height", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("battery", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("sex", RealmFieldType.STRING, false, false, false);
        builder.addProperty("age", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("user", RealmFieldType.STRING, false, false, false);
        builder.addProperty("fat", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("bone", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("water", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("muscle", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("source", RealmFieldType.STRING, false, false, false);
        builder.addProperty("impedance", RealmFieldType.INTEGER, false, false, false);
        builder.addProperty("bmi", RealmFieldType.FLOAT, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_WeightExtraInfo";
    }

    public static WeightExtraInfoColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_WeightExtraInfo")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'WeightExtraInfo' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_WeightExtraInfo");
        long l = table.getColumnCount();
        if (l != 14L) {
            if (l < 14L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 14 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 14 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 14 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        WeightExtraInfoColumnInfo weightExtraInfoColumnInfo = new WeightExtraInfoColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("measurementId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'measurementId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("measurementId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'measurementId' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.measurementIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'measurementId' is required. Either set @Required to field 'measurementId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("height")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'height' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("height") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'height' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.heightIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'height' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'height' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("battery")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'battery' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("battery") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'battery' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.batteryIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'battery' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'battery' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("sex")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sex' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("sex") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'sex' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.sexIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sex' is required. Either set @Required to field 'sex' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("age")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'age' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("age") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'age' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.ageIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'age' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'age' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Long' for field 'userId' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("user")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("user") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'user' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.userIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'user' is required. Either set @Required to field 'user' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("fat")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'fat' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("fat") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'fat' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.fatIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'fat' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'fat' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("bone")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'bone' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("bone") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'bone' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.boneIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'bone' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'bone' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("water")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'water' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("water") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'water' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.waterIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'water' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'water' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("muscle")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'muscle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("muscle") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'muscle' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.muscleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'muscle' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'muscle' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("source")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'source' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("source") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'source' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.sourceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'source' is required. Either set @Required to field 'source' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("impedance")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'impedance' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("impedance") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'impedance' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.impedanceIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'impedance' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'impedance' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("bmi")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'bmi' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("bmi") != RealmFieldType.FLOAT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Float' for field 'bmi' in existing Realm file.");
        }
        if (!table.isColumnNullable(weightExtraInfoColumnInfo.bmiIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'bmi' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'bmi' or migrate using RealmObjectSchema.setNullable().");
        }
        return weightExtraInfoColumnInfo;
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
                object = (WeightExtraInfoRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((WeightExtraInfoRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((WeightExtraInfoRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((WeightExtraInfoRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (WeightExtraInfoColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<WeightExtraInfoRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public Integer realmGet$age() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.ageIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.ageIndex);
    }

    @Override
    public Integer realmGet$battery() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.batteryIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.batteryIndex);
    }

    @Override
    public Float realmGet$bmi() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.bmiIndex)) {
            return null;
        }
        return Float.valueOf(this.proxyState.getRow$realm().getFloat(this.columnInfo.bmiIndex));
    }

    @Override
    public Integer realmGet$bone() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.boneIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.boneIndex);
    }

    @Override
    public Integer realmGet$fat() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.fatIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.fatIndex);
    }

    @Override
    public Integer realmGet$height() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.heightIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.heightIndex);
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
    public String realmGet$measurementId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.measurementIdIndex);
    }

    @Override
    public Integer realmGet$muscle() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.muscleIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.muscleIndex);
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    @Override
    public String realmGet$sex() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.sexIndex);
    }

    @Override
    public String realmGet$source() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.sourceIndex);
    }

    @Override
    public String realmGet$user() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.userIndex);
    }

    @Override
    public Long realmGet$userId() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.userIdIndex)) {
            return null;
        }
        return this.proxyState.getRow$realm().getLong(this.columnInfo.userIdIndex);
    }

    @Override
    public Integer realmGet$water() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.proxyState.getRow$realm().isNull(this.columnInfo.waterIndex)) {
            return null;
        }
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.waterIndex);
    }

    @Override
    public void realmSet$age(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.ageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.ageIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.ageIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.ageIndex, n.intValue());
    }

    @Override
    public void realmSet$battery(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.batteryIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.batteryIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.batteryIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.batteryIndex, n.intValue());
    }

    @Override
    public void realmSet$bmi(Float f) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (f == null) {
                row.getTable().setNull(this.columnInfo.bmiIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setFloat(this.columnInfo.bmiIndex, row.getIndex(), f.floatValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (f == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.bmiIndex);
            return;
        }
        this.proxyState.getRow$realm().setFloat(this.columnInfo.bmiIndex, f.floatValue());
    }

    @Override
    public void realmSet$bone(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.boneIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.boneIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.boneIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.boneIndex, n.intValue());
    }

    @Override
    public void realmSet$fat(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.fatIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.fatIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.fatIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.fatIndex, n.intValue());
    }

    @Override
    public void realmSet$height(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.heightIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.heightIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.heightIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.heightIndex, n.intValue());
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
    public void realmSet$measurementId(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.measurementIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.measurementIdIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.measurementIdIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.measurementIdIndex, string2);
    }

    @Override
    public void realmSet$muscle(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.muscleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.muscleIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.muscleIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.muscleIndex, n.intValue());
    }

    @Override
    public void realmSet$sex(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.sexIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.sexIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.sexIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.sexIndex, string2);
    }

    @Override
    public void realmSet$source(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.sourceIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.sourceIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.sourceIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.sourceIndex, string2);
    }

    @Override
    public void realmSet$user(String string2) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (string2 == null) {
                row.getTable().setNull(this.columnInfo.userIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(this.columnInfo.userIndex, row.getIndex(), string2, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (string2 == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.userIndex);
            return;
        }
        this.proxyState.getRow$realm().setString(this.columnInfo.userIndex, string2);
    }

    @Override
    public void realmSet$userId(Long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (l == null) {
                row.getTable().setNull(this.columnInfo.userIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.userIdIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (l == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.userIdIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.userIdIndex, l);
    }

    @Override
    public void realmSet$water(Integer n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            if (n == null) {
                row.getTable().setNull(this.columnInfo.waterIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(this.columnInfo.waterIndex, row.getIndex(), n.intValue(), true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (n == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.waterIndex);
            return;
        }
        this.proxyState.getRow$realm().setLong(this.columnInfo.waterIndex, n.intValue());
    }

    static final class WeightExtraInfoColumnInfo
    extends ColumnInfo {
        long ageIndex;
        long batteryIndex;
        long bmiIndex;
        long boneIndex;
        long fatIndex;
        long heightIndex;
        long impedanceIndex;
        long measurementIdIndex;
        long muscleIndex;
        long sexIndex;
        long sourceIndex;
        long userIdIndex;
        long userIndex;
        long waterIndex;

        WeightExtraInfoColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        WeightExtraInfoColumnInfo(SharedRealm sharedRealm, Table table) {
            super(14);
            this.measurementIdIndex = this.addColumnDetails(table, "measurementId", RealmFieldType.STRING);
            this.heightIndex = this.addColumnDetails(table, "height", RealmFieldType.INTEGER);
            this.batteryIndex = this.addColumnDetails(table, "battery", RealmFieldType.INTEGER);
            this.sexIndex = this.addColumnDetails(table, "sex", RealmFieldType.STRING);
            this.ageIndex = this.addColumnDetails(table, "age", RealmFieldType.INTEGER);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.userIndex = this.addColumnDetails(table, "user", RealmFieldType.STRING);
            this.fatIndex = this.addColumnDetails(table, "fat", RealmFieldType.INTEGER);
            this.boneIndex = this.addColumnDetails(table, "bone", RealmFieldType.INTEGER);
            this.waterIndex = this.addColumnDetails(table, "water", RealmFieldType.INTEGER);
            this.muscleIndex = this.addColumnDetails(table, "muscle", RealmFieldType.INTEGER);
            this.sourceIndex = this.addColumnDetails(table, "source", RealmFieldType.STRING);
            this.impedanceIndex = this.addColumnDetails(table, "impedance", RealmFieldType.INTEGER);
            this.bmiIndex = this.addColumnDetails(table, "bmi", RealmFieldType.FLOAT);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new WeightExtraInfoColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (WeightExtraInfoColumnInfo)columnInfo;
            columnInfo2 = (WeightExtraInfoColumnInfo)columnInfo2;
            ((WeightExtraInfoColumnInfo)columnInfo2).measurementIdIndex = ((WeightExtraInfoColumnInfo)columnInfo).measurementIdIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).heightIndex = ((WeightExtraInfoColumnInfo)columnInfo).heightIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).batteryIndex = ((WeightExtraInfoColumnInfo)columnInfo).batteryIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).sexIndex = ((WeightExtraInfoColumnInfo)columnInfo).sexIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).ageIndex = ((WeightExtraInfoColumnInfo)columnInfo).ageIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).userIdIndex = ((WeightExtraInfoColumnInfo)columnInfo).userIdIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).userIndex = ((WeightExtraInfoColumnInfo)columnInfo).userIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).fatIndex = ((WeightExtraInfoColumnInfo)columnInfo).fatIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).boneIndex = ((WeightExtraInfoColumnInfo)columnInfo).boneIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).waterIndex = ((WeightExtraInfoColumnInfo)columnInfo).waterIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).muscleIndex = ((WeightExtraInfoColumnInfo)columnInfo).muscleIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).sourceIndex = ((WeightExtraInfoColumnInfo)columnInfo).sourceIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).impedanceIndex = ((WeightExtraInfoColumnInfo)columnInfo).impedanceIndex;
            ((WeightExtraInfoColumnInfo)columnInfo2).bmiIndex = ((WeightExtraInfoColumnInfo)columnInfo).bmiIndex;
        }
    }

}

