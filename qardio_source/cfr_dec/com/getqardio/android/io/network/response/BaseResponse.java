/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.response;

public class BaseResponse<DataType, ErrorType> {
    private DataType data;
    private ErrorType error;
    private Status status = Status.UNKNOWN;

    public DataType getData() {
        return this.data;
    }

    public ErrorType getError() {
        return this.error;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean isSuccessful() {
        return this.status == Status.SUCCESS;
    }

    public void setData(DataType DataType2) {
        this.data = DataType2;
    }

    public void setError(ErrorType ErrorType) {
        this.error = ErrorType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        UNKNOWN,
        SUCCESS,
        FAILURE,
        NETWORK_ERROR;

    }

}

