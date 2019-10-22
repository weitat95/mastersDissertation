/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 */
package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;

public class IconRequest {
    public final String hash;
    public final int height;
    public final int iconResourceId;
    public final int width;

    public IconRequest(String string2, int n, int n2, int n3) {
        this.hash = string2;
        this.iconResourceId = n;
        this.width = n2;
        this.height = n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static IconRequest build(Context context, String string2) {
        Object object = null;
        if (string2 == null) return object;
        try {
            int n = CommonUtils.getAppIconResourceId(context);
            Fabric.getLogger().d("Fabric", "App icon resource ID is " + n);
            object = new BitmapFactory.Options();
            object.inJustDecodeBounds = true;
            BitmapFactory.decodeResource((Resources)context.getResources(), (int)n, (BitmapFactory.Options)object);
            return new IconRequest(string2, n, object.outWidth, object.outHeight);
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Failed to load icon", exception);
            return null;
        }
    }
}

