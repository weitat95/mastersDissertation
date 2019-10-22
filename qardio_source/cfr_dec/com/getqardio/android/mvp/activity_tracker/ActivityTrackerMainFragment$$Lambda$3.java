/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker;

import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerMainFragment$$Lambda$3
implements Consumer {
    private static final ActivityTrackerMainFragment$$Lambda$3 instance = new ActivityTrackerMainFragment$$Lambda$3();

    private ActivityTrackerMainFragment$$Lambda$3() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        ActivityTrackerMainFragment.lambda$onConnected$2((Throwable)object);
    }
}

