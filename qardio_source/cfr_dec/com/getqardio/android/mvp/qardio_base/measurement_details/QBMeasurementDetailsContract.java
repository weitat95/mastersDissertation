/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details;

import java.util.Date;

public interface QBMeasurementDetailsContract {

    public static interface View {
        public int getWeightUnit();

        public void setBMIChartValue(Float var1);

        public void setBoneValue(String var1);

        public void setDateValue(Date var1);

        public void setFatValue(String var1);

        public void setMuscleValue(String var1);

        public void setNoteValue(String var1);

        public void setTimeValue(String var1);

        public void setWaterValue(String var1);

        public void setWeightUnitValue(int var1);

        public void setWeightValue(String var1);
    }

}

