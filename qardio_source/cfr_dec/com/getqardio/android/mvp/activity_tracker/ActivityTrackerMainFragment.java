/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.TransitionDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.activity_tracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.googlefit.ActivityGoalsJobService;
import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat;
import com.getqardio.android.googlefit.GoogleClientObservable;
import com.getqardio.android.googlefit.GoogleClientObserver;
import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment$$Lambda$1;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment$$Lambda$2;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerMainFragment$$Lambda$3;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerPageAdapter;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.utils.analytics.ActivityTrackerAnalytics;
import com.getqardio.android.utils.analytics.GeneralAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.Convert;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class ActivityTrackerMainFragment
extends Fragment
implements GoogleClientObservable,
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private View enablingActivityTracker;
    private boolean isConnectionCheckInProgress = true;
    private GoogleApiClient mGoogleApiClient;
    private boolean needsAuthorization;
    private List<WeakReference<GoogleClientObserver>> registrants = new ArrayList<WeakReference<GoogleClientObserver>>();
    @BindView
    TabLayout tabLayout;
    private boolean tabTransitionShown;
    private TransitionDrawable transition;
    @BindView
    ViewPager viewPager;

    static /* synthetic */ void lambda$onConnected$2(Throwable throwable) throws Exception {
        throwable.printStackTrace();
    }

    public static ActivityTrackerMainFragment newInstance() {
        return new ActivityTrackerMainFragment();
    }

    private void setupViews() {
        this.tabLayout.setVisibility(4);
        this.viewPager.setVisibility(4);
        this.viewPager.setAdapter(new ActivityTrackerPageAdapter((Context)this.getActivity(), this.getChildFragmentManager()));
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int n = tab.getPosition();
                if (n == 0) {
                    GeneralAnalyticsTracker.trackActivityTrackerTodayScreen((Context)ActivityTrackerMainFragment.this.getActivity());
                    return;
                } else {
                    if (n != 1) return;
                    {
                        GeneralAnalyticsTracker.trackActivityTrackerHistoryScreen((Context)ActivityTrackerMainFragment.this.getActivity());
                        return;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
        });
        this.transition = (TransitionDrawable)this.tabLayout.getBackground();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void enableContextualToolbarMode(boolean bl) {
        if (this.getActivity() == null) return;
        if (bl) {
            ActivityUtils.changeStatusBarColor(this.getActivity(), ContextCompat.getColor((Context)this.getActivity(), 2131689549));
            this.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor((Context)this.getActivity(), 2131689621));
            if (this.tabTransitionShown) return;
            {
                this.transition.startTransition(250);
                this.tabTransitionShown = true;
                return;
            }
        }
        ActivityUtils.changeStatusBarColor(this.getActivity(), ContextCompat.getColor((Context)this.getActivity(), 2131689600));
        this.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor((Context)this.getActivity(), 2131689548));
        if (!this.tabTransitionShown) {
            return;
        }
        this.transition.reverseTransition(250);
        this.tabTransitionShown = false;
    }

    /* synthetic */ void lambda$onConnected$1(Boolean object) throws Exception {
        block6: {
            block5: {
                if (!((Boolean)((Object)object)).booleanValue()) break block5;
                for (WeakReference<GoogleClientObserver> weakReference : this.registrants) {
                    if (weakReference.get() == null) continue;
                    ((GoogleClientObserver)weakReference.get()).onConnected(new GoogleFitApiImpl(this.mGoogleApiClient));
                }
                if (Build.VERSION.SDK_INT < 21) break block6;
                if (!ActivityGoalsJobService.areJobsScheduled((Context)this.getActivity())) {
                    ActivityGoalsJobService.scheduleActivityGoalJob((Context)this.getActivity());
                }
            }
            return;
        }
        ActivityGoalsJobServiceCompat.scheduleActivityGoalJob((Context)this.getActivity());
    }

    /* synthetic */ void lambda$onCreateView$0(View view) {
        this.isConnectionCheckInProgress = false;
        this.mGoogleApiClient.reconnect();
        ActivityTrackerAnalytics.trackActivityTrackerEnabled((Context)this.getActivity(), true, "Activity tracker");
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        block3: {
            block2: {
                if (10001 != n) break block2;
                if (-1 != n2) break block3;
                this.mGoogleApiClient.reconnect();
            }
            return;
        }
        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("google_fit_activity_tracker", false).apply();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Timber.d("onConnected", new Object[0]);
        this.isConnectionCheckInProgress = false;
        this.needsAuthorization = false;
        if (this.getActivity() != null) {
            this.enablingActivityTracker.setVisibility(8);
            this.tabLayout.setVisibility(0);
            this.viewPager.setVisibility(0);
            PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("google_fit_activity_tracker", true).apply();
            new GoogleFitApiImpl(this.mGoogleApiClient).startGoogleFitSubscriptions().subscribe(ActivityTrackerMainFragment$$Lambda$2.lambdaFactory$(this), ActivityTrackerMainFragment$$Lambda$3.lambdaFactory$());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onConnectionFailed(ConnectionResult iterator) {
        Timber.e("onConnectionFailed code = " + ((ConnectionResult)((Object)iterator)).getErrorCode() + " msg = " + ((ConnectionResult)((Object)iterator)).getErrorMessage(), new Object[0]);
        if (((ConnectionResult)((Object)iterator)).getErrorCode() == 4) {
            if (this.getActivity() != null) {
                this.tabLayout.setVisibility(4);
                this.viewPager.setVisibility(4);
                this.enablingActivityTracker.setVisibility(0);
                if (!this.isConnectionCheckInProgress) {
                    try {
                        if (((ConnectionResult)((Object)iterator)).hasResolution()) {
                            ((ConnectionResult)((Object)iterator)).startResolutionForResult(this.getActivity(), 10001);
                        }
                    }
                    catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
            this.needsAuthorization = true;
        } else {
            for (WeakReference<GoogleClientObserver> weakReference : this.registrants) {
                if (weakReference.get() == null) continue;
                ((GoogleClientObserver)weakReference.get()).onDisconneced();
            }
        }
        this.isConnectionCheckInProgress = false;
    }

    @Override
    public void onConnectionSuspended(int n) {
        Timber.e("onConnectionSuspended code = " + n, new Object[0]);
        for (WeakReference<GoogleClientObserver> weakReference : this.registrants) {
            if (weakReference.get() == null) continue;
            ((GoogleClientObserver)weakReference.get()).onDisconneced();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mGoogleApiClient = new GoogleApiClient.Builder((Context)this.getActivity()).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addApi(Fitness.RECORDING_API).addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968624, viewGroup, false);
        ButterKnife.bind(this, (View)layoutInflater);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362094);
        this.enablingActivityTracker = layoutInflater.findViewById(2131820827);
        this.enablingActivityTracker.setBackgroundColor(0);
        layoutInflater.findViewById(2131821015).setVisibility(4);
        viewGroup = (TextView)layoutInflater.findViewById(2131821013);
        viewGroup = (TextView)layoutInflater.findViewById(2131821014);
        layoutInflater.findViewById(2131821016).setOnClickListener(ActivityTrackerMainFragment$$Lambda$1.lambdaFactory$(this));
        this.setHasOptionsMenu(true);
        this.transition = (TransitionDrawable)this.tabLayout.getBackground();
        this.tabTransitionShown = false;
        if (this.tabLayout.getSelectedTabPosition() == 0) {
            GeneralAnalyticsTracker.trackActivityTrackerTodayScreen((Context)this.getActivity());
        }
        this.setupViews();
        return layoutInflater;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mGoogleApiClient = null;
    }

    public void onPause() {
        super.onPause();
        this.enableContextualToolbarMode(false);
    }

    public void onStart() {
        block5: {
            block4: {
                super.onStart();
                if (Build.VERSION.SDK_INT >= 21) {
                    ((MainActivity)this.getActivity()).getToolbar().setElevation(Convert.convertDpToPixel(0.0f, (Context)this.getActivity()));
                }
                if (this.needsAuthorization) break block4;
                if (!PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("google_fit_activity_tracker", false)) break block5;
                this.mGoogleApiClient.connect();
            }
            return;
        }
        this.tabLayout.setVisibility(4);
        this.viewPager.setVisibility(4);
        this.enablingActivityTracker.setVisibility(0);
    }

    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= 21) {
            ((MainActivity)this.getActivity()).getToolbar().setElevation(this.getActivity().getResources().getDimension(2131427686));
        }
        this.mGoogleApiClient.disconnect();
    }

    @Override
    public void registerForGoogleClientChanges(GoogleClientObserver googleClientObserver) {
        this.registrants.add(new WeakReference<GoogleClientObserver>(googleClientObserver));
    }

    @Override
    public void unregisterForGoogleClientChanges(GoogleClientObserver googleClientObserver) {
        for (int i = 0; i < this.registrants.size(); ++i) {
            if (this.registrants.get(i) == null || !googleClientObserver.equals(this.registrants.get(i))) continue;
            this.registrants.get(i).clear();
            this.registrants.remove(i);
        }
    }

}

