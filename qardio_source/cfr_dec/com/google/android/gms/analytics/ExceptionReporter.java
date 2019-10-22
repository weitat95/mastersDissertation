/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.internal.zzapr;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaru;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ExceptionReporter
implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    private final Thread.UncaughtExceptionHandler zzdoq;
    private final Tracker zzdor;
    private ExceptionParser zzdos;
    private GoogleAnalytics zzdot;

    /*
     * Enabled aggressive block sorting
     */
    public ExceptionReporter(Tracker object, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (object == null) {
            throw new NullPointerException("tracker cannot be null");
        }
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.zzdoq = uncaughtExceptionHandler;
        this.zzdor = object;
        this.zzdos = new StandardExceptionParser(context, new ArrayList<String>());
        this.mContext = context.getApplicationContext();
        object = uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName();
        object = ((String)(object = String.valueOf(object))).length() != 0 ? "ExceptionReporter created, original handler is ".concat((String)object) : new String("ExceptionReporter created, original handler is ");
        zzaru.v((String)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String string2;
        Object object = "UncaughtException";
        if (this.zzdos != null) {
            object = thread != null ? thread.getName() : null;
            object = this.zzdos.getDescription((String)object, throwable);
        }
        string2 = (string2 = String.valueOf(object)).length() != 0 ? "Reporting uncaught exception: ".concat(string2) : new String("Reporting uncaught exception: ");
        zzaru.v(string2);
        this.zzdor.send(new HitBuilders.ExceptionBuilder().setDescription((String)object).setFatal(true).build());
        if (this.zzdot == null) {
            this.zzdot = GoogleAnalytics.getInstance(this.mContext);
        }
        object = this.zzdot;
        ((GoogleAnalytics)object).dispatchLocalHits();
        ((zza)object).zzum().zzwx().zzwo();
        if (this.zzdoq != null) {
            zzaru.v("Passing exception to the original handler");
            this.zzdoq.uncaughtException(thread, throwable);
        }
    }

    final Thread.UncaughtExceptionHandler zzuq() {
        return this.zzdoq;
    }
}

