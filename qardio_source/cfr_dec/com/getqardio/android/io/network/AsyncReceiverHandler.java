/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.util.SparseArray
 */
package com.getqardio.android.io.network;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.io.network.request.BPMeasurementsRequestHandler;
import com.getqardio.android.io.network.request.ClaimMeasurementsRequestHandler;
import com.getqardio.android.io.network.request.CreateNewUserRequestHandler;
import com.getqardio.android.io.network.request.CurrentGoalRequestHandler;
import com.getqardio.android.io.network.request.DeviceAssociationsRequestHandler;
import com.getqardio.android.io.network.request.FAQRequestHandler;
import com.getqardio.android.io.network.request.FirmwareUpdateRequestHandler;
import com.getqardio.android.io.network.request.FlickrRequestHandler;
import com.getqardio.android.io.network.request.ForgotPasswordRequestHandler;
import com.getqardio.android.io.network.request.GoogleFitRequestHandler;
import com.getqardio.android.io.network.request.LoginRequestHandler;
import com.getqardio.android.io.network.request.LogoutRequestHandler;
import com.getqardio.android.io.network.request.PregnancyModeRequestHandler;
import com.getqardio.android.io.network.request.ProfileRequestHandler;
import com.getqardio.android.io.network.request.ReminderRequestHandler;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.request.SHealthRequestHandler;
import com.getqardio.android.io.network.request.SendHistoryRequestHandler;
import com.getqardio.android.io.network.request.SettingsRequestHandler;
import com.getqardio.android.io.network.request.StatisticRequestHandler;
import com.getqardio.android.io.network.request.SupportRequestHandler;
import com.getqardio.android.io.network.request.TooltipsRequestHandler;
import com.getqardio.android.io.network.request.WeightMeasurementsRequestHandler;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.utils.NotificationHelper;
import timber.log.Timber;

