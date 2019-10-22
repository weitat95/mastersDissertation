/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.zzag;
import com.google.android.gms.fitness.data.zzah;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public class Subscription
extends zzbfm {
    public static final Parcelable.Creator<Subscription> CREATOR = new zzah();
    private final int zzeck;
    private final DataSource zzhbr;
    private final DataType zzhbs;
    private final long zzhdy;
    private final int zzhdz;

    Subscription(int n, DataSource dataSource, DataType dataType, long l, int n2) {
        this.zzeck = n;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
        this.zzhdy = l;
        this.zzhdz = n2;
    }

    private Subscription(zza zza2) {
        this.zzeck = 1;
        this.zzhbs = zza2.zzhbs;
        this.zzhbr = zza2.zzhbr;
        this.zzhdy = zza2.zzhdy;
        this.zzhdz = zza2.zzhdz;
    }

    /* synthetic */ Subscription(zza zza2, zzag zzag2) {
        this(zza2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof Subscription)) return bl2;
        object = (Subscription)object;
        boolean bl3 = zzbg.equal(this.zzhbr, ((Subscription)object).zzhbr) && zzbg.equal(this.zzhbs, ((Subscription)object).zzhbs) && this.zzhdy == ((Subscription)object).zzhdy && this.zzhdz == ((Subscription)object).zzhdz;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public DataSource getDataSource() {
        return this.zzhbr;
    }

    public DataType getDataType() {
        return this.zzhbs;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, this.zzhbr, this.zzhdy, this.zzhdz});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("dataSource", this.zzhbr).zzg("dataType", this.zzhbs).zzg("samplingIntervalMicros", this.zzhdy).zzg("accuracyMode", this.zzhdz).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getDataSource(), n, false);
        zzbfp.zza(parcel, 2, this.getDataType(), n, false);
        zzbfp.zza(parcel, 3, this.zzhdy);
        zzbfp.zzc(parcel, 4, this.zzhdz);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }

    public final DataType zzaqq() {
        if (this.zzhbs == null) {
            return this.zzhbr.getDataType();
        }
        return this.zzhbs;
    }

    public static final class zza {
        private DataSource zzhbr;
        private DataType zzhbs;
        private long zzhdy = -1L;
        private int zzhdz = 2;

        public final zza zza(DataType dataType) {
            this.zzhbs = dataType;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final Subscription zzaqr() {
            boolean bl;
            block3: {
                block2: {
                    boolean bl2 = false;
                    bl = this.zzhbr != null || this.zzhbs != null;
                    zzbq.zza(bl, "Must call setDataSource() or setDataType()");
                    if (this.zzhbs == null || this.zzhbr == null) break block2;
                    bl = bl2;
                    if (!this.zzhbs.equals(this.zzhbr.getDataType())) break block3;
                }
                bl = true;
            }
            zzbq.zza(bl, "Specified data type is incompatible with specified data source");
            return new Subscription(this, null);
        }
    }

}

