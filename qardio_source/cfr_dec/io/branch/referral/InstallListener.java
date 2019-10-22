/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.util.Log
 */
package io.branch.referral;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import io.branch.referral.Defines;
import io.branch.referral.PrefHelper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class InstallListener
extends BroadcastReceiver {
    private static IInstallReferrerEvents callback_;
    private static String installID_;
    private static boolean isWaitingForReferrer;
    private static boolean unReportedReferrerAvailable;

    static {
        installID_ = "bnc_no_value";
        callback_ = null;
    }

    public static void captureInstallReferrer(long l) {
        if (unReportedReferrerAvailable) {
            InstallListener.reportInstallReferrer();
            return;
        }
        isWaitingForReferrer = true;
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                InstallListener.reportInstallReferrer();
            }
        }, l);
    }

    public static String getInstallationID() {
        return installID_;
    }

    private static void reportInstallReferrer() {
        if (callback_ != null) {
            callback_.onInstallReferrerEventsFinished();
            callback_ = null;
            unReportedReferrerAvailable = false;
        }
    }

    public static void setListener(IInstallReferrerEvents iInstallReferrerEvents) {
        callback_ = iInstallReferrerEvents;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onReceive(Context object, Intent object2) {
        int n;
        String[] arrstring;
        HashMap<String, String> hashMap;
        int n2 = 0;
        if ((object2 = object2.getStringExtra("referrer")) == null) return;
        try {
            object2 = URLDecoder.decode((String)object2, "UTF-8");
            hashMap = new HashMap<String, String>();
            arrstring = ((String)object2).split("&");
            n = arrstring.length;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            Log.w((String)"BranchSDK", (String)"Illegal characters in url encoded string");
            return;
        }
        do {
            if (n2 < n) {
                String[] arrstring2 = arrstring[n2].split("=");
                if (arrstring2.length > 1) {
                    hashMap.put(URLDecoder.decode(arrstring2[0], "UTF-8"), URLDecoder.decode(arrstring2[1], "UTF-8"));
                }
            } else {
                object = PrefHelper.getInstance((Context)object);
                if (hashMap.containsKey(Defines.Jsonkey.LinkClickID.getKey())) {
                    installID_ = (String)hashMap.get(Defines.Jsonkey.LinkClickID.getKey());
                    ((PrefHelper)object).setLinkClickIdentifier(installID_);
                }
                if (hashMap.containsKey(Defines.Jsonkey.IsFullAppConv.getKey()) && hashMap.containsKey(Defines.Jsonkey.ReferringLink.getKey())) {
                    ((PrefHelper)object).setIsFullAppConversion(Boolean.parseBoolean((String)hashMap.get(Defines.Jsonkey.IsFullAppConv.getKey())));
                    ((PrefHelper)object).setAppLink((String)hashMap.get(Defines.Jsonkey.ReferringLink.getKey()));
                }
                if (hashMap.containsKey(Defines.Jsonkey.GoogleSearchInstallReferrer.getKey())) {
                    ((PrefHelper)object).setGoogleSearchInstallIdentifier((String)hashMap.get(Defines.Jsonkey.GoogleSearchInstallReferrer.getKey()));
                    ((PrefHelper)object).setGooglePlayReferrer((String)object2);
                }
                unReportedReferrerAvailable = true;
                if (isWaitingForReferrer) {
                    InstallListener.reportInstallReferrer();
                }
                return;
            }
            ++n2;
        } while (true);
    }

    static interface IInstallReferrerEvents {
        public void onInstallReferrerEventsFinished();
    }

}

