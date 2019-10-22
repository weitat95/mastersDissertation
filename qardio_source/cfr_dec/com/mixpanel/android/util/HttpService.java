/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.mixpanel.android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.OfflineMode;
import com.mixpanel.android.util.RemoteService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;

public class HttpService
implements RemoteService {
    private static boolean sIsMixpanelBlocked;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean onOfflineMode(OfflineMode offlineMode) {
        if (offlineMode == null) return false;
        try {
            boolean bl = offlineMode.isOffline();
            if (!bl) return false;
            return true;
        }
        catch (Exception exception) {
            MPLog.v("MixpanelAPI.Message", "Client State should not throw exception, will assume is not on offline mode", exception);
            return false;
        }
    }

    private static byte[] slurp(InputStream inputStream) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[8192];
        while ((n = inputStream.read(arrby, 0, arrby.length)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        byteArrayOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void checkIsMixpanelBlocked() {
        new Thread(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                boolean bl;
                block4: {
                    block3: {
                        try {
                            InetAddress inetAddress = InetAddress.getByName("api.mixpanel.com");
                            InetAddress inetAddress2 = InetAddress.getByName("decide.mixpanel.com");
                            if (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress() || inetAddress2.isLoopbackAddress() || inetAddress2.isAnyLocalAddress()) break block3;
                            bl = false;
                            break block4;
                        }
                        catch (Exception exception) {
                            return;
                        }
                    }
                    bl = true;
                }
                sIsMixpanelBlocked = bl;
                if (sIsMixpanelBlocked) {
                    MPLog.v("MixpanelAPI.Message", "AdBlocker is enabled. Won't be able to use Mixpanel services.");
                }
            }
        }).start();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"MissingPermission"})
    @Override
    public boolean isOnline(Context object, OfflineMode object2) {
        if (sIsMixpanelBlocked || this.onOfflineMode((OfflineMode)object2)) {
            return false;
        }
        try {
            object = ((ConnectivityManager)object.getSystemService("connectivity")).getActiveNetworkInfo();
            if (object == null) {
                MPLog.v("MixpanelAPI.Message", "A default network has not been set so we cannot be certain whether we are offline");
                return true;
            }
        }
        catch (SecurityException securityException) {
            MPLog.v("MixpanelAPI.Message", "Don't have permission to check connectivity, will assume we are online");
            return true;
        }
        {
            boolean bl = object.isConnectedOrConnecting();
            object2 = new StringBuilder().append("ConnectivityManager says we ");
            object = bl ? "are" : "are not";
            MPLog.v("MixpanelAPI.Message", ((StringBuilder)object2).append((String)object).append(" online").toString());
            return bl;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] performRequest(String var1_1, Map<String, Object> var2_5, SSLSocketFactory var3_10) throws RemoteService.ServiceUnavailableException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 17[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

}

