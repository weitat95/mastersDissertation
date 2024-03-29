/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class RouteException
extends RuntimeException {
    private static final Method addSuppressedExceptionMethod;
    private IOException lastException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        Method method;
        try {
            method = Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class);
        }
        catch (Exception exception) {
            method = null;
        }
        addSuppressedExceptionMethod = method;
    }

    public RouteException(IOException iOException) {
        super(iOException);
        this.lastException = iOException;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void addSuppressedIfPossible(IOException iOException, IOException iOException2) {
        if (addSuppressedExceptionMethod == null) return;
        try {
            addSuppressedExceptionMethod.invoke(iOException, iOException2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            return;
        }
    }

    public void addConnectException(IOException iOException) {
        this.addSuppressedIfPossible(iOException, this.lastException);
        this.lastException = iOException;
    }

    public IOException getLastConnectException() {
        return this.lastException;
    }
}

