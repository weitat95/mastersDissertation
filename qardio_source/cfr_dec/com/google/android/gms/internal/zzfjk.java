/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjl;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class zzfjk {
    private final ByteBuffer buffer;

    private zzfjk(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzfjk(byte[] arrby, int n, int n2) {
        this(ByteBuffer.wrap(arrby, n, n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int zza(CharSequence charSequence, byte[] arrby, int n, int n2) {
        int n3 = charSequence.length();
        int n4 = 0;
        int n5 = n + n2;
        n2 = n4;
        while (n2 < n3 && n2 + n < n5 && (n4 = (int)charSequence.charAt(n2)) < 128) {
            arrby[n + n2] = (byte)n4;
            ++n2;
        }
        if (n2 == n3) {
            return n + n3;
        }
        n += n2;
        do {
            block9: {
                char c;
                int n6;
                char c2;
                block13: {
                    block12: {
                        block11: {
                            block10: {
                                block8: {
                                    if (n2 >= n3) {
                                        return n;
                                    }
                                    c2 = charSequence.charAt(n2);
                                    if (c2 >= '\u0080' || n >= n5) break block8;
                                    n4 = n + 1;
                                    arrby[n] = (byte)c2;
                                    n = n4;
                                    break block9;
                                }
                                if (c2 >= '\u0800' || n > n5 - 2) break block10;
                                n4 = n + 1;
                                arrby[n] = (byte)(c2 >>> 6 | 0x3C0);
                                n = n4 + 1;
                                arrby[n4] = (byte)(c2 & 0x3F | 0x80);
                                break block9;
                            }
                            if (c2 >= '\ud800' && '\udfff' >= c2 || n > n5 - 3) break block11;
                            n4 = n + 1;
                            arrby[n] = (byte)(c2 >>> 12 | 0x1E0);
                            n6 = n4 + 1;
                            arrby[n4] = (byte)(c2 >>> 6 & 0x3F | 0x80);
                            n = n6 + 1;
                            arrby[n6] = (byte)(c2 & 0x3F | 0x80);
                            break block9;
                        }
                        if (n > n5 - 4) {
                            throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(c2).append(" at index ").append(n).toString());
                        }
                        n4 = n2;
                        if (n2 + 1 == charSequence.length()) break block12;
                        if (Character.isSurrogatePair(c2, c = charSequence.charAt(++n2))) break block13;
                        n4 = n2;
                    }
                    throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(n4 - 1).toString());
                }
                n4 = Character.toCodePoint(c2, c);
                n6 = n + 1;
                arrby[n] = (byte)(n4 >>> 18 | 0xF0);
                n = n6 + 1;
                arrby[n6] = (byte)(n4 >>> 12 & 0x3F | 0x80);
                n6 = n + 1;
                arrby[n] = (byte)(n4 >>> 6 & 0x3F | 0x80);
                n = n6 + 1;
                arrby[n6] = (byte)(n4 & 0x3F | 0x80);
            }
            ++n2;
        } while (true);
    }

    private static void zza(CharSequence charSequence, ByteBuffer object) {
        if (((Buffer)object).isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (((ByteBuffer)object).hasArray()) {
            try {
                ((Buffer)object).position(zzfjk.zza(charSequence, ((ByteBuffer)object).array(), ((ByteBuffer)object).arrayOffset() + ((Buffer)object).position(), ((Buffer)object).remaining()) - ((ByteBuffer)object).arrayOffset());
                return;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                object = new BufferOverflowException();
                ((Throwable)object).initCause(arrayIndexOutOfBoundsException);
                throw object;
            }
        }
        zzfjk.zzb(charSequence, (ByteBuffer)object);
    }

    public static int zzad(int n, int n2) {
        return zzfjk.zzlg(n) + zzfjk.zzlh(n2);
    }

    public static int zzb(int n, zzfjs zzfjs2) {
        n = zzfjk.zzlg(n);
        int n2 = zzfjs2.zzho();
        return n + (n2 + zzfjk.zzlp(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int n = charSequence.length();
        int n2 = 0;
        do {
            block6: {
                char c;
                int n3;
                char c2;
                block10: {
                    block9: {
                        block8: {
                            block7: {
                                block5: {
                                    if (n2 >= n) {
                                        return;
                                    }
                                    c2 = charSequence.charAt(n2);
                                    if (c2 >= '\u0080') break block5;
                                    byteBuffer.put((byte)c2);
                                    break block6;
                                }
                                if (c2 >= '\u0800') break block7;
                                byteBuffer.put((byte)(c2 >>> 6 | 0x3C0));
                                byteBuffer.put((byte)(c2 & 0x3F | 0x80));
                                break block6;
                            }
                            if (c2 >= '\ud800' && '\udfff' >= c2) break block8;
                            byteBuffer.put((byte)(c2 >>> 12 | 0x1E0));
                            byteBuffer.put((byte)(c2 >>> 6 & 0x3F | 0x80));
                            byteBuffer.put((byte)(c2 & 0x3F | 0x80));
                            break block6;
                        }
                        n3 = n2;
                        if (n2 + 1 == charSequence.length()) break block9;
                        if (Character.isSurrogatePair(c2, c = charSequence.charAt(++n2))) break block10;
                        n3 = n2;
                    }
                    throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(n3 - 1).toString());
                }
                n3 = Character.toCodePoint(c2, c);
                byteBuffer.put((byte)(n3 >>> 18 | 0xF0));
                byteBuffer.put((byte)(n3 >>> 12 & 0x3F | 0x80));
                byteBuffer.put((byte)(n3 >>> 6 & 0x3F | 0x80));
                byteBuffer.put((byte)(n3 & 0x3F | 0x80));
            }
            ++n2;
        } while (true);
    }

    public static zzfjk zzbf(byte[] arrby) {
        return zzfjk.zzo(arrby, 0, arrby.length);
    }

    public static int zzbg(byte[] arrby) {
        return zzfjk.zzlp(arrby.length) + arrby.length;
    }

    public static int zzc(int n, long l) {
        return zzfjk.zzlg(n) + zzfjk.zzdi(l);
    }

    public static int zzd(int n, byte[] arrby) {
        return zzfjk.zzlg(n) + zzfjk.zzbg(arrby);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int zzd(CharSequence charSequence) {
        int n;
        int n2 = 0;
        int n3 = charSequence.length();
        for (n = 0; n < n3 && charSequence.charAt(n) < '\u0080'; ++n) {
        }
        int n4 = n3;
        while (n < n3) {
            int n5 = charSequence.charAt(n);
            if (n5 < 2048) {
                n4 += 127 - n5 >>> 31;
                ++n;
                continue;
            }
            int n6 = charSequence.length();
            while (n < n6) {
                char c = charSequence.charAt(n);
                if (c < '\u0800') {
                    n2 += 127 - c >>> 31;
                    n5 = n;
                } else {
                    int n7 = n2 + 2;
                    n5 = n;
                    n2 = n7;
                    if ('\ud800' <= c) {
                        n5 = n;
                        n2 = n7;
                        if (c <= '\udfff') {
                            if (Character.codePointAt(charSequence, n) < 65536) {
                                throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(n).toString());
                            }
                            n5 = n + 1;
                            n2 = n7;
                        }
                    }
                }
                n = n5 + 1;
            }
            n4 += n2;
            break;
        }
        if (n4 < n3) {
            long l = n4;
            throw new IllegalArgumentException(new StringBuilder(54).append("UTF-8 length does not fit in int: ").append(l + 0x100000000L).toString());
        }
        return n4;
    }

    private final void zzdh(long l) throws IOException {
        do {
            if ((0xFFFFFFFFFFFFFF80L & l) == 0L) {
                this.zzmh((int)l);
                return;
            }
            this.zzmh((int)l & 0x7F | 0x80);
            l >>>= 7;
        } while (true);
    }

    public static int zzdi(long l) {
        if ((0xFFFFFFFFFFFFFF80L & l) == 0L) {
            return 1;
        }
        if ((0xFFFFFFFFFFFFC000L & l) == 0L) {
            return 2;
        }
        if ((0xFFFFFFFFFFE00000L & l) == 0L) {
            return 3;
        }
        if ((0xFFFFFFFFF0000000L & l) == 0L) {
            return 4;
        }
        if ((0xFFFFFFF800000000L & l) == 0L) {
            return 5;
        }
        if ((0xFFFFFC0000000000L & l) == 0L) {
            return 6;
        }
        if ((0xFFFE000000000000L & l) == 0L) {
            return 7;
        }
        if ((0xFF00000000000000L & l) == 0L) {
            return 8;
        }
        if ((Long.MIN_VALUE & l) == 0L) {
            return 9;
        }
        return 10;
    }

    private final void zzdj(long l) throws IOException {
        if (this.buffer.remaining() < 8) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putLong(l);
    }

    public static int zzlg(int n) {
        return zzfjk.zzlp(n << 3);
    }

    public static int zzlh(int n) {
        if (n >= 0) {
            return zzfjk.zzlp(n);
        }
        return 10;
    }

    public static int zzlo(int n) {
        return n << 1 ^ n >> 31;
    }

    public static int zzlp(int n) {
        if ((n & 0xFFFFFF80) == 0) {
            return 1;
        }
        if ((n & 0xFFFFC000) == 0) {
            return 2;
        }
        if ((0xFFE00000 & n) == 0) {
            return 3;
        }
        if ((0xF0000000 & n) == 0) {
            return 4;
        }
        return 5;
    }

    private final void zzmh(int n) throws IOException {
        byte by = (byte)n;
        if (!this.buffer.hasRemaining()) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(by);
    }

    public static int zzo(int n, String string2) {
        return zzfjk.zzlg(n) + zzfjk.zztt(string2);
    }

    public static zzfjk zzo(byte[] arrby, int n, int n2) {
        return new zzfjk(arrby, 0, n2);
    }

    public static int zztt(String string2) {
        int n = zzfjk.zzd(string2);
        return n + zzfjk.zzlp(n);
    }

    public final void zza(int n, double d) throws IOException {
        this.zzz(n, 1);
        this.zzdj(Double.doubleToLongBits(d));
    }

    public final void zza(int n, long l) throws IOException {
        this.zzz(n, 0);
        this.zzdh(l);
    }

    public final void zza(int n, zzfjs zzfjs2) throws IOException {
        this.zzz(n, 2);
        this.zzb(zzfjs2);
    }

    public final void zzaa(int n, int n2) throws IOException {
        this.zzz(n, 0);
        if (n2 >= 0) {
            this.zzmi(n2);
            return;
        }
        this.zzdh(n2);
    }

    public final void zzb(zzfjs zzfjs2) throws IOException {
        this.zzmi(zzfjs2.zzdam());
        zzfjs2.zza(this);
    }

    public final void zzbh(byte[] arrby) throws IOException {
        int n = arrby.length;
        if (this.buffer.remaining() >= n) {
            this.buffer.put(arrby, 0, n);
            return;
        }
        throw new zzfjl(this.buffer.position(), this.buffer.limit());
    }

    public final void zzc(int n, float f) throws IOException {
        this.zzz(n, 5);
        n = Float.floatToIntBits(f);
        if (this.buffer.remaining() < 4) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putInt(n);
    }

    public final void zzc(int n, byte[] arrby) throws IOException {
        this.zzz(n, 2);
        this.zzmi(arrby.length);
        this.zzbh(arrby);
    }

    public final void zzcwt() {
        if (this.buffer.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", this.buffer.remaining()));
        }
    }

    public final void zzf(int n, long l) throws IOException {
        this.zzz(n, 0);
        this.zzdh(l);
    }

    public final void zzl(int n, boolean bl) throws IOException {
        int n2 = 0;
        this.zzz(n, 0);
        n = n2;
        if (bl) {
            n = 1;
        }
        byte by = (byte)n;
        if (!this.buffer.hasRemaining()) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(by);
    }

    public final void zzmi(int n) throws IOException {
        do {
            if ((n & 0xFFFFFF80) == 0) {
                this.zzmh(n);
                return;
            }
            this.zzmh(n & 0x7F | 0x80);
            n >>>= 7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzn(int n, String string2) throws IOException {
        this.zzz(n, 2);
        try {
            n = zzfjk.zzlp(string2.length());
            if (n == zzfjk.zzlp(string2.length() * 3)) {
                int n2 = this.buffer.position();
                if (this.buffer.remaining() < n) {
                    throw new zzfjl(n + n2, this.buffer.limit());
                }
                this.buffer.position(n2 + n);
                zzfjk.zza(string2, this.buffer);
                int n3 = this.buffer.position();
                this.buffer.position(n2);
                this.zzmi(n3 - n2 - n);
                this.buffer.position(n3);
                return;
            }
        }
        catch (BufferOverflowException bufferOverflowException) {
            zzfjl zzfjl2 = new zzfjl(this.buffer.position(), this.buffer.limit());
            zzfjl2.initCause(bufferOverflowException);
            throw zzfjl2;
        }
        this.zzmi(zzfjk.zzd(string2));
        zzfjk.zza(string2, this.buffer);
    }

    public final void zzz(int n, int n2) throws IOException {
        this.zzmi(n << 3 | n2);
    }
}

