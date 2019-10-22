/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.getqardio.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;
import java.util.concurrent.TimeUnit;

public class RateAppManager {
    private Context context;
    private final Tweak<Integer> daysToPass;
    private boolean hasSuccessfulMeasurement;
    private long lastRateDate;
    private int qaMeasurementsDone;
    private final Tweak<Integer> qaMeasurementsToBeDone;
    private int ratedAppVersion;
    private boolean userHasSelectedNoThanks;

    public RateAppManager(Context context) {
        this.context = context;
        this.daysToPass = MixpanelAPI.intTweak("Rate period", 60);
        this.qaMeasurementsToBeDone = MixpanelAPI.intTweak("Rate QA measurements count", 1);
        context = context.getSharedPreferences("rate_app_storage", 0);
        this.ratedAppVersion = context.getInt("rated_app_version", 0);
        this.qaMeasurementsDone = context.getInt("qa_measurements_done", 0);
        this.lastRateDate = context.getLong("last_rate_date", 0L);
        this.userHasSelectedNoThanks = context.getBoolean("no_rate_app", false);
        this.hasSuccessfulMeasurement = false;
    }

    public boolean hasSuccessfulMeasurement() {
        return this.hasSuccessfulMeasurement;
    }

    public void onNoThanks(Context context) {
        context = context.getSharedPreferences("rate_app_storage", 0).edit();
        context.putBoolean("no_rate_app", true);
        context.apply();
    }

    public void onQAMeasurementComplete(Context context) {
        if (System.currentTimeMillis() - this.lastRateDate > TimeUnit.DAYS.toMillis(this.daysToPass.get().intValue())) {
            ++this.qaMeasurementsDone;
            context = context.getSharedPreferences("rate_app_storage", 0).edit();
            context.putInt("qa_measurements_done", this.qaMeasurementsDone);
            context.apply();
        }
    }

    public void onRateDialogWasShown(Context context) {
        this.ratedAppVersion = 91;
        this.qaMeasurementsDone = 0;
        this.lastRateDate = System.currentTimeMillis();
        context = context.getSharedPreferences("rate_app_storage", 0).edit();
        context.putInt("rated_app_version", this.ratedAppVersion);
        context.putInt("qa_measurements_done", this.qaMeasurementsDone);
        context.putLong("last_rate_date", this.lastRateDate);
        context.apply();
    }

    public void onRemindMeLater(Context context) {
        this.lastRateDate = System.currentTimeMillis();
        this.qaMeasurementsDone = 0;
        context = context.getSharedPreferences("rate_app_storage", 0).edit();
        context.putInt("qa_measurements_done", this.qaMeasurementsDone);
        context.putLong("last_rate_date", this.lastRateDate);
        context.apply();
    }

    public void setHasSuccessfulMeasurement(boolean bl) {
        this.hasSuccessfulMeasurement = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean shouldShowRateDialog() {
        this.userHasSelectedNoThanks = this.context.getSharedPreferences("rate_app_storage", 0).getBoolean("no_rate_app", false);
        if (91 <= this.ratedAppVersion) return false;
        if (this.userHasSelectedNoThanks) {
            return false;
        }
        if (System.currentTimeMillis() - this.lastRateDate < TimeUnit.DAYS.toMillis(this.daysToPass.get().intValue())) return false;
        if (this.qaMeasurementsDone < this.qaMeasurementsToBeDone.get()) return false;
        return true;
    }
}

