/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.tls;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

public final class BasicCertificateChainCleaner
extends CertificateChainCleaner {
    private final TrustRootIndex trustRootIndex;

    public BasicCertificateChainCleaner(TrustRootIndex trustRootIndex) {
        this.trustRootIndex = trustRootIndex;
    }

    private boolean verifySignature(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        if (!x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
            return false;
        }
        try {
            x509Certificate.verify(x509Certificate2.getPublicKey());
            return true;
        }
        catch (GeneralSecurityException generalSecurityException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public List<Certificate> clean(List<Certificate> collection, String arrayList) throws SSLPeerUnverifiedException {
        collection = new ArrayDeque<Certificate>(collection);
        arrayList = new ArrayList();
        arrayList.add((Certificate)collection.removeFirst());
        boolean bl = false;
        int n = 0;
        while (n < 9) {
            X509Certificate x509Certificate = (X509Certificate)arrayList.get(arrayList.size() - 1);
            Object object = this.trustRootIndex.findByIssuerAndSignature(x509Certificate);
            if (object != null) {
                if (arrayList.size() > 1 || !x509Certificate.equals(object)) {
                    arrayList.add((Certificate)object);
                }
                if (this.verifySignature((X509Certificate)object, (X509Certificate)object)) {
                    return arrayList;
                }
                bl = true;
            } else {
                X509Certificate x509Certificate2;
                object = collection.iterator();
                do {
                    if (object.hasNext()) continue;
                    if (bl) return arrayList;
                    throw new SSLPeerUnverifiedException("Failed to find a trusted cert that signed " + x509Certificate);
                } while (!this.verifySignature(x509Certificate, x509Certificate2 = (X509Certificate)object.next()));
                object.remove();
                arrayList.add(x509Certificate2);
            }
            ++n;
        }
        throw new SSLPeerUnverifiedException("Certificate chain too long: " + arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        return object == this || object instanceof BasicCertificateChainCleaner && ((BasicCertificateChainCleaner)object).trustRootIndex.equals(this.trustRootIndex);
    }

    public int hashCode() {
        return this.trustRootIndex.hashCode();
    }
}

