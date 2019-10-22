/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.tls;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

public abstract class TrustRootIndex {
    public static TrustRootIndex get(X509TrustManager x509TrustManager) {
        try {
            Object object = x509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
            ((AccessibleObject)object).setAccessible(true);
            object = new AndroidTrustRootIndex(x509TrustManager, (Method)object);
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return TrustRootIndex.get(x509TrustManager.getAcceptedIssuers());
        }
    }

    public static TrustRootIndex get(X509Certificate ... arrx509Certificate) {
        return new BasicTrustRootIndex(arrx509Certificate);
    }

    public abstract X509Certificate findByIssuerAndSignature(X509Certificate var1);

    static final class AndroidTrustRootIndex
    extends TrustRootIndex {
        private final Method findByIssuerAndSignatureMethod;
        private final X509TrustManager trustManager;

        AndroidTrustRootIndex(X509TrustManager x509TrustManager, Method method) {
            this.findByIssuerAndSignatureMethod = method;
            this.trustManager = x509TrustManager;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (object == this) break block4;
                    if (!(object instanceof AndroidTrustRootIndex)) {
                        return false;
                    }
                    object = (AndroidTrustRootIndex)object;
                    if (!this.trustManager.equals(((AndroidTrustRootIndex)object).trustManager) || !this.findByIssuerAndSignatureMethod.equals(((AndroidTrustRootIndex)object).findByIssuerAndSignatureMethod)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
            block4: {
                Object var2_4 = null;
                TrustAnchor trustAnchor = (TrustAnchor)this.findByIssuerAndSignatureMethod.invoke(this.trustManager, x509Certificate);
                x509Certificate = var2_4;
                if (trustAnchor == null) break block4;
                try {
                    x509Certificate = trustAnchor.getTrustedCert();
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new AssertionError();
                }
                catch (InvocationTargetException invocationTargetException) {
                    return null;
                }
            }
            return x509Certificate;
        }

        public int hashCode() {
            return this.trustManager.hashCode() + this.findByIssuerAndSignatureMethod.hashCode() * 31;
        }
    }

    static final class BasicTrustRootIndex
    extends TrustRootIndex {
        private final Map<X500Principal, Set<X509Certificate>> subjectToCaCerts = new LinkedHashMap<X500Principal, Set<X509Certificate>>();

        public BasicTrustRootIndex(X509Certificate ... arrx509Certificate) {
            for (X509Certificate x509Certificate : arrx509Certificate) {
                Set<X509Certificate> set;
                X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
                Set<X509Certificate> set2 = set = this.subjectToCaCerts.get(x500Principal);
                if (set == null) {
                    set2 = new LinkedHashSet<X509Certificate>(1);
                    this.subjectToCaCerts.put(x500Principal, set2);
                }
                set2.add(x509Certificate);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            return object == this || object instanceof BasicTrustRootIndex && ((BasicTrustRootIndex)object).subjectToCaCerts.equals(this.subjectToCaCerts);
        }

        @Override
        public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
            Iterator iterator = x509Certificate.getIssuerX500Principal();
            if ((iterator = this.subjectToCaCerts.get(iterator)) == null) {
                return null;
            }
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                X509Certificate x509Certificate2 = (X509Certificate)iterator.next();
                PublicKey publicKey = x509Certificate2.getPublicKey();
                try {
                    x509Certificate.verify(publicKey);
                    return x509Certificate2;
                }
                catch (Exception exception) {
                }
            }
            return null;
        }

        public int hashCode() {
            return this.subjectToCaCerts.hashCode();
        }
    }

}

