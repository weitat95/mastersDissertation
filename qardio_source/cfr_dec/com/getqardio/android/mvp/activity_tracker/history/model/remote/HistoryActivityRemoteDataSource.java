/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.model.remote;

import com.getqardio.android.mvp.activity_tracker.history.model.HistoryActivityDataSource;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.remote.ServerInterface;

public class HistoryActivityRemoteDataSource
implements HistoryActivityDataSource {
    private AccountContextHelper accountContextHelper;
    private final ServerInterface serverInterface;

    public HistoryActivityRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        this.serverInterface = serverInterface;
        this.accountContextHelper = accountContextHelper;
    }
}

