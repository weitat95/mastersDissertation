/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.PathMeasure
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.VectorDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.graphics.drawable.AndroidResources;
import android.support.graphics.drawable.VectorDrawableCommon;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat
extends VectorDrawableCommon {
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private boolean mAllowCaching = true;
    private Drawable.ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private final float[] mTmpFloats = new float[9];
    private final Matrix mTmpMatrix = new Matrix();
    private VectorDrawableCompatState mVectorState;

    VectorDrawableCompat() {
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    VectorDrawableCompat(VectorDrawableCompatState vectorDrawableCompatState) {
        this.mTmpBounds = new Rect();
        this.mVectorState = vectorDrawableCompatState;
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
    }

    static int applyAlpha(int n, float f) {
        return n & 0xFFFFFF | (int)((float)Color.alpha((int)n) * f) << 24;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static VectorDrawableCompat create(Resources object, int n, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = ResourcesCompat.getDrawable(object, n, theme);
            vectorDrawableCompat.mCachedConstantStateDelegate = new VectorDrawableDelegateState(vectorDrawableCompat.mDelegateDrawable.getConstantState());
            return vectorDrawableCompat;
        }
        try {
            XmlResourceParser xmlResourceParser = object.getXml(n);
            AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
            while ((n = xmlResourceParser.next()) != 2 && n != 1) {
            }
            if (n == 2) return VectorDrawableCompat.createFromXmlInner(object, (XmlPullParser)xmlResourceParser, attributeSet, theme);
            throw new XmlPullParserException("No start tag found");
        }
        catch (XmlPullParserException xmlPullParserException) {
            Log.e((String)"VectorDrawableCompat", (String)"parser error", (Throwable)xmlPullParserException);
            return null;
        }
        catch (IOException iOException) {
            Log.e((String)"VectorDrawableCompat", (String)"parser error", (Throwable)iOException);
            return null;
        }
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void inflateInternal(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
        boolean bl = true;
        Stack<Object> stack = new Stack<Object>();
        stack.push(vPathRenderer.mRootGroup);
        int n = xmlPullParser.getEventType();
        int n2 = xmlPullParser.getDepth();
        while (n != 1 && (xmlPullParser.getDepth() >= n2 + 1 || n != 3)) {
            boolean bl2;
            if (n == 2) {
                Object object2 = xmlPullParser.getName();
                VGroup vGroup = (VGroup)stack.peek();
                if ("path".equals(object2)) {
                    object2 = new VFullPath();
                    ((VFullPath)object2).inflate((Resources)object, attributeSet, theme, xmlPullParser);
                    vGroup.mChildren.add(object2);
                    if (((VPath)object2).getPathName() != null) {
                        vPathRenderer.mVGTargetsMap.put(((VPath)object2).getPathName(), object2);
                    }
                    bl2 = false;
                    vectorDrawableCompatState.mChangingConfigurations |= ((VFullPath)object2).mChangingConfigurations;
                } else if ("clip-path".equals(object2)) {
                    object2 = new VClipPath();
                    ((VClipPath)object2).inflate((Resources)object, attributeSet, theme, xmlPullParser);
                    vGroup.mChildren.add(object2);
                    if (((VPath)object2).getPathName() != null) {
                        vPathRenderer.mVGTargetsMap.put(((VPath)object2).getPathName(), object2);
                    }
                    vectorDrawableCompatState.mChangingConfigurations |= ((VClipPath)object2).mChangingConfigurations;
                    bl2 = bl;
                } else {
                    bl2 = bl;
                    if ("group".equals(object2)) {
                        object2 = new VGroup();
                        ((VGroup)object2).inflate((Resources)object, attributeSet, theme, xmlPullParser);
                        vGroup.mChildren.add(object2);
                        stack.push(object2);
                        if (((VGroup)object2).getGroupName() != null) {
                            vPathRenderer.mVGTargetsMap.put(((VGroup)object2).getGroupName(), object2);
                        }
                        vectorDrawableCompatState.mChangingConfigurations |= ((VGroup)object2).mChangingConfigurations;
                        bl2 = bl;
                    }
                }
            } else {
                bl2 = bl;
                if (n == 3) {
                    bl2 = bl;
                    if ("group".equals(xmlPullParser.getName())) {
                        stack.pop();
                        bl2 = bl;
                    }
                }
            }
            n = xmlPullParser.next();
            bl = bl2;
        }
        if (!bl) {
            return;
        }
        object = new StringBuffer();
        if (((StringBuffer)object).length() > 0) {
            ((StringBuffer)object).append(" or ");
        }
        ((StringBuffer)object).append("path");
        throw new XmlPullParserException("no " + object + " defined");
    }

    private boolean needMirroring() {
        if (Build.VERSION.SDK_INT >= 17) {
            return this.isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static PorterDuff.Mode parseTintModeCompat(int n, PorterDuff.Mode mode) {
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
                return PorterDuff.Mode.ADD;
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray object, XmlPullParser xmlPullParser) throws XmlPullParserException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
        vectorDrawableCompatState.mTintMode = VectorDrawableCompat.parseTintModeCompat(TypedArrayUtils.getNamedInt(object, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList colorStateList = object.getColorStateList(1);
        if (colorStateList != null) {
            vectorDrawableCompatState.mTint = colorStateList;
        }
        vectorDrawableCompatState.mAutoMirrored = TypedArrayUtils.getNamedBoolean(object, xmlPullParser, "autoMirrored", 5, vectorDrawableCompatState.mAutoMirrored);
        vPathRenderer.mViewportWidth = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "viewportWidth", 7, vPathRenderer.mViewportWidth);
        vPathRenderer.mViewportHeight = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "viewportHeight", 8, vPathRenderer.mViewportHeight);
        if (vPathRenderer.mViewportWidth <= 0.0f) {
            throw new XmlPullParserException(object.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (vPathRenderer.mViewportHeight <= 0.0f) {
            throw new XmlPullParserException(object.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        vPathRenderer.mBaseWidth = object.getDimension(3, vPathRenderer.mBaseWidth);
        vPathRenderer.mBaseHeight = object.getDimension(2, vPathRenderer.mBaseHeight);
        if (vPathRenderer.mBaseWidth <= 0.0f) {
            throw new XmlPullParserException(object.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (vPathRenderer.mBaseHeight <= 0.0f) {
            throw new XmlPullParserException(object.getPositionDescription() + "<vector> tag requires height > 0");
        }
        vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(object, xmlPullParser, "alpha", 4, vPathRenderer.getAlpha()));
        object = object.getString(0);
        if (object != null) {
            vPathRenderer.mRootName = object;
            vPathRenderer.mVGTargetsMap.put((String)object, vPathRenderer);
        }
    }

    public boolean canApplyTheme() {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.canApplyTheme(this.mDelegateDrawable);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.draw(canvas);
            return;
        }
        this.copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) return;
        Object object = this.mColorFilter == null ? this.mTintFilter : this.mColorFilter;
        canvas.getMatrix(this.mTmpMatrix);
        this.mTmpMatrix.getValues(this.mTmpFloats);
        float f = Math.abs(this.mTmpFloats[0]);
        float f2 = Math.abs(this.mTmpFloats[4]);
        float f3 = Math.abs(this.mTmpFloats[1]);
        float f4 = Math.abs(this.mTmpFloats[3]);
        if (f3 != 0.0f || f4 != 0.0f) {
            f = 1.0f;
            f2 = 1.0f;
        }
        int n = (int)((float)this.mTmpBounds.width() * f);
        int n2 = (int)((float)this.mTmpBounds.height() * f2);
        n = Math.min(2048, n);
        n2 = Math.min(2048, n2);
        if (n <= 0 || n2 <= 0) return;
        int n3 = canvas.save();
        canvas.translate((float)this.mTmpBounds.left, (float)this.mTmpBounds.top);
        if (this.needMirroring()) {
            canvas.translate((float)this.mTmpBounds.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mTmpBounds.offsetTo(0, 0);
        this.mVectorState.createCachedBitmapIfNeeded(n, n2);
        if (!this.mAllowCaching) {
            this.mVectorState.updateCachedBitmap(n, n2);
        } else if (!this.mVectorState.canReuseCache()) {
            this.mVectorState.updateCachedBitmap(n, n2);
            this.mVectorState.updateCacheStates();
        }
        this.mVectorState.drawCachedBitmapWithRootAlpha(canvas, (ColorFilter)object, this.mTmpBounds);
        canvas.restoreToCount(n3);
    }

    public int getAlpha() {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.getAlpha(this.mDelegateDrawable);
        }
        return this.mVectorState.mVPathRenderer.getRootAlpha();
    }

    public int getChangingConfigurations() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        if (this.mDelegateDrawable != null && Build.VERSION.SDK_INT >= 24) {
            return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        this.mVectorState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mVectorState;
    }

    public int getIntrinsicHeight() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getIntrinsicHeight();
        }
        return (int)this.mVectorState.mVPathRenderer.mBaseHeight;
    }

    public int getIntrinsicWidth() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getIntrinsicWidth();
        }
        return (int)this.mVectorState.mVPathRenderer.mBaseWidth;
    }

    public int getOpacity() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getOpacity();
        }
        return -3;
    }

    Object getTargetByName(String string2) {
        return this.mVectorState.mVPathRenderer.mVGTargetsMap.get(string2);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.inflate(resources, xmlPullParser, attributeSet);
            return;
        }
        this.inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.inflate(this.mDelegateDrawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        vectorDrawableCompatState.mVPathRenderer = new VPathRenderer();
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
        this.updateStateFromTypedArray(typedArray, xmlPullParser);
        typedArray.recycle();
        vectorDrawableCompatState.mChangingConfigurations = this.getChangingConfigurations();
        vectorDrawableCompatState.mCacheDirty = true;
        this.inflateInternal(resources, xmlPullParser, attributeSet, theme);
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
    }

    public void invalidateSelf() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.invalidateSelf();
            return;
        }
        super.invalidateSelf();
    }

    public boolean isAutoMirrored() {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.isAutoMirrored(this.mDelegateDrawable);
        }
        return this.mVectorState.mAutoMirrored;
    }

    public boolean isStateful() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.isStateful();
        }
        return super.isStateful() || this.mVectorState != null && this.mVectorState.mTint != null && this.mVectorState.mTint.isStateful();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Drawable mutate() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.mutate();
            return this;
        } else {
            if (this.mMutated || super.mutate() != this) return this;
            {
                this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
                this.mMutated = true;
                return this;
            }
        }
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds(rect);
        }
    }

    protected boolean onStateChange(int[] object) {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState(object);
        }
        object = this.mVectorState;
        if (object.mTint != null && object.mTintMode != null) {
            this.mTintFilter = this.updateTintFilter(this.mTintFilter, object.mTint, object.mTintMode);
            this.invalidateSelf();
            return true;
        }
        return false;
    }

    public void scheduleSelf(Runnable runnable, long l) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.scheduleSelf(runnable, l);
            return;
        }
        super.scheduleSelf(runnable, l);
    }

    void setAllowCaching(boolean bl) {
        this.mAllowCaching = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAlpha(int n) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setAlpha(n);
            return;
        } else {
            if (this.mVectorState.mVPathRenderer.getRootAlpha() == n) return;
            {
                this.mVectorState.mVPathRenderer.setRootAlpha(n);
                this.invalidateSelf();
                return;
            }
        }
    }

    public void setAutoMirrored(boolean bl) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setAutoMirrored(this.mDelegateDrawable, bl);
            return;
        }
        this.mVectorState.mAutoMirrored = bl;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter(colorFilter);
            return;
        }
        this.mColorFilter = colorFilter;
        this.invalidateSelf();
    }

    @Override
    public void setTint(int n) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTint(this.mDelegateDrawable, n);
            return;
        }
        this.setTintList(ColorStateList.valueOf((int)n));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setTintList(ColorStateList colorStateList) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintList(this.mDelegateDrawable, colorStateList);
            return;
        } else {
            VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
            if (vectorDrawableCompatState.mTint == colorStateList) return;
            {
                vectorDrawableCompatState.mTint = colorStateList;
                this.mTintFilter = this.updateTintFilter(this.mTintFilter, colorStateList, vectorDrawableCompatState.mTintMode);
                this.invalidateSelf();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintMode(this.mDelegateDrawable, mode);
            return;
        } else {
            VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
            if (vectorDrawableCompatState.mTintMode == mode) return;
            {
                vectorDrawableCompatState.mTintMode = mode;
                this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, mode);
                this.invalidateSelf();
                return;
            }
        }
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setVisible(bl, bl2);
        }
        return super.setVisible(bl, bl2);
    }

    public void unscheduleSelf(Runnable runnable) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.unscheduleSelf(runnable);
            return;
        }
        super.unscheduleSelf(runnable);
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(this.getState(), 0), mode);
    }

    private static class VClipPath
    extends VPath {
        public VClipPath() {
        }

        public VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }

        private void updateStateFromTypedArray(TypedArray object) {
            String string2 = object.getString(0);
            if (string2 != null) {
                this.mPathName = string2;
            }
            if ((object = object.getString(1)) != null) {
                this.mNodes = PathParser.createNodesFromPathData((String)object);
            }
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (!TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                return;
            }
            resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
            this.updateStateFromTypedArray((TypedArray)resources);
            resources.recycle();
        }

        @Override
        public boolean isClipPath() {
            return true;
        }
    }

    private static class VFullPath
    extends VPath {
        float mFillAlpha = 1.0f;
        int mFillColor = 0;
        int mFillRule = 0;
        float mStrokeAlpha = 1.0f;
        int mStrokeColor = 0;
        Paint.Cap mStrokeLineCap = Paint.Cap.BUTT;
        Paint.Join mStrokeLineJoin = Paint.Join.MITER;
        float mStrokeMiterlimit = 4.0f;
        float mStrokeWidth = 0.0f;
        private int[] mThemeAttrs;
        float mTrimPathEnd = 1.0f;
        float mTrimPathOffset = 0.0f;
        float mTrimPathStart = 0.0f;

        public VFullPath() {
        }

        public VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.mStrokeColor = vFullPath.mStrokeColor;
            this.mStrokeWidth = vFullPath.mStrokeWidth;
            this.mStrokeAlpha = vFullPath.mStrokeAlpha;
            this.mFillColor = vFullPath.mFillColor;
            this.mFillRule = vFullPath.mFillRule;
            this.mFillAlpha = vFullPath.mFillAlpha;
            this.mTrimPathStart = vFullPath.mTrimPathStart;
            this.mTrimPathEnd = vFullPath.mTrimPathEnd;
            this.mTrimPathOffset = vFullPath.mTrimPathOffset;
            this.mStrokeLineCap = vFullPath.mStrokeLineCap;
            this.mStrokeLineJoin = vFullPath.mStrokeLineJoin;
            this.mStrokeMiterlimit = vFullPath.mStrokeMiterlimit;
        }

        private Paint.Cap getStrokeLineCap(int n, Paint.Cap cap) {
            switch (n) {
                default: {
                    return cap;
                }
                case 0: {
                    return Paint.Cap.BUTT;
                }
                case 1: {
                    return Paint.Cap.ROUND;
                }
                case 2: 
            }
            return Paint.Cap.SQUARE;
        }

        private Paint.Join getStrokeLineJoin(int n, Paint.Join join) {
            switch (n) {
                default: {
                    return join;
                }
                case 0: {
                    return Paint.Join.MITER;
                }
                case 1: {
                    return Paint.Join.ROUND;
                }
                case 2: 
            }
            return Paint.Join.BEVEL;
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.mThemeAttrs = null;
            if (!TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                return;
            }
            String string2 = typedArray.getString(0);
            if (string2 != null) {
                this.mPathName = string2;
            }
            if ((string2 = typedArray.getString(2)) != null) {
                this.mNodes = PathParser.createNodesFromPathData(string2);
            }
            this.mFillColor = TypedArrayUtils.getNamedColor(typedArray, xmlPullParser, "fillColor", 1, this.mFillColor);
            this.mFillAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.mFillAlpha);
            this.mStrokeLineCap = this.getStrokeLineCap(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.mStrokeLineCap);
            this.mStrokeLineJoin = this.getStrokeLineJoin(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.mStrokeLineJoin);
            this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.mStrokeMiterlimit);
            this.mStrokeColor = TypedArrayUtils.getNamedColor(typedArray, xmlPullParser, "strokeColor", 3, this.mStrokeColor);
            this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.mStrokeAlpha);
            this.mStrokeWidth = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.mStrokeWidth);
            this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.mTrimPathEnd);
            this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.mTrimPathOffset);
            this.mTrimPathStart = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.mTrimPathStart);
            this.mFillRule = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.mFillRule);
        }

        float getFillAlpha() {
            return this.mFillAlpha;
        }

        int getFillColor() {
            return this.mFillColor;
        }

        float getStrokeAlpha() {
            return this.mStrokeAlpha;
        }

        int getStrokeColor() {
            return this.mStrokeColor;
        }

        float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        float getTrimPathEnd() {
            return this.mTrimPathEnd;
        }

        float getTrimPathOffset() {
            return this.mTrimPathOffset;
        }

        float getTrimPathStart() {
            return this.mTrimPathStart;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
            this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser);
            resources.recycle();
        }

        void setFillAlpha(float f) {
            this.mFillAlpha = f;
        }

        void setFillColor(int n) {
            this.mFillColor = n;
        }

        void setStrokeAlpha(float f) {
            this.mStrokeAlpha = f;
        }

        void setStrokeColor(int n) {
            this.mStrokeColor = n;
        }

        void setStrokeWidth(float f) {
            this.mStrokeWidth = f;
        }

        void setTrimPathEnd(float f) {
            this.mTrimPathEnd = f;
        }

        void setTrimPathOffset(float f) {
            this.mTrimPathOffset = f;
        }

        void setTrimPathStart(float f) {
            this.mTrimPathStart = f;
        }
    }

    private static class VGroup {
        int mChangingConfigurations;
        final ArrayList<Object> mChildren;
        private String mGroupName = null;
        private final Matrix mLocalMatrix;
        private float mPivotX = 0.0f;
        private float mPivotY = 0.0f;
        float mRotate = 0.0f;
        private float mScaleX = 1.0f;
        private float mScaleY = 1.0f;
        private final Matrix mStackedMatrix = new Matrix();
        private int[] mThemeAttrs;
        private float mTranslateX = 0.0f;
        private float mTranslateY = 0.0f;

        public VGroup() {
            this.mChildren = new ArrayList();
            this.mLocalMatrix = new Matrix();
        }

        /*
         * Enabled aggressive block sorting
         */
        public VGroup(VGroup object, ArrayMap<String, Object> arrayMap) {
            this.mChildren = new ArrayList();
            this.mLocalMatrix = new Matrix();
            this.mRotate = ((VGroup)object).mRotate;
            this.mPivotX = ((VGroup)object).mPivotX;
            this.mPivotY = ((VGroup)object).mPivotY;
            this.mScaleX = ((VGroup)object).mScaleX;
            this.mScaleY = ((VGroup)object).mScaleY;
            this.mTranslateX = ((VGroup)object).mTranslateX;
            this.mTranslateY = ((VGroup)object).mTranslateY;
            this.mThemeAttrs = ((VGroup)object).mThemeAttrs;
            this.mGroupName = ((VGroup)object).mGroupName;
            this.mChangingConfigurations = ((VGroup)object).mChangingConfigurations;
            if (this.mGroupName != null) {
                arrayMap.put(this.mGroupName, this);
            }
            this.mLocalMatrix.set(((VGroup)object).mLocalMatrix);
            ArrayList<Object> arrayList = ((VGroup)object).mChildren;
            int n = 0;
            while (n < arrayList.size()) {
                object = arrayList.get(n);
                if (object instanceof VGroup) {
                    object = (VGroup)object;
                    this.mChildren.add(new VGroup((VGroup)object, arrayMap));
                } else {
                    if (object instanceof VFullPath) {
                        object = new VFullPath((VFullPath)object);
                    } else {
                        if (!(object instanceof VClipPath)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        object = new VClipPath((VClipPath)object);
                    }
                    this.mChildren.add(object);
                    if (((VPath)object).mPathName != null) {
                        arrayMap.put(((VPath)object).mPathName, object);
                    }
                }
                ++n;
            }
            return;
        }

        private void updateLocalMatrix() {
            this.mLocalMatrix.reset();
            this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
            this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
            this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f);
            this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        private void updateStateFromTypedArray(TypedArray object, XmlPullParser xmlPullParser) {
            this.mThemeAttrs = null;
            this.mRotate = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "rotation", 5, this.mRotate);
            this.mPivotX = object.getFloat(1, this.mPivotX);
            this.mPivotY = object.getFloat(2, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "scaleX", 3, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "scaleY", 4, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "translateX", 6, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "translateY", 7, this.mTranslateY);
            if ((object = object.getString(0)) != null) {
                this.mGroupName = object;
            }
            this.updateLocalMatrix();
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.mLocalMatrix;
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public float getRotation() {
            return this.mRotate;
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
            this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser);
            resources.recycle();
        }

        public void setPivotX(float f) {
            if (f != this.mPivotX) {
                this.mPivotX = f;
                this.updateLocalMatrix();
            }
        }

        public void setPivotY(float f) {
            if (f != this.mPivotY) {
                this.mPivotY = f;
                this.updateLocalMatrix();
            }
        }

        public void setRotation(float f) {
            if (f != this.mRotate) {
                this.mRotate = f;
                this.updateLocalMatrix();
            }
        }

        public void setScaleX(float f) {
            if (f != this.mScaleX) {
                this.mScaleX = f;
                this.updateLocalMatrix();
            }
        }

        public void setScaleY(float f) {
            if (f != this.mScaleY) {
                this.mScaleY = f;
                this.updateLocalMatrix();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.mTranslateX) {
                this.mTranslateX = f;
                this.updateLocalMatrix();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.mTranslateY) {
                this.mTranslateY = f;
                this.updateLocalMatrix();
            }
        }
    }

    private static class VPath {
        int mChangingConfigurations;
        protected PathParser.PathDataNode[] mNodes = null;
        String mPathName;

        public VPath() {
        }

        public VPath(VPath vPath) {
            this.mPathName = vPath.mPathName;
            this.mChangingConfigurations = vPath.mChangingConfigurations;
            this.mNodes = PathParser.deepCopyNodes(vPath.mNodes);
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.mNodes;
        }

        public String getPathName() {
            return this.mPathName;
        }

        public boolean isClipPath() {
            return false;
        }

        public void setPathData(PathParser.PathDataNode[] arrpathDataNode) {
            if (!PathParser.canMorph(this.mNodes, arrpathDataNode)) {
                this.mNodes = PathParser.deepCopyNodes(arrpathDataNode);
                return;
            }
            PathParser.updateNodes(this.mNodes, arrpathDataNode);
        }

        public void toPath(Path path) {
            path.reset();
            if (this.mNodes != null) {
                PathParser.PathDataNode.nodesToPath(this.mNodes, path);
            }
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX = new Matrix();
        float mBaseHeight = 0.0f;
        float mBaseWidth = 0.0f;
        private int mChangingConfigurations;
        private Paint mFillPaint;
        private final Matrix mFinalPathMatrix = new Matrix();
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;
        int mRootAlpha = 255;
        final VGroup mRootGroup;
        String mRootName = null;
        private Paint mStrokePaint;
        final ArrayMap<String, Object> mVGTargetsMap = new ArrayMap();
        float mViewportHeight = 0.0f;
        float mViewportWidth = 0.0f;

        public VPathRenderer() {
            this.mRootGroup = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            this.mRootGroup = new VGroup(vPathRenderer.mRootGroup, this.mVGTargetsMap);
            this.mPath = new Path(vPathRenderer.mPath);
            this.mRenderPath = new Path(vPathRenderer.mRenderPath);
            this.mBaseWidth = vPathRenderer.mBaseWidth;
            this.mBaseHeight = vPathRenderer.mBaseHeight;
            this.mViewportWidth = vPathRenderer.mViewportWidth;
            this.mViewportHeight = vPathRenderer.mViewportHeight;
            this.mChangingConfigurations = vPathRenderer.mChangingConfigurations;
            this.mRootAlpha = vPathRenderer.mRootAlpha;
            this.mRootName = vPathRenderer.mRootName;
            if (vPathRenderer.mRootName != null) {
                this.mVGTargetsMap.put(vPathRenderer.mRootName, this);
            }
        }

        private static float cross(float f, float f2, float f3, float f4) {
            return f * f4 - f2 * f3;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void drawGroupTree(VGroup vGroup, Matrix object, Canvas canvas, int n, int n2, ColorFilter colorFilter) {
            void var3_5;
            vGroup.mStackedMatrix.set(object);
            vGroup.mStackedMatrix.preConcat(vGroup.mLocalMatrix);
            var3_5.save();
            int n3 = 0;
            do {
                void var4_6;
                void var6_8;
                void var5_7;
                if (n3 >= vGroup.mChildren.size()) {
                    var3_5.restore();
                    return;
                }
                Object object2 = vGroup.mChildren.get(n3);
                if (object2 instanceof VGroup) {
                    this.drawGroupTree((VGroup)object2, vGroup.mStackedMatrix, (Canvas)var3_5, (int)var4_6, (int)var5_7, (ColorFilter)var6_8);
                } else if (object2 instanceof VPath) {
                    this.drawPath(vGroup, (VPath)object2, (Canvas)var3_5, (int)var4_6, (int)var5_7, (ColorFilter)var6_8);
                }
                ++n3;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void drawPath(VGroup vGroup, VPath vPath, Canvas canvas, int n, int n2, ColorFilter colorFilter) {
            float f;
            float f2;
            block14: {
                block13: {
                    f2 = (float)n / this.mViewportWidth;
                    float f3 = (float)n2 / this.mViewportHeight;
                    f = Math.min(f2, f3);
                    vGroup = vGroup.mStackedMatrix;
                    this.mFinalPathMatrix.set((Matrix)vGroup);
                    this.mFinalPathMatrix.postScale(f2, f3);
                    f2 = this.getMatrixScale((Matrix)vGroup);
                    if (f2 == 0.0f) break block13;
                    vPath.toPath(this.mPath);
                    vGroup = this.mPath;
                    this.mRenderPath.reset();
                    if (vPath.isClipPath()) {
                        this.mRenderPath.addPath((Path)vGroup, this.mFinalPathMatrix);
                        canvas.clipPath(this.mRenderPath);
                        return;
                    }
                    vPath = (VFullPath)vPath;
                    if (((VFullPath)vPath).mTrimPathStart != 0.0f || ((VFullPath)vPath).mTrimPathEnd != 1.0f) {
                        float f4 = ((VFullPath)vPath).mTrimPathStart;
                        float f5 = ((VFullPath)vPath).mTrimPathOffset;
                        float f6 = ((VFullPath)vPath).mTrimPathEnd;
                        float f7 = ((VFullPath)vPath).mTrimPathOffset;
                        if (this.mPathMeasure == null) {
                            this.mPathMeasure = new PathMeasure();
                        }
                        this.mPathMeasure.setPath(this.mPath, false);
                        f3 = this.mPathMeasure.getLength();
                        f4 = (f4 + f5) % 1.0f * f3;
                        f6 = (f6 + f7) % 1.0f * f3;
                        vGroup.reset();
                        if (f4 > f6) {
                            this.mPathMeasure.getSegment(f4, f3, (Path)vGroup, true);
                            this.mPathMeasure.getSegment(0.0f, f6, (Path)vGroup, true);
                        } else {
                            this.mPathMeasure.getSegment(f4, f6, (Path)vGroup, true);
                        }
                        vGroup.rLineTo(0.0f, 0.0f);
                    }
                    this.mRenderPath.addPath((Path)vGroup, this.mFinalPathMatrix);
                    if (((VFullPath)vPath).mFillColor != 0) {
                        if (this.mFillPaint == null) {
                            this.mFillPaint = new Paint();
                            this.mFillPaint.setStyle(Paint.Style.FILL);
                            this.mFillPaint.setAntiAlias(true);
                        }
                        Paint paint = this.mFillPaint;
                        paint.setColor(VectorDrawableCompat.applyAlpha(((VFullPath)vPath).mFillColor, ((VFullPath)vPath).mFillAlpha));
                        paint.setColorFilter(colorFilter);
                        Path path = this.mRenderPath;
                        vGroup = ((VFullPath)vPath).mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                        path.setFillType((Path.FillType)vGroup);
                        canvas.drawPath(this.mRenderPath, paint);
                    }
                    if (((VFullPath)vPath).mStrokeColor != 0) break block14;
                }
                return;
            }
            if (this.mStrokePaint == null) {
                this.mStrokePaint = new Paint();
                this.mStrokePaint.setStyle(Paint.Style.STROKE);
                this.mStrokePaint.setAntiAlias(true);
            }
            vGroup = this.mStrokePaint;
            if (((VFullPath)vPath).mStrokeLineJoin != null) {
                vGroup.setStrokeJoin(((VFullPath)vPath).mStrokeLineJoin);
            }
            if (((VFullPath)vPath).mStrokeLineCap != null) {
                vGroup.setStrokeCap(((VFullPath)vPath).mStrokeLineCap);
            }
            vGroup.setStrokeMiter(((VFullPath)vPath).mStrokeMiterlimit);
            vGroup.setColor(VectorDrawableCompat.applyAlpha(((VFullPath)vPath).mStrokeColor, ((VFullPath)vPath).mStrokeAlpha));
            vGroup.setColorFilter(colorFilter);
            vGroup.setStrokeWidth(((VFullPath)vPath).mStrokeWidth * (f * f2));
            canvas.drawPath(this.mRenderPath, (Paint)vGroup);
        }

        private float getMatrixScale(Matrix matrix) {
            float[] arrf;
            float[] arrf2 = arrf = new float[4];
            arrf[0] = 0.0f;
            arrf2[1] = 1.0f;
            arrf2[2] = 1.0f;
            arrf2[3] = 0.0f;
            matrix.mapVectors(arrf);
            float f = (float)Math.hypot(arrf[0], arrf[1]);
            float f2 = (float)Math.hypot(arrf[2], arrf[3]);
            float f3 = VPathRenderer.cross(arrf[0], arrf[1], arrf[2], arrf[3]);
            f2 = Math.max(f, f2);
            f = 0.0f;
            if (f2 > 0.0f) {
                f = Math.abs(f3) / f2;
            }
            return f;
        }

        public void draw(Canvas canvas, int n, int n2, ColorFilter colorFilter) {
            this.drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, canvas, n, n2, colorFilter);
        }

        public float getAlpha() {
            return (float)this.getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.mRootAlpha;
        }

        public void setAlpha(float f) {
            this.setRootAlpha((int)(255.0f * f));
        }

        public void setRootAlpha(int n) {
            this.mRootAlpha = n;
        }
    }

    private static class VectorDrawableCompatState
    extends Drawable.ConstantState {
        boolean mAutoMirrored;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        Bitmap mCachedBitmap;
        int mCachedRootAlpha;
        ColorStateList mCachedTint;
        PorterDuff.Mode mCachedTintMode;
        int mChangingConfigurations;
        Paint mTempPaint;
        ColorStateList mTint = null;
        PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;
        VPathRenderer mVPathRenderer;

        public VectorDrawableCompatState() {
            this.mVPathRenderer = new VPathRenderer();
        }

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            if (vectorDrawableCompatState != null) {
                this.mChangingConfigurations = vectorDrawableCompatState.mChangingConfigurations;
                this.mVPathRenderer = new VPathRenderer(vectorDrawableCompatState.mVPathRenderer);
                if (vectorDrawableCompatState.mVPathRenderer.mFillPaint != null) {
                    this.mVPathRenderer.mFillPaint = new Paint(vectorDrawableCompatState.mVPathRenderer.mFillPaint);
                }
                if (vectorDrawableCompatState.mVPathRenderer.mStrokePaint != null) {
                    this.mVPathRenderer.mStrokePaint = new Paint(vectorDrawableCompatState.mVPathRenderer.mStrokePaint);
                }
                this.mTint = vectorDrawableCompatState.mTint;
                this.mTintMode = vectorDrawableCompatState.mTintMode;
                this.mAutoMirrored = vectorDrawableCompatState.mAutoMirrored;
            }
        }

        public boolean canReuseBitmap(int n, int n2) {
            return n == this.mCachedBitmap.getWidth() && n2 == this.mCachedBitmap.getHeight();
        }

        public boolean canReuseCache() {
            return !this.mCacheDirty && this.mCachedTint == this.mTint && this.mCachedTintMode == this.mTintMode && this.mCachedAutoMirrored == this.mAutoMirrored && this.mCachedRootAlpha == this.mVPathRenderer.getRootAlpha();
        }

        public void createCachedBitmapIfNeeded(int n, int n2) {
            if (this.mCachedBitmap == null || !this.canReuseBitmap(n, n2)) {
                this.mCachedBitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                this.mCacheDirty = true;
            }
        }

        public void drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            colorFilter = this.getPaint(colorFilter);
            canvas.drawBitmap(this.mCachedBitmap, null, rect, (Paint)colorFilter);
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public Paint getPaint(ColorFilter colorFilter) {
            if (!this.hasTranslucentRoot() && colorFilter == null) {
                return null;
            }
            if (this.mTempPaint == null) {
                this.mTempPaint = new Paint();
                this.mTempPaint.setFilterBitmap(true);
            }
            this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha());
            this.mTempPaint.setColorFilter(colorFilter);
            return this.mTempPaint;
        }

        public boolean hasTranslucentRoot() {
            return this.mVPathRenderer.getRootAlpha() < 255;
        }

        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public void updateCacheStates() {
            this.mCachedTint = this.mTint;
            this.mCachedTintMode = this.mTintMode;
            this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha();
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public void updateCachedBitmap(int n, int n2) {
            this.mCachedBitmap.eraseColor(0);
            Canvas canvas = new Canvas(this.mCachedBitmap);
            this.mVPathRenderer.draw(canvas, n, n2, null);
        }
    }

    private static class VectorDrawableDelegateState
    extends Drawable.ConstantState {
        private final Drawable.ConstantState mDelegateState;

        public VectorDrawableDelegateState(Drawable.ConstantState constantState) {
            this.mDelegateState = constantState;
        }

        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable)this.mDelegateState.newDrawable();
            return vectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable)this.mDelegateState.newDrawable(resources);
            return vectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable)this.mDelegateState.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }
    }

}

