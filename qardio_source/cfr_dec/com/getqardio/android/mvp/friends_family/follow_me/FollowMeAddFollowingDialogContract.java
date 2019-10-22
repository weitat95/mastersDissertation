/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me;

public interface FollowMeAddFollowingDialogContract {

    public static interface View {
        public void dismiss();

        public void enableSendButton(boolean var1);

        public void hideError();

        public void showError(int var1);

        public void showInputScreen();

        public void showProgress();
    }

}

