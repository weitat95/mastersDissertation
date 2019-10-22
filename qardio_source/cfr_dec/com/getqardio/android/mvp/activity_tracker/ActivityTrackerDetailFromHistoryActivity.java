/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.mvp.activity_tracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.googlefit.GoogleClientObservable;
import com.getqardio.android.googlefit.GoogleClientObserver;
import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerDetailFromHistoryActivity$$Lambda$1;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import timber.log.Timber;

public class ActivityTrackerDetailFromHistoryActivity
extends BaseActivity
implements GoogleClientObservable,
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private List<WeakReference<GoogleClientObserver>> registrants = new ArrayList<WeakReference<GoogleClientObserver>>();

    private String getDateTextFromTimestamp(long l) {
        return new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date(l));
    }

    private void goToFragment(long l) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(2131820818, (Fragment)ActivityTrackerTodayFragment.newInstance(l));
        fragmentTransaction.commit();
    }

    static /* synthetic */ void lambda$onConnected$0(Throwable throwable) throws Exception {
        throwable.printStackTrace();
    }

    public static Intent start(Context context, long l) {
        context = new Intent(context, ActivityTrackerDetailFromHistoryActivity.class);
        context.putExtra("com.getqardio.android.extras.DATE_OF_ACTIVITY", l);
        return context;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    @Override
    public void onConnected(Bundle object) {
        object = new GoogleFitApiImpl(this.mGoogleApiClient);
        ((GoogleFitApiImpl)object).startGoogleFitSubscriptions().subscribe(new Consumer<Boolean>((GoogleFitApiImpl)object){
            final /* synthetic */ GoogleFitApiImpl val$googleFitApi;
            {
                this.val$googleFitApi = googleFitApiImpl;
            }

            @Override
            public void accept(Boolean object) throws Exception {
                if (((Boolean)object).booleanValue()) {
                    for (WeakReference weakReference : ActivityTrackerDetailFromHistoryActivity.this.registrants) {
                        if (weakReference.get() == null) continue;
                        ((GoogleClientObserver)weakReference.get()).onConnected(this.val$googleFitApi);
                    }
                }
            }
        }, ActivityTrackerDetailFromHistoryActivity$$Lambda$1.lambdaFactory$());
    }

    @Override
    public void onConnectionFailed(ConnectionResult object) {
        try {
            Timber.e("onConnectionFailed code = " + ((ConnectionResult)((Object)object)).getErrorCode() + " msg = " + ((ConnectionResult)((Object)object)).getErrorMessage(), new Object[0]);
            if (((ConnectionResult)((Object)object)).getErrorCode() == 4) {
                if (((ConnectionResult)((Object)object)).hasResolution()) {
                    ((ConnectionResult)((Object)object)).startResolutionForResult(this, 10001);
                    return;
                }
            } else {
                for (WeakReference<GoogleClientObserver> weakReference : this.registrants) {
                    if (weakReference.get() == null) continue;
                    ((GoogleClientObserver)weakReference.get()).onDisconneced();
                }
            }
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            sendIntentException.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int n) {
        Timber.e("onConnectionSuspended code = " + n, new Object[0]);
        for (WeakReference<GoogleClientObserver> weakReference : this.registrants) {
            if (weakReference.get() == null) continue;
            ((GoogleClientObserver)weakReference.get()).onDisconneced();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968620);
        this.mGoogleApiClient = new GoogleApiClient.Builder((Context)this).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addApi(Fitness.RECORDING_API).addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        long l = this.getIntent().getExtras().getLong("com.getqardio.android.extras.DATE_OF_ACTIVITY");
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        ActivityUtils.getActionBar(this).setTitle(this.getString(2131362094) + " " + this.getDateTextFromTimestamp(l));
        this.goToFragment(l);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
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

