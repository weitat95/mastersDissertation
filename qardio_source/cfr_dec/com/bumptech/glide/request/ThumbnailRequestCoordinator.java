/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.request;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestCoordinator;

public class ThumbnailRequestCoordinator
implements Request,
RequestCoordinator {
    private RequestCoordinator coordinator;
    private Request full;
    private Request thumb;

    public ThumbnailRequestCoordinator() {
        this(null);
    }

    public ThumbnailRequestCoordinator(RequestCoordinator requestCoordinator) {
        this.coordinator = requestCoordinator;
    }

    private boolean parentCanNotifyStatusChanged() {
        return this.coordinator == null || this.coordinator.canNotifyStatusChanged(this);
    }

    private boolean parentCanSetImage() {
        return this.coordinator == null || this.coordinator.canSetImage(this);
    }

    private boolean parentIsAnyResourceSet() {
        return this.coordinator != null && this.coordinator.isAnyResourceSet();
    }

    @Override
    public void begin() {
        if (!this.thumb.isRunning()) {
            this.thumb.begin();
        }
        if (!this.full.isRunning()) {
            this.full.begin();
        }
    }

    @Override
    public boolean canNotifyStatusChanged(Request request) {
        return this.parentCanNotifyStatusChanged() && request.equals(this.full) && !this.isAnyResourceSet();
    }

    @Override
    public boolean canSetImage(Request request) {
        return this.parentCanSetImage() && (request.equals(this.full) || !this.full.isResourceSet());
    }

    @Override
    public void clear() {
        this.thumb.clear();
        this.full.clear();
    }

    @Override
    public boolean isAnyResourceSet() {
        return this.parentIsAnyResourceSet() || this.isResourceSet();
    }

    @Override
    public boolean isCancelled() {
        return this.full.isCancelled();
    }

    @Override
    public boolean isComplete() {
        return this.full.isComplete() || this.thumb.isComplete();
    }

    @Override
    public boolean isResourceSet() {
        return this.full.isResourceSet() || this.thumb.isResourceSet();
    }

    @Override
    public boolean isRunning() {
        return this.full.isRunning();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onRequestSuccess(Request request) {
        block5: {
            block4: {
                if (request.equals(this.thumb)) break block4;
                if (this.coordinator != null) {
                    this.coordinator.onRequestSuccess(this);
                }
                if (!this.thumb.isComplete()) break block5;
            }
            return;
        }
        this.thumb.clear();
    }

    @Override
    public void pause() {
        this.full.pause();
        this.thumb.pause();
    }

    @Override
    public void recycle() {
        this.full.recycle();
        this.thumb.recycle();
    }

    public void setRequests(Request request, Request request2) {
        this.full = request;
        this.thumb = request2;
    }
}

