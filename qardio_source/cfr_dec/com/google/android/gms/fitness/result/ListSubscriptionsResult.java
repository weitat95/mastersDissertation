/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.result.zzg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListSubscriptionsResult
extends zzbfm
implements Result {
    public static final Parcelable.Creator<ListSubscriptionsResult> CREATOR = new zzg();
    private final Status mStatus;
    private final int zzeck;
    private final List<Subscription> zzhio;

    ListSubscriptionsResult(int n, List<Subscription> list, Status status) {
        this.zzeck = n;
        this.zzhio = list;
        this.mStatus = status;
    }

    private ListSubscriptionsResult(List<Subscription> list, Status status) {
        this.zzeck = 3;
        this.zzhio = Collections.unmodifiableList(list);
        this.mStatus = zzbq.checkNotNull(status, "status");
    }

    public static ListSubscriptionsResult zzae(Status status) {
        return new ListSubscriptionsResult(Collections.<Subscription>emptyList(), status);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) return true;
        boolean bl2 = bl;
        if (!(object instanceof ListSubscriptionsResult)) return bl2;
        object = (ListSubscriptionsResult)object;
        boolean bl3 = this.mStatus.equals(((ListSubscriptionsResult)object).mStatus) && zzbg.equal(this.zzhio, ((ListSubscriptionsResult)object).zzhio);
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    @Override
    public Status getStatus() {
        return this.mStatus;
    }

    public List<Subscription> getSubscriptions() {
        return this.zzhio;
    }

    public List<Subscription> getSubscriptions(DataType dataType) {
        ArrayList<Subscription> arrayList = new ArrayList<Subscription>();
        for (Subscription subscription : this.zzhio) {
            if (!dataType.equals(subscription.zzaqq())) continue;
            arrayList.add(subscription);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhio});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("status", this.mStatus).zzg("subscriptions", this.zzhio).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.getSubscriptions(), false);
        zzbfp.zza(parcel, 2, this.getStatus(), n, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }
}

