/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public final class zzak {
    private static final Uri zzgau;
    private static final Uri zzgav;

    static {
        Uri uri;
        zzgau = uri = Uri.parse((String)"https://plus.google.com/");
        zzgav = uri.buildUpon().appendPath("circles").appendPath("find").build();
    }

    public static Intent zzaln() {
        Intent intent = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
        intent.setPackage("com.google.android.wearable.app");
        return intent;
    }

    public static Intent zzgk(String string2) {
        string2 = Uri.fromParts((String)"package", (String)string2, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData((Uri)string2);
        return intent;
    }

    public static Intent zzt(String string2, String string3) {
        Intent intent = new Intent("android.intent.action.VIEW");
        string2 = Uri.parse((String)"market://details").buildUpon().appendQueryParameter("id", string2);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            string2.appendQueryParameter("pcampaignid", string3);
        }
        intent.setData(string2.build());
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }
}

