/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.googlefit;

import android.view.View;
import com.getqardio.android.googlefit.GoogleFitOnboardActivity;
import java.lang.invoke.LambdaForm;

final class GoogleFitOnboardActivity$$Lambda$1
implements View.OnClickListener {
    private final GoogleFitOnboardActivity arg$1;

    private GoogleFitOnboardActivity$$Lambda$1(GoogleFitOnboardActivity googleFitOnboardActivity) {
        this.arg$1 = googleFitOnboardActivity;
    }

    public static View.OnClickListener lambdaFactory$(GoogleFitOnboardActivity googleFitOnboardActivity) {
        return new GoogleFitOnboardActivity$$Lambda$1(googleFitOnboardActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreate$0(view);
    }
}

