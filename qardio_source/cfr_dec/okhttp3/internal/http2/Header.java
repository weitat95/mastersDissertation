/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import okhttp3.internal.Util;
import okio.ByteString;

public final class Header {
    public static final ByteString PSEUDO_PREFIX = ByteString.encodeUtf8(":");
    public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
    public static final ByteString TARGET_AUTHORITY;
    public static final ByteString TARGET_METHOD;
    public static final ByteString TARGET_PATH;
    public static final ByteString TARGET_SCHEME;
    final int hpackSize;
    public final ByteString name;
    public final ByteString value;

    static {
        TARGET_METHOD = ByteString.encodeUtf8(":method");
        TARGET_PATH = ByteString.encodeUtf8(":path");
        TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
        TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
    }

    public Header(String string2, String string3) {
        this(ByteString.encodeUtf8(string2), ByteString.encodeUtf8(string3));
    }

    public Header(ByteString byteString, String string2) {
        this(byteString, ByteString.encodeUtf8(string2));
    }

    public Header(ByteString byteString, ByteString byteString2) {
        this.name = byteString;
        this.value = byteString2;
        this.hpackSize = byteString.size() + 32 + byteString2.size();
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object instanceof Header) {
            object = (Header)object;
            bl2 = bl;
            if (this.name.equals(((Header)object).name)) {
                bl2 = bl;
                if (this.value.equals(((Header)object).value)) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public int hashCode() {
        return (this.name.hashCode() + 527) * 31 + this.value.hashCode();
    }

    public String toString() {
        return Util.format("%s: %s", this.name.utf8(), this.value.utf8());
    }
}

