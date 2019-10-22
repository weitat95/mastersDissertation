/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Base64
 */
package com.facebook.common.webp;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import com.facebook.common.webp.WebpBitmapFactory;
import java.io.UnsupportedEncodingException;

public class WebpSupportStatus {
    private static final byte[] WEBP_NAME_BYTES;
    private static final byte[] WEBP_RIFF_BYTES;
    private static final byte[] WEBP_VP8L_BYTES;
    private static final byte[] WEBP_VP8X_BYTES;
    private static final byte[] WEBP_VP8_BYTES;
    public static final boolean sIsExtendedWebpSupported;
    public static final boolean sIsSimpleWebpSupported;
    public static final boolean sIsWebpSupportRequired;
    public static WebpBitmapFactory sWebpBitmapFactory;
    public static boolean sWebpLibraryPresent;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        boolean bl = true;
        boolean bl2 = Build.VERSION.SDK_INT <= 17;
        sIsWebpSupportRequired = bl2;
        bl2 = Build.VERSION.SDK_INT >= 14 ? bl : false;
        sIsSimpleWebpSupported = bl2;
        sIsExtendedWebpSupported = WebpSupportStatus.isExtendedWebpSupported();
        sWebpBitmapFactory = null;
        sWebpLibraryPresent = false;
        try {
            sWebpBitmapFactory = (WebpBitmapFactory)Class.forName("com.facebook.webpsupport.WebpBitmapFactoryImpl").newInstance();
            sWebpLibraryPresent = true;
        }
        catch (Throwable throwable) {
            sWebpLibraryPresent = false;
        }
        WEBP_RIFF_BYTES = WebpSupportStatus.asciiBytes("RIFF");
        WEBP_NAME_BYTES = WebpSupportStatus.asciiBytes("WEBP");
        WEBP_VP8_BYTES = WebpSupportStatus.asciiBytes("VP8 ");
        WEBP_VP8L_BYTES = WebpSupportStatus.asciiBytes("VP8L");
        WEBP_VP8X_BYTES = WebpSupportStatus.asciiBytes("VP8X");
    }

    private static byte[] asciiBytes(String arrby) {
        try {
            arrby = arrby.getBytes("ASCII");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("ASCII not found!", unsupportedEncodingException);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isAnimatedWebpHeader(byte[] arrby, int n) {
        boolean bl = WebpSupportStatus.matchBytePattern(arrby, n + 12, WEBP_VP8X_BYTES);
        if ((arrby[n + 20] & 2) == 2) {
            n = 1;
            do {
                return bl && n != 0;
                break;
            } while (true);
        }
        n = 0;
        return bl && n != 0;
    }

    public static boolean isExtendedWebpHeader(byte[] arrby, int n, int n2) {
        return n2 >= 21 && WebpSupportStatus.matchBytePattern(arrby, n + 12, WEBP_VP8X_BYTES);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isExtendedWebpHeaderWithAlpha(byte[] arrby, int n) {
        boolean bl = WebpSupportStatus.matchBytePattern(arrby, n + 12, WEBP_VP8X_BYTES);
        if ((arrby[n + 20] & 0x10) == 16) {
            n = 1;
            do {
                return bl && n != 0;
                break;
            } while (true);
        }
        n = 0;
        return bl && n != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isExtendedWebpSupported() {
        block3: {
            block2: {
                if (Build.VERSION.SDK_INT < 17) break block2;
                if (Build.VERSION.SDK_INT != 17) break block3;
                byte[] arrby = Base64.decode((String)"UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==", (int)0);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray((byte[])arrby, (int)0, (int)arrby.length, (BitmapFactory.Options)options);
                if (options.outHeight == 1 && options.outWidth == 1) break block3;
            }
            return false;
        }
        return true;
    }

    public static boolean isLosslessWebpHeader(byte[] arrby, int n) {
        return WebpSupportStatus.matchBytePattern(arrby, n + 12, WEBP_VP8L_BYTES);
    }

    public static boolean isSimpleWebpHeader(byte[] arrby, int n) {
        return WebpSupportStatus.matchBytePattern(arrby, n + 12, WEBP_VP8_BYTES);
    }

    public static boolean isWebpHeader(byte[] arrby, int n, int n2) {
        return n2 >= 20 && WebpSupportStatus.matchBytePattern(arrby, n, WEBP_RIFF_BYTES) && WebpSupportStatus.matchBytePattern(arrby, n + 8, WEBP_NAME_BYTES);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean matchBytePattern(byte[] arrby, int n, byte[] arrby2) {
        if (arrby2 != null && arrby != null && arrby2.length + n <= arrby.length) {
            int n2 = 0;
            do {
                if (n2 >= arrby2.length) {
                    return true;
                }
                if (arrby[n2 + n] != arrby2[n2]) break;
                ++n2;
            } while (true);
        }
        return false;
    }
}

