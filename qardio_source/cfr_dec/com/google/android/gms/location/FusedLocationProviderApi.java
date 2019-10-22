/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

@Deprecated
public interface FusedLocationProviderApi {
    public PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationListener var3);
}

