/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

class JdkWithJettyBootPlatform
extends Platform {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;

    public JdkWithJettyBootPlatform(Method method, Method method2, Method method3, Class<?> class_, Class<?> class_2) {
        this.putMethod = method;
        this.getMethod = method2;
        this.removeMethod = method3;
        this.clientProviderClass = class_;
        this.serverProviderClass = class_2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Platform buildIfSupported() {
        try {
            Class<?> class_ = Class.forName("org.eclipse.jetty.alpn.ALPN");
            Class<?> class_2 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$Provider");
            Class<?> class_3 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ClientProvider");
            Class<?> class_4 = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ServerProvider");
            return new JdkWithJettyBootPlatform(class_.getMethod("put", SSLSocket.class, class_2), class_.getMethod("get", SSLSocket.class), class_.getMethod("remove", SSLSocket.class), class_3, class_4);
        }
        catch (ClassNotFoundException classNotFoundException) {
            do {
                return null;
                break;
            } while (true);
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
    public void afterHandshake(SSLSocket sSLSocket) {
        try {
            this.removeMethod.invoke(null, sSLSocket);
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
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void configureTlsExtensions(SSLSocket sSLSocket, String object, List<Protocol> class_) {
        void var1_3;
        Object object2 = JdkWithJettyBootPlatform.alpnProtocolNames(class_);
        try {
            object = Platform.class.getClassLoader();
            class_ = this.clientProviderClass;
            Class<?> class_2 = this.serverProviderClass;
            object2 = new JettyNegoProvider((List<String>)object2);
            object = Proxy.newProxyInstance((ClassLoader)object, new Class[]{class_, class_2}, (InvocationHandler)object2);
            this.putMethod.invoke(null, sSLSocket, object);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new AssertionError(var1_3);
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new AssertionError(var1_3);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String getSelectedProtocol(SSLSocket object) {
        block5: {
            object = (JettyNegoProvider)Proxy.getInvocationHandler(this.getMethod.invoke(null, object));
            if (((JettyNegoProvider)object).unsupported || ((JettyNegoProvider)object).selected != null) break block5;
            Platform.get().log(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
            return null;
        }
        try {
            if (((JettyNegoProvider)object).unsupported) return null;
            return ((JettyNegoProvider)object).selected;
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

    private static class JettyNegoProvider
    implements InvocationHandler {
        private final List<String> protocols;
        String selected;
        boolean unsupported;

        public JettyNegoProvider(List<String> list) {
            this.protocols = list;
        }

        @Override
        public Object invoke(Object object, Method method, Object[] arrobject) throws Throwable {
            String string2 = method.getName();
            Class<?> class_ = method.getReturnType();
            object = arrobject;
            if (arrobject == null) {
                object = Util.EMPTY_STRING_ARRAY;
            }
            if (string2.equals("supports") && Boolean.TYPE == class_) {
                return true;
            }
            if (string2.equals("unsupported") && Void.TYPE == class_) {
                this.unsupported = true;
                return null;
            }
            if (string2.equals("protocols") && ((Object[])object).length == 0) {
                return this.protocols;
            }
            if ((string2.equals("selectProtocol") || string2.equals("select")) && String.class == class_ && ((Object[])object).length == 1 && object[0] instanceof List) {
                object = (List)object[0];
                int n = object.size();
                for (int i = 0; i < n; ++i) {
                    if (!this.protocols.contains(object.get(i))) continue;
                    this.selected = object = (String)object.get(i);
                    return object;
                }
                this.selected = object = this.protocols.get(0);
                return object;
            }
            if ((string2.equals("protocolSelected") || string2.equals("selected")) && ((Object[])object).length == 1) {
                this.selected = (String)object[0];
                return null;
            }
            return method.invoke(this, (Object[])object);
        }
    }

}

