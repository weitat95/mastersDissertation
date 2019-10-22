/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.tls;

import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import okhttp3.internal.Util;
import okhttp3.internal.tls.DistinguishedNameParser;

public final class OkHostnameVerifier
implements HostnameVerifier {
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();

    private OkHostnameVerifier() {
    }

    public static List<String> allSubjectAltNames(X509Certificate object) {
        List<String> list = OkHostnameVerifier.getSubjectAltNames((X509Certificate)object, 7);
        object = OkHostnameVerifier.getSubjectAltNames((X509Certificate)object, 2);
        ArrayList<String> arrayList = new ArrayList<String>(list.size() + object.size());
        arrayList.addAll(list);
        arrayList.addAll((Collection<String>)object);
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<String> getSubjectAltNames(X509Certificate list, int n) {
        ArrayList arrayList = new ArrayList();
        try {
            list = ((X509Certificate)((Object)list)).getSubjectAlternativeNames();
            if (list == null) {
                return Collections.emptyList();
            }
            Iterator<List<?>> iterator = list.iterator();
            do {
                Integer n2;
                list = arrayList;
                if (!iterator.hasNext()) return list;
                list = iterator.next();
                if (list == null || list.size() < 2 || (n2 = (Integer)list.get(0)) == null || n2 != n || (list = (String)list.get(1)) == null) continue;
                arrayList.add(list);
            } while (true);
        }
        catch (CertificateParsingException certificateParsingException) {
            return Collections.emptyList();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean verifyHostname(String string2, String string3) {
        block7: {
            block6: {
                int n;
                if (string2 == null || string2.length() == 0 || string2.startsWith(".") || string2.endsWith("..") || string3 == null || string3.length() == 0 || string3.startsWith(".") || string3.endsWith("..")) break block6;
                String string4 = string2;
                if (!string2.endsWith(".")) {
                    string4 = string2 + '.';
                }
                string2 = string3;
                if (!string3.endsWith(".")) {
                    string2 = string3 + '.';
                }
                if (!(string2 = string2.toLowerCase(Locale.US)).contains("*")) {
                    return string4.equals(string2);
                }
                if (string2.startsWith("*.") && string2.indexOf(42, 1) == -1 && string4.length() >= string2.length() && !"*.".equals(string2) && string4.endsWith(string2 = string2.substring(1)) && ((n = string4.length() - string2.length()) <= 0 || string4.lastIndexOf(46, n - 1) == -1)) break block7;
            }
            return false;
        }
        return true;
    }

    private boolean verifyHostname(String string2, X509Certificate object) {
        string2 = string2.toLowerCase(Locale.US);
        boolean bl = false;
        List<String> list = OkHostnameVerifier.getSubjectAltNames((X509Certificate)object, 2);
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            bl = true;
            if (!this.verifyHostname(string2, list.get(i))) continue;
            return true;
        }
        if (!bl && (object = new DistinguishedNameParser(((X509Certificate)object).getSubjectX500Principal()).findMostSpecific("cn")) != null) {
            return this.verifyHostname(string2, (String)object);
        }
        return false;
    }

    private boolean verifyIpAddress(String string2, X509Certificate object) {
        object = OkHostnameVerifier.getSubjectAltNames((X509Certificate)object, 7);
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            if (!string2.equalsIgnoreCase((String)object.get(i))) continue;
            return true;
        }
        return false;
    }

    public boolean verify(String string2, X509Certificate x509Certificate) {
        if (Util.verifyAsIpAddress(string2)) {
            return this.verifyIpAddress(string2, x509Certificate);
        }
        return this.verifyHostname(string2, x509Certificate);
    }

    @Override
    public boolean verify(String string2, SSLSession sSLSession) {
        try {
            boolean bl = this.verify(string2, (X509Certificate)sSLSession.getPeerCertificates()[0]);
            return bl;
        }
        catch (SSLException sSLException) {
            return false;
        }
    }
}

