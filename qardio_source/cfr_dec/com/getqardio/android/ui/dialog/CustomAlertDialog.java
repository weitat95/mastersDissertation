/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class CustomAlertDialog
extends AlertDialog
implements DialogInterface.OnClickListener {
    public CustomAlertDialog(Context context, String string2, String string3) {
        super(context, 2131493366);
        this.initDialog(string2, string3);
    }

    public static CustomAlertDialog newInstance(Context context, String string2) {
        return new CustomAlertDialog(context, null, string2);
    }

    public static CustomAlertDialog newInstance(Context context, String string2, String string3) {
        return new CustomAlertDialog(context, string2, string3);
    }

    protected void initDialog(String string2, String string3) {
        this.setButton(-1, this.getContext().getText(17039370), this);
        this.setTitle(string2);
        this.setMessage(string3);
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        this.dismiss();
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.setButton(-1, this.getContext().getText(17039370), onClickListener);
    }

    public void setOnNegativeClickListener(DialogInterface.OnClickListener onClickListener) {
        this.setButton(-2, this.getContext().getText(17039360), onClickListener);
    }
}

