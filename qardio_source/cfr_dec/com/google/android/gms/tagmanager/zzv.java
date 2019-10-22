/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzw;
import com.google.android.gms.tagmanager.zzx;

final class zzv
implements ContainerHolder {
    private Status mStatus;
    private boolean zzgln;
    private Container zzkdl;
    private Container zzkdm;
    private zzx zzkdn;
    private zzw zzkdo;
    private TagManager zzkdp;

    final String getContainerId() {
        if (this.zzgln) {
            zzdj.e("getContainerId called on a released ContainerHolder.");
            return "";
        }
        return this.zzkdl.getContainerId();
    }

    @Override
    public final Status getStatus() {
        return this.mStatus;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void refresh() {
        synchronized (this) {
            if (this.zzgln) {
                zzdj.e("Refreshing a released ContainerHolder.");
            } else {
                this.zzkdo.zzbdx();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void release() {
        synchronized (this) {
            if (this.zzgln) {
                zzdj.e("Releasing a released ContainerHolder.");
            } else {
                this.zzgln = true;
                this.zzkdp.zzb(this);
                this.zzkdl.release();
                this.zzkdl = null;
                this.zzkdm = null;
                this.zzkdo = null;
                this.zzkdn = null;
            }
            return;
        }
    }

    final String zzbdv() {
        if (this.zzgln) {
            zzdj.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return "";
        }
        return this.zzkdo.zzbdv();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void zzld(String string2) {
        synchronized (this) {
            block6: {
                boolean bl = this.zzgln;
                if (!bl) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            this.zzkdl.zzld(string2);
            return;
        }
    }

    final void zzle(String string2) {
        if (this.zzgln) {
            zzdj.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return;
        }
        this.zzkdo.zzle(string2);
    }
}

