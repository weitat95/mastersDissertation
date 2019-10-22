/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.os.Handler
 */
package com.getqardio.android.shealth;

import android.database.Cursor;
import android.os.Handler;
import com.getqardio.android.CustomApplication;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthDevice;
import com.samsung.android.sdk.healthdata.HealthDeviceManager;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import timber.log.Timber;

public class ShealthUtils {
    public static void delete(HealthDataResolver healthDataResolver, HealthDataResolver.Filter object, String string2) {
        object = new HealthDataResolver.DeleteRequest.Builder().setDataType(string2).setFilter((HealthDataResolver.Filter)object).build();
        try {
            healthDataResolver.delete((HealthDataResolver.DeleteRequest)object).setResultListener(new HealthResultHolder.ResultListener<HealthResultHolder.BaseResult>(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void onResult(HealthResultHolder.BaseResult baseResult) {
                    int n = baseResult == null ? 0 : baseResult.getCount();
                    Timber.d("Count of deleted measurements from SHealth: %d", n);
                }
            });
            return;
        }
        catch (Exception exception) {
            Timber.d("Cannot delete measurement from SHealth", new Object[0]);
            return;
        }
    }

    public static void deleteMeasurement(HealthDataStore healthDataStore, long l, String string2) {
        ShealthUtils.delete(new HealthDataResolver(healthDataStore, null), HealthDataResolver.Filter.or(HealthDataResolver.Filter.eq("create_time", l), HealthDataResolver.Filter.eq("update_time", l), HealthDataResolver.Filter.eq("start_time", l)), string2);
    }

    public static String getLocalDevice(HealthDataStore object, String object2) {
        String string2;
        block5: {
            HealthDeviceManager healthDeviceManager = new HealthDeviceManager((HealthDataStore)object);
            string2 = "";
            try {
                object = object2 = healthDeviceManager.getDeviceBySeed((String)object2);
                if (object2 != null) break block5;
            }
            catch (IllegalStateException illegalStateException) {
                Timber.d(illegalStateException, "Cannot get local device UUID", new Object[0]);
                CustomApplication.getApplication().connectToShealth();
                return "";
            }
            object = healthDeviceManager.getLocalDevice();
        }
        object2 = string2;
        if (object != null) {
            object2 = ((HealthDevice)object).getUuid();
        }
        return object2;
    }

    public static HealthResultHolder.BaseResult insert(HealthDataResolver object, HealthData healthData, String object2) {
        object2 = new HealthDataResolver.InsertRequest.Builder().setDataType((String)object2).build();
        object2.addHealthData(healthData);
        try {
            object = ((HealthDataResolver)object).insert((HealthDataResolver.InsertRequest)object2).await();
            return object;
        }
        catch (Exception exception) {
            Timber.e(exception, "Cannot insert measurement into SHealth", new Object[0]);
            return null;
        }
    }

    public static boolean isPermissionAcquired(HealthDataStore object, HealthPermissionManager.PermissionKey permissionKey) {
        object = new HealthPermissionManager((HealthDataStore)object);
        HashSet<HealthPermissionManager.PermissionKey> hashSet = new HashSet<HealthPermissionManager.PermissionKey>();
        hashSet.add(permissionKey);
        try {
            boolean bl = ((HealthPermissionManager)object).isPermissionAcquired(hashSet).get(permissionKey);
            return bl;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Timber.d(illegalArgumentException, "Cannot check permission", new Object[0]);
            return false;
        }
    }

    public static Cursor read(HealthDataResolver healthDataResolver, HealthDataResolver.Filter object, String object2, String[] arrstring) {
        object2 = new HealthDataResolver.ReadRequest.Builder().setDataType((String)object2).setProperties(arrstring);
        if (object != null) {
            ((HealthDataResolver.ReadRequest.Builder)object2).setFilter((HealthDataResolver.Filter)object);
        }
        object = null;
        try {
            healthDataResolver = healthDataResolver.read(((HealthDataResolver.ReadRequest.Builder)object2).build()).await().getResultCursor();
            object = healthDataResolver;
        }
        catch (Exception exception) {
            Timber.d(exception, "Cannot read data from SHealth", new Object[0]);
            return object;
        }
        Timber.d("Count of imported measurements from SHealth: %d", healthDataResolver.getCount());
        return healthDataResolver;
    }

    public static HealthResultHolder.BaseResult update(HealthDataResolver object, HealthData object2, HealthDataResolver.Filter filter, String string2) {
        object2 = new HealthDataResolver.UpdateRequest.Builder().setDataType(string2).setHealthData((HealthData)object2);
        if (filter != null) {
            ((HealthDataResolver.UpdateRequest.Builder)object2).setFilter(filter);
        }
        try {
            object = ((HealthDataResolver)object).update(((HealthDataResolver.UpdateRequest.Builder)object2).build()).await();
            return object;
        }
        catch (Exception exception) {
            Timber.e(exception, "Cannot update measurement in SHealth", new Object[0]);
            return null;
        }
    }

}

