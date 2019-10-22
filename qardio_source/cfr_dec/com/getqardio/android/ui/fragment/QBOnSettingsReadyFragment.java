/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.OnBoardingProfileProvider;
import com.getqardio.android.ui.fragment.QBOnSettingsReadyFragment$$Lambda$1;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBOnSettingsReadyFragment
extends AbstractQBOnboardingFragment {
    private TextView messageView;
    private TextView titleView;

    public static QBOnSettingsReadyFragment newInstance(boolean bl) {
        return QBOnSettingsReadyFragment.newInstance(bl, null);
    }

    public static QBOnSettingsReadyFragment newInstance(boolean bl, Integer n) {
        QBOnSettingsReadyFragment qBOnSettingsReadyFragment = new QBOnSettingsReadyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.getqardio.android.argument.SHOW_MESSAGE", bl);
        if (n != null) {
            bundle.putInt("com.getqardio.android.argument.MESSAGE", n.intValue());
        }
        qBOnSettingsReadyFragment.setArguments(bundle);
        return qBOnSettingsReadyFragment;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968795;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.onDoneClickListener.onDoneClick();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        bundle = this.getArguments();
        Profile profile = ((OnBoardingProfileProvider)this.getParentFragment()).getProfile();
        if (profile != null) {
            this.titleView.setText((CharSequence)this.getString(2131362216, new Object[]{profile.firstName}));
        }
        if (bundle.containsKey("com.getqardio.android.argument.MESSAGE")) {
            this.messageView.setText(bundle.getInt("com.getqardio.android.argument.MESSAGE"));
        } else {
            this.messageView.setVisibility(8);
        }
        if (!this.getArguments().getBoolean("com.getqardio.android.argument.SHOW_MESSAGE")) {
            this.messageView.setVisibility(4);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.titleView = (TextView)this.rootView.findViewById(2131821229);
        this.messageView = (TextView)this.rootView.findViewById(2131821253);
        view.findViewById(2131821252).setVisibility(0);
        ((Button)this.rootView.findViewById(2131821251)).setOnClickListener(QBOnSettingsReadyFragment$$Lambda$1.lambdaFactory$(this));
    }
}

