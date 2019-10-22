/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.getqardio.android.ui.activity.QBWifiConfigurationActivity;
import com.getqardio.android.ui.activity.SupportActivity;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateErrorFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateErrorFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateErrorFragment$$Lambda$3;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import timber.log.Timber;

public class QBFirmwareUpdateErrorFragment
extends Fragment {
    private OnDoneClickListener onDoneClickListener;

    private int getError() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.ERROR")) {
            return bundle.getInt("com.getqardio.android.argument.ERROR");
        }
        return -1;
    }

    private void init(View view) {
        TextView textView = (TextView)view.findViewById(2131820567);
        Button button = (Button)view.findViewById(2131821224);
        TextView textView2 = (TextView)view.findViewById(2131821223);
        switch (this.getError()) {
            default: {
                return;
            }
            case 1: {
                button.setOnClickListener(QBFirmwareUpdateErrorFragment$$Lambda$1.lambdaFactory$(this));
                textView.setText(2131362079);
                button.setText(2131362355);
                textView2.setText(2131362076);
                return;
            }
            case 4: 
        }
        button.setOnClickListener(QBFirmwareUpdateErrorFragment$$Lambda$2.lambdaFactory$(this));
        button.setText(2131362071);
        textView.setText(2131362078);
        textView2.setText(2131362080);
        view = (Button)view.findViewById(2131821225);
        view.setVisibility(0);
        view.setOnClickListener(QBFirmwareUpdateErrorFragment$$Lambda$3.lambdaFactory$(this));
    }

    public static QBFirmwareUpdateErrorFragment newInstance(int n) {
        Bundle bundle = new Bundle(1);
        bundle.putInt("com.getqardio.android.argument.ERROR", n);
        QBFirmwareUpdateErrorFragment qBFirmwareUpdateErrorFragment = new QBFirmwareUpdateErrorFragment();
        qBFirmwareUpdateErrorFragment.setArguments(bundle);
        return qBFirmwareUpdateErrorFragment;
    }

    /* synthetic */ void lambda$init$0(View view) {
        this.getActivity().finish();
        this.startActivity(QBWifiConfigurationActivity.createStartIntentFromSettings((Context)this.getActivity()));
    }

    /* synthetic */ void lambda$init$1(View view) {
        this.getActivity().recreate();
    }

    /* synthetic */ void lambda$init$2(View view) {
        if (this.getActivity() != null) {
            this.startActivity(SupportActivity.getStartIntent((Context)this.getActivity()));
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361928);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968787, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    public void onDetach() {
        super.onDetach();
        this.onDoneClickListener = null;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        try {
            this.onDoneClickListener = (OnDoneClickListener)this.getParentFragment();
            return;
        }
        catch (ClassCastException classCastException) {
            Timber.e("Host fragment for the QBFirmwareUpdateErrorFragment should implement OnDoneClickListener", new Object[0]);
            return;
        }
    }
}

