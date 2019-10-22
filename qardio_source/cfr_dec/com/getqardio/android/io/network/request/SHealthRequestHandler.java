/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.samsung.android.sdk.healthdata.HealthDataStore;

public class SHealthRequestHandler
extends RequestHandler {
    private static Intent createDefaultIntent(Context context, long l, int n) {
        context = AsyncReceiverHandler.createIntent(context, 17, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", n);
        return context;
    }

    public static Intent createReadBpMeasurementsIntent(Context context, long l) {
        return SHealthRequestHandler.createDefaultIntent(context, l, 1);
    }

    public static Intent createReadWeightMeasurementsIntent(Context context, long l) {
        return SHealthRequestHandler.createDefaultIntent(context, l, 4);
    }

    public static Intent createSaveBpMeasurementIntent(Context context, long l, long l2) {
        context = SHealthRequestHandler.createDefaultIntent(context, l2, 3);
        context.putExtra("com.getqardio.android.extra.MEASUREMENT_TIME", l);
        return context;
    }

    public static Intent createSaveBpMeasurementsIntent(Context context, long l) {
        return SHealthRequestHandler.createDefaultIntent(context, l, 2);
    }

    public static Intent createSaveWeightMeasurementIntent(Context context, long l, long l2) {
        context = SHealthRequestHandler.createDefaultIntent(context, l2, 6);
        context.putExtra("com.getqardio.android.extra.MEASUREMENT_TIME", l);
        return context;
    }

    public static Intent createSaveWeightMeasurementsIntent(Context context, long l) {
        return SHealthRequestHandler.createDefaultIntent(context, l, 5);
    }

    private RequestHandler.ProcessResult readMeasurements(Context context, HealthDataStore healthDataStore, long l) {
        if (healthDataStore != null) {
            ShealthDataHelper.BpMeasurements.readMeasurements(context, healthDataStore, l);
        }
        return RequestHandler.ProcessResult.SUCCESS;
    }

    private RequestHandler.ProcessResult readWeightMeasurements(Context context, HealthDataStore healthDataStore, long l) {
        if (healthDataStore != null) {
            ShealthDataHelper.WeightMeasurements.readWeightMeasurements(context, healthDataStore, l);
        }
        return RequestHandler.ProcessResult.SUCCESS;
    }

    private RequestHandler.ProcessResult saveMeasurement(Context context, HealthDataStore healthDataStore, long l, long l2) {
        BPMeasurement bPMeasurement = MeasurementHelper.BloodPressure.getMeasurementByTimestamp(context, l2, l);
        if (bPMeasurement != null) {
            CustomApplication.getApplication().unregisterBpDataObserver();
            ShealthDataHelper.BpMeasurements.addMeasurement(context, healthDataStore, bPMeasurement);
            CustomApplication.getApplication().registerBpDataObserver();
            return RequestHandler.ProcessResult.SUCCESS;
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }

    private RequestHandler.ProcessResult saveMeasurements(Context context, HealthDataStore healthDataStore, long l) {
        CustomApplication.getApplication().unregisterBpDataObserver();
        ShealthDataHelper.BpMeasurements.addBpMeasurements(context, healthDataStore, MeasurementHelper.BloodPressure.getMeasurementsForSHealth(context, l));
        CustomApplication.getApplication().registerBpDataObserver();
        return RequestHandler.ProcessResult.SUCCESS;
    }

    private RequestHandler.ProcessResult saveWeightMeasurement(Context context, HealthDataStore healthDataStore, long l, long l2) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        WeightMeasurement weightMeasurement = MeasurementHelper.Weight.getMeasurementByTimestamp(context, l2, l);
        if (weightMeasurement != null) {
            CustomApplication.getApplication().unregisterWeightDataObserver();
            ShealthDataHelper.WeightMeasurements.addWeightMeasurement(context, healthDataStore, weightMeasurement);
            CustomApplication.getApplication().registerBpDataObserver();
            return processResult;
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }

    private RequestHandler.ProcessResult saveWeightMeasurements(Context context, HealthDataStore healthDataStore, long l) {
        CustomApplication.getApplication().unregisterWeightDataObserver();
        ShealthDataHelper.WeightMeasurements.addWeightMeasurements(context, healthDataStore, MeasurementHelper.Weight.getWeightMeasurementsForSHealth(context, l));
        CustomApplication.getApplication().registerWeightDataObserver();
        return RequestHandler.ProcessResult.SUCCESS;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String object) {
        int n = intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        object = CustomApplication.getApplication().getHealthDataStore();
        switch (n) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 1: {
                return this.readMeasurements(context, (HealthDataStore)object, l);
            }
            case 2: {
                return this.saveMeasurements(context, (HealthDataStore)object, l);
            }
            case 3: {
                return this.saveMeasurement(context, (HealthDataStore)object, intent.getLongExtra("com.getqardio.android.extra.MEASUREMENT_TIME", 0L), l);
            }
            case 4: {
                return this.readWeightMeasurements(context, (HealthDataStore)object, l);
            }
            case 5: {
                return this.saveWeightMeasurements(context, (HealthDataStore)object, l);
            }
            case 6: 
        }
        return this.saveWeightMeasurement(context, (HealthDataStore)object, intent.getLongExtra("com.getqardio.android.extra.MEASUREMENT_TIME", 0L), l);
    }
}

