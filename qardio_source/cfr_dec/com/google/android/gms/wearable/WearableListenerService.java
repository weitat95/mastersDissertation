/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzas;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzen;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzhp;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import com.google.android.gms.wearable.zzk;
import com.google.android.gms.wearable.zzm;
import com.google.android.gms.wearable.zzn;
import com.google.android.gms.wearable.zzo;
import com.google.android.gms.wearable.zzp;
import com.google.android.gms.wearable.zzq;
import com.google.android.gms.wearable.zzr;
import com.google.android.gms.wearable.zzs;
import com.google.android.gms.wearable.zzt;
import java.util.List;

public class WearableListenerService
extends Service
implements CapabilityApi.CapabilityListener,
ChannelApi.ChannelListener,
DataApi.DataListener,
MessageApi.MessageListener {
    private IBinder zzfzf;
    private ComponentName zzlhd;
    private zzc zzlhe;
    private Intent zzlhf;
    private Looper zzlhg;
    private final Object zzlhh = new Object();
    private boolean zzlhi;
    private zzas zzlhj = new zzas(new zza(this, null));

    static /* synthetic */ zzas zzc(WearableListenerService wearableListenerService) {
        return wearableListenerService.zzlhj;
    }

    public Looper getLooper() {
        if (this.zzlhg == null) {
            HandlerThread handlerThread = new HandlerThread("WearableListenerService");
            handlerThread.start();
            this.zzlhg = handlerThread.getLooper();
        }
        return this.zzlhg;
    }

    public final IBinder onBind(Intent intent) {
        if ("com.google.android.gms.wearable.BIND_LISTENER".equals(intent.getAction())) {
            return this.zzfzf;
        }
        return null;
    }

    @Override
    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    @Override
    public void onChannelClosed(Channel channel, int n, int n2) {
    }

    public void onChannelClosed(ChannelClient.Channel channel, int n, int n2) {
    }

    @Override
    public void onChannelOpened(Channel channel) {
    }

    public void onChannelOpened(ChannelClient.Channel channel) {
    }

    public void onConnectedNodes(List<Node> list) {
    }

    public void onCreate() {
        super.onCreate();
        this.zzlhd = new ComponentName((Context)this, this.getClass().getName());
        if (Log.isLoggable((String)"WearableLS", (int)3)) {
            String string2 = String.valueOf((Object)this.zzlhd);
            Log.d((String)"WearableLS", (String)new StringBuilder(String.valueOf(string2).length() + 10).append("onCreate: ").append(string2).toString());
        }
        this.zzlhe = new zzc(this, this.getLooper());
        this.zzlhf = new Intent("com.google.android.gms.wearable.BIND_LISTENER");
        this.zzlhf.setComponent(this.zzlhd);
        this.zzfzf = new zzd(null);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDestroy() {
        Object object;
        if (Log.isLoggable((String)"WearableLS", (int)3)) {
            object = String.valueOf((Object)this.zzlhd);
            Log.d((String)"WearableLS", (String)new StringBuilder(String.valueOf(object).length() + 11).append("onDestroy: ").append((String)object).toString());
        }
        object = this.zzlhh;
        synchronized (object) {
            this.zzlhi = true;
            if (this.zzlhe == null) {
                String string2 = String.valueOf((Object)this.zzlhd);
                throw new IllegalStateException(new StringBuilder(String.valueOf(string2).length() + 111).append("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()? component=").append(string2).toString());
            }
            this.zzlhe.quit();
        }
        super.onDestroy();
    }

    public void onEntityUpdate(com.google.android.gms.wearable.zzb zzb2) {
    }

    @Override
    public void onInputClosed(Channel channel, int n, int n2) {
    }

    public void onInputClosed(ChannelClient.Channel channel, int n, int n2) {
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
    }

    public void onNotificationReceived(com.google.android.gms.wearable.zzd zzd2) {
    }

    @Override
    public void onOutputClosed(Channel channel, int n, int n2) {
    }

    public void onOutputClosed(ChannelClient.Channel channel, int n, int n2) {
    }

    public void onPeerConnected(Node node) {
    }

    public void onPeerDisconnected(Node node) {
    }

    final class zza
    extends ChannelClient.ChannelCallback {
        private /* synthetic */ WearableListenerService zzlhk;

        private zza(WearableListenerService wearableListenerService) {
            this.zzlhk = wearableListenerService;
        }

        /* synthetic */ zza(WearableListenerService wearableListenerService, zzk zzk2) {
            this(wearableListenerService);
        }

        @Override
        public final void onChannelClosed(ChannelClient.Channel channel, int n, int n2) {
            this.zzlhk.onChannelClosed(channel, n, n2);
        }

        @Override
        public final void onChannelOpened(ChannelClient.Channel channel) {
            this.zzlhk.onChannelOpened(channel);
        }

        @Override
        public final void onInputClosed(ChannelClient.Channel channel, int n, int n2) {
            this.zzlhk.onInputClosed(channel, n, n2);
        }

        @Override
        public final void onOutputClosed(ChannelClient.Channel channel, int n, int n2) {
            this.zzlhk.onOutputClosed(channel, n, n2);
        }
    }

    final class zzb
    implements ServiceConnection {
        private zzb(WearableListenerService wearableListenerService) {
        }

        /* synthetic */ zzb(WearableListenerService wearableListenerService, zzk zzk2) {
            this(wearableListenerService);
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    final class zzc
    extends Handler {
        private boolean started;
        private /* synthetic */ WearableListenerService zzlhk;
        private final zzb zzlhl;

        zzc(WearableListenerService wearableListenerService, Looper looper) {
            this.zzlhk = wearableListenerService;
            super(looper);
            this.zzlhl = new zzb(this.zzlhk, null);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @SuppressLint(value={"UntrackedBindService"})
        private final void zzbke() {
            synchronized (this) {
                block7: {
                    boolean bl = this.started;
                    if (!bl) break block7;
                    do {
                        return;
                        break;
                    } while (true);
                }
                if (Log.isLoggable((String)"WearableLS", (int)2)) {
                    String string2 = String.valueOf((Object)this.zzlhk.zzlhd);
                    Log.v((String)"WearableLS", (String)new StringBuilder(String.valueOf(string2).length() + 13).append("bindService: ").append(string2).toString());
                }
                this.zzlhk.bindService(this.zzlhk.zzlhf, (ServiceConnection)this.zzlhl, 1);
                this.started = true;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @SuppressLint(value={"UntrackedBindService"})
        private final void zznx(String string2) {
            synchronized (this) {
                boolean bl = this.started;
                if (bl) {
                    if (Log.isLoggable((String)"WearableLS", (int)2)) {
                        String string3 = String.valueOf((Object)this.zzlhk.zzlhd);
                        Log.v((String)"WearableLS", (String)new StringBuilder(String.valueOf(string2).length() + 17 + String.valueOf(string3).length()).append("unbindService: ").append(string2).append(", ").append(string3).toString());
                    }
                    try {
                        this.zzlhk.unbindService((ServiceConnection)this.zzlhl);
                    }
                    catch (RuntimeException runtimeException) {
                        Log.e((String)"WearableLS", (String)"Exception when unbinding from local service", (Throwable)runtimeException);
                    }
                    this.started = false;
                }
                return;
            }
        }

        public final void dispatchMessage(Message message) {
            this.zzbke();
            try {
                super.dispatchMessage(message);
                return;
            }
            finally {
                if (!this.hasMessages(0)) {
                    this.zznx("dispatch");
                }
            }
        }

        final void quit() {
            this.getLooper().quit();
            this.zznx("quit");
        }
    }

    final class zzd
    extends zzen {
        private volatile int zzlhm = -1;

        private zzd() {
        }

        /* synthetic */ zzd(zzk zzk2) {
            this();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private final boolean zza(Runnable runnable, String object, Object object2) {
            int n;
            if (Log.isLoggable((String)"WearableLS", (int)3)) {
                Log.d((String)"WearableLS", (String)String.format("%s: %s %s", object, WearableListenerService.this.zzlhd.toString(), object2));
            }
            if ((n = Binder.getCallingUid()) == this.zzlhm) {
                n = 1;
            } else if (zzhp.zzep((Context)WearableListenerService.this).zznz("com.google.android.wearable.app.cn") && zzx.zzb((Context)WearableListenerService.this, n, "com.google.android.wearable.app.cn")) {
                this.zzlhm = n;
                n = 1;
            } else if (zzx.zzf((Context)WearableListenerService.this, n)) {
                this.zzlhm = n;
                n = 1;
            } else {
                Log.e((String)"WearableLS", (String)new StringBuilder(57).append("Caller is not GooglePlayServices; caller UID: ").append(n).toString());
                return false;
            }
            if (n == 0) {
                return false;
            }
            object = WearableListenerService.this.zzlhh;
            synchronized (object) {
                if (WearableListenerService.this.zzlhi) {
                    return false;
                }
                WearableListenerService.this.zzlhe.post(runnable);
                return true;
            }
        }

        @Override
        public final void onConnectedNodes(List<zzfo> list) {
            this.zza(new zzp(this, list), "onConnectedNodes", list);
        }

        @Override
        public final void zza(zzah zzah2) {
            this.zza(new zzq(this, zzah2), "onConnectedCapabilityChanged", zzah2);
        }

        @Override
        public final void zza(zzaw zzaw2) {
            this.zza(new zzt(this, zzaw2), "onChannelEvent", zzaw2);
        }

        @Override
        public final void zza(zzfe zzfe2) {
            this.zza(new zzm(this, zzfe2), "onMessageReceived", zzfe2);
        }

        @Override
        public final void zza(zzfo zzfo2) {
            this.zza(new zzn(this, zzfo2), "onPeerConnected", zzfo2);
        }

        @Override
        public final void zza(zzi zzi2) {
            this.zza(new zzs(this, zzi2), "onEntityUpdate", zzi2);
        }

        @Override
        public final void zza(zzl zzl2) {
            this.zza(new zzr(this, zzl2), "onNotificationReceived", zzl2);
        }

        @Override
        public final void zzas(DataHolder dataHolder) {
            com.google.android.gms.wearable.zzl zzl2 = new com.google.android.gms.wearable.zzl(this, dataHolder);
            try {
                String string2 = String.valueOf(dataHolder);
                int n = dataHolder.getCount();
                boolean bl = this.zza(zzl2, "onDataItemChanged", new StringBuilder(String.valueOf(string2).length() + 18).append(string2).append(", rows=").append(n).toString());
                if (!bl) {
                    dataHolder.close();
                }
                return;
            }
            catch (Throwable throwable) {
                dataHolder.close();
                throw throwable;
            }
        }

        @Override
        public final void zzb(zzfo zzfo2) {
            this.zza(new zzo(this, zzfo2), "onPeerDisconnected", zzfo2);
        }
    }

}

