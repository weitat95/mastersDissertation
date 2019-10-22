/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.app.Activity;
import android.content.Context;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class MixpanelHelper {
    public static MixpanelAPI setupMixpanel(Context context, String string2) {
        MixpanelAPI mixpanelAPI = MixpanelAPI.getInstance(context, Constants.MIXPANEL_PROJECT_TOKEN);
        mixpanelAPI.logPosts();
        MixpanelAPI.People people = mixpanelAPI.getPeople();
        people.identify(string2);
        people.initPushHandling(NetworkContract.GcmData.SENDER_ID);
        IdentifyHelper.identify(context, string2, new CustomTraits());
        return mixpanelAPI;
    }

    public static void showNotificationIfAvailable(Activity activity, MixpanelAPI mixpanelAPI) {
        mixpanelAPI.getPeople().showNotificationIfAvailable(activity);
    }
}

