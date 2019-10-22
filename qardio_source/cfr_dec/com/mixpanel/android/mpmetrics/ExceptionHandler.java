/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.os.Process;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private static ExceptionHandler sInstance;
    private final Thread.UncaughtExceptionHandler mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    public ExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void init() {
        if (sInstance != null) {
            return;
        }
        synchronized (ExceptionHandler.class) {
            if (sInstance == null) {
                sInstance = new ExceptionHandler();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void killProcessAndExit() {
        try {
            Thread.sleep(400L);
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        Process.killProcess((int)Process.myPid());
        System.exit(10);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(){

            @Override
            public void process(MixpanelAPI mixpanelAPI) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("$ae_crashed_reason", (Object)throwable.toString());
                    mixpanelAPI.track("$ae_crashed", jSONObject, true);
                    return;
                }
                catch (JSONException jSONException) {
                    return;
                }
            }
        });
        MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(){

            @Override
            public void process(MixpanelAPI mixpanelAPI) {
                mixpanelAPI.flushNoDecideCheck();
            }
        });
        if (this.mDefaultExceptionHandler != null) {
            this.mDefaultExceptionHandler.uncaughtException(thread, throwable);
            return;
        }
        this.killProcessAndExit();
    }

}

