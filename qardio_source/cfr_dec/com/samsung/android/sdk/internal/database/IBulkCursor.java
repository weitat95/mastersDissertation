/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.internal.database;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;

public interface IBulkCursor
extends IInterface {
    public void close() throws RemoteException;

    public void deactivate() throws RemoteException;

    public Bundle getExtras() throws RemoteException;

    public CursorWindow getWindow(int var1) throws RemoteException;

    public void onMove(int var1) throws RemoteException;

    public int requery() throws RemoteException;

    public Bundle respond(Bundle var1) throws RemoteException;
}

