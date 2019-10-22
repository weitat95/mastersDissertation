/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.StaticLayout$Builder
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

class AppCompatTextViewAutoSizeHelper {
    private static final RectF TEMP_RECTF = new RectF();
    private static Hashtable<String, Method> sTextViewMethodByNameCache = new Hashtable();
    private float mAutoSizeMaxTextSizeInPx = -1.0f;
    private float mAutoSizeMinTextSizeInPx = -1.0f;
    private float mAutoSizeStepGranularityInPx = -1.0f;
    private int[] mAutoSizeTextSizesInPx = new int[0];
    private int mAutoSizeTextType = 0;
    private final Context mContext;
    private boolean mHasPresetAutoSizeValues = false;
    private boolean mNeedsAutoSizeText = false;
    private TextPaint mTempTextPaint;
    private final TextView mTextView;

    AppCompatTextViewAutoSizeHelper(TextView textView) {
        this.mTextView = textView;
        this.mContext = this.mTextView.getContext();
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] cleanupAutoSizePresetSizes(int[] arrn) {
        int n;
        int n2;
        ArrayList<Integer> arrayList;
        block7: {
            block6: {
                n = arrn.length;
                if (n == 0) break block6;
                Arrays.sort(arrn);
                arrayList = new ArrayList<Integer>();
                for (n2 = 0; n2 < n; ++n2) {
                    int n3 = arrn[n2];
                    if (n3 <= 0 || Collections.binarySearch(arrayList, n3) >= 0) continue;
                    arrayList.add(n3);
                }
                if (n != arrayList.size()) break block7;
            }
            return arrn;
        }
        n = arrayList.size();
        arrn = new int[n];
        n2 = 0;
        while (n2 < n) {
            arrn[n2] = (Integer)arrayList.get(n2);
            ++n2;
        }
        return arrn;
    }