public class AsyncReceiverHandler
extends Service {
    private static SparseArray<RequestHandler> handlers = new SparseArray();
    private volatile ServiceHandler serviceHandler;
    private volatile Looper serviceLooper;

    private void clearQueue() {
        this.serviceHandler.removeMessages(0);
        this.stopSelf();
    }

    public static Intent createIntent(Context context, int n, long l) {
        context = new Intent(context, AsyncReceiverHandler.class);
        context.putExtra("com.getqardio.android.extra.ACTION", n);
        context.putExtra("com.getqardio.android.extra.USER_ID", l);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private RequestHandler getHandler(int n) {
        RequestHandler requestHandler;
        RequestHandler requestHandler2 = requestHandler = (RequestHandler)handlers.get(n);
        if (requestHandler == null) {
            requestHandler2 = requestHandler;
            switch (n) {
                default: {
                    requestHandler2 = requestHandler;
                    break;
                }
                case 0: {
                    requestHandler2 = new FAQRequestHandler();
                    break;
                }
                case 1: {
                    requestHandler2 = new LoginRequestHandler();
                    break;
                }
                case 2: {
                    requestHandler2 = new ProfileRequestHandler();
                    break;
                }
                case 3: {
                    requestHandler2 = new CreateNewUserRequestHandler();
                    break;
                }
                case 4: {
                    requestHandler2 = new SettingsRequestHandler();
                    break;
                }
                case 5: {
                    requestHandler2 = new ForgotPasswordRequestHandler();
                    break;
                }
                case 6: {
                    requestHandler2 = new BPMeasurementsRequestHandler();
                    break;
                }
                case 7: {
                    requestHandler2 = new ReminderRequestHandler();
                    break;
                }
                case 10: {
                    requestHandler2 = new SendHistoryRequestHandler();
                    break;
                }
                case 11: {
                    requestHandler2 = new TooltipsRequestHandler();
                    break;
                }
                case 13: {
                    requestHandler2 = new SupportRequestHandler();
                    break;
                }
                case 14: {
                    requestHandler2 = new LogoutRequestHandler();
                    break;
                }
                case 15: {
                    requestHandler2 = new FlickrRequestHandler();
                    break;
                }
                case 16: {
                    requestHandler2 = new StatisticRequestHandler();
                    break;
                }
                case 17: {
                    requestHandler2 = new SHealthRequestHandler();
                    break;
                }
                case 18: {
                    requestHandler2 = new WeightMeasurementsRequestHandler();
                    break;
                }
                case 19: {
                    requestHandler2 = new ClaimMeasurementsRequestHandler();
                    break;
                }
                case 20: {
                    requestHandler2 = new FirmwareUpdateRequestHandler();
                    break;
                }
                case 21: {
                    requestHandler2 = new CurrentGoalRequestHandler();
                    break;
                }
                case 22: {
                    requestHandler2 = new DeviceAssociationsRequestHandler();
                    break;
                }
                case 23: {
                    requestHandler2 = new PregnancyModeRequestHandler();
                }
                case 8: 
                case 9: 
                case 12: 
                case 24: {
                    break;
                }
                case 25: {
                    requestHandler2 = new GoogleFitRequestHandler();
                }
            }
            handlers.put(n, (Object)requestHandler2);
        }
        return requestHandler2;
    }

    private void notifyReloginFailed() {
        Intent intent = NotificationHelper.ReloginFailedNotification.createNotificationIntent();
        LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent);
    }

    public static void stopSyncService(Context context) {
        context.stopService(new Intent(context, AsyncReceiverHandler.class));
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("Service[" + AsyncReceiverHandler.class.getName() + "]");
        handlerThread.start();
        this.serviceLooper = handlerThread.getLooper();
        this.serviceHandler = new ServiceHandler(this.serviceLooper);
    }

    public void onDestroy() {
        this.serviceLooper.quit();
    }

    protected void onHandleIntent(Intent intent) {
        block4: {
            int n;
            block5: {
                RequestHandler requestHandler;
                long l;
                Object object;
                block7: {
                    block6: {
                        if (intent == null) break block4;
                        n = intent.getIntExtra("com.getqardio.android.extra.ACTION", -1);
                        l = intent.getLongExtra("com.getqardio.android.extra.USER_ID", -1L);
                        object = CustomApplication.getApplication().getCurrentUserToken();
                        Long l2 = CustomApplication.getApplication().getCurrentUserId();
                        requestHandler = this.getHandler(n);
                        if (requestHandler == null || !requestHandler.checkUserId(intent, l2, l)) break block5;
                        if (requestHandler.processIntent((Context)this, intent, l, (String)object) != RequestHandler.ProcessResult.INVALID_TOKEN) break block6;
                        object = CustomApplication.getApplication().getCurrentUserEmail();
                        if (object != null) break block7;
                        this.clearQueue();
                    }
                    return;
                }
                object = AuthHelper.getUserByEmail((Context)CustomApplication.getApplication(), (String)object);
                if (object != null && LoginRequestHandler.relogin((Context)this, (User)object)) {
                    requestHandler.processIntent((Context)this, intent, l, CustomApplication.getApplication().getCurrentUserToken());
                    return;
                }
                this.notifyReloginFailed();
                this.clearQueue();
                return;
            }
            Timber.e("RequestHandler did not find for action %d or wrong user id", n);
            return;
        }
        Timber.e("RequestHandler: Intent is null", new Object[0]);
    }

    public void onStart(Intent intent, int n) {
        Message message = this.serviceHandler.obtainMessage();
        message.what = 0;
        message.arg1 = n;
        message.obj = intent;
        this.serviceHandler.sendMessage(message);
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        this.onStart(intent, n2);
        return 2;
    }

    private final class ServiceHandler
    extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            AsyncReceiverHandler.this.onHandleIntent((Intent)message.obj);
            AsyncReceiverHandler.this.stopSelf(message.arg1);
        }
    }

}

