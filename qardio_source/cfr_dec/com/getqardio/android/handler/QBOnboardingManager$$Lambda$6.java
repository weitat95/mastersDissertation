/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.getqardio.android.handler;

import com.getqardio.android.handler.QBOnboardingManager;
import java.lang.invoke.LambdaForm;
import org.json.JSONObject;

final class QBOnboardingManager$$Lambda$6
implements Runnable {
    private final QBOnboardingManager arg$1;
    private final JSONObject arg$2;

    private QBOnboardingManager$$Lambda$6(QBOnboardingManager qBOnboardingManager, JSONObject jSONObject) {
        this.arg$1 = qBOnboardingManager;
        this.arg$2 = jSONObject;
    }

    public static Runnable lambdaFactory$(QBOnboardingManager qBOnboardingManager, JSONObject jSONObject) {
        return new QBOnboardingManager$$Lambda$6(qBOnboardingManager, jSONObject);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$writeUserProfileDelay$5(this.arg$2);
    }
}

