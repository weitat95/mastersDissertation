/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.listener;

import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class ForwardingRequestListener
implements RequestListener {
    private final List<RequestListener> mRequestListeners;

    public ForwardingRequestListener(Set<RequestListener> object) {
        this.mRequestListeners = new ArrayList<RequestListener>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            RequestListener requestListener = (RequestListener)object.next();
            this.mRequestListeners.add(requestListener);
        }
    }

    public ForwardingRequestListener(RequestListener ... arrrequestListener) {
        this.mRequestListeners = Arrays.asList(arrrequestListener);
    }

    private void onException(String string2, Throwable throwable) {
        FLog.e("ForwardingRequestListener", string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onProducerEvent(String string2, String string3, String string4) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onProducerEvent(string2, string3, string4);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onIntermediateChunkStart", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onProducerFinishWithCancellation(String string2, String string3, @Nullable Map<String, String> map) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onProducerFinishWithCancellation(string2, string3, map);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onProducerFinishWithCancellation", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onProducerFinishWithFailure(String string2, String string3, Throwable throwable, @Nullable Map<String, String> map) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onProducerFinishWithFailure(string2, string3, throwable, map);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onProducerFinishWithFailure", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onProducerFinishWithSuccess(String string2, String string3, @Nullable Map<String, String> map) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onProducerFinishWithSuccess(string2, string3, map);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onProducerFinishWithSuccess", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onProducerStart(String string2, String string3) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onProducerStart(string2, string3);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onProducerStart", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRequestCancellation(String string2) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onRequestCancellation(string2);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onRequestCancellation", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRequestFailure(ImageRequest imageRequest, String string2, Throwable throwable, boolean bl) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onRequestFailure(imageRequest, string2, throwable, bl);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onRequestFailure", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRequestStart(ImageRequest imageRequest, Object object, String string2, boolean bl) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onRequestStart(imageRequest, object, string2, bl);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onRequestStart", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRequestSuccess(ImageRequest imageRequest, String string2, boolean bl) {
        int n = this.mRequestListeners.size();
        int n2 = 0;
        while (n2 < n) {
            RequestListener requestListener = this.mRequestListeners.get(n2);
            try {
                requestListener.onRequestSuccess(imageRequest, string2, bl);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onRequestSuccess", exception);
            }
            ++n2;
        }
        return;
    }

    @Override
    public boolean requiresExtraMap(String string2) {
        int n = this.mRequestListeners.size();
        for (int i = 0; i < n; ++i) {
            if (!this.mRequestListeners.get(i).requiresExtraMap(string2)) continue;
            return true;
        }
        return false;
    }
}

