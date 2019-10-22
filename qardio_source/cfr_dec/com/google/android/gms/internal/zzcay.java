/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface zzcay
extends IInterface {
    public boolean getBooleanFlagValue(String var1, boolean var2, int var3) throws RemoteException;

    public int getIntFlagValue(String var1, int var2, int var3) throws RemoteException;

    public long getLongFlagValue(String var1, long var2, int var4) throws RemoteException;

    public String getStringFlagValue(String var1, String var2, int var3) throws RemoteException;

    public void init(IObjectWrapper var1) throws RemoteException;
}

