/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.getqardio.android.fcm;

import android.content.Context;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.QardioFirebaseInstanceIDService$$Lambda$1;
import com.getqardio.android.fcm.QardioFirebaseInstanceIDService$$Lambda$2;
import com.getqardio.android.fcm.dagger.DaggerFcmInstanceIdComponent;
import com.getqardio.android.fcm.dagger.FCMModule;
import com.getqardio.android.fcm.dagger.FcmInstanceIdComponent;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class QardioFirebaseInstanceIDService
extends FirebaseInstanceIdService {
    FCMManager fcmManager;

    static /* synthetic */ void lambda$sendRegistrationToServer$0() throws Exception {
        Timber.d("sent to server", new Object[0]);
    }

    private void sendRegistrationToServer(String string2) {
        if (this.isUserSignedIn() && string2 != null) {
            Timber.d("Registering the token in the app", new Object[0]);
            this.fcmManager.registerFCMToken(this.retrieveCurrentUserToken(), string2).subscribe(QardioFirebaseInstanceIDService$$Lambda$1.lambdaFactory$(), QardioFirebaseInstanceIDService$$Lambda$2.lambdaFactory$(this));
            return;
        }
        Timber.d("setting the registration to pending", new Object[0]);
        this.fcmManager.setRegistrationPending();
    }

    protected FcmInstanceIdComponent getFcmInstanceIdComponent() {
        return DaggerFcmInstanceIdComponent.builder().fCMModule(new FCMModule(this.getApplicationContext())).build();
    }

    protected boolean isUserSignedIn() {
        return !TextUtils.isEmpty((CharSequence)this.retrieveCurrentUserToken());
    }

    /* synthetic */ void lambda$sendRegistrationToServer$1(Throwable throwable) throws Exception {
        Timber.e(throwable);
        Timber.d("setting the registration to pending", new Object[0]);
        this.fcmManager.setRegistrationPending();
    }

    @Override
    public void onTokenRefresh() {
        this.getFcmInstanceIdComponent().inject(this);
        String string2 = this.retrieveFirebaseToken();
        Timber.d("Refreshed token: " + string2, new Object[0]);
        this.sendRegistrationToServer(string2);
    }

    protected String retrieveCurrentUserToken() {
        if (CustomApplication.getApplication() != null) {
            return CustomApplication.getApplication().getCurrentUserToken();
        }
        return "";
    }

    protected String retrieveFirebaseToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }
}

