/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.telephony.TelephonyManager
 *  android.util.Log
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package com.samsung.android.sdk.healthdata;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.healthdata.IHealth;
import com.samsung.android.sdk.internal.healthdata.HealthResultReceiver;
import com.samsung.android.sdk.internal.healthdata.IpcUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class HealthDataStore {
    private static String b = "com.sec.android.app.shealth";
    private static String g;
    private static long h;
    private static long j;
    HealthResultHolder<HealthResultHolder.BaseResult> a = null;
    private final ConnectionListener c;
    private IHealth d;
    private final Context e;
    private final b f = new b(this);
    private Boolean i = null;
    private long k = j;
    private final ServiceConnection l = new ServiceConnection(this){
        private /* synthetic */ HealthDataStore a;
        {
            this.a = healthDataStore;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void onServiceConnected(ComponentName componentName, IBinder object) {
            int n;
            block17: {
                Log.d((String)"HealthDataStore", (String)"Service for HealthDataStore is connected");
                object = IHealth.Stub.asInterface((IBinder)object);
                componentName = new Bundle();
                componentName.putString("packageName", this.a.e.getPackageName());
                componentName.putInt("clientVersion", 1004000);
                if (this.a.e instanceof Activity) {
                    componentName.putInt("userPasswordInputMode", 0);
                } else {
                    componentName.putInt("userPasswordInputMode", 1);
                }
                try {
                    if (this.a.b() >= 4600000) {
                        componentName = object.getConnectionResult2((Bundle)componentName);
                        break block17;
                    }
                    componentName = object.getConnectionResult(this.a.e.getPackageName(), 1004000);
                }
                catch (RemoteException remoteException) {
                    if (this.a.c != null) {
                        this.a.c.onConnectionFailed(new HealthConnectionErrorResult(0, false));
                        this.a.a = null;
                    }
                    componentName = null;
                }
            }
            if (componentName != null) {
                n = componentName.getInt("result", 0);
                g = componentName.getString("socketKey");
                h = componentName.getLong("myUserId", 0L);
            } else {
                n = 0;
            }
            switch (n) {
                default: {
                    Log.d((String)"HealthDataStore", (String)("HealthConnectionErrorResult code : " + n));
                    this.a.disconnectService();
                    this.a.f.sendEmptyMessageDelayed(n, 2L);
                    return;
                }
                case -1: {
                    if (this.a.c == null) return;
                    {
                        this.a.d = (IHealth)object;
                        this.a.c.onConnected();
                        this.a.a = null;
                        return;
                    }
                }
                case -3: {
                    Log.d((String)"HealthDataStore", (String)"User password popup is required");
                    Message message = new Message();
                    message.what = n;
                    message.setData((Bundle)componentName);
                    this.a.f.sendMessageDelayed(message, 2L);
                    break;
                }
                case -2: 
            }
            try {
                HealthDataStore.a(this.a, this.a.k, (IHealth)object);
                return;
            }
            catch (RemoteException remoteException) {
                if (this.a.c == null) return;
                this.a.c.onConnectionFailed(new HealthConnectionErrorResult(0, false));
                this.a.a = null;
                return;
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d((String)"HealthDataStore", (String)"Service for HealthDataStore is disconnected");
            this.a.d = null;
            if (this.a.c != null) {
                this.a.c.onDisconnected();
                this.a.a = null;
            }
        }
    };

    static {
        j = 60000L;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public HealthDataStore(Context context, ConnectionListener connectionListener) {
        this.e = context;
        this.c = connectionListener;
        context = this.e.getPackageManager().getApplicationInfo((String)this.e.getPackageName(), (int)128).metaData;
        if (context == null) return;
        try {
            if (!"dev".equalsIgnoreCase(context.getString("com.samsung.android.health.platform_type"))) return;
            b = "com.samsung.android.sdkapp.health";
            return;
        }
        catch (NullPointerException nullPointerException) {
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
    }

    static /* synthetic */ HealthResultHolder a(HealthDataStore healthDataStore, long l, IHealth iHealth) throws RemoteException {
        Log.d((String)"HealthDataStore", (String)"Waiting for initialization of Health framework ...");
        if (healthDataStore.a != null) {
            healthDataStore.a.cancel();
        }
        HealthResultReceiver.ForwardAsync forwardAsync = new HealthResultReceiver.ForwardAsync();
        HealthResultHolder healthResultHolder = IpcUtil.makeResultHolder(forwardAsync, Looper.myLooper());
        iHealth.waitForInit2(healthDataStore.e.getPackageName(), forwardAsync, l);
        healthDataStore.a = healthResultHolder;
        healthDataStore.a.setResultListener(new HealthResultHolder.ResultListener<HealthResultHolder.BaseResult>(healthDataStore, iHealth){
            private /* synthetic */ IHealth a;
            private /* synthetic */ HealthDataStore b;
            {
                this.b = healthDataStore;
                this.a = iHealth;
            }

            @Override
            public final void onResult(HealthResultHolder.BaseResult baseResult) {
                if (baseResult.getStatus() == 1) {
                    if (this.b.c != null) {
                        this.b.d = this.a;
                        this.b.c.onConnected();
                        this.b.a = null;
                    }
                    return;
                }
                this.b.f.sendEmptyMessage(7);
            }
        });
        iHealth = new Message();
        ((Message)iHealth).what = 5;
        healthDataStore.f.sendMessageDelayed((Message)iHealth, l);
        return healthDataStore.a;
    }

    /*
     * Enabled aggressive block sorting
     */
    static /* synthetic */ void a(HealthDataStore healthDataStore, int n) {
        block5: {
            block4: {
                boolean bl;
                if (healthDataStore.c == null) break block4;
                Log.d((String)"HealthDataStore", (String)("Trying to connect with Health Service fails (error code: " + n + ")"));
                if (n != 2 && n != 4 || !(bl = healthDataStore.e != null && healthDataStore.e.checkCallingOrSelfPermission("android.permission.INTERNET") == 0)) break block5;
                Log.d((String)"HealthDataStore", (String)"Check SupportedDevice");
                new a(healthDataStore, n, 0).execute((Object[])new Void[0]);
            }
            return;
        }
        HealthConnectionErrorResult healthConnectionErrorResult = new HealthConnectionErrorResult(n, false);
        if (n == 2 || n == 4 || n == 6) {
            healthConnectionErrorResult.setPackageManager(healthDataStore.e.getPackageManager());
        }
        healthDataStore.c.onConnectionFailed(healthConnectionErrorResult);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean a(URL var0) {
        block33: {
            var3_6 = var4_4 = null;
            try {
                var6_10 = XmlPullParserFactory.newInstance().newPullParser();
                var3_6 = var4_4;
                var3_6 = var0 = var0.openStream();
                try {
                    block31: {
                        block32: {
                            var6_10.setInput((InputStream)var0, null);
                            var3_6 = var0;
                            var1_11 = var6_10.getEventType();
                            var4_4 = "";
                            var2_12 = false;
lbl13:
                            // 2 sources
                            do {
                                if (var1_11 != 1) {
                                    block30: {
                                        var5_13 = var4_4;
                                        if (var1_11 == 2) {
                                            var3_6 = var0;
                                            var7_14 = var6_10.getName();
                                            var3_6 = var0;
                                            if ("appId".equals(var7_14)) {
                                                var5_13 = var4_4;
                                                var3_6 = var0;
                                                if (var6_10.next() == 4) {
                                                    var3_6 = var0;
                                                    var6_10.getText();
                                                    var5_13 = var4_4;
                                                }
                                            } else {
                                                var3_6 = var0;
                                                if ("resultCode".equals(var7_14)) {
                                                    var5_13 = var4_4;
                                                    var3_6 = var0;
                                                    if (var6_10.next() == 4) {
                                                        var3_6 = var0;
                                                        var5_13 = var6_10.getText();
                                                    }
                                                } else {
                                                    var3_6 = var0;
                                                    if ("resultMsg".equals(var7_14)) {
                                                        var5_13 = var4_4;
                                                        var3_6 = var0;
                                                        if (var6_10.next() == 4) {
                                                            var3_6 = var0;
                                                            var6_10.getText();
                                                            var5_13 = var4_4;
                                                        }
                                                    } else {
                                                        var3_6 = var0;
                                                        if ("version".equals(var7_14)) {
                                                            var5_13 = var4_4;
                                                            var3_6 = var0;
                                                            if (var6_10.next() == 4) {
                                                                var3_6 = var0;
                                                                var6_10.getText();
                                                                var5_13 = var4_4;
                                                            }
                                                            break block30;
                                                        }
                                                        var5_13 = var4_4;
                                                        var3_6 = var0;
                                                        if (!"versionCode".equals(var7_14)) break block30;
                                                        var5_13 = var4_4;
                                                        var3_6 = var0;
                                                        if (var6_10.next() != 4) break block30;
                                                        var3_6 = var0;
                                                        String.format("%010d", new Object[]{Integer.parseInt(var6_10.getText())});
                                                        var5_13 = var4_4;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (var1_11 != 3) break block31;
                                    var3_6 = var0;
                                    if (!var6_10.getName().equals("appInfo")) break block31;
                                    var3_6 = var0;
                                    if ("2".equals(var5_13)) break block32;
                                    var3_6 = var0;
                                    if ("1".equals(var5_13)) break block32;
                                    var2_12 = false;
                                    break block31;
                                }
                                if (var0 == null) return var2_12;
                                try {
                                    var0.close();
                                    return var2_12;
                                }
                                catch (IOException var0_2) {
                                    return var2_12;
                                }
                                break;
                            } while (true);
                            catch (Exception var3_9) {
                                var0 = null;
                                ** GOTO lbl-1000
                            }
                        }
                        var2_12 = true;
                    }
                    var3_6 = var0;
                    var1_11 = var6_10.next();
                    var4_4 = var5_13;
                    ** continue;
                }
                catch (Exception var3_7) lbl-1000:
                // 2 sources
                {
                    try {
                        Log.e((String)"HealthDataStore", (String)"Failed to check update", (Throwable)var3_6);
                        if (var0 == null) return false;
                    }
                    catch (Throwable var4_5) {
                        var3_6 = var0;
                        var0 = var4_5;
                        break block33;
                    }
                    try {
                        var0.close();
                        return false;
                    }
                    catch (IOException var0_3) {
                        return false;
                    }
                }
            }
            catch (Throwable var0_1) {}
        }
        if (var3_6 == null) throw var0;
        try {
            var3_6.close();
        }
        catch (IOException var3_8) {
            throw var0;
        }
        throw var0;
    }

    /*
     * WARNING - void declaration
     */
    private static boolean a(Signature[] arrsignature) {
        Signature signature = new Signature("308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a");
        Signature signature2 = new Signature("308201e53082014ea00302010202044f54468b300d06092a864886f70d01010505003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3132303330353034353232375a170d3432303232363034353232375a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730819f300d06092a864886f70d010101050003818d00308189028181008a53be36d02befe1d152724281630bd1c42eff0edf5fdca8eb944f536ab3f54dca9b22cfb421b37706a4ad259101815723202b359250cf6c59905032798273462bfa3f9f1881f7475ee5b25849edefac81085815f42383a44cb2be1bfd5c1f049ef42f5818f35fe0b1131c769cee347d558395a5fa87c3d425b2b9c819cf91870203010001300d06092a864886f70d0101050500038181000512992268a01e0941481931f3f9b6647fbe25ee0bc9648f35d56c55f8cfa6c935fb3d435125fd60ef566769ac7e64fe2823409461ca7a04570c43baaab3fb877bf3a6a8dd9ef7e69944f65b0e5e36f2ac2bf085fdeda063898855ea2ce84c60655d824844fe1659a77c12604c3fb84d41df6f1a7705a1b9962ac2fdc9933122");
        Signature signature3 = new Signature("308204a830820390a003020102020900936eacbe07f201df300d06092a864886f70d0101050500308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d301e170d3038303232393031333334365a170d3335303731373031333334365a308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100d6931904dec60b24b1edc762e0d9d8253e3ecd6ceb1de2ff068ca8e8bca8cd6bd3786ea70aa76ce60ebb0f993559ffd93e77a943e7e83d4b64b8e4fea2d3e656f1e267a81bbfb230b578c20443be4c7218b846f5211586f038a14e89c2be387f8ebecf8fcac3da1ee330c9ea93d0a7c3dc4af350220d50080732e0809717ee6a053359e6a694ec2cb3f284a0a466c87a94d83b31093a67372e2f6412c06e6d42f15818dffe0381cc0cd444da6cddc3b82458194801b32564134fbfde98c9287748dbf5676a540d8154c8bbca07b9e247553311c46b9af76fdeeccc8e69e7c8a2d08e782620943f99727d3c04fe72991d99df9bae38a0b2177fa31d5b6afee91f020103a381fc3081f9301d0603551d0e04160414485900563d272c46ae118605a47419ac09ca8c113081c90603551d230481c13081be8014485900563d272c46ae118605a47419ac09ca8c11a1819aa48197308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d820900936eacbe07f201df300c0603551d13040530030101ff300d06092a864886f70d010105050003820101007aaf968ceb50c441055118d0daabaf015b8a765a27a715a2c2b44f221415ffdace03095abfa42df70708726c2069e5c36eddae0400be29452c084bc27eb6a17eac9dbe182c204eb15311f455d824b656dbe4dc2240912d7586fe88951d01a8feb5ae5a4260535df83431052422468c36e22c2a5ef994d61dd7306ae4c9f6951ba3c12f1d1914ddc61f1a62da2df827f603fea5603b2c540dbd7c019c36bab29a4271c117df523cdbc5f3817a49e0efa60cbd7f74177e7a4f193d43f4220772666e4c4d83e1bd5a86087cf34f2dec21e245ca6c2bb016e683638050d2c430eea7c26a1c49d3760a58ab7f1a82cc938b4831384324bd0401fa12163a50570e684d");
        Signature signature4 = new Signature("308204a830820390a003020102020900b3998086d056cffa300d06092a864886f70d0101040500308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d301e170d3038303431353232343035305a170d3335303930313232343035305a308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009c780592ac0d5d381cdeaa65ecc8a6006e36480c6d7207b12011be50863aabe2b55d009adf7146d6f2202280c7cd4d7bdb26243b8a806c26b34b137523a49268224904dc01493e7c0acf1a05c874f69b037b60309d9074d24280e16bad2a8734361951eaf72a482d09b204b1875e12ac98c1aa773d6800b9eafde56d58bed8e8da16f9a360099c37a834a6dfedb7b6b44a049e07a269fccf2c5496f2cf36d64df90a3b8d8f34a3baab4cf53371ab27719b3ba58754ad0c53fc14e1db45d51e234fbbe93c9ba4edf9ce54261350ec535607bf69a2ff4aa07db5f7ea200d09a6c1b49e21402f89ed1190893aab5a9180f152e82f85a45753cf5fc19071c5eec827020103a381fc3081f9301d0603551d0e041604144fe4a0b3dd9cba29f71d7287c4e7c38f2086c2993081c90603551d230481c13081be80144fe4a0b3dd9cba29f71d7287c4e7c38f2086c299a1819aa48197308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d820900b3998086d056cffa300c0603551d13040530030101ff300d06092a864886f70d01010405000382010100572551b8d93a1f73de0f6d469f86dad6701400293c88a0cd7cd778b73dafcc197fab76e6212e56c1c761cfc42fd733de52c50ae08814cefc0a3b5a1a4346054d829f1d82b42b2048bf88b5d14929ef85f60edd12d72d55657e22e3e85d04c831d613d19938bb8982247fa321256ba12d1d6a8f92ea1db1c373317ba0c037f0d1aff645aef224979fba6e7a14bc025c71b98138cef3ddfc059617cf24845cf7b40d6382f7275ed738495ab6e5931b9421765c491b72fb68e080dbdb58c2029d347c8b328ce43ef6a8b15533edfbe989bd6a48dd4b202eda94c6ab8dd5b8399203daae2ed446232e4fe9bd961394c6300e5138e3cfd285e6e4e483538cb8b1b357");
        Signature signature5 = new Signature("308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158");
        Signature[] arrsignature22 = new Signature[]{signature, signature5};
        if (Build.TYPE.equalsIgnoreCase("eng") || Build.TYPE.equalsIgnoreCase("userdebug")) {
            Log.d((String)"HealthDataStore", (String)" SIGNATURES_ENG ");
            Signature[] arrsignature2 = new Signature[]{signature, signature5, signature2, signature3, signature4};
        }
        int n = arrsignature.length;
        for (int i = 0; i < n; ++i) {
            void var5_8;
            signature = arrsignature[i];
            int n2 = ((void)var5_8).length;
            for (int j = 0; j < n2; ++j) {
                if (!var5_8[j].equals((Object)signature)) continue;
                Log.d((String)"HealthDataStore", (String)" signature matched ");
                return true;
            }
        }
        if (arrsignature.length > 0) {
            for (Signature signature6 : arrsignature) {
                n = signature6.toCharsString().length();
                Log.d((String)"HealthDataStore", (String)(" signature : " + signature6.toCharsString().substring(n - 5, n)));
            }
        } else {
            Log.d((String)"HealthDataStore", (String)" no signatures");
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int b() {
        int n = -1;
        PackageManager packageManager = this.e.getPackageManager();
        if (packageManager == null) return n;
        try {
            return packageManager.getPackageInfo((String)"com.sec.android.app.shealth", (int)0).versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean c() {
        if (this.e == null) {
            return false;
        }
        if (this.i != null) {
            return this.i;
        }
        CharSequence charSequence = Build.MODEL;
        Object object = charSequence;
        if (((String)charSequence).startsWith("OMAP_SS")) {
            object = HealthDataStore.d();
        }
        charSequence = object;
        if (((String)object).startsWith("SAMSUNG-")) {
            charSequence = ((String)object).substring(8);
        }
        object = "http://hub.samsungapps.com/product/appCheck.as?" + "appInfo=com.sec.android.app.shealth@0";
        object = (String)object + "&deviceId=" + (String)charSequence;
        charSequence = new StringBuilder().append((String)object).append("&mnc=");
        object = this.e != null && (object = (TelephonyManager)this.e.getSystemService("phone")) != null && (object = object.getSimOperator()) != null && ((String)object).length() != 0 ? ((String)object).substring(3) : "";
        object = ((StringBuilder)charSequence).append((String)object).toString();
        CharSequence charSequence2 = new StringBuilder().append((String)object).append("&csc=");
        charSequence = "";
        object = charSequence;
        if (HealthDataStore.e()) {
            String string2 = HealthDataStore.f();
            object = charSequence;
            if (string2 != null) {
                object = charSequence;
                if (!string2.equalsIgnoreCase("FAIL")) {
                    object = string2.substring(0, 3);
                }
            }
        }
        object = ((StringBuilder)charSequence2).append((String)object).toString();
        object = (String)object + "&openApi=" + String.valueOf(Build.VERSION.SDK_INT);
        if (new File("mnt/sdcard/pd.test").exists()) {
            object = (String)object + "&pd=1";
            charSequence = new StringBuilder().append((String)object);
            object = "&mcc=000";
        } else {
            object = (String)object + "&pd=";
            charSequence = new StringBuilder().append((String)object).append("&mcc=");
            if (this.e == null) {
                object = "";
            } else {
                object = (TelephonyManager)this.e.getSystemService("phone");
                if (object == null) {
                    object = "";
                } else {
                    charSequence2 = object.getSimOperator();
                    switch (object.getPhoneType()) {
                        default: {
                            object = object.getSimOperator();
                            object = object != null && ((String)object).length() != 0 ? ((String)object).substring(0, 3) : "";
                        }
                        case 0: {
                            if (charSequence2 != null && ((String)charSequence2).length() != 0) {
                                object = ((String)charSequence2).substring(0, 3);
                                break;
                            }
                            object = "";
                            break;
                        }
                    }
                }
            }
        }
        object = ((StringBuilder)charSequence).append((String)object).toString();
        Log.d((String)"HealthDataStore", (String)("Server URL : " + (String)object));
        try {
            object = new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {
            Boolean bl;
            this.i = bl = Boolean.valueOf(false);
            return bl;
        }
        this.i = object = Boolean.valueOf(HealthDataStore.a((URL)object));
        return (Boolean)object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String d() {
        Object object;
        String string2;
        block11: {
            string2 = "";
            File file = new File("/system/version");
            object = string2;
            if (!file.isFile()) return object;
            byte[] arrby = new byte[128];
            try {
                object = new FileInputStream(file);
            }
            catch (FileNotFoundException fileNotFoundException) {
                return "";
            }
            int n = ((InputStream)object).read(arrby);
            if (n <= 0) break block11;
            string2 = new String(arrby, 0, n);
        }
        ((InputStream)object).close();
        return string2;
        {
            catch (IOException iOException) {
                return string2;
            }
        }
        catch (Exception exception) {
            try {
                ((InputStream)object).close();
                return "";
            }
            catch (IOException iOException) {
                return "";
            }
        }
        catch (Throwable throwable) {
            try {
                ((InputStream)object).close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            throw throwable;
        }
    }

    private static boolean e() {
        File file = new File("/system/csc/sales_code.dat");
        try {
            boolean bl = file.exists();
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static String f() {
        block18: {
            var2 = null;
            var1_1 = new File("/system/csc/sales_code.dat");
            var0_4 = var2;
            if (!var1_1.isFile()) ** GOTO lbl14
            var0_5 = new byte[20];
            var1_1 = new FileInputStream((File)var1_1);
            try {
                if (var1_1.read(var0_5) <= 0) break block18;
                var0_6 = new String(var0_5);
            }
            catch (Throwable var0_16) {
                ** continue;
            }
            catch (IOException var0_17) {
                ** continue;
            }
lbl11:
            // 2 sources
            do {
                var1_1.close();
lbl14:
                // 3 sources
                do {
                    return var0_8;
                    break;
                } while (true);
                break;
            } while (true);
        }
        var0_9 = "FAIL";
        ** while (true)
        catch (IOException var0_10) {
            var1_1 = null;
lbl21:
            // 2 sources
            do {
                var0_12 = var2;
                if (var1_1 == null) ** continue;
                try {
                    var1_1.close();
                    return null;
                }
                catch (IOException var0_13) {
                    return null;
                }
                break;
            } while (true);
        }
        catch (Throwable var0_14) {
            var1_1 = null;
lbl31:
            // 2 sources
            do {
                if (var1_1 != null) {
                    var1_1.close();
                }
lbl35:
                // 4 sources
                do {
                    throw var0_15;
                    break;
                } while (true);
                break;
            } while (true);
        }
        catch (IOException var1_2) {
            return var0_7;
        }
        {
            catch (IOException var1_3) {
                ** continue;
            }
        }
    }

    public static IHealth getInterface(HealthDataStore healthDataStore) {
        if (healthDataStore == null) {
            throw new IllegalStateException("HealthDataStore is null");
        }
        if (healthDataStore.d == null) {
            throw new IllegalStateException("Health data service is not connected");
        }
        return healthDataStore.d;
    }

    public static long getMyUserId() {
        return h;
    }

    public static String getPlatformPackageName() {
        return b;
    }

    public static String getSocketKey() {
        return g;
    }

    final Context a() {
        return this.e;
    }

    public void connectService() {
        this.connectService(j);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void connectService(long l) {
        Intent intent;
        block19: {
            if (this.e == null) {
                throw new IllegalStateException("Context is not specified(null)");
            }
            if (!HealthDataService.a) {
                throw new IllegalStateException("HealthDataService is not initialized correctly");
            }
            if (!"com.samsung.android.sdkapp.health".equals(b)) {
                intent = this.e.getPackageManager().getPackageInfo(b, 64);
                String string2 = this.e.getPackageName();
                if (b.equals(string2)) break block19;
                try {
                    if (!HealthDataStore.a(intent.signatures)) {
                        this.f.sendEmptyMessageDelayed(8, 2L);
                        return;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    Log.e((String)"HealthDataStore", (String)exception.toString());
                    this.f.sendEmptyMessageDelayed(8, 2L);
                    return;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    this.f.sendEmptyMessageDelayed(2, 2L);
                    return;
                }
                catch (Exception exception) {
                    throw new IllegalStateException("Context is not valid. " + exception.toString());
                }
            }
        }
        intent = new Intent("com.samsung.android.sdk.healthdata.IHealthDataStore");
        intent.setPackage(b);
        try {
            boolean bl = this.e.bindService(intent, this.l, 65);
            if (bl) {
                this.k = l;
                return;
            }
        }
        catch (Exception exception) {
            throw new IllegalStateException("Context is not valid. " + exception.toString());
        }
        try {
            intent = this.e.getPackageManager().getPackageInfo(b, 0);
            if (intent.versionCode < 4000000) {
                this.f.sendEmptyMessageDelayed(4, 2L);
                return;
            }
            if (intent.applicationInfo.enabled) {
                this.f.sendEmptyMessageDelayed(1, 2L);
                return;
            }
            this.f.sendEmptyMessageDelayed(6, 2L);
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            this.f.sendEmptyMessageDelayed(2, 2L);
            return;
        }
        catch (Exception exception) {
            throw new IllegalStateException("Context is not valid. " + exception.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void disconnectService() {
        if (this.e == null) return;
        try {
            this.e.unbindService(this.l);
            return;
        }
        catch (NullPointerException var1_1) {}
        ** GOTO lbl-1000
        catch (IllegalArgumentException var1_2) {}
lbl-1000:
        // 2 sources
        {
            Log.e((String)"HealthDataStore", (String)"disconnectService: Context instance is invalid");
            return;
        }
    }

    public static interface ConnectionListener {
        public void onConnected();

        public void onConnectionFailed(HealthConnectionErrorResult var1);

        public void onDisconnected();
    }

    final class a
    extends AsyncTask<Void, Void, Boolean> {
        private final int a;
        private /* synthetic */ HealthDataStore b;

        private a(HealthDataStore healthDataStore, int n) {
            this.b = healthDataStore;
            this.a = n;
        }

        /* synthetic */ a(HealthDataStore healthDataStore, int n, byte by) {
            this(healthDataStore, n);
        }

        protected final /* synthetic */ Object doInBackground(Object[] arrobject) {
            return this.b.c();
        }

        /*
         * Enabled aggressive block sorting
         */
        protected final /* synthetic */ void onPostExecute(Object object) {
            object = (Boolean)object;
            if (this.b.c != null) {
                int n = this.a;
                boolean bl = object == null ? false : (Boolean)object;
                object = new HealthConnectionErrorResult(n, bl);
                ((HealthConnectionErrorResult)object).setPackageManager(this.b.e.getPackageManager());
                this.b.c.onConnectionFailed((HealthConnectionErrorResult)object);
            }
        }
    }

    static final class b
    extends Handler {
        private final WeakReference<HealthDataStore> a;

        b(HealthDataStore healthDataStore) {
            this.a = new WeakReference<HealthDataStore>(healthDataStore);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public final void handleMessage(Message object) {
            HealthDataStore healthDataStore = (HealthDataStore)this.a.get();
            if (healthDataStore == null) {
                return;
            }
            switch (object.what) {
                case 5: {
                    if (healthDataStore.a == null) return;
                    healthDataStore.a.cancel();
                    healthDataStore.a = null;
                    Log.i((String)"HealthDataStore", (String)"Init ResultHolder is canceled by time out");
                }
                default: {
                    HealthDataStore.a(healthDataStore, object.what);
                    return;
                }
                case -3: 
            }
            String string2 = object.getData().getString("pincode_activity_pkg");
            String string3 = object.getData().getString("pincode_activity_class");
            Intent intent = new Intent();
            intent.addFlags(4194304);
            intent.putExtra("type", 1);
            intent.setComponent(new ComponentName(string2, string3));
            Log.i((String)"HealthDataStore", (String)("Pop up PinCode activity pkg = " + string2 + ", classname = " + string3));
            try {
                if (!(healthDataStore.e instanceof Activity)) {
                    intent.addFlags(268435456);
                }
                healthDataStore.e.startActivity(intent);
                return;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                Log.i((String)"HealthDataStore", (String)("Only this app cannot access with this " + activityNotFoundException.getMessage()));
                return;
            }
        }
    }

}

