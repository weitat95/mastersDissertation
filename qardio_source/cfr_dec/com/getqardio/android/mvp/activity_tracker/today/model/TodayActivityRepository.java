/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.model;

import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;

public class TodayActivityRepository
implements TodayActivityDataSource {
    private final TodayActivityDataSource localDataSource;
    private final TodayActivityDataSource remoteDataSource;

    public TodayActivityRepository(TodayActivityDataSource todayActivityDataSource, TodayActivityDataSource todayActivityDataSource2) {
        this.localDataSource = todayActivityDataSource;
        this.remoteDataSource = todayActivityDataSource2;
    }
}

