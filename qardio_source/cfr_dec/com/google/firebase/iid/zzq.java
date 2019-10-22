/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.firebase.iid;

import android.os.Bundle;
import com.google.firebase.iid.zzr;
import com.google.firebase.iid.zzs;

final class zzq
extends zzr<Void> {
    zzq(int n, int n2, Bundle bundle) {
        super(n, 2, bundle);
    }

    @Override
    final void zzac(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            this.finish(null);
            return;
        }
        this.zzb(new zzs(4, "Invalid response to one way request"));
    }

    @Override
    final boolean zzcje() {
        return true;
    }
}

