/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.content.ClipData$Item
 *  android.content.ClipDescription
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.app;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.RemoteInput;
import android.support.v4.app.RemoteInputCompatBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class RemoteInputCompatJellybean {
    private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
    private static final String KEY_ALLOWED_DATA_TYPES = "allowedDataTypes";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_LABEL = "label";
    private static final String KEY_RESULT_KEY = "resultKey";

    RemoteInputCompatJellybean() {
    }

    /*
     * WARNING - void declaration
     */
    public static void addDataResultToIntent(RemoteInput remoteInput, Intent intent, Map<String, Uri> object2) {
        Intent intent2;
        Intent intent3 = intent2 = RemoteInputCompatJellybean.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        for (Map.Entry entry : object2.entrySet()) {
            void var2_7;
            String string2 = (String)entry.getKey();
            Uri uri = (Uri)entry.getValue();
            if (string2 == null) continue;
            Intent intent4 = intent2 = intent3.getBundleExtra(RemoteInputCompatJellybean.getExtraResultsKeyForData(string2));
            if (intent2 == null) {
                Bundle bundle = new Bundle();
            }
            var2_7.putString(remoteInput.getResultKey(), uri.toString());
            intent3.putExtra(RemoteInputCompatJellybean.getExtraResultsKeyForData(string2), (Bundle)var2_7);
        }
        intent.setClipData(ClipData.newIntent((CharSequence)"android.remoteinput.results", (Intent)intent3));
    }

    static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] arrremoteInput, Intent intent, Bundle bundle) {
        Intent intent2;
        Intent intent3 = intent2 = RemoteInputCompatJellybean.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        Bundle object2 = intent3.getBundleExtra("android.remoteinput.resultsData");
        intent2 = object2;
        if (object2 == null) {
            intent2 = new Bundle();
        }
        for (RemoteInputCompatBase.RemoteInput remoteInput : arrremoteInput) {
            Object object = bundle.get(remoteInput.getResultKey());
            if (!(object instanceof CharSequence)) continue;
            intent2.putCharSequence(remoteInput.getResultKey(), (CharSequence)object);
        }
        intent3.putExtra("android.remoteinput.resultsData", (Bundle)intent2);
        intent.setClipData(ClipData.newIntent((CharSequence)"android.remoteinput.results", (Intent)intent3));
    }

    static RemoteInputCompatBase.RemoteInput fromBundle(Bundle bundle, RemoteInputCompatBase.RemoteInput.Factory factory) {
        Object object = bundle.getStringArrayList(KEY_ALLOWED_DATA_TYPES);
        HashSet<String> hashSet = new HashSet<String>();
        if (object != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                hashSet.add((String)object.next());
            }
        }
        return factory.build(bundle.getString(KEY_RESULT_KEY), bundle.getCharSequence(KEY_LABEL), bundle.getCharSequenceArray(KEY_CHOICES), bundle.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), bundle.getBundle(KEY_EXTRAS), hashSet);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static RemoteInputCompatBase.RemoteInput[] fromBundleArray(Bundle[] arrbundle, RemoteInputCompatBase.RemoteInput.Factory factory) {
        if (arrbundle == null) {
            return null;
        }
        RemoteInputCompatBase.RemoteInput[] arrremoteInput = factory.newArray(arrbundle.length);
        int n = 0;
        do {
            RemoteInputCompatBase.RemoteInput[] arrremoteInput2 = arrremoteInput;
            if (n >= arrbundle.length) return arrremoteInput2;
            arrremoteInput[n] = RemoteInputCompatJellybean.fromBundle(arrbundle[n], factory);
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Intent getClipDataIntentFromIntent(Intent intent) {
        ClipDescription clipDescription;
        if ((intent = intent.getClipData()) == null || !(clipDescription = intent.getDescription()).hasMimeType("text/vnd.android.intent") || !clipDescription.getLabel().equals("android.remoteinput.results")) {
            return null;
        }
        return intent.getItemAt(0).getIntent();
    }

    static Map<String, Uri> getDataResultsFromIntent(Intent object, String string2) {
        if ((object = RemoteInputCompatJellybean.getClipDataIntentFromIntent(object)) == null) {
            return null;
        }
        HashMap<String, Uri> hashMap = new HashMap<String, Uri>();
        for (String string3 : object.getExtras().keySet()) {
            String string4;
            if (!string3.startsWith(EXTRA_DATA_TYPE_RESULTS_DATA) || (string4 = string3.substring(EXTRA_DATA_TYPE_RESULTS_DATA.length())) == null || string4.isEmpty() || (string3 = object.getBundleExtra(string3).getString(string2)) == null || string3.isEmpty()) continue;
            hashMap.put(string4, Uri.parse((String)string3));
        }
        object = hashMap;
        if (hashMap.isEmpty()) {
            object = null;
        }
        return object;
    }

    private static String getExtraResultsKeyForData(String string2) {
        return EXTRA_DATA_TYPE_RESULTS_DATA + string2;
    }

    static Bundle getResultsFromIntent(Intent intent) {
        if ((intent = RemoteInputCompatJellybean.getClipDataIntentFromIntent(intent)) == null) {
            return null;
        }
        return (Bundle)intent.getExtras().getParcelable("android.remoteinput.resultsData");
    }

    static Bundle toBundle(RemoteInputCompatBase.RemoteInput object) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT_KEY, ((RemoteInputCompatBase.RemoteInput)object).getResultKey());
        bundle.putCharSequence(KEY_LABEL, ((RemoteInputCompatBase.RemoteInput)object).getLabel());
        bundle.putCharSequenceArray(KEY_CHOICES, ((RemoteInputCompatBase.RemoteInput)object).getChoices());
        bundle.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, ((RemoteInputCompatBase.RemoteInput)object).getAllowFreeFormInput());
        bundle.putBundle(KEY_EXTRAS, ((RemoteInputCompatBase.RemoteInput)object).getExtras());
        Object object2 = ((RemoteInputCompatBase.RemoteInput)object).getAllowedDataTypes();
        if (object2 != null && !object2.isEmpty()) {
            object = new ArrayList(object2.size());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                ((ArrayList)object).add((String)object2.next());
            }
            bundle.putStringArrayList(KEY_ALLOWED_DATA_TYPES, (ArrayList)object);
        }
        return bundle;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Bundle[] toBundleArray(RemoteInputCompatBase.RemoteInput[] arrremoteInput) {
        if (arrremoteInput == null) {
            return null;
        }
        Bundle[] arrbundle = new Bundle[arrremoteInput.length];
        int n = 0;
        do {
            Bundle[] arrbundle2 = arrbundle;
            if (n >= arrremoteInput.length) return arrbundle2;
            arrbundle[n] = RemoteInputCompatJellybean.toBundle(arrremoteInput[n]);
            ++n;
        } while (true);
    }
}

