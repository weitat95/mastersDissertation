/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.TextView
 */
package com.getqardio.android.ui.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.provider.SyncHelper;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.view.collections.CollectionListActivity;
import com.getqardio.android.shopify.view.qardio.ShopNotAvailableActivity;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.InitProfileActivity;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$1;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$2;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$3;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$4;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$5;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$6;
import com.getqardio.android.ui.activity.SignActivity$$Lambda$7;
import com.getqardio.android.ui.activity.SignActivityCallback;
import com.getqardio.android.ui.activity.SignForgotPasswordActivity;
import com.getqardio.android.ui.activity.WebActivity;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.ui.dialog.CustomProgressDialog;
import com.getqardio.android.ui.fragment.MarketingLoginFragment1;
import com.getqardio.android.ui.fragment.MarketingLoginFragment2;
import com.getqardio.android.ui.fragment.MarketingLoginFragment3;
import com.getqardio.android.ui.fragment.MarketingLoginFragment4;
import com.getqardio.android.ui.fragment.MarketingLoginFragment5;
import com.getqardio.android.ui.fragment.MarketingLoginFragment6;
import com.getqardio.android.ui.fragment.MarketingLoginFragment7;
import com.getqardio.android.ui.fragment.MarketingLoginFragment8;
import com.getqardio.android.ui.fragment.MarketingLoginFragment9;
import com.getqardio.android.ui.fragment.SignFragment;
import com.getqardio.android.ui.fragment.SignInFragment;
import com.getqardio.android.ui.fragment.SignUpFragment;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.KeyboardHelper;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;

