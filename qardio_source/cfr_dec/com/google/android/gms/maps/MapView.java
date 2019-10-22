/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 *  android.os.StrictMode$ThreadPolicy$Builder
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzap;
import com.google.android.gms.maps.internal.zzby;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.zzac;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapView
extends FrameLayout {
    private final zzb zzisw;

    public MapView(Context context) {
        super(context);
        this.zzisw = new zzb((ViewGroup)this, context, null);
        this.setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzisw = new zzb((ViewGroup)this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        this.setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.zzisw = new zzb((ViewGroup)this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        this.setClickable(true);
    }

    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        zzbq.zzge("getMapAsync() must be called on the main thread");
        this.zzisw.getMapAsync(onMapReadyCallback);
    }

    public final void onCreate(Bundle bundle) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder(threadPolicy).permitAll().build());
        try {
            this.zzisw.onCreate(bundle);
            if (this.zzisw.zzapw() == null) {
                com.google.android.gms.dynamic.zza.zzb(this);
            }
            return;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }

    public final void onDestroy() {
        this.zzisw.onDestroy();
    }

    public final void onLowMemory() {
        this.zzisw.onLowMemory();
    }

    public final void onPause() {
        this.zzisw.onPause();
    }

    public final void onResume() {
        this.zzisw.onResume();
    }

    static final class zza
    implements MapLifecycleDelegate {
        private final ViewGroup zzisx;
        private final IMapViewDelegate zzisy;
        private View zzisz;

        public zza(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.zzisy = zzbq.checkNotNull(iMapViewDelegate);
            this.zzisx = zzbq.checkNotNull(viewGroup);
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            try {
                this.zzisy.getMapAsync(new zzac(this, onMapReadyCallback));
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onCreate(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                this.zzisy.onCreate(bundle2);
                zzby.zzd(bundle2, bundle);
                this.zzisz = (View)zzn.zzx(this.zzisy.getView());
                this.zzisx.removeAllViews();
                this.zzisx.addView(this.zzisz);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        @Override
        public final void onDestroy() {
            try {
                this.zzisy.onDestroy();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
        }

        @Override
        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
        }

        @Override
        public final void onLowMemory() {
            try {
                this.zzisy.onLowMemory();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onPause() {
            try {
                this.zzisy.onPause();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onResume() {
            try {
                this.zzisy.onResume();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onSaveInstanceState(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                this.zzisy.onSaveInstanceState(bundle2);
                zzby.zzd(bundle2, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onStart() {
            try {
                this.zzisy.onStart();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onStop() {
            try {
                this.zzisy.onStop();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }
    }

    static final class zzb
    extends com.google.android.gms.dynamic.zza<zza> {
        private zzo<zza> zzisu;
        private final List<OnMapReadyCallback> zzisv = new ArrayList<OnMapReadyCallback>();
        private final ViewGroup zzita;
        private final Context zzitb;
        private final GoogleMapOptions zzitc;

        zzb(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.zzita = viewGroup;
            this.zzitb = context;
            this.zzitc = googleMapOptions;
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            if (this.zzapw() != null) {
                ((zza)this.zzapw()).getMapAsync(onMapReadyCallback);
                return;
            }
            this.zzisv.add(onMapReadyCallback);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected final void zza(zzo<zza> iterator) {
            this.zzisu = iterator;
            if (this.zzisu == null || this.zzapw() != null) return;
            try {
                MapsInitializer.initialize(this.zzitb);
                iterator = zzbz.zzdt(this.zzitb).zza(zzn.zzz(this.zzitb), this.zzitc);
                if (iterator == null) {
                    return;
                }
                this.zzisu.zza(new zza(this.zzita, (IMapViewDelegate)((Object)iterator)));
                for (OnMapReadyCallback onMapReadyCallback : this.zzisv) {
                    ((zza)this.zzapw()).getMapAsync(onMapReadyCallback);
                }
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                // empty catch block
                return;
            }
            {
                this.zzisv.clear();
                return;
            }
        }
    }

}

