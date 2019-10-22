/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.firebase.jobdispatcher;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.JobInvocation;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.ObservedUri;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.firebase.jobdispatcher.TriggerReason;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class JobCoder {
    private final String prefix;

    JobCoder(String string2) {
        this.prefix = string2;
    }

    private static List<ObservedUri> convertJsonToObservedUris(String string2) {
        ArrayList<ObservedUri> arrayList = new ArrayList<ObservedUri>();
        JSONObject jSONObject = new JSONObject(string2);
        string2 = jSONObject.getJSONArray("uri_flags");
        jSONObject = jSONObject.getJSONArray("uris");
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            try {
                int n2 = string2.getInt(i);
                arrayList.add(new ObservedUri(Uri.parse((String)jSONObject.getString(i)), n2));
                continue;
            }
            catch (JSONException jSONException) {
                throw new RuntimeException(jSONException);
            }
        }
        return arrayList;
    }

    private static String convertObservedUrisToJsonString(List<ObservedUri> object) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        object = object.iterator();
        while (object.hasNext()) {
            ObservedUri observedUri = (ObservedUri)object.next();
            jSONArray.put(observedUri.getFlags());
            jSONArray2.put((Object)observedUri.getUri());
        }
        try {
            jSONObject.put("uri_flags", (Object)jSONArray);
            jSONObject.put("uris", (Object)jSONArray2);
        }
        catch (JSONException jSONException) {
            throw new RuntimeException(jSONException);
        }
        return jSONObject.toString();
    }

    private RetryStrategy decodeRetryStrategy(Bundle bundle) {
        int n = bundle.getInt(this.prefix + "retry_policy");
        if (n != 1 && n != 2) {
            return RetryStrategy.DEFAULT_EXPONENTIAL;
        }
        return new RetryStrategy(n, bundle.getInt(this.prefix + "initial_backoff_seconds"), bundle.getInt(this.prefix + "maximum_backoff_seconds"));
    }

    private JobTrigger decodeTrigger(Bundle bundle) {
        switch (bundle.getInt(this.prefix + "trigger_type")) {
            default: {
                if (Log.isLoggable((String)"FJD.ExternalReceiver", (int)3)) {
                    Log.d((String)"FJD.ExternalReceiver", (String)"Unsupported trigger.");
                }
                return null;
            }
            case 2: {
                return Trigger.NOW;
            }
            case 1: {
                return Trigger.executionWindow(bundle.getInt(this.prefix + "window_start"), bundle.getInt(this.prefix + "window_end"));
            }
            case 3: 
        }
        return Trigger.contentUriTrigger(Collections.unmodifiableList(JobCoder.convertJsonToObservedUris(bundle.getString(this.prefix + "observed_uris"))));
    }

    private void encodeRetryStrategy(RetryStrategy retryStrategy, Bundle bundle) {
        RetryStrategy retryStrategy2 = retryStrategy;
        if (retryStrategy == null) {
            retryStrategy2 = RetryStrategy.DEFAULT_EXPONENTIAL;
        }
        bundle.putInt(this.prefix + "retry_policy", retryStrategy2.getPolicy());
        bundle.putInt(this.prefix + "initial_backoff_seconds", retryStrategy2.getInitialBackoff());
        bundle.putInt(this.prefix + "maximum_backoff_seconds", retryStrategy2.getMaximumBackoff());
    }

    private void encodeTrigger(JobTrigger object, Bundle bundle) {
        if (object == Trigger.NOW) {
            bundle.putInt(this.prefix + "trigger_type", 2);
            return;
        }
        if (object instanceof JobTrigger.ExecutionWindowTrigger) {
            object = (JobTrigger.ExecutionWindowTrigger)object;
            bundle.putInt(this.prefix + "trigger_type", 1);
            bundle.putInt(this.prefix + "window_start", ((JobTrigger.ExecutionWindowTrigger)object).getWindowStart());
            bundle.putInt(this.prefix + "window_end", ((JobTrigger.ExecutionWindowTrigger)object).getWindowEnd());
            return;
        }
        if (object instanceof JobTrigger.ContentUriTrigger) {
            bundle.putInt(this.prefix + "trigger_type", 3);
            object = JobCoder.convertObservedUrisToJsonString(((JobTrigger.ContentUriTrigger)object).getUris());
            bundle.putString(this.prefix + "observed_uris", (String)object);
            return;
        }
        throw new IllegalArgumentException("Unsupported trigger.");
    }

    public JobInvocation.Builder decode(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Unexpected null Bundle provided");
        }
        bundle = new Bundle(bundle);
        boolean bl = bundle.getBoolean(this.prefix + "recurring");
        boolean bl2 = bundle.getBoolean(this.prefix + "replace_current");
        int n = bundle.getInt(this.prefix + "persistent");
        Object object = Constraint.uncompact(bundle.getInt(this.prefix + "constraints"));
        JobTrigger jobTrigger = this.decodeTrigger(bundle);
        RetryStrategy retryStrategy = this.decodeRetryStrategy(bundle);
        String string2 = bundle.getString(this.prefix + "tag");
        String string3 = bundle.getString(this.prefix + "service");
        if (string2 == null || string3 == null || jobTrigger == null || retryStrategy == null) {
            return null;
        }
        JobInvocation.Builder builder = new JobInvocation.Builder();
        builder.setTag(string2);
        builder.setService(string3);
        builder.setTrigger(jobTrigger);
        builder.setRetryStrategy(retryStrategy);
        builder.setRecurring(bl);
        builder.setLifetime(n);
        builder.setConstraints((int[])object);
        builder.setReplaceCurrent(bl2);
        if (!TextUtils.isEmpty((CharSequence)this.prefix)) {
            object = bundle.keySet().iterator();
            while (object.hasNext()) {
                if (!((String)object.next()).startsWith(this.prefix)) continue;
                object.remove();
            }
        }
        builder.addExtras(bundle);
        return builder;
    }

    /*
     * Enabled aggressive block sorting
     */
    JobInvocation decodeIntentBundle(Bundle object) {
        if (object == null) {
            Log.e((String)"FJD.ExternalReceiver", (String)"Unexpected null Bundle provided");
            return null;
        } else {
            Object object2 = object.getBundle("extras");
            if (object2 == null) return null;
            {
                object2 = this.decode((Bundle)object2);
                ArrayList arrayList = object.getParcelableArrayList("triggered_uris");
                if (arrayList == null) return ((JobInvocation.Builder)object2).build();
                {
                    ((JobInvocation.Builder)object2).setTriggerReason(new TriggerReason(arrayList));
                }
                return ((JobInvocation.Builder)object2).build();
            }
        }
    }

    Bundle encode(JobParameters jobParameters, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Unexpected null Bundle provided");
        }
        Bundle bundle2 = jobParameters.getExtras();
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        bundle.putInt(this.prefix + "persistent", jobParameters.getLifetime());
        bundle.putBoolean(this.prefix + "recurring", jobParameters.isRecurring());
        bundle.putBoolean(this.prefix + "replace_current", jobParameters.shouldReplaceCurrent());
        bundle.putString(this.prefix + "tag", jobParameters.getTag());
        bundle.putString(this.prefix + "service", jobParameters.getService());
        bundle.putInt(this.prefix + "constraints", Constraint.compact(jobParameters.getConstraints()));
        this.encodeTrigger(jobParameters.getTrigger(), bundle);
        this.encodeRetryStrategy(jobParameters.getRetryStrategy(), bundle);
        return bundle;
    }
}

