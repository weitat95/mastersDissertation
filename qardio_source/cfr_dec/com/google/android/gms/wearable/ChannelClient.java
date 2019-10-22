/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package com.google.android.gms.wearable;

import android.os.Parcelable;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.wearable.Wearable;

public abstract class ChannelClient
extends GoogleApi<Wearable.WearableOptions> {

    public static interface Channel
    extends Parcelable {
    }

    public static class ChannelCallback {
        public void onChannelClosed(Channel channel, int n, int n2) {
        }

        public void onChannelOpened(Channel channel) {
        }

        public void onInputClosed(Channel channel, int n, int n2) {
        }

        public void onOutputClosed(Channel channel, int n, int n2) {
        }
    }

}

