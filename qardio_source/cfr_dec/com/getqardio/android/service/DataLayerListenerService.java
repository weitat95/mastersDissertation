/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.getqardio.android.service;

import android.content.Context;
import android.net.Uri;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.service.WearableCommunicationService;
import com.getqardio.android.utils.ApplicationUtils;
import com.getqardio.android.utils.CrashlyticsUtil;
import com.getqardio.shared.wearable.Contract;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayerListenerService
extends WearableListenerService {
    private void onWearAppLaunched(MessageEvent messageEvent) {
        WearableCommunicationService.sendAllData((Context)this);
    }

    private void onWearAskDisconnectFromArm(MessageEvent messageEvent) {
        if (ApplicationUtils.isBPMeasurementInProcess((Context)this)) {
            WearableCommunicationService.sendRefuseDisconnect((Context)this, messageEvent.getSourceNodeId());
            return;
        }
        MobileDeviceFactory.stopMeasurementService((Context)this);
    }

    private void onWearException(MessageEvent messageEvent) {
        CrashlyticsUtil.logExceptionFromWear(DataMap.fromByteArray(messageEvent.getData()));
    }

    @Override
    public void onDataChanged(DataEventBuffer object) {
        super.onDataChanged((DataEventBuffer)object);
        object = ((AbstractDataBuffer)object).iterator();
        while (object.hasNext()) {
            DataEvent dataEvent = (DataEvent)object.next();
            String string2 = dataEvent.getDataItem().getUri().getPath();
            if (!Contract.WEAR_BP_MEASUREMENTS_PATTERN.matcher(string2).matches() || dataEvent.getType() != 1) continue;
            WearableCommunicationService.saveWearBpMeasurements((Context)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        CustomApplication.getApplication().setHasWatch(true);
        String string2 = messageEvent.getPath();
        int n = -1;
        switch (string2.hashCode()) {
            case -1622927311: {
                if (!string2.equals("/wear/app_launched")) break;
                n = 0;
                break;
            }
            case 1289976934: {
                if (!string2.equals("/wear/ask_arm_disconnect")) break;
                n = 1;
                break;
            }
            case 17146798: {
                if (!string2.equals("/wear/exception")) break;
                n = 2;
                break;
            }
        }
        switch (n) {
            default: {
                return;
            }
            case 0: {
                this.onWearAppLaunched(messageEvent);
                return;
            }
            case 1: {
                this.onWearAskDisconnectFromArm(messageEvent);
                return;
            }
            case 2: 
        }
        this.onWearException(messageEvent);
    }
}

