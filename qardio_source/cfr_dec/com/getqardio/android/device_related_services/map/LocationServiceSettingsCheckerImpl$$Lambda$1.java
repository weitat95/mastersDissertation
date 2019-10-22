/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.device_related_services.map;

import android.support.v4.app.FragmentActivity;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsCheckerImpl;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationSettingsResult;
import java.lang.invoke.LambdaForm;

final class LocationServiceSettingsCheckerImpl$$Lambda$1
implements ResultCallback {
    private final LocationServiceSettingsCheckerImpl arg$1;
    private final FragmentActivity arg$2;
    private final int arg$3;

    private LocationServiceSettingsCheckerImpl$$Lambda$1(LocationServiceSettingsCheckerImpl locationServiceSettingsCheckerImpl, FragmentActivity fragmentActivity, int n) {
        this.arg$1 = locationServiceSettingsCheckerImpl;
        this.arg$2 = fragmentActivity;
        this.arg$3 = n;
    }

    public static ResultCallback lambdaFactory$(LocationServiceSettingsCheckerImpl locationServiceSettingsCheckerImpl, FragmentActivity fragmentActivity, int n) {
        return new LocationServiceSettingsCheckerImpl$$Lambda$1(locationServiceSettingsCheckerImpl, fragmentActivity, n);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        this.arg$1.lambda$enableLocation$0(this.arg$2, this.arg$3, (LocationSettingsResult)result);
    }
}

