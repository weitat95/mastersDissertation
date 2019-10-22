/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.maps;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;

public class StreetViewPanorama {
    private final IStreetViewPanoramaDelegate zzite;

    public StreetViewPanorama(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) {
        this.zzite = zzbq.checkNotNull(iStreetViewPanoramaDelegate, "delegate");
    }
}

