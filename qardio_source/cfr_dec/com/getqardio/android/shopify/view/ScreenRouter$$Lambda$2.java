/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.shopify.view;

import android.content.Context;
import com.getqardio.android.shopify.util.BiConsumer;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.collections.CollectionProductClickActionEvent;
import java.lang.invoke.LambdaForm;

final class ScreenRouter$$Lambda$2
implements BiConsumer {
    private static final ScreenRouter$$Lambda$2 instance = new ScreenRouter$$Lambda$2();

    private ScreenRouter$$Lambda$2() {
    }

    public static BiConsumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object, Object object2) {
        ScreenRouter.lambda$new$1((Context)object, (CollectionProductClickActionEvent)object2);
    }
}

