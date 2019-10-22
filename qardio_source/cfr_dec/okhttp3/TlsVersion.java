/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

public enum TlsVersion {
    TLS_1_3("TLSv1.3"),
    TLS_1_2("TLSv1.2"),
    TLS_1_1("TLSv1.1"),
    TLS_1_0("TLSv1"),
    SSL_3_0("SSLv3");

    final String javaName;

    private TlsVersion(String string3) {
        this.javaName = string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static TlsVersion forJavaName(String string2) {
        int n = -1;
        switch (string2.hashCode()) {
            case -503070501: {
                if (!string2.equals("TLSv1.3")) break;
                n = 0;
                break;
            }
            case -503070502: {
                if (!string2.equals("TLSv1.2")) break;
                n = 1;
                break;
            }
            case -503070503: {
                if (!string2.equals("TLSv1.1")) break;
                n = 2;
                break;
            }
            case 79923350: {
                if (!string2.equals("TLSv1")) break;
                n = 3;
                break;
            }
            case 79201641: {
                if (!string2.equals("SSLv3")) break;
                n = 4;
                break;
            }
        }
        switch (n) {
            default: {
                throw new IllegalArgumentException("Unexpected TLS version: " + string2);
            }
            case 0: {
                return TLS_1_3;
            }
            case 1: {
                return TLS_1_2;
            }
            case 2: {
                return TLS_1_1;
            }
            case 3: {
                return TLS_1_0;
            }
            case 4: 
        }
        return SSL_3_0;
    }

    public String javaName() {
        return this.javaName;
    }
}