public class SignActivity
extends BaseActivity
implements SignActivityCallback,
SignInFragment.Callback,
SignUpFragment.Callback {
    private CustomTabsServiceConnection customTabsServiceConnection;
    private CircleIndicator indicator;
    private boolean isFirstLogin;
    private TextView rightLink;
    private Button signButton;
    private SignFragment signFragment;
    private int signFragmentInt = 11;
    private ViewPager viewPager;

    private void configureBottomPanel() {
        this.signButton = (Button)this.findViewById(2131821364);
        this.rightLink = (TextView)this.findViewById(2131821366);
        this.findViewById(2131821365).setOnClickListener(SignActivity$$Lambda$1.lambdaFactory$(this));
        if (this.getIntent().getBooleanExtra("com.getqardio.android.extras.FROM_NOTIFICATION", false)) {
            this.viewPager.setCurrentItem(10);
        }
        this.updateBottomPanel();
    }

    private void destroyChromeTabs() {
        if (this.customTabsServiceConnection == null) {
            return;
        }
        this.unbindService((ServiceConnection)this.customTabsServiceConnection);
        this.customTabsServiceConnection = null;
    }

    private void doDismissDialog(int n) {
        try {
            this.dismissDialog(n);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public static Intent getStartIntent(Context context, boolean bl) {
        context = new Intent(context, SignActivity.class);
        context.addFlags(268435456);
        context.putExtra("com.getqardio.android.extras.FROM_NOTIFICATION", bl);
        return context;
    }

    private void initChromeTabs() {
        this.customTabsServiceConnection = new CustomTabsServiceConnection(){

            @Override
            public void onCustomTabsServiceConnected(ComponentName object, CustomTabsClient customTabsClient) {
                customTabsClient.warmup(0L);
                object = customTabsClient.newSession(null);
                if (object != null) {
                    ((CustomTabsSession)object).mayLaunchUrl(Uri.parse((String)"https://www.getqardio.com/store?utm_source=qardioapp&utm_campaign=buy%20now%20postlog&utm_medium=referral"), null, null);
                    ((CustomTabsSession)object).mayLaunchUrl(Uri.parse((String)"https://www.getqardio.com/store?utm_source=qardioapp&utm_campaign=buy%20now%20prelog&utm_medium=referral"), null, null);
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        CustomTabsClient.bindCustomTabsService((Context)this, "com.android.chrome", this.customTabsServiceConnection);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateBottomPanel() {
        int n = this.viewPager.getCurrentItem();
        if (n < 9) {
            this.rightLink.setVisibility(0);
            if (!this.isFirstLogin) {
                this.signButton.setText(2131361847);
                this.signButton.setOnClickListener(SignActivity$$Lambda$4.lambdaFactory$(this));
                this.rightLink.setText(2131361894);
                this.rightLink.setOnClickListener(SignActivity$$Lambda$5.lambdaFactory$(this));
                return;
            }
            this.signButton.setText(2131361894);
            this.signButton.setOnClickListener(SignActivity$$Lambda$2.lambdaFactory$(this));
            this.rightLink.setText(2131362052);
            this.rightLink.setOnClickListener(SignActivity$$Lambda$3.lambdaFactory$(this));
            return;
        } else {
            if (n == 9 && this.signFragmentInt == 10) {
                this.signButton.setText(2131361894);
                this.signButton.setOnClickListener(SignActivity$$Lambda$6.lambdaFactory$(this));
                this.rightLink.setVisibility(8);
                return;
            }
            if (n != 9 || this.signFragmentInt != 11) return;
            {
                this.signButton.setText(2131361847);
                this.signButton.setOnClickListener(SignActivity$$Lambda$7.lambdaFactory$(this));
                this.rightLink.setVisibility(8);
                return;
            }
        }
    }

    @Override
    public void dismissProgressDialog() {
        this.doDismissDialog(1);
    }

    @Override
    public void displayForgotPassword() {
        this.startActivity(SignForgotPasswordActivity.getStartIntent((Context)this));
        this.overridePendingTransition(2131034132, 2131034131);
        KeyboardHelper.hideKeyboard((Context)this, this.getCurrentFocus());
    }

    @Override
    public void displayInitProfile() {
        this.startActivityForResult(InitProfileActivity.getStartIntent((Context)this), 1);
        this.overridePendingTransition(2131034132, 2131034131);
    }

    @Override
    public void displayPrivacyPolicy() {
        this.startActivity(WebActivity.getStartIntent((Context)this, this.getString(2131362020), "http://www.getqardio.com/privacy-policy/", true, 2131034130, 2131034133));
        this.overridePendingTransition(2131034132, 2131034131);
    }

    @Override
    public void displayProgressDialog() {
        this.showDialog(1);
    }

    public void displaySignIn() {
        if (this.viewPager.getCurrentItem() < 9) {
            this.viewPager.setCurrentItem(9, true);
            this.signFragment.displaySignIn();
            return;
        }
        this.viewPager.setCurrentItem(9, true);
        this.signFragment.displaySignInWithAnimation();
    }

    @Override
    public void displaySignUp() {
        if (this.viewPager.getCurrentItem() < 9) {
            this.viewPager.setCurrentItem(9, true);
            this.signFragment.displaySignUp();
            return;
        }
        this.viewPager.setCurrentItem(9, true);
        this.signFragment.displaySignUpWithAnimation();
    }

    @Override
    public void displayStartActivity() {
        long l = CustomApplication.getApplication().getCurrentUserId();
        this.startActivity(ChooseDeviceUtils.getMainIntent((Context)this, l));
        MeasurementHelper.Claim.removeAllClaimMeasurements((Context)this, l);
        ((MvpApplication)this.getApplicationContext()).getSyncHelper().syncAll(this.getApplicationContext(), l);
        this.finish();
    }

    @Override
    public void displayTermsOfService() {
        this.startActivity(WebActivity.getStartIntent((Context)this, this.getString(2131362064), "http://www.getqardio.com/terms-and-conditions/", true, 2131034130, 2131034133));
        this.overridePendingTransition(2131034132, 2131034131);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$configureBottomPanel$0(View view) {
        ShopifyAnalytics.getInstance().trackClickBuyNow("welcome");
        view = Utils.isNetworkAvaliable((Context)this) ? new Intent((Context)this, CollectionListActivity.class) : new Intent((Context)this, ShopNotAvailableActivity.class);
        this.startActivity((Intent)view);
    }

    /* synthetic */ void lambda$updateBottomPanel$1(View view) {
        this.displaySignUp();
    }

    /* synthetic */ void lambda$updateBottomPanel$2(View view) {
        this.displaySignIn();
    }

    /* synthetic */ void lambda$updateBottomPanel$3(View view) {
        this.displaySignIn();
    }

    /* synthetic */ void lambda$updateBottomPanel$4(View view) {
        this.displaySignUp();
    }

    /* synthetic */ void lambda$updateBottomPanel$5(View view) {
        this.displaySignUp();
    }

    /* synthetic */ void lambda$updateBottomPanel$6(View view) {
        this.displaySignIn();
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1 && n2 == -1) {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        this.indicator.setVisibility(0);
        if (this.viewPager.getCurrentItem() == 9) {
            KeyboardHelper.hideKeyboard((Context)this, this.getCurrentFocus());
            this.viewPager.setCurrentItem(0, true);
            return;
        }
        super.onBackPressed();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        ActivityUtils.getActionBar(this).hide();
        this.setContentView(2130968828);
        boolean bl = CustomApplication.getApplication().getLastUserId() == null;
        this.isFirstLogin = bl;
        if (object == null) {
            this.signFragment = SignFragment.newInstance(this.isFirstLogin);
        } else {
            this.signFragment = (SignFragment)this.getFragmentManager().getFragment(object, "com.getqardio.android.extras.SIGN_FRAGMENT");
            if (this.signFragment == null) {
                this.signFragment = SignFragment.newInstance(this.isFirstLogin);
            }
        }
        this.indicator = (CircleIndicator)((Object)this.findViewById(2131821363));
        this.viewPager = (ViewPager)((Object)this.findViewById(2131821095));
        this.viewPager.setOffscreenPageLimit(2);
        ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(this.getFragmentManager());
        this.viewPager.setAdapter(screenSlidePagerAdapter);
        this.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onPageSelected(int n) {
                KeyboardHelper.hideKeyboard((Context)SignActivity.this, SignActivity.this.getCurrentFocus());
                SignActivity.this.invalidateOptionsMenu();
                SignActivity.this.updateBottomPanel();
                if (n == 9) {
                    SignActivity.this.indicator.setVisibility(8);
                    return;
                } else {
                    if (n != 8) return;
                    {
                        SignActivity.this.signFragment.hideErrorMarks();
                        SignActivity.this.indicator.setVisibility(0);
                        return;
                    }
                }
            }
        });
        this.indicator.setViewPager(this.viewPager);
        this.configureBottomPanel();
    }

    protected Dialog onCreateDialog(int n) {
        switch (n) {
            default: {
                return super.onCreateDialog(n);
            }
            case 1: {
                return CustomProgressDialog.newInstance((Context)this);
            }
            case 2: 
        }
        return CustomAlertDialog.newInstance((Context)this, this.getText(2131361956).toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.signFragment != null && this.signFragment.isAdded()) {
            this.getFragmentManager().putFragment(bundle, "com.getqardio.android.extras.SIGN_FRAGMENT", (Fragment)this.signFragment);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.initChromeTabs();
    }

    @Override
    public void onStop() {
        this.destroyChromeTabs();
        super.onStop();
    }

    @Override
    public void setSignFragment(int n) {
        this.signFragmentInt = n;
        this.updateBottomPanel();
    }

    private class ScreenSlidePagerAdapter
    extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Fragment getItem(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 0: {
                    return MarketingLoginFragment1.newInstance();
                }
                case 1: {
                    return MarketingLoginFragment2.newInstance();
                }
                case 2: {
                    return MarketingLoginFragment3.newInstance();
                }
                case 3: {
                    return MarketingLoginFragment4.newInstance();
                }
                case 4: {
                    return MarketingLoginFragment5.newInstance();
                }
                case 5: {
                    return MarketingLoginFragment6.newInstance();
                }
                case 6: {
                    return MarketingLoginFragment7.newInstance();
                }
                case 7: {
                    return MarketingLoginFragment8.newInstance();
                }
                case 8: {
                    return MarketingLoginFragment9.newInstance();
                }
                case 9: 
            }
            return SignActivity.this.signFragment;
        }
    }

}

