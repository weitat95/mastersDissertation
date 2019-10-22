/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package okhttp3.internal.platform;

import android.util.Log;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.platform.OptionalMethod;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.CertificateChainCleaner;

class AndroidPlatform
extends Platform {
    private final CloseGuard closeGuard = CloseGuard.get();
    private final OptionalMethod<Socket> getAlpnSelectedProtocol;
    private final OptionalMethod<Socket> setAlpnProtocols;
    private final OptionalMethod<Socket> setHostname;
    private final OptionalMethod<Socket> setUseSessionTickets;
    private final Class<?> sslParametersClass;

    public AndroidPlatform(Class<?> class_, OptionalMethod<Socket> optionalMethod, OptionalMethod<Socket> optionalMethod2, OptionalMethod<Socket> optionalMethod3, OptionalMethod<Socket> optionalMethod4) {
        this.sslParametersClass = class_;
        this.setUseSessionTickets = optionalMethod;
        this.setHostname = optionalMethod2;
        this.getAlpnSelectedProtocol = optionalMethod3;
        this.setAlpnProtocols = optionalMethod4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Platform buildIfSupported() {
        var1 = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
        {
            catch (ClassNotFoundException var0_1) {
                var1 = Class.forName("org.apache.harmony.xnet.provider.jsse.SSLParametersImpl");
            }
            var4_5 = new OptionalMethod<Socket>(null, "setUseSessionTickets", new Class[]{Boolean.TYPE});
            var5_6 = new OptionalMethod<Socket>(null, "setHostname", new Class[]{String.class});
            var3_7 = null;
            var2_10 = null;
        }
lbl11:
        // 2 sources
        catch (ClassNotFoundException var0_3) {
            return null;
        }
        {
            block9: {
                Class.forName("android.net.Network");
                var0_2 = new OptionalMethod<Socket>(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
                try {
                    var2_11 = var3_8 = new OptionalMethod<T>(null, "setAlpnProtocols", new Class[]{byte[].class});
                    break block9;
                }
                catch (ClassNotFoundException var3_9) {}
                catch (ClassNotFoundException var0_4) {
                    var0_2 = var3_7;
                }
            }
            ** try [egrp 4[TRYBLOCK] [4 : 97->122)] { 
lbl26:
            // 1 sources
            return new AndroidPlatform(var1, var4_5, var5_6, var0_2, (OptionalMethod<Socket>)var2_12);
        }
    }

    @Override
    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager x509TrustManager) {
        try {
            Object object = Class.forName("android.net.http.X509TrustManagerExtensions");
            object = new AndroidCertificateChainCleaner(((Class)object).getConstructor(X509TrustManager.class).newInstance(x509TrustManager), ((Class)object).getMethod("checkServerTrusted", X509Certificate[].class, String.class, String.class));
            return object;
        }
        catch (Exception exception) {
            return super.buildCertificateChainCleaner(x509TrustManager);
        }
    }

    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String arrby, List<Protocol> list) {
        if (arrby != null) {
            this.setUseSessionTickets.invokeOptionalWithoutCheckedException(sSLSocket, true);
            this.setHostname.invokeOptionalWithoutCheckedException(sSLSocket, new Object[]{arrby});
        }
        if (this.setAlpnProtocols != null && this.setAlpnProtocols.isSupported(sSLSocket)) {
            arrby = AndroidPlatform.concatLengthPrefixed(list);
            this.setAlpnProtocols.invokeWithoutCheckedException(sSLSocket, new Object[]{arrby});
        }
    }

    @Override
    public void connectSocket(Socket socket, InetSocketAddress serializable, int n) throws IOException {
        try {
            socket.connect((SocketAddress)serializable, n);
            return;
        }
        catch (AssertionError assertionError) {
            if (Util.isAndroidGetsocknameError(assertionError)) {
                throw new IOException((Throwable)((Object)assertionError));
            }
            throw assertionError;
        }
        catch (SecurityException securityException) {
            serializable = new IOException("Exception in connect");
            ((Throwable)serializable).initCause(securityException);
            throw serializable;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public String getSelectedProtocol(SSLSocket object) {
        void var1_4;
        if (this.getAlpnSelectedProtocol == null || !this.getAlpnSelectedProtocol.isSupported((Socket)object)) {
            return null;
        }
        byte[] arrby = (byte[])this.getAlpnSelectedProtocol.invokeWithoutCheckedException((Socket)object, new Object[0]);
        if (arrby != null) {
            String string2 = new String(arrby, Util.UTF_8);
            return var1_4;
        }
        return var1_4;
    }

    @Override
    public Object getStackTraceForCloseable(String string2) {
        return this.closeGuard.createAndOpen(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isCleartextTrafficPermitted(String string2) {
        try {
            Class<?> class_ = Class.forName("android.security.NetworkSecurityPolicy");
            Object object = class_.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            return (Boolean)class_.getMethod("isCleartextTrafficPermitted", String.class).invoke(object, string2);
        }
        catch (ClassNotFoundException classNotFoundException) {
            do {
                return super.isCleartextTrafficPermitted(string2);
                break;
            } while (true);
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new AssertionError();
                break;
            } while (true);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new AssertionError();
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new AssertionError();
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return super.isCleartextTrafficPermitted(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void log(int n, String string2, Throwable throwable) {
        int n2 = 5;
        if (n != 5) {
            n2 = 3;
        }
        String string3 = string2;
        if (throwable != null) {
            string3 = string2 + '\n' + Log.getStackTraceString((Throwable)throwable);
        }
        n = 0;
        int n3 = string3.length();
        while (n < n3) {
            int n4;
            int n5 = string3.indexOf(10, n);
            if (n5 == -1) {
                n5 = n3;
            }
            do {
                n4 = Math.min(n5, n + 4000);
                Log.println((int)n2, (String)"OkHttp", (String)string3.substring(n, n4));
                n = n4;
            } while (n4 < n5);
            n = n4 + 1;
        }
        return;
    }

    @Override
    public void logCloseableLeak(String string2, Object object) {
        if (!this.closeGuard.warnIfOpen(object)) {
            this.log(5, string2, null);
        }
    }

    static final class AndroidCertificateChainCleaner
    extends CertificateChainCleaner {
        private final Method checkServerTrusted;
        private final Object x509TrustManagerExtensions;

        AndroidCertificateChainCleaner(Object object, Method method) {
            this.x509TrustManagerExtensions = object;
            this.checkServerTrusted = method;
        }

        @Override
        public List<Certificate> clean(List<Certificate> object, String object2) throws SSLPeerUnverifiedException {
            try {
                object = object.toArray(new X509Certificate[object.size()]);
                object = (List)this.checkServerTrusted.invoke(this.x509TrustManagerExtensions, object, "RSA", object2);
                return object;
            }
            catch (InvocationTargetException invocationTargetException) {
                object2 = new SSLPeerUnverifiedException(invocationTargetException.getMessage());
                ((Throwable)object2).initCause(invocationTargetException);
                throw object2;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
        }

        public boolean equals(Object object) {
            return object instanceof AndroidCertificateChainCleaner;
        }

        public int hashCode() {
            return 0;
        }
    }

    static final class CloseGuard {
        private final Method getMethod;
        private final Method openMethod;
        private final Method warnIfOpenMethod;

        CloseGuard(Method method, Method method2, Method method3) {
            this.getMethod = method;
            this.openMethod = method2;
            this.warnIfOpenMethod = method3;
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        static CloseGuard get() {
            Method method;
            void var2_2;
            Method method2;
            try {
                Class<?> class_ = Class.forName("dalvik.system.CloseGuard");
                method2 = class_.getMethod("get", new Class[0]);
                method = class_.getMethod("open", String.class);
                Method method3 = class_.getMethod("warnIfOpen", new Class[0]);
                return new CloseGuard(method2, method, (Method)var2_2);
            }
            catch (Exception exception) {
                method2 = null;
                method = null;
                return new CloseGuard(method2, method, (Method)var2_2);
            }
        }

        Object createAndOpen(String string2) {
            if (this.getMethod != null) {
                try {
                    Object object = this.getMethod.invoke(null, new Object[0]);
                    this.openMethod.invoke(object, string2);
                    return object;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        boolean warnIfOpen(Object object) {
            boolean bl = false;
            if (object == null) return bl;
            try {
                this.warnIfOpenMethod.invoke(object, new Object[0]);
                return true;
            }
            catch (Exception exception) {
                return false;
            }
        }
    }

}

