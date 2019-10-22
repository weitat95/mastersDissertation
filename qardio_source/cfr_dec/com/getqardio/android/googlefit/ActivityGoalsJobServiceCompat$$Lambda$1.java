/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat;
import io.reactivex.functions.BiFunction;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class ActivityGoalsJobServiceCompat$$Lambda$1
implements BiFunction {
    private final ActivityGoalsJobServiceCompat arg$1;

    private ActivityGoalsJobServiceCompat$$Lambda$1(ActivityGoalsJobServiceCompat activityGoalsJobServiceCompat) {
        this.arg$1 = activityGoalsJobServiceCompat;
    }

    public static BiFunction lambdaFactory$(ActivityGoalsJobServiceCompat activityGoalsJobServiceCompat) {
        return new ActivityGoalsJobServiceCompat$$Lambda$1(activityGoalsJobServiceCompat);
    }

    @LambdaForm.Hidden
    public Object apply(Object object, Object object2) {
        return this.arg$1.lambda$onConnected$0((Integer)object, (List)object2);
    }
}

