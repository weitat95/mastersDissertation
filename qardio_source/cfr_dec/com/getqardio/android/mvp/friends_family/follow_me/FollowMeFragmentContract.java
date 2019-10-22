/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me;

import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeUiModelItem;
import java.util.List;

public interface FollowMeFragmentContract {

    public static interface View {
        public void showData(List<FollowMeUiModelItem> var1);

        public void showEmpty();

        public void showError();

        public void showProgress();

        public void showToastError();

        public void startContextualActionBarMode();

        public void stopContextualActionBarMode();

        public void updateItemAtPosition(int var1);
    }

}

