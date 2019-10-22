/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.segment.analytics.integrations;

import android.util.Log;
import com.segment.analytics.Analytics;

public final class Logger {
    public final Analytics.LogLevel logLevel;
    private final String tag;

    public Logger(String string2, Analytics.LogLevel logLevel) {
        this.tag = string2;
        this.logLevel = logLevel;
    }

    private boolean shouldLog(Analytics.LogLevel logLevel) {
        return this.logLevel.ordinal() >= logLevel.ordinal();
    }

    public static Logger with(Analytics.LogLevel logLevel) {
        return new Logger("Analytics", logLevel);
    }

    public void debug(String string2, Object ... arrobject) {
        if (this.shouldLog(Analytics.LogLevel.DEBUG)) {
            Log.d((String)this.tag, (String)String.format(string2, arrobject));
        }
    }

    public void error(Throwable throwable, String string2, Object ... arrobject) {
        if (this.shouldLog(Analytics.LogLevel.INFO)) {
            Log.e((String)this.tag, (String)String.format(string2, arrobject), (Throwable)throwable);
        }
    }

    public void info(String string2, Object ... arrobject) {
        if (this.shouldLog(Analytics.LogLevel.INFO)) {
            Log.i((String)this.tag, (String)String.format(string2, arrobject));
        }
    }

    public Logger subLog(String string2) {
        return new Logger("Analytics-" + string2, this.logLevel);
    }

    public void verbose(String string2, Object ... arrobject) {
        if (this.shouldLog(Analytics.LogLevel.VERBOSE)) {
            Log.v((String)this.tag, (String)String.format(string2, arrobject));
        }
    }
}

