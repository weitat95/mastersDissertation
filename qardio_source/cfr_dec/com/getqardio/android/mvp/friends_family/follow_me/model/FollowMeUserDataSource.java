/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.model;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

public interface FollowMeUserDataSource {
    public Single<FollowMeUser> deleteFollowMeUser(long var1, FollowMeUser var3);

    public Maybe<List<FollowMeUser>> getFollowMeUsersMaybe(long var1);

    public Single<FollowMeUser> inviteOrSaveUserToFollowMe(long var1, FollowMeUser var3, SyncStatus var4);

    public Maybe<List<FollowMeUser>> rewriteFollowMeUsers(long var1, List<FollowMeUser> var3);
}

