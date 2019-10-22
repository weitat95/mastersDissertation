/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Base64
 */
package com.getqardio.android.io.network;

import android.net.Uri;
import android.util.Base64;
import com.getqardio.android.io.network.NetworkRequestHelper;
import java.io.UnsupportedEncodingException;
import timber.log.Timber;

public abstract class NetworkContract {

    public static interface AddDeviceAssociation {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.POST;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/associations/add");
    }

    public static interface CreateNewAccount {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/add_new_user");
    }

    public static interface CreateOrUpdateCurrentGoal {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.POST;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/create_or_update_goal");
    }

    public static interface CreateUpdateReminder {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/reminders");
    }

    public static interface DeleteCurrentGoal {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.POST;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/delete_goal");
    }

    public static interface DeleteMeasurementById {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/delete_measurement_by_id");
    }

    public static interface DeleteMeasurements {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/delete_measurement_data");
    }

    public static interface DeleteReminder {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/delete_reminders");
    }

    public static interface Faqs {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/support/get_faq_list");
    }

    public static interface Flickr {
        public static final Uri URI = Uri.parse((String)"https://api.flickr.com/services/rest/");
    }

    public static interface ForgotPassword {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/forgot_password");
    }

    public static class GcmData {
        public static String SENDER_ID = "683699925690";
    }

    public static interface GetCurrentGoal {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.GET;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/get_goal");
    }

    public static interface GetLastWeightMeasurementBySn {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/get_last_measurements_by_sn");
    }

    public static interface GetLatestFirmware {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/firmware/latest");
    }

    public static interface GetPregnancyModeHistory {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.GET;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/pregnancy_history");
    }

    public static interface GetProfileInfo {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/get_profile_info");
    }

    public static interface GetReminders {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/reminders");
    }

    public static interface GetSettings {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/settings");
    }

    public static interface GetShortMeasurements {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/get_short_measurements");
    }

    public static interface GetStatistic {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/get_device_usage");
    }

    public static interface GetTolltips {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/get_tooltips");
    }

    public static interface Login {
        public static final Uri URI = Uri.parse((String)"https://oauth2.getqardio.com/oauth/token");
    }

    public static interface Logout {
        public static final Uri URI = Uri.parse((String)"https://oauth2.getqardio.com/logout");
    }

    public static class OAuthData {
        public static String generateAuthorization() {
            try {
                Object object = Base64.encode((byte[])"kidJdCIJX39GnXpDlZBvM5ynS:kYvLN3E5X9jRjnDF5OCcypNZf".getBytes("UTF-8"), (int)0);
                object = "Basic " + new String((byte[])object);
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                Timber.e(unsupportedEncodingException, "", new Object[0]);
                return null;
            }
        }
    }

    public static interface RemoveDeviceAssociation {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.POST;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/associations/sn_reset_associations");
    }

    public static interface SaveMeasurements {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/save_measurement_data");
    }

    public static interface SendHistory {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/send_measurements_to_doctor");
    }

    public static interface SetPregnancyMode {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.POST;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/set_pregnancy_mode");
    }

    public static interface SetProfileInfo {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/update_profile_info");
    }

    public static interface SetSettings {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/settings");
    }

    public static interface Support {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/support/support_ticket");
    }

    public static interface UpdateMeasurementUserId {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/update_measurement_user_id");
    }

    public static interface UpdateStatistic {
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/user/update_device_usage");
    }

    public static interface UserActiveAssociations {
        public static final NetworkRequestHelper.Method METHOD = NetworkRequestHelper.Method.GET;
        public static final Uri URI = Uri.parse((String)"https://api.getqardio.com/associations/get_user_sn_association");
    }

}

