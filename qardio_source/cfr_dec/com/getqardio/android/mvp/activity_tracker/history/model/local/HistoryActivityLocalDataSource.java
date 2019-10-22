/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.model.local;

import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import io.realm.Realm;

public class HistoryActivityLocalDataSource
implements HistoryActivityDataSource {
    private Realm realm;

    public HistoryActivityLocalDataSource(Realm realm) {
        this.realm = realm;
    }
}

