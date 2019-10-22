/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.fitness.data.zzb;
import com.google.android.gms.fitness.data.zzj;
import com.google.android.gms.fitness.data.zzk;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public class DataSource
extends zzbfm {
    public static final Parcelable.Creator<DataSource> CREATOR;
    private static final int[] zzhap;
    private final String name;
    private final int type;
    private final int versionCode;
    private final DataType zzgzg;
    private final Device zzhaq;
    private final zzb zzhar;
    private final String zzhas;
    private final int[] zzhat;
    private final String zzhau;

    static {
        zzhap = new int[0];
        CREATOR = new zzk();
    }

    /*
     * Enabled aggressive block sorting
     */
    DataSource(int n, DataType dataType, String string2, int n2, Device device, zzb zzb2, String string3, int[] arrn) {
        this.versionCode = n;
        this.zzgzg = dataType;
        this.type = n2;
        this.name = string2;
        this.zzhaq = device;
        this.zzhar = zzb2;
        this.zzhas = string3;
        this.zzhau = this.zzaqm();
        if (arrn == null) {
            arrn = zzhap;
        }
        this.zzhat = arrn;
    }

    private DataSource(Builder builder) {
        this.versionCode = 3;
        this.zzgzg = builder.zzgzg;
        this.type = builder.type;
        this.name = builder.name;
        this.zzhaq = builder.zzhaq;
        this.zzhar = builder.zzhar;
        this.zzhas = builder.zzhas;
        this.zzhau = this.zzaqm();
        this.zzhat = builder.zzhat;
    }

    /* synthetic */ DataSource(Builder builder, zzj zzj2) {
        this(builder);
    }

    private final String getTypeString() {
        switch (this.type) {
            default: {
                return "derived";
            }
            case 0: {
                return "raw";
            }
            case 1: {
                return "derived";
            }
            case 2: {
                return "cleaned";
            }
            case 3: 
        }
        return "converted";
    }

    private final String zzaqm() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getTypeString());
        stringBuilder.append(":").append(this.zzgzg.getName());
        if (this.zzhar != null) {
            stringBuilder.append(":").append(this.zzhar.getPackageName());
        }
        if (this.zzhaq != null) {
            stringBuilder.append(":").append(this.zzhaq.getStreamIdentifier());
        }
        if (this.zzhas != null) {
            stringBuilder.append(":").append(this.zzhas);
        }
        return stringBuilder.toString();
    }

    public static String zzdd(int n) {
        switch (n) {
            default: {
                return "unknown";
            }
            case 1: {
                return "blood_pressure_esh2002";
            }
            case 2: {
                return "blood_pressure_esh2010";
            }
            case 3: {
                return "blood_pressure_aami";
            }
            case 4: {
                return "blood_pressure_bhs_a_a";
            }
            case 5: {
                return "blood_pressure_bhs_a_b";
            }
            case 6: {
                return "blood_pressure_bhs_b_a";
            }
            case 7: {
                return "blood_pressure_bhs_b_b";
            }
            case 8: {
                return "blood_glucose_iso151972003";
            }
            case 9: 
        }
        return "blood_glucose_iso151972013";
    }

    public boolean equals(Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof DataSource)) break block3;
                object = (DataSource)object;
                if (!this.zzhau.equals(((DataSource)object).zzhau)) break block3;
            }
            return true;
        }
        return false;
    }

    public int[] getDataQualityStandards() {
        return this.zzhat;
    }

    public DataType getDataType() {
        return this.zzgzg;
    }

    public Device getDevice() {
        return this.zzhaq;
    }

    public String getName() {
        return this.name;
    }

    public String getStreamIdentifier() {
        return this.zzhau;
    }

    public String getStreamName() {
        return this.zzhas;
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return this.zzhau.hashCode();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toDebugString() {
        String string2;
        String string3;
        String string4;
        String string5;
        switch (this.type) {
            default: {
                string3 = "?";
                break;
            }
            case 0: {
                string3 = "r";
                break;
            }
            case 1: {
                string3 = "d";
                break;
            }
            case 2: {
                string3 = "c";
                break;
            }
            case 3: {
                string3 = "v";
            }
        }
        String string6 = this.zzgzg.zzaqp();
        string5 = this.zzhar == null ? "" : (this.zzhar.equals(zzb.zzgzu) ? ":gms" : ((string5 = String.valueOf(this.zzhar.getPackageName())).length() != 0 ? ":".concat(string5) : new String(":")));
        if (this.zzhaq != null) {
            string2 = this.zzhaq.getModel();
            string4 = this.zzhaq.getUid();
            string4 = new StringBuilder(String.valueOf(string2).length() + 2 + String.valueOf(string4).length()).append(":").append(string2).append(":").append(string4).toString();
        } else {
            string4 = "";
        }
        if (this.zzhas != null) {
            string2 = String.valueOf(this.zzhas);
            if (string2.length() != 0) {
                string2 = ":".concat(string2);
                return new StringBuilder(String.valueOf(string3).length() + 1 + String.valueOf(string6).length() + String.valueOf(string5).length() + String.valueOf(string4).length() + String.valueOf(string2).length()).append(string3).append(":").append(string6).append(string5).append(string4).append(string2).toString();
            }
            string2 = new String(":");
            return new StringBuilder(String.valueOf(string3).length() + 1 + String.valueOf(string6).length() + String.valueOf(string5).length() + String.valueOf(string4).length() + String.valueOf(string2).length()).append(string3).append(":").append(string6).append(string5).append(string4).append(string2).toString();
        }
        string2 = "";
        return new StringBuilder(String.valueOf(string3).length() + 1 + String.valueOf(string6).length() + String.valueOf(string5).length() + String.valueOf(string4).length() + String.valueOf(string2).length()).append(string3).append(":").append(string6).append(string5).append(string4).append(string2).toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DataSource{");
        stringBuilder.append(this.getTypeString());
        if (this.name != null) {
            stringBuilder.append(":").append(this.name);
        }
        if (this.zzhar != null) {
            stringBuilder.append(":").append(this.zzhar);
        }
        if (this.zzhaq != null) {
            stringBuilder.append(":").append(this.zzhaq);
        }
        if (this.zzhas != null) {
            stringBuilder.append(":").append(this.zzhas);
        }
        stringBuilder.append(":").append(this.zzgzg);
        return stringBuilder.append("}").toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.getDataType(), n, false);
        zzbfp.zza(parcel, 2, this.getName(), false);
        zzbfp.zzc(parcel, 3, this.getType());
        zzbfp.zza(parcel, 4, this.getDevice(), n, false);
        zzbfp.zza(parcel, 5, this.zzhar, n, false);
        zzbfp.zza(parcel, 6, this.getStreamName(), false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, this.getDataQualityStandards(), false);
        zzbfp.zzai(parcel, n2);
    }

    public final zzb zzaql() {
        return this.zzhar;
    }

    public static final class Builder {
        private String name;
        private int type = -1;
        private DataType zzgzg;
        private Device zzhaq;
        private zzb zzhar;
        private String zzhas = "";
        private int[] zzhat;

        /*
         * Enabled aggressive block sorting
         */
        public final DataSource build() {
            boolean bl = true;
            boolean bl2 = this.zzgzg != null;
            zzbq.zza(bl2, "Must set data type");
            bl2 = this.type >= 0 ? bl : false;
            zzbq.zza(bl2, "Must set data source type");
            return new DataSource(this, null);
        }

        public final Builder setAppPackageName(Context context) {
            return this.setAppPackageName(context.getPackageName());
        }

        public final Builder setAppPackageName(String string2) {
            this.zzhar = zzb.zzhd(string2);
            return this;
        }

        public final Builder setDataType(DataType dataType) {
            this.zzgzg = dataType;
            return this;
        }

        public final Builder setName(String string2) {
            this.name = string2;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final Builder setStreamName(String string2) {
            boolean bl = string2 != null;
            zzbq.checkArgument(bl, "Must specify a valid stream name");
            this.zzhas = string2;
            return this;
        }

        public final Builder setType(int n) {
            this.type = n;
            return this;
        }
    }

}

