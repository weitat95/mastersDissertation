/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.google.android.gms.dynamic;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.dynamic.zzm;
import com.google.android.gms.internal.zzev;

public interface IObjectWrapper
extends IInterface {

    public static class zza
    extends zzev
    implements IObjectWrapper {
        public zza() {
            this.attachInterface((IInterface)this, "com.google.android.gms.dynamic.IObjectWrapper");
        }

        public static IObjectWrapper zzaq(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            if (iInterface instanceof IObjectWrapper) {
                return (IObjectWrapper)iInterface;
            }
            return new zzm(iBinder);
        }
    }

}

