/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.model.local;

import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import io.realm.Realm;

public class TodayActivityLocalDataSource
implements TodayActivityDataSource {
    private Realm realm;

    public TodayActivityLocalDataSource(Realm realm) {
        this.realm = realm;
    }
}

