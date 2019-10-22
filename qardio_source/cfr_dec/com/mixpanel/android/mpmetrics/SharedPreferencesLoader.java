/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class SharedPreferencesLoader {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    public Future<SharedPreferences> loadPreferences(Context object, String string2, OnPrefsLoadedListener onPrefsLoadedListener) {
        object = new FutureTask<SharedPreferences>(new LoadSharedPreferences((Context)object, string2, onPrefsLoadedListener));
        this.mExecutor.execute((Runnable)object);
        return object;
    }

    private static class LoadSharedPreferences
    implements Callable<SharedPreferences> {
        private final Context mContext;
        private final OnPrefsLoadedListener mListener;
        private final String mPrefsName;

        public LoadSharedPreferences(Context context, String string2, OnPrefsLoadedListener onPrefsLoadedListener) {
            this.mContext = context;
            this.mPrefsName = string2;
            this.mListener = onPrefsLoadedListener;
        }

        @Override
        public SharedPreferences call() {
            SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(this.mPrefsName, 0);
            if (this.mListener != null) {
                this.mListener.onPrefsLoaded(sharedPreferences);
            }
            return sharedPreferences;
        }
    }

    static interface OnPrefsLoadedListener {
        public void onPrefsLoaded(SharedPreferences var1);
    }

}

