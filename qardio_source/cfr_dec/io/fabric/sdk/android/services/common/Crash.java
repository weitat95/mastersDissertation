/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

public abstract class Crash {
    private final String exceptionName;
    private final String sessionId;

    public Crash(String string2, String string3) {
        this.sessionId = string2;
        this.exceptionName = string3;
    }

    public String getExceptionName() {
        return this.exceptionName;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public static class FatalException
    extends Crash {
        public FatalException(String string2, String string3) {
            super(string2, string3);
        }
    }

    public static class LoggedException
    extends Crash {
        public LoggedException(String string2, String string3) {
            super(string2, string3);
        }
    }

}

