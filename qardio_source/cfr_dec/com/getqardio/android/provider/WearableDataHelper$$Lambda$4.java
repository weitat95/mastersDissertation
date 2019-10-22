/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.provider;

import android.content.Context;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.provider.WearableDataHelper;
import com.getqardio.shared.wearable.datamodel.FollowingData;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class WearableDataHelper$$Lambda$4
implements Function {
    private final WearableDataHelper arg$1;
    private final Context arg$2;

    private WearableDataHelper$$Lambda$4(WearableDataHelper wearableDataHelper, Context context) {
        this.arg$1 = wearableDataHelper;
        this.arg$2 = context;
    }

    public static Function lambdaFactory$(WearableDataHelper wearableDataHelper, Context context) {
        return new WearableDataHelper$$Lambda$4(wearableDataHelper, context);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$getFollowingData$2(this.arg$2, (IFollowUser)object);
    }
}

