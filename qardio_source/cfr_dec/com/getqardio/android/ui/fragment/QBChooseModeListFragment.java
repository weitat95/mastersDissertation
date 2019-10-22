/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.widget.Button
 */
package com.getqardio.android.ui.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment;
import com.getqardio.android.ui.fragment.QBPregnancySetupFragment;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.util.HashMap;

public class QBChooseModeListFragment
extends AbstractQBChooseModeListFragment
implements AbstractQBChooseModeListFragment.SelectModeAdapter.ModeSelectionListener {
    private boolean backFromPregnancy = false;
    private AbstractQBChooseModeListFragment.Callback callback;
    private HashMap<String, Object> modeParameters = null;
    private long userId;

    public static QBChooseModeListFragment newInstance() {
        return new QBChooseModeListFragment();
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.getActivity().getActionBar() != null) {
            this.getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.modesListAdapter.setModeSelectionListener(this);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent object) {
        if (n == 1) {
            void var3_5;
            this.backFromPregnancy = true;
            boolean bl = object.getBooleanExtra("com.getqardio.android.extra.HIDE_WEIGHT", true);
            if (this.modeParameters == null) {
                HashMap hashMap = new HashMap(1);
            } else {
                HashMap<String, Object> hashMap = this.modeParameters;
            }
            this.modeParameters = var3_5;
            this.modeParameters.put("com.getqardio.android.extra.HIDE_WEIGHT", bl);
            this.getFragmentManager().popBackStackImmediate();
            this.callback.onModeSaved(QardioBaseDevice.BaseMode.MODE_PREGNANCY, this.modeParameters);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.callback = (AbstractQBChooseModeListFragment.Callback)activity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.userId = CustomApplication.getApplication().getCurrentUserId();
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    @Override
    protected void onDone() {
        if (this.callback != null) {
            this.callback.onModeSaved(this.selectedMode, this.modeParameters);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onModeSelected(QardioBaseDevice.BaseMode baseMode) {
        boolean bl = true;
        if (baseMode == QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            this.getFragmentManager().beginTransaction().setCustomAnimations(2131099657, 2131099658).replace(2131820778, (Fragment)QBPregnancySetupFragment.newInstance(1, this)).addToBackStack(null).commit();
        }
        if (this.callback != null) {
            this.callback.onModeItemClicked(baseMode);
        }
        if (baseMode != QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            this.selectedMode = baseMode;
        }
        baseMode = this.doneButton;
        if (this.selectedMode == null) {
            bl = false;
        }
        baseMode.setEnabled(bl);
    }

    @Override
    public void onResume() {
        super.onResume();
        new IntentFilter().addAction("com.qardio.base.QB_USER_CONFIG_WRITTEN");
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361889);
        if (DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), this.userId)) {
            this.selectedMode = QardioBaseDevice.BaseMode.MODE_PREGNANCY;
        }
        if (this.backFromPregnancy) {
            this.backFromPregnancy = false;
        }
    }
}

