/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;

public class BooleanResult
implements Result {
    private final Status mStatus;
    private final boolean zzfmd;

    public BooleanResult(Status status, boolean bl) {
        this.mStatus = zzbq.checkNotNull(status, "Status must not be null");
        this.zzfmd = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof BooleanResult)) {
                    return false;
                }
                object = (BooleanResult)object;
                if (!this.mStatus.equals(((BooleanResult)object).mStatus) || this.zzfmd != ((BooleanResult)object).zzfmd) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public Status getStatus() {
        return this.mStatus;
    }

    public boolean getValue() {
        return this.zzfmd;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int hashCode() {
        int n;
        int n2 = this.mStatus.hashCode();
        if (this.zzfmd) {
            n = 1;
            do {
                return n + (n2 + 527) * 31;
                break;
            } while (true);
        }
        n = 0;
        return n + (n2 + 527) * 31;
    }
}

