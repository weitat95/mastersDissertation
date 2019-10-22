/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.getqardio.android.utils;

import android.content.Intent;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.utils.ErrorHelper;
import java.util.List;

public abstract class NotificationHelper {

    public static abstract class CreateNewUserNotification {
        public static Intent createErrorsResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.CreateNewUser");
            intent.putExtra("com.getqardio.android.Notifications.CreateNewUser.Result", false);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.CreateNewUser");
            intent.putExtra("com.getqardio.android.Notifications.CreateNewUser.Result", true);
            return intent;
        }
    }

    public static abstract class ForgotPasswordNotification {
        public static Intent createErrorsResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.ForgotPassword");
            intent.putExtra("com.getqardio.android.Notifications.ForgotPassword.Result", false);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.ForgotPassword");
            intent.putExtra("com.getqardio.android.Notifications.ForgotPassword.Result", true);
            return intent;
        }
    }

    public static abstract class LoginNotification {
        public static Intent createErrorsResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.LOGIN");
            intent.putExtra("com.getqardio.android.Notifications.Login.Result", false);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.LOGIN");
            intent.putExtra("com.getqardio.android.Notifications.Login.Result", true);
            return intent;
        }
    }

    public static abstract class ReloginFailedNotification {
        public static Intent createNotificationIntent() {
            return new Intent("com.getqardio.android.Notifications.RELOGIN_FAILED");
        }
    }

    public static abstract class SendHistoryNotification {
        public static Intent createErrorsResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.SendHistory");
            intent.putExtra("com.getqardio.android.Notifications.SendHistory.Result", false);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.SendHistory");
            intent.putExtra("com.getqardio.android.Notifications.SendHistory.Result", true);
            return intent;
        }
    }

    public static abstract class SendVisitorMeasurementNotification {
        public static Intent createIntent() {
            return new Intent("com.getqardio.android.Notifications.SendVisitorMeasurement");
        }
    }

    public static abstract class SupportNotification {
        public static Intent createErrorsResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.Support");
            intent.putExtra("com.getqardio.android.Notifications.Support.Result", false);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.Support");
            intent.putExtra("com.getqardio.android.Notifications.Support.Result", true);
            return intent;
        }
    }

    public static abstract class SyncRemindersNotification {
        public static Intent createErrorsResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.GetReminders");
            intent.putExtra("com.getqardio.android.Notifications.GetReminders.Result", false);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.GetReminders");
            intent.putExtra("com.getqardio.android.Notifications.GetReminders.Result", true);
            return intent;
        }
    }

    public static abstract class UpdateProfileNotification {
        public static Intent createErrorsResult(List<BaseError> list) {
            Intent intent = new Intent("com.getqardio.android.Notifications.UPDATE_PROFILE");
            intent.putExtra("com.getqardio.android.Notifications.UpdateProfile.Result", false);
            ErrorHelper.putErrorsToIntent(intent, list);
            return intent;
        }

        public static Intent createSuccessResult() {
            Intent intent = new Intent("com.getqardio.android.Notifications.UPDATE_PROFILE");
            intent.putExtra("com.getqardio.android.Notifications.UpdateProfile.Result", true);
            return intent;
        }

        public static boolean isSuccessful(Intent intent) {
            return intent.getBooleanExtra("com.getqardio.android.Notifications.UpdateProfile.Result", false);
        }
    }

}

