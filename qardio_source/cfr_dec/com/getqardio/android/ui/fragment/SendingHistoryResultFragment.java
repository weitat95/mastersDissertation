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

public class SendingHistoryResultFragment
extends Fragment {
    private boolean isSentToMyDoctor() {
        boolean bl = false;
        Bundle bundle = this.getArguments();
        boolean bl2 = bl;
        if (bundle != null) {
            bl2 = bl;
            if (bundle.containsKey("com.getqardio.android.argument.SENT_TO_DOCTOR")) {
                bl2 = bl;
                if (bundle.getBoolean("com.getqardio.android.argument.SENT_TO_DOCTOR", false)) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public static SendingHistoryResultFragment newInstance(boolean bl) {
        SendingHistoryResultFragment sendingHistoryResultFragment = new SendingHistoryResultFragment();
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("com.getqardio.android.argument.SENT_TO_DOCTOR", bl);
        sendingHistoryResultFragment.setArguments(bundle);
        return sendingHistoryResultFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968823, viewGroup, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        int n = this.isSentToMyDoctor() ? 2131362066 : 2131362065;
        view = (TextView)view.findViewById(2131821301);
        view.setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
        view.setText(n);
    }
}

