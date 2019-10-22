/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.os.Build;
import android.view.ViewGroup;

public final class ViewGroupCompat {
    static final ViewGroupCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 21 ? new ViewGroupCompatApi21Impl() : (Build.VERSION.SDK_INT >= 18 ? new ViewGroupCompatApi18Impl() : new ViewGroupCompatBaseImpl());

    public static int getLayoutMode(ViewGroup viewGroup) {
        return IMPL.getLayoutMode(viewGroup);
    }

    @Deprecated
    public static void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean bl) {
        viewGroup.setMotionEventSplittingEnabled(bl);
    }

    static class ViewGroupCompatApi18Impl
    extends ViewGroupCompatBaseImpl {
        ViewGroupCompatApi18Impl() {
        }

        @Override
        public int getLayoutMode(ViewGroup viewGroup) {
            return viewGroup.getLayoutMode();
        }
    }

    static class ViewGroupCompatApi21Impl
    extends ViewGroupCompatApi18Impl {
        ViewGroupCompatApi21Impl() {
        }
    }

    static class ViewGroupCompatBaseImpl {
        ViewGroupCompatBaseImpl() {
        }

        public int getLayoutMode(ViewGroup viewGroup) {
            return 0;
        }
    }

}

