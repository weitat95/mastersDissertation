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
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzbp;
import com.google.android.gms.maps.internal.zzby;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.zzal;
import java.util.ArrayList;
import java.util.List;

public class SupportStreetViewPanoramaFragment
extends Fragment {
    private final zzb zzitz = new zzb(this);

    @Override
    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.zzitz.setActivity(activity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzitz.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzitz.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onDestroy() {
        this.zzitz.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        this.zzitz.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder(threadPolicy).permitAll().build());
        try {
            super.onInflate(activity, attributeSet, bundle);
            this.zzitz.setActivity(activity);
            attributeSet = new Bundle();
            this.zzitz.onInflate(activity, (Bundle)attributeSet, bundle);
            return;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }

    @Override
    public void onLowMemory() {
        this.zzitz.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        this.zzitz.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.zzitz.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.zzitz.onSaveInstanceState(bundle);
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    static final class zza
    implements StreetViewLifecycleDelegate {
        private final Fragment zzgwp;
        private final IStreetViewPanoramaFragmentDelegate zzitk;

        public zza(Fragment fragment, IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate) {
            this.zzitk = zzbq.checkNotNull(iStreetViewPanoramaFragmentDelegate);
            this.zzgwp = zzbq.checkNotNull(fragment);
        }

        public final void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
            try {
                this.zzitk.getStreetViewPanoramaAsync(new zzal(this, onStreetViewPanoramaReadyCallback));
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
                if (bundle3 != null && bundle3.containsKey("StreetViewPanoramaOptions")) {
                    zzby.zza(bundle2, "StreetViewPanoramaOptions", bundle3.getParcelable("StreetViewPanoramaOptions"));
                }
                this.zzitk.onCreate(bundle2);
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
                object = this.zzitk.onCreateView(zzn.zzz(object), zzn.zzz(viewGroup), bundle2);
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
                this.zzitk.onDestroy();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onDestroyView() {
            try {
                this.zzitk.onDestroyView();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                bundle = new Bundle();
                zzby.zzd(bundle2, bundle);
                this.zzitk.onInflate(zzn.zzz(activity), null, bundle);
                zzby.zzd(bundle, bundle2);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onLowMemory() {
            try {
                this.zzitk.onLowMemory();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onPause() {
            try {
                this.zzitk.onPause();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
        }

        @Override
        public final void onResume() {
            try {
                this.zzitk.onResume();
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
                this.zzitk.onSaveInstanceState(bundle2);
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
        private Activity mActivity;
        private final Fragment zzgwp;
        private zzo<zza> zzisu;
        private final List<OnStreetViewPanoramaReadyCallback> zzitm = new ArrayList<OnStreetViewPanoramaReadyCallback>();

        zzb(Fragment fragment) {
            this.zzgwp = fragment;
        }

        private final void setActivity(Activity activity) {
            this.mActivity = activity;
            this.zzawb();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private final void zzawb() {
            if (this.mActivity == null) return;
            if (this.zzisu == null) return;
            if (this.zzapw() != null) return;
            try {
                MapsInitializer.initialize((Context)this.mActivity);
                IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate = zzbz.zzdt((Context)this.mActivity).zzab(zzn.zzz(this.mActivity));
                this.zzisu.zza(new zza(this.zzgwp, iStreetViewPanoramaFragmentDelegate));
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

        @Override
        protected final void zza(zzo<zza> zzo2) {
            this.zzisu = zzo2;
            this.zzawb();
        }
    }

}

