/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.HolderFragment;
import android.arch.lifecycle.ViewModelStore;
import android.support.v4.app.FragmentActivity;

public class ViewModelStores {
    public static ViewModelStore of(FragmentActivity fragmentActivity) {
        return HolderFragment.holderFragmentFor(fragmentActivity).getViewModelStore();
    }
}

