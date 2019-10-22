/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.nio.charset.Charset;

final class Util {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static boolean arrayRangeEquals(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            if (arrby[i + n] == arrby2[i + n2]) continue;
            return false;
        }
        return true;
    }

    public static void checkOffsetAndCount(long l, long l2, long l3) {
        if ((l2 | l3) < 0L || l2 > l || l - l2 < l3) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", l, l2, l3));
        }
    }

    public static int reverseBytesInt(int n) {
        return (0xFF000000 & n) >>> 24 | (0xFF0000 & n) >>> 8 | (0xFF00 & n) << 8 | (n & 0xFF) << 24;
    }

    public static short reverseBytesShort(short s) {
        s = (short)(s & 0xFFFF);
        return (short)((0xFF00 & s) >>> 8 | (s & 0xFF) << 8);
    }

    public static void sneakyRethrow(Throwable throwable) {
        Util.sneakyThrow2(throwable);
    }

    private static <T extends Throwable> void sneakyThrow2(Throwable throwable) throws Throwable {
        throw throwable;
    }
}

