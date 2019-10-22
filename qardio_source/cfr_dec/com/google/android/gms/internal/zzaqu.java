/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqv;
import com.google.android.gms.internal.zzaqw;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class zzaqu
extends zzaqa {
    private volatile String zzdrv;
    private Future<String> zzdve;

    protected zzaqu(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    static /* synthetic */ String zza(zzaqu zzaqu2) {
        return zzaqu2.zzyn();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private final String zzbn(Context var1_1) {
        block32: {
            block33: {
                block30: {
                    block31: {
                        zzbq.zzgn("ClientId should be loaded from worker thread");
                        var4_15 = var3_13 = var1_1.openFileInput("gaClientId");
                        var5_16 = new byte[36];
                        var4_15 = var3_13;
                        var2_21 = var3_13.read(var5_16, 0, 36);
                        var4_15 = var3_13;
                        if (var3_13.available() <= 0) break block30;
                        var4_15 = var3_13;
                        this.zzdx("clientId file seems corrupted, deleting it.");
                        var4_15 = var3_13;
                        var3_13.close();
                        var4_15 = var3_13;
                        var1_1.deleteFile("gaClientId");
                        if (var3_13 == null) break block31;
                        try {
                            var3_13.close();
                        }
                        catch (IOException var1_2) {
                            this.zze("Failed to close client id reading stream", var1_2);
                            return null;
                        }
                    }
lbl21:
                    // 4 sources
                    do {
                        return null;
                        break;
                    } while (true);
                }
                if (var2_21 >= 14) break block33;
                var4_15 = var3_13;
                this.zzdx("clientId file is empty, deleting it.");
                var4_15 = var3_13;
                var3_13.close();
                var4_15 = var3_13;
                var1_1.deleteFile("gaClientId");
                if (var3_13 == null) ** GOTO lbl21
                try {
                    var3_13.close();
                    return null;
                }
                catch (IOException var1_3) {
                    this.zze("Failed to close client id reading stream", var1_3);
                    return null;
                }
            }
            var4_15 = var3_13;
            var3_13.close();
            var4_15 = var3_13;
            var5_17 = new String(var5_16, 0, var2_21);
            var4_15 = var3_13;
            this.zza("Read client id from disk", var5_17);
            if (var3_13 == null) break block32;
            try {
                var3_13.close();
            }
            catch (IOException var1_4) {
                this.zze("Failed to close client id reading stream", var1_4);
                ** continue;
            }
        }
lbl58:
        // 2 sources
        do {
            return var5_17;
            break;
        } while (true);
        catch (FileNotFoundException var1_5) {
            var1_6 = null;
lbl62:
            // 2 sources
            do {
                if (var1_6 == null) ** GOTO lbl21
                try {
                    var1_6.close();
                    return null;
                }
                catch (IOException var1_7) {
                    this.zze("Failed to close client id reading stream", var1_7);
                    return null;
                }
                break;
            } while (true);
        }
        catch (IOException var5_18) {
            var3_13 = null;
lbl72:
            // 2 sources
            do {
                var4_15 = var3_13;
                this.zze("Error reading client id file, deleting it", var5_19);
                var4_15 = var3_13;
                var1_1.deleteFile("gaClientId");
                if (var3_13 == null) ** continue;
                try {
                    var3_13.close();
                    return null;
                }
                catch (IOException var1_8) {
                    this.zze("Failed to close client id reading stream", var1_8);
                    return null;
                }
                break;
            } while (true);
        }
        catch (Throwable var1_9) {
            var4_15 = null;
lbl88:
            // 2 sources
            do {
                if (var4_15 != null) {
                    var4_15.close();
                }
lbl92:
                // 4 sources
                do {
                    throw var1_10;
                    break;
                } while (true);
                catch (IOException var3_14) {
                    this.zze("Failed to close client id reading stream", var3_14);
                    ** continue;
                }
                break;
            } while (true);
        }
        {
            catch (Throwable var1_11) {
                ** continue;
            }
        }
        catch (IOException var5_20) {
            ** continue;
        }
        catch (FileNotFoundException var1_12) {
            var1_6 = var3_13;
            ** continue;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final boolean zzs(Context object, String string2) {
        boolean bl = false;
        zzbq.zzgm(string2);
        zzbq.zzgn("ClientId should be saved from worker thread");
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        FileOutputStream fileOutputStream3 = null;
        Object object2 = fileOutputStream3;
        Object object3 = fileOutputStream;
        Object object4 = fileOutputStream2;
        this.zza("Storing clientId", string2);
        object2 = fileOutputStream3;
        object3 = fileOutputStream;
        object4 = fileOutputStream2;
        object2 = object = object.openFileOutput("gaClientId", 0);
        object3 = object;
        object4 = object;
        ((FileOutputStream)object).write(string2.getBytes());
        if (object == null) return true;
        try {
            ((FileOutputStream)object).close();
            return true;
        }
        catch (IOException iOException) {
            this.zze("Failed to close clientId writing stream", iOException);
        }
        return true;
        catch (FileNotFoundException fileNotFoundException) {
            object4 = object2;
            this.zze("Error creating clientId file", fileNotFoundException);
            if (object2 == null) return bl;
            {
                catch (Throwable throwable) {
                    if (object4 == null) throw throwable;
                    try {
                        ((FileOutputStream)object4).close();
                    }
                    catch (IOException iOException2) {
                        this.zze("Failed to close clientId writing stream", iOException2);
                        throw throwable;
                    }
                    throw throwable;
                }
            }
            try {
                ((FileOutputStream)object2).close();
                return false;
            }
            catch (IOException iOException) {
                this.zze("Failed to close clientId writing stream", iOException);
                return false;
            }
            catch (IOException iOException) {
                object4 = object3;
                this.zze("Error writing to clientId file", iOException);
                if (object3 == null) return bl;
                try {
                    ((FileOutputStream)object3).close();
                    return false;
                }
                catch (IOException iOException3) {
                    this.zze("Failed to close clientId writing stream", iOException3);
                    return false;
                }
            }
        }
    }

    private final String zzyn() {
        String string2;
        String string3 = string2 = UUID.randomUUID().toString().toLowerCase();
        try {
            if (!this.zzs(this.zzwv().getContext(), string2)) {
                string3 = "0";
            }
            return string3;
        }
        catch (Exception exception) {
            this.zze("Error saving clientId file", exception);
            return "0";
        }
    }

    @Override
    protected final void zzvf() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final String zzyk() {
        this.zzxf();
        synchronized (this) {
            Object object;
            if (this.zzdrv == null) {
                this.zzdve = this.zzwv().zza(new zzaqv(this));
            }
            if ((object = this.zzdve) == null) return this.zzdrv;
            try {
                this.zzdrv = this.zzdve.get();
            }
            catch (InterruptedException interruptedException) {
                this.zzd("ClientId loading or generation was interrupted", interruptedException);
                this.zzdrv = "0";
            }
            catch (ExecutionException executionException) {
                this.zze("Failed to load or generate client id", executionException);
                this.zzdrv = "0";
            }
            if (this.zzdrv == null) {
                this.zzdrv = "0";
            }
            this.zza("Loaded clientId", this.zzdrv);
            this.zzdve = null;
            return this.zzdrv;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final String zzyl() {
        synchronized (this) {
            this.zzdrv = null;
            this.zzdve = this.zzwv().zza(new zzaqw(this));
            return this.zzyk();
        }
    }

    final String zzym() {
        String string2;
        String string3 = string2 = this.zzbn(this.zzwv().getContext());
        if (string2 == null) {
            string3 = this.zzyn();
        }
        return string3;
    }
}

