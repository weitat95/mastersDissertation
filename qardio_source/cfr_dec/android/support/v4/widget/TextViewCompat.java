/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Field;

public final class TextViewCompat {
    static final TextViewCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 26 ? new TextViewCompatApi26Impl() : (Build.VERSION.SDK_INT >= 23 ? new TextViewCompatApi23Impl() : (Build.VERSION.SDK_INT >= 18 ? new TextViewCompatApi18Impl() : (Build.VERSION.SDK_INT >= 17 ? new TextViewCompatApi17Impl() : (Build.VERSION.SDK_INT >= 16 ? new TextViewCompatApi16Impl() : new TextViewCompatBaseImpl()))));

    public static Drawable[] getCompoundDrawablesRelative(TextView textView) {
        return IMPL.getCompoundDrawablesRelative(textView);
    }

    public static int getMaxLines(TextView textView) {
        return IMPL.getMaxLines(textView);
    }

    public static void setCompoundDrawablesRelative(TextView textView, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        IMPL.setCompoundDrawablesRelative(textView, drawable2, drawable3, drawable4, drawable5);
    }

    public static void setTextAppearance(TextView textView, int n) {
        IMPL.setTextAppearance(textView, n);
    }

    static class TextViewCompatApi16Impl
    extends TextViewCompatBaseImpl {
        TextViewCompatApi16Impl() {
        }

        @Override
        public int getMaxLines(TextView textView) {
            return textView.getMaxLines();
        }
    }

    static class TextViewCompatApi17Impl
    extends TextViewCompatApi16Impl {
        TextViewCompatApi17Impl() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Drawable[] getCompoundDrawablesRelative(TextView arrdrawable) {
            boolean bl = true;
            if (arrdrawable.getLayoutDirection() != 1) {
                bl = false;
            }
            arrdrawable = arrdrawable.getCompoundDrawables();
            if (bl) {
                Drawable drawable2 = arrdrawable[2];
                Drawable drawable3 = arrdrawable[0];
                arrdrawable[0] = drawable2;
                arrdrawable[2] = drawable3;
            }
            return arrdrawable;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setCompoundDrawablesRelative(TextView textView, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
            boolean bl = true;
            if (textView.getLayoutDirection() != 1) {
                bl = false;
            }
            Drawable drawable6 = bl ? drawable4 : drawable2;
            if (!bl) {
                drawable2 = drawable4;
            }
            textView.setCompoundDrawables(drawable6, drawable3, drawable2, drawable5);
        }
    }

    static class TextViewCompatApi18Impl
    extends TextViewCompatApi17Impl {
        TextViewCompatApi18Impl() {
        }

        @Override
        public Drawable[] getCompoundDrawablesRelative(TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }

        @Override
        public void setCompoundDrawablesRelative(TextView textView, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
            textView.setCompoundDrawablesRelative(drawable2, drawable3, drawable4, drawable5);
        }
    }

    static class TextViewCompatApi23Impl
    extends TextViewCompatApi18Impl {
        TextViewCompatApi23Impl() {
        }

        @Override
        public void setTextAppearance(TextView textView, int n) {
            textView.setTextAppearance(n);
        }
    }

    static class TextViewCompatApi26Impl
    extends TextViewCompatApi23Impl {
        TextViewCompatApi26Impl() {
        }
    }

    static class TextViewCompatBaseImpl {
        private static Field sMaxModeField;
        private static boolean sMaxModeFieldFetched;
        private static Field sMaximumField;
        private static boolean sMaximumFieldFetched;

        TextViewCompatBaseImpl() {
        }

        private static Field retrieveField(String string2) {
            Field field;
            Field field2 = null;
            try {
                field2 = field = TextView.class.getDeclaredField(string2);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"TextViewCompatBase", (String)("Could not retrieve " + string2 + " field."));
                return field2;
            }
            field.setAccessible(true);
            return field;
        }

        private static int retrieveIntFromField(Field field, TextView textView) {
            try {
                int n = field.getInt((Object)textView);
                return n;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.d((String)"TextViewCompatBase", (String)("Could not retrieve value of " + field.getName() + " field."));
                return -1;
            }
        }

        public Drawable[] getCompoundDrawablesRelative(TextView textView) {
            return textView.getCompoundDrawables();
        }

        public int getMaxLines(TextView textView) {
            if (!sMaxModeFieldFetched) {
                sMaxModeField = TextViewCompatBaseImpl.retrieveField("mMaxMode");
                sMaxModeFieldFetched = true;
            }
            if (sMaxModeField != null && TextViewCompatBaseImpl.retrieveIntFromField(sMaxModeField, textView) == 1) {
                if (!sMaximumFieldFetched) {
                    sMaximumField = TextViewCompatBaseImpl.retrieveField("mMaximum");
                    sMaximumFieldFetched = true;
                }
                if (sMaximumField != null) {
                    return TextViewCompatBaseImpl.retrieveIntFromField(sMaximumField, textView);
                }
            }
            return -1;
        }

        public void setCompoundDrawablesRelative(TextView textView, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
            textView.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
        }

        public void setTextAppearance(TextView textView, int n) {
            textView.setTextAppearance(textView.getContext(), n);
        }
    }

}

