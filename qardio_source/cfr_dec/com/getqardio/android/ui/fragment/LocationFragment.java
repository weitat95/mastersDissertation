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
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsoluteLayout
 *  android.widget.AbsoluteLayout$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
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
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.LocationCluster;
import com.getqardio.android.datamodel.LocationDatesItem;
import com.getqardio.android.datamodel.MinMaxDate;
import com.getqardio.android.device_related_services.map.MapUiHelper;
import com.getqardio.android.device_related_services.map.MapUiHelperImpl;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.device_related_services.map.QPoint;
import com.getqardio.android.provider.LocationClusterManager;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.activity.LocationTagsListActivity;
import com.getqardio.android.ui.activity.PBMeasurementsListByTagActivity;
import com.getqardio.android.ui.adapter.LocationDatesAdapter;
import com.getqardio.android.ui.fragment.LocationFragment$$Lambda$1;
import com.getqardio.android.ui.widget.HorizontalListView;
import com.getqardio.android.utils.Utils;
import com.getqardio.shared.utils.SingleEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
LocationClusterManager.OnClustersDataChangedListener {
    private Calendar calendar = Calendar.getInstance();
    private LocationCluster clusterForChangingTag;
    private SparseArray<LocationDatesAdapter.LocationDateFormatter> dateFormatters;
    private LocationDatesAdapter datesAdapter;
    private final SingleEvent eventLock = new SingleEvent();
    private boolean firstCameraMoving;
    private Handler handler;
    private LayoutInflater inflater;
    private LocationClusterManager locationClusterManager;
    private MapUiHelper mapUiHelper = MapUiHelperImpl.getInstance();
    private int markerHeight;
    private MinMaxDate minMaxDate;
    private AbsoluteLayout overlayContainer;
    private HorizontalListView periodList;
    private SparseIntArray pins;
    private Runnable positionUpdaterRunnable;
    private Spinner timePeriod;

    private QLatLng addClusterToMap(LocationCluster locationCluster, LocationCluster.AvgData avgData) {
        QLatLng qLatLng = new QLatLng(locationCluster.getLatitude(), locationCluster.getLongitude());
        this.mapUiHelper.addPin(qLatLng, this.pins.get(locationCluster.getTag()), false);
        locationCluster = this.getInfoWindow(locationCluster, avgData);
        locationCluster.setTag((Object)qLatLng);
        this.mapUiHelper.convertLocationToScreenPosition(qLatLng, new MapUiHelper.LocationConverted((View)locationCluster){
            final /* synthetic */ View val$infoWindow;
            {
                this.val$infoWindow = view;
            }

            @Override
            public void locationConverted(QPoint qPoint) {
                AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)this.val$infoWindow.getLayoutParams();
                layoutParams.x = qPoint.x - layoutParams.width / 2;
                layoutParams.y = qPoint.y - layoutParams.height - LocationFragment.this.markerHeight;
                LocationFragment.this.overlayContainer.addView(this.val$infoWindow);
            }
        });
        return qLatLng;
    }

    private void changeClusterTag(LocationCluster locationCluster, int n) {
        Activity activity = this.getActivity();
        if (activity != null) {
            MeasurementHelper.BloodPressure.changeMeasurementsTag((Context)activity, this.getUserId(), locationCluster.getMeasurements(), n);
        }
    }

    private MinMaxDate generateDefaultMinMaxDate() {
        MinMaxDate minMaxDate = new MinMaxDate();
        minMaxDate.maxDate = minMaxDate.minDate = new Date();
        return minMaxDate;
    }

    private List<LocationDatesItem> getDates(long l, long l2) {
        switch (this.getSelectedPeriod()) {
            default: {
                return this.getWeeklyDates(l, l2);
            }
            case 0: {
                return this.getWeeklyDates(l, l2);
            }
            case 1: {
                return this.getMonthlyDates(l, l2);
            }
            case 2: 
        }
        return this.getYearlyDates(l, l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private View getInfoWindow(LocationCluster locationCluster, LocationCluster.AvgData avgData) {
        View view = this.inflater.inflate(2130968739, (ViewGroup)this.overlayContainer, false);
        ImageView imageView = (ImageView)view.findViewById(2131821125);
        switch (locationCluster.getTag()) {
            case 1: {
                imageView.setBackgroundResource(2130838022);
                imageView.setImageResource(2130837852);
                break;
            }
            case 2: {
                imageView.setBackgroundResource(2130838023);
                imageView.setImageResource(2130837854);
                break;
            }
            case 3: {
                imageView.setBackgroundResource(2130838024);
                imageView.setImageResource(2130837858);
                break;
            }
            case 4: {
                imageView.setBackgroundResource(2130838021);
                imageView.setImageResource(2130837850);
                break;
            }
            case 5: {
                imageView.setBackgroundResource(2130838019);
                imageView.setImageResource(2130837848);
                break;
            }
            case 6: {
                imageView.setBackgroundResource(2130838020);
                imageView.setImageResource(2130837856);
                break;
            }
            case 0: {
                imageView.setBackgroundResource(2130838020);
                imageView.setImageResource(2130837799);
                break;
            }
        }
        ((TextView)view.findViewById(2131821126)).setText((CharSequence)(Math.round(avgData.getSys()) + "/" + Math.round(avgData.getDia())));
        ((TextView)view.findViewById(2131821127)).setText((CharSequence)Long.toString(Math.round(avgData.getPulse())));
        avgData = view.findViewById(2131821125);
        avgData.setTag((Object)locationCluster);
        avgData.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                LocationFragment.this.onTagIconClick((LocationCluster)view.getTag());
            }
        });
        view.setOnClickListener((View.OnClickListener)new MeasurementClickListener(locationCluster.getTag()));
        return view;
    }

    private List<LocationDatesItem> getMonthlyDates(long l, long l2) {
        ArrayList<LocationDatesItem> arrayList = new ArrayList<LocationDatesItem>();
        this.calendar.setTimeInMillis(l);
        this.calendar.set(14, 0);
        this.calendar.set(13, 0);
        this.calendar.set(12, 0);
        this.calendar.set(11, 0);
        this.calendar.set(5, 1);
        while (arrayList.size() < 8 || this.calendar.getTimeInMillis() <= l2) {
            l = this.calendar.getTimeInMillis();
            this.calendar.add(2, 1);
            long l3 = this.calendar.getTimeInMillis() - 1L;
            arrayList.add(new LocationDatesItem(l, l3, this.locationClusterManager.hasMeasurements(l, l3)));
        }
        return arrayList;
    }

    private int getSelectedPeriod() {
        switch (this.timePeriod.getSelectedItemPosition()) {
            default: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: 
        }
        return 2;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private List<LocationDatesItem> getWeeklyDates(long l, long l2) {
        ArrayList<LocationDatesItem> arrayList = new ArrayList<LocationDatesItem>();
        this.calendar.setTimeInMillis(l);
        this.calendar.set(14, 0);
        this.calendar.set(13, 0);
        this.calendar.set(12, 0);
        this.calendar.set(11, 0);
        this.calendar.set(7, this.calendar.getFirstDayOfWeek());
        Calendar calendar = Calendar.getInstance();
        while (arrayList.size() < 8 || this.calendar.getTimeInMillis() <= l2) {
            Long l3 = this.calendar.getTimeInMillis();
            calendar.setTimeInMillis(l3);
            calendar.add(5, 7);
            Long l4 = calendar.getTimeInMillis() - 1L;
            boolean bl = this.locationClusterManager.hasMeasurements(l3, l4);
            arrayList.add(new LocationDatesItem(l3, l4, bl));
            this.calendar.add(5, 7);
        }
        return arrayList;
    }

    private List<LocationDatesItem> getYearlyDates(long l, long l2) {
        ArrayList<LocationDatesItem> arrayList = new ArrayList<LocationDatesItem>();
        this.calendar.setTimeInMillis(l);
        this.calendar.set(14, 0);
        this.calendar.set(13, 0);
        this.calendar.set(12, 0);
        this.calendar.set(11, 0);
        this.calendar.set(6, 1);
        while (arrayList.size() < 8 || this.calendar.getTimeInMillis() <= l2) {
            l = this.calendar.getTimeInMillis();
            this.calendar.add(1, 1);
            long l3 = this.calendar.getTimeInMillis() - 1L;
            arrayList.add(new LocationDatesItem(l, l3, this.locationClusterManager.hasMeasurements(l, l3)));
        }
        return arrayList;
    }

    private void init(View view) {
        this.overlayContainer = (AbsoluteLayout)view.findViewById(2131821124);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource((Context)view.getContext(), (int)2131755013, (int)17367048);
        view.findViewById(2131820914).setOnClickListener(LocationFragment$$Lambda$1.lambdaFactory$(this));
        this.timePeriod = (Spinner)view.findViewById(2131820915);
        this.timePeriod.setAdapter((SpinnerAdapter)arrayAdapter);
        arrayAdapter.setDropDownViewResource(2130968840);
        this.timePeriod.setDropDownWidth(-1);
        this.timePeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                LocationFragment.this.updateDates();
                LocationFragment.this.updateClustersOnMap(true);
                LocationFragment.this.datesAdapter.setFormatter((LocationDatesAdapter.LocationDateFormatter)LocationFragment.this.dateFormatters.get(LocationFragment.this.getSelectedPeriod()));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.periodList = (HorizontalListView)view.findViewById(2131821122);
        this.datesAdapter = new LocationDatesAdapter((Context)this.getActivity(), (LocationDatesAdapter.LocationDateFormatter)this.dateFormatters.get(this.getSelectedPeriod()), 8);
        this.periodList.setAdapter((ListAdapter)this.datesAdapter);
        this.periodList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                LocationFragment.this.onDateSelected(n);
            }
        });
    }

    public static LocationFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        LocationFragment locationFragment = new LocationFragment();
        locationFragment.setArguments(bundle);
        return locationFragment;
    }

    private void onDateSelected(int n) {
        if (this.datesAdapter.getItem((int)n).clustersExist) {
            this.datesAdapter.setSelectedPosition(n);
            this.updateClustersOnMap(true);
        }
    }

    private void onMeasurementsClick(int n) {
        Activity activity = this.getActivity();
        if (activity != null) {
            activity.startActivity(PBMeasurementsListByTagActivity.getStartIntent((Context)activity, this.getUserId(), n));
        }
    }

    private void onTagIconClick(final LocationCluster locationCluster) {
        this.eventLock.run(new Runnable(){

            @Override
            public void run() {
                Activity activity = LocationFragment.this.getActivity();
                if (activity != null) {
                    LocationFragment.this.clusterForChangingTag = locationCluster;
                    LocationFragment.this.startActivityForResult(LocationTagsListActivity.getStartIntent((Context)activity, locationCluster.getTag()), 1);
                }
            }
        });
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

    private void showMarkers(boolean bl) {
        this.mapUiHelper.addPin(null, 0, true);
        this.overlayContainer.removeAllViews();
        LocationDatesItem locationDatesItem = this.datesAdapter.getSelectedItem();
        if (locationDatesItem != null) {
            ArrayList<QLatLng> arrayList = new ArrayList<QLatLng>();
            for (LocationCluster locationCluster : this.locationClusterManager.getClusters()) {
                LocationCluster.AvgData avgData = locationCluster.getAvgData(locationDatesItem.startDate, locationDatesItem.endDate);
                if (!avgData.hasMeasurements()) continue;
                arrayList.add(this.addClusterToMap(locationCluster, avgData));
            }
            if (bl) {
                this.mapUiHelper.zoomCameraToCoordinatesCenter(arrayList, this.markerHeight * 3);
                this.firstCameraMoving = false;
            }
        }
    }

    private void updateClustersOnMap(final boolean bl) {
        if (this.mapUiHelper.isMapLoaded()) {
            this.showMarkers(bl);
            return;
        }
        this.mapUiHelper.listenOnMapLoaded(new MapUiHelper.MapLoaded(){

            @Override
            public void mapLoaded() {
                LocationFragment.this.showMarkers(bl);
            }
        });
    }

    private void updateDates() {
        LocationDatesItem locationDatesItem = this.datesAdapter.getSelectedItem();
        List<LocationDatesItem> list = this.getDates(this.minMaxDate.minDate.getTime(), this.minMaxDate.maxDate.getTime());
        this.datesAdapter.setItemsList(list);
        if (!this.datesAdapter.setDateSelected(locationDatesItem)) {
            this.datesAdapter.setLastDateSelected();
        }
        this.periodList.scrollTo(this.datesAdapter.getSelectedItemX());
    }

    /* synthetic */ void lambda$init$0(View view) {
        this.timePeriod.performClick();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        if (n == 1) {
            if (n2 == -1 && this.clusterForChangingTag != null) {
                int n3 = intent.getIntExtra("com.getqardio.android.extras.SELECTED_TAG", 0);
                this.changeClusterTag(this.clusterForChangingTag, n3);
            }
            this.clusterForChangingTag = null;
        }
        super.onActivityResult(n, n2, intent);
    }

    @Override
    public void onClustersDataChanged(LocationClusterManager locationClusterManager) {
        this.minMaxDate = MeasurementHelper.BloodPressure.getMinMaxMeasurementsDate((Context)this.getActivity(), this.getUserId());
        if (this.minMaxDate == null) {
            this.minMaxDate = this.generateDefaultMinMaxDate();
        }
        this.updateDates();
        this.updateClustersOnMap(this.firstCameraMoving);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.eventLock.setInterval(400L);
        this.locationClusterManager = new LocationClusterManager();
        this.locationClusterManager.setOnClustersDataChangedListener(this);
        this.dateFormatters = new SparseArray(3);
        this.dateFormatters.put(0, (Object)new WeeklyDateFormatter());
        this.dateFormatters.put(1, (Object)new MonthlylyDateFormatter());
        this.dateFormatters.put(2, (Object)new YearlyDateFormatter());
        this.minMaxDate = this.generateDefaultMinMaxDate();
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return MeasurementHelper.BloodPressure.getMeasurementsWithLocationLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.BloodPressure.MEASUREMENTS_LOCATIONS_PROJECTION);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.inflater = layoutInflater;
        layoutInflater = layoutInflater.inflate(2130968738, viewGroup, false);
        viewGroup = this.mapUiHelper.getMapView((Context)this.getActivity(), bundle, new MapUiHelper.MapInitiated(){

            @Override
            public void mapInitiated() {
                LocationFragment.this.handler = new Handler(Looper.getMainLooper());
                LocationFragment.this.positionUpdaterRunnable = new PositionUpdaterRunnable();
                LocationFragment.this.handler.post(LocationFragment.this.positionUpdaterRunnable);
            }
        });
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
        if (this.handler != null) {
            this.handler.removeCallbacks(this.positionUpdaterRunnable);
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
        this.locationClusterManager.setData(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.locationClusterManager.clear();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mapUiHelper.onLowMemory();
    }

    public void onPause() {
        super.onPause();
        this.mapUiHelper.onPause();
    }

    public void onResume() {
        super.onResume();
        this.mapUiHelper.onResume();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.setupResources();
        this.firstCameraMoving = true;
    }

    private class MeasurementClickListener
    implements View.OnClickListener {
        private int tag;

        private MeasurementClickListener(int n) {
            this.tag = n;
        }

        public void onClick(View view) {
            LocationFragment.this.onMeasurementsClick(this.tag);
        }
    }

    private static class MonthlylyDateFormatter
    implements LocationDatesAdapter.LocationDateFormatter {
        private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("LLL", Utils.getLocale());
        private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Utils.getLocale());

        private MonthlylyDateFormatter() {
        }

        @Override
        public String convertDateToString(LocationDatesItem locationDatesItem) {
            return MONTH_FORMAT.format(locationDatesItem.startDate) + "\n" + YEAR_FORMAT.format(locationDatesItem.startDate);
        }
    }

    private class PositionUpdaterRunnable
    implements Runnable {
        private QLatLng cameraTarget;
        private float zoom;

        private PositionUpdaterRunnable() {
        }

        @Override
        public void run() {
            LocationFragment.this.handler.postDelayed((Runnable)this, 20L);
            LocationFragment.this.mapUiHelper.getCameraPosition(new MapUiHelper.CameraPositionGot(){

                @Override
                public void cameraPositionGot(QLatLng qLatLng, float f) {
                    if (!qLatLng.equals(PositionUpdaterRunnable.this.cameraTarget) || f != PositionUpdaterRunnable.this.zoom) {
                        for (int i = 0; i < LocationFragment.this.overlayContainer.getChildCount(); ++i) {
                            final View view = LocationFragment.this.overlayContainer.getChildAt(i);
                            LocationFragment.this.mapUiHelper.convertLocationToScreenPosition((QLatLng)view.getTag(), new MapUiHelper.LocationConverted(){

                                @Override
                                public void locationConverted(QPoint qPoint) {
                                    ((AbsoluteLayout.LayoutParams)view.getLayoutParams()).x = qPoint.x - view.getWidth() / 2;
                                    ((AbsoluteLayout.LayoutParams)view.getLayoutParams()).y = qPoint.y - view.getHeight() - LocationFragment.this.markerHeight;
                                }
                            });
                        }
                        LocationFragment.this.overlayContainer.requestLayout();
                    }
                    PositionUpdaterRunnable.this.cameraTarget = qLatLng;
                    PositionUpdaterRunnable.this.zoom = f;
                }

            });
        }

    }

    private static class WeeklyDateFormatter
    implements LocationDatesAdapter.LocationDateFormatter {
        private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd", Utils.getLocale());
        private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM", Utils.getLocale());

        private WeeklyDateFormatter() {
        }

        @Override
        public String convertDateToString(LocationDatesItem locationDatesItem) {
            return DAY_FORMAT.format(locationDatesItem.startDate) + "-" + DAY_FORMAT.format(locationDatesItem.endDate) + "\n" + MONTH_FORMAT.format(locationDatesItem.endDate);
        }
    }

    private static class YearlyDateFormatter
    implements LocationDatesAdapter.LocationDateFormatter {
        private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Utils.getLocale());

        private YearlyDateFormatter() {
        }

        @Override
        public String convertDateToString(LocationDatesItem locationDatesItem) {
            return YEAR_FORMAT.format(locationDatesItem.startDate);
        }
    }

}

