/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.ActivityGoalsJobService;
import io.reactivex.functions.BiFunction;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class ActivityGoalsJobService$$Lambda$1
implements BiFunction {
    private final ActivityGoalsJobService arg$1;

    private ActivityGoalsJobService$$Lambda$1(ActivityGoalsJobService activityGoalsJobService) {
        this.arg$1 = activityGoalsJobService;
    }

    public static BiFunction lambdaFactory$(ActivityGoalsJobService activityGoalsJobService) {
        return new ActivityGoalsJobService$$Lambda$1(activityGoalsJobService);
    }

    @LambdaForm.Hidden
    public Object apply(Object object, Object object2) {
        return this.arg$1.lambda$onConnected$0((Integer)object, (List)object2);
    }
}

