/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import java.util.List;

public interface IFollowContract {

    public static interface View {
        public void showBpDetails(String var1);

        public void showData(List<IFollowUser> var1);

        public void showEmpty();

        public void showError();

        public void showProgress();

        public void showToastError();

        public void showWeightDetails(String var1);

        public void startContextualActionBarMode();

        public void stopContextualActionBarMode();

        public void updateItemAtPosition(int var1);
    }

}

