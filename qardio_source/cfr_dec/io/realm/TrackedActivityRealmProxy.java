/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedActivity;
import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedDetectedActivity;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByHour;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByMinute;
import io.realm.ActivityTrackedGroupedByDayRealmProxy;
import io.realm.ActivityTrackedGroupedByHourRealmProxy;
import io.realm.ActivityTrackedGroupedByMinuteRealmProxy;
import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.TrackedActivityRealmProxyInterface;
import io.realm.TrackedDetectedActivityRealmProxy;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TrackedActivityRealmProxy
extends TrackedActivity
implements TrackedActivityRealmProxyInterface,
RealmObjectProxy {
    private static final List<String> FIELD_NAMES;
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo;
    private RealmList<TrackedDetectedActivity> bufferRealmList;
    private TrackedActivityColumnInfo columnInfo;
    private RealmList<ActivityTrackedGroupedByDay> daysRealmList;
    private RealmList<ActivityTrackedGroupedByHour> hoursRealmList;
    private RealmList<ActivityTrackedGroupedByMinute> minutesRealmList;
    private ProxyState<TrackedActivity> proxyState;

    static {
        expectedObjectSchemaInfo = TrackedActivityRealmProxy.createExpectedObjectSchemaInfo();
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("userId");
        arrayList.add("buffer");
        arrayList.add("minutes");
        arrayList.add("hours");
        arrayList.add("days");
        FIELD_NAMES = Collections.unmodifiableList(arrayList);
    }

    TrackedActivityRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static TrackedActivity copy(Realm realm, TrackedActivity realmList, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        int n;
        Object object;
        RealmObjectProxy realmObjectProxy = map.get(realmList);
        if (realmObjectProxy != null) {
            return (TrackedActivity)((Object)realmObjectProxy);
        }
        TrackedActivity trackedActivity = realm.createObjectInternal(TrackedActivity.class, false, Collections.<String>emptyList());
        map.put((RealmModel)((Object)realmList), (RealmObjectProxy)((Object)trackedActivity));
        Object object2 = (TrackedActivityRealmProxyInterface)((Object)realmList);
        realmList = trackedActivity;
        realmList.realmSet$userId(object2.realmGet$userId());
        Object object3 = object2.realmGet$buffer();
        if (object3 != null) {
            object = realmList.realmGet$buffer();
            for (n = 0; n < ((RealmList)object3).size(); ++n) {
                TrackedDetectedActivity trackedDetectedActivity = (TrackedDetectedActivity)((RealmList)object3).get(n);
                TrackedDetectedActivity trackedDetectedActivity2 = (TrackedDetectedActivity)((Object)map.get(trackedDetectedActivity));
                if (trackedDetectedActivity2 != null) {
                    ((RealmList)object).add((TrackedDetectedActivity)trackedDetectedActivity2);
                    continue;
                }
                ((RealmList)object).add((RealmObject)TrackedDetectedActivityRealmProxy.copyOrUpdate(realm, trackedDetectedActivity, bl, map));
            }
        }
        if ((object3 = object2.realmGet$minutes()) != null) {
            object = realmList.realmGet$minutes();
            for (n = 0; n < ((RealmList)object3).size(); ++n) {
                ActivityTrackedGroupedByMinute activityTrackedGroupedByMinute = (ActivityTrackedGroupedByMinute)((RealmList)object3).get(n);
                ActivityTrackedGroupedByMinute activityTrackedGroupedByMinute2 = (ActivityTrackedGroupedByMinute)((Object)map.get(activityTrackedGroupedByMinute));
                if (activityTrackedGroupedByMinute2 != null) {
                    ((RealmList)object).add((RealmObject)activityTrackedGroupedByMinute2);
                    continue;
                }
                ((RealmList)object).add((RealmObject)ActivityTrackedGroupedByMinuteRealmProxy.copyOrUpdate(realm, activityTrackedGroupedByMinute, bl, map));
            }
        }
        if ((object3 = object2.realmGet$hours()) != null) {
            object = realmList.realmGet$hours();
            for (n = 0; n < ((RealmList)object3).size(); ++n) {
                ActivityTrackedGroupedByHour activityTrackedGroupedByHour = (ActivityTrackedGroupedByHour)((RealmList)object3).get(n);
                ActivityTrackedGroupedByHour activityTrackedGroupedByHour2 = (ActivityTrackedGroupedByHour)((Object)map.get(activityTrackedGroupedByHour));
                if (activityTrackedGroupedByHour2 != null) {
                    ((RealmList)object).add((RealmObject)activityTrackedGroupedByHour2);
                    continue;
                }
                ((RealmList)object).add((RealmObject)ActivityTrackedGroupedByHourRealmProxy.copyOrUpdate(realm, activityTrackedGroupedByHour, bl, map));
            }
        }
        if ((object2 = object2.realmGet$days()) != null) {
            realmList = realmList.realmGet$days();
            for (n = 0; n < ((RealmList)object2).size(); ++n) {
                object3 = (ActivityTrackedGroupedByDay)((RealmList)object2).get(n);
                object = (ActivityTrackedGroupedByDay)((Object)map.get(object3));
                if (object != null) {
                    realmList.add((ActivityTrackedGroupedByDay)object);
                    continue;
                }
                realmList.add(ActivityTrackedGroupedByDayRealmProxy.copyOrUpdate(realm, (ActivityTrackedGroupedByDay)object3, bl, map));
            }
        }
        return trackedActivity;
    }

    public static TrackedActivity copyOrUpdate(Realm realm, TrackedActivity trackedActivity, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        if (trackedActivity instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)trackedActivity)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)trackedActivity).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (trackedActivity instanceof RealmObjectProxy && ((RealmObjectProxy)((Object)trackedActivity)).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)((Object)trackedActivity)).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return trackedActivity;
        }
        Object object = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        object = map.get(trackedActivity);
        if (object != null) {
            return (TrackedActivity)object;
        }
        return TrackedActivityRealmProxy.copy(realm, trackedActivity, bl, map);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static TrackedActivity createDetachedCopy(TrackedActivity realmList, int n, int n2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        TrackedActivity trackedActivity;
        int n3;
        RealmList<TrackedDetectedActivity> realmList2;
        RealmList<RealmObject> realmList3;
        int n4;
        if (n > n2 || realmList == null) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(realmList);
        if (cacheData == null) {
            trackedActivity = new TrackedActivity();
            map.put((RealmModel)((Object)realmList), (RealmObjectProxy.CacheData<RealmModel>)new RealmObjectProxy.CacheData<TrackedActivity>(n, trackedActivity));
        } else {
            if (n >= cacheData.minDepth) {
                return (TrackedActivity)cacheData.object;
            }
            trackedActivity = (TrackedActivity)cacheData.object;
            cacheData.minDepth = n;
        }
        cacheData = trackedActivity;
        realmList = (TrackedActivityRealmProxyInterface)((Object)realmList);
        cacheData.realmSet$userId(realmList.realmGet$userId());
        if (n == n2) {
            cacheData.realmSet$buffer(null);
        } else {
            realmList3 = realmList.realmGet$buffer();
            realmList2 = new RealmList<TrackedDetectedActivity>();
            cacheData.realmSet$buffer(realmList2);
            n4 = realmList3.size();
            for (n3 = 0; n3 < n4; ++n3) {
                realmList2.add(TrackedDetectedActivityRealmProxy.createDetachedCopy((TrackedDetectedActivity)realmList3.get(n3), n + 1, n2, map));
            }
        }
        if (n == n2) {
            cacheData.realmSet$minutes(null);
        } else {
            realmList3 = realmList.realmGet$minutes();
            realmList2 = new RealmList();
            cacheData.realmSet$minutes(realmList2);
            n4 = realmList3.size();
            for (n3 = 0; n3 < n4; ++n3) {
                realmList2.add((TrackedDetectedActivity)((Object)ActivityTrackedGroupedByMinuteRealmProxy.createDetachedCopy((ActivityTrackedGroupedByMinute)realmList3.get(n3), n + 1, n2, map)));
            }
        }
        if (n == n2) {
            cacheData.realmSet$hours(null);
        } else {
            realmList3 = realmList.realmGet$hours();
            realmList2 = new RealmList();
            cacheData.realmSet$hours(realmList2);
            n4 = realmList3.size();
            for (n3 = 0; n3 < n4; ++n3) {
                realmList2.add((TrackedDetectedActivity)((Object)ActivityTrackedGroupedByHourRealmProxy.createDetachedCopy((ActivityTrackedGroupedByHour)realmList3.get(n3), n + 1, n2, map)));
            }
        }
        if (n == n2) {
            cacheData.realmSet$days(null);
            return trackedActivity;
        } else {
            realmList = realmList.realmGet$days();
            realmList3 = new RealmList();
            cacheData.realmSet$days(realmList3);
            n4 = realmList.size();
            for (n3 = 0; n3 < n4; ++n3) {
                realmList3.add(ActivityTrackedGroupedByDayRealmProxy.createDetachedCopy((ActivityTrackedGroupedByDay)realmList.get(n3), n + 1, n2, map));
            }
        }
        return trackedActivity;
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("TrackedActivity");
        builder.addProperty("userId", RealmFieldType.INTEGER, false, false, true);
        builder.addLinkedProperty("buffer", RealmFieldType.LIST, "TrackedDetectedActivity");
        builder.addLinkedProperty("minutes", RealmFieldType.LIST, "ActivityTrackedGroupedByMinute");
        builder.addLinkedProperty("hours", RealmFieldType.LIST, "ActivityTrackedGroupedByHour");
        builder.addLinkedProperty("days", RealmFieldType.LIST, "ActivityTrackedGroupedByDay");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static String getTableName() {
        return "class_TrackedActivity";
    }

    public static TrackedActivityColumnInfo validateTable(SharedRealm sharedRealm, boolean bl) {
        if (!sharedRealm.hasTable("class_TrackedActivity")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'TrackedActivity' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_TrackedActivity");
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
        TrackedActivityColumnInfo trackedActivityColumnInfo = new TrackedActivityColumnInfo(sharedRealm, table);
        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }
        if (!object.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (object.get("userId") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'userId' in existing Realm file.");
        }
        if (table.isColumnNullable(trackedActivityColumnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' does support null values in the existing Realm file. Use corresponding boxed type for field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!object.containsKey("buffer")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'buffer'");
        }
        if (object.get("buffer") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'TrackedDetectedActivity' for field 'buffer'");
        }
        if (!sharedRealm.hasTable("class_TrackedDetectedActivity")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_TrackedDetectedActivity' for field 'buffer'");
        }
        Table table2 = sharedRealm.getTable("class_TrackedDetectedActivity");
        if (!table.getLinkTarget(trackedActivityColumnInfo.bufferIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'buffer': '" + table.getLinkTarget(trackedActivityColumnInfo.bufferIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!object.containsKey("minutes")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'minutes'");
        }
        if (object.get("minutes") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'ActivityTrackedGroupedByMinute' for field 'minutes'");
        }
        if (!sharedRealm.hasTable("class_ActivityTrackedGroupedByMinute")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_ActivityTrackedGroupedByMinute' for field 'minutes'");
        }
        table2 = sharedRealm.getTable("class_ActivityTrackedGroupedByMinute");
        if (!table.getLinkTarget(trackedActivityColumnInfo.minutesIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'minutes': '" + table.getLinkTarget(trackedActivityColumnInfo.minutesIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!object.containsKey("hours")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'hours'");
        }
        if (object.get("hours") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'ActivityTrackedGroupedByHour' for field 'hours'");
        }
        if (!sharedRealm.hasTable("class_ActivityTrackedGroupedByHour")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_ActivityTrackedGroupedByHour' for field 'hours'");
        }
        table2 = sharedRealm.getTable("class_ActivityTrackedGroupedByHour");
        if (!table.getLinkTarget(trackedActivityColumnInfo.hoursIndex).hasSameSchema(table2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'hours': '" + table.getLinkTarget(trackedActivityColumnInfo.hoursIndex).getName() + "' expected - was '" + table2.getName() + "'");
        }
        if (!object.containsKey("days")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'days'");
        }
        if (object.get("days") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'ActivityTrackedGroupedByDay' for field 'days'");
        }
        if (!sharedRealm.hasTable("class_ActivityTrackedGroupedByDay")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_ActivityTrackedGroupedByDay' for field 'days'");
        }
        object = sharedRealm.getTable("class_ActivityTrackedGroupedByDay");
        if (!table.getLinkTarget(trackedActivityColumnInfo.daysIndex).hasSameSchema((Table)object)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'days': '" + table.getLinkTarget(trackedActivityColumnInfo.daysIndex).getName() + "' expected - was '" + ((Table)object).getName() + "'");
        }
        return trackedActivityColumnInfo;
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
                object = (TrackedActivityRealmProxy)object;
                String string2 = this.proxyState.getRealm$realm().getPath();
                String string3 = ((TrackedActivityRealmProxy)object).proxyState.getRealm$realm().getPath();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                string2 = this.proxyState.getRow$realm().getTable().getName();
                string3 = ((TrackedActivityRealmProxy)object).proxyState.getRow$realm().getTable().getName();
                if (string2 != null ? !string2.equals(string3) : string3 != null) {
                    return false;
                }
                if (this.proxyState.getRow$realm().getIndex() != ((TrackedActivityRealmProxy)object).proxyState.getRow$realm().getIndex()) break block7;
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
        this.columnInfo = (TrackedActivityColumnInfo)realmObjectContext.getColumnInfo();
        this.proxyState = new ProxyState<TrackedActivityRealmProxy>(this);
        this.proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override
    public RealmList<TrackedDetectedActivity> realmGet$buffer() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.bufferRealmList != null) {
            return this.bufferRealmList;
        }
        this.bufferRealmList = new RealmList<TrackedDetectedActivity>(TrackedDetectedActivity.class, this.proxyState.getRow$realm().getLinkList(this.columnInfo.bufferIndex), this.proxyState.getRealm$realm());
        return this.bufferRealmList;
    }

    @Override
    public RealmList<ActivityTrackedGroupedByDay> realmGet$days() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.daysRealmList != null) {
            return this.daysRealmList;
        }
        this.daysRealmList = new RealmList<ActivityTrackedGroupedByDay>(ActivityTrackedGroupedByDay.class, this.proxyState.getRow$realm().getLinkList(this.columnInfo.daysIndex), this.proxyState.getRealm$realm());
        return this.daysRealmList;
    }

    @Override
    public RealmList<ActivityTrackedGroupedByHour> realmGet$hours() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.hoursRealmList != null) {
            return this.hoursRealmList;
        }
        this.hoursRealmList = new RealmList<ActivityTrackedGroupedByHour>(ActivityTrackedGroupedByHour.class, this.proxyState.getRow$realm().getLinkList(this.columnInfo.hoursIndex), this.proxyState.getRealm$realm());
        return this.hoursRealmList;
    }

    @Override
    public RealmList<ActivityTrackedGroupedByMinute> realmGet$minutes() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (this.minutesRealmList != null) {
            return this.minutesRealmList;
        }
        this.minutesRealmList = new RealmList<ActivityTrackedGroupedByMinute>(ActivityTrackedGroupedByMinute.class, this.proxyState.getRow$realm().getLinkList(this.columnInfo.minutesIndex), this.proxyState.getRealm$realm());
        return this.minutesRealmList;
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

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void realmSet$buffer(RealmList<TrackedDetectedActivity> iterator) {
        Object object;
        Object object2 = iterator;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (this.proxyState.getExcludeFields$realm().contains("buffer")) return;
            object2 = iterator;
            if (iterator != null) {
                object2 = iterator;
                if (!((RealmList)((Object)iterator)).isManaged()) {
                    Realm realm = (Realm)this.proxyState.getRealm$realm();
                    object = new RealmList();
                    iterator = ((RealmList)((Object)iterator)).iterator();
                    do {
                        object2 = object;
                        if (!iterator.hasNext()) break;
                        object2 = (TrackedDetectedActivity)iterator.next();
                        if (object2 == null || RealmObject.isManaged(object2)) {
                            ((RealmList)object).add(object2);
                            continue;
                        }
                        ((RealmList)object).add(realm.copyToRealm(object2));
                    } while (true);
                }
            }
        }
        this.proxyState.getRealm$realm().checkIfValid();
        iterator = this.proxyState.getRow$realm().getLinkList(this.columnInfo.bufferIndex);
        ((LinkView)((Object)iterator)).clear();
        if (object2 == null) return;
        object2 = ((RealmList)object2).iterator();
        while (object2.hasNext()) {
            object = (RealmModel)object2.next();
            if (!RealmObject.isManaged(object)) throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            if (!RealmObject.isValid(object)) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            ((LinkView)((Object)iterator)).add(((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void realmSet$days(RealmList<ActivityTrackedGroupedByDay> iterator) {
        Object object;
        Object object2 = iterator;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (this.proxyState.getExcludeFields$realm().contains("days")) return;
            object2 = iterator;
            if (iterator != null) {
                object2 = iterator;
                if (!((RealmList)((Object)iterator)).isManaged()) {
                    Realm realm = (Realm)this.proxyState.getRealm$realm();
                    object = new RealmList();
                    iterator = ((RealmList)((Object)iterator)).iterator();
                    do {
                        object2 = object;
                        if (!iterator.hasNext()) break;
                        object2 = (ActivityTrackedGroupedByDay)iterator.next();
                        if (object2 == null || RealmObject.isManaged(object2)) {
                            ((RealmList)object).add(object2);
                            continue;
                        }
                        ((RealmList)object).add(realm.copyToRealm(object2));
                    } while (true);
                }
            }
        }
        this.proxyState.getRealm$realm().checkIfValid();
        iterator = this.proxyState.getRow$realm().getLinkList(this.columnInfo.daysIndex);
        ((LinkView)((Object)iterator)).clear();
        if (object2 == null) return;
        object2 = ((RealmList)object2).iterator();
        while (object2.hasNext()) {
            object = (RealmModel)object2.next();
            if (!RealmObject.isManaged(object)) throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            if (!RealmObject.isValid(object)) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            ((LinkView)((Object)iterator)).add(((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void realmSet$hours(RealmList<ActivityTrackedGroupedByHour> iterator) {
        Object object;
        Object object2 = iterator;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (this.proxyState.getExcludeFields$realm().contains("hours")) return;
            object2 = iterator;
            if (iterator != null) {
                object2 = iterator;
                if (!((RealmList)((Object)iterator)).isManaged()) {
                    Realm realm = (Realm)this.proxyState.getRealm$realm();
                    object = new RealmList();
                    iterator = ((RealmList)((Object)iterator)).iterator();
                    do {
                        object2 = object;
                        if (!iterator.hasNext()) break;
                        object2 = (ActivityTrackedGroupedByHour)iterator.next();
                        if (object2 == null || RealmObject.isManaged(object2)) {
                            ((RealmList)object).add(object2);
                            continue;
                        }
                        ((RealmList)object).add(realm.copyToRealm(object2));
                    } while (true);
                }
            }
        }
        this.proxyState.getRealm$realm().checkIfValid();
        iterator = this.proxyState.getRow$realm().getLinkList(this.columnInfo.hoursIndex);
        ((LinkView)((Object)iterator)).clear();
        if (object2 == null) return;
        object2 = ((RealmList)object2).iterator();
        while (object2.hasNext()) {
            object = (RealmModel)object2.next();
            if (!RealmObject.isManaged(object)) throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            if (!RealmObject.isValid(object)) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            ((LinkView)((Object)iterator)).add(((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void realmSet$minutes(RealmList<ActivityTrackedGroupedByMinute> iterator) {
        Object object;
        Object object2 = iterator;
        if (this.proxyState.isUnderConstruction()) {
            if (!this.proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (this.proxyState.getExcludeFields$realm().contains("minutes")) return;
            object2 = iterator;
            if (iterator != null) {
                object2 = iterator;
                if (!((RealmList)((Object)iterator)).isManaged()) {
                    Realm realm = (Realm)this.proxyState.getRealm$realm();
                    object = new RealmList();
                    iterator = ((RealmList)((Object)iterator)).iterator();
                    do {
                        object2 = object;
                        if (!iterator.hasNext()) break;
                        object2 = (ActivityTrackedGroupedByMinute)iterator.next();
                        if (object2 == null || RealmObject.isManaged(object2)) {
                            ((RealmList)object).add(object2);
                            continue;
                        }
                        ((RealmList)object).add(realm.copyToRealm(object2));
                    } while (true);
                }
            }
        }
        this.proxyState.getRealm$realm().checkIfValid();
        iterator = this.proxyState.getRow$realm().getLinkList(this.columnInfo.minutesIndex);
        ((LinkView)((Object)iterator)).clear();
        if (object2 == null) return;
        object2 = ((RealmList)object2).iterator();
        while (object2.hasNext()) {
            object = (RealmModel)object2.next();
            if (!RealmObject.isManaged(object)) throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            if (!RealmObject.isValid(object)) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != this.proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            ((LinkView)((Object)iterator)).add(((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
        }
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
        StringBuilder stringBuilder = new StringBuilder("TrackedActivity = proxy[");
        stringBuilder.append("{userId:");
        stringBuilder.append(this.realmGet$userId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{buffer:");
        stringBuilder.append("RealmList<TrackedDetectedActivity>[").append(this.realmGet$buffer().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{minutes:");
        stringBuilder.append("RealmList<ActivityTrackedGroupedByMinute>[").append(this.realmGet$minutes().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{hours:");
        stringBuilder.append("RealmList<ActivityTrackedGroupedByHour>[").append(this.realmGet$hours().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{days:");
        stringBuilder.append("RealmList<ActivityTrackedGroupedByDay>[").append(this.realmGet$days().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static final class TrackedActivityColumnInfo
    extends ColumnInfo {
        long bufferIndex;
        long daysIndex;
        long hoursIndex;
        long minutesIndex;
        long userIdIndex;

        TrackedActivityColumnInfo(ColumnInfo columnInfo, boolean bl) {
            super(columnInfo, bl);
            this.copy(columnInfo, this);
        }

        TrackedActivityColumnInfo(SharedRealm sharedRealm, Table table) {
            super(5);
            this.userIdIndex = this.addColumnDetails(table, "userId", RealmFieldType.INTEGER);
            this.bufferIndex = this.addColumnDetails(table, "buffer", RealmFieldType.LIST);
            this.minutesIndex = this.addColumnDetails(table, "minutes", RealmFieldType.LIST);
            this.hoursIndex = this.addColumnDetails(table, "hours", RealmFieldType.LIST);
            this.daysIndex = this.addColumnDetails(table, "days", RealmFieldType.LIST);
        }

        @Override
        protected final ColumnInfo copy(boolean bl) {
            return new TrackedActivityColumnInfo(this, bl);
        }

        @Override
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            columnInfo = (TrackedActivityColumnInfo)columnInfo;
            columnInfo2 = (TrackedActivityColumnInfo)columnInfo2;
            ((TrackedActivityColumnInfo)columnInfo2).userIdIndex = ((TrackedActivityColumnInfo)columnInfo).userIdIndex;
            ((TrackedActivityColumnInfo)columnInfo2).bufferIndex = ((TrackedActivityColumnInfo)columnInfo).bufferIndex;
            ((TrackedActivityColumnInfo)columnInfo2).minutesIndex = ((TrackedActivityColumnInfo)columnInfo).minutesIndex;
            ((TrackedActivityColumnInfo)columnInfo2).hoursIndex = ((TrackedActivityColumnInfo)columnInfo).hoursIndex;
            ((TrackedActivityColumnInfo)columnInfo2).daysIndex = ((TrackedActivityColumnInfo)columnInfo).daysIndex;
        }
    }

}

