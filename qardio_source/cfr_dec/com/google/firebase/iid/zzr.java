/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.iid.zzs;

abstract class zzr<T> {
    final int what;
    final TaskCompletionSource<T> zzgrq = new TaskCompletionSource();
    final int zzift;
    final Bundle zznzj;

    zzr(int n, int n2, Bundle bundle) {
        this.zzift = n;
        this.what = n2;
        this.zznzj = bundle;
    }

    final void finish(T t) {
        if (Log.isLoggable((String)"MessengerIpcClient", (int)3)) {
            String string2 = String.valueOf(this);
            String string3 = String.valueOf(t);
            Log.d((String)"MessengerIpcClient", (String)new StringBuilder(String.valueOf(string2).length() + 16 + String.valueOf(string3).length()).append("Finishing ").append(string2).append(" with ").append(string3).toString());
        }
        this.zzgrq.setResult(t);
    }

    public String toString() {
        int n = this.what;
        int n2 = this.zzift;
        boolean bl = this.zzcje();
        return new StringBuilder(55).append("Request { what=").append(n).append(" id=").append(n2).append(" oneWay=").append(bl).append("}").toString();
    }

    abstract void zzac(Bundle var1);

    final void zzb(zzs zzs2) {
        if (Log.isLoggable((String)"MessengerIpcClient", (int)3)) {
            String string2 = String.valueOf(this);
            String string3 = String.valueOf(zzs2);
            Log.d((String)"MessengerIpcClient", (String)new StringBuilder(String.valueOf(string2).length() + 14 + String.valueOf(string3).length()).append("Failing ").append(string2).append(" with ").append(string3).toString());
        }
        this.zzgrq.setException(zzs2);
    }

    abstract boolean zzcje();
}

