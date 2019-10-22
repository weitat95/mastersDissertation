/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.os.Bundle
 */
package com.getqardio.android.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class CustomProgressDialog
extends ProgressDialog {
    private CustomProgressDialog(Context context, String string2, boolean bl) {
        super(context, 2131493132);
        this.setCancelable(bl);
    }

    public static CustomProgressDialog newInstance(Context context) {
        return CustomProgressDialog.newInstance(context, null, false);
    }

    public static CustomProgressDialog newInstance(Context context, String string2, boolean bl) {
        return new CustomProgressDialog(context, string2, bl);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968783);
    }
}

