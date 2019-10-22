/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SendMeasurementResultFragment
extends Fragment {
    private View rootView;

    public static SendMeasurementResultFragment newInstance() {
        return new SendMeasurementResultFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968821, null, false);
        return this.rootView;
    }

    public void onViewCreated(View view, Bundle bundle) {
        ((TextView)this.rootView.findViewById(2131821298)).setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
    }
}

