/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.RemoteInput
 *  android.app.RemoteInput$Builder
 *  android.content.ClipData
 *  android.content.ClipData$Item
 *  android.content.ClipDescription
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.RemoteInput;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class RemoteInputCompatApi20 {
    private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";

    RemoteInputCompatApi20() {
    }

    /*
     * WARNING - void declaration
     */
    public static void addDataResultToIntent(RemoteInputCompatBase.RemoteInput remoteInput, Intent intent, Map<String, Uri> object2) {
        Intent intent2;
        Intent intent3 = intent2 = RemoteInputCompatApi20.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        for (Map.Entry entry : object2.entrySet()) {
            void var2_7;
            String string2 = (String)entry.getKey();
            Uri uri = (Uri)entry.getValue();
            if (string2 == null) continue;
            Intent intent4 = intent2 = intent3.getBundleExtra(RemoteInputCompatApi20.getExtraResultsKeyForData(string2));
            if (intent2 == null) {
                Bundle bundle = new Bundle();
            }
            var2_7.putString(remoteInput.getResultKey(), uri.toString());
            intent3.putExtra(RemoteInputCompatApi20.getExtraResultsKeyForData(string2), (Bundle)var2_7);
        }
        intent.setClipData(ClipData.newIntent((CharSequence)"android.remoteinput.results", (Intent)intent3));
    }

    /*
     * Enabled aggressive block sorting
     */
    static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] arrremoteInput, Intent intent, Bundle bundle) {
        Bundle bundle2 = RemoteInputCompatApi20.getResultsFromIntent(intent);
        if (bundle2 != null) {
            bundle2.putAll(bundle);
            bundle = bundle2;
        }
        int n = arrremoteInput.length;
        int n2 = 0;
        while (n2 < n) {
            RemoteInputCompatBase.RemoteInput remoteInput = arrremoteInput[n2];
            Map<String, Uri> map = RemoteInputCompatApi20.getDataResultsFromIntent(intent, remoteInput.getResultKey());
            RemoteInput.addResultsToIntent((RemoteInput[])RemoteInputCompatApi20.fromCompat(new RemoteInputCompatBase.RemoteInput[]{remoteInput}), (Intent)intent, (Bundle)bundle);
            if (map != null) {
                RemoteInputCompatApi20.addDataResultToIntent(remoteInput, intent, map);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static RemoteInput[] fromCompat(RemoteInputCompatBase.RemoteInput[] arrremoteInput) {
        if (arrremoteInput == null) {
            return null;
        }
        RemoteInput[] arrremoteInput2 = new RemoteInput[arrremoteInput.length];
        int n = 0;
        do {
            Object object = arrremoteInput2;
            if (n >= arrremoteInput.length) return object;
            object = arrremoteInput[n];
            arrremoteInput2[n] = new RemoteInput.Builder(((RemoteInputCompatBase.RemoteInput)object).getResultKey()).setLabel(((RemoteInputCompatBase.RemoteInput)object).getLabel()).setChoices(((RemoteInputCompatBase.RemoteInput)object).getChoices()).setAllowFreeFormInput(((RemoteInputCompatBase.RemoteInput)object).getAllowFreeFormInput()).addExtras(((RemoteInputCompatBase.RemoteInput)object).getExtras()).build();
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
        if ((object = RemoteInputCompatApi20.getClipDataIntentFromIntent(object)) == null) {
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
        return RemoteInput.getResultsFromIntent((Intent)intent);
    }

    static RemoteInputCompatBase.RemoteInput[] toCompat(RemoteInput[] arrremoteInput, RemoteInputCompatBase.RemoteInput.Factory factory) {
        if (arrremoteInput == null) {
            return null;
        }
        RemoteInputCompatBase.RemoteInput[] arrremoteInput2 = factory.newArray(arrremoteInput.length);
        for (int i = 0; i < arrremoteInput.length; ++i) {
            RemoteInput remoteInput = arrremoteInput[i];
            arrremoteInput2[i] = factory.build(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null);
        }
        return arrremoteInput2;
    }
}

