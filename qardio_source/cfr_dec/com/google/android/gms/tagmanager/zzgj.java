/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

final class zzgj
extends Number
implements Comparable<zzgj> {
    private double zzkki;
    private long zzkkj;
    private boolean zzkkk;

    private zzgj(long l) {
        this.zzkkj = l;
        this.zzkkk = true;
    }

    public static zzgj zzbi(long l) {
        return new zzgj(l);
    }

    @Override
    public final byte byteValue() {
        return (byte)this.longValue();
    }

    @Override
    public final /* synthetic */ int compareTo(Object object) {
        return this.zza((zzgj)object);
    }

    @Override
    public final double doubleValue() {
        if (this.zzkkk) {
            return this.zzkkj;
        }
        return this.zzkki;
    }

    public final boolean equals(Object object) {
        return object instanceof zzgj && this.zza((zzgj)object) == 0;
    }

    @Override
    public final float floatValue() {
        return (float)this.doubleValue();
    }

    public final int hashCode() {
        return new Long(this.longValue()).hashCode();
    }

    @Override
    public final int intValue() {
        return (int)this.longValue();
    }

    @Override
    public final long longValue() {
        if (this.zzkkk) {
            return this.zzkkj;
        }
        return (long)this.zzkki;
    }

    @Override
    public final short shortValue() {
        return (short)this.longValue();
    }

    public final String toString() {
        if (this.zzkkk) {
            return Long.toString(this.zzkkj);
        }
        return Double.toString(this.zzkki);
    }

    public final int zza(zzgj zzgj2) {
        if (this.zzkkk && zzgj2.zzkkk) {
            return new Long(this.zzkkj).compareTo(zzgj2.zzkkj);
        }
        return Double.compare(this.doubleValue(), zzgj2.doubleValue());
    }

    public final boolean zzbgk() {
        return !this.zzkkk;
    }

    public final boolean zzbgl() {
        return this.zzkkk;
    }
}

