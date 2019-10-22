/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package android.support.v4.app;

import android.app.Activity;
import android.support.v4.util.SimpleArrayMap;

public class SupportActivity
extends Activity {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap();

    public <T extends ExtraData> T getExtraData(Class<T> class_) {
        return (T)this.mExtraDataMap.get(class_);
    }

    public void putExtraData(ExtraData extraData) {
        this.mExtraDataMap.put(extraData.getClass(), extraData);
    }

    public static class ExtraData {
    }

}

