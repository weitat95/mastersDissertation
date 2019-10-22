/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 */
package com.getqardio.android.shopify.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ProgressDialogHelper$$Lambda$1;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class ProgressDialogHelper {
    private final AtomicBoolean canBeShown;
    private final ProgressDialog dialog;
    private final AtomicLong requestId = new AtomicLong(0L);

    public ProgressDialogHelper(Context context) {
        this.canBeShown = new AtomicBoolean(true);
        this.dialog = new ProgressDialog(Util.checkNotNull(context, "context == null"));
        this.dialog.setIndeterminate(true);
        this.dialog.setCancelable(true);
    }

    static /* synthetic */ void lambda$show$0(Runnable runnable, DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        if (runnable != null) {
            runnable.run();
        }
    }

    public void dismiss() {
        this.canBeShown.set(true);
        this.dialog.dismiss();
    }

    public boolean dismiss(long l) {
        if (this.requestId.compareAndSet(l, 0L)) {
            this.canBeShown.set(true);
            this.dialog.dismiss();
            return true;
        }
        return false;
    }

    public boolean show(long l, String string2, String string3, Runnable runnable) {
        this.requestId.set(l);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setTitle((CharSequence)string2);
        this.dialog.setMessage((CharSequence)string3);
        this.dialog.setOnCancelListener(ProgressDialogHelper$$Lambda$1.lambdaFactory$(runnable));
        this.dialog.show();
        return true;
    }

    public boolean show(String string2, String string3, Runnable runnable) {
        return this.show(0L, string2, string3, runnable);
    }
}

