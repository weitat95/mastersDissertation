/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
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
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzbp;
import com.google.android.gms.maps.internal.zzby;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.zzaj;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreetViewPanoramaView
extends FrameLayout {
    private final zzb zzitu;

    public StreetViewPanoramaView(Context context) {
        super(context);
        this.zzitu = new zzb((ViewGroup)this, context, null);
    }

    public StreetViewPanoramaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzitu = new zzb((ViewGroup)this, context, null);
    }

    public StreetViewPanoramaView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.zzitu = new zzb((ViewGroup)this, context, null);
    }

    static final class zza
    implements StreetViewLifecycleDelegate {
        private final ViewGroup zzisx;
        private final IStreetViewPanoramaViewDelegate zzitv;
        private View zzitw;

        public zza(ViewGroup viewGroup, IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate) {
            this.zzitv = zzbq.checkNotNull(iStreetViewPanoramaViewDelegate);
            this.zzisx = zzbq.checkNotNull(viewGroup);
        }

        public final void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            try {
                this.zzitv.getStreetViewPanoramaAsync(new zzaj(this, onStreetViewPanoramaReadyCallback));
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
                this.zzitv.onCreate(bundle2);
                zzby.zzd(bundle2, bundle);
                this.zzitw = (View)zzn.zzx(this.zzitv.getView());
                this.zzisx.removeAllViews();
                this.zzisx.addView(this.zzitw);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
        }

        @Override
        public final void onDestroy() {
            try {
                this.zzitv.onDestroy();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
        }

        @Override
        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
        }

        @Override
        public final void onLowMemory() {
            try {
                this.zzitv.onLowMemory();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onPause() {
            try {
                this.zzitv.onPause();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onResume() {
            try {
                this.zzitv.onResume();
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
                this.zzitv.onSaveInstanceState(bundle2);
                zzby.zzd(bundle2, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onStart() {
        }

        @Override
        public final void onStop() {
        }
    }

    static final class zzb
    extends com.google.android.gms.dynamic.zza<zza> {
        private zzo<zza> zzisu;
        private final ViewGroup zzita;
        private final Context zzitb;
        private final List<OnStreetViewPanoramaReadyCallback> zzitm = new ArrayList<OnStreetViewPanoramaReadyCallback>();
        private final StreetViewPanoramaOptions zzitx;

        zzb(ViewGroup viewGroup, Context context, StreetViewPanoramaOptions streetViewPanoramaOptions) {
            this.zzita = viewGroup;
            this.zzitb = context;
            this.zzitx = streetViewPanoramaOptions;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected final void zza(zzo<zza> iterator) {
            this.zzisu = iterator;
            if (this.zzisu == null) return;
            if (this.zzapw() != null) return;
            try {
                MapsInitializer.initialize(this.zzitb);
                iterator = zzbz.zzdt(this.zzitb).zza(zzn.zzz(this.zzitb), this.zzitx);
                this.zzisu.zza(new zza(this.zzita, (IStreetViewPanoramaViewDelegate)((Object)iterator)));
                for (OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback : this.zzitm) {
                    ((zza)this.zzapw()).getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
                }
                this.zzitm.clear();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                return;
            }
        }
    }

}

