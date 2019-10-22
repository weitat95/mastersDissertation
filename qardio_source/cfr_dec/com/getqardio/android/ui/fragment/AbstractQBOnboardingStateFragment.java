/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;

public abstract class AbstractQBOnboardingStateFragment
extends AbstractQBOnboardingFragment {
    protected TextView title;

    @Override
    protected int getLayoutResource() {
        return 2130968796;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        view = this.getArguments();
        this.title = (TextView)this.rootView.findViewById(2131821229);
        this.title.setText(view.getInt("com.getqardio.android.argument.TITLE_RES_ID"));
    }
}

