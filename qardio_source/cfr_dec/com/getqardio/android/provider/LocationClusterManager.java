/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.location.Location
 */
package com.getqardio.android.provider;

import android.database.Cursor;
import android.location.Location;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.LocationCluster;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.provider.MeasurementHelper;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LocationClusterManager {
    private List<LocationCluster> clusters = new LinkedList<LocationCluster>();
    private float[] distanceResults = new float[1];
    private OnClustersDataChangedListener onClustersDataChangedListener;

    public static void findMeasurementsDatesByLocation(QLatLng qLatLng, Cursor cursor, Collection<Long> collection) {
        float[] arrf = new float[1];
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BPMeasurement bPMeasurement = MeasurementHelper.BloodPressure.parseMeasurement(cursor);
            if (bPMeasurement.latitude == null || bPMeasurement.longitude == null) continue;
            Location.distanceBetween((double)bPMeasurement.latitude, (double)bPMeasurement.longitude, (double)qLatLng.latitude, (double)qLatLng.longitude, (float[])arrf);
            if (arrf[0] <= 200.0f) {
                collection.add(bPMeasurement.measureDate.getTime());
            }
            cursor.moveToNext();
        }
    }

    private LocationCluster findNearestCluster(BPMeasurement object) {
        ListIterator<LocationCluster> listIterator = this.clusters.listIterator();
        while (listIterator.hasNext()) {
            LocationCluster locationCluster = listIterator.next();
            Location.distanceBetween((double)((BPMeasurement)object).latitude, (double)((BPMeasurement)object).longitude, (double)locationCluster.getLatitude(), (double)locationCluster.getLongitude(), (float[])this.distanceResults);
            if (!(this.distanceResults[0] <= 200.0f)) continue;
            return locationCluster;
        }
        object = new LocationCluster();
        this.clusters.add((LocationCluster)object);
        return object;
    }

    public static int findTagForMeasurement(QLatLng qLatLng, Cursor cursor) {
        int n = 6;
        float[] arrf = new float[1];
        cursor.moveToFirst();
        do {
            block4: {
                int n2;
                block3: {
                    n2 = n;
                    if (cursor.isAfterLast()) break block3;
                    BPMeasurement bPMeasurement = MeasurementHelper.BloodPressure.parseMeasurement(cursor);
                    if (bPMeasurement.latitude == null || bPMeasurement.longitude == null) continue;
                    Location.distanceBetween((double)bPMeasurement.latitude, (double)bPMeasurement.longitude, (double)qLatLng.latitude, (double)qLatLng.longitude, (float[])arrf);
                    if (!(arrf[0] <= 200.0f) || bPMeasurement.tag == null) break block4;
                    n2 = bPMeasurement.tag;
                }
                return n2;
            }
            cursor.moveToNext();
        } while (true);
    }

    private void notifyClustersDataChanged() {
        if (this.onClustersDataChangedListener != null) {
            this.onClustersDataChangedListener.onClustersDataChanged(this);
        }
    }

    public void clear() {
        this.clusters.clear();
        this.notifyClustersDataChanged();
    }

    public List<LocationCluster> getClusters() {
        return this.clusters;
    }

    public boolean hasMeasurements(long l, long l2) {
        Iterator<LocationCluster> iterator = this.clusters.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().hasMeasurements(l, l2)) continue;
            return true;
        }
        return false;
    }

    public void setData(Cursor cursor) {
        this.clusters.clear();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BPMeasurement bPMeasurement = MeasurementHelper.BloodPressure.parseMeasurement(cursor);
            this.findNearestCluster(bPMeasurement).addMeasurement(bPMeasurement);
            cursor.moveToNext();
        }
        this.notifyClustersDataChanged();
    }

    public void setOnClustersDataChangedListener(OnClustersDataChangedListener onClustersDataChangedListener) {
        this.onClustersDataChangedListener = onClustersDataChangedListener;
    }

    public static interface OnClustersDataChangedListener {
        public void onClustersDataChanged(LocationClusterManager var1);
    }

}

