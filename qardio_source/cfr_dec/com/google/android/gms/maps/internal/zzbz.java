/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.maps.internal;

import android.content.Context;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.maps.internal.zze;
import com.google.android.gms.maps.internal.zzf;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class zzbz {
    private static final String TAG = zzbz.class.getSimpleName();
    private static Context zziub = null;
    private static zze zziuc;

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static <T> T zza(ClassLoader classLoader, String string2) {
        void var1_7;
        T t;
        try {
            t = zzbz.zzd(zzbq.checkNotNull(classLoader).loadClass((String)var1_7));
        }
        catch (ClassNotFoundException classNotFoundException) {
            void var0_5;
            String string3 = String.valueOf(var1_7);
            if (string3.length() != 0) {
                String string4 = "Unable to find dynamic class ".concat(string3);
                do {
                    throw new IllegalStateException((String)var0_5);
                    break;
                } while (true);
            }
            String string5 = new String("Unable to find dynamic class ");
            throw new IllegalStateException((String)var0_5);
        }
        return t;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static <T> T zzd(Class<?> object) {
        Object t;
        try {
            t = ((Class)object).newInstance();
        }
        catch (InstantiationException instantiationException) {
            object = String.valueOf(((Class)object).getName());
            if (((String)object).length() != 0) {
                object = "Unable to instantiate the dynamic class ".concat((String)object);
                do {
                    throw new IllegalStateException((String)object);
                    break;
                } while (true);
            }
            object = new String("Unable to instantiate the dynamic class ");
            throw new IllegalStateException((String)object);
        }
        catch (IllegalAccessException illegalAccessException) {
            object = String.valueOf(((Class)object).getName());
            if (((String)object).length() != 0) {
                object = "Unable to call the default constructor of ".concat((String)object);
                do {
                    throw new IllegalStateException((String)object);
                    break;
                } while (true);
            }
            object = new String("Unable to call the default constructor of ");
            throw new IllegalStateException((String)object);
        }
        return t;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zze zzdt(Context context) throws GooglePlayServicesNotAvailableException {
        void var2_5;
        zzbq.checkNotNull(context);
        if (zziuc != null) {
            return zziuc;
        }
        int n = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (n) {
            default: {
                throw new GooglePlayServicesNotAvailableException(n);
            }
            case 0: 
        }
        Log.i((String)TAG, (String)"Making Creator dynamically");
        IBinder iBinder = (IBinder)zzbz.zza(zzbz.zzdu(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl");
        if (iBinder == null) {
            Object var2_4 = null;
        } else {
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            if (iInterface instanceof zze) {
                zze zze2 = (zze)iInterface;
            } else {
                zzf zzf2 = new zzf(iBinder);
            }
        }
        zziuc = var2_5;
        try {
            zziuc.zzi(zzn.zzz(zzbz.zzdu(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return zziuc;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    private static Context zzdu(Context context) {
        if (zziub != null) {
            return zziub;
        }
        zziub = context = zzbz.zzdv(context);
        return context;
    }

    private static Context zzdv(Context context) {
        try {
            Context context2 = DynamiteModule.zza(context, DynamiteModule.zzgww, "com.google.android.gms.maps_dynamite").zzaqb();
            return context2;
        }
        catch (Throwable throwable) {
            Log.e((String)TAG, (String)"Failed to load maps module, use legacy", (Throwable)throwable);
            return GooglePlayServicesUtil.getRemoteContext(context);
        }
    }
}

