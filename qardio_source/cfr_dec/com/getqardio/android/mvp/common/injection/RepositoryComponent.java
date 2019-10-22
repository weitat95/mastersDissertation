/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository;

public interface RepositoryComponent {
    public FollowMeUserRepository getFollowingMeUserRepository();

    public GoalForActivityTypeRepository getGoalForActivityTypeRepository();

    public IFollowUserRepository getIFollowUserRepository();

    public QBMeasurementDetailsRepository getQBMeasurementDetailsRepository();

    public void inject(MvpApplication var1);

    public InviteApi provideInviteApi();
}

