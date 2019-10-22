/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedActivity;
import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedDetectedActivity;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByHour;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByMinute;
import com.getqardio.android.mvp.common.local.model.User;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;
import io.realm.ActivityTrackedGroupedByDayRealmProxy;
import io.realm.ActivityTrackedGroupedByHourRealmProxy;
import io.realm.ActivityTrackedGroupedByMinuteRealmProxy;
import io.realm.BaseRealm;
import io.realm.BpLastMeasurementRealmProxy;
import io.realm.FollowMeUserRealmProxy;
import io.realm.IFollowUserRealmProxy;
import io.realm.LastBpMeasurementDataRealmProxy;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.TrackedActivityRealmProxy;
import io.realm.TrackedDetectedActivityRealmProxy;
import io.realm.UserRealmProxy;
import io.realm.WeightExtraInfoRealmProxy;
import io.realm.WeightLastMeasurementRealmProxy;
import io.realm.annotations.RealmModule;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RealmModule
class DefaultRealmModuleMediator
extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;

    static {
        HashSet<Class> hashSet = new HashSet<Class>();
        hashSet.add(ActivityTrackedGroupedByHour.class);
        hashSet.add(WeightLastMeasurement.class);
        hashSet.add(TrackedDetectedActivity.class);
        hashSet.add(ActivityTrackedGroupedByDay.class);
        hashSet.add(LastBpMeasurementData.class);
        hashSet.add(IFollowUser.class);
        hashSet.add(FollowMeUser.class);
        hashSet.add(WeightExtraInfo.class);
        hashSet.add(ActivityTrackedGroupedByMinute.class);
        hashSet.add(User.class);
        hashSet.add(BpLastMeasurement.class);
        hashSet.add(TrackedActivity.class);
        MODEL_CLASSES = Collections.unmodifiableSet(hashSet);
    }

    DefaultRealmModuleMediator() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean bl, Map<RealmModel, RealmObjectProxy> map) {
        Class<?> class_ = e instanceof RealmObjectProxy ? e.getClass().getSuperclass() : e.getClass();
        if (class_.equals(ActivityTrackedGroupedByHour.class)) {
            return (E)((RealmModel)class_.cast(ActivityTrackedGroupedByHourRealmProxy.copyOrUpdate(realm, (ActivityTrackedGroupedByHour)e, bl, map)));
        }
        if (class_.equals(WeightLastMeasurement.class)) {
            return (E)((RealmModel)class_.cast(WeightLastMeasurementRealmProxy.copyOrUpdate(realm, (WeightLastMeasurement)e, bl, map)));
        }
        if (class_.equals(TrackedDetectedActivity.class)) {
            return (E)((RealmModel)class_.cast(TrackedDetectedActivityRealmProxy.copyOrUpdate(realm, (TrackedDetectedActivity)e, bl, map)));
        }
        if (class_.equals(ActivityTrackedGroupedByDay.class)) {
            return (E)((RealmModel)class_.cast(ActivityTrackedGroupedByDayRealmProxy.copyOrUpdate(realm, (ActivityTrackedGroupedByDay)e, bl, map)));
        }
        if (class_.equals(LastBpMeasurementData.class)) {
            return (E)((RealmModel)class_.cast(LastBpMeasurementDataRealmProxy.copyOrUpdate(realm, (LastBpMeasurementData)e, bl, map)));
        }
        if (class_.equals(IFollowUser.class)) {
            return (E)((RealmModel)class_.cast(IFollowUserRealmProxy.copyOrUpdate(realm, (IFollowUser)e, bl, map)));
        }
        if (class_.equals(FollowMeUser.class)) {
            return (E)((RealmModel)class_.cast(FollowMeUserRealmProxy.copyOrUpdate(realm, (FollowMeUser)e, bl, map)));
        }
        if (class_.equals(WeightExtraInfo.class)) {
            return (E)((RealmModel)class_.cast(WeightExtraInfoRealmProxy.copyOrUpdate(realm, (WeightExtraInfo)e, bl, map)));
        }
        if (class_.equals(ActivityTrackedGroupedByMinute.class)) {
            return (E)((RealmModel)class_.cast(ActivityTrackedGroupedByMinuteRealmProxy.copyOrUpdate(realm, (ActivityTrackedGroupedByMinute)e, bl, map)));
        }
        if (class_.equals(User.class)) {
            return (E)((RealmModel)class_.cast(UserRealmProxy.copyOrUpdate(realm, (User)e, bl, map)));
        }
        if (class_.equals(BpLastMeasurement.class)) {
            return (E)((RealmModel)class_.cast(BpLastMeasurementRealmProxy.copyOrUpdate(realm, (BpLastMeasurement)e, bl, map)));
        }
        if (class_.equals(TrackedActivity.class)) {
            return (E)((RealmModel)class_.cast(TrackedActivityRealmProxy.copyOrUpdate(realm, (TrackedActivity)e, bl, map)));
        }
        throw DefaultRealmModuleMediator.getMissingProxyClassException(class_);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E e, int n, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<?> class_ = e.getClass().getSuperclass();
        if (class_.equals(ActivityTrackedGroupedByHour.class)) {
            return (E)((RealmModel)class_.cast(ActivityTrackedGroupedByHourRealmProxy.createDetachedCopy((ActivityTrackedGroupedByHour)e, 0, n, map)));
        }
        if (class_.equals(WeightLastMeasurement.class)) {
            return (E)((RealmModel)class_.cast(WeightLastMeasurementRealmProxy.createDetachedCopy((WeightLastMeasurement)e, 0, n, map)));
        }
        if (class_.equals(TrackedDetectedActivity.class)) {
            return (E)((RealmModel)class_.cast(TrackedDetectedActivityRealmProxy.createDetachedCopy((TrackedDetectedActivity)e, 0, n, map)));
        }
        if (class_.equals(ActivityTrackedGroupedByDay.class)) {
            return (E)((RealmModel)class_.cast(ActivityTrackedGroupedByDayRealmProxy.createDetachedCopy((ActivityTrackedGroupedByDay)e, 0, n, map)));
        }
        if (class_.equals(LastBpMeasurementData.class)) {
            return (E)((RealmModel)class_.cast(LastBpMeasurementDataRealmProxy.createDetachedCopy((LastBpMeasurementData)e, 0, n, map)));
        }
        if (class_.equals(IFollowUser.class)) {
            return (E)((RealmModel)class_.cast(IFollowUserRealmProxy.createDetachedCopy((IFollowUser)e, 0, n, map)));
        }
        if (class_.equals(FollowMeUser.class)) {
            return (E)((RealmModel)class_.cast(FollowMeUserRealmProxy.createDetachedCopy((FollowMeUser)e, 0, n, map)));
        }
        if (class_.equals(WeightExtraInfo.class)) {
            return (E)((RealmModel)class_.cast(WeightExtraInfoRealmProxy.createDetachedCopy((WeightExtraInfo)e, 0, n, map)));
        }
        if (class_.equals(ActivityTrackedGroupedByMinute.class)) {
            return (E)((RealmModel)class_.cast(ActivityTrackedGroupedByMinuteRealmProxy.createDetachedCopy((ActivityTrackedGroupedByMinute)e, 0, n, map)));
        }
        if (class_.equals(User.class)) {
            return (E)((RealmModel)class_.cast(UserRealmProxy.createDetachedCopy((User)e, 0, n, map)));
        }
        if (class_.equals(BpLastMeasurement.class)) {
            return (E)((RealmModel)class_.cast(BpLastMeasurementRealmProxy.createDetachedCopy((BpLastMeasurement)e, 0, n, map)));
        }
        if (class_.equals(TrackedActivity.class)) {
            return (E)((RealmModel)class_.cast(TrackedActivityRealmProxy.createDetachedCopy((TrackedActivity)e, 0, n, map)));
        }
        throw DefaultRealmModuleMediator.getMissingProxyClassException(class_);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo> hashMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>();
        hashMap.put(ActivityTrackedGroupedByHour.class, ActivityTrackedGroupedByHourRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(WeightLastMeasurement.class, WeightLastMeasurementRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(TrackedDetectedActivity.class, TrackedDetectedActivityRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(ActivityTrackedGroupedByDay.class, ActivityTrackedGroupedByDayRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(LastBpMeasurementData.class, LastBpMeasurementDataRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(IFollowUser.class, IFollowUserRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(FollowMeUser.class, FollowMeUserRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(WeightExtraInfo.class, WeightExtraInfoRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(ActivityTrackedGroupedByMinute.class, ActivityTrackedGroupedByMinuteRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(User.class, UserRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(BpLastMeasurement.class, BpLastMeasurementRealmProxy.getExpectedObjectSchemaInfo());
        hashMap.put(TrackedActivity.class, TrackedActivityRealmProxy.getExpectedObjectSchemaInfo());
        return hashMap;
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public String getTableName(Class<? extends RealmModel> class_) {
        DefaultRealmModuleMediator.checkClass(class_);
        if (class_.equals(ActivityTrackedGroupedByHour.class)) {
            return ActivityTrackedGroupedByHourRealmProxy.getTableName();
        }
        if (class_.equals(WeightLastMeasurement.class)) {
            return WeightLastMeasurementRealmProxy.getTableName();
        }
        if (class_.equals(TrackedDetectedActivity.class)) {
            return TrackedDetectedActivityRealmProxy.getTableName();
        }
        if (class_.equals(ActivityTrackedGroupedByDay.class)) {
            return ActivityTrackedGroupedByDayRealmProxy.getTableName();
        }
        if (class_.equals(LastBpMeasurementData.class)) {
            return LastBpMeasurementDataRealmProxy.getTableName();
        }
        if (class_.equals(IFollowUser.class)) {
            return IFollowUserRealmProxy.getTableName();
        }
        if (class_.equals(FollowMeUser.class)) {
            return FollowMeUserRealmProxy.getTableName();
        }
        if (class_.equals(WeightExtraInfo.class)) {
            return WeightExtraInfoRealmProxy.getTableName();
        }
        if (class_.equals(ActivityTrackedGroupedByMinute.class)) {
            return ActivityTrackedGroupedByMinuteRealmProxy.getTableName();
        }
        if (class_.equals(User.class)) {
            return UserRealmProxy.getTableName();
        }
        if (class_.equals(BpLastMeasurement.class)) {
            return BpLastMeasurementRealmProxy.getTableName();
        }
        if (class_.equals(TrackedActivity.class)) {
            return TrackedActivityRealmProxy.getTableName();
        }
        throw DefaultRealmModuleMediator.getMissingProxyClassException(class_);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> object, Object object2, Row row, ColumnInfo columnInfo, boolean bl, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = (BaseRealm.RealmObjectContext)BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm)object2, row, columnInfo, bl, list);
            DefaultRealmModuleMediator.checkClass(object);
            if (object.equals(ActivityTrackedGroupedByHour.class)) {
                object = (RealmModel)((Class)object).cast(new ActivityTrackedGroupedByHourRealmProxy());
                return (E)object;
            }
            if (object.equals(WeightLastMeasurement.class)) {
                object = (RealmModel)((Class)object).cast(new WeightLastMeasurementRealmProxy());
                return (E)object;
            }
            if (object.equals(TrackedDetectedActivity.class)) {
                object = (RealmModel)((Class)object).cast(new TrackedDetectedActivityRealmProxy());
                return (E)object;
            }
            if (object.equals(ActivityTrackedGroupedByDay.class)) {
                object = (RealmModel)((Class)object).cast(new ActivityTrackedGroupedByDayRealmProxy());
                return (E)object;
            }
            if (object.equals(LastBpMeasurementData.class)) {
                object = (RealmModel)((Class)object).cast(new LastBpMeasurementDataRealmProxy());
                return (E)object;
            }
            if (object.equals(IFollowUser.class)) {
                object = (RealmModel)((Class)object).cast(new IFollowUserRealmProxy());
                return (E)object;
            }
            if (object.equals(FollowMeUser.class)) {
                object = (RealmModel)((Class)object).cast(new FollowMeUserRealmProxy());
                return (E)object;
            }
            if (object.equals(WeightExtraInfo.class)) {
                object = (RealmModel)((Class)object).cast(new WeightExtraInfoRealmProxy());
                return (E)object;
            }
            if (object.equals(ActivityTrackedGroupedByMinute.class)) {
                object = (RealmModel)((Class)object).cast(new ActivityTrackedGroupedByMinuteRealmProxy());
                return (E)object;
            }
            if (object.equals(User.class)) {
                object = (RealmModel)((Class)object).cast(new UserRealmProxy());
                return (E)object;
            }
            if (object.equals(BpLastMeasurement.class)) {
                object = (RealmModel)((Class)object).cast(new BpLastMeasurementRealmProxy());
                return (E)object;
            }
            if (object.equals(TrackedActivity.class)) {
                object = (RealmModel)((Class)object).cast(new TrackedActivityRealmProxy());
                return (E)object;
            }
            throw DefaultRealmModuleMediator.getMissingProxyClassException((Class<? extends RealmModel>)object);
        }
        finally {
            realmObjectContext.clear();
        }
    }

    @Override
    public boolean transformerApplied() {
        return true;
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> class_, SharedRealm sharedRealm, boolean bl) {
        DefaultRealmModuleMediator.checkClass(class_);
        if (class_.equals(ActivityTrackedGroupedByHour.class)) {
            return ActivityTrackedGroupedByHourRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(WeightLastMeasurement.class)) {
            return WeightLastMeasurementRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(TrackedDetectedActivity.class)) {
            return TrackedDetectedActivityRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(ActivityTrackedGroupedByDay.class)) {
            return ActivityTrackedGroupedByDayRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(LastBpMeasurementData.class)) {
            return LastBpMeasurementDataRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(IFollowUser.class)) {
            return IFollowUserRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(FollowMeUser.class)) {
            return FollowMeUserRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(WeightExtraInfo.class)) {
            return WeightExtraInfoRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(ActivityTrackedGroupedByMinute.class)) {
            return ActivityTrackedGroupedByMinuteRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(User.class)) {
            return UserRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(BpLastMeasurement.class)) {
            return BpLastMeasurementRealmProxy.validateTable(sharedRealm, bl);
        }
        if (class_.equals(TrackedActivity.class)) {
            return TrackedActivityRealmProxy.validateTable(sharedRealm, bl);
        }
        throw DefaultRealmModuleMediator.getMissingProxyClassException(class_);
    }
}

