/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.ui.dialog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QBFirmwareUpdateProgress
extends Fragment {
    protected View rootView;

    private String getUpdateVersion() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.UPDATE_VERSION")) {
            return bundle.getString("com.getqardio.android.argument.UPDATE_VERSION");
        }
        return "";
    }

    public static QBFirmwareUpdateProgress newInstance(String object) {
        Bundle bundle = new Bundle(1);
        bundle.putString("com.getqardio.android.argument.UPDATE_VERSION", object);
        object = new QBFirmwareUpdateProgress();
        object.setArguments(bundle);
        return object;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968788, viewGroup, false);
        return this.rootView;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((TextView)this.rootView.findViewById(2131821226)).setText((CharSequence)this.getString(2131362438, new Object[]{this.getUpdateVersion()}));
    }
}

