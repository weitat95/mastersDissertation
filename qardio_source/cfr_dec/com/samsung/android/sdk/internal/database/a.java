/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.database.DatabaseUtils
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.internal.database;

import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sdk.internal.database.IBulkCursor;

final class a
implements IBulkCursor {
    private final IBinder a;
    private Bundle b;

    public a(IBinder iBinder) {
        this.a = iBinder;
        this.b = null;
    }

    public final IBinder asBinder() {
        return this.a;
    }

    @Override
    public final void close() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            this.a.transact(7, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public final void deactivate() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            this.a.transact(2, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public final Bundle getExtras() throws RemoteException {
        Parcel parcel;
        Parcel parcel2;
        if (this.b == null) {
            parcel2 = Parcel.obtain();
            parcel = Parcel.obtain();
            parcel2.writeInterfaceToken("android.content.IBulkCursor");
            this.a.transact(5, parcel2, parcel, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel);
            this.b = parcel.readBundle();
        }
        return this.b;
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public final CursorWindow getWindow(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeInt(n);
            this.a.transact(1, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel2);
            CursorWindow cursorWindow = null;
            if (parcel2.readInt() == 1) {
                cursorWindow = CursorWindow.newFromParcel((Parcel)parcel2);
            }
            return cursorWindow;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public final void onMove(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeInt(n);
            this.a.transact(4, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final int requery() throws RemoteException {
        Parcel parcel;
        Parcel parcel2;
        block5: {
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            boolean bl = this.a.transact(3, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel2);
            if (bl) break block5;
            return -1;
        }
        try {
            int n = parcel2.readInt();
            this.b = parcel2.readBundle();
            return n;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    @Override
    public final Bundle respond(Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            parcel.writeBundle(bundle);
            this.a.transact(6, parcel, parcel2, 0);
            DatabaseUtils.readExceptionFromParcel((Parcel)parcel2);
            bundle = parcel2.readBundle();
            return bundle;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }
}

