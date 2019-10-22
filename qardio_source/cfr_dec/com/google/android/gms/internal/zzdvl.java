/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdvm;
import com.google.android.gms.internal.zzdvp;
import com.google.android.gms.internal.zzdvq;
import java.io.PrintStream;
import java.lang.reflect.Field;

public final class zzdvl {
    private static zzdvm zzmag;
    private static int zzmah;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static {
        var2 = zzdvl.zzbqm();
        if (var2 == null) ** GOTO lbl-1000
        try {
            if (var2 >= 19) {
                var1_1 = new zzdvq();
            } else lbl-1000:
            // 2 sources
            {
                if ((var0_4 = Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") == false ? 1 : 0) != 0) {
                    var1_1 = new zzdvp();
                }
                var1_1 = new zza();
            }
        }
        catch (Throwable var1_2) {}
        ** GOTO lbl-1000
        catch (Throwable var1_3) {
            var2 = null;
        }
lbl-1000:
        // 2 sources
        {
            var3_5 = System.err;
            var4_6 = zza.class.getName();
            var3_5.println(new StringBuilder(String.valueOf(var4_6).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(var4_6).append("will be used. The error is: ").toString());
            var1_1.printStackTrace(System.err);
            var1_1 = new zza();
        }
        zzdvl.zzmag = var1_1;
        var0_4 = var2 == null ? 1 : var2;
        zzdvl.zzmah = var0_4;
    }

    public static void zza(Throwable throwable, PrintStream printStream) {
        zzmag.zza(throwable, printStream);
    }

    private static Integer zzbqm() {
        try {
            Integer n = (Integer)Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            return n;
        }
        catch (Exception exception) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            exception.printStackTrace(System.err);
            return null;
        }
    }

    static final class zza
    extends zzdvm {
        zza() {
        }

        @Override
        public final void zza(Throwable throwable, PrintStream printStream) {
            throwable.printStackTrace(printStream);
        }
    }

}

