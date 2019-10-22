/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeContactPermissionExplanationDialog$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeContactPermissionExplanationDialog$$Lambda$2;
import io.reactivex.functions.Action;
import timber.log.Timber;

public class FollowMeContactPermissionExplanationDialog
extends DialogFragment {
    private Action onCancelClickListener;
    private Action onOkClickListener;

    public static FollowMeContactPermissionExplanationDialog newInstance() {
        return new FollowMeContactPermissionExplanationDialog();
    }

    /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int n) {
        try {
            this.onOkClickListener.run();
            return;
        }
        catch (Exception exception) {
            Timber.e(exception);
            return;
        }
    }

    /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialogInterface, int n) {
        try {
            this.onCancelClickListener.run();
            return;
        }
        catch (Exception exception) {
            Timber.e(exception);
            return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setStyle(0, 2131493050);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        bundle = new AlertDialog.Builder((Context)this.getActivity());
        bundle.setTitle(2131362030);
        bundle.setMessage(2131362029);
        bundle.setCancelable(false);
        bundle.setPositiveButton(2131362399, FollowMeContactPermissionExplanationDialog$$Lambda$1.lambdaFactory$(this));
        bundle.setNegativeButton(2131362229, FollowMeContactPermissionExplanationDialog$$Lambda$2.lambdaFactory$(this));
        return bundle.create();
    }

    public void setOnCancelClickListener(Action action) {
        this.onCancelClickListener = action;
    }

    public void setOnOkClickListener(Action action) {
        this.onOkClickListener = action;
    }
}

