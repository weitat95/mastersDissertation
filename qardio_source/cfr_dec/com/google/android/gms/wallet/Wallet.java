/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.RemoteException
 */
package com.google.android.gms.wallet;

import android.accounts.Account;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzdkp;
import com.google.android.gms.internal.zzdlh;
import com.google.android.gms.internal.zzdlo;
import com.google.android.gms.internal.zzdlw;
import com.google.android.gms.internal.zzdlx;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.wobs.WalletObjects;
import com.google.android.gms.wallet.zzap;
import java.util.Arrays;
import java.util.Locale;

public final class Wallet {
    public static final Api<WalletOptions> API;
    @Deprecated
    public static final Payments Payments;
    private static final Api.zzf<zzdlo> zzebf;
    private static final Api.zza<zzdlo, WalletOptions> zzebg;
    private static WalletObjects zzldx;
    private static zzdkp zzldy;

    static {
        zzebf = new Api.zzf();
        zzebg = new zzap();
        API = new Api<WalletOptions>("Wallet.API", zzebg, zzebf);
        Payments = new zzdlh();
        zzldx = new zzdlx();
        zzldy = new zzdlw();
    }

    public static final class WalletOptions
    implements Api.ApiOptions.HasAccountOptions {
        private Account account;
        public final int environment;
        public final int theme;
        final boolean zzldz;

        private WalletOptions() {
            this(new Builder());
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.zzlea;
            this.theme = builder.mTheme;
            this.zzldz = builder.zzleb;
            this.account = null;
        }

        /* synthetic */ WalletOptions(Builder builder, zzap zzap2) {
            this(builder);
        }

        /* synthetic */ WalletOptions(zzap zzap2) {
            this();
        }

        public final boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof WalletOptions) {
                object = (WalletOptions)object;
                bl2 = bl;
                if (zzbg.equal(this.environment, ((WalletOptions)object).environment)) {
                    bl2 = bl;
                    if (zzbg.equal(this.theme, ((WalletOptions)object).theme)) {
                        bl2 = bl;
                        if (zzbg.equal(null, null)) {
                            bl2 = bl;
                            if (zzbg.equal(this.zzldz, ((WalletOptions)object).zzldz)) {
                                bl2 = true;
                            }
                        }
                    }
                }
            }
            return bl2;
        }

        @Override
        public final Account getAccount() {
            return null;
        }

        public final int hashCode() {
            return Arrays.hashCode(new Object[]{this.environment, this.theme, null, this.zzldz});
        }

        public static final class Builder {
            private int mTheme = 0;
            private int zzlea = 3;
            private boolean zzleb = true;

            public final WalletOptions build() {
                return new WalletOptions(this, null);
            }

            public final Builder setEnvironment(int n) {
                if (n == 0 || n == 0 || n == 2 || n == 1 || n == 3) {
                    this.zzlea = n;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid environment value %d", n));
            }

            public final Builder setTheme(int n) {
                if (n == 0 || n == 1) {
                    this.mTheme = n;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid theme value %d", n));
            }
        }

    }

    public static abstract class zza<R extends Result>
    extends zzm<R, zzdlo> {
        public zza(GoogleApiClient googleApiClient) {
            super(API, googleApiClient);
        }

        @Override
        protected abstract void zza(zzdlo var1) throws RemoteException;
    }

    public static abstract class zzb
    extends zza<Status> {
        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override
        protected final /* synthetic */ Result zzb(Status status) {
            return status;
        }
    }

}

