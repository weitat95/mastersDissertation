/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Protocol;

public final class StatusLine {
    public final int code;
    public final String message;
    public final Protocol protocol;

    public StatusLine(Protocol protocol, int n, String string2) {
        this.protocol = protocol;
        this.code = n;
        this.message = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static StatusLine parse(String string2) throws IOException {
        String string3;
        int n;
        Protocol protocol;
        block11: {
            int n2;
            if (string2.startsWith("HTTP/1.")) {
                if (string2.length() < 9 || string2.charAt(8) != ' ') {
                    throw new ProtocolException("Unexpected status line: " + string2);
                }
                n = string2.charAt(7) - 48;
                n2 = 9;
                if (n == 0) {
                    protocol = Protocol.HTTP_1_0;
                } else {
                    if (n != 1) {
                        throw new ProtocolException("Unexpected status line: " + string2);
                    }
                    protocol = Protocol.HTTP_1_1;
                }
            } else {
                if (!string2.startsWith("ICY ")) {
                    throw new ProtocolException("Unexpected status line: " + string2);
                }
                protocol = Protocol.HTTP_1_0;
                n2 = 4;
            }
            if (string2.length() < n2 + 3) {
                throw new ProtocolException("Unexpected status line: " + string2);
            }
            try {
                n = Integer.parseInt(string2.substring(n2, n2 + 3));
                string3 = "";
            }
            catch (NumberFormatException numberFormatException) {
                throw new ProtocolException("Unexpected status line: " + string2);
            }
            if (string2.length() <= n2 + 3) break block11;
            if (string2.charAt(n2 + 3) != ' ') {
                throw new ProtocolException("Unexpected status line: " + string2);
            }
            string3 = string2.substring(n2 + 4);
        }
        return new StatusLine(protocol, n, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
        stringBuilder.append(string2);
        stringBuilder.append(' ').append(this.code);
        if (this.message != null) {
            stringBuilder.append(' ').append(this.message);
        }
        return stringBuilder.toString();
    }
}

