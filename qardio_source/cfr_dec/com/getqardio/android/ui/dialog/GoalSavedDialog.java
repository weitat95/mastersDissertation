/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.View
 */
package com.getqardio.android.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.getqardio.android.ui.widget.AnimatedCheckView;
import java.util.concurrent.TimeUnit;

public class GoalSavedDialog
extends AlertDialog {
    private static final long DIALOG_DURATION = TimeUnit.SECONDS.toMillis(2L);

    protected GoalSavedDialog(Context context) {
        super(context, true, null);
    }

    private void init() {
        ((AnimatedCheckView)((Object)this.findViewById(2131821088))).setChecked();
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                if (GoalSavedDialog.this.isShowing()) {
                    GoalSavedDialog.this.dismiss();
                }
            }
        }, DIALOG_DURATION);
    }

    public static GoalSavedDialog newInstance(Context context) {
        return new GoalSavedDialog(context);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968722);
        this.init();
    }

}

