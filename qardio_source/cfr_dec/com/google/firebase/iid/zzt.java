/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.firebase.iid;

import android.os.Bundle;
import com.google.firebase.iid.zzr;

final class zzt
extends zzr<Bundle> {
    zzt(int n, int n2, Bundle bundle) {
        super(n, 1, bundle);
    }

    @Override
    final void zzac(Bundle bundle) {
        Bundle bundle2;
        bundle = bundle2 = bundle.getBundle("data");
        if (bundle2 == null) {
            bundle = Bundle.EMPTY;
        }
        this.finish(bundle);
    }

    @Override
    final boolean zzcje() {
        return false;
    }
}

