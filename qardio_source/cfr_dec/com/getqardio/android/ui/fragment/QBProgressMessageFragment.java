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
package com.getqardio.android.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QBProgressMessageFragment
extends Fragment {
    public static QBProgressMessageFragment newInstance(int n) {
        QBProgressMessageFragment qBProgressMessageFragment = new QBProgressMessageFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("MESSAGE", n);
        qBProgressMessageFragment.setArguments(bundle);
        return qBProgressMessageFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = (TextView)layoutInflater.inflate(2130968785, viewGroup, false);
        layoutInflater.setText(this.getArguments().getInt("MESSAGE"));
        return layoutInflater;
    }
}

