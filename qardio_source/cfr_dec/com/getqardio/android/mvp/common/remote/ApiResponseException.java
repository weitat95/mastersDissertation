/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.remote.StandardErrorResponse;

public class ApiResponseException
extends Throwable {
    private StandardErrorResponse standardErrorResponse;

    public ApiResponseException(StandardErrorResponse standardErrorResponse) {
        this.standardErrorResponse = standardErrorResponse;
    }
}

