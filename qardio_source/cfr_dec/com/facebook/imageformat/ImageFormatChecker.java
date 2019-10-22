/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imageformat;

import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Ints;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ImageFormatChecker {
    private static final byte[] BMP_HEADER;
    private static final ImageFormat.FormatChecker DEFAULT_FORMAT_CHECKER;
    private static final byte[] GIF_HEADER_87A;
    private static final byte[] GIF_HEADER_89A;
    private static final byte[] JPEG_HEADER;
    private static final int MAX_HEADER_LENGTH;
    private static final byte[] PNG_HEADER;

    static {
        DEFAULT_FORMAT_CHECKER = new ImageFormat.FormatChecker(){

            @Override
            public ImageFormat determineFormat(byte[] arrby, int n) {
                Preconditions.checkNotNull(arrby);
                if (WebpSupportStatus.isWebpHeader(arrby, 0, n)) {
                    return ImageFormatChecker.getWebpFormat(arrby, n);
                }
                if (ImageFormatChecker.isJpegHeader(arrby, n)) {
                    return DefaultImageFormats.JPEG;
                }
                if (ImageFormatChecker.isPngHeader(arrby, n)) {
                    return DefaultImageFormats.PNG;
                }
                if (ImageFormatChecker.isGifHeader(arrby, n)) {
                    return DefaultImageFormats.GIF;
                }
                if (ImageFormatChecker.isBmpHeader(arrby, n)) {
                    return DefaultImageFormats.BMP;
                }
                return ImageFormat.UNKNOWN;
            }
        };
        JPEG_HEADER = new byte[]{-1, -40, -1};
        PNG_HEADER = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
        GIF_HEADER_87A = ImageFormatChecker.asciiBytes("GIF87a");
        GIF_HEADER_89A = ImageFormatChecker.asciiBytes("GIF89a");
        BMP_HEADER = ImageFormatChecker.asciiBytes("BM");
        MAX_HEADER_LENGTH = Ints.max(21, 20, JPEG_HEADER.length, PNG_HEADER.length, 6, BMP_HEADER.length);
    }

    private static byte[] asciiBytes(String arrby) {
        Preconditions.checkNotNull(arrby);
        try {
            arrby = arrby.getBytes("ASCII");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("ASCII not found!", unsupportedEncodingException);
        }
    }

    public static ImageFormat getImageFormat(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        byte[] arrby = new byte[MAX_HEADER_LENGTH];
        int n = ImageFormatChecker.readHeaderFromStream(inputStream, arrby);
        return DEFAULT_FORMAT_CHECKER.determineFormat(arrby, n);
    }

    public static ImageFormat getImageFormat_WrapIOException(InputStream object) {
        try {
            object = ImageFormatChecker.getImageFormat((InputStream)object);
            return object;
        }
        catch (IOException iOException) {
            throw Throwables.propagate(iOException);
        }
    }

    private static ImageFormat getWebpFormat(byte[] arrby, int n) {
        Preconditions.checkArgument(WebpSupportStatus.isWebpHeader(arrby, 0, n));
        if (WebpSupportStatus.isSimpleWebpHeader(arrby, 0)) {
            return DefaultImageFormats.WEBP_SIMPLE;
        }
        if (WebpSupportStatus.isLosslessWebpHeader(arrby, 0)) {
            return DefaultImageFormats.WEBP_LOSSLESS;
        }
        if (WebpSupportStatus.isExtendedWebpHeader(arrby, 0, n)) {
            if (WebpSupportStatus.isAnimatedWebpHeader(arrby, 0)) {
                return DefaultImageFormats.WEBP_ANIMATED;
            }
            if (WebpSupportStatus.isExtendedWebpHeaderWithAlpha(arrby, 0)) {
                return DefaultImageFormats.WEBP_EXTENDED_WITH_ALPHA;
            }
            return DefaultImageFormats.WEBP_EXTENDED;
        }
        return ImageFormat.UNKNOWN;
    }

    private static boolean isBmpHeader(byte[] arrby, int n) {
        if (n < BMP_HEADER.length) {
            return false;
        }
        return ImageFormatChecker.matchBytePattern(arrby, 0, BMP_HEADER);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isGifHeader(byte[] arrby, int n) {
        return n >= 6 && (ImageFormatChecker.matchBytePattern(arrby, 0, GIF_HEADER_87A) || ImageFormatChecker.matchBytePattern(arrby, 0, GIF_HEADER_89A));
    }

    private static boolean isJpegHeader(byte[] arrby, int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if (n >= JPEG_HEADER.length) {
            bl2 = bl;
            if (ImageFormatChecker.matchBytePattern(arrby, 0, JPEG_HEADER)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private static boolean isPngHeader(byte[] arrby, int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if (n >= PNG_HEADER.length) {
            bl2 = bl;
            if (ImageFormatChecker.matchBytePattern(arrby, 0, PNG_HEADER)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean matchBytePattern(byte[] arrby, int n, byte[] arrby2) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkNotNull(arrby2);
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        if (arrby2.length + n <= arrby.length) {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int readHeaderFromStream(InputStream inputStream, byte[] arrby) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(arrby);
        boolean bl = arrby.length >= MAX_HEADER_LENGTH;
        Preconditions.checkArgument(bl);
        if (!inputStream.markSupported()) return ByteStreams.read(inputStream, arrby, 0, MAX_HEADER_LENGTH);
        try {
            inputStream.mark(MAX_HEADER_LENGTH);
            int n = ByteStreams.read(inputStream, arrby, 0, MAX_HEADER_LENGTH);
            return n;
        }
        finally {
            inputStream.reset();
        }
    }

}

