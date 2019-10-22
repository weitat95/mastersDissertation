/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.print;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

public final class PrintHelper {
    private final PrintHelperVersionImpl mImpl;

    public PrintHelper(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mImpl = new PrintHelperApi24(context);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new PrintHelperApi23(context);
            return;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mImpl = new PrintHelperApi20(context);
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.mImpl = new PrintHelperApi19(context);
            return;
        }
        this.mImpl = new PrintHelperStub();
    }

    private static class PrintHelperApi19
    implements PrintHelperVersionImpl {
        int mColorMode = 2;
        final Context mContext;
        BitmapFactory.Options mDecodeOptions = null;
        protected boolean mIsMinMarginsHandlingCorrect = true;
        private final Object mLock = new Object();
        protected boolean mPrintActivityRespectsOrientation = true;
        int mScaleMode = 2;

        PrintHelperApi19(Context context) {
            this.mContext = context;
        }
    }

    private static class PrintHelperApi20
    extends PrintHelperApi19 {
        PrintHelperApi20(Context context) {
            super(context);
            this.mPrintActivityRespectsOrientation = false;
        }
    }

    private static class PrintHelperApi23
    extends PrintHelperApi20 {
        PrintHelperApi23(Context context) {
            super(context);
            this.mIsMinMarginsHandlingCorrect = false;
        }
    }

    private static class PrintHelperApi24
    extends PrintHelperApi23 {
        PrintHelperApi24(Context context) {
            super(context);
            this.mIsMinMarginsHandlingCorrect = true;
            this.mPrintActivityRespectsOrientation = true;
        }
    }

    private static final class PrintHelperStub
    implements PrintHelperVersionImpl {
        int mColorMode = 2;
        int mOrientation = 1;
        int mScaleMode = 2;

        private PrintHelperStub() {
        }
    }

    static interface PrintHelperVersionImpl {
    }

}

