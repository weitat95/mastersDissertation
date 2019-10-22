/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import io.realm.ActivityTrackedGroupedByDayRealmProxyInterface;
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

public class ActivityTrackedGroupedByDayRealmProxy
extends ActivityTrackedGroupedByDay
implements ActivityTrackedGroupedByDayRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private ActivityTrackedGroupedByDayColumnInfo columnInfo;
    private ProxyState<ActivityTrackedGroupedByDay> proxyState;

    static {
        expectedObjectSchemaInfo = ActivityTrackedGroupedByDayRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("startTimestamp");
        arrayList.add("endTimestamp");
        arrayList.add("userId");
        arrayList.add("totalSteps");
        arrayList.add("totalSecondsOfActivity");
        arrayList.add("totalSecondsWalking");
        arrayList.add("totalSecondsRunning");
        arrayList.add("totalSecondsCycling");
        arrayList.add("goalSteps");
        arrayList.add("goalWalk");
        arrayList.add("goalRun");
        arrayList.add("goalCycle");
        arrayList.add("goalActivity");
        arrayList.add("goalWeight");
        arrayList.add("notifyTotalGoal");
        arrayList.add("notifyActivityGoal");
        arrayList.add("notifyStepsGoal");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    ActivityTrackedGroupedByDayRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    public static ActivityTrackedGroupedByDay copy(Realm object, ActivityTrackedGroupedByDay activityTrackedGroupedByDayRealmProxyInterface, boolean bl, Map<RealmModel, RealmObjectProxy> object2) {
        RealmObjectProxy realmObjectProxy = object2.get(activityTrackedGroupedByDayRealmProxyInterface);
        if (realmObjectProxy != null) {
            return (ActivityTrackedGroupedByDay)((Object)realmObjectProxy);
        }
        object = ((Realm)object).createObjectInternal(ActivityTrackedGroupedByDay.class, false, Collections.<String>emptyList());
        object2.put((RealmModel)((Object)activityTrackedGroupedByDayRealmProxyInterface), (RealmObjectProxy)((RealmObjectProxy)object));
        activityTrackedGroupedByDayRealmProxyInterface = activityTrackedGroupedByDayRealmProxyInterface;
        object2 = (ActivityTrackedGroupedByDayRealmProxyInterface)object;
        object2.realmSet$startTimestamp(activityTrackedGroupedByDayRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$endTimestamp(activityTrackedGroupedByDayRealmProxyInterface.realmGet$endTimestamp());
        object2.realmSet$userId(activityTrackedGroupedByDayRealmProxyInterface.realmGet$userId());
        object2.realmSet$totalSteps(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSteps());
        object2.realmSet$totalSecondsOfActivity(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsOfActivity());
        object2.realmSet$totalSecondsWalking(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsWalking());
        object2.realmSet$totalSecondsRunning(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsRunning());
        object2.realmSet$totalSecondsCycling(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsCycling());
        object2.realmSet$goalSteps(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalSteps());
        object2.realmSet$goalWalk(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalWalk());
        object2.realmSet$goalRun(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalRun());
        object2.realmSet$goalCycle(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalCycle());
        object2.realmSet$goalActivity(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalActivity());
        object2.realmSet$goalWeight(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalWeight());
        object2.realmSet$notifyTotalGoal(activityTrackedGroupedByDayRealmProxyInterface.realmGet$notifyTotalGoal());
        object2.realmSet$notifyActivityGoal(activityTrackedGroupedByDayRealmProxyInterface.realmGet$notifyActivityGoal());
        object2.realmSet$notifyStepsGoal(activityTrackedGroupedByDayRealmProxyInterface.realmGet$notifyStepsGoal());
        return object;
    }

    public static ActivityTrackedGroupedByDay copyOrUpdate(Realm realm, ActivityTrackedGroupedByDay activityTrackedGroupedByDay, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (activityTrackedGroupedByDay instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)activityTrackedGroupedByDay)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)activityTrackedGroupedByDay).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (activityTrackedGroupedByDay instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)activityTrackedGroupedByDay)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)activityTrackedGroupedByDay)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return activityTrackedGroupedByDay;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(activityTrackedGroupedByDay);
        if (object != null) {
            return (ActivityTrackedGroupedByDay)object;
        }
        return ActivityTrackedGroupedByDayRealmProxy.copy(realm, activityTrackedGroupedByDay, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ActivityTrackedGroupedByDay createDetachedCopy(ActivityTrackedGroupedByDay activityTrackedGroupedByDayRealmProxyInterface, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> object) {
        if (n > n2 || activityTrackedGroupedByDayRealmProxyInterface == null) {
            return null;
        }
        Object object2 = (RealmObjectProxy.CacheData)object.get(activityTrackedGroupedByDayRealmProxyInterface);
        if (object2 == null) {
            object2 = new ActivityTrackedGroupedByDay();
            object.put(activityTrackedGroupedByDayRealmProxyInterface, new RealmObjectProxy.CacheData<Object>(n, object2));
            object = object2;
        } else {
            if (n >= ((RealmObjectProxy.CacheData)object2).minDepth) {
                return (ActivityTrackedGroupedByDay)((RealmObjectProxy.CacheData)object2).object;
            }
            object = (ActivityTrackedGroupedByDay)((RealmObjectProxy.CacheData)object2).object;
            ((RealmObjectProxy.CacheData)object2).minDepth = n;
        }
        object2 = (ActivityTrackedGroupedByDayRealmProxyInterface)object;
        object2.realmSet$startTimestamp(activityTrackedGroupedByDayRealmProxyInterface.realmGet$startTimestamp());
        object2.realmSet$endTimestamp(activityTrackedGroupedByDayRealmProxyInterface.realmGet$endTimestamp());
        object2.realmSet$userId(activityTrackedGroupedByDayRealmProxyInterface.realmGet$userId());
        object2.realmSet$totalSteps(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSteps());
        object2.realmSet$totalSecondsOfActivity(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsOfActivity());
        object2.realmSet$totalSecondsWalking(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsWalking());
        object2.realmSet$totalSecondsRunning(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsRunning());
        object2.realmSet$totalSecondsCycling(activityTrackedGroupedByDayRealmProxyInterface.realmGet$totalSecondsCycling());
        object2.realmSet$goalSteps(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalSteps());
        object2.realmSet$goalWalk(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalWalk());
        object2.realmSet$goalRun(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalRun());
        object2.realmSet$goalCycle(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalCycle());
        object2.realmSet$goalActivity(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalActivity());
        object2.realmSet$goalWeight(activityTrackedGroupedByDayRealmProxyInterface.realmGet$goalWeight());
        object2.realmSet$notifyTotalGoal(activityTrackedGroupedByDayRealmProxyInterface.realmGet$notifyTotalGoal());
        object2.realmSet$notifyActivityGoal(activityTrackedGroupedByDayRealmProxyInterface.realmGet$notifyActivityGoal());
        object2.realmSet$notifyStepsGoal(activityTrackedGroupedByDayRealmProxyInterface.realmGet$notifyStepsGoal());
        return object;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("ActivityTrackedGroupedByDay");
        builder.addProperty("startTimestamp", RealmFieldType.INTEGER, false, true, true);
        builder.addProperty("endTimestamp", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("totalSteps", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("totalSecondsOfActivity", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("totalSecondsWalking", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("totalSecondsRunning", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("totalSecondsCycling", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("goalSteps", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("goalWalk", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("goalRun", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("goalCycle", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("goalActivity", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("goalWeight", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("notifyTotalGoal", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("notifyActivityGoal", RealmFieldType.INTEGER, false, false, true);
        builder.addProperty("notifyStepsGoal", RealmFieldType.INTEGER, false, false, true);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_ActivityTrackedGroupedByDay";
    }

    public static ActivityTrackedGroupedByDayColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_ActivityTrackedGroupedByDay")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'ActivityTrackedGroupedByDay' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_ActivityTrackedGroupedByDay");
        long l = table.getColumnCount();
        if (l != 17L) {
            if (l < 17L) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 17 but was " + l);
            }
            if (!bl) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 17 but was " + l);
            }
            RealmLog.debug("Field count is more than expected - expected 17 but was %1$d", l);
        }
        HashMap<String, RealmFieldType> hashMap = new HashMap<String, RealmFieldType>();
        for (long i = 0L; i < l; ++i) {
            hashMap.put(table.getColumnName(i), table.getColumnType(i));
        }
        ActivityTrackedGroupedByDayColumnInfo activityTrackedGroupedByDayColumnInfo = new ActivityTrackedGroupedByDayColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!hashMap.containsKey("startTimestamp")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'startTimestamp' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("startTimestamp") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'startTimestamp' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.startTimestampIndex)) {
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
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.endTimestampIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'endTimestamp' does support null values in the existing Realm file. Use corresponding boxed type for field 'endTimestamp' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("totalSteps")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'totalSteps' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("totalSteps") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'totalSteps' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.totalStepsIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'totalSteps' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalSteps' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("totalSecondsOfActivity")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'totalSecondsOfActivity' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("totalSecondsOfActivity") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'totalSecondsOfActivity' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.totalSecondsOfActivityIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'totalSecondsOfActivity' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalSecondsOfActivity' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("totalSecondsWalking")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'totalSecondsWalking' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("totalSecondsWalking") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'totalSecondsWalking' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.totalSecondsWalkingIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'totalSecondsWalking' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalSecondsWalking' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("totalSecondsRunning")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'totalSecondsRunning' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("totalSecondsRunning") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'totalSecondsRunning' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.totalSecondsRunningIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'totalSecondsRunning' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalSecondsRunning' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("totalSecondsCycling")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'totalSecondsCycling' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("totalSecondsCycling") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'totalSecondsCycling' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.totalSecondsCyclingIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'totalSecondsCycling' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalSecondsCycling' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("goalSteps")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'goalSteps' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("goalSteps") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'goalSteps' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.goalStepsIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'goalSteps' does support null values in the existing Realm file. Use corresponding boxed type for field 'goalSteps' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("goalWalk")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'goalWalk' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("goalWalk") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'goalWalk' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.goalWalkIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'goalWalk' does support null values in the existing Realm file. Use corresponding boxed type for field 'goalWalk' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("goalRun")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'goalRun' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("goalRun") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'goalRun' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.goalRunIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'goalRun' does support null values in the existing Realm file. Use corresponding boxed type for field 'goalRun' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("goalCycle")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'goalCycle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("goalCycle") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'goalCycle' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.goalCycleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'goalCycle' does support null values in the existing Realm file. Use corresponding boxed type for field 'goalCycle' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("goalActivity")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'goalActivity' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("goalActivity") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'goalActivity' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.goalActivityIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'goalActivity' does support null values in the existing Realm file. Use corresponding boxed type for field 'goalActivity' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("goalWeight")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'goalWeight' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("goalWeight") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'goalWeight' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.goalWeightIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'goalWeight' does support null values in the existing Realm file. Use corresponding boxed type for field 'goalWeight' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("notifyTotalGoal")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'notifyTotalGoal' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("notifyTotalGoal") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'notifyTotalGoal' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.notifyTotalGoalIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'notifyTotalGoal' does support null values in the existing Realm file. Use corresponding boxed type for field 'notifyTotalGoal' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("notifyActivityGoal")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'notifyActivityGoal' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("notifyActivityGoal") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'notifyActivityGoal' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.notifyActivityGoalIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'notifyActivityGoal' does support null values in the existing Realm file. Use corresponding boxed type for field 'notifyActivityGoal' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!hashMap.containsKey("notifyStepsGoal")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'notifyStepsGoal' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (hashMap.get("notifyStepsGoal") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'notifyStepsGoal' in existing Realm file.");
        }
        if (table.isColumnNullable(activityTrackedGroupedByDayColumnInfo.notifyStepsGoalIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'notifyStepsGoal' does support null values in the existing Realm file. Use corresponding boxed type for field 'notifyStepsGoal' or migrate using RealmObjectSchema.setNullable().");
        }
        return activityTrackedGroupedByDayColumnInfo;
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
                object = (ActivityTrackedGroupedByDayRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((ActivityTrackedGroupedByDayRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((ActivityTrackedGroupedByDayRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((ActivityTrackedGroupedByDayRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (ActivityTrackedGroupedByDayColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<ActivityTrackedGroupedByDayRealmProxy>(this);
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
    public int realmGet$goalActivity() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.goalActivityIndex);
    }

    @Override
    public int realmGet$goalCycle() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.goalCycleIndex);
    }

    @Override
    public int realmGet$goalRun() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.goalRunIndex);
    }

    @Override
    public int realmGet$goalSteps() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.goalStepsIndex);
    }

    @Override
    public int realmGet$goalWalk() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.goalWalkIndex);
    }

    @Override
    public int realmGet$goalWeight() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.goalWeightIndex);
    }

    @Override
    public int realmGet$notifyActivityGoal() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.notifyActivityGoalIndex);
    }

    @Override
    public int realmGet$notifyStepsGoal() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.notifyStepsGoalIndex);
    }

    @Override
    public int realmGet$notifyTotalGoal() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int)this.proxyState.getRow$realm().getLong(this.columnInfo.notifyTotalGoalIndex);
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
    public long realmGet$totalSecondsCycling() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.totalSecondsCyclingIndex);
    }

    @Override
    public long realmGet$totalSecondsOfActivity() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.totalSecondsOfActivityIndex);
    }

    @Override
    public long realmGet$totalSecondsRunning() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.totalSecondsRunningIndex);
    }

    @Override
    public long realmGet$totalSecondsWalking() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.totalSecondsWalkingIndex);
    }

    @Override
    public long realmGet$totalSteps() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getLong(this.columnInfo.totalStepsIndex);
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
    public void realmSet$goalActivity(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.goalActivityIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.goalActivityIndex, n);
    }

    @Override
    public void realmSet$goalCycle(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.goalCycleIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.goalCycleIndex, n);
    }

    @Override
    public void realmSet$goalRun(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.goalRunIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.goalRunIndex, n);
    }

    @Override
    public void realmSet$goalSteps(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.goalStepsIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.goalStepsIndex, n);
    }

    @Override
    public void realmSet$goalWalk(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.goalWalkIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.goalWalkIndex, n);
    }

    @Override
    public void realmSet$goalWeight(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.goalWeightIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.goalWeightIndex, n);
    }

    @Override
    public void realmSet$notifyActivityGoal(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.notifyActivityGoalIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.notifyActivityGoalIndex, n);
    }

    @Override
    public void realmSet$notifyStepsGoal(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.notifyStepsGoalIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.notifyStepsGoalIndex, n);
    }

    @Override
    public void realmSet$notifyTotalGoal(int n) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.notifyTotalGoalIndex, row.getIndex(), n, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.notifyTotalGoalIndex, n);
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
    public void realmSet$totalSecondsCycling(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.totalSecondsCyclingIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.totalSecondsCyclingIndex, l);
    }

    @Override
    public void realmSet$totalSecondsOfActivity(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.totalSecondsOfActivityIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.totalSecondsOfActivityIndex, l);
    }

    @Override
    public void realmSet$totalSecondsRunning(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.totalSecondsRunningIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.totalSecondsRunningIndex, l);
    }

    @Override
    public void realmSet$totalSecondsWalking(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.totalSecondsWalkingIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.totalSecondsWalkingIndex, l);
    }

    @Override
    public void realmSet$totalSteps(long l) {
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            Row row = this.proxyState.getRow$realm();
            row.getTable().setLong(this.columnInfo.totalStepsIndex, row.getIndex(), l, true);
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.totalStepsIndex, l);
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
        StringBuilder stringBuilder = new StringBuilder("ActivityTrackedGroupedByDay = proxy[");
        stringBuilder.append("{startTimestamp:");
        stringBuilder.append(this.realmGet$startTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{endTimestamp:");
        stringBuilder.append(this.realmGet$endTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{totalSteps:");
        stringBuilder.append(this.realmGet$totalSteps());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{totalSecondsOfActivity:");
        stringBuilder.append(this.realmGet$totalSecondsOfActivity());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{totalSecondsWalking:");
        stringBuilder.append(this.realmGet$totalSecondsWalking());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{totalSecondsRunning:");
        stringBuilder.append(this.realmGet$totalSecondsRunning());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{totalSecondsCycling:");
        stringBuilder.append(this.realmGet$totalSecondsCycling());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goalSteps:");
        stringBuilder.append(this.realmGet$goalSteps());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goalWalk:");
        stringBuilder.append(this.realmGet$goalWalk());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goalRun:");
        stringBuilder.append(this.realmGet$goalRun());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goalCycle:");
        stringBuilder.append(this.realmGet$goalCycle());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goalActivity:");
        stringBuilder.append(this.realmGet$goalActivity());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{goalWeight:");
        stringBuilder.append(this.realmGet$goalWeight());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notifyTotalGoal:");
        stringBuilder.append(this.realmGet$notifyTotalGoal());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notifyActivityGoal:");
        stringBuilder.append(this.realmGet$notifyActivityGoal());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notifyStepsGoal:");
        stringBuilder.append(this.realmGet$notifyStepsGoal());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class ActivityTrackedGroupedByDayColumnInfo
    extends ColumnInfo {
        long endTimestampIndex;
        long goalActivityIndex;
        long goalCycleIndex;
        long goalRunIndex;
        long goalStepsIndex;
        long goalWalkIndex;
        long goalWeightIndex;
        long notifyActivityGoalIndex;
        long notifyStepsGoalIndex;
        long notifyTotalGoalIndex;
        long startTimestampIndex;
        long totalSecondsCyclingIndex;
        long totalSecondsOfActivityIndex;
        long totalSecondsRunningIndex;
        long totalSecondsWalkingIndex;
        long totalStepsIndex;
        long userIdIndex;

        ActivityTrackedGroupedByDayColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        ActivityTrackedGroupedByDayColumnInfo(SharedRealm sharedRealm, Table table) {
            super(17);
            this.startTimestampIndex = this.addColumnDetails(table, "startTimestamp", RealmFieldType.INTEGER);
            this.endTimestampIndex = this.addColumnDetails(table, "endTimestamp", RealmFieldType.INTEGER);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.totalStepsIndex = this.addColumnDetails(table, "totalSteps", RealmFieldType.INTEGER);
            this.totalSecondsOfActivityIndex = this.addColumnDetails(table, "totalSecondsOfActivity", RealmFieldType.INTEGER);
            this.totalSecondsWalkingIndex = this.addColumnDetails(table, "totalSecondsWalking", RealmFieldType.INTEGER);
            this.totalSecondsRunningIndex = this.addColumnDetails(table, "totalSecondsRunning", RealmFieldType.INTEGER);
            this.totalSecondsCyclingIndex = this.addColumnDetails(table, "totalSecondsCycling", RealmFieldType.INTEGER);
            this.goalStepsIndex = this.addColumnDetails(table, "goalSteps", RealmFieldType.INTEGER);
            this.goalWalkIndex = this.addColumnDetails(table, "goalWalk", RealmFieldType.INTEGER);
            this.goalRunIndex = this.addColumnDetails(table, "goalRun", RealmFieldType.INTEGER);
            this.goalCycleIndex = this.addColumnDetails(table, "goalCycle", RealmFieldType.INTEGER);
            this.goalActivityIndex = this.addColumnDetails(table, "goalActivity", RealmFieldType.INTEGER);
            this.goalWeightIndex = this.addColumnDetails(table, "goalWeight", RealmFieldType.INTEGER);
            this.notifyTotalGoalIndex = this.addColumnDetails(table, "notifyTotalGoal", RealmFieldType.INTEGER);
            this.notifyActivityGoalIndex = this.addColumnDetails(table, "notifyActivityGoal", RealmFieldType.INTEGER);
            this.notifyStepsGoalIndex = this.addColumnDetails(table, "notifyStepsGoal", RealmFieldType.INTEGER);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new ActivityTrackedGroupedByDayColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (ActivityTrackedGroupedByDayColumnInfo)columnInfo;
            columnInfo2 = (ActivityTrackedGroupedByDayColumnInfo)columnInfo2;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).startTimestampIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).startTimestampIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).endTimestampIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).endTimestampIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).userIdIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).userIdIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).totalStepsIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).totalStepsIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).totalSecondsOfActivityIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).totalSecondsOfActivityIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).totalSecondsWalkingIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).totalSecondsWalkingIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).totalSecondsRunningIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).totalSecondsRunningIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).totalSecondsCyclingIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).totalSecondsCyclingIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).goalStepsIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).goalStepsIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).goalWalkIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).goalWalkIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).goalRunIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).goalRunIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).goalCycleIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).goalCycleIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).goalActivityIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).goalActivityIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).goalWeightIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).goalWeightIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).notifyTotalGoalIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).notifyTotalGoalIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).notifyActivityGoalIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).notifyActivityGoalIndex;
            ((ActivityTrackedGroupedByDayColumnInfo)columnInfo2).notifyStepsGoalIndex = ((ActivityTrackedGroupedByDayColumnInfo)columnInfo).notifyStepsGoalIndex;
        }
    }

}

