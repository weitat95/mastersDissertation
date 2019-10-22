/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.model;

import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;

public class HistoryActivityRepository
implements HistoryActivityDataSource {
    private final HistoryActivityDataSource localDataSource;
    private final HistoryActivityDataSource remoteDataSource;

    public HistoryActivityRepository(HistoryActivityDataSource historyActivityDataSource, HistoryActivityDataSource historyActivityDataSource2) {
        this.localDataSource = historyActivityDataSource;
        this.remoteDataSource = historyActivityDataSource2;
    }
}

