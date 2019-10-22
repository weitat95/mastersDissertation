/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.model.remote;

import com.getqardio.android.mvp.activity_tracker.today.model.TodayActivityDataSource;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.remote.ServerInterface;

public class TodayActivityRemoteDataSource
implements TodayActivityDataSource {
    private AccountContextHelper accountContextHelper;
    private final ServerInterface serverInterface;

    public TodayActivityRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        this.serverInterface = serverInterface;
        this.accountContextHelper = accountContextHelper;
    }
}

