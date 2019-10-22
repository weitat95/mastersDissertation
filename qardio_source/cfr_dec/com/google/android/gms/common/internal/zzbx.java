/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbv;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;

public final class zzbx
extends zzp<zzbd> {
    private static final zzbx zzgbs = new zzbx();

    private zzbx() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zzc(Context context, int n, int n2) throws zzq {
        return zzgbs.zzd(context, n, n2);
    }

    private final View zzd(Context context, int n, int n2) throws zzq {
        try {
            zzbv zzbv2 = new zzbv(n, n2, null);
            IObjectWrapper iObjectWrapper = zzn.zzz(context);
            context = (View)zzn.zzx(((zzbd)this.zzde(context)).zza(iObjectWrapper, zzbv2));
            return context;
        }
        catch (Exception exception) {
            throw new zzq(new StringBuilder(64).append("Could not get button with size ").append(n).append(" and color ").append(n2).toString(), exception);
        }
    }

    @Override
    public final /* synthetic */ Object zze(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
        if (iInterface instanceof zzbd) {
            return (zzbd)iInterface;
        }
        return new zzbe(iBinder);
    }
}

