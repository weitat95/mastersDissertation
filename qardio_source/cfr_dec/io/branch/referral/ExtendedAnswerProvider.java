/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.text.TextUtils;
import com.crashlytics.android.answers.shim.AnswersOptionalLogger;
import com.crashlytics.android.answers.shim.KitEvent;
import io.branch.referral.Defines;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ExtendedAnswerProvider {
    ExtendedAnswerProvider() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addBranchAttributes(KitEvent kitEvent, String string2, String string3, String string4) {
        if (TextUtils.isEmpty((CharSequence)string4)) return;
        {
            if (string3.startsWith("~")) {
                kitEvent.putAttribute(string2.replaceFirst("~", "") + string3.replaceFirst("~", ""), string4);
                return;
            } else {
                if (!string3.equals("$" + Defines.Jsonkey.IdentityID.getKey())) return;
                {
                    kitEvent.putAttribute(Defines.Jsonkey.ReferringBranchIdentity.getKey(), string4);
                    return;
                }
            }
        }
    }

    private void addJsonArrayToKitEvent(KitEvent kitEvent, JSONArray jSONArray, String string2) throws JSONException {
        for (int i = 0; i < jSONArray.length(); ++i) {
            this.addBranchAttributes(kitEvent, string2, "~" + Integer.toString(i), jSONArray.getString(i));
        }
    }

    private void addJsonObjectToKitEvent(KitEvent kitEvent, JSONObject jSONObject, String string2) throws JSONException {
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            String string3 = (String)iterator.next();
            Object object = jSONObject.get(string3);
            if (string3.startsWith("+")) continue;
            if (object instanceof JSONObject) {
                this.addJsonObjectToKitEvent(kitEvent, (JSONObject)object, string2 + string3 + ".");
                continue;
            }
            if (object instanceof JSONArray) {
                this.addJsonArrayToKitEvent(kitEvent, (JSONArray)object, string3 + ".");
                continue;
            }
            this.addBranchAttributes(kitEvent, string2, string3, jSONObject.getString(string3));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void provideData(String object, JSONObject jSONObject, String string2) {
        try {
            object = new KitEvent((String)object);
            if (jSONObject == null) return;
        }
        catch (Throwable throwable) {
            return;
        }
        this.addJsonObjectToKitEvent((KitEvent)object, jSONObject, "");
        ((KitEvent)object).putAttribute(Defines.Jsonkey.BranchIdentity.getKey(), string2);
        AnswersOptionalLogger.get().logKitEvent((KitEvent)object);
    }
}

