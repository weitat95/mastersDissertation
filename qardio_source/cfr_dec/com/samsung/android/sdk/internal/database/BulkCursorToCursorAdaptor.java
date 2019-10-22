/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.AbstractWindowedCursor
 *  android.database.CursorWindow
 *  android.database.StaleDataException
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.samsung.android.sdk.internal.database;

import android.database.AbstractWindowedCursor;
import android.database.CursorWindow;
import android.database.StaleDataException;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.sdk.internal.database.BulkCursorDescriptor;
import com.samsung.android.sdk.internal.database.IBulkCursor;
import com.samsung.android.sdk.internal.healthdata.IpcUtil;

public final class BulkCursorToCursorAdaptor
extends AbstractWindowedCursor {
    private IBulkCursor a;
    private String[] b;
    private boolean c;
    private int d;
    private String e;

    private void a() {
        if (this.a == null) {
            throw new StaleDataException("Attempted to access a cursor after it has been closed.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void close() {
        super.close();
        if (this.a == null) return;
        try {
            this.a.close();
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"BulkCursor", (String)"Remote process exception when closing");
            return;
        }
    }

    protected final void closeWindow() {
        if (this.mWindow != null) {
            this.mWindow.close();
            this.mWindow = null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void deactivate() {
        super.deactivate();
        if (this.a == null) return;
        try {
            this.a.deactivate();
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"BulkCursor", (String)"Remote process exception when deactivating");
            return;
        }
    }

    public final byte[] getBlob(int n) {
        byte[] arrby;
        if (n >= 0 && n < this.b.length && this.e != null && this.getType(n) == 3 && (arrby = IpcUtil.receiveBlob(this.e, this.getString(n))) != null) {
            return arrby;
        }
        return super.getBlob(n);
    }

    public final int getColumnIndex(String string2) {
        int n = 0;
        String[] arrstring = this.b;
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            if (arrstring[n3].equalsIgnoreCase(string2)) {
                return n;
            }
            ++n3;
            ++n;
        }
        return -1;
    }

    public final String[] getColumnNames() {
        this.a();
        return this.b;
    }

    public final int getCount() {
        this.a();
        return this.d;
    }

    public final Bundle getExtras() {
        this.a();
        try {
            Bundle bundle = this.a.getExtras();
            return bundle;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public final void initialize(BulkCursorDescriptor bulkCursorDescriptor) {
        this.a = bulkCursorDescriptor.cursor;
        this.b = bulkCursorDescriptor.columnNames;
        this.c = bulkCursorDescriptor.wantsAllOnMoveCalls;
        this.d = bulkCursorDescriptor.count;
        if (bulkCursorDescriptor.window != null) {
            this.setWindow(bulkCursorDescriptor.window);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean onMove(int n, int n2) {
        boolean bl = false;
        this.a();
        try {
            if (this.mWindow == null || n2 < this.mWindow.getStartPosition() || n2 >= this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
                this.setWindow(this.a.getWindow(n2));
            } else if (this.c) {
                this.a.onMove(n2);
            }
            if (this.mWindow == null) return bl;
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"BulkCursor", (String)"Unable to get window because the remote process is dead");
            return false;
        }
    }

    public final boolean requery() {
        block3: {
            this.a();
            try {
                this.d = this.a.requery();
                if (this.d == -1) break block3;
                this.mPos = -1;
                this.closeWindow();
                super.requery();
                return true;
            }
            catch (Exception exception) {
                Log.e((String)"BulkCursor", (String)("Unable to requery because the remote process exception " + exception.getMessage()));
                this.deactivate();
                return false;
            }
        }
        this.deactivate();
        return false;
    }

    public final Bundle respond(Bundle bundle) {
        this.a();
        try {
            bundle = this.a.respond(bundle);
            return bundle;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"BulkCursor", (String)"respond() threw RemoteException, returning an empty bundle.", (Throwable)remoteException);
            return Bundle.EMPTY;
        }
    }

    public final void setResultId(String string2) {
        this.e = string2;
    }
}

