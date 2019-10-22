/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsCheckerImpl;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingChooseModeStateFragment;
import com.getqardio.android.ui.fragment.QBOnboardingDataProvider;
import com.getqardio.android.ui.fragment.QBOnboardingGetReadyStateFragment;
import com.getqardio.android.ui.fragment.QBPregnancySetupFragment;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import com.getqardio.android.utils.wizard.OnOnboardingFinishedListener;
import com.getqardio.android.utils.wizard.QardioBaseOnboardingWizard;
import com.getqardio.android.utils.wizard.QardioBaseState;
import java.util.HashMap;

public class QBOnboardingFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
AbstractQBChooseModeListFragment.Callback,
QBOnboardingDataProvider,
QBOnboardingGetReadyStateFragment.onSelectQBVersionListener,
QBPregnancySetupFragment.OnPregnancyDataSaveListener,
OnDoneClickListener,
OnOnboardingFinishedListener,
QardioBaseOnboardingWizard.OnStateChangedListener {
    private boolean isWifiConfSkept;
    private QardioBaseDevice.BaseMode onBoardingMode;
    private Profile profile;
    private WifiAp wifiAp;
    private String wifiPassword;
    private QardioBaseOnboardingWizard wizard;

    public static QBOnboardingFragment newInstance() {
        return new QBOnboardingFragment();
    }

    public void changeState(QardioBaseState qardioBaseState) {
        this.wizard.setState(qardioBaseState);
    }

    @Override
    public QardioBaseDevice.BaseMode getMode() {
        return this.onBoardingMode;
    }

    @Override
    public Profile getProfile() {
        return this.profile;
    }

    @Override
    public WifiAp getWifiAp() {
        return this.wifiAp;
    }

    @Override
    public String getWifiPassword() {
        return this.wifiPassword;
    }

    @Override
    public boolean isWifiConfigurationSkippped() {
        return this.isWifiConfSkept;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.wizard = new QardioBaseOnboardingWizard(this);
        this.wizard.setOnboardingFinishedListener(this);
        if (!LocationServiceSettingsCheckerImpl.getInstance().isLocationAvailable((Context)this.getActivity()) && this.getActivity() instanceof MainActivity) {
            ((MainActivity)this.getActivity()).checkLocationSettings();
        }
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        FragmentManager fragmentManager;
        if (n == 1 && (fragmentManager = this.getChildFragmentManager()).popBackStackImmediate() && (fragmentManager = fragmentManager.findFragmentById(2131820778)) instanceof QBOnboardingChooseModeStateFragment) {
            ((QBOnboardingChooseModeStateFragment)fragmentManager).setPregnancyHideWeight(intent.getBooleanExtra("com.getqardio.android.extra.HIDE_WEIGHT", true));
        }
        super.onActivityResult(n, n2, intent);
    }

    public void onBluetoothStateChange(boolean bl) {
        if (bl) {
            this.wizard.setBluetoothOn();
            return;
        }
        this.wizard.setBluetoothOff();
    }

    @Override
    public void onBoardingStateChanged(AbstractQBOnboardingFragment abstractQBOnboardingFragment) {
        try {
            if (abstractQBOnboardingFragment.isTransitionEnabled()) {
                this.getChildFragmentManager().beginTransaction().setCustomAnimations(2131099657, 2131099658).replace(2131820778, (Fragment)abstractQBOnboardingFragment).commitAllowingStateLoss();
                return;
            }
            this.getChildFragmentManager().beginTransaction().replace(2131820778, (Fragment)abstractQBOnboardingFragment).commitAllowingStateLoss();
            return;
        }
        catch (IllegalStateException illegalStateException) {
            return;
        }
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        bundle = null;
        if (n == 1) {
            bundle = DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), null);
        }
        return bundle;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.getActivity().getWindow().addFlags(128);
        return layoutInflater.inflate(2130968786, viewGroup, false);
    }

    @Override
    public void onDoneClick() {
        this.wizard.changeState();
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == 1 && cursor.moveToFirst()) {
            this.profile = DataHelper.ProfileHelper.parseProfile(cursor);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onModeItemClicked(QardioBaseDevice.BaseMode baseMode) {
        if (baseMode == QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            this.getChildFragmentManager().beginTransaction().setCustomAnimations(2131099657, 2131099658).replace(2131820778, (Fragment)QBPregnancySetupFragment.newInstance(1, this)).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    @Override
    public void onModeSaved(QardioBaseDevice.BaseMode baseMode, HashMap<String, Object> hashMap) {
        this.wizard.changeState();
    }

    @Override
    public void onOnBoardingFinished() {
        Intent intent = new Intent();
        intent.putExtra("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING", true);
        if (this.getActivity() != null && !this.getActivity().isFinishing()) {
            this.getActivity().setResult(-1, intent);
            this.getActivity().finish();
        }
    }

    @Override
    public void onPregnancyDataSaved(boolean bl) {
        FragmentManager fragmentManager = this.getChildFragmentManager();
        if (fragmentManager.popBackStackImmediate() && (fragmentManager = fragmentManager.findFragmentById(2131820778)) instanceof QBOnboardingChooseModeStateFragment) {
            ((QBOnboardingChooseModeStateFragment)fragmentManager).setPregnancyHideWeight(bl);
        }
    }

    @Override
    public void onSelectQBVersion(int n) {
        this.wizard.setQbVersion(n);
    }

    @Override
    public void setMode(QardioBaseDevice.BaseMode baseMode) {
        this.onBoardingMode = baseMode;
    }

    @Override
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void setWifiAp(WifiAp wifiAp, String string2) {
        this.wifiAp = wifiAp;
        this.wifiPassword = string2;
    }

    @Override
    public void skipWifiConfiguration() {
        this.isWifiConfSkept = true;
    }
}

