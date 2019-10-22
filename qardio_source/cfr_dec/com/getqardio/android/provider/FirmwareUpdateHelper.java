/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 */
package com.getqardio.android.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.io.network.request.FirmwareUpdateRequestHandler;
import com.getqardio.android.provider.DataHelper;
import timber.log.Timber;

public class FirmwareUpdateHelper {
    public static FirmwareDescription getCurrentQBFirmwareUpdate(Context context) {
        if ((context = context.getSharedPreferences("firmware_update_info", 0)).contains("com.getqardio.android.QB_SERIAL")) {
            FirmwareDescription firmwareDescription = new FirmwareDescription();
            if (context.contains("com.getqardio.android.QB_ID")) {
                firmwareDescription.id = context.getLong("com.getqardio.android.QB_ID", -1L);
            }
            if (context.contains("com.getqardio.android.QB_DEVICE_TYP")) {
                firmwareDescription.deviceType = context.getString("com.getqardio.android.QB_DEVICE_TYP", "");
            }
            if (context.contains("com.getqardio.android.QB_VERSION_MAJOR")) {
                firmwareDescription.versionMajor = context.getInt("com.getqardio.android.QB_VERSION_MAJOR", 0);
            }
            if (context.contains("com.getqardio.android.QB_VERSION_MINOR")) {
                firmwareDescription.versionMinor = context.getInt("com.getqardio.android.QB_VERSION_MINOR", 0);
            }
            if (context.contains("com.getqardio.android.QB_VERSION_BUG_FIX")) {
                firmwareDescription.versionBugFix = context.getInt("com.getqardio.android.QB_VERSION_BUG_FIX", 0);
            }
            if (context.contains("com.getqardio.android.QB_VERSION_REVISION")) {
                firmwareDescription.versionRevision = context.getString("com.getqardio.android.QB_VERSION_REVISION", "");
            }
            if (context.contains("com.getqardio.android.QB_DESCRIPTION")) {
                firmwareDescription.description = context.getString("com.getqardio.android.QB_DESCRIPTION", "");
            }
            if (context.contains("com.getqardio.android.QB_UPLOAD_DATE")) {
                firmwareDescription.uploadDate = context.getLong("com.getqardio.android.QB_UPLOAD_DATE", 0L);
            }
            if (context.contains("com.getqardio.android.QB_UPDATE_DATE")) {
                firmwareDescription.updateDate = context.getLong("com.getqardio.android.QB_UPDATE_DATE", 0L);
            }
            if (context.contains("com.getqardio.android.QB_SIZE")) {
                firmwareDescription.size = context.getLong("com.getqardio.android.QB_SIZE", 0L);
            }
            if (context.contains("com.getqardio.android.QB_LOCALE")) {
                firmwareDescription.locale = context.getString("com.getqardio.android.QB_LOCALE", "");
            }
            if (context.contains("com.getqardio.android.IP_UPDATE_ADDRESS")) {
                firmwareDescription.ipAddress = context.getString("com.getqardio.android.IP_UPDATE_ADDRESS", "");
            }
            return firmwareDescription;
        }
        return null;
    }

    public static FirmwareDescription getServerQBFirmwareVersion(Context context) {
        if ((context = context.getSharedPreferences("firmware_update_info_server", 0)).contains("com.getqardio.android.QB_ID")) {
            FirmwareDescription firmwareDescription = new FirmwareDescription();
            if (context.contains("com.getqardio.android.QB_ID")) {
                firmwareDescription.id = context.getLong("com.getqardio.android.QB_ID", -1L);
            }
            if (context.contains("com.getqardio.android.QB_DEVICE_TYP")) {
                firmwareDescription.deviceType = context.getString("com.getqardio.android.QB_DEVICE_TYP", "");
            }
            if (context.contains("com.getqardio.android.QB_VERSION_MAJOR")) {
                firmwareDescription.versionMajor = context.getInt("com.getqardio.android.QB_VERSION_MAJOR", 0);
            }
            if (context.contains("com.getqardio.android.QB_VERSION_MINOR")) {
                firmwareDescription.versionMinor = context.getInt("com.getqardio.android.QB_VERSION_MINOR", 0);
            }
            if (context.contains("com.getqardio.android.QB_VERSION_BUG_FIX")) {
                firmwareDescription.versionBugFix = context.getInt("com.getqardio.android.QB_VERSION_BUG_FIX", 0);
            }
            if (context.contains("com.getqardio.android.QB_VERSION_REVISION")) {
                firmwareDescription.versionRevision = context.getString("com.getqardio.android.QB_VERSION_REVISION", "");
            }
            if (context.contains("com.getqardio.android.QB_DESCRIPTION")) {
                firmwareDescription.description = context.getString("com.getqardio.android.QB_DESCRIPTION", "");
            }
            if (context.contains("com.getqardio.android.QB_UPLOAD_DATE")) {
                firmwareDescription.uploadDate = context.getLong("com.getqardio.android.QB_UPLOAD_DATE", 0L);
            }
            if (context.contains("com.getqardio.android.QB_UPDATE_DATE")) {
                firmwareDescription.updateDate = context.getLong("com.getqardio.android.QB_UPDATE_DATE", 0L);
            }
            if (context.contains("com.getqardio.android.QB_SIZE")) {
                firmwareDescription.size = context.getLong("com.getqardio.android.QB_SIZE", 0L);
            }
            if (context.contains("com.getqardio.android.QB_LOCALE")) {
                firmwareDescription.locale = context.getString("com.getqardio.android.QB_LOCALE", "");
            }
            if (context.contains("com.getqardio.android.IP_UPDATE_ADDRESS")) {
                firmwareDescription.ipAddress = context.getString("com.getqardio.android.IP_UPDATE_ADDRESS", "");
            }
            return firmwareDescription;
        }
        return null;
    }

