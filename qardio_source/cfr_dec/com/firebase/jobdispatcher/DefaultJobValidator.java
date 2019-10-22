/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.util.Log
 */
package com.firebase.jobdispatcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.JobValidator;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DefaultJobValidator
implements JobValidator {
    private final Context context;

    public DefaultJobValidator(Context context) {
        this.context = context;
    }

    private static List<String> addError(List<String> list, String string2) {
        if (string2 == null) {
            return list;
        }
        if (list == null) {
            return DefaultJobValidator.getMutableSingletonList(string2);
        }
        Collections.addAll(list, string2);
        return list;
    }

    private static List<String> addErrorsIf(boolean bl, List<String> list, String string2) {
        List<String> list2 = list;
        if (bl) {
            list2 = DefaultJobValidator.addError(list, string2);
        }
        return list2;
    }

    private static List<String> getMutableSingletonList(String string2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string2);
        return arrayList;
    }

    private static int measureBundleSize(Bundle bundle) {
        Parcel parcel = Parcel.obtain();
        bundle.writeToParcel(parcel, 0);
        int n = parcel.dataSize();
        parcel.recycle();
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static List<String> mergeErrorLists(List<String> list, List<String> list2) {
        if (list == null) {
            return list2;
        }
        List<String> list3 = list;
        if (list2 == null) return list3;
        list.addAll(list2);
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String validateExtrasType(Bundle object, String string2) {
        Object var2_2 = null;
        if ((object = object.get(string2)) == null) return null;
        if (object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof String || object instanceof Boolean) {
            return null;
        }
        Locale locale = Locale.US;
        if (object == null) {
            object = var2_2;
            do {
                return String.format(locale, "Received value of type '%s' for key '%s', but only the following extra parameter types are supported: Integer, Long, Double, String, and Boolean", object, string2);
                break;
            } while (true);
        }
        object = object.getClass();
        return String.format(locale, "Received value of type '%s' for key '%s', but only the following extra parameter types are supported: Integer, Long, Double, String, and Boolean", object, string2);
    }

    private static List<String> validateForPersistence(Bundle bundle) {
        List<String> list = null;
        List<String> list2 = null;
        if (bundle != null) {
            Iterator iterator = bundle.keySet().iterator();
            do {
                list = list2;
                if (!iterator.hasNext()) break;
                list2 = DefaultJobValidator.addError(list2, DefaultJobValidator.validateExtrasType(bundle, (String)iterator.next()));
            } while (true);
        }
        return list;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static List<String> validateForTransport(Bundle bundle) {
        int n;
        if (bundle == null || (n = DefaultJobValidator.measureBundleSize(bundle)) <= 10240) {
            return null;
        }
        return DefaultJobValidator.getMutableSingletonList(String.format(Locale.US, "Extras too large: %d bytes is > the max (%d bytes)", n, 10240));
    }

    private static List<String> validateTag(String string2) {
        if (string2 == null) {
            return DefaultJobValidator.getMutableSingletonList("Tag can't be null");
        }
        if (string2.length() > 100) {
            return DefaultJobValidator.getMutableSingletonList("Tag must be shorter than 100");
        }
        return null;
    }

    @Override
    public List<String> validate(JobParameters jobParameters) {
        List<String> list;
        List<String> list2 = list = DefaultJobValidator.mergeErrorLists(DefaultJobValidator.mergeErrorLists(null, this.validate(jobParameters.getTrigger())), this.validate(jobParameters.getRetryStrategy()));
        if (jobParameters.isRecurring()) {
            list2 = list;
            if (jobParameters.getTrigger() == Trigger.NOW) {
                list2 = DefaultJobValidator.addError(list, "ImmediateTriggers can't be used with recurring jobs");
            }
        }
        list2 = list = DefaultJobValidator.mergeErrorLists(list2, DefaultJobValidator.validateForTransport(jobParameters.getExtras()));
        if (jobParameters.getLifetime() > 1) {
            list2 = DefaultJobValidator.mergeErrorLists(list, DefaultJobValidator.validateForPersistence(jobParameters.getExtras()));
        }
        return DefaultJobValidator.mergeErrorLists(DefaultJobValidator.mergeErrorLists(list2, DefaultJobValidator.validateTag(jobParameters.getTag())), this.validateService(jobParameters.getService()));
    }

    public List<String> validate(JobTrigger jobTrigger) {
        if (jobTrigger != Trigger.NOW && !(jobTrigger instanceof JobTrigger.ExecutionWindowTrigger) && !(jobTrigger instanceof JobTrigger.ContentUriTrigger)) {
            return DefaultJobValidator.getMutableSingletonList("Unknown trigger provided");
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<String> validate(RetryStrategy list) {
        boolean bl = true;
        int n = ((RetryStrategy)((Object)list)).getPolicy();
        int n2 = ((RetryStrategy)((Object)list)).getInitialBackoff();
        int n3 = ((RetryStrategy)((Object)list)).getMaximumBackoff();
        boolean bl2 = n != 1 && n != 2;
        list = DefaultJobValidator.addErrorsIf(bl2, null, "Unknown retry policy provided");
        bl2 = n3 < n2;
        list = DefaultJobValidator.addErrorsIf(bl2, list, "Maximum backoff must be greater than or equal to initial backoff");
        bl2 = 300 > n3;
        list = DefaultJobValidator.addErrorsIf(bl2, list, "Maximum backoff must be greater than 300s (5 minutes)");
        if (n2 < 30) {
            bl2 = bl;
            return DefaultJobValidator.addErrorsIf(bl2, list, "Initial backoff must be at least 30s");
        }
        bl2 = false;
        return DefaultJobValidator.addErrorsIf(bl2, list, "Initial backoff must be at least 30s");
    }

    List<String> validateService(String string2) {
        if (string2 == null || string2.isEmpty()) {
            return DefaultJobValidator.getMutableSingletonList("Service can't be empty");
        }
        if (this.context == null) {
            return DefaultJobValidator.getMutableSingletonList("Context is null, can't query PackageManager");
        }
        Object object = this.context.getPackageManager();
        if (object == null) {
            return DefaultJobValidator.getMutableSingletonList("PackageManager is null, can't validate service");
        }
        Intent intent = new Intent("com.firebase.jobdispatcher.ACTION_EXECUTE");
        intent.setClassName(this.context, string2);
        object = object.queryIntentServices(intent, 0);
        if (object == null || object.isEmpty()) {
            Log.e((String)"FJD.GooglePlayReceiver", (String)("Couldn't find a registered service with the name " + string2 + ". Is it declared in the manifest with the right intent-filter? If not, the job won't be started."));
            return null;
        }
        object = object.iterator();
        while (object.hasNext()) {
            intent = (ResolveInfo)object.next();
            if (intent.serviceInfo == null || !intent.serviceInfo.enabled) continue;
            return null;
        }
        return DefaultJobValidator.getMutableSingletonList(string2 + " is disabled.");
    }
}

