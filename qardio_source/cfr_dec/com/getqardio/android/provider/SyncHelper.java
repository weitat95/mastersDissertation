/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.provider;

import android.content.Context;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.provider.TooltipHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.utils.Utils;
import java.util.Locale;

public class SyncHelper {
    private FollowMeUserRepository followMeUserRepository;
    private IFollowUserRepository iFollowUserRepository;

    public SyncHelper(IFollowUserRepository iFollowUserRepository, FollowMeUserRepository followMeUserRepository) {
        this.iFollowUserRepository = iFollowUserRepository;
        this.followMeUserRepository = followMeUserRepository;
    }

    static void cancelAllRequests(Context context) {
        AsyncReceiverHandler.stopSyncService(context);
    }

    private static void updateCurrentUserLocalization(Context context, long l) {
        String string2;
        Profile profile = DataHelper.ProfileHelper.getProfileForUser(context, l);
        if (profile != null && !(string2 = Locale.getDefault().getLanguage()).equals(profile.locale)) {
            profile.locale = string2;
            DataHelper.ProfileHelper.saveProfile(context, profile, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void syncAll(Context context, long l) {
        if (Utils.isNetworkAvaliable(context)) {
            FirmwareUpdateHelper.loadQBLatestFirmwareInfo(context, l);
            SyncHelper.updateCurrentUserLocalization(context, l);
            DataHelper.ProfileHelper.requestProfileUpdate(context, l);
            DataHelper.SettingsHelper.requestSettingsUpdate(context, l);
            DataHelper.DeviceAssociationsHelper.requestDeviceAssociationsGet(context, l);
            MeasurementHelper.Claim.requestClaimMeasurementsUpdate(context, l, DataHelper.DeviceIdHelper.getDeviceId(context, l));
            ShealthDataHelper.BpMeasurements.requestReadMeasurements(context, l);
            ShealthDataHelper.WeightMeasurements.requestReadWeightMeasurements(context, l);
            GoogleFitDataHelper.Weight.requestReadFromGoogleFit(context, l);
            MeasurementHelper.BloodPressure.requestBPMeasurementsUpdate(context, l);
            MeasurementHelper.Weight.requestWeightMeasurementsUpdate(context, l);
            DataHelper.CurrentGoalHelper.requestGoalUpdate(context, l);
            DataHelper.ReminderHelper.requestReminderUpdate(context, l, new String[]{"bp", "weight"});
            DataHelper.FaqHelper.requestArmFaqUpdate(context, l);
            DataHelper.FaqHelper.requestBaseFaqUpdate(context, l);
            MeasurementHelper.BloodPressure.requestSaveOldVisitoreMeasurements(context, l);
            TooltipHelper.createGetTooltipIntent(context, l);
            DataHelper.FlickrHelper.requestFlickrSync(context, l);
            DataHelper.HistoryHelper.requestSendOldHistory(context, l);
            DataHelper.DeviceAssociationsHelper.requestDeviceAssociationsSync(context, l);
            DataHelper.PregnancyDataHelper.requestPregnancyModeUpdate(context, l);
        }
        if (DataHelper.NotesHelper.hasPredefinedBPMeasurementNotes(context, l)) {
            DataHelper.NotesHelper.repairPredefinedBPMeasurementNotes(context, l);
        } else {
            DataHelper.NotesHelper.addPredefinedBPMeasurementNotes(context, l);
            DataHelper.NotesHelper.setHasPredefinedBPMeasurementNotes(context, l, true);
        }
        if (!DataHelper.NotesHelper.hasPredefinedWeightMeasurementNotes(context, l)) {
            DataHelper.NotesHelper.addPredefinedWeightMeasurementNotes(context, l);
            DataHelper.NotesHelper.setHasPredefinedWeightMeasurementNotes(context, l, true);
        }
    }
}

