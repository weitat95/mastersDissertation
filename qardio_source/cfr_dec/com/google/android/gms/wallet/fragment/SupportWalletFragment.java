/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 */
package com.google.android.gms.wallet.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.dynamic.zzr;
import com.google.android.gms.internal.zzdkw;
import com.google.android.gms.internal.zzdla;
import com.google.android.gms.internal.zzdlv;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;

public final class SupportWalletFragment
extends Fragment {
    private boolean mCreated = false;
    private final Fragment zzgwp;
    private zzb zzlel;
    private final zzr zzlem = zzr.zza(this);
    private final zzc zzlen = new zzc(this, null);
    private zza zzleo = new zza(this);
    private WalletFragmentOptions zzlep;
    private WalletFragmentInitParams zzleq;
    private MaskedWalletRequest zzler;
    private MaskedWallet zzles;
    private Boolean zzlet;

    public SupportWalletFragment() {
        this.zzgwp = this;
    }

    public static SupportWalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        SupportWalletFragment supportWalletFragment = new SupportWalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", (Parcelable)walletFragmentOptions);
        supportWalletFragment.zzgwp.setArguments(bundle);
        return supportWalletFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzlel != null) {
            this.zzlel.initialize(walletFragmentInitParams);
            this.zzleq = null;
            return;
        } else {
            if (this.zzleq != null) {
                Log.w((String)"SupportWalletFragment", (String)"initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
                return;
            }
            this.zzleq = walletFragmentInitParams;
            if (this.zzler != null) {
                Log.w((String)"SupportWalletFragment", (String)"updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzles == null) return;
            {
                Log.w((String)"SupportWalletFragment", (String)"updateMaskedWallet() was called before initialize()");
                return;
            }
        }
    }

    @Override
    public final void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (this.zzlel != null) {
            this.zzlel.onActivityResult(n, n2, intent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void onCreate(Bundle bundle) {
        WalletFragmentOptions walletFragmentOptions;
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams)bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzleq != null) {
                    Log.w((String)"SupportWalletFragment", (String)"initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.zzleq = walletFragmentInitParams;
            }
            if (this.zzler == null) {
                this.zzler = (MaskedWalletRequest)bundle.getParcelable("maskedWalletRequest");
            }
            if (this.zzles == null) {
                this.zzles = (MaskedWallet)bundle.getParcelable("maskedWallet");
            }
            if (bundle.containsKey("walletFragmentOptions")) {
                this.zzlep = (WalletFragmentOptions)bundle.getParcelable("walletFragmentOptions");
            }
            if (bundle.containsKey("enabled")) {
                this.zzlet = bundle.getBoolean("enabled");
            }
        } else if (this.zzgwp.getArguments() != null && (walletFragmentOptions = (WalletFragmentOptions)this.zzgwp.getArguments().getParcelable("extraWalletFragmentOptions")) != null) {
            walletFragmentOptions.zzeo((Context)this.zzgwp.getActivity());
            this.zzlep = walletFragmentOptions;
        }
        this.mCreated = true;
        this.zzlen.onCreate(bundle);
    }

    @Override
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzlen.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    @Override
    public final void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.zzlep == null) {
            this.zzlep = WalletFragmentOptions.zza((Context)activity, attributeSet);
        }
        attributeSet = new Bundle();
        attributeSet.putParcelable("attrKeyWalletFragmentOptions", (Parcelable)this.zzlep);
        this.zzlen.onInflate(activity, (Bundle)attributeSet, bundle);
    }

    @Override
    public final void onPause() {
        super.onPause();
        this.zzlen.onPause();
    }

    @Override
    public final void onResume() {
        super.onResume();
        this.zzlen.onResume();
        FragmentManager fragmentManager = this.zzgwp.getActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("GooglePlayServicesErrorDialog");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)this.zzgwp.getActivity()), this.zzgwp.getActivity(), -1);
        }
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzlen.onSaveInstanceState(bundle);
        if (this.zzleq != null) {
            bundle.putParcelable("walletFragmentInitParams", (Parcelable)this.zzleq);
            this.zzleq = null;
        }
        if (this.zzler != null) {
            bundle.putParcelable("maskedWalletRequest", (Parcelable)this.zzler);
            this.zzler = null;
        }
        if (this.zzles != null) {
            bundle.putParcelable("maskedWallet", (Parcelable)this.zzles);
            this.zzles = null;
        }
        if (this.zzlep != null) {
            bundle.putParcelable("walletFragmentOptions", (Parcelable)this.zzlep);
            this.zzlep = null;
        }
        if (this.zzlet != null) {
            bundle.putBoolean("enabled", this.zzlet.booleanValue());
            this.zzlet = null;
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        this.zzlen.onStart();
    }

    @Override
    public final void onStop() {
        super.onStop();
        this.zzlen.onStop();
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzlel != null) {
            this.zzlel.updateMaskedWallet(maskedWallet);
            this.zzles = null;
            return;
        }
        this.zzles = maskedWallet;
    }

    public static interface OnStateChangedListener {
        public void onStateChanged(SupportWalletFragment var1, int var2, int var3, Bundle var4);
    }

    static final class zza
    extends zzdla {
        private OnStateChangedListener zzleu;
        private final SupportWalletFragment zzlev;

        zza(SupportWalletFragment supportWalletFragment) {
            this.zzlev = supportWalletFragment;
        }

        @Override
        public final void zza(int n, int n2, Bundle bundle) {
            if (this.zzleu != null) {
                this.zzleu.onStateChanged(this.zzlev, n, n2, bundle);
            }
        }
    }

    static final class zzb
    implements LifecycleDelegate {
        private final zzdkw zzlew;

        private zzb(zzdkw zzdkw2) {
            this.zzlew = zzdkw2;
        }

        /* synthetic */ zzb(zzdkw zzdkw2, com.google.android.gms.wallet.fragment.zza zza2) {
            this(zzdkw2);
        }

        private final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
            try {
                this.zzlew.initialize(walletFragmentInitParams);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        private final void onActivityResult(int n, int n2, Intent intent) {
            try {
                this.zzlew.onActivityResult(n, n2, intent);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        private final void setEnabled(boolean bl) {
            try {
                this.zzlew.setEnabled(bl);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        private final void updateMaskedWallet(MaskedWallet maskedWallet) {
            try {
                this.zzlew.updateMaskedWallet(maskedWallet);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        private final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            try {
                this.zzlew.updateMaskedWalletRequest(maskedWalletRequest);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onCreate(Bundle bundle) {
            try {
                this.zzlew.onCreate(bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                layoutInflater = (View)zzn.zzx(this.zzlew.onCreateView(zzn.zzz(layoutInflater), zzn.zzz(viewGroup), bundle));
                return layoutInflater;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onDestroy() {
        }

        @Override
        public final void onDestroyView() {
        }

        @Override
        public final void onInflate(Activity activity, Bundle object, Bundle bundle) {
            object = (WalletFragmentOptions)object.getParcelable("extraWalletFragmentOptions");
            try {
                this.zzlew.zza(zzn.zzz(activity), (WalletFragmentOptions)object, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onLowMemory() {
        }

        @Override
        public final void onPause() {
            try {
                this.zzlew.onPause();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onResume() {
            try {
                this.zzlew.onResume();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onSaveInstanceState(Bundle bundle) {
            try {
                this.zzlew.onSaveInstanceState(bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onStart() {
            try {
                this.zzlew.onStart();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        @Override
        public final void onStop() {
            try {
                this.zzlew.onStop();
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }
    }

    final class zzc
    extends com.google.android.gms.dynamic.zza<zzb>
    implements View.OnClickListener {
        private /* synthetic */ SupportWalletFragment zzlex;

        private zzc(SupportWalletFragment supportWalletFragment) {
            this.zzlex = supportWalletFragment;
        }

        /* synthetic */ zzc(SupportWalletFragment supportWalletFragment, com.google.android.gms.wallet.fragment.zza zza2) {
            this(supportWalletFragment);
        }

        public final void onClick(View object) {
            object = this.zzlex.zzgwp.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable((Context)object), (Activity)object, -1);
        }

        @Override
        protected final void zza(FrameLayout frameLayout) {
            int n = -1;
            int n2 = -2;
            Button button = new Button((Context)this.zzlex.zzgwp.getActivity());
            button.setText(R.string.wallet_buy_button_place_holder);
            int n3 = n2;
            int n4 = n;
            if (this.zzlex.zzlep != null) {
                WalletFragmentStyle walletFragmentStyle = this.zzlex.zzlep.getFragmentStyle();
                n3 = n2;
                n4 = n;
                if (walletFragmentStyle != null) {
                    DisplayMetrics displayMetrics = this.zzlex.zzgwp.getResources().getDisplayMetrics();
                    n4 = walletFragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                    n3 = walletFragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new ViewGroup.LayoutParams(n4, n3));
            button.setOnClickListener((View.OnClickListener)this);
            frameLayout.addView((View)button);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected final void zza(zzo<zzb> zzo2) {
            Object object = this.zzlex.zzgwp.getActivity();
            if (this.zzlex.zzlel != null || !this.zzlex.mCreated || object == null) return;
            try {
                object = zzdlv.zza((Activity)object, this.zzlex.zzlem, this.zzlex.zzlep, this.zzlex.zzleo);
                this.zzlex.zzlel = new zzb((zzdkw)object, null);
                this.zzlex.zzlep = null;
                zzo2.zza(this.zzlex.zzlel);
            }
            catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
                return;
            }
            if (this.zzlex.zzleq != null) {
                this.zzlex.zzlel.initialize(this.zzlex.zzleq);
                this.zzlex.zzleq = null;
            }
            if (this.zzlex.zzler != null) {
                this.zzlex.zzlel.updateMaskedWalletRequest(this.zzlex.zzler);
                this.zzlex.zzler = null;
            }
            if (this.zzlex.zzles != null) {
                this.zzlex.zzlel.updateMaskedWallet(this.zzlex.zzles);
                this.zzlex.zzles = null;
            }
            if (this.zzlex.zzlet == null) return;
            this.zzlex.zzlel.setEnabled(this.zzlex.zzlet);
            this.zzlex.zzlet = null;
        }
    }

}

