/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.media.session.PlaybackState
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.os.Bundle;

class PlaybackStateCompatApi22 {
    public static Bundle getExtras(Object object) {
        return ((PlaybackState)object).getExtras();
    }
}

