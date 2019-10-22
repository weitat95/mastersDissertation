/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.activity.ActivityTrackingDataSource;
import com.getqardio.android.mvp.activity.ActivityTrackingLocalDataSource;
import com.getqardio.android.mvp.activity.ActivityTrackingRepository;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityRepository;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityRepository;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserDataSource;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.provider.SyncHelper;

public class RepositoryModule {
    HistoryActivityRepository provideActivityTrackerHistoryRepo(HistoryActivityDataSource historyActivityDataSource, HistoryActivityDataSource historyActivityDataSource2) {
        return new HistoryActivityRepository(historyActivityDataSource, historyActivityDataSource2);
    }

    TodayActivityRepository provideActivityTrackerRepo(TodayActivityDataSource todayActivityDataSource, TodayActivityDataSource todayActivityDataSource2) {
        return new TodayActivityRepository(todayActivityDataSource, todayActivityDataSource2);
    }

    ActivityTrackingRepository provideActivityTrackingRepository(ActivityTrackingLocalDataSource activityTrackingLocalDataSource) {
        return new ActivityTrackingRepository(activityTrackingLocalDataSource);
    }

    FollowMeUserRepository provideFollowingMeUserRepo(FollowMeUserDataSource followMeUserDataSource, FollowMeUserDataSource followMeUserDataSource2) {
        return new FollowMeUserRepository(followMeUserDataSource, followMeUserDataSource2);
    }

    GoalForActivityTypeRepository provideGoalForActivityTypeRepo(GoalForActivityTypeDataSource goalForActivityTypeDataSource, GoalForActivityTypeDataSource goalForActivityTypeDataSource2) {
        return new GoalForActivityTypeRepository(goalForActivityTypeDataSource, goalForActivityTypeDataSource2);
    }

    IFollowUserRepository provideIFollowRepo(IFollowUserDataSource iFollowUserDataSource, IFollowUserDataSource iFollowUserDataSource2) {
        return new IFollowUserRepository(iFollowUserDataSource, iFollowUserDataSource2);
    }

    SyncHelper provideSyncHelper(IFollowUserRepository iFollowUserRepository, FollowMeUserRepository followMeUserRepository) {
        return new SyncHelper(iFollowUserRepository, followMeUserRepository);
    }
}

