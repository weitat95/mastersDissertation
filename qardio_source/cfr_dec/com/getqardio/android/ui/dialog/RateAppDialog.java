/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.utils.RateAppManager;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.RateAppTracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

public class RateAppDialog
extends AlertDialog {
    private final Tweak<String> dialogText;
    private final Tweak<String> dialogTitle;

    public RateAppDialog(Context context) {
        super(context);
        this.dialogTitle = MixpanelAPI.stringTweak("Rate dialog title", context.getString(2131362342));
        this.dialogText = MixpanelAPI.stringTweak("Rate dialog text", context.getString(2131362341));
        this.initDialog();
    }

    private View createTitleView(String string2) {
        View view = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2130968802, null);
        ((TextView)view.findViewById(2131820567)).setText((CharSequence)string2);
        return view;
    }

    private View createView(String string2) {
        View view = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2130968801, null);
        ((TextView)view.findViewById(2131821262)).setText((CharSequence)string2);
        view.findViewById(2131821265).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RateAppDialog.this.onRateQardio();
                RateAppDialog.this.dismiss();
            }
        });
        view.findViewById(2131821263).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RateAppDialog.this.onRemindMeLater();
                RateAppDialog.this.dismiss();
            }
        });
        view.findViewById(2131821264).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RateAppDialog.this.onNoThanks();
                RateAppDialog.this.dismiss();
            }
        });
        return view;
    }

    private void initDialog() {
        this.setCancelable(false);
        this.setCustomTitle(this.createTitleView(this.dialogTitle.get()));
        this.setView(this.createView(this.dialogText.get()));
    }

    public static RateAppDialog newInstance(Context context) {
        return new RateAppDialog(context);
    }

    private void onNoThanks() {
        Context context = this.getContext();
        if (context != null) {
            CustomApplication.getApplication().getRateAppManager(context).onNoThanks(context);
            RateAppTracker.trackNoThanks(context);
        }
    }

    private void onRateDialogWasShown(Context context) {
        CustomApplication.getApplication().getRateAppManager(context).onRateDialogWasShown(context);
    }

    private void onRateQardio() {
        Context context = this.getContext();
        if (context != null) {
            context.startActivity(Utils.getGooglePlayIntent(context.getPackageName()));
            this.onRateDialogWasShown(context);
            RateAppTracker.trackRateQardio(context);
        }
    }

    private void onRemindMeLater() {
        Context context = this.getContext();
        if (context != null) {
            CustomApplication.getApplication().getRateAppManager(context).onRemindMeLater(context);
            RateAppTracker.trackRemindMeLater(context);
        }
    }

}

