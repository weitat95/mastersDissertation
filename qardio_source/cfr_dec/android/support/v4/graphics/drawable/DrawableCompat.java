/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v4.graphics.drawable.DrawableWrapperApi14;
import android.support.v4.graphics.drawable.DrawableWrapperApi19;
import android.support.v4.graphics.drawable.DrawableWrapperApi21;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.util.AttributeSet;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableCompat {
    static final DrawableCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 23 ? new DrawableCompatApi23Impl() : (Build.VERSION.SDK_INT >= 21 ? new DrawableCompatApi21Impl() : (Build.VERSION.SDK_INT >= 19 ? new DrawableCompatApi19Impl() : (Build.VERSION.SDK_INT >= 17 ? new DrawableCompatApi17Impl() : new DrawableCompatBaseImpl())));

    public static void applyTheme(Drawable drawable2, Resources.Theme theme) {
        IMPL.applyTheme(drawable2, theme);
    }

    public static boolean canApplyTheme(Drawable drawable2) {
        return IMPL.canApplyTheme(drawable2);
    }

    public static void clearColorFilter(Drawable drawable2) {
        IMPL.clearColorFilter(drawable2);
    }

    public static int getAlpha(Drawable drawable2) {
        return IMPL.getAlpha(drawable2);
    }

    public static ColorFilter getColorFilter(Drawable drawable2) {
        return IMPL.getColorFilter(drawable2);
    }

    public static int getLayoutDirection(Drawable drawable2) {
        return IMPL.getLayoutDirection(drawable2);
    }

    public static void inflate(Drawable drawable2, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        IMPL.inflate(drawable2, resources, xmlPullParser, attributeSet, theme);
    }

    public static boolean isAutoMirrored(Drawable drawable2) {
        return IMPL.isAutoMirrored(drawable2);
    }

    public static void jumpToCurrentState(Drawable drawable2) {
        IMPL.jumpToCurrentState(drawable2);
    }

    public static void setAutoMirrored(Drawable drawable2, boolean bl) {
        IMPL.setAutoMirrored(drawable2, bl);
    }

    public static void setHotspot(Drawable drawable2, float f, float f2) {
        IMPL.setHotspot(drawable2, f, f2);
    }

    public static void setHotspotBounds(Drawable drawable2, int n, int n2, int n3, int n4) {
        IMPL.setHotspotBounds(drawable2, n, n2, n3, n4);
    }

    public static boolean setLayoutDirection(Drawable drawable2, int n) {
        return IMPL.setLayoutDirection(drawable2, n);
    }

    public static void setTint(Drawable drawable2, int n) {
        IMPL.setTint(drawable2, n);
    }

    public static void setTintList(Drawable drawable2, ColorStateList colorStateList) {
        IMPL.setTintList(drawable2, colorStateList);
    }

    public static void setTintMode(Drawable drawable2, PorterDuff.Mode mode) {
        IMPL.setTintMode(drawable2, mode);
    }

    public static <T extends Drawable> T unwrap(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 instanceof DrawableWrapper) {
            drawable3 = ((DrawableWrapper)drawable2).getWrappedDrawable();
        }
        return (T)drawable3;
    }

    public static Drawable wrap(Drawable drawable2) {
        return IMPL.wrap(drawable2);
    }

    static class DrawableCompatApi17Impl
    extends DrawableCompatBaseImpl {
        private static Method sGetLayoutDirectionMethod;
        private static boolean sGetLayoutDirectionMethodFetched;
        private static Method sSetLayoutDirectionMethod;
        private static boolean sSetLayoutDirectionMethodFetched;

        DrawableCompatApi17Impl() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int getLayoutDirection(Drawable drawable2) {
            if (!sGetLayoutDirectionMethodFetched) {
                try {
                    sGetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                    sGetLayoutDirectionMethod.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)"DrawableCompatApi17", (String)"Failed to retrieve getLayoutDirection() method", (Throwable)noSuchMethodException);
                }
                sGetLayoutDirectionMethodFetched = true;
            }
            if (sGetLayoutDirectionMethod == null) return 0;
            try {
                return (Integer)sGetLayoutDirectionMethod.invoke((Object)drawable2, new Object[0]);
            }
            catch (Exception exception) {
                Log.i((String)"DrawableCompatApi17", (String)"Failed to invoke getLayoutDirection() via reflection", (Throwable)exception);
                sGetLayoutDirectionMethod = null;
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean setLayoutDirection(Drawable drawable2, int n) {
            if (!sSetLayoutDirectionMethodFetched) {
                try {
                    sSetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", Integer.TYPE);
                    sSetLayoutDirectionMethod.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)"DrawableCompatApi17", (String)"Failed to retrieve setLayoutDirection(int) method", (Throwable)noSuchMethodException);
                }
                sSetLayoutDirectionMethodFetched = true;
            }
            if (sSetLayoutDirectionMethod != null) {
                try {
                    sSetLayoutDirectionMethod.invoke((Object)drawable2, n);
                    return true;
                }
                catch (Exception exception) {
                    Log.i((String)"DrawableCompatApi17", (String)"Failed to invoke setLayoutDirection(int) via reflection", (Throwable)exception);
                    sSetLayoutDirectionMethod = null;
                }
            }
            return false;
        }
    }

    static class DrawableCompatApi19Impl
    extends DrawableCompatApi17Impl {
        DrawableCompatApi19Impl() {
        }

        @Override
        public int getAlpha(Drawable drawable2) {
            return drawable2.getAlpha();
        }

        @Override
        public boolean isAutoMirrored(Drawable drawable2) {
            return drawable2.isAutoMirrored();
        }

        @Override
        public void setAutoMirrored(Drawable drawable2, boolean bl) {
            drawable2.setAutoMirrored(bl);
        }

        @Override
        public Drawable wrap(Drawable drawable2) {
            Drawable drawable3 = drawable2;
            if (!(drawable2 instanceof TintAwareDrawable)) {
                drawable3 = new DrawableWrapperApi19(drawable2);
            }
            return drawable3;
        }
    }

    static class DrawableCompatApi21Impl
    extends DrawableCompatApi19Impl {
        DrawableCompatApi21Impl() {
        }

        @Override
        public void applyTheme(Drawable drawable2, Resources.Theme theme) {
            drawable2.applyTheme(theme);
        }

        @Override
        public boolean canApplyTheme(Drawable drawable2) {
            return drawable2.canApplyTheme();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void clearColorFilter(Drawable drawable2) {
            drawable2.clearColorFilter();
            if (drawable2 instanceof InsetDrawable) {
                this.clearColorFilter(((InsetDrawable)drawable2).getDrawable());
                return;
            } else {
                if (drawable2 instanceof DrawableWrapper) {
                    this.clearColorFilter(((DrawableWrapper)drawable2).getWrappedDrawable());
                    return;
                }
                if (!(drawable2 instanceof DrawableContainer) || (drawable2 = (DrawableContainer.DrawableContainerState)((DrawableContainer)drawable2).getConstantState()) == null) return;
                {
                    int n = drawable2.getChildCount();
                    for (int i = 0; i < n; ++i) {
                        Drawable drawable3 = drawable2.getChild(i);
                        if (drawable3 == null) continue;
                        this.clearColorFilter(drawable3);
                    }
                }
            }
        }

        @Override
        public ColorFilter getColorFilter(Drawable drawable2) {
            return drawable2.getColorFilter();
        }

        @Override
        public void inflate(Drawable drawable2, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
            drawable2.inflate(resources, xmlPullParser, attributeSet, theme);
        }

        @Override
        public void setHotspot(Drawable drawable2, float f, float f2) {
            drawable2.setHotspot(f, f2);
        }

        @Override
        public void setHotspotBounds(Drawable drawable2, int n, int n2, int n3, int n4) {
            drawable2.setHotspotBounds(n, n2, n3, n4);
        }

        @Override
        public void setTint(Drawable drawable2, int n) {
            drawable2.setTint(n);
        }

        @Override
        public void setTintList(Drawable drawable2, ColorStateList colorStateList) {
            drawable2.setTintList(colorStateList);
        }

        @Override
        public void setTintMode(Drawable drawable2, PorterDuff.Mode mode) {
            drawable2.setTintMode(mode);
        }

        @Override
        public Drawable wrap(Drawable drawable2) {
            Drawable drawable3 = drawable2;
            if (!(drawable2 instanceof TintAwareDrawable)) {
                drawable3 = new DrawableWrapperApi21(drawable2);
            }
            return drawable3;
        }
    }

    static class DrawableCompatApi23Impl
    extends DrawableCompatApi21Impl {
        DrawableCompatApi23Impl() {
        }

        @Override
        public void clearColorFilter(Drawable drawable2) {
            drawable2.clearColorFilter();
        }

        @Override
        public int getLayoutDirection(Drawable drawable2) {
            return drawable2.getLayoutDirection();
        }

        @Override
        public boolean setLayoutDirection(Drawable drawable2, int n) {
            return drawable2.setLayoutDirection(n);
        }

        @Override
        public Drawable wrap(Drawable drawable2) {
            return drawable2;
        }
    }

    static class DrawableCompatBaseImpl {
        DrawableCompatBaseImpl() {
        }

        public void applyTheme(Drawable drawable2, Resources.Theme theme) {
        }

        public boolean canApplyTheme(Drawable drawable2) {
            return false;
        }

        public void clearColorFilter(Drawable drawable2) {
            drawable2.clearColorFilter();
        }

        public int getAlpha(Drawable drawable2) {
            return 0;
        }

        public ColorFilter getColorFilter(Drawable drawable2) {
            return null;
        }

        public int getLayoutDirection(Drawable drawable2) {
            return 0;
        }

        public void inflate(Drawable drawable2, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
            drawable2.inflate(resources, xmlPullParser, attributeSet);
        }

        public boolean isAutoMirrored(Drawable drawable2) {
            return false;
        }

        public void jumpToCurrentState(Drawable drawable2) {
            drawable2.jumpToCurrentState();
        }

        public void setAutoMirrored(Drawable drawable2, boolean bl) {
        }

        public void setHotspot(Drawable drawable2, float f, float f2) {
        }

        public void setHotspotBounds(Drawable drawable2, int n, int n2, int n3, int n4) {
        }

        public boolean setLayoutDirection(Drawable drawable2, int n) {
            return false;
        }

        public void setTint(Drawable drawable2, int n) {
            if (drawable2 instanceof TintAwareDrawable) {
                ((TintAwareDrawable)drawable2).setTint(n);
            }
        }

        public void setTintList(Drawable drawable2, ColorStateList colorStateList) {
            if (drawable2 instanceof TintAwareDrawable) {
                ((TintAwareDrawable)drawable2).setTintList(colorStateList);
            }
        }

        public void setTintMode(Drawable drawable2, PorterDuff.Mode mode) {
            if (drawable2 instanceof TintAwareDrawable) {
                ((TintAwareDrawable)drawable2).setTintMode(mode);
            }
        }

        public Drawable wrap(Drawable drawable2) {
            Drawable drawable3 = drawable2;
            if (!(drawable2 instanceof TintAwareDrawable)) {
                drawable3 = new DrawableWrapperApi14(drawable2);
            }
            return drawable3;
        }
    }

}

