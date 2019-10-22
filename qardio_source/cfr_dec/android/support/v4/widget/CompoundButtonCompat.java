/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.widget.CompoundButton
 */
package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.TintableCompoundButton;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

public final class CompoundButtonCompat {
    private static final CompoundButtonCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 23 ? new CompoundButtonCompatApi23Impl() : (Build.VERSION.SDK_INT >= 21 ? new CompoundButtonCompatApi21Impl() : new CompoundButtonCompatBaseImpl());

    public static Drawable getButtonDrawable(CompoundButton compoundButton) {
        return IMPL.getButtonDrawable(compoundButton);
    }

    public static void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
        IMPL.setButtonTintList(compoundButton, colorStateList);
    }

    public static void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode mode) {
        IMPL.setButtonTintMode(compoundButton, mode);
    }

    static class CompoundButtonCompatApi21Impl
    extends CompoundButtonCompatBaseImpl {
        CompoundButtonCompatApi21Impl() {
        }

        @Override
        public void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
            compoundButton.setButtonTintList(colorStateList);
        }

        @Override
        public void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode mode) {
            compoundButton.setButtonTintMode(mode);
        }
    }

    static class CompoundButtonCompatApi23Impl
    extends CompoundButtonCompatApi21Impl {
        CompoundButtonCompatApi23Impl() {
        }

        @Override
        public Drawable getButtonDrawable(CompoundButton compoundButton) {
            return compoundButton.getButtonDrawable();
        }
    }

    static class CompoundButtonCompatBaseImpl {
        private static Field sButtonDrawableField;
        private static boolean sButtonDrawableFieldFetched;

        CompoundButtonCompatBaseImpl() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Drawable getButtonDrawable(CompoundButton compoundButton) {
            if (!sButtonDrawableFieldFetched) {
                try {
                    sButtonDrawableField = CompoundButton.class.getDeclaredField("mButtonDrawable");
                    sButtonDrawableField.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.i((String)"CompoundButtonCompat", (String)"Failed to retrieve mButtonDrawable field", (Throwable)noSuchFieldException);
                }
                sButtonDrawableFieldFetched = true;
            }
            if (sButtonDrawableField == null) return null;
            try {
                return (Drawable)sButtonDrawableField.get((Object)compoundButton);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)"CompoundButtonCompat", (String)"Failed to get button drawable via reflection", (Throwable)illegalAccessException);
                sButtonDrawableField = null;
            }
            return null;
        }

        public void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) {
            if (compoundButton instanceof TintableCompoundButton) {
                ((TintableCompoundButton)compoundButton).setSupportButtonTintList(colorStateList);
            }
        }

        public void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode mode) {
            if (compoundButton instanceof TintableCompoundButton) {
                ((TintableCompoundButton)compoundButton).setSupportButtonTintMode(mode);
            }
        }
    }

}

