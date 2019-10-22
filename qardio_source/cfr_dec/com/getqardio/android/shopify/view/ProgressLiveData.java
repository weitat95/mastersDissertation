/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import android.arch.lifecycle.LiveData;

public final class ProgressLiveData
extends LiveData<Progress> {
    public void hide() {
        Progress progress = (Progress)this.getValue();
        if (progress == null || !progress.show) {
            return;
        }
        this.setValue(new Progress(progress.requestId, false));
    }

    public void hide(int n) {
        Progress progress = (Progress)this.getValue();
        if (progress == null || !progress.show || progress.requestId != n) {
            return;
        }
        this.setValue(new Progress(n, false));
    }

    public void show(int n) {
        this.setValue(new Progress(n, true));
    }

    public static final class Progress {
        public final int requestId;
        public final boolean show;

        private Progress(int n, boolean bl) {
            this.requestId = n;
            this.show = bl;
        }
    }

}

