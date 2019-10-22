/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ScaleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v7.widget;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.ThemeUtils;

public class DrawableUtils {
    public static final Rect INSETS_NONE;
    private static Class<?> sInsetsClazz;

    static {
        block2: {
            INSETS_NONE = new Rect();
            if (Build.VERSION.SDK_INT < 18) break block2;
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            }
            catch (ClassNotFoundException classNotFoundException) {}
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean canSafelyMutateDrawable(Drawable arrdrawable) {
        int n;
        if (Build.VERSION.SDK_INT < 15 && arrdrawable instanceof InsetDrawable || Build.VERSION.SDK_INT < 15 && arrdrawable instanceof GradientDrawable || Build.VERSION.SDK_INT < 17 && arrdrawable instanceof LayerDrawable) return false;
        if (arrdrawable instanceof DrawableContainer) {
            if (!((arrdrawable = arrdrawable.getConstantState()) instanceof DrawableContainer.DrawableContainerState)) return true;
            arrdrawable = ((DrawableContainer.DrawableContainerState)arrdrawable).getChildren();
            n = arrdrawable.length;
        } else {
            if (arrdrawable instanceof android.support.v4.graphics.drawable.DrawableWrapper) {
                return DrawableUtils.canSafelyMutateDrawable(((android.support.v4.graphics.drawable.DrawableWrapper)arrdrawable).getWrappedDrawable());
            }
            if (arrdrawable instanceof DrawableWrapper) {
                return DrawableUtils.canSafelyMutateDrawable(((DrawableWrapper)arrdrawable).getWrappedDrawable());
            }
            if (!(arrdrawable instanceof ScaleDrawable)) return true;
            return DrawableUtils.canSafelyMutateDrawable(((ScaleDrawable)arrdrawable).getDrawable());
        }
        for (int i = 0; i < n; ++i) {
            if (DrawableUtils.canSafelyMutateDrawable(arrdrawable[i])) continue;
            return false;
        }
        return true;
    }

    static void fixDrawable(Drawable drawable2) {
        if (Build.VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(drawable2.getClass().getName())) {
            DrawableUtils.fixVectorDrawableTinting(drawable2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void fixVectorDrawableTinting(Drawable drawable2) {
        int[] arrn = drawable2.getState();
        if (arrn == null || arrn.length == 0) {
            drawable2.setState(ThemeUtils.CHECKED_STATE_SET);
        } else {
            drawable2.setState(ThemeUtils.EMPTY_STATE_SET);
        }
        drawable2.setState(arrn);
    }

    /*
     * Exception decompiling
     */
    public static Rect getOpticalBounds(Drawable var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous, and can't clone.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:509)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static PorterDuff.Mode parseTintMode(int n, PorterDuff.Mode mode) {
        switch (n) {
            default: {
                return mode;
            }
            case 3: {
                return PorterDuff.Mode.SRC_OVER;
            }
            case 5: {
                return PorterDuff.Mode.SRC_IN;
            }
            case 9: {
                return PorterDuff.Mode.SRC_ATOP;
            }
            case 14: {
                return PorterDuff.Mode.MULTIPLY;
            }
            case 15: {
                return PorterDuff.Mode.SCREEN;
            }
            case 16: {
                if (Build.VERSION.SDK_INT < 11) return mode;
                return PorterDuff.Mode.valueOf((String)"ADD");
            }
        }
    }
}

