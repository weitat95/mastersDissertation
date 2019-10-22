/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.Toast
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.googlefit.BPReadDataActivity$$Lambda$1;
import com.getqardio.android.googlefit.BPReadDataActivity$1$$Lambda$1;
import com.getqardio.android.googlefit.BPReadDataActivity$1$$Lambda$2;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.googlefit.MyAdapter;
import com.getqardio.android.ui.activity.BaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.HealthDataTypes;
import com.google.android.gms.fitness.result.DataReadResult;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BPReadDataActivity
extends BaseActivity {
    private GoogleApiClient googleApiClient;
    private RecyclerView recyclerView;

    /* synthetic */ void lambda$onCreate$0(ConnectionResult connectionResult) {
        Toast.makeText((Context)this, (CharSequence)("Something went wrong! = " + connectionResult.getErrorCode()), (int)0).show();
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968611);
        this.recyclerView = (RecyclerView)this.findViewById(2131820787);
        this.recyclerView.setLayoutManager(new LinearLayoutManager((Context)this, 1, false));
        object = new DividerItemDecoration(this.recyclerView.getContext(), 1);
        this.recyclerView.addItemDecoration((RecyclerView.ItemDecoration)object);
        this.googleApiClient = GoogleFitUtils.buildNewClient((Context)this);
        this.googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            /* synthetic */ void lambda$onConnected$0(DataReadResult object) throws Exception {
                object = ((DataReadResult)object).getDataSet(HealthDataTypes.TYPE_BLOOD_PRESSURE);
                ArrayList<String> arrayList = new ArrayList<String>();
                for (int i = 0; i < ((DataSet)object).getDataPoints().size(); ++i) {
                    arrayList.add(((DataSet)object).getDataPoints().get(i).toString());
                }
                Collections.reverse(arrayList);
                object = new MyAdapter(arrayList.toArray(new String[((DataSet)object).getDataPoints().size()]));
                BPReadDataActivity.this.recyclerView.setAdapter((RecyclerView.Adapter)object);
            }

            @Override
            public void onConnected(Bundle bundle) {
                new GoogleFitApiImpl(BPReadDataActivity.this.googleApiClient).readBloodPressureMeasurements((Context)BPReadDataActivity.this, CustomApplication.getApplication().getCurrentUserId()).subscribe(BPReadDataActivity$1$$Lambda$1.lambdaFactory$(this), BPReadDataActivity$1$$Lambda$2.lambdaFactory$());
            }

            @Override
            public void onConnectionSuspended(int n) {
                Toast.makeText((Context)BPReadDataActivity.this, (CharSequence)("Something went wrong! = " + n), (int)0).show();
            }
        });
        this.googleApiClient.registerConnectionFailedListener(BPReadDataActivity$$Lambda$1.lambdaFactory$(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.googleApiClient.disconnect();
    }

}

