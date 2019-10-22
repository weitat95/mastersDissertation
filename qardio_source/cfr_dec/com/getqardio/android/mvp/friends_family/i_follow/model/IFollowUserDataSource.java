/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model;

import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

public interface IFollowUserDataSource {
    public Single<IFollowUser> deleteIFollowUser(long var1, IFollowUser var3);

    public Single<IFollowUser> enablePushNotifications(long var1, IFollowUser var3, boolean var4);

    public Maybe<List<IFollowUser>> getIFollowUsersMaybe(long var1);

    public Maybe<List<IFollowUser>> rewriteIFollowUsers(long var1, List<IFollowUser> var3);

    public Single<IFollowUser> saveIFollowUser(long var1, IFollowUser var3, SyncStatus var4);
}

