/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.wearable.Channel;

@Deprecated
public interface ChannelApi {

    @Deprecated
    public static interface ChannelListener {
        public void onChannelClosed(Channel var1, int var2, int var3);

        public void onChannelOpened(Channel var1);

        public void onInputClosed(Channel var1, int var2, int var3);

        public void onOutputClosed(Channel var1, int var2, int var3);
    }

}

