/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzk;
import com.google.android.gms.dynamic.zzl;
import com.google.android.gms.dynamic.zzn;

public final class zzr
extends zzl {
    private Fragment zzgwp;

    private zzr(Fragment fragment) {
        this.zzgwp = fragment;
    }

    public static zzr zza(Fragment fragment) {
        if (fragment != null) {
            return new zzr(fragment);
        }
        return null;
    }

    @Override
    public final Bundle getArguments() {
        return this.zzgwp.getArguments();
    }

    @Override
    public final int getId() {
        return this.zzgwp.getId();
    }

    @Override
    public final boolean getRetainInstance() {
        return this.zzgwp.getRetainInstance();
    }

    @Override
    public final String getTag() {
        return this.zzgwp.getTag();
    }

    @Override
    public final int getTargetRequestCode() {
        return this.zzgwp.getTargetRequestCode();
    }

    @Override
    public final boolean getUserVisibleHint() {
        return this.zzgwp.getUserVisibleHint();
    }

    @Override
    public final IObjectWrapper getView() {
        return zzn.zzz(this.zzgwp.getView());
    }

    @Override
    public final boolean isAdded() {
        return this.zzgwp.isAdded();
    }

    @Override
    public final boolean isDetached() {
        return this.zzgwp.isDetached();
    }

    @Override
    public final boolean isHidden() {
        return this.zzgwp.isHidden();
    }

    @Override
    public final boolean isInLayout() {
        return this.zzgwp.isInLayout();
    }

    @Override
    public final boolean isRemoving() {
        return this.zzgwp.isRemoving();
    }

    @Override
    public final boolean isResumed() {
        return this.zzgwp.isResumed();
    }

    @Override
    public final boolean isVisible() {
        return this.zzgwp.isVisible();
    }

    @Override
    public final void setHasOptionsMenu(boolean bl) {
        this.zzgwp.setHasOptionsMenu(bl);
    }

    @Override
    public final void setMenuVisibility(boolean bl) {
        this.zzgwp.setMenuVisibility(bl);
    }

    @Override
    public final void setRetainInstance(boolean bl) {
        this.zzgwp.setRetainInstance(bl);
    }

    @Override
    public final void setUserVisibleHint(boolean bl) {
        this.zzgwp.setUserVisibleHint(bl);
    }

    @Override
    public final void startActivity(Intent intent) {
        this.zzgwp.startActivity(intent);
    }

    @Override
    public final void startActivityForResult(Intent intent, int n) {
        this.zzgwp.startActivityForResult(intent, n);
    }

    @Override
    public final IObjectWrapper zzapx() {
        return zzn.zzz(this.zzgwp.getActivity());
    }

    @Override
    public final zzk zzapy() {
        return zzr.zza(this.zzgwp.getParentFragment());
    }

    @Override
    public final IObjectWrapper zzapz() {
        return zzn.zzz(this.zzgwp.getResources());
    }

    @Override
    public final zzk zzaqa() {
        return zzr.zza(this.zzgwp.getTargetFragment());
    }

    @Override
    public final void zzv(IObjectWrapper iObjectWrapper) {
        iObjectWrapper = (View)zzn.zzx(iObjectWrapper);
        this.zzgwp.registerForContextMenu((View)iObjectWrapper);
    }

    @Override
    public final void zzw(IObjectWrapper iObjectWrapper) {
        iObjectWrapper = (View)zzn.zzx(iObjectWrapper);
        this.zzgwp.unregisterForContextMenu((View)iObjectWrapper);
    }
}

