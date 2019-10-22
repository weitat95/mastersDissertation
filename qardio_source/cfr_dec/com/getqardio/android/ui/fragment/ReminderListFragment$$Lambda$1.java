/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.ReminderListFragment;
import java.lang.invoke.LambdaForm;

final class ReminderListFragment$$Lambda$1
implements View.OnClickListener {
    private final ReminderListFragment arg$1;

    private ReminderListFragment$$Lambda$1(ReminderListFragment reminderListFragment) {
        this.arg$1 = reminderListFragment;
    }

    public static View.OnClickListener lambdaFactory$(ReminderListFragment reminderListFragment) {
        return new ReminderListFragment$$Lambda$1(reminderListFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onResume$0(view);
    }
}

