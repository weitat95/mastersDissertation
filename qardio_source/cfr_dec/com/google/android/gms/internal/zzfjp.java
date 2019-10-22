/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjn;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfju;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class zzfjp
implements Cloneable {
    private Object value;
    private zzfjn<?, ?> zzpni;
    private List<zzfju> zzpnj = new ArrayList<zzfju>();

    zzfjp() {
    }

    private final byte[] toByteArray() throws IOException {
        byte[] arrby = new byte[this.zzq()];
        this.zza(zzfjk.zzbf(arrby));
        return arrby;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private zzfjp zzdah() {
        zzfjp zzfjp2 = new zzfjp();
        try {
            zzfjp2.zzpni = this.zzpni;
            if (this.zzpnj == null) {
                zzfjp2.zzpnj = null;
            } else {
                zzfjp2.zzpnj.addAll(this.zzpnj);
            }
            if (this.value == null) return zzfjp2;
            if (this.value instanceof zzfjs) {
                zzfjp2.value = (zzfjs)((zzfjs)this.value).clone();
                return zzfjp2;
            }
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
        {
            zzfjs[] arrzzfjs;
            if (this.value instanceof byte[]) {
                zzfjp2.value = ((byte[])this.value).clone();
                return zzfjp2;
            }
            if (this.value instanceof byte[][]) {
                byte[][] arrarrby;
                byte[][] arrby = (byte[][])this.value;
                zzfjp2.value = arrarrby = new byte[arrby.length][];
                for (int i = 0; i < arrby.length; ++i) {
                    arrarrby[i] = (byte[])arrby[i].clone();
                }
                return zzfjp2;
            }
            if (this.value instanceof boolean[]) {
                zzfjp2.value = ((boolean[])this.value).clone();
                return zzfjp2;
            }
            if (this.value instanceof int[]) {
                zzfjp2.value = ((int[])this.value).clone();
                return zzfjp2;
            }
            if (this.value instanceof long[]) {
                zzfjp2.value = ((long[])this.value).clone();
                return zzfjp2;
            }
            if (this.value instanceof float[]) {
                zzfjp2.value = ((float[])this.value).clone();
                return zzfjp2;
            }
            if (this.value instanceof double[]) {
                zzfjp2.value = ((double[])this.value).clone();
                return zzfjp2;
            }
            if (!(this.value instanceof zzfjs[])) return zzfjp2;
            zzfjs[] arrzzfjs2 = (zzfjs[])this.value;
            zzfjp2.value = arrzzfjs = new zzfjs[arrzzfjs2.length];
            for (int i = 0; i < arrzzfjs2.length; ++i) {
                arrzzfjs[i] = (zzfjs)arrzzfjs2[i].clone();
            }
            return zzfjp2;
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzdah();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals(Object object) {
        boolean bl = false;
        if (object == this) {
            return true;
        }
        boolean bl2 = bl;
        if (!(object instanceof zzfjp)) return bl2;
        object = (zzfjp)object;
        if (this.value != null && ((zzfjp)object).value != null) {
            bl2 = bl;
            if (this.zzpni != ((zzfjp)object).zzpni) return bl2;
            if (!this.zzpni.zznfk.isArray()) {
                return this.value.equals(((zzfjp)object).value);
            }
            if (this.value instanceof byte[]) {
                return Arrays.equals((byte[])this.value, (byte[])((zzfjp)object).value);
            }
            if (this.value instanceof int[]) {
                return Arrays.equals((int[])this.value, (int[])((zzfjp)object).value);
            }
            if (this.value instanceof long[]) {
                return Arrays.equals((long[])this.value, (long[])((zzfjp)object).value);
            }
            if (this.value instanceof float[]) {
                return Arrays.equals((float[])this.value, (float[])((zzfjp)object).value);
            }
            if (this.value instanceof double[]) {
                return Arrays.equals((double[])this.value, (double[])((zzfjp)object).value);
            }
            if (!(this.value instanceof boolean[])) return Arrays.deepEquals((Object[])this.value, (Object[])((zzfjp)object).value);
            return Arrays.equals((boolean[])this.value, (boolean[])((zzfjp)object).value);
        }
        if (this.zzpnj != null && ((zzfjp)object).zzpnj != null) {
            return this.zzpnj.equals(((zzfjp)object).zzpnj);
        }
        try {
            return Arrays.equals(this.toByteArray(), super.toByteArray());
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    public final int hashCode() {
        try {
            int n = Arrays.hashCode(this.toByteArray());
            return n + 527;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zza(zzfjk zzfjk2) throws IOException {
        if (this.value != null) {
            zzfjn<?, ?> zzfjn2 = this.zzpni;
            Object object = this.value;
            if (zzfjn2.zzpnd) {
                int n = Array.getLength(object);
                for (int i = 0; i < n; ++i) {
                    Object object2 = Array.get(object, i);
                    if (object2 == null) continue;
                    zzfjn2.zza(object2, zzfjk2);
                }
                return;
            } else {
                zzfjn2.zza(object, zzfjk2);
            }
            return;
        } else {
            for (zzfju zzfju2 : this.zzpnj) {
                zzfjk2.zzmi(zzfju2.tag);
                zzfjk2.zzbh(zzfju2.zzjng);
            }
        }
    }

    final void zza(zzfju zzfju2) {
        this.zzpnj.add(zzfju2);
    }

    final int zzq() {
        int n = 0;
        if (this.value != null) {
            int n2;
            zzfjn<?, ?> zzfjn2 = this.zzpni;
            Object object = this.value;
            if (zzfjn2.zzpnd) {
                int n3 = Array.getLength(object);
                int n4 = 0;
                do {
                    n2 = n;
                    if (n4 < n3) {
                        n2 = n;
                        if (Array.get(object, n4) != null) {
                            n2 = n + zzfjn2.zzcs(Array.get(object, n4));
                        }
                        ++n4;
                        n = n2;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                n2 = zzfjn2.zzcs(object);
            }
            return n2;
        }
        Iterator<zzfju> iterator = this.zzpnj.iterator();
        n = 0;
        while (iterator.hasNext()) {
            zzfju zzfju2 = iterator.next();
            int n5 = zzfjk.zzlp(zzfju2.tag);
            n = zzfju2.zzjng.length + (n5 + 0) + n;
        }
        return n;
    }
}

