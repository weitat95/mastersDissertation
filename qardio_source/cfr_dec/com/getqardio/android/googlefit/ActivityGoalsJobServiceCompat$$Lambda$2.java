/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityGoalsJobServiceCompat$$Lambda$2
implements Consumer {
    private final ActivityGoalsJobServiceCompat arg$1;

    private ActivityGoalsJobServiceCompat$$Lambda$2(ActivityGoalsJobServiceCompat activityGoalsJobServiceCompat) {
        this.arg$1 = activityGoalsJobServiceCompat;
    }

    public static Consumer lambdaFactory$(ActivityGoalsJobServiceCompat activityGoalsJobServiceCompat) {
        return new ActivityGoalsJobServiceCompat$$Lambda$2(activityGoalsJobServiceCompat);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$1((Boolean)object);
    }
}

