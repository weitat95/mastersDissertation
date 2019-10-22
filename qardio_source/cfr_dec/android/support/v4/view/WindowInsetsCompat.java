/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.WindowInsets
 */
package android.support.v4.view;

import android.os.Build;
import android.view.WindowInsets;

public class WindowInsetsCompat {
    private final Object mInsets;

    private WindowInsetsCompat(Object object) {
        this.mInsets = object;
    }

    static Object unwrap(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat == null) {
            return null;
        }
        return windowInsetsCompat.mInsets;
    }

    static WindowInsetsCompat wrap(Object object) {
        if (object == null) {
            return null;
        }
        return new WindowInsetsCompat(object);
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return new WindowInsetsCompat((Object)((WindowInsets)this.mInsets).consumeSystemWindowInsets());
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6: {
            block5: {
                if (this == object) break block5;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (WindowInsetsCompat)object;
                if (this.mInsets != null) {
                    return this.mInsets.equals(((WindowInsetsCompat)object).mInsets);
                }
                if (((WindowInsetsCompat)object).mInsets != null) break block6;
            }
            return true;
        }
        return false;
    }

    public int getSystemWindowInsetBottom() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets)this.mInsets).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public int getSystemWindowInsetLeft() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets)this.mInsets).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int getSystemWindowInsetRight() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets)this.mInsets).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int getSystemWindowInsetTop() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets)this.mInsets).getSystemWindowInsetTop();
        }
        return 0;
    }

    public boolean hasSystemWindowInsets() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets)this.mInsets).hasSystemWindowInsets();
        }
        return false;
    }

    public int hashCode() {
        if (this.mInsets == null) {
            return 0;
        }
        return this.mInsets.hashCode();
    }

    public boolean isConsumed() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets)this.mInsets).isConsumed();
        }
        return false;
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int n, int n2, int n3, int n4) {
        if (Build.VERSION.SDK_INT >= 20) {
            return new WindowInsetsCompat((Object)((WindowInsets)this.mInsets).replaceSystemWindowInsets(n, n2, n3, n4));
        }
        return null;
    }
}

