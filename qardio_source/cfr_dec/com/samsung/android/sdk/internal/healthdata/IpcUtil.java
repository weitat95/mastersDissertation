/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.LocalSocket
 *  android.net.LocalSocketAddress
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.internal.healthdata;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Looper;
import android.os.RemoteException;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.internal.healthdata.HealthResultHolderImpl;
import com.samsung.android.sdk.internal.healthdata.HealthResultReceiver;
import com.samsung.android.sdk.internal.healthdata.RemoteResultListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IpcUtil {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void a(String arrby, HealthData object, String string2, InputStream inputStream) {
        byte[] arrby2 = (byte[])((HealthData)object).get(string2);
        if (arrby2 == null) {
            return;
        }
        try {
            if (inputStream.available() >= 10485760) {
                throw new IllegalArgumentException(string2 + "'s size cannot be bigger than 10MB");
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        LocalSocket localSocket = new LocalSocket();
        try {
            block18: {
                block17: {
                    try {
                        int n;
                        String string3 = HealthDataStore.getPlatformPackageName() + ".BlobSocketServer";
                        object = string3;
                        if (HealthDataStore.getMyUserId() != 0L) {
                            object = string3 + "." + HealthDataStore.getMyUserId();
                        }
                        localSocket.connect(new LocalSocketAddress((String)object));
                        object = new DataOutputStream(localSocket.getOutputStream());
                        ((DataOutputStream)object).writeUTF(HealthDataStore.getSocketKey());
                        ((DataOutputStream)object).writeUTF(new String(arrby2, "UTF-8"));
                        if (inputStream == null) break block17;
                        ((DataOutputStream)object).writeInt(-15876749);
                        ((DataOutputStream)object).writeUTF((String)arrby);
                        ((DataOutputStream)object).writeUTF(string2);
                        arrby = new byte[102400];
                        while ((n = inputStream.read(arrby)) > 0) {
                            ((DataOutputStream)object).write(arrby, 0, n);
                        }
                    }
                    catch (IOException iOException) {
                        throw new IllegalStateException("Stream data sending failure", iOException);
                    }
                    inputStream.close();
                    break block18;
                }
                ((DataOutputStream)object).writeInt(0);
            }
            ((DataOutputStream)object).flush();
            return;
        }
        finally {
            localSocket.close();
        }
    }

    public static <T extends HealthResultHolder.BaseResult> HealthResultHolder<T> makeResultHolder(HealthResultReceiver.ForwardAsync forwardAsync, Looper object) {
        object = new HealthResultHolderImpl<T>((Looper)object, forwardAsync){
            private /* synthetic */ HealthResultReceiver.ForwardAsync a;
            {
                this.a = forwardAsync;
                super(looper);
            }

            @Override
            protected final void cancelResult() throws RemoteException {
                this.a.cancel();
            }
        };
        forwardAsync.registerListener(object);
        return object;
    }

    /*
     * Exception decompiling
     */
    public static byte[] receiveBlob(String var0, String var1_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 6 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static InputStream receiveStream(String string2, String string3) {
        localSocket = new LocalSocket();
        try {
            object2 = object = HealthDataStore.getPlatformPackageName() + ".BlobSocketServer";
            if (HealthDataStore.getMyUserId() != 0L) {
                object2 = (String)object + "." + HealthDataStore.getMyUserId();
            }
            localSocket.connect(new LocalSocketAddress((String)object2));
            object2 = new DataOutputStream(localSocket.getOutputStream());
            object = new DataInputStream(localSocket.getInputStream());
            object2.writeUTF(HealthDataStore.getSocketKey());
            object2.writeUTF(string2);
            object2.writeInt(-42382435);
            object2.writeUTF(string3);
            object2.flush();
            n = object.readInt();
            if (n > 0) {
                return object;
            }
            ** GOTO lbl23
        }
        catch (Exception exception) {
            localSocket.close();
            throw new IllegalStateException("Stream data receiving failure", exception);
lbl23:
            // 2 sources
            localSocket.close();
            return null;
        }
        catch (IOException iOException) {
            return null;
        }
        {
            catch (IOException iOException) {
                throw new IllegalStateException("Stream data receiving failure", exception);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public static void sendBlob(String var0, HealthData var1_4, String var2_6) throws RemoteException {
        var3_7 = var1_4.getBlob(var2_6);
        if (var3_7 == null) ** GOTO lbl10
        var4_8 = new ByteArrayInputStream((byte[])var3_7);
        var3_7 = null;
        IpcUtil.a(var0, var1_4, var2_6, var4_8);
        var4_8.close();
lbl10:
        // 2 sources
        return;
        catch (Throwable var0_1) {
            var3_7 = var0_1;
            try {
                throw var0_1;
            }
            catch (Throwable var0_2) {
                if (var3_7 == null) ** GOTO lbl25
                try {
                    try {
                        var4_8.close();
                    }
                    catch (Throwable var1_5) {
                        var3_7.addSuppressed(var1_5);
                    }
lbl20:
                    // 3 sources
                    do {
                        throw var0_2;
                        break;
                    } while (true);
lbl25:
                    // 1 sources
                    var4_8.close();
                    ** continue;
                }
                catch (IOException var0_3) {
                    return;
                }
            }
        }
    }

    public static void sendStream(String string2, HealthData healthData, String string3) throws RemoteException {
        IpcUtil.a(string2, healthData, string3, healthData.getInputStream(string3));
    }

}

