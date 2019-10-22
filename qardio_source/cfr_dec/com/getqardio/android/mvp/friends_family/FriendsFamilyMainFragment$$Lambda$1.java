/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.mvp.friends_family;

import android.view.View;
import com.getqardio.android.mvp.friends_family.FriendsFamilyMainFragment;
import java.lang.invoke.LambdaForm;

final class FriendsFamilyMainFragment$$Lambda$1
implements View.OnClickListener {
    private static final FriendsFamilyMainFragment$$Lambda$1 instance = new FriendsFamilyMainFragment$$Lambda$1();

    private FriendsFamilyMainFragment$$Lambda$1() {
    }

    public static View.OnClickListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        FriendsFamilyMainFragment.lambda$onCreateView$0(view);
    }
}

