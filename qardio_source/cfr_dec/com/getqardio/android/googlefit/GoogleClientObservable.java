/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleClientObserver;

public interface GoogleClientObservable {
    public void registerForGoogleClientChanges(GoogleClientObserver var1);

    public void unregisterForGoogleClientChanges(GoogleClientObserver var1);
}

