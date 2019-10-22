/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.content.ServiceConnection
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.io.network.ConnectionStateReceiver;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment;
import com.getqardio.android.mvp.friends_family.FriendsFamilyMainFragment;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.view.collections.CollectionListActivity;
import com.getqardio.android.shopify.view.qardio.NoConnectivtyFragment;
import com.getqardio.android.shortcuts.QardioSCHelper;
import com.getqardio.android.ui.activity.EnableBluetoothAndLocationActivity;
import com.getqardio.android.ui.activity.FaqDetailsActivity;
import com.getqardio.android.ui.activity.MainActivity$$Lambda$1;
import com.getqardio.android.ui.activity.MainActivity$$Lambda$2;
import com.getqardio.android.ui.activity.MainActivity$$Lambda$3;
import com.getqardio.android.ui.activity.MainActivity$$Lambda$4;
import com.getqardio.android.ui.activity.SignActivity;
import com.getqardio.android.ui.fragment.BloodPressureFragment;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment;
import com.getqardio.android.ui.fragment.EngineeringModeFragment;
import com.getqardio.android.ui.fragment.FaqListFragment;
import com.getqardio.android.ui.fragment.ProfileFragment;
import com.getqardio.android.ui.fragment.ProgressDialogFragment;
import com.getqardio.android.ui.fragment.SettingsFragment;
import com.getqardio.android.ui.fragment.SupportHostFragment;
import com.getqardio.android.ui.fragment.WeightFragment;
import com.getqardio.android.ui.widget.PickContactView;
import com.getqardio.android.ui.widget.SimpleDrawerListener;
import com.getqardio.android.utils.ContactsHelper;
import com.getqardio.android.utils.UIUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.AnalyticsDevice;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.GeneralAnalyticsTracker;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.analytics.MixpanelHelper;
import com.getqardio.android.utils.notifications.AppNotificationAssistant;
import com.getqardio.android.utils.permission.PermissionUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;
import timber.log.Timber;

