/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.activity;

import com.getqardio.android.ui.activity.InviteUserActivity;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class InviteUserActivity$$Lambda$2
implements Consumer {
    private final InviteUserActivity arg$1;

    private InviteUserActivity$$Lambda$2(InviteUserActivity inviteUserActivity) {
        this.arg$1 = inviteUserActivity;
    }

    public static Consumer lambdaFactory$(InviteUserActivity inviteUserActivity) {
        return new InviteUserActivity$$Lambda$2(inviteUserActivity);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onClick$1((Throwable)object);
    }
}

