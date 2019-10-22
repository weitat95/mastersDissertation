/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import com.getqardio.android.mvp.common.util.RxEventBus;

public class ApplicationModule {
    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    Context provideContext() {
        return this.context;
    }

    RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }
}

