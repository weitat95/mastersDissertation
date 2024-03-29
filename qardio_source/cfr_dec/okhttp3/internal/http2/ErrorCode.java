/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

public enum ErrorCode {
    NO_ERROR(0),
    PROTOCOL_ERROR(1),
    INTERNAL_ERROR(2),
    FLOW_CONTROL_ERROR(3),
    REFUSED_STREAM(7),
    CANCEL(8);

    public final int httpCode;

    private ErrorCode(int n2) {
        this.httpCode = n2;
    }

    public static ErrorCode fromHttp2(int n) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.httpCode != n) continue;
            return errorCode;
        }
        return null;
    }
}

