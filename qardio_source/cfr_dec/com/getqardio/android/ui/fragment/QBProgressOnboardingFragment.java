/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.ProgressBar
 *  android.widget.Toast
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.QardioBaseSettings;
import com.getqardio.android.handler.QBOnboardingManager;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.QardioBaseSettingsHelper;
import com.getqardio.android.ui.activity.QBOnboardingActivity;
import com.getqardio.android.ui.adapter.QBProgressPagerAdapter;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingDataProvider;
import com.getqardio.android.ui.fragment.QBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import com.getqardio.android.ui.widget.NonSwipeableViewPager;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.getqardio.android.utils.PregnancyUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import com.getqardio.android.utils.wizard.QardioBaseState;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class QBProgressOnboardingFragment
extends AbstractQBOnboardingFragment
implements QBOnboardingManager.BaseCommHandler,
QBSelectWiFiFromBaseOnboardingFragment.SkipWifiDuringConfigurationListener {
    private QBProgressPagerAdapter adapter;
    private LocalBroadcastManager broadcastManager;
    private Context context;
    private QBOnboardingDataProvider dataProvider;
    private Handler handler;
    private QBOnboardingManager onboardingManager;
    @BindView
    NonSwipeableViewPager pager;
    @BindView
    ProgressBar progressBar;
    private boolean progressing = false;
    @BindView
    ImageView smileImage;
    private BroadcastReceiver stateReceiver = new BroadcastReceiver(){
        public boolean isZeroingDone;

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context object, Intent intent) {
            Timber.d("stateReceiver  - %s with extra - %s", intent.getAction(), intent.getIntExtra("com.qardio.base.DATA", 0));
            object = intent.getAction();
            int n = -1;
            switch (((String)object).hashCode()) {
                case 421757567: {
                    if (((String)object).equals("com.qardio.base.DISCONNECTED")) {
                        n = 0;
                    }
                }
                default: {
                    break;
                }
                case -53910355: {
                    if (!((String)object).equals("com.qardio.base.STATE")) break;
                    n = 1;
                }
            }
            switch (n) {
                default: {
                    return;
                }
                case 0: {
                    QBProgressOnboardingFragment.this.proceed();
                    return;
                }
                case 1: {
                    QBProgressOnboardingFragment.this.timeout = false;
                    n = intent.getIntExtra("com.qardio.base.DATA", 0);
                    Timber.d("QB state = " + n, new Object[0]);
                    if (n == 1) {
                        QBProgressOnboardingFragment.this.readStateDelayed();
                        return;
                    }
                    if (n != 2) {
                        QBProgressOnboardingFragment.this.proceed();
                        return;
                    }
                    if (this.isZeroingDone) return;
                    this.isZeroingDone = true;
                    QBProgressOnboardingFragment.this.onboardingManager.zeroing();
                    QBProgressOnboardingFragment.this.readStateDelayed();
                    return;
                }
            }
        }
    };
    private boolean timeout = true;
    @BindView
    View upgradeFailed;
    private String wifiAppsList;
    @BindView
    View wifiConfFailed;

    public QBProgressOnboardingFragment() {
        this.handler = new Handler();
    }

    public static QBProgressOnboardingFragment newInstance() {
        return new QBProgressOnboardingFragment();
    }

    private void proceed() {
        Timber.d("proceed", new Object[0]);
        QBOnboardingActivity qBOnboardingActivity = (QBOnboardingActivity)this.getActivity();
        if (qBOnboardingActivity != null) {
            Long l = CustomApplication.getApplication().getCurrentUserId();
            DataHelper.OnBoardingHelper.setOnboardingFinished((Context)qBOnboardingActivity, l, true);
            DataHelper.DeviceAddressHelper.setDeviceAddress((Context)qBOnboardingActivity, l, qBOnboardingActivity.getDeviceAddress());
            ChooseDeviceUtils.setQardioBaseKnown((Context)qBOnboardingActivity, l, true);
        }
        this.handler.postDelayed(QBProgressOnboardingFragment$$Lambda$4.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(1L));
    }

    private void readStateDelayed() {
        this.handler.postDelayed(QBProgressOnboardingFragment$$Lambda$2.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(2L));
    }

    private void saveQbSettings() {
        QardioBaseDevice.BaseMode baseMode = this.dataProvider.getMode();
        QardioBaseSettings qardioBaseSettings = new QardioBaseSettings();
        qardioBaseSettings.enableHaptic = true;
        qardioBaseSettings.enableComposition = QardioBaseUtils.impedanceFromMode(baseMode);
        QardioBaseSettingsHelper.setCurrentBaseSettings((Context)this.getActivity(), qardioBaseSettings);
    }

    private void setReadTimeout() {
        this.handler.postDelayed(QBProgressOnboardingFragment$$Lambda$3.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(3L));
    }

    private void setupViewPager() {
        this.adapter = new QBProgressPagerAdapter(this.getChildFragmentManager());
        this.pager.setAdapter(this.adapter);
        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int n) {
            }

            @Override
            public void onPageScrolled(int n, float f, int n2) {
            }

            @Override
            public void onPageSelected(int n) {
            }
        });
    }

    @Override
    public String getDeviceAddress() {
        return null;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968784;
    }

    @Override
    public void goToWifiList(String string2) {
        this.wifiAppsList = string2;
        this.wifiConfFailed.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$onboardingFinished$0(boolean bl) {
        FragmentManager fragmentManager = this.getFragmentManager();
        if (this.dataProvider.getMode() == QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            DataHelper.PregnancyDataHelper.setPregnancyModeActive(this.context, CustomApplication.getApplication().getCurrentUserId(), true);
        } else if (this.dataProvider.getMode() != QardioBaseDevice.BaseMode.MODE_PREGNANCY && DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId())) {
            PregnancyUtils.stopPregnancyModeAsync((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId());
        }
        if (fragmentManager != null && this.getActivity() != null && !this.getActivity().isFinishing()) {
            if (!bl) {
                this.proceed();
                return;
            }
            this.onboardingManager.readState();
        }
    }

    /* synthetic */ void lambda$proceed$3() {
        if (!this.progressing) {
            this.onDoneClickListener.onDoneClick();
            this.progressing = true;
        }
    }

    /* synthetic */ void lambda$readStateDelayed$1() {
        this.timeout = true;
        this.onboardingManager.readState();
        this.setReadTimeout();
    }

    /* synthetic */ void lambda$setReadTimeout$2() {
        if (this.timeout) {
            Timber.d("Timeout out", new Object[0]);
            this.proceed();
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.broadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
    }

    public void onPause() {
        super.onPause();
        this.broadcastManager.unregisterReceiver(this.stateReceiver);
        this.onboardingManager.unregisterForActions();
        this.onboardingManager.unregisterForWifiActions();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onResume() {
        super.onResume();
        this.context = this.getActivity();
        this.dataProvider = (QBOnboardingDataProvider)this.getParentFragment();
        this.onboardingManager.registerForActions();
        this.onboardingManager.setProfile(this.dataProvider.getProfile());
        this.onboardingManager.setSelectedMode(this.dataProvider.getMode());
        if (!this.dataProvider.isWifiConfigurationSkippped()) {
            this.onboardingManager.setWifiAndPasswordFromPhone(this.dataProvider.getWifiAp(), this.dataProvider.getWifiPassword());
            this.onboardingManager.registerForWifiActions();
        } else {
            this.onboardingManager.setSkipWifiConfig();
        }
        this.onboardingManager.startOnboarding();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        this.broadcastManager.registerReceiver(this.stateReceiver, intentFilter);
    }

    public void onStart() {
        super.onStart();
        this.onboardingManager = new QBOnboardingManager(this.getActivity(), new QardioBaseManager((Context)this.getActivity()));
        this.onboardingManager.setBaseCommHandler(this);
    }

    public void onStop() {
        super.onStop();
    }

    @OnClick
    void onUpgradeSkipClick() {
    }

    @OnClick
    void onUpgradeTryAgainClick() {
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        this.setupViewPager();
        ((ProgressBar)view.findViewById(2131821222)).getIndeterminateDrawable().setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        this.hideCloseOnBoardingButton();
    }

    @OnClick
    void onWifiTryAgainClick() {
        this.onboardingManager.unregisterForWifiActions();
        ((QBOnboardingFragment)this.getParentFragment()).changeState(QardioBaseState.SELECT_WIFI);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onboardingFailed(QBOnboardingManager.BaseCommHandler.FailReason failReason) {
        Activity activity = this.getActivity();
        if (activity != null) {
            switch (3.$SwitchMap$com$getqardio$android$handler$QBOnboardingManager$BaseCommHandler$FailReason[failReason.ordinal()]) {
                case 1: {
                    Toast.makeText((Context)activity, (int)2131362364, (int)1).show();
                }
                default: {
                    break;
                }
                case 2: {
                    Toast.makeText((Context)activity, (int)2131362338, (int)1).show();
                }
            }
            activity.finish();
        }
    }

    @Override
    public void onboardingFinished(boolean bl) {
        this.saveQbSettings();
        Timber.d("onboardingFinished userStepOn - %s", bl);
        new Handler().postDelayed(QBProgressOnboardingFragment$$Lambda$1.lambdaFactory$(this, bl), 2000L);
    }

    @Override
    public void setProgress(int n, QBOnboardingManager.BaseCommHandler.Page page) {
        Timber.d("setProgress progress - %d, page - %s", new Object[]{n, page});
        this.progressBar.setProgress(n);
        if (n >= 100) {
            this.smileImage.setVisibility(0);
        }
        this.pager.setCurrentItem(this.adapter.getPagePosition(page));
    }

    @Override
    public void skipWifiAndFinish() {
        this.dataProvider.skipWifiConfiguration();
    }

    @Override
    public void upgradingFailed() {
        this.upgradeFailed.setVisibility(0);
    }

}

