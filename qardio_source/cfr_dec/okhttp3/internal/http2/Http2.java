/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.IOException;
import okhttp3.internal.Util;
import okio.ByteString;

public final class Http2 {
    static final String[] BINARY;
    static final ByteString CONNECTION_PREFACE;
    static final String[] FLAGS;
    private static final String[] FRAME_NAMES;

    static {
        int n;
        int n2;
        int[] arrn;
        CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
        FRAME_NAMES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        FLAGS = new String[64];
        BINARY = new String[256];
        for (n2 = 0; n2 < BINARY.length; ++n2) {
            Http2.BINARY[n2] = Util.format("%8s", Integer.toBinaryString(n2)).replace(' ', '0');
        }
        Http2.FLAGS[0] = "";
        Http2.FLAGS[1] = "END_STREAM";
        int[] arrn2 = new int[]{1};
        Http2.FLAGS[8] = "PADDED";
        int n3 = arrn2.length;
        for (n2 = 0; n2 < n3; ++n2) {
            n = arrn2[n2];
            Http2.FLAGS[n | 8] = FLAGS[n] + "|PADDED";
        }
        Http2.FLAGS[4] = "END_HEADERS";
        Http2.FLAGS[32] = "PRIORITY";
        Http2.FLAGS[36] = "END_HEADERS|PRIORITY";
        int[] arrn3 = arrn = new int[3];
        arrn[0] = 4;
        arrn3[1] = 32;
        arrn3[2] = 36;
        n = arrn.length;
        for (n2 = 0; n2 < n; ++n2) {
            int n4 = arrn[n2];
            for (int n5 : arrn2) {
                Http2.FLAGS[n5 | n4] = FLAGS[n5] + '|' + FLAGS[n4];
                Http2.FLAGS[n5 | n4 | 8] = FLAGS[n5] + '|' + FLAGS[n4] + "|PADDED";
            }
        }
        for (n2 = 0; n2 < FLAGS.length; ++n2) {
            if (FLAGS[n2] != null) continue;
            Http2.FLAGS[n2] = BINARY[n2];
        }
    }

    private Http2() {
    }

    /*
     * Enabled aggressive block sorting
     */
    static String formatFlags(byte by, byte by2) {
        String string2;
        if (by2 == 0) {
            return "";
        }
        switch (by) {
            default: {
                string2 = by2 < FLAGS.length ? FLAGS[by2] : BINARY[by2];
            }
            case 4: 
            case 6: {
                if (by2 == 1) {
                    return "ACK";
                }
                return BINARY[by2];
            }
            case 2: 
            case 3: 
            case 7: 
            case 8: {
                return BINARY[by2];
            }
        }
        if (by == 5 && (by2 & 4) != 0) {
            return string2.replace("HEADERS", "PUSH_PROMISE");
        }
        if (by == 0 && (by2 & 0x20) != 0) {
            return string2.replace("PRIORITY", "COMPRESSED");
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    static String frameLog(boolean bl, int n, int n2, byte by, byte by2) {
        String string2 = by < FRAME_NAMES.length ? FRAME_NAMES[by] : Util.format("0x%02x", by);
        String string3 = Http2.formatFlags(by, by2);
        String string4 = bl ? "<<" : ">>";
        return Util.format("%s 0x%08x %5d %-13s %s", string4, n, n2, string2, string3);
    }

    static IllegalArgumentException illegalArgument(String string2, Object ... arrobject) {
        throw new IllegalArgumentException(Util.format(string2, arrobject));
    }

    static IOException ioException(String string2, Object ... arrobject) throws IOException {
        throw new IOException(Util.format(string2, arrobject));
    }
}

