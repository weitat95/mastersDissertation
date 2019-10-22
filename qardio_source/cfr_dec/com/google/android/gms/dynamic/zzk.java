/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface zzk
extends IInterface {
    public Bundle getArguments() throws RemoteException;

    public int getId() throws RemoteException;

    public boolean getRetainInstance() throws RemoteException;

    public String getTag() throws RemoteException;

    public int getTargetRequestCode() throws RemoteException;

    public boolean getUserVisibleHint() throws RemoteException;

    public IObjectWrapper getView() throws RemoteException;

    public boolean isAdded() throws RemoteException;

    public boolean isDetached() throws RemoteException;

    public boolean isHidden() throws RemoteException;

    public boolean isInLayout() throws RemoteException;

    public boolean isRemoving() throws RemoteException;

    public boolean isResumed() throws RemoteException;

    public boolean isVisible() throws RemoteException;

    public void setHasOptionsMenu(boolean var1) throws RemoteException;

    public void setMenuVisibility(boolean var1) throws RemoteException;

    public void setRetainInstance(boolean var1) throws RemoteException;

    public void setUserVisibleHint(boolean var1) throws RemoteException;

    public void startActivity(Intent var1) throws RemoteException;

    public void startActivityForResult(Intent var1, int var2) throws RemoteException;

    public IObjectWrapper zzapx() throws RemoteException;

    public zzk zzapy() throws RemoteException;

    public IObjectWrapper zzapz() throws RemoteException;

    public zzk zzaqa() throws RemoteException;

    public void zzv(IObjectWrapper var1) throws RemoteException;

    public void zzw(IObjectWrapper var1) throws RemoteException;
}

