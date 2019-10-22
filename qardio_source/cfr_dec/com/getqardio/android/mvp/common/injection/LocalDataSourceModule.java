/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.preference.PreferenceManager
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.activity.ActivityTrackingLocalDataSource;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerAggregator;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.goals.model.local.GoalForActivityTypeLocalDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.local.HistoryActivityLocalDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.local.TodayActivityLocalDataSource;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUserLocalDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUserLocalDataSource;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class LocalDataSourceModule {
    AccountContextHelper provideAccountContextHelper(Realm realm) {
        return new AccountContextHelper(realm);
    }

    HistoryActivityDataSource provideActivityTrackerHistoryLocalDataSource(Realm realm) {
        return new HistoryActivityLocalDataSource(realm);
    }

    TodayActivityDataSource provideActivityTrackerLocalDataSource(Realm realm) {
        return new TodayActivityLocalDataSource(realm);
    }

    ActivityTrackingLocalDataSource provideActivityTrackingLocalDataSource(Realm realm, Context context) {
        return new ActivityTrackingLocalDataSource(realm, 60, MvpApplication.get(context).getActivityTracker());
    }

    FollowMeUserDataSource provideFollowingMeUserLocalDataSource(Realm realm, AccountContextHelper accountContextHelper) {
        return new FollowMeUserLocalDataSource(realm, accountContextHelper);
    }

    GoalForActivityTypeDataSource provideGoalForActivityTypeLocalDataSource(Realm realm) {
        return new GoalForActivityTypeLocalDataSource(realm);
    }

    IFollowUserDataSource provideIFollowUserLocalDataSource(Realm realm, AccountContextHelper accountContextHelper) {
        return new IFollowUserLocalDataSource(realm, accountContextHelper);
    }

    QBMeasurementDetailsLocalDataSource provideQBMeasurementDetailsLocalDataSource(Context context, AccountContextHelper accountContextHelper) {
        return new QBMeasurementDetailsLocalDataSource(context, accountContextHelper);
    }

    Realm provideRealm(RealmConfiguration object) {
        Realm.setDefaultConfiguration((RealmConfiguration)object);
        try {
            Realm.getDefaultInstance();
            object = Realm.getDefaultInstance();
            return object;
        }
        catch (Exception exception) {
            Timber.e(exception, exception.getMessage(), new Object[0]);
            return null;
        }
    }

    RealmConfiguration provideRealmConfig(Context context) {
        Realm.init(context);
        return new RealmConfiguration.Builder().schemaVersion(3L).deleteRealmIfMigrationNeeded().build();
    }

    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences((Context)context);
    }
}

