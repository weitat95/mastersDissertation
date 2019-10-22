/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.AdvertisingInfoStrategy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy
implements AdvertisingInfoStrategy {
    private final Context context;

    public AdvertisingInfoServiceStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    /*
     * Exception decompiling
     */
    @Override
    public AdvertisingInfo getAdvertisingInfo() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    private static final class AdvertisingConnection
    implements ServiceConnection {
        private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue(1);
        private boolean retrieved = false;

        private AdvertisingConnection() {
        }

        public IBinder getBinder() {
            if (this.retrieved) {
                Fabric.getLogger().e("Fabric", "getBinder already called");
            }
            this.retrieved = true;
            try {
                IBinder iBinder = this.queue.poll(200L, TimeUnit.MILLISECONDS);
                return iBinder;
            }
            catch (InterruptedException interruptedException) {
                return null;
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.queue.put(iBinder);
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.queue.clear();
        }
    }

    private static final class AdvertisingInterface
    implements IInterface {
        private final IBinder binder;

        public AdvertisingInterface(IBinder iBinder) {
            this.binder = iBinder;
        }

        public IBinder asBinder() {
            return this.binder;
        }

        public String getId() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.binder.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                String string2 = parcel2.readString();
                return string2;
            }
            catch (Exception exception) {
                Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
                return null;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean isLimitAdTrackingEnabled() throws RemoteException {
            boolean bl;
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                parcel.writeInt(1);
                this.binder.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                bl = n != 0;
            }
            catch (Exception exception) {
                Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture Advertising limitAdTracking");
                return false;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }
    }

}

