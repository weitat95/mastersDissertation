/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.healthdata;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.internal.healthdata.IpcUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class HealthData
implements Parcelable {
    public static final Parcelable.Creator<HealthData> CREATOR = new Parcelable.Creator<HealthData>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new HealthData(parcel, 0);
        }
    };
    private String a;
    private String b;
    private final ContentValues c;
    private Map<String, byte[]> d;
    private Map<String, InputStream> e;
    private String f;
    private Object g;

    public HealthData() {
        this(false);
    }

    private HealthData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = (ContentValues)ContentValues.CREATOR.createFromParcel(parcel);
    }

    /* synthetic */ HealthData(Parcel parcel, byte by) {
        this(parcel);
    }

    HealthData(boolean bl) {
        this.c = new ContentValues();
        this.d = new HashMap<String, byte[]>();
        this.e = new HashMap<String, InputStream>();
        if (bl) {
            this.a = null;
            return;
        }
        this.a();
    }

    private void a() {
        UUID uUID = UUID.randomUUID();
        while (uUID.getMostSignificantBits() == 0L && uUID.getLeastSignificantBits() < 100L) {
            uUID = UUID.randomUUID();
        }
        this.a = uUID.toString();
    }

    final void a(Object object) {
        this.g = object;
    }

    final void a(String string2) {
        this.f = string2;
    }

    public final int describeContents() {
        return 0;
    }

    public final Object get(String string2) {
        return this.c.get(string2);
    }

    public final byte[] getBlob(String string2) {
        byte[] arrby = this.d.get(string2);
        if (arrby == null) {
            if (this.f != null && this.c.containsKey(string2)) {
                if (this.c.get(string2) instanceof String && (arrby = IpcUtil.receiveBlob(this.f, this.c.getAsString(string2))) != null) {
                    return arrby;
                }
                return this.c.getAsByteArray(string2);
            }
            return null;
        }
        return (byte[])arrby.clone();
    }

    public final Set<String> getBlobKeySet() {
        return this.d.keySet();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final InputStream getInputStream(String string2) {
        InputStream inputStream;
        void var2_6;
        InputStream inputStream2 = inputStream = this.e.get(string2);
        if (inputStream != null) return var2_6;
        {
            byte[] arrby = this.d.get(string2);
            if (arrby != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])arrby.clone());
                return var2_6;
            } else {
                InputStream inputStream3 = inputStream;
                if (this.f == null) return var2_6;
                {
                    InputStream inputStream4 = inputStream;
                    if (!this.c.containsKey(string2)) return var2_6;
                    {
                        InputStream inputStream5 = inputStream;
                        if (!(this.c.get(string2) instanceof String)) return var2_6;
                        return IpcUtil.receiveStream(this.f, this.c.getAsString(string2));
                    }
                }
            }
        }
    }

    public final Set<String> getInputStreamKeySet() {
        return this.e.keySet();
    }

    public final long getLong(String object) {
        if ((object = this.c.getAsLong((String)object)) == null) {
            return 0L;
        }
        return (Long)object;
    }

    public final String getSourceDevice() {
        return this.b;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void putBlob(String string2, byte[] arrby) {
        if (arrby == null) {
            this.c.put(string2, null);
        } else {
            this.c.put(string2, UUID.randomUUID().toString().getBytes(Charset.forName("UTF-8")));
        }
        this.e.remove(string2);
        this.d.put(string2, arrby);
    }

    public final void putDouble(String string2, double d) {
        this.c.put(string2, Double.valueOf(d));
    }

    public final void putInt(String string2, int n) {
        this.c.put(string2, Integer.valueOf(n));
    }

    public final void putLong(String string2, long l) {
        this.c.put(string2, Long.valueOf(l));
    }

    public final void putString(String string2, String string3) {
        this.c.put(string2, string3);
        this.d.remove(string2);
        this.e.remove(string2);
    }

    public final void setSourceDevice(String string2) {
        this.b = string2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        this.c.writeToParcel(parcel, 0);
    }

}

