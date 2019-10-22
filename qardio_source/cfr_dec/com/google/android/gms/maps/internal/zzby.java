/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.Parcelable;

public final class zzby {
    private zzby() {
    }

    public static void zza(Bundle bundle, String string2, Parcelable parcelable) {
        Bundle bundle2;
        bundle.setClassLoader(zzby.class.getClassLoader());
        Bundle bundle3 = bundle2 = bundle.getBundle("map_state");
        if (bundle2 == null) {
            bundle3 = new Bundle();
        }
        bundle3.setClassLoader(zzby.class.getClassLoader());
        bundle3.putParcelable(string2, parcelable);
        bundle.putBundle("map_state", bundle3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zzd(Bundle bundle, Bundle bundle2) {
        block8: {
            block7: {
                if (bundle == null || bundle2 == null) break block7;
                T t = zzby.zzg(bundle, "MapOptions");
                if (t != null) {
                    zzby.zza(bundle2, "MapOptions", t);
                }
                if ((t = zzby.zzg(bundle, "StreetViewPanoramaOptions")) != null) {
                    zzby.zza(bundle2, "StreetViewPanoramaOptions", t);
                }
                if ((t = zzby.zzg(bundle, "camera")) != null) {
                    zzby.zza(bundle2, "camera", t);
                }
                if (bundle.containsKey("position")) {
                    bundle2.putString("position", bundle.getString("position"));
                }
                if (bundle.containsKey("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT")) break block8;
            }
            return;
        }
        bundle2.putBoolean("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT", bundle.getBoolean("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT", false));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static <T extends Parcelable> T zzg(Bundle bundle, String string2) {
        block3: {
            block2: {
                if (bundle == null) break block2;
                bundle.setClassLoader(zzby.class.getClassLoader());
                if ((bundle = bundle.getBundle("map_state")) != null) break block3;
            }
            return null;
        }
        bundle.setClassLoader(zzby.class.getClassLoader());
        return (T)bundle.getParcelable(string2);
    }
}

