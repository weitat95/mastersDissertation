/*
 * Decompiled with CFR 0.147.
 */
package okio;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import okio.Base64;
import okio.Buffer;
import okio.Util;

public class ByteString
implements Serializable,
Comparable<ByteString> {
    public static final ByteString EMPTY;
    static final char[] HEX_DIGITS;
    final byte[] data;
    transient int hashCode;
    transient String utf8;

    static {
        HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        EMPTY = ByteString.of(new byte[0]);
    }

    ByteString(byte[] arrby) {
        this.data = arrby;
    }

    static int codePointIndexToCharIndex(String string2, int n) {
        int n2;
        int n3 = 0;
        int n4 = string2.length();
        for (int i = 0; i < n4; i += Character.charCount(n2)) {
            if (n3 == n) {
                return i;
            }
            n2 = string2.codePointAt(i);
            if (Character.isISOControl(n2) && n2 != 10 && n2 != 13 || n2 == 65533) {
                return -1;
            }
            ++n3;
        }
        return string2.length();
    }

    public static ByteString decodeBase64(String arrby) {
        if (arrby == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        if ((arrby = Base64.decode((String)arrby)) != null) {
            return new ByteString(arrby);
        }
        return null;
    }

    public static ByteString decodeHex(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("hex == null");
        }
        if (string2.length() % 2 != 0) {
            throw new IllegalArgumentException("Unexpected hex string: " + string2);
        }
        byte[] arrby = new byte[string2.length() / 2];
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = (byte)((ByteString.decodeHexDigit(string2.charAt(i * 2)) << 4) + ByteString.decodeHexDigit(string2.charAt(i * 2 + 1)));
        }
        return ByteString.of(arrby);
    }

    private static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        throw new IllegalArgumentException("Unexpected hex digit: " + c);
    }

    private ByteString digest(String object) {
        try {
            object = ByteString.of(MessageDigest.getInstance((String)object).digest(this.data));
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    public static ByteString encodeUtf8(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("s == null");
        }
        ByteString byteString = new ByteString(string2.getBytes(Util.UTF_8));
        byteString.utf8 = string2;
        return byteString;
    }

    public static ByteString of(byte ... arrby) {
        if (arrby == null) {
            throw new IllegalArgumentException("data == null");
        }
        return new ByteString((byte[])arrby.clone());
    }

    public String base64() {
        return Base64.encode(this.data);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int compareTo(ByteString byteString) {
        int n = this.size();
        int n2 = byteString.size();
        int n3 = Math.min(n, n2);
        for (int i = 0; i < n3; ++i) {
            int n4;
            int n5 = this.getByte(i) & 0xFF;
            if (n5 == (n4 = byteString.getByte(i) & 0xFF)) {
                continue;
            }
            if (n5 < n4) return -1;
            return 1;
        }
        if (n == n2) {
            return 0;
        }
        if (n >= n2) return 1;
        return -1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByteString)) return false;
        if (((ByteString)object).size() != this.data.length) return false;
        if (!((ByteString)object).rangeEquals(0, this.data, 0, this.data.length)) return false;
        return true;
    }

    public byte getByte(int n) {
        return this.data[n];
    }

    public int hashCode() {
        int n = this.hashCode;
        if (n != 0) {
            return n;
        }
        this.hashCode = n = Arrays.hashCode(this.data);
        return n;
    }

    public String hex() {
        char[] arrc = new char[this.data.length * 2];
        byte[] arrby = this.data;
        int n = arrby.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            byte by = arrby[i];
            int n3 = n2 + 1;
            arrc[n2] = HEX_DIGITS[by >> 4 & 0xF];
            n2 = n3 + 1;
            arrc[n3] = HEX_DIGITS[by & 0xF];
        }
        return new String(arrc);
    }

    public ByteString md5() {
        return this.digest("MD5");
    }

    public boolean rangeEquals(int n, ByteString byteString, int n2, int n3) {
        return byteString.rangeEquals(n2, this.data, n, n3);
    }

    public boolean rangeEquals(int n, byte[] arrby, int n2, int n3) {
        return n >= 0 && n <= this.data.length - n3 && n2 >= 0 && n2 <= arrby.length - n3 && Util.arrayRangeEquals(this.data, n, arrby, n2, n3);
    }

    public ByteString sha1() {
        return this.digest("SHA-1");
    }

    public ByteString sha256() {
        return this.digest("SHA-256");
    }

    public int size() {
        return this.data.length;
    }

    public final boolean startsWith(ByteString byteString) {
        return this.rangeEquals(0, byteString, 0, byteString.size());
    }

    public ByteString substring(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        }
        if (n2 > this.data.length) {
            throw new IllegalArgumentException("endIndex > length(" + this.data.length + ")");
        }
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex");
        }
        if (n == 0 && n2 == this.data.length) {
            return this;
        }
        byte[] arrby = new byte[n3];
        System.arraycopy(this.data, n, arrby, 0, n3);
        return new ByteString(arrby);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public ByteString toAsciiLowercase() {
        void var3_6;
        block3: {
            byte by;
            int n = 0;
            do {
                ByteString byteString = this;
                if (n >= this.data.length) break block3;
                by = this.data[n];
                if (by >= 65 && by <= 90) break;
                ++n;
            } while (true);
            byte[] arrby = (byte[])this.data.clone();
            arrby[n] = (byte)(by + 32);
            ++n;
            while (n < arrby.length) {
                by = arrby[n];
                if (by >= 65 && by <= 90) {
                    arrby[n] = (byte)(by + 32);
                }
                ++n;
            }
            ByteString byteString = new ByteString(arrby);
        }
        return var3_6;
    }

    public byte[] toByteArray() {
        return (byte[])this.data.clone();
    }

    public String toString() {
        if (this.data.length == 0) {
            return "[size=0]";
        }
        String string2 = this.utf8();
        int n = ByteString.codePointIndexToCharIndex(string2, 64);
        if (n == -1) {
            if (this.data.length <= 64) {
                return "[hex=" + this.hex() + "]";
            }
            return "[size=" + this.data.length + " hex=" + this.substring(0, 64).hex() + "\u2026]";
        }
        String string3 = string2.substring(0, n).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
        if (n < string2.length()) {
            return "[size=" + this.data.length + " text=" + string3 + "\u2026]";
        }
        return "[text=" + string3 + "]";
    }

    public String utf8() {
        String string2 = this.utf8;
        if (string2 != null) {
            return string2;
        }
        this.utf8 = string2 = new String(this.data, Util.UTF_8);
        return string2;
    }

    void write(Buffer buffer) {
        buffer.write(this.data, 0, this.data.length);
    }
}

