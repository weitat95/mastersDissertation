/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.getqardio.android.ui.fragment;

import android.net.Uri;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import java.io.File;
import java.lang.invoke.LambdaForm;

final class PregnancyMeasurementFragment$$Lambda$6
implements Runnable {
    private final PregnancyMeasurementFragment arg$1;
    private final Uri arg$2;
    private final File arg$3;

    private PregnancyMeasurementFragment$$Lambda$6(PregnancyMeasurementFragment pregnancyMeasurementFragment, Uri uri, File file) {
        this.arg$1 = pregnancyMeasurementFragment;
        this.arg$2 = uri;
        this.arg$3 = file;
    }

    public static Runnable lambdaFactory$(PregnancyMeasurementFragment pregnancyMeasurementFragment, Uri uri, File file) {
        return new PregnancyMeasurementFragment$$Lambda$6(pregnancyMeasurementFragment, uri, file);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$processPhoto$4(this.arg$2, this.arg$3);
    }
}

