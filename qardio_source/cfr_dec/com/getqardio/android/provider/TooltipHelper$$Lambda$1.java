/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.provider;

import android.content.Context;
import com.getqardio.android.datamodel.Tooltip;
import com.getqardio.android.provider.TooltipHelper;
import java.lang.invoke.LambdaForm;

final class TooltipHelper$$Lambda$1
implements Runnable {
    private final Context arg$1;
    private final Tooltip arg$2;

    private TooltipHelper$$Lambda$1(Context context, Tooltip tooltip) {
        this.arg$1 = context;
        this.arg$2 = tooltip;
    }

    public static Runnable lambdaFactory$(Context context, Tooltip tooltip) {
        return new TooltipHelper$$Lambda$1(context, tooltip);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        TooltipHelper.lambda$saveTooltips$0(this.arg$1, this.arg$2);
    }
}