public class MainActivity
extends EnableBluetoothAndLocationActivity
implements NoConnectivtyFragment.OnConnectivityChangeListener,
ChooseDeviceFragment.Callback,
FaqListFragment.Callback,
SupportHostFragment.Callback,
PickContactView.Callback {
    private boolean baseFragmentActive;
    private ConnectionStateReceiver connectionStateReceiver;
    private CustomTabsServiceConnection customTabsServiceConnection;
    private CompositeDisposable disposable;
    private DrawerLayout drawerLayout;
    private boolean fromNotification;
    private int isFromQAShortcut = 1;
    private boolean isFromQBShortcut;
    private NavigationView navigationView;
    private int prevSelectedMenuItemId = -1;
    private BroadcastReceiver profileUpdatedNotificationReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            MainActivity.this.loadProfileAndShowData();
        }
    };
    private ProgressDialogFragment progressDialogFragment;
    private boolean qbOnboardedStatus;

    public MainActivity() {
        this.connectionStateReceiver = new ConnectionStateReceiver();
        this.disposable = new CompositeDisposable();
    }

    private void changeFragment(Fragment fragment, String string2) {
        if (fragment != null) {
            this.getFragmentManager().beginTransaction().setCustomAnimations(2131099650, 2131099651).replace(2131821020, fragment, string2).commitAllowingStateLoss();
        }
    }

    private void destroyChromeTabs() {
        if (this.customTabsServiceConnection == null) {
            return;
        }
        this.unbindService((ServiceConnection)this.customTabsServiceConnection);
        this.customTabsServiceConnection = null;
    }

    private void doLogout() {
        CustomApplication.getApplication().getMixpanelApi().flush();
        AuthHelper.logout((Context)this);
        IdentifyHelper.reset((Context)this);
        CustomApplication.getApplication().setBpTab(1);
        this.startActivity(SignActivity.getStartIntent((Context)this, false));
        this.finish();
    }

    public static Intent getStartIntent(Context context) {
        context = new Intent(context, MainActivity.class);
        context.addFlags(67108864);
        return context;
    }

    public static Intent getStartIntent(Context context, int n) {
        context = MainActivity.getStartIntent(context);
        context.putExtra("com.getqardio.android.extras.EXTRA_SELECTED_TAB", n);
        return context;
    }

    public static Intent getStartIntent(Context context, String string2) {
        context = MainActivity.getStartIntent(context);
        context.putExtra("deepLinkFromUrl", string2);
        return context;
    }

    public static Intent getStartIntent(Context context, boolean bl) {
        context = MainActivity.getStartIntent(context);
        context.putExtra("com.getqardio.android.extras.EXTRA_SHOW_CHOOSE_DEVICE", bl);
        return context;
    }

    public static Intent getStartIntent(Context context, boolean bl, int n) {
        context = MainActivity.getStartIntent(context);
        context.addFlags(268435456);
        context.putExtra("com.getqardio.android.extras.FROM_NOTIFICATION", bl);
        context.putExtra("com.getqardio.android.extras.NOTIFICATION_ID", n);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleDeepLink(String string2) {
        int n = -1;
        if (string2.equalsIgnoreCase("qardio://activity")) {
            n = 2131821478;
        }
        if (string2.equalsIgnoreCase("qardio://armhistory") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/armhistory")) {
            n = 2131821476;
            CustomApplication.getApplication().setBpTab(2);
            return n;
        } else {
            if (string2.equalsIgnoreCase("qardio://armreminder") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/armreminder")) {
                CustomApplication.getApplication().setBpTab(3);
                return 2131821476;
            }
            if (string2.equalsIgnoreCase("qardio://armstart") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/armstart")) {
                CustomApplication.getApplication().setBpTab(1);
                return 2131821476;
            }
            if (string2.equalsIgnoreCase("qardio://basefirmwareupdate") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/basefirmwareupdate")) {
                return 2131821483;
            }
            if (string2.equalsIgnoreCase("qardio://basehistory") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/basehistory")) {
                CustomApplication.getApplication().setQBTab(2);
                return 2131821477;
            }
            if (string2.equalsIgnoreCase("qardio://basereminder") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/basereminder")) {
                CustomApplication.getApplication().setQBTab(3);
                return 2131821477;
            }
            if (string2.equalsIgnoreCase("qardio://goal") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/goal")) {
                CustomApplication.getApplication().setQBTab(2);
                CustomApplication.getApplication().setQbHistoryTab(2131821432);
                return 2131821477;
            }
            if (string2.equalsIgnoreCase("qardio://following") || string2.equalsIgnoreCase("qardio://friendsandfamily") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/following") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/friendsandfamily")) {
                return 2131821479;
            }
            if (string2.equalsIgnoreCase("qardio://settings") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/settings")) {
                return 2131821483;
            }
            if (string2.equalsIgnoreCase("qardio://passwordreset") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/passwordreset")) {
                return 2131821484;
            }
            if (string2.equalsIgnoreCase("qardio://buynow") || string2.equalsIgnoreCase("https://dl.getqardio.com/app/buynow")) {
                return 2131821480;
            }
            if (string2.equalsIgnoreCase("shortcut://dl.getqardio.com/app/qarm/history")) {
                this.isFromQAShortcut = 2;
                return 2131821476;
            }
            if (string2.equalsIgnoreCase("shortcut://dl.getqardio.com/app/qbase/history")) {
                this.isFromQBShortcut = true;
                return 2131821477;
            }
            if (string2.equalsIgnoreCase("shortcut://dl.getqardio.com/app/qarm/start")) {
                return 2131821476;
            }
            if (!string2.equalsIgnoreCase("shortcut://dl.getqardio.com/app/fandf")) return n;
            return 2131821479;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleIntentAndSelectTab(Intent intent, CustomApplication customApplication) {
        int n;
        int n2;
        block12: {
            Uri uri;
            block13: {
                uri = intent.getData();
                n = intent.getIntExtra("com.getqardio.android.extras.EXTRA_SELECTED_TAB", 2131821476);
                this.fromNotification = intent.getBooleanExtra("com.getqardio.android.extras.FROM_NOTIFICATION", false);
                if (intent.hasExtra("deepLinkFromNotification")) {
                    n = this.handleDeepLink(intent.getStringExtra("deepLinkFromNotification"));
                }
                if (intent.hasExtra("deepLinkFromUrl")) {
                    n = this.handleDeepLink(intent.getStringExtra("deepLinkFromUrl"));
                }
                n2 = n;
                if (intent.getData() == null) break block12;
                if (uri.toString().contains("https://dl.getqardio.com/app/")) break block13;
                n2 = n;
                if (!uri.toString().contains("shortcut://dl.getqardio.com/app/")) break block12;
            }
            if (customApplication.isUserLoggedIn()) {
                n2 = this.handleDeepLink(intent.getData().toString());
            } else {
                Intent intent2 = new Intent((Context)this, SignActivity.class);
                intent2.putExtra("deepLinkFromUrl", uri.toString());
                this.startActivity(intent2);
                this.finish();
                n2 = n;
            }
        }
        if (intent.getBooleanExtra("com.getqardio.android.extras.EXTRA_SHOW_CHOOSE_DEVICE", false)) {
            this.showChooseDeviceFragment();
            return -1;
        }
        n = n2;
        if (!this.fromNotification) return n;
        switch (intent.getIntExtra("com.getqardio.android.extras.NOTIFICATION_ID", -1)) {
            default: {
                return n2;
            }
            case 1: {
                n = 2131821476;
                customApplication.setBpTab(1);
                return n;
            }
            case 2: 
        }
        n = 2131821477;
        customApplication.setQBTab(1);
        return n;
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

    private boolean isEngineeringModeEnabled() {
        String string2 = CustomApplication.getApplication().getCurrentUserEmail();
        return !TextUtils.isEmpty((CharSequence)string2) && string2.endsWith("@getqardio.com");
    }

    static /* synthetic */ void lambda$onCreate$0() throws Exception {
        Timber.d("sent to server", new Object[0]);
    }

    static /* synthetic */ void lambda$onCreate$1(FCMManager fCMManager, Throwable throwable) throws Exception {
        Timber.e(throwable);
        Timber.d("setting the registration to pending", new Object[0]);
        fCMManager.setRegistrationPending();
    }

    private void loadProfileAndShowData() {
        this.getLoaderManager().restartLoader(0, null, (LoaderManager.LoaderCallbacks)new LoaderManager.LoaderCallbacks<Cursor>(){

            public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
                if (CustomApplication.getApplication().getCurrentUserId() != null) {
                    return DataHelper.ProfileHelper.getProfileLoader((Context)MainActivity.this, CustomApplication.getApplication().getCurrentUserId(), DataHelper.ProfileHelper.PROFILE_SCREEN_PROJECTION);
                }
                return null;
            }

            public void onLoadFinished(Loader<Cursor> object, Cursor cursor) {
                if (cursor.moveToFirst()) {
                    object = DataHelper.ProfileHelper.parseProfile(cursor);
                    cursor = MainActivity.this.navigationView.getHeaderView(0);
                    ((TextView)cursor.findViewById(2131821158)).setText((CharSequence)((Profile)object).buildFullName());
                    ((TextView)cursor.findViewById(2131821061)).setText((CharSequence)((Profile)object).getEmail());
                }
            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        });
    }

    private void navigateTo(int n) {
        this.navigationView.setCheckedItem(n);
        if (this.drawerLayout.isDrawerOpen(8388611)) {
            this.drawerLayout.closeDrawer(8388611);
            return;
        }
        this.onNavigationDrawerItemSelected(n);
    }

    private void setupDrawer(DrawerLayout drawerLayout) {
        drawerLayout.addDrawerListener(new SimpleDrawerListener(){

            @Override
            public void onDrawerOpened(View view) {
                GeneralAnalyticsTracker.trackClickedOnMenu((Context)MainActivity.this);
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, this.toolbar, 2131362517, 2131362516);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(new SimpleDrawerListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onDrawerClosed(View view) {
                int n = UIUtils.getNavigationViewCheckedItemId(MainActivity.this.navigationView);
                if (n == MainActivity.this.prevSelectedMenuItemId || n == -1) {
                    return;
                }
                MainActivity.this.onNavigationDrawerItemSelected(n);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(MainActivity$$Lambda$3.lambdaFactory$(this));
        navigationView.getMenu().findItem(2131821486).setVisible(this.isEngineeringModeEnabled());
        navigationView.getHeaderView(0).setOnClickListener(MainActivity$$Lambda$4.lambdaFactory$(this));
        this.loadProfileAndShowData();
    }

    private void setupMixTracking(CustomApplication customApplication) {
        MixpanelAPI mixpanelAPI = MixpanelHelper.setupMixpanel((Context)this, customApplication.getCurrentUserTrackingId());
        customApplication.setMixpanelApi(mixpanelAPI);
        MixpanelHelper.showNotificationIfAvailable(this, mixpanelAPI);
    }

    private void setupViews() {
        this.navigationView = (NavigationView)((Object)this.findViewById(2131821132));
        this.setupDrawerContent(this.navigationView);
        this.drawerLayout = (DrawerLayout)((Object)this.findViewById(2131821131));
        this.setupDrawer(this.drawerLayout);
    }

    private void showChooseDeviceFragment() {
        this.getFragmentManager().beginTransaction().replace(2131821020, (Fragment)ChooseDeviceFragment.newInstance(), "CHOOSE_DEVICE_TAG").commit();
    }

    @Override
    public void displayFAQDetailsFragment(long l) {
        this.startActivity(FaqDetailsActivity.getStartIntent((Context)this, l));
    }

    /* synthetic */ boolean lambda$setupDrawerContent$2(MenuItem menuItem) {
        this.navigateTo(menuItem.getItemId());
        return true;
    }

    /* synthetic */ void lambda$setupDrawerContent$3(View view) {
        this.navigateTo(2131821485);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        Object object = this.getFragmentManager();
        if (n == 10001) {
            if ((object = object.findFragmentByTag("ACTIVITY_TRACKER_FRAGMENT_TAG")) == null || !(object instanceof ActivityTrackerMainFragment)) return;
            {
                object.onActivityResult(n, n2, intent);
                return;
            }
        } else {
            SettingsFragment settingsFragment = (SettingsFragment)object.findFragmentByTag("SETTINGS_FRAGMENT_TAG");
            if (settingsFragment != null && settingsFragment.isAdded()) {
                settingsFragment.onActivityResult(n, n2, intent);
                return;
            }
            if ((object = (ProfileFragment)object.findFragmentByTag("PROFILE_FRAGMENT_TAG")) == null || !object.isAdded()) return;
            {
                ((ProfileFragment)object).onActivityResult(n, n2, intent);
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = this.getFragmentManager();
        if (this.drawerLayout.isDrawerOpen(8388611)) {
            this.drawerLayout.closeDrawer(8388611);
            return;
        }
        if (fragmentManager.findFragmentByTag("QARDIO_BASE_FRAGMENT_TAG") == null && fragmentManager.findFragmentByTag("QARDIO_ARM_FRAGMENT_TAG") == null && fragmentManager.findFragmentByTag("CHOOSE_DEVICE_TAG") == null && fragmentManager.findFragmentByTag("ACTIVITY_TRACKER_FRAGMENT_TAG") == null) {
            if (this.baseFragmentActive) {
                this.navigateTo(2131821477);
                return;
            }
            this.navigateTo(2131821476);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConnectivityAvailable() {
        this.navigateTo(2131821480);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968742);
        this.setupViews();
        Timber.d("Registering device token", new Object[0]);
        object = CustomApplication.getApplication();
        FCMManager fCMManager = ((MvpApplication)this.getApplicationContext()).getFCMManager();
        if (!fCMManager.isRegisteredForFCM()) {
            String string2 = FirebaseInstanceId.getInstance().getToken();
            Timber.d("FB token = " + string2, new Object[0]);
            if (!TextUtils.isEmpty((CharSequence)CustomApplication.getApplication().getCurrentUserToken()) && string2 != null) {
                Timber.d("Registering the token later in the app", new Object[0]);
                this.disposable.add(fCMManager.registerFCMToken(CustomApplication.getApplication().getCurrentUserToken(), string2).subscribe(MainActivity$$Lambda$1.lambdaFactory$(), MainActivity$$Lambda$2.lambdaFactory$(fCMManager)));
            }
        }
        this.setupMixTracking((CustomApplication)((Object)object));
        int n = this.handleIntentAndSelectTab(this.getIntent(), (CustomApplication)((Object)object));
        if (n == -1) return;
        {
            if (2131821479 == n && ((CustomApplication)this.getApplicationContext()).getCurrentUserToken() == null) {
                this.startActivity(SignActivity.getStartIntent((Context)this, true));
                this.finish();
                return;
            } else {
                this.navigateTo(n);
                if (Build.VERSION.SDK_INT < 26) return;
                {
                    object = new CustomTraits();
                    ((CustomTraits)object).putEnableEssentialNotification(MvpApplication.get((Context)this).getNotificationAssistant().checkChannelEnabled("essential_channel_id")).putActivityTrackerEssentialNotification(MvpApplication.get((Context)this).getNotificationAssistant().checkChannelEnabled("activity_tracker_channel_id"));
                    IdentifyHelper.identify((Context)this, CustomApplication.getApplication().getCurrentUserTrackingId(), (CustomTraits)object);
                    return;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.disposable.clear();
    }

    @Override
    public void onDeviceSelected(int n) {
        this.navigateTo(n);
        switch (n) {
            default: {
                return;
            }
            case 2131821476: {
                GeneralAnalyticsTracker.trackChooseDevice((Context)this, AnalyticsDevice.QA);
                return;
            }
            case 2131821477: 
        }
        GeneralAnalyticsTracker.trackChooseDevice((Context)this, AnalyticsDevice.QB);
    }

    @Override
    public void onHideProgress() {
        this.progressDialogFragment.dismiss();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onNavigationDrawerItemSelected(int n) {
        Fragment fragment = null;
        String string2 = null;
        switch (n) {
            default: {
                string2 = "QARDIO_ARM_FRAGMENT_TAG";
                this.baseFragmentActive = false;
                fragment = BloodPressureFragment.newInstance(this.fromNotification, this.isFromQAShortcut);
                this.isFromQAShortcut = 1;
                break;
            }
            case 2131821476: {
                string2 = "QARDIO_ARM_FRAGMENT_TAG";
                this.baseFragmentActive = false;
                fragment = BloodPressureFragment.newInstance(this.fromNotification, this.isFromQAShortcut);
                this.isFromQAShortcut = 1;
                break;
            }
            case 2131821477: {
                string2 = "QARDIO_BASE_FRAGMENT_TAG";
                this.baseFragmentActive = true;
                fragment = WeightFragment.newInstance(CustomApplication.getApplication().getCurrentUserId(), this.qbOnboardedStatus, this.isFromQBShortcut);
                this.isFromQBShortcut = false;
                this.qbOnboardedStatus = false;
                break;
            }
            case 2131821485: {
                string2 = "PROFILE_FRAGMENT_TAG";
                fragment = ProfileFragment.newInstance(CustomApplication.getApplication().getCurrentUserId());
                break;
            }
            case 2131821479: {
                string2 = "FRIENDS_FRAGMENT_TAG";
                fragment = FriendsFamilyMainFragment.newInstance();
                break;
            }
            case 2131821482: {
                string2 = "HELP_FRAGMENT_TAG";
                fragment = FaqListFragment.newInstance();
                break;
            }
            case 2131821481: {
                string2 = "SUPPORT_FRAGMENT_TAG";
                fragment = SupportHostFragment.newInstance();
                break;
            }
            case 2131821483: {
                string2 = "SETTINGS_FRAGMENT_TAG";
                fragment = SettingsFragment.newInstance(CustomApplication.getApplication().getCurrentUserId());
                break;
            }
            case 2131821480: {
                if (Utils.isNetworkAvaliable((Context)this)) {
                    ShopifyAnalytics.getInstance().trackClickBuyNow("menu");
                    this.navigationView.setCheckedItem(this.prevSelectedMenuItemId);
                    this.startActivity(new Intent((Context)this, CollectionListActivity.class));
                    return;
                }
                fragment = NoConnectivtyFragment.newInstance();
                break;
            }
            case 2131821478: {
                string2 = "ACTIVITY_TRACKER_FRAGMENT_TAG";
                fragment = ActivityTrackerMainFragment.newInstance();
                break;
            }
            case 2131821484: {
                this.doLogout();
                break;
            }
            case 2131821486: {
                string2 = "ENGINEERING_MODE_FRAGMENT_TAG";
                fragment = new EngineeringModeFragment();
            }
        }
        this.prevSelectedMenuItemId = n;
        if (this.drawerLayout != null) {
            this.drawerLayout.closeDrawer(8388611);
        }
        this.changeFragment(fragment, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        switch (n) {
            case 1: {
                if (arrn.length <= 0 || arrn[0] != 0) return;
                {
                    PermissionUtil.BlePermissions.checkFineLocationPermission(this);
                    return;
                }
            }
            default: {
                return;
            }
            case 3: {
                if (arrn.length <= 0 || arrn[0] != 0) return;
                if (PermissionUtil.Contact.hasReadContactsPermission((Context)this)) {
                    ContactsHelper.requestPickContact(this);
                    return;
                } else {
                    break;
                }
            }
        }
        PermissionUtil.Contact.checkReadContactsPermission(this);
    }

    @Override
    public void onShowProgress() {
        if (this.progressDialogFragment == null) {
            this.progressDialogFragment = ProgressDialogFragment.newInstance(false);
        }
        this.progressDialogFragment.show(this.getFragmentManager(), null);
    }

    @Override
    public void onStart() {
        super.onStart();
        QardioSCHelper.getInstance((Context)this.getApplication()).build();
        if (((CustomApplication)this.getApplicationContext()).getCurrentUserId() != null && ((CustomApplication)this.getApplicationContext()).getCurrentUserId() != -1L) {
            if (((CustomApplication)this.getApplicationContext()).isSHealthBPSyncPending()) {
                ShealthDataHelper.BpMeasurements.requestReadMeasurements(this.getApplicationContext(), ((CustomApplication)this.getApplicationContext()).getCurrentUserId());
                ((CustomApplication)this.getApplicationContext()).setSHealthBPSyncPending(false);
            }
            if (((CustomApplication)this.getApplicationContext()).isSHealthWeightSyncPending()) {
                Timber.d("SHealth - isSHealthWeightSyncPending", new Object[0]);
                ShealthDataHelper.WeightMeasurements.requestReadWeightMeasurements(this.getApplicationContext(), ((CustomApplication)this.getApplicationContext()).getCurrentUserId());
                ((CustomApplication)this.getApplicationContext()).setSHealthWeightSyncPending(false);
            }
        }
        if (!PermissionUtil.BlePermissions.hasFineLocationPermission(this) && !PermissionUtil.BlePermissions.hasCourseLocationPermission(this)) {
            PermissionUtil.BlePermissions.checkCoarseLocationPermission(this);
        }
        this.checkLocationSettings();
        LocalBroadcastManager.getInstance((Context)this).registerReceiver(this.profileUpdatedNotificationReceiver, new IntentFilter("com.getqardio.android.Notifications.UPDATE_PROFILE"));
        this.registerReceiver((BroadcastReceiver)this.connectionStateReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.initChromeTabs();
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance((Context)this).unregisterReceiver(this.profileUpdatedNotificationReceiver);
        this.unregisterReceiver((BroadcastReceiver)this.connectionStateReceiver);
        this.destroyChromeTabs();
        super.onStop();
    }

    @Override
    public void requestContactPermissions() {
        PermissionUtil.Contact.checkReadContactsPermission(this);
    }

    @Override
    public void requestPickContact() {
        ContactsHelper.requestPickContact(this);
    }

    public void setQbOnboardedStatus(boolean bl) {
        Timber.d("setQbOnboardedStatus = " + bl, new Object[0]);
        this.qbOnboardedStatus = bl;
    }

}

