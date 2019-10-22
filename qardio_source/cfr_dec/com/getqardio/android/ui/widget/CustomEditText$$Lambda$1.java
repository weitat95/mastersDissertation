/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.getqardio.android.ui.widget;

import android.view.MotionEvent;
import android.view.View;
import com.getqardio.android.ui.widget.CustomEditText;
import java.lang.invoke.LambdaForm;

final class CustomEditText$$Lambda$1
implements View.OnTouchListener {
    private final CustomEditText arg$1;

    private CustomEditText$$Lambda$1(CustomEditText customEditText) {
        this.arg$1 = customEditText;
    }

    public static View.OnTouchListener lambdaFactory$(CustomEditText customEditText) {
        return new CustomEditText$$Lambda$1(customEditText);
    }

    @LambdaForm.Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$showError$0(view, motionEvent);
    }
}

