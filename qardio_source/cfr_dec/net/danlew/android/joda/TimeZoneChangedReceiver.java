/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 */
package net.danlew.android.joda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.TimeZone;
import org.joda.time.DateTimeZone;

public class TimeZoneChangedReceiver
extends BroadcastReceiver {
    public void onReceive(Context object, Intent intent) {
        object = intent.getStringExtra("time-zone");
        try {
            DateTimeZone.setDefault(DateTimeZone.forTimeZone(TimeZone.getDefault()));
            Log.d((String)"joda-time-android", (String)("TIMEZONE_CHANGED received, changed default timezone to \"" + (String)object + "\""));
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e((String)"joda-time-android", (String)("Could not recognize timezone id \"" + (String)object + "\""), (Throwable)illegalArgumentException);
            return;
        }
    }
}

