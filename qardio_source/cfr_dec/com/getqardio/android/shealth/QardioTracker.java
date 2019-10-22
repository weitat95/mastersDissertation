/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 */
package com.getqardio.android.shealth;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.activity.SplashActivity;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.ui.Convert;
import com.samsung.android.sdk.shealth.tracker.TrackerEventListener;
import com.samsung.android.sdk.shealth.tracker.TrackerTile;
import com.samsung.android.sdk.shealth.tracker.TrackerTileManager;
import timber.log.Timber;

public class QardioTracker
implements TrackerEventListener {
    private static final String BP_TILE_ID = "qardio_bp_tile";
    private static final String DEFAULT_TILE_ID = "qardio_default_tile";
    private static final String PULSE_TILE_ID = "qardio_pulse_tile";
    private static final String WEIGHT_TILE_ID = "qardio_weight_tile";
    private TrackerTileManager trackerTileManager;

    public QardioTracker() {
    }

    public QardioTracker(Context context) {
        this.initTileManager(context);
    }

    private Intent createLaunchIntent(Context context) {
        return SplashActivity.createStartIntent(context);
    }

    private void deleteAllTiles() {
        Timber.d("deleteAllTiles", new Object[0]);
    }

    private static String floatToString(float f) {
        return Integer.toString(Math.round(f));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void initTileManager(Context context) {
        if (this.trackerTileManager != null) return;
        try {
            this.trackerTileManager = new TrackerTileManager(context);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            return;
        }
    }

    private void postCurrentUserTiles(Context context, String string2) {
        Long l = CustomApplication.getApplication().getCurrentUserId();
        if (l != null) {
            MeasurementHelper.BloodPressure.getLastMeasurement(context, l);
            MeasurementHelper.Weight.getLastMeasurement(context, l, MeasurementHelper.Weight.SHEALTH_TILE_PROJECTION);
            DataHelper.ProfileHelper.getWeightUnit(context, l);
        }
        this.showDefaultTile(context, string2, DEFAULT_TILE_ID, 2131362458, this.createLaunchIntent(context));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void showDefaultTile(Context var1_1, String var2_5, String var3_6, int var4_7, Intent var5_8) {
        if (this.trackerTileManager == null) return;
        try {
            var2_5 = new TrackerTile(var1_1, (String)var2_5, var3_6, 0);
            var2_5.setTitle(var4_7).setIcon(2130837817).setContentColor(var1_1.getResources().getColor(2131689555)).setButtonIntent(var1_1.getString(2131362382), 0, var5_8);
            this.trackerTileManager.post((TrackerTile)var2_5);
            return;
        }
        catch (IllegalArgumentException var1_2) {}
        ** GOTO lbl-1000
        catch (Resources.NotFoundException var1_4) {}
lbl-1000:
        // 2 sources
        {
            var1_3.printStackTrace();
            return;
        }
    }

    private static String weightToString(int n, float f) {
        return Convert.floatToString(Float.valueOf(MetricUtils.convertWeight(0, n, f)), 1);
    }

    @Override
    public void onCreate(Context context, String string2) {
        Timber.d("onCreate(%s)", string2);
        this.initTileManager(context);
    }

    @Override
    public void onPaused(Context context, String string2) {
        Timber.d("onPaused(%s)", string2);
    }

    @Override
    public void onSubscribed(Context context, String string2) {
        Timber.d("onSubscribed(%s)", string2);
        this.postCurrentUserTiles(context, string2);
    }

    @Override
    public void onTileRemoved(Context context, String string2, String string3) {
        Timber.d("onTileRemoved(%s, %s)", string2, string3);
    }

    @Override
    public void onTileRequested(Context context, String string2, String string3) {
        Timber.d("onTileRequested(%s, %s)", string2, string3);
        this.postCurrentUserTiles(context, string2);
    }

    @Override
    public void onUnsubscribed(Context context, String string2) {
        Timber.d("onUnsubscribed(%s)", string2);
    }
}

