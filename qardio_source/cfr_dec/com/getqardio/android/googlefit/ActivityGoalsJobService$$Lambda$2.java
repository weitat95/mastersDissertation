/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.ActivityGoalsJobService;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityGoalsJobService$$Lambda$2
implements Consumer {
    private final ActivityGoalsJobService arg$1;

    private ActivityGoalsJobService$$Lambda$2(ActivityGoalsJobService activityGoalsJobService) {
        this.arg$1 = activityGoalsJobService;
    }

    public static Consumer lambdaFactory$(ActivityGoalsJobService activityGoalsJobService) {
        return new ActivityGoalsJobService$$Lambda$2(activityGoalsJobService);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$1((Boolean)object);
    }
}