    private void clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = new int[0];
        this.mNeedsAutoSizeText = false;
    }

    @TargetApi(value=23)
    private StaticLayout createStaticLayoutForMeasuring(CharSequence charSequence, Layout.Alignment alignment, int n, int n2) {
        TextDirectionHeuristic textDirectionHeuristic = this.invokeAndReturnWithDefault((Object)this.mTextView, "getTextDirectionHeuristic", (T)TextDirectionHeuristics.FIRSTSTRONG_LTR);
        charSequence = StaticLayout.Builder.obtain((CharSequence)charSequence, (int)0, (int)charSequence.length(), (TextPaint)this.mTempTextPaint, (int)n).setAlignment(alignment).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency());
        n = n2;
        if (n2 == -1) {
            n = Integer.MAX_VALUE;
        }
        return charSequence.setMaxLines(n).setTextDirection(textDirectionHeuristic).build();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @TargetApi(value=14)
    private StaticLayout createStaticLayoutForMeasuringPre23(CharSequence charSequence, Layout.Alignment alignment, int n) {
        float f;
        float f2;
        boolean bl;
        if (Build.VERSION.SDK_INT >= 16) {
            f2 = this.mTextView.getLineSpacingMultiplier();
            f = this.mTextView.getLineSpacingExtra();
            bl = this.mTextView.getIncludeFontPadding();
            do {
                return new StaticLayout(charSequence, this.mTempTextPaint, n, alignment, f2, f, bl);
                break;
            } while (true);
        }
        f2 = this.invokeAndReturnWithDefault((Object)this.mTextView, "getLineSpacingMultiplier", Float.valueOf(1.0f)).floatValue();
        f = this.invokeAndReturnWithDefault((Object)this.mTextView, "getLineSpacingExtra", Float.valueOf(0.0f)).floatValue();
        bl = this.invokeAndReturnWithDefault((Object)this.mTextView, "getIncludeFontPadding", true);
        return new StaticLayout(charSequence, this.mTempTextPaint, n, alignment, f2, f, bl);
    }

    private int findLargestTextSizeWhichFits(RectF rectF) {
        int n = this.mAutoSizeTextSizesInPx.length;
        if (n == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int n2 = 0;
        int n3 = 0 + 1;
        --n;
        while (n3 <= n) {
            n2 = (n3 + n) / 2;
            if (this.suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[n2], rectF)) {
                int n4 = n2 + 1;
                n2 = n3;
                n3 = n4;
                continue;
            }
            n2 = n = n2 - 1;
        }
        return this.mAutoSizeTextSizesInPx[n2];
    }

    private Method getTextViewMethod(String string2) {
        Method method;
        block4: {
            Method method2;
            try {
                method = method2 = sTextViewMethodByNameCache.get(string2);
                if (method2 != null) break block4;
            }
            catch (Exception exception) {
                Log.w((String)"ACTVAutoSizeHelper", (String)("Failed to retrieve TextView#" + string2 + "() method"), (Throwable)exception);
                return null;
            }
            method = method2 = TextView.class.getDeclaredMethod(string2, new Class[0]);
            if (method2 == null) break block4;
            method2.setAccessible(true);
            sTextViewMethodByNameCache.put(string2, method2);
            method = method2;
        }
        return method;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private <T> T invokeAndReturnWithDefault(Object object, String object2, T t) {
        Object var5_6 = null;
        boolean bl = false;
        try {
            try {
                object = object2 = (object = this.getTextViewMethod((String)object2).invoke(object, new Object[0]));
                if (object2 != null) return (T)object;
                {
                    object = object2;
                    if (!false) return (T)object;
                    {
                        object = t;
                        return (T)object;
                    }
                }
            }
            catch (Exception exception) {
                bl = true;
                Log.w((String)"ACTVAutoSizeHelper", (String)("Failed to invoke TextView#" + (String)object2 + "() method"), (Throwable)exception);
                object = var5_6;
                if (false) return (T)object;
                object = var5_6;
                if (!true) return (T)object;
                return t;
            }
        }
        catch (Throwable throwable) {
            if (!false && !bl) throw throwable;
            {
                // empty if block
            }
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setRawTextSize(float f) {
        if (f != this.mTextView.getPaint().getTextSize()) {
            this.mTextView.getPaint().setTextSize(f);
            boolean bl = false;
            if (Build.VERSION.SDK_INT >= 18) {
                bl = this.mTextView.isInLayout();
            }
            if (this.mTextView.getLayout() != null) {
                this.mNeedsAutoSizeText = false;
                try {
                    Method method = this.getTextViewMethod("nullLayouts");
                    if (method != null) {
                        method.invoke((Object)this.mTextView, new Object[0]);
                    }
                }
                catch (Exception exception) {
                    Log.w((String)"ACTVAutoSizeHelper", (String)"Failed to invoke TextView#nullLayouts() method", (Throwable)exception);
                }
                if (!bl) {
                    this.mTextView.requestLayout();
                } else {
                    this.mTextView.forceLayout();
                }
                this.mTextView.invalidate();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean setupAutoSizeText() {
        if (this.supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int n = 1;
                float f = Math.round(this.mAutoSizeMinTextSizeInPx);
                while (Math.round(this.mAutoSizeStepGranularityInPx + f) <= Math.round(this.mAutoSizeMaxTextSizeInPx)) {
                    ++n;
                    f += this.mAutoSizeStepGranularityInPx;
                }
                int[] arrn = new int[n];
                f = this.mAutoSizeMinTextSizeInPx;
                for (int i = 0; i < n; ++i) {
                    arrn[i] = Math.round(f);
                    f += this.mAutoSizeStepGranularityInPx;
                }
                this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(arrn);
            }
            this.mNeedsAutoSizeText = true;
            do {
                return this.mNeedsAutoSizeText;
                break;
            } while (true);
        }
        this.mNeedsAutoSizeText = false;
        return this.mNeedsAutoSizeText;
    }

    private void setupAutoSizeUniformPresetSizes(TypedArray typedArray) {
        int n = typedArray.length();
        int[] arrn = new int[n];
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                arrn[i] = typedArray.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(arrn);
            this.setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        int n = this.mAutoSizeTextSizesInPx.length;
        boolean bl = n > 0;
        this.mHasPresetAutoSizeValues = bl;
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = this.mAutoSizeTextSizesInPx[0];
            this.mAutoSizeMaxTextSizeInPx = this.mAutoSizeTextSizesInPx[n - 1];
            this.mAutoSizeStepGranularityInPx = -1.0f;
        }
        return this.mHasPresetAutoSizeValues;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean suggestedSizeFitsInSpace(int n, RectF rectF) {
        CharSequence charSequence = this.mTextView.getText();
        int n2 = Build.VERSION.SDK_INT >= 16 ? this.mTextView.getMaxLines() : -1;
        if (this.mTempTextPaint == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            this.mTempTextPaint.reset();
        }
        this.mTempTextPaint.set(this.mTextView.getPaint());
        this.mTempTextPaint.setTextSize((float)n);
        Layout.Alignment alignment = this.invokeAndReturnWithDefault((Object)this.mTextView, "getLayoutAlignment", (T)Layout.Alignment.ALIGN_NORMAL);
        alignment = Build.VERSION.SDK_INT >= 23 ? this.createStaticLayoutForMeasuring(charSequence, alignment, Math.round(rectF.right), n2) : this.createStaticLayoutForMeasuringPre23(charSequence, alignment, Math.round(rectF.right));
        if (n2 != -1 && (alignment.getLineCount() > n2 || alignment.getLineEnd(alignment.getLineCount() - 1) != charSequence.length())) {
            return false;
        }
        return !((float)alignment.getHeight() > rectF.bottom);
    }

    private boolean supportsAutoSizeText() {
        return !(this.mTextView instanceof AppCompatEditText);
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float f, float f2, float f3) throws IllegalArgumentException {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)");
        }
        if (f2 <= f) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + f2 + "px) is less or equal to minimum auto-size " + "text size (" + f + "px)");
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)");
        }
        this.mAutoSizeTextType = 1;
        this.mAutoSizeMinTextSizeInPx = f;
        this.mAutoSizeMaxTextSizeInPx = f2;
        this.mAutoSizeStepGranularityInPx = f3;
        this.mHasPresetAutoSizeValues = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void autoSizeText() {
        block8: {
            int n;
            int n2;
            block9: {
                block7: {
                    if (!this.isAutoSizeEnabled()) break block7;
                    if (!this.mNeedsAutoSizeText) break block8;
                    if (this.mTextView.getMeasuredHeight() <= 0 || this.mTextView.getMeasuredWidth() <= 0) break block7;
                    n2 = this.invokeAndReturnWithDefault((Object)this.mTextView, "getHorizontallyScrolling", false) != false ? 1048576 : this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft() - this.mTextView.getTotalPaddingRight();
                    n = this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom() - this.mTextView.getCompoundPaddingTop();
                    if (n2 > 0 && n > 0) break block9;
                }
                return;
            }
            RectF rectF = TEMP_RECTF;
            synchronized (rectF) {
                TEMP_RECTF.setEmpty();
                AppCompatTextViewAutoSizeHelper.TEMP_RECTF.right = n2;
                AppCompatTextViewAutoSizeHelper.TEMP_RECTF.bottom = n;
                float f = this.findLargestTextSizeWhichFits(TEMP_RECTF);
                if (f != this.mTextView.getTextSize()) {
                    this.setTextSizeInternal(0, f);
                }
            }
        }
        this.mNeedsAutoSizeText = true;
    }

    int getAutoSizeMaxTextSize() {
        return Math.round(this.mAutoSizeMaxTextSizeInPx);
    }

    int getAutoSizeMinTextSize() {
        return Math.round(this.mAutoSizeMinTextSizeInPx);
    }

    int getAutoSizeStepGranularity() {
        return Math.round(this.mAutoSizeStepGranularityInPx);
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx;
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextType;
    }

    boolean isAutoSizeEnabled() {
        return this.supportsAutoSizeText() && this.mAutoSizeTextType != 0;
    }

    void loadFromAttributes(AttributeSet attributeSet, int n) {
        float f = -1.0f;
        float f2 = -1.0f;
        float f3 = -1.0f;
        if ((attributeSet = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.AppCompatTextView, n, 0)).hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
            this.mAutoSizeTextType = attributeSet.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
        }
        if (attributeSet.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
            f3 = attributeSet.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0f);
        }
        if (attributeSet.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
            f = attributeSet.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0f);
        }
        if (attributeSet.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
            f2 = attributeSet.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0f);
        }
        if (attributeSet.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes) && (n = attributeSet.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray typedArray = attributeSet.getResources().obtainTypedArray(n);
            this.setupAutoSizeUniformPresetSizes(typedArray);
            typedArray.recycle();
        }
        attributeSet.recycle();
        if (this.supportsAutoSizeText()) {
            if (this.mAutoSizeTextType == 1) {
                if (!this.mHasPresetAutoSizeValues) {
                    attributeSet = this.mContext.getResources().getDisplayMetrics();
                    float f4 = f;
                    if (f == -1.0f) {
                        f4 = TypedValue.applyDimension((int)2, (float)12.0f, (DisplayMetrics)attributeSet);
                    }
                    f = f2;
                    if (f2 == -1.0f) {
                        f = TypedValue.applyDimension((int)2, (float)112.0f, (DisplayMetrics)attributeSet);
                    }
                    f2 = f3;
                    if (f3 == -1.0f) {
                        f2 = 1.0f;
                    }
                    this.validateAndSetAutoSizeTextTypeUniformConfiguration(f4, f, f2);
                }
                this.setupAutoSizeText();
            }
            return;
        }
        this.mAutoSizeTextType = 0;
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int n, int n2, int n3, int n4) throws IllegalArgumentException {
        if (this.supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension((int)n4, (float)n, (DisplayMetrics)displayMetrics), TypedValue.applyDimension((int)n4, (float)n2, (DisplayMetrics)displayMetrics), TypedValue.applyDimension((int)n4, (float)n3, (DisplayMetrics)displayMetrics));
            if (this.setupAutoSizeText()) {
                this.autoSizeText();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void setAutoSizeTextTypeUniformWithPresetSizes(int[] arrn, int n) throws IllegalArgumentException {
        if (this.supportsAutoSizeText()) {
            int n2 = arrn.length;
            if (n2 > 0) {
                int[] arrn2;
                int[] arrn3 = new int[n2];
                if (n == 0) {
                    arrn2 = Arrays.copyOf(arrn, n2);
                } else {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    int n3 = 0;
                    do {
                        arrn2 = arrn3;
                        if (n3 >= n2) break;
                        arrn3[n3] = Math.round(TypedValue.applyDimension((int)n, (float)arrn[n3], (DisplayMetrics)displayMetrics));
                        ++n3;
                    } while (true);
                }
                this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(arrn2);
                if (!this.setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(arrn));
                }
            } else {
                this.mHasPresetAutoSizeValues = false;
            }
            if (this.setupAutoSizeText()) {
                this.autoSizeText();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void setAutoSizeTextTypeWithDefaults(int n) {
        if (!this.supportsAutoSizeText()) return;
        {
            switch (n) {
                default: {
                    throw new IllegalArgumentException("Unknown auto-size text type: " + n);
                }
                case 0: {
                    this.clearAutoSizeConfiguration();
                    return;
                }
                case 1: {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension((int)2, (float)12.0f, (DisplayMetrics)displayMetrics), TypedValue.applyDimension((int)2, (float)112.0f, (DisplayMetrics)displayMetrics), 1.0f);
                    if (!this.setupAutoSizeText()) return;
                    this.autoSizeText();
                    return;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void setTextSizeInternal(int n, float f) {
        Resources resources = this.mContext == null ? Resources.getSystem() : this.mContext.getResources();
        this.setRawTextSize(TypedValue.applyDimension((int)n, (float)f, (DisplayMetrics)resources.getDisplayMetrics()));
    }
}

