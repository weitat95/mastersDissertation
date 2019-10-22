/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tagmanager.zza;
import com.google.android.gms.tagmanager.zzd;
import com.google.android.gms.tagmanager.zzdj;
import java.io.IOException;

final class zzb
implements zzd {
    private /* synthetic */ zza zzkct;

    zzb(zza zza2) {
        this.zzkct = zza2;
    }

    @Override
    public final AdvertisingIdClient.Info zzbdo() {
        try {
            AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(zza.zza(this.zzkct));
            return info;
        }
        catch (IllegalStateException illegalStateException) {
            zzdj.zzc("IllegalStateException getting Advertising Id Info", illegalStateException);
            return null;
        }
        catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
            zzdj.zzc("GooglePlayServicesRepairableException getting Advertising Id Info", googlePlayServicesRepairableException);
            return null;
        }
        catch (IOException iOException) {
            zzdj.zzc("IOException getting Ad Id Info", iOException);
            return null;
        }
        catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
            this.zzkct.close();
            zzdj.zzc("GooglePlayServicesNotAvailableException getting Advertising Id Info", googlePlayServicesNotAvailableException);
            return null;
        }
        catch (Exception exception) {
            zzdj.zzc("Unknown exception. Could not get the Advertising Id Info.", exception);
            return null;
        }
    }
}

