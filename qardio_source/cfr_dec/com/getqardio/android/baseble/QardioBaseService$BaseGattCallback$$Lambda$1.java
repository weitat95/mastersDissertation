/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.baseble;

import com.getqardio.android.baseble.GattQueue;
import com.getqardio.android.baseble.QardioBaseService;
import java.lang.invoke.LambdaForm;

final class QardioBaseService$BaseGattCallback$$Lambda$1
implements GattQueue.GattCallback {
    private final QardioBaseService.BaseGattCallback arg$1;

    private QardioBaseService$BaseGattCallback$$Lambda$1(QardioBaseService.BaseGattCallback baseGattCallback) {
        this.arg$1 = baseGattCallback;
    }

    public static GattQueue.GattCallback lambdaFactory$(QardioBaseService.BaseGattCallback baseGattCallback) {
        return new QardioBaseService$BaseGattCallback$$Lambda$1(baseGattCallback);
    }

    @LambdaForm.Hidden
    @Override
    public void onGattCommandFailed(String string2) {
        QardioBaseService.BaseGattCallback.access$lambda$0(this.arg$1, string2);
    }
}

