/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdvm;
import com.google.android.gms.internal.zzdvn;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

final class zzdvp
extends zzdvm {
    private final zzdvn zzmam = new zzdvn();

    zzdvp() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(Throwable object, PrintStream printStream) {
        ((Throwable)object).printStackTrace(printStream);
        object = this.zzmam.zza((Throwable)object, false);
        if (object == null) {
            return;
        }
        synchronized (object) {
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                Throwable throwable = (Throwable)iterator.next();
                printStream.print("Suppressed: ");
                throwable.printStackTrace(printStream);
            }
            return;
        }
    }
}

