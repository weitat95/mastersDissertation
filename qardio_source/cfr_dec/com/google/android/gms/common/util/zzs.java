/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 */
package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import com.google.android.gms.common.util.zzn;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public final class zzs {
    private static String zzget = null;
    private static final int zzgeu = Process.myPid();

    public static String zzamo() {
        if (zzget == null) {
            zzget = zzs.zzcj(zzgeu);
        }
        return zzget;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String zzcj(int n) {
        if (n <= 0) {
            return null;
        }
        StrictMode.ThreadPolicy threadPolicy = StrictMode.allowThreadDiskReads();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new StringBuilder(25).append("/proc/").append(n).append("/cmdline").toString()));
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        String string2 = bufferedReader.readLine().trim();
        zzn.closeQuietly(bufferedReader);
        return string2;
        catch (Throwable throwable) {
            try {
                StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
                throw throwable;
            }
            catch (IOException iOException) {
                block10: {
                    bufferedReader = null;
                    break block10;
                    catch (Throwable throwable2) {
                        BufferedReader bufferedReader2;
                        void var2_4;
                        block11: {
                            bufferedReader2 = null;
                            Throwable throwable3 = throwable2;
                            break block11;
                            catch (Throwable throwable4) {
                                bufferedReader2 = bufferedReader;
                            }
                        }
                        zzn.closeQuietly(bufferedReader2);
                        throw var2_4;
                    }
                    catch (IOException iOException2) {}
                }
                zzn.closeQuietly(bufferedReader);
                return null;
            }
        }
    }
}

