/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.getqardio.android.utils;

import android.content.Intent;
import com.getqardio.android.io.network.response.BaseError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class ErrorHelper {
    /*
     * Enabled aggressive block sorting
     */
    public static int getErrorId(String string2) {
        block14: {
            block13: {
                if (string2 == null) break block13;
                if ("local_network_error_key".equals(string2)) {
                    return 1;
                }
                if ("local_create_params_error_key".equals(string2)) {
                    return 2;
                }
                if ("hsynch.user.already.exists".equals(string2)) {
                    return 3;
                }
                if ("hsynch.user.password.wrong".equals(string2)) {
                    return 4;
                }
                if ("hsynch.user.not.found".equals(string2)) {
                    return 5;
                }
                if ("hsynch.invalid.ticket".equals(string2)) {
                    return 6;
                }
                if ("hsynch.user.first.name.invalid".equals(string2)) {
                    return 7;
                }
                if ("hsynch.user.email.invalid".equals(string2)) {
                    return 8;
                }
                if ("local_invalid_offline_password".equals(string2)) {
                    return 9;
                }
                if ("hsynch.user.with.email.exists".equals(string2)) {
                    return 10;
                }
                if ("hsynch.fatal.error".equals(string2)) break block14;
            }
            return 0;
        }
        return 11;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static List<BaseError> getErrorsFromIntent(Intent object) {
        void var0_2;
        ArrayList arrayList = object.getIntegerArrayListExtra("com.getqardio.android.Notifications.REST_ERROR_ID");
        if (arrayList == null || arrayList.size() == 0) {
            List list = Collections.EMPTY_LIST;
            return var0_2;
        } else {
            ArrayList arrayList2 = object.getStringArrayListExtra("com.getqardio.android.Notifications.REST_ERROR_KEY");
            ArrayList arrayList3 = object.getStringArrayListExtra("com.getqardio.android.Notifications.REST_ERROR_MESSAGE");
            ArrayList<BaseError> arrayList4 = new ArrayList<BaseError>(arrayList.size());
            int n = 0;
            do {
                ArrayList<BaseError> arrayList5 = arrayList4;
                if (n >= arrayList.size()) return var0_2;
                BaseError baseError = new BaseError();
                baseError.id = (Integer)arrayList.get(n);
                baseError.messageKey = (String)arrayList2.get(n);
                baseError.defaultMessageText = (String)arrayList3.get(n);
                arrayList4.add(baseError);
                ++n;
            } while (true);
        }
    }

    public static void logErrorsInDebug(List<BaseError> object) {
        object = object.iterator();
        while (object.hasNext()) {
            BaseError baseError = (BaseError)object.next();
            Timber.w("logErrorsInDebug_ Base error id = %d, key = %s, message = %s", baseError.id, baseError.messageKey, baseError.defaultMessageText);
        }
    }

    public static BaseError makeCreateParamsError() {
        return new BaseError("local_create_params_error_key", "Create request params error");
    }

    public static BaseError makeNetworkError() {
        return new BaseError("local_network_error_key", "Network error");
    }

    public static BaseError makeParseJsonError() {
        return new BaseError("local_parse_json_error_key", "Parse json error");
    }

    public static BaseError makeWrongCredentials() {
        return new BaseError("hsynch.user.not.found", "Invalid credentials");
    }

    public static void putErrorsToIntent(Intent intent, List<BaseError> object) {
        if (object != null) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>(object.size());
            ArrayList<String> arrayList2 = new ArrayList<String>(object.size());
            ArrayList<String> arrayList3 = new ArrayList<String>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                BaseError baseError = (BaseError)object.next();
                arrayList.add(baseError.id);
                arrayList2.add(baseError.messageKey);
                arrayList3.add(baseError.defaultMessageText);
            }
            intent.putIntegerArrayListExtra("com.getqardio.android.Notifications.REST_ERROR_ID", arrayList).putStringArrayListExtra("com.getqardio.android.Notifications.REST_ERROR_KEY", arrayList2).putStringArrayListExtra("com.getqardio.android.Notifications.REST_ERROR_MESSAGE", arrayList3);
        }
    }
}

