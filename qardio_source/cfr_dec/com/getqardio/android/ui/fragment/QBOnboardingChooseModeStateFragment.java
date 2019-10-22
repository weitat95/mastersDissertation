/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.widget.Button
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.Button;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment;
import com.getqardio.android.ui.fragment.QBOnboardingDataProvider;
import com.getqardio.android.utils.analytics.AnalyticsUtil;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.util.HashMap;

public class QBOnboardingChooseModeStateFragment
extends AbstractQBChooseModeListFragment
implements AbstractQBChooseModeListFragment.SelectModeAdapter.ModeSelectionListener {
    private AbstractQBChooseModeListFragment.Callback callback;
    private long userId;

    public static QBOnboardingChooseModeStateFragment newInstance() {
        return new QBOnboardingChooseModeStateFragment();
    }

    private void storeProfile() {
        Object object = this.selectedMode;
        this.callback.onModeSaved(this.selectedMode, null);
        object = AnalyticsUtil.getQardioBaseMode((Context)this.getActivity(), object);
        QardioBaseDeviceAnalyticsTracker.trackModeChanged((Context)this.getActivity(), (String)object);
        object = new CustomTraits();
        String string2 = CustomApplication.getApplication().getCurrentUserTrackingId();
        if (string2 != null) {
            IdentifyHelper.identify((Context)this.getActivity(), string2, (CustomTraits)object);
        }
    }

    @Override
    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        QardioBaseDeviceAnalyticsTracker.trackChooseModeScreen((Context)this.getActivity());
        object = ActivityUtils.getActionBar(this.getActivity());
        if (object != null) {
            ((ActionBar)object).setTitle(2131361889);
        }
        this.callback = (AbstractQBChooseModeListFragment.Callback)this.getParentFragment();
        this.modesListAdapter.setModeSelectionListener(this);
        this.doneButton.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.userId = CustomApplication.getApplication().getCurrentUserId();
    }

    @Override
    protected void onDone() {
        QardioBaseDeviceAnalyticsTracker.trackQbSelectedMode((Context)this.getActivity());
        this.storeProfile();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onModeSelected(QardioBaseDevice.BaseMode baseMode) {
        this.dataProvider.setMode(baseMode);
        if (baseMode != QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            this.selectedMode = baseMode;
        }
        Button button = this.doneButton;
        boolean bl = this.selectedMode != null;
        button.setEnabled(bl);
        if (this.callback != null) {
            this.callback.onModeItemClicked(baseMode);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.QB_USER_CONFIG");
        intentFilter.addAction("com.qardio.base.QB_USER_CONFIG_WRITTEN");
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361889);
        if (DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), this.userId)) {
            this.selectedMode = QardioBaseDevice.BaseMode.MODE_PREGNANCY;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void setPregnancyHideWeight(Boolean object) {
        this.doneButton.setVisibility(8);
        if (object != null) {
            void var1_3;
            if (((Boolean)object).booleanValue()) {
                QardioBaseDevice.BaseMode baseMode = QardioBaseDevice.BaseMode.MODE_PREGNANCY;
            } else {
                QardioBaseDevice.BaseMode baseMode = QardioBaseDevice.BaseMode.MODE_WEIGHT_ONLY;
            }
            this.selectedMode = var1_3;
            this.storeProfile();
        }
    }
}

