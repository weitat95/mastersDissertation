/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 */
package android.support.v7.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.TooltipCompatHandler;
import android.view.View;

public class TooltipCompat {
    private static final ViewCompatImpl IMPL = Build.VERSION.SDK_INT >= 26 ? new Api26ViewCompatImpl() : new BaseViewCompatImpl();

    public static void setTooltipText(View view, CharSequence charSequence) {
        IMPL.setTooltipText(view, charSequence);
    }

    @TargetApi(value=26)
    private static class Api26ViewCompatImpl
    implements ViewCompatImpl {
        private Api26ViewCompatImpl() {
        }

        @Override
        public void setTooltipText(View view, CharSequence charSequence) {
            view.setTooltipText(charSequence);
        }
    }

    private static class BaseViewCompatImpl
    implements ViewCompatImpl {
        private BaseViewCompatImpl() {
        }

        @Override
        public void setTooltipText(View view, CharSequence charSequence) {
            TooltipCompatHandler.setTooltipText(view, charSequence);
        }
    }

    private static interface ViewCompatImpl {
        public void setTooltipText(View var1, CharSequence var2);
    }

}

