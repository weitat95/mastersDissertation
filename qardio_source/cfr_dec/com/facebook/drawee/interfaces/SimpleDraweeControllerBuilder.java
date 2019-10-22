/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.interfaces;

import android.net.Uri;
import com.facebook.drawee.interfaces.DraweeController;
import javax.annotation.Nullable;

public interface SimpleDraweeControllerBuilder {
    public DraweeController build();

    public SimpleDraweeControllerBuilder setCallerContext(Object var1);

    public SimpleDraweeControllerBuilder setOldController(@Nullable DraweeController var1);

    public SimpleDraweeControllerBuilder setUri(Uri var1);
}

