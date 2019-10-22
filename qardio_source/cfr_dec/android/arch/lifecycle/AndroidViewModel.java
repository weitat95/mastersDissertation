/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package android.arch.lifecycle;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

public class AndroidViewModel
extends ViewModel {
    private Application mApplication;

    public AndroidViewModel(Application application) {
        this.mApplication = application;
    }
}

