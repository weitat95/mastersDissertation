/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.provider.MeasurementHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class GoogleFitRequestHandler
extends RequestHandler {
    private GoogleApiClient googleApiClient;

    public static Intent createDeleteMeasurementIntent(Context context, long l, long l2) {
        return GoogleFitRequestHandler.createIntentWithTimestampExtra(context, 3, l, l2);
    }

    private static Intent createIntent(Context context, int n, long l) {
        context = AsyncReceiverHandler.createIntent(context, 25, l);
        context.putExtra("com.getqardio.android.extra.ACTION_TYPE", n);
        return context;
    }

    private static Intent createIntentWithTimestampExtra(Context context, int n, long l, long l2) {
        context = GoogleFitRequestHandler.createIntent(context, n, l2);
        context.putExtra("com.getqardio.android.extra.MEASUREMENT_TIME", l);
        return context;
    }

    public static Intent createReadMeasurementsIntent(Context context, long l) {
        return GoogleFitRequestHandler.createIntent(context, 4, l);
    }

    public static Intent createSaveMeasurementIntent(Context context, long l, long l2) {
        return GoogleFitRequestHandler.createIntentWithTimestampExtra(context, 1, l, l2);
    }

    public static Intent createSaveMeasurementsIntent(Context context, long l) {
        return GoogleFitRequestHandler.createIntent(context, 2, l);
    }

    private RequestHandler.ProcessResult deleteMeasurement(Context context, Intent intent, long l) {
        long l2 = intent.getLongExtra("com.getqardio.android.extra.MEASUREMENT_TIME", 0L);
        GoogleFitDataHelper.Weight.delete(context, this.googleApiClient, l2, l);
        return RequestHandler.ProcessResult.SUCCESS;
    }

    private RequestHandler.ProcessResult processAction(Context context, int n, Intent intent, long l) {
        Timber.d("GoogleFitRequestHandler: processing action - %d", n);
        switch (n) {
            default: {
                return RequestHandler.ProcessResult.UNKNOWN_REQUEST;
            }
            case 2: {
                return this.saveMeasurements(context, l);
            }
            case 1: {
                return this.saveMeasurement(context, intent, l);
            }
            case 4: {
                return this.readMeasurements(context, l);
            }
            case 3: 
        }
        return this.deleteMeasurement(context, intent, l);
    }

    private RequestHandler.ProcessResult readMeasurements(Context context, long l) {
        List<WeightMeasurement> list = GoogleFitDataHelper.Weight.read(context, this.googleApiClient, l);
        if (list != null) {
            for (WeightMeasurement weightMeasurement : list) {
                Timber.d("readMeasurements from fit - %s", weightMeasurement);
                Long l2 = weightMeasurement.userId;
                list = null;
                if (l2 != null) {
                    list = MeasurementHelper.Weight.getMeasurementByTimestamp(context, weightMeasurement.userId, weightMeasurement.measureDate.getTime());
                }
                if (list == null) {
                    MeasurementHelper.Weight.addMeasurement(context, l, weightMeasurement, true);
                    continue;
                }
                if (weightMeasurement.isWeightEqual((WeightMeasurement)((Object)list))) continue;
                MeasurementHelper.Weight.updateMeasurementWeight(context, l, weightMeasurement);
            }
        }
        return RequestHandler.ProcessResult.SUCCESS;
    }

    private RequestHandler.ProcessResult saveMeasurement(Context context, Intent object, long l) {
        object = MeasurementHelper.Weight.getMeasurementByTimestamp(context, l, object.getLongExtra("com.getqardio.android.extra.MEASUREMENT_TIME", 0L));
        if (object != null) {
            Timber.d("saveMeasurement to fit - %s", object);
            GoogleFitDataHelper.Weight.insert(context, this.googleApiClient, (WeightMeasurement)object, (Long)l);
            return RequestHandler.ProcessResult.SUCCESS;
        }
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }

    private RequestHandler.ProcessResult saveMeasurements(Context context, long l) {
        RequestHandler.ProcessResult processResult = RequestHandler.ProcessResult.SUCCESS;
        Cursor cursor = MeasurementHelper.Weight.getWeightMeasurementsForSHealth(context, l);
        GoogleFitDataHelper.Weight.insert(context, this.googleApiClient, cursor, (Long)l);
        return processResult;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context object, Intent intent, long l, String object2) {
        int n = intent.getIntExtra("com.getqardio.android.extra.ACTION_TYPE", 0);
        Timber.d("GoogleFitRequestHandler: processIntent", new Object[0]);
        this.googleApiClient = GoogleFitUtils.buildNewClient(object);
        object2 = this.googleApiClient.blockingConnect(10L, TimeUnit.SECONDS);
        Timber.d("GoogleFitRequestHandler: connecting to google fit...", new Object[0]);
        if (((ConnectionResult)object2).getErrorCode() == 0) {
            Timber.d("GoogleFitRequestHandler: SUCCESS", new Object[0]);
            object = this.processAction((Context)object, n, intent, l);
            this.googleApiClient.disconnect();
            Timber.d("GoogleFitRequestHandler: disconnect from google fit", new Object[0]);
            return object;
        }
        Timber.d("GoogleFitRequestHandler: FAILURE. Error code %s", ((ConnectionResult)object2).getErrorCode());
        Timber.d("GoogleFitRequestHandler: FAILURE. Error message %s", ((ConnectionResult)object2).getErrorMessage());
        return RequestHandler.ProcessResult.UNKNOWN_ERROR;
    }
}

