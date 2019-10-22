/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.Loader
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.SparseIntArray
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsoluteLayout
 *  android.widget.AbsoluteLayout$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.device_related_services.map.MapUiHelper;
import com.getqardio.android.device_related_services.map.MapUiHelperImpl;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.device_related_services.map.QPoint;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.provider.LocationClusterManager;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.activity.LocationTagsListActivity;
import com.getqardio.android.ui.activity.OnBoardingActivity;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment$PositionUpdaterRunnable$$Lambda$1;
import com.getqardio.android.utils.wizard.OnboardingPrefsManager;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Date;
import java.util.HashSet;
import timber.log.Timber;

public class SelectLocationTagFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private Handler handler;
    private View infoWindow;
    private MapUiHelper mapUiHelper = MapUiHelperImpl.getInstance();
    private int markerHeight;
    private BPMeasurement measurement;
    private QLatLng pinLocation;
    private SparseIntArray pins;
    private PositionUpdaterRunnable positionUpdaterRunnable;

    static /* synthetic */ void access$lambda$0(SelectLocationTagFragment selectLocationTagFragment) {
        selectLocationTagFragment.setInitPositionOfInfoWindow();
    }

    private static void applyTag(long l, BPMeasurement bPMeasurement) {
        Observable.just(new Object()).map(SelectLocationTagFragment$$Lambda$3.lambdaFactory$(bPMeasurement, l)).compose(RxUtil.applySchedulers()).subscribe(SelectLocationTagFragment$$Lambda$4.lambdaFactory$(), SelectLocationTagFragment$$Lambda$5.lambdaFactory$());
    }

    private long getMeasurementDate() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.MEASUREMENT_DATE")) {
            return bundle.getLong("com.getqardio.android.argument.MEASUREMENT_DATE");
        }
        return -1L;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void init(View view) {
        this.infoWindow = view.findViewById(2131821280);
        this.infoWindow.setVisibility(8);
    }

    static /* synthetic */ Object lambda$applyTag$2(BPMeasurement bPMeasurement, long l, Object object) throws Exception {
        if (bPMeasurement != null && bPMeasurement.latitude != null && bPMeasurement.longitude != null && bPMeasurement.tag != null) {
            CustomApplication customApplication = CustomApplication.getApplication();
            HashSet<Long> hashSet = new HashSet<Long>();
            hashSet.add(bPMeasurement.measureDate.getTime());
            object = MeasurementHelper.BloodPressure.getMeasurementsWithLocation((Context)customApplication, l, MeasurementHelper.BloodPressure.UPDATE_LOCATION_TAG_PROJECTION);
            LocationClusterManager.findMeasurementsDatesByLocation(new QLatLng(bPMeasurement.latitude, bPMeasurement.longitude), (Cursor)object, hashSet);
            MeasurementHelper.BloodPressure.changeMeasurementsTag((Context)customApplication, l, hashSet, (int)bPMeasurement.tag);
        }
        return null;
        finally {
            object.close();
        }
    }

    static /* synthetic */ void lambda$applyTag$3(Object object) throws Exception {
        Timber.d("saved", new Object[0]);
    }

    public static SelectLocationTagFragment newInstance(long l, long l2) {
        Bundle bundle = new Bundle(2);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putLong("com.getqardio.android.argument.MEASUREMENT_DATE", l2);
        SelectLocationTagFragment selectLocationTagFragment = new SelectLocationTagFragment();
        selectLocationTagFragment.setArguments(bundle);
        return selectLocationTagFragment;
    }

    private void setInfoWindowTagIndicator(int n) {
        ImageView imageView = (ImageView)this.infoWindow.findViewById(2131821125);
        switch (n) {
            default: {
                return;
            }
            case 1: {
                imageView.setBackgroundResource(2130838022);
                imageView.setImageResource(2130837852);
                return;
            }
            case 2: {
                imageView.setBackgroundResource(2130838023);
                imageView.setImageResource(2130837854);
                return;
            }
            case 3: {
                imageView.setBackgroundResource(2130838024);
                imageView.setImageResource(2130837858);
                return;
            }
            case 4: {
                imageView.setBackgroundResource(2130838021);
                imageView.setImageResource(2130837850);
                return;
            }
            case 5: {
                imageView.setBackgroundResource(2130838019);
                imageView.setImageResource(2130837848);
                return;
            }
            case 6: {
                imageView.setBackgroundResource(2130838020);
                imageView.setImageResource(2130837856);
                return;
            }
            case 0: 
        }
        imageView.setBackgroundResource(2130838020);
        imageView.setImageResource(2130837799);
    }

    private void setInitPositionOfInfoWindow() {
        this.mapUiHelper.convertLocationToScreenPosition(this.pinLocation, SelectLocationTagFragment$$Lambda$2.lambdaFactory$(this));
    }

    private void setMeasurementData(Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.measurement = MeasurementHelper.BloodPressure.parseMeasurement(cursor);
            this.setupInfoWindow(this.measurement);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupInfoWindow(BPMeasurement bPMeasurement) {
        this.measurement = bPMeasurement;
        int n = bPMeasurement.tag != null ? bPMeasurement.tag : 0;
        this.setInfoWindowTagIndicator(n);
        ((TextView)this.infoWindow.findViewById(2131821126)).setText((CharSequence)(Math.round(bPMeasurement.sys.intValue()) + "/" + Math.round(bPMeasurement.dia.intValue())));
        ((TextView)this.infoWindow.findViewById(2131821127)).setText((CharSequence)Long.toString(Math.round(bPMeasurement.pulse.intValue())));
        this.pinLocation = new QLatLng(bPMeasurement.latitude, bPMeasurement.longitude);
        this.mapUiHelper.addPin(this.pinLocation, this.pins.get(n), false);
        if (this.mapUiHelper.isMapLoaded()) {
            this.setInitPositionOfInfoWindow();
            return;
        }
        this.mapUiHelper.listenOnMapLoaded(SelectLocationTagFragment$$Lambda$1.lambdaFactory$(this));
    }

    private void setupResources() {
        this.markerHeight = this.getResources().getDrawable(2130837921).getIntrinsicHeight();
        this.pins = new SparseIntArray();
        this.pins.put(1, 2130837921);
        this.pins.put(2, 2130837922);
        this.pins.put(3, 2130837923);
        this.pins.put(4, 2130837920);
        this.pins.put(5, 2130837918);
        this.pins.put(6, 2130837919);
        this.pins.put(0, 2130837919);
    }

    private void startInfoWindowUpdate() {
        this.handler = new Handler(Looper.getMainLooper());
        this.positionUpdaterRunnable = new PositionUpdaterRunnable();
        this.handler.post((Runnable)this.positionUpdaterRunnable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void startTagSelectorActivity() {
        Activity activity = this.getActivity();
        if (activity != null) {
            int n = this.measurement.tag != null ? this.measurement.tag : 0;
            this.startActivityForResult(LocationTagsListActivity.getStartIntent((Context)activity, n), 1);
        }
    }

    /* synthetic */ void lambda$null$0(View view) {
        this.startTagSelectorActivity();
    }

    /* synthetic */ void lambda$setInitPositionOfInfoWindow$1(QPoint qPoint) {
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)this.infoWindow.getLayoutParams();
        layoutParams.x = qPoint.x - layoutParams.width / 2;
        layoutParams.y = qPoint.y - layoutParams.height - this.markerHeight;
        this.infoWindow.setOnClickListener(SelectLocationTagFragment$$Lambda$6.lambdaFactory$(this));
        this.infoWindow.setVisibility(0);
        this.mapUiHelper.zoomCamera(this.pinLocation, 14.0f);
        this.startInfoWindowUpdate();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.measurement != null && this.measurement.latitude != null && this.measurement.longitude != null) {
            this.setupInfoWindow(this.measurement);
            return;
        }
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        if (n == 1 && n2 == -1) {
            int n3 = intent.getIntExtra("com.getqardio.android.extras.SELECTED_TAG", 0);
            this.measurement.tag = n3;
            this.mapUiHelper.addPin(this.pinLocation, this.pins.get(n3), true);
            this.setInfoWindowTagIndicator(n3);
        }
        super.onActivityResult(n, n2, intent);
    }

    public void onBackPressed() {
        SelectLocationTagFragment.applyTag(this.getUserId(), this.measurement);
    }

    public void onCreate(Bundle bundle) {
        if (bundle != null && bundle.containsKey("com.getqardio.android.extra.MEASUREMENT")) {
            this.measurement = (BPMeasurement)bundle.getParcelable("com.getqardio.android.extra.MEASUREMENT");
            bundle.remove("com.getqardio.android.extra.MEASUREMENT");
        }
        super.onCreate(bundle);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return MeasurementHelper.BloodPressure.getMeasurementsLoaderByDate((Context)this.getActivity(), this.getUserId(), this.getMeasurementDate(), null);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886095, menu2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.setHasOptionsMenu(true);
        layoutInflater = layoutInflater.inflate(2130968814, viewGroup, false);
        viewGroup = this.mapUiHelper.getMapView((Context)this.getActivity(), bundle, null);
        ((FrameLayout)layoutInflater.findViewById(2131821123)).addView((View)viewGroup, 0, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mapUiHelper.onDestroy();
        if (this.handler != null && this.positionUpdaterRunnable != null) {
            this.handler.removeCallbacks((Runnable)this.positionUpdaterRunnable);
            this.handler = null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.setMeasurementData(cursor);
        this.getLoaderManager().destroyLoader(1);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mapUiHelper.onLowMemory();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131821494: 
        }
        SelectLocationTagFragment.applyTag(this.getUserId(), this.measurement);
        if (!OnboardingPrefsManager.isOutroOnboardingDiscovered()) {
            this.startActivity(OnBoardingActivity.createStartIntent((Context)this.getActivity(), true));
            OnboardingPrefsManager.updateOutroOnboardingDiscovered();
        }
        this.getActivity().finish();
        return true;
    }

    public void onPause() {
        super.onPause();
        this.mapUiHelper.onPause();
    }

    public void onResume() {
        super.onResume();
        this.mapUiHelper.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.measurement != null) {
            bundle.putParcelable("com.getqardio.android.extra.MEASUREMENT", (Parcelable)this.measurement);
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.setupResources();
    }

    private class PositionUpdaterRunnable
    implements Runnable {
        private int prevX;
        private int prevY;

        private PositionUpdaterRunnable() {
        }

        private void updatePosition(QPoint qPoint) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)SelectLocationTagFragment.this.infoWindow.getLayoutParams();
            layoutParams.x = qPoint.x - SelectLocationTagFragment.this.infoWindow.getWidth() / 2;
            layoutParams.y = qPoint.y - SelectLocationTagFragment.this.infoWindow.getHeight() - SelectLocationTagFragment.this.markerHeight;
            if (layoutParams.x != this.prevX || layoutParams.y != this.prevY) {
                this.prevX = layoutParams.x;
                this.prevY = layoutParams.y;
                SelectLocationTagFragment.this.infoWindow.requestLayout();
            }
        }

        /* synthetic */ void lambda$run$0(QPoint qPoint) {
            this.updatePosition(qPoint);
        }

        @Override
        public void run() {
            SelectLocationTagFragment.this.handler.postDelayed((Runnable)this, 20L);
            SelectLocationTagFragment.this.mapUiHelper.convertLocationToScreenPosition(SelectLocationTagFragment.this.pinLocation, SelectLocationTagFragment$PositionUpdaterRunnable$$Lambda$1.lambdaFactory$(this));
        }
    }

}

