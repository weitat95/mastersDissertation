/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Animatable
 *  android.util.Log
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import android.util.Log;
import com.facebook.drawee.controller.ControllerListener;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ForwardingControllerListener<INFO>
implements ControllerListener<INFO> {
    private final List<ControllerListener<? super INFO>> mListeners = new ArrayList<ControllerListener<? super INFO>>(2);

    private void onException(String string2, Throwable throwable) {
        synchronized (this) {
            Log.e((String)"FdingControllerListener", (String)string2, (Throwable)throwable);
            return;
        }
    }

    public void addListener(ControllerListener<? super INFO> controllerListener) {
        synchronized (this) {
            this.mListeners.add(controllerListener);
            return;
        }
    }

    public void clearListeners() {
        synchronized (this) {
            this.mListeners.clear();
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onFailure(String string2, Throwable throwable) {
        synchronized (this) {
            int n = this.mListeners.size();
            int n2 = 0;
            while (n2 < n) {
                ControllerListener<INFO> controllerListener = this.mListeners.get(n2);
                try {
                    void var2_2;
                    controllerListener.onFailure(string2, (Throwable)var2_2);
                }
                catch (Exception exception) {
                    this.onException("InternalListener exception in onFailure", exception);
                }
                ++n2;
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onFinalImageSet(String string2, @Nullable INFO INFO, @Nullable Animatable animatable) {
        synchronized (this) {
            int n = this.mListeners.size();
            int n2 = 0;
            while (n2 < n) {
                ControllerListener<void> controllerListener = this.mListeners.get(n2);
                try {
                    void var3_3;
                    void var2_2;
                    controllerListener.onFinalImageSet(string2, var2_2, (Animatable)var3_3);
                }
                catch (Exception exception) {
                    this.onException("InternalListener exception in onFinalImageSet", exception);
                }
                ++n2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onIntermediateImageFailed(String string2, Throwable throwable) {
        int n = this.mListeners.size();
        int n2 = 0;
        while (n2 < n) {
            ControllerListener<INFO> controllerListener = this.mListeners.get(n2);
            try {
                controllerListener.onIntermediateImageFailed(string2, throwable);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onIntermediateImageFailed", exception);
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
    public void onIntermediateImageSet(String string2, @Nullable INFO INFO) {
        int n = this.mListeners.size();
        int n2 = 0;
        while (n2 < n) {
            ControllerListener<INFO> controllerListener = this.mListeners.get(n2);
            try {
                controllerListener.onIntermediateImageSet(string2, INFO);
            }
            catch (Exception exception) {
                this.onException("InternalListener exception in onIntermediateImageSet", exception);
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
    public void onRelease(String string2) {
        synchronized (this) {
            int n = this.mListeners.size();
            int n2 = 0;
            while (n2 < n) {
                ControllerListener<INFO> controllerListener = this.mListeners.get(n2);
                try {
                    controllerListener.onRelease(string2);
                }
                catch (Exception exception) {
                    this.onException("InternalListener exception in onRelease", exception);
                }
                ++n2;
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSubmit(String string2, Object object) {
        synchronized (this) {
            int n = this.mListeners.size();
            int n2 = 0;
            while (n2 < n) {
                ControllerListener<INFO> controllerListener = this.mListeners.get(n2);
                try {
                    void var2_2;
                    controllerListener.onSubmit(string2, var2_2);
                }
                catch (Exception exception) {
                    this.onException("InternalListener exception in onSubmit", exception);
                }
                ++n2;
            }
            return;
        }
    }
}

