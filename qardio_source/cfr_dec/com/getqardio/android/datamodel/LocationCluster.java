/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BPMeasurement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LocationCluster {
    private double avgLatitude = 0.0;
    private double avgLongitude = 0.0;
    private int count = 0;
    private List<BPMeasurement> measurements = new LinkedList<BPMeasurement>();
    private int tag = 0;

    public void addMeasurement(BPMeasurement bPMeasurement) {
        if (bPMeasurement.latitude != null) {
            this.avgLatitude = (this.avgLatitude * (double)this.count + bPMeasurement.latitude) / (double)(this.count + 1);
        }
        if (bPMeasurement.longitude != null) {
            this.avgLongitude = (this.avgLongitude * (double)this.count + bPMeasurement.longitude) / (double)(this.count + 1);
        }
        if (bPMeasurement.tag != null) {
            this.tag = bPMeasurement.tag;
        }
        this.measurements.add(bPMeasurement);
        ++this.count;
    }

    public AvgData getAvgData(long l, long l2) {
        AvgData avgData = new AvgData();
        for (BPMeasurement bPMeasurement : this.measurements) {
            if (bPMeasurement.measureDate.getTime() < l || bPMeasurement.measureDate.getTime() > l2) continue;
            avgData.addMeasurement(bPMeasurement);
        }
        return avgData;
    }

    public double getLatitude() {
        return this.avgLatitude;
    }

    public double getLongitude() {
        return this.avgLongitude;
    }

    public List<BPMeasurement> getMeasurements() {
        return this.measurements;
    }

    public int getTag() {
        return this.tag;
    }

    public boolean hasMeasurements(long l, long l2) {
        for (BPMeasurement bPMeasurement : this.measurements) {
            if (bPMeasurement.measureDate.getTime() < l || bPMeasurement.measureDate.getTime() > l2) continue;
            return true;
        }
        return false;
    }

    public static class AvgData {
        private double avgDia = 0.0;
        private double avgPulse = 0.0;
        private double avgSys = 0.0;
        private int count = 0;

        public void addMeasurement(BPMeasurement bPMeasurement) {
            if (bPMeasurement.dia != null && bPMeasurement.dia > 0) {
                this.avgDia = (this.avgDia * (double)this.count + (double)bPMeasurement.dia.intValue()) / (double)(this.count + 1);
            }
            if (bPMeasurement.sys != null && bPMeasurement.sys > 0) {
                this.avgSys = (this.avgSys * (double)this.count + (double)bPMeasurement.sys.intValue()) / (double)(this.count + 1);
            }
            if (bPMeasurement.pulse != null && bPMeasurement.pulse > 0) {
                this.avgPulse = (this.avgPulse * (double)this.count + (double)bPMeasurement.pulse.intValue()) / (double)(this.count + 1);
            }
            ++this.count;
        }

        public double getDia() {
            return this.avgDia;
        }

        public double getPulse() {
            return this.avgPulse;
        }

        public double getSys() {
            return this.avgSys;
        }

        public boolean hasMeasurements() {
            return this.count > 0;
        }
    }

}

