/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 *  android.os.StrictMode$ThreadPolicy$Builder
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzap;
import com.google.android.gms.maps.internal.zzby;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.zzak;
import java.util.ArrayList;
import java.util.List;

public class SupportMapFragment
extends Fragment {
    private final zzb zzity = new zzb(this);

    @Override
    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportMapFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.zzity.setActivity(activity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzity.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = this.zzity.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.setClickable(true);
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        this.zzity.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        this.zzity.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet object, Bundle bundle) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder(threadPolicy).permitAll().build());
        try {
            super.onInflate(activity, (AttributeSet)object, bundle);
            this.zzity.setActivity(activity);
            object = GoogleMapOptions.createFromAttributes((Context)activity, object);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("MapOptions", (Parcelable)object);
            this.zzity.onInflate(activity, bundle2, bundle);
            return;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }

    @Override
    public void onLowMemory() {
        this.zzity.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        this.zzity.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.zzity.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportMapFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.zzity.onSaveInstanceState(bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.zzity.onStart();
    }

    @Override
    public void onStop() {
        this.zzity.onStop();
        super.onStop();
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    static final class zza
    implements MapLifecycleDelegate {
        private final Fragment zzgwp;
        private final IMapFragmentDelegate zziss;

        public zza(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
            this.zziss = zzbq.checkNotNull(iMapFragmentDelegate);
            this.zzgwp = zzbq.checkNotNull(fragment);
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            try {
                this.zziss.getMapAsync(new zzak(this, onMapReadyCallback));
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void onCreate(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                Bundle bundle3 = this.zzgwp.getArguments();
                if (bundle3 != null && bundle3.containsKey("MapOptions")) {
                    zzby.zza(bundle2, "MapOptions", bundle3.getParcelable("MapOptions"));
                }
                this.zziss.onCreate(bundle2);
                zzby.zzd(bundle2, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                object = this.zziss.onCreateView(zzn.zzz(object), zzn.zzz(viewGroup), bundle2);
                zzby.zzd(bundle2, bundle);
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
            return (View)zzn.zzx((IObjectWrapper)object);
        }

        @Override
        public final void onDestroy() {
            try {
                this.zziss.onDestroy();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onDestroyView() {
            try {
                this.zziss.onDestroyView();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onInflate(Activity activity, Bundle object, Bundle bundle) {
            object = (GoogleMapOptions)object.getParcelable("MapOptions");
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                this.zziss.onInflate(zzn.zzz(activity), (GoogleMapOptions)object, bundle2);
                zzby.zzd(bundle2, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onLowMemory() {
            try {
                this.zziss.onLowMemory();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onPause() {
            try {
                this.zziss.onPause();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onResume() {
            try {
                this.zziss.onResume();
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
                this.zziss.onSaveInstanceState(bundle2);
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
                this.zziss.onStart();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onStop() {
            try {
                this.zziss.onStop();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }
    }

    static final class zzb
    extends com.google.android.gms.dynamic.zza<zza> {
        private Activity mActivity;
        private final Fragment zzgwp;
        private zzo<zza> zzisu;
        private final List<OnMapReadyCallback> zzisv = new ArrayList<OnMapReadyCallback>();

        zzb(Fragment fragment) {
            this.zzgwp = fragment;
        }

        private final void setActivity(Activity activity) {
            this.mActivity = activity;
            this.zzawb();
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private final void zzawb() {
            if (this.mActivity == null || this.zzisu == null || this.zzapw() != null) return;
            try {
                MapsInitializer.initialize((Context)this.mActivity);
                IMapFragmentDelegate iMapFragmentDelegate = zzbz.zzdt((Context)this.mActivity).zzaa(zzn.zzz(this.mActivity));
                if (iMapFragmentDelegate == null) {
                    return;
                }
                this.zzisu.zza(new zza(this.zzgwp, iMapFragmentDelegate));
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

        @Override
        protected final void zza(zzo<zza> zzo2) {
            this.zzisu = zzo2;
            this.zzawb();
        }
    }

}

