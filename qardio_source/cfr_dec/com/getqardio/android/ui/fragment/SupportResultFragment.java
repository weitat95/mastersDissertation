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

public class SupportResultFragment
extends Fragment {
    private View rootView;

    public static SupportResultFragment newInstance() {
        return new SupportResultFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968822, viewGroup, false);
        return this.rootView;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        view = Typeface.create((String)"sans-serif-light", (int)0);
        ((TextView)this.rootView.findViewById(2131821299)).setTypeface((Typeface)view);
        bundle = (TextView)this.rootView.findViewById(2131821300);
        bundle.setTypeface((Typeface)view);
        bundle.setText(2131361892);
    }
}

