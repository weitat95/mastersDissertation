/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.widget.ScrollView
 *  android.widget.TextView
 */
package com.crashlytics.android.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.crashlytics.android.core.DialogStringResolver;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import java.util.concurrent.CountDownLatch;

class CrashPromptDialog {
    private final AlertDialog.Builder dialog;
    private final OptInLatch latch;

    private CrashPromptDialog(AlertDialog.Builder builder, OptInLatch optInLatch) {
        this.latch = optInLatch;
        this.dialog = builder;
    }

    public static CrashPromptDialog create(Activity object, PromptSettingsData promptSettingsData, final AlwaysSendCallback alwaysSendCallback) {
        final OptInLatch optInLatch = new OptInLatch();
        DialogStringResolver dialogStringResolver = new DialogStringResolver((Context)object, promptSettingsData);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)object);
        object = CrashPromptDialog.createDialogView(object, dialogStringResolver.getMessage());
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                optInLatch.setOptIn(true);
                dialogInterface.dismiss();
            }
        };
        builder.setView((View)object).setTitle((CharSequence)dialogStringResolver.getTitle()).setCancelable(false).setNeutralButton((CharSequence)dialogStringResolver.getSendButtonTitle(), onClickListener);
        if (promptSettingsData.showCancelButton) {
            object = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    optInLatch.setOptIn(false);
                    dialogInterface.dismiss();
                }
            };
            builder.setNegativeButton((CharSequence)dialogStringResolver.getCancelButtonTitle(), (DialogInterface.OnClickListener)object);
        }
        if (promptSettingsData.showAlwaysSendButton) {
            object = new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    alwaysSendCallback.sendUserReportsWithoutPrompting(true);
                    optInLatch.setOptIn(true);
                    dialogInterface.dismiss();
                }
            };
            builder.setPositiveButton((CharSequence)dialogStringResolver.getAlwaysSendButtonTitle(), (DialogInterface.OnClickListener)object);
        }
        return new CrashPromptDialog(builder, optInLatch);
    }

    private static ScrollView createDialogView(Activity activity, String string2) {
        float f = activity.getResources().getDisplayMetrics().density;
        int n = CrashPromptDialog.dipsToPixels(f, 5);
        TextView textView = new TextView((Context)activity);
        textView.setAutoLinkMask(15);
        textView.setText((CharSequence)string2);
        textView.setTextAppearance((Context)activity, 16973892);
        textView.setPadding(n, n, n, n);
        textView.setFocusable(false);
        activity = new ScrollView((Context)activity);
        activity.setPadding(CrashPromptDialog.dipsToPixels(f, 14), CrashPromptDialog.dipsToPixels(f, 2), CrashPromptDialog.dipsToPixels(f, 10), CrashPromptDialog.dipsToPixels(f, 12));
        activity.addView((View)textView);
        return activity;
    }

    private static int dipsToPixels(float f, int n) {
        return (int)((float)n * f);
    }

    public void await() {
        this.latch.await();
    }

    public boolean getOptIn() {
        return this.latch.getOptIn();
    }

    public void show() {
        this.dialog.show();
    }

    static interface AlwaysSendCallback {
        public void sendUserReportsWithoutPrompting(boolean var1);
    }

    private static class OptInLatch {
        private final CountDownLatch latch = new CountDownLatch(1);
        private boolean send = false;

        private OptInLatch() {
        }

        void await() {
            try {
                this.latch.await();
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }

        boolean getOptIn() {
            return this.send;
        }

        void setOptIn(boolean bl) {
            this.send = bl;
            this.latch.countDown();
        }
    }

}