    public static void loadQBLatestFirmwareInfo(Context context, long l) {
        if (TextUtils.isEmpty((CharSequence)DataHelper.DeviceSnHelper.getDeviceSn(context, l))) {
            return;
        }
        context.startService(FirmwareUpdateRequestHandler.createGetQBLatestFirmwareIntent(context, l));
    }

    public static FirmwareDescription parseBaseVersion(String object) {
        Timber.d("parseBaseVersion - %s", object);
        int n = ((String)object).indexOf(46);
        if (n == -1) {
            return null;
        }
        int n2 = ((String)object).indexOf(46, n + 1);
        int n3 = Integer.parseInt(((String)object).substring(0, n));
        n = Integer.parseInt(((String)object).substring(n + 1, n2));
        n2 = Integer.parseInt(((String)object).substring(n2 + 1).replaceAll("[^0-9]", ""));
        object = new FirmwareDescription();
        ((FirmwareDescription)object).versionMajor = n3;
        ((FirmwareDescription)object).versionMinor = n;
        ((FirmwareDescription)object).versionBugFix = n2;
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void setCurrentQBFirmwareVersion(Context context, String string2, FirmwareDescription firmwareDescription) {
        block16: {
            block15: {
                if (context == null) break block15;
                context = context.getSharedPreferences("firmware_update_info", 0).edit();
                if (firmwareDescription != null) break block16;
            }
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            context.putString("com.getqardio.android.QB_SERIAL", string2);
        }
        if (firmwareDescription.id != null) {
            context.putLong("com.getqardio.android.QB_ID", firmwareDescription.id.longValue());
        }
        if (firmwareDescription.deviceType != null) {
            context.putString("com.getqardio.android.QB_DEVICE_TYP", firmwareDescription.deviceType);
        }
        if (firmwareDescription.versionMajor != null) {
            context.putInt("com.getqardio.android.QB_VERSION_MAJOR", firmwareDescription.versionMajor.intValue());
        }
        if (firmwareDescription.versionMinor != null) {
            context.putInt("com.getqardio.android.QB_VERSION_MINOR", firmwareDescription.versionMinor.intValue());
        }
        if (firmwareDescription.versionBugFix != null) {
            context.putInt("com.getqardio.android.QB_VERSION_BUG_FIX", firmwareDescription.versionBugFix.intValue());
        }
        if (firmwareDescription.versionRevision != null) {
            context.putString("com.getqardio.android.QB_VERSION_REVISION", firmwareDescription.versionRevision);
        }
        if (firmwareDescription.description != null) {
            context.putString("com.getqardio.android.QB_DESCRIPTION", firmwareDescription.description);
        }
        if (firmwareDescription.uploadDate != null) {
            context.putLong("com.getqardio.android.QB_UPLOAD_DATE", firmwareDescription.uploadDate.longValue());
        }
        if (firmwareDescription.updateDate != null) {
            context.putLong("com.getqardio.android.QB_UPDATE_DATE", firmwareDescription.updateDate.longValue());
        }
        if (firmwareDescription.size != null) {
            context.putLong("com.getqardio.android.QB_SIZE", firmwareDescription.size.longValue());
        }
        if (firmwareDescription.locale != null) {
            context.putString("com.getqardio.android.QB_LOCALE", firmwareDescription.locale);
        }
        context.commit();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void setServerQBFirmwareVersion(Context context, FirmwareDescription firmwareDescription) {
        block16: {
            block15: {
                if (context == null) break block15;
                context = context.getSharedPreferences("firmware_update_info_server", 0).edit();
                if (firmwareDescription != null) break block16;
            }
            return;
        }
        if (firmwareDescription.id != null) {
            context.putLong("com.getqardio.android.QB_ID", firmwareDescription.id.longValue());
        }
        if (firmwareDescription.deviceType != null) {
            context.putString("com.getqardio.android.QB_DEVICE_TYP", firmwareDescription.deviceType);
        }
        if (firmwareDescription.versionMajor != null) {
            context.putInt("com.getqardio.android.QB_VERSION_MAJOR", firmwareDescription.versionMajor.intValue());
        }
        if (firmwareDescription.versionMinor != null) {
            context.putInt("com.getqardio.android.QB_VERSION_MINOR", firmwareDescription.versionMinor.intValue());
        }
        if (firmwareDescription.versionBugFix != null) {
            context.putInt("com.getqardio.android.QB_VERSION_BUG_FIX", firmwareDescription.versionBugFix.intValue());
        }
        if (firmwareDescription.versionRevision != null) {
            context.putString("com.getqardio.android.QB_VERSION_REVISION", firmwareDescription.versionRevision);
        }
        if (firmwareDescription.description != null) {
            context.putString("com.getqardio.android.QB_DESCRIPTION", firmwareDescription.description);
        }
        if (firmwareDescription.uploadDate != null) {
            context.putLong("com.getqardio.android.QB_UPLOAD_DATE", firmwareDescription.uploadDate.longValue());
        }
        if (firmwareDescription.updateDate != null) {
            context.putLong("com.getqardio.android.QB_UPDATE_DATE", firmwareDescription.updateDate.longValue());
        }
        if (firmwareDescription.size != null) {
            context.putLong("com.getqardio.android.QB_SIZE", firmwareDescription.size.longValue());
        }
        if (firmwareDescription.locale != null) {
            context.putString("com.getqardio.android.QB_LOCALE", firmwareDescription.locale);
        }
        if (firmwareDescription.ipAddress != null) {
            context.putString("com.getqardio.android.IP_UPDATE_ADDRESS", firmwareDescription.ipAddress);
        }
        context.commit();
    }
}

