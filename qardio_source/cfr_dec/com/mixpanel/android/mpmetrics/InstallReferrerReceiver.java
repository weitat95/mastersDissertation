/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.mixpanel.android.mpmetrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.mixpanel.android.mpmetrics.PersistentIdentity;
import com.mixpanel.android.util.MPLog;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstallReferrerReceiver
extends BroadcastReceiver {
    private final Pattern UTM_CAMPAIGN_PATTERN;
    private final Pattern UTM_CONTENT_PATTERN;
    private final Pattern UTM_MEDIUM_PATTERN;
    private final Pattern UTM_SOURCE_PATTERN = Pattern.compile("(^|&)utm_source=([^&#=]*)([#&]|$)");
    private final Pattern UTM_TERM_PATTERN;

    public InstallReferrerReceiver() {
        this.UTM_MEDIUM_PATTERN = Pattern.compile("(^|&)utm_medium=([^&#=]*)([#&]|$)");
        this.UTM_CAMPAIGN_PATTERN = Pattern.compile("(^|&)utm_campaign=([^&#=]*)([#&]|$)");
        this.UTM_CONTENT_PATTERN = Pattern.compile("(^|&)utm_content=([^&#=]*)([#&]|$)");
        this.UTM_TERM_PATTERN = Pattern.compile("(^|&)utm_term=([^&#=]*)([#&]|$)");
    }

    private String find(Matcher object) {
        if (((Matcher)object).find() && (object = ((Matcher)object).group(2)) != null) {
            try {
                object = URLDecoder.decode((String)object, "UTF-8");
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                MPLog.e("MixpanelAPI.InstRfrRcvr", "Could not decode a parameter into UTF-8");
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent object) {
        String string2;
        if ((object = object.getExtras()) == null || (string2 = object.getString("referrer")) == null) {
            return;
        }
        object = new HashMap();
        object.put("referrer", string2);
        String string3 = this.find(this.UTM_SOURCE_PATTERN.matcher(string2));
        if (string3 != null) {
            object.put("utm_source", string3);
        }
        if ((string3 = this.find(this.UTM_MEDIUM_PATTERN.matcher(string2))) != null) {
            object.put("utm_medium", string3);
        }
        if ((string3 = this.find(this.UTM_CAMPAIGN_PATTERN.matcher(string2))) != null) {
            object.put("utm_campaign", string3);
        }
        if ((string3 = this.find(this.UTM_CONTENT_PATTERN.matcher(string2))) != null) {
            object.put("utm_content", string3);
        }
        if ((string2 = this.find(this.UTM_TERM_PATTERN.matcher(string2))) != null) {
            object.put("utm_term", string2);
        }
        PersistentIdentity.writeReferrerPrefs(context, "com.mixpanel.android.mpmetrics.ReferralInfo", (Map<String, String>)object);
    }
}

