/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateSuccessFragment$$Lambda$1;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBFirmwareUpdateSuccessFragment
extends Fragment {
    protected OnDoneClickListener onDoneClickListener;

    private String getUpdateVersion() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.UPDATE_VERSION")) {
            return bundle.getString("com.getqardio.android.argument.UPDATE_VERSION");
        }
        return "";
    }

    private void init(View view) {
        ((TextView)view.findViewById(2131821227)).setText((CharSequence)this.getString(2131361930, new Object[]{this.getUpdateVersion()}));
        view.findViewById(2131821228).setOnClickListener(QBFirmwareUpdateSuccessFragment$$Lambda$1.lambdaFactory$(this));
    }

    public static QBFirmwareUpdateSuccessFragment newInstance(String object) {
        Bundle bundle = new Bundle(1);
        bundle.putString("com.getqardio.android.argument.UPDATE_VERSION", object);
        object = new QBFirmwareUpdateSuccessFragment();
        object.setArguments(bundle);
        return object;
    }

    /* synthetic */ void lambda$init$0(View view) {
        if (this.onDoneClickListener != null) {
            this.onDoneClickListener.onDoneClick();
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361928);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968789, viewGroup, false);
    }

    public void onPause() {
        super.onPause();
        this.onDoneClickListener = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onResume() {
        super.onResume();
        try {
            this.onDoneClickListener = (OnDoneClickListener)this.getParentFragment();
        }
        catch (ClassCastException classCastException) {
            this.onDoneClickListener = null;
        }
        this.init(this.getView());
    }
}

