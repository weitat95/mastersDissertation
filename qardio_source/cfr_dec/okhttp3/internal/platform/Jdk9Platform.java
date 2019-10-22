/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;

final class Jdk9Platform
extends Platform {
    final Method getProtocolMethod;
    final Method setProtocolMethod;

    public Jdk9Platform(Method method, Method method2) {
        this.setProtocolMethod = method;
        this.getProtocolMethod = method2;
    }

    public static Jdk9Platform buildIfSupported() {
        try {
            Jdk9Platform jdk9Platform = new Jdk9Platform(SSLParameters.class.getMethod("setApplicationProtocols", String[].class), SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]));
            return jdk9Platform;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String object, List<Protocol> list) {
        try {
            object = sSLSocket.getSSLParameters();
            list = Jdk9Platform.alpnProtocolNames(list);
            this.setProtocolMethod.invoke(object, new Object[]{list.toArray(new String[list.size()])});
            sSLSocket.setSSLParameters((SSLParameters)object);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new AssertionError();
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new AssertionError();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String getSelectedProtocol(SSLSocket object) {
        object = (String)this.getProtocolMethod.invoke(object, new Object[0]);
        if (object == null) return null;
        try {
            boolean bl = ((String)object).equals("");
            if (!bl) return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new AssertionError();
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new AssertionError();
        }
        return null;
    }
}

