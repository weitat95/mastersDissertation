/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.utils.analytics;

import android.os.Build;
import com.segment.analytics.Traits;

public class CustomTraits
extends Traits {
    private String enabledToString(boolean bl) {
        if (bl) {
            return "yes";
        }
        return "no";
    }

    private CustomTraits putValueInternal(String string2, Object object) {
        return (CustomTraits)this.putValue(string2, object);
    }

    public CustomTraits putActivityGoal(int n) {
        return this.putValueInternal("activity goal", n);
    }

    public CustomTraits putActivityTrackerEssentialNotification(boolean bl) {
        return this.putValueInternal("enabled activity tracker notifications", this.enabledToString(bl));
    }

    public CustomTraits putEnableEssentialNotification(boolean bl) {
        return this.putValueInternal("enabled essential notifications", this.enabledToString(bl));
    }

    public CustomTraits putEnabledBodyComposition(boolean bl) {
        return this.putValueInternal("enabled body comp", this.enabledToString(bl));
    }

    public CustomTraits putEnabledGoogleFit(boolean bl) {
        return this.putValueInternal("enabled google fit", this.enabledToString(bl));
    }

    public CustomTraits putEnabledImportantNotifications(boolean bl) {
        CustomTraits customTraits = this;
        if (Build.VERSION.SDK_INT < 26) {
            customTraits = this.putValueInternal("enable important notifications", this.enabledToString(bl));
        }
        return customTraits;
    }

    public CustomTraits putEnabledPhotoSlideshow(boolean bl) {
        return this.putValueInternal("enabled photo slideshow", this.enabledToString(bl));
    }

    public CustomTraits putEnabledPlaces(boolean bl) {
        return this.putValueInternal("enabled places", this.enabledToString(bl));
    }

    public CustomTraits putEnteredDoctorInfo(boolean bl) {
        return this.putValueInternal("entered doc info", this.enabledToString(bl));
    }

    public CustomTraits putQaMeasurementCount(int n) {
        return this.putValueInternal("QA measurement count setting", n);
    }

    public CustomTraits putQaMeasurementPause(int n) {
        return this.putValueInternal("QA measurement pause", n);
    }

    public CustomTraits putStepsGoal(int n) {
        return this.putValueInternal("step goal", n);
    }
}

