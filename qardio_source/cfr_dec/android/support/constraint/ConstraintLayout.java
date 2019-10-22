/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.constraint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.constraint.R;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class ConstraintLayout
extends ViewGroup {
    static final boolean ALLOWS_EMBEDDED = false;
    private static final boolean SIMPLE_LAYOUT = true;
    private static final String TAG = "ConstraintLayout";
    public static final String VERSION = "ConstraintLayout-1.0.0";
    SparseArray<View> mChildrenByIds = new SparseArray();
    private ConstraintSet mConstraintSet = null;
    private boolean mDirtyHierarchy = true;
    ConstraintWidgetContainer mLayoutWidget;
    private int mMaxHeight = Integer.MAX_VALUE;
    private int mMaxWidth = Integer.MAX_VALUE;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOptimizationLevel = 2;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList(100);

    public ConstraintLayout(Context context) {
        super(context);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.init(null);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.init(attributeSet);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.init(attributeSet);
    }

    private final ConstraintWidget getTargetWidget(int n) {
        if (n == 0) {
            return this.mLayoutWidget;
        }
        View view = (View)this.mChildrenByIds.get(n);
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).widget;
    }

    private final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).widget;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(AttributeSet attributeSet) {
        this.mLayoutWidget.setCompanionWidget((Object)this);
        this.mChildrenByIds.put(this.getId(), (Object)this);
        this.mConstraintSet = null;
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int n = attributeSet.getIndexCount();
            for (int i = 0; i < n; ++i) {
                int n2 = attributeSet.getIndex(i);
                if (n2 == R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = attributeSet.getDimensionPixelOffset(n2, this.mMinWidth);
                    continue;
                }
                if (n2 == R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = attributeSet.getDimensionPixelOffset(n2, this.mMinHeight);
                    continue;
                }
                if (n2 == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = attributeSet.getDimensionPixelOffset(n2, this.mMaxWidth);
                    continue;
                }
                if (n2 == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = attributeSet.getDimensionPixelOffset(n2, this.mMaxHeight);
                    continue;
                }
                if (n2 == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = attributeSet.getInt(n2, this.mOptimizationLevel);
                    continue;
                }
                if (n2 != R.styleable.ConstraintLayout_Layout_constraintSet) continue;
                n2 = attributeSet.getResourceId(n2, 0);
                this.mConstraintSet = new ConstraintSet();
                this.mConstraintSet.load(this.getContext(), n2);
            }
            attributeSet.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void internalMeasureChildren(int n, int n2) {
        int n3 = this.getPaddingTop() + this.getPaddingBottom();
        int n4 = this.getPaddingLeft() + this.getPaddingRight();
        int n5 = this.getChildCount();
        int n6 = 0;
        while (n6 < n5) {
            View view = this.getChildAt(n6);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                ConstraintWidget constraintWidget = layoutParams.widget;
                if (!layoutParams.isGuideline) {
                    int n7 = layoutParams.width;
                    int n8 = layoutParams.height;
                    int n9 = layoutParams.horizontalDimensionFixed || layoutParams.verticalDimensionFixed || !layoutParams.horizontalDimensionFixed && layoutParams.matchConstraintDefaultWidth == 1 || layoutParams.width == -1 || !layoutParams.verticalDimensionFixed && (layoutParams.matchConstraintDefaultHeight == 1 || layoutParams.height == -1) ? 1 : 0;
                    int n10 = 0;
                    int n11 = 0;
                    boolean bl = false;
                    boolean bl2 = false;
                    int n12 = n8;
                    int n13 = n7;
                    if (n9 != 0) {
                        if (n7 == 0 || n7 == -1) {
                            n13 = ConstraintLayout.getChildMeasureSpec((int)n, (int)n4, (int)-2);
                            n9 = 1;
                        } else {
                            n13 = ConstraintLayout.getChildMeasureSpec((int)n, (int)n4, (int)n7);
                            n9 = n11;
                        }
                        if (n8 == 0 || n8 == -1) {
                            n12 = ConstraintLayout.getChildMeasureSpec((int)n2, (int)n3, (int)-2);
                            bl = true;
                        } else {
                            n12 = ConstraintLayout.getChildMeasureSpec((int)n2, (int)n3, (int)n8);
                            bl = bl2;
                        }
                        view.measure(n13, n12);
                        n13 = view.getMeasuredWidth();
                        n12 = view.getMeasuredHeight();
                        n10 = n9;
                    }
                    constraintWidget.setWidth(n13);
                    constraintWidget.setHeight(n12);
                    if (n10 != 0) {
                        constraintWidget.setWrapWidth(n13);
                    }
                    if (bl) {
                        constraintWidget.setWrapHeight(n12);
                    }
                    if (layoutParams.needsBaseline && (n9 = view.getBaseline()) != -1) {
                        constraintWidget.setBaselineDistance(n9);
                    }
                }
            }
            ++n6;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setChildrenConstraints() {
        if (this.mConstraintSet != null) {
            this.mConstraintSet.applyToInternal(this);
        }
        int n = this.getChildCount();
        this.mLayoutWidget.removeAllChildren();
        int n2 = 0;
        while (n2 < n) {
            Object object = this.getChildAt(n2);
            ConstraintWidget constraintWidget = this.getViewWidget((View)object);
            if (constraintWidget != null) {
                LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
                constraintWidget.reset();
                constraintWidget.setVisibility(object.getVisibility());
                constraintWidget.setCompanionWidget(object);
                this.mLayoutWidget.add(constraintWidget);
                if (!layoutParams.verticalDimensionFixed || !layoutParams.horizontalDimensionFixed) {
                    this.mVariableDimensionsWidgets.add(constraintWidget);
                }
                if (layoutParams.isGuideline) {
                    constraintWidget = (android.support.constraint.solver.widgets.Guideline)constraintWidget;
                    if (layoutParams.guideBegin != -1) {
                        ((android.support.constraint.solver.widgets.Guideline)constraintWidget).setGuideBegin(layoutParams.guideBegin);
                    }
                    if (layoutParams.guideEnd != -1) {
                        ((android.support.constraint.solver.widgets.Guideline)constraintWidget).setGuideEnd(layoutParams.guideEnd);
                    }
                    if (layoutParams.guidePercent != -1.0f) {
                        ((android.support.constraint.solver.widgets.Guideline)constraintWidget).setGuidePercent(layoutParams.guidePercent);
                    }
                } else if (layoutParams.resolvedLeftToLeft != -1 || layoutParams.resolvedLeftToRight != -1 || layoutParams.resolvedRightToLeft != -1 || layoutParams.resolvedRightToRight != -1 || layoutParams.topToTop != -1 || layoutParams.topToBottom != -1 || layoutParams.bottomToTop != -1 || layoutParams.bottomToBottom != -1 || layoutParams.baselineToBaseline != -1 || layoutParams.editorAbsoluteX != -1 || layoutParams.editorAbsoluteY != -1 || layoutParams.width == -1 || layoutParams.height == -1) {
                    int n3 = layoutParams.resolvedLeftToLeft;
                    int n4 = layoutParams.resolvedLeftToRight;
                    int n5 = layoutParams.resolvedRightToLeft;
                    int n6 = layoutParams.resolvedRightToRight;
                    int n7 = layoutParams.resolveGoneLeftMargin;
                    int n8 = layoutParams.resolveGoneRightMargin;
                    float f = layoutParams.resolvedHorizontalBias;
                    if (Build.VERSION.SDK_INT < 17) {
                        n5 = layoutParams.leftToLeft;
                        n6 = layoutParams.leftToRight;
                        int n9 = layoutParams.rightToLeft;
                        int n10 = layoutParams.rightToRight;
                        int n11 = layoutParams.goneLeftMargin;
                        int n12 = layoutParams.goneRightMargin;
                        float f2 = layoutParams.horizontalBias;
                        int n13 = n5;
                        int n14 = n6;
                        if (n5 == -1) {
                            n13 = n5;
                            n14 = n6;
                            if (n6 == -1) {
                                if (layoutParams.startToStart != -1) {
                                    n13 = layoutParams.startToStart;
                                    n14 = n6;
                                } else {
                                    n13 = n5;
                                    n14 = n6;
                                    if (layoutParams.startToEnd != -1) {
                                        n14 = layoutParams.startToEnd;
                                        n13 = n5;
                                    }
                                }
                            }
                        }
                        n7 = n11;
                        n8 = n12;
                        f = f2;
                        n3 = n13;
                        n4 = n14;
                        n5 = n9;
                        n6 = n10;
                        if (n9 == -1) {
                            n7 = n11;
                            n8 = n12;
                            f = f2;
                            n3 = n13;
                            n4 = n14;
                            n5 = n9;
                            n6 = n10;
                            if (n10 == -1) {
                                if (layoutParams.endToStart != -1) {
                                    n5 = layoutParams.endToStart;
                                    n6 = n10;
                                    n4 = n14;
                                    n3 = n13;
                                    f = f2;
                                    n8 = n12;
                                    n7 = n11;
                                } else {
                                    n7 = n11;
                                    n8 = n12;
                                    f = f2;
                                    n3 = n13;
                                    n4 = n14;
                                    n5 = n9;
                                    n6 = n10;
                                    if (layoutParams.endToEnd != -1) {
                                        n6 = layoutParams.endToEnd;
                                        n7 = n11;
                                        n8 = n12;
                                        f = f2;
                                        n3 = n13;
                                        n4 = n14;
                                        n5 = n9;
                                    }
                                }
                            }
                        }
                    }
                    if (n3 != -1) {
                        object = this.getTargetWidget(n3);
                        if (object != null) {
                            constraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)object, ConstraintAnchor.Type.LEFT, layoutParams.leftMargin, n7);
                        }
                    } else if (n4 != -1 && (object = this.getTargetWidget(n4)) != null) {
                        constraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)object, ConstraintAnchor.Type.RIGHT, layoutParams.leftMargin, n7);
                    }
                    if (n5 != -1) {
                        object = this.getTargetWidget(n5);
                        if (object != null) {
                            constraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, (ConstraintWidget)object, ConstraintAnchor.Type.LEFT, layoutParams.rightMargin, n8);
                        }
                    } else if (n6 != -1 && (object = this.getTargetWidget(n6)) != null) {
                        constraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, (ConstraintWidget)object, ConstraintAnchor.Type.RIGHT, layoutParams.rightMargin, n8);
                    }
                    if (layoutParams.topToTop != -1) {
                        object = this.getTargetWidget(layoutParams.topToTop);
                        if (object != null) {
                            constraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, (ConstraintWidget)object, ConstraintAnchor.Type.TOP, layoutParams.topMargin, layoutParams.goneTopMargin);
                        }
                    } else if (layoutParams.topToBottom != -1 && (object = this.getTargetWidget(layoutParams.topToBottom)) != null) {
                        constraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, (ConstraintWidget)object, ConstraintAnchor.Type.BOTTOM, layoutParams.topMargin, layoutParams.goneTopMargin);
                    }
                    if (layoutParams.bottomToTop != -1) {
                        object = this.getTargetWidget(layoutParams.bottomToTop);
                        if (object != null) {
                            constraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, (ConstraintWidget)object, ConstraintAnchor.Type.TOP, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                        }
                    } else if (layoutParams.bottomToBottom != -1 && (object = this.getTargetWidget(layoutParams.bottomToBottom)) != null) {
                        constraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, (ConstraintWidget)object, ConstraintAnchor.Type.BOTTOM, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                    }
                    if (layoutParams.baselineToBaseline != -1) {
                        View view = (View)this.mChildrenByIds.get(layoutParams.baselineToBaseline);
                        object = this.getTargetWidget(layoutParams.baselineToBaseline);
                        if (object != null && view != null && view.getLayoutParams() instanceof LayoutParams) {
                            LayoutParams layoutParams2 = (LayoutParams)view.getLayoutParams();
                            layoutParams.needsBaseline = true;
                            layoutParams2.needsBaseline = true;
                            constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE).connect(((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.BASELINE), 0, -1, ConstraintAnchor.Strength.STRONG, 0, true);
                            constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).reset();
                            constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
                        }
                    }
                    if (f >= 0.0f && f != 0.5f) {
                        constraintWidget.setHorizontalBiasPercent(f);
                    }
                    if (layoutParams.verticalBias >= 0.0f && layoutParams.verticalBias != 0.5f) {
                        constraintWidget.setVerticalBiasPercent(layoutParams.verticalBias);
                    }
                    if (this.isInEditMode() && (layoutParams.editorAbsoluteX != -1 || layoutParams.editorAbsoluteY != -1)) {
                        constraintWidget.setOrigin(layoutParams.editorAbsoluteX, layoutParams.editorAbsoluteY);
                    }
                    if (!layoutParams.horizontalDimensionFixed) {
                        if (layoutParams.width == -1) {
                            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                            constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.LEFT).mMargin = layoutParams.leftMargin;
                            constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.RIGHT).mMargin = layoutParams.rightMargin;
                        } else {
                            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                            constraintWidget.setWidth(0);
                        }
                    } else {
                        constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        constraintWidget.setWidth(layoutParams.width);
                    }
                    if (!layoutParams.verticalDimensionFixed) {
                        if (layoutParams.height == -1) {
                            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                            constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.TOP).mMargin = layoutParams.topMargin;
                            constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.BOTTOM).mMargin = layoutParams.bottomMargin;
                        } else {
                            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                            constraintWidget.setHeight(0);
                        }
                    } else {
                        constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        constraintWidget.setHeight(layoutParams.height);
                    }
                    if (layoutParams.dimensionRatio != null) {
                        constraintWidget.setDimensionRatio(layoutParams.dimensionRatio);
                    }
                    constraintWidget.setHorizontalWeight(layoutParams.horizontalWeight);
                    constraintWidget.setVerticalWeight(layoutParams.verticalWeight);
                    constraintWidget.setHorizontalChainStyle(layoutParams.horizontalChainStyle);
                    constraintWidget.setVerticalChainStyle(layoutParams.verticalChainStyle);
                    constraintWidget.setHorizontalMatchStyle(layoutParams.matchConstraintDefaultWidth, layoutParams.matchConstraintMinWidth, layoutParams.matchConstraintMaxWidth);
                    constraintWidget.setVerticalMatchStyle(layoutParams.matchConstraintDefaultHeight, layoutParams.matchConstraintMinHeight, layoutParams.matchConstraintMaxHeight);
                }
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSelfDimensionBehaviour(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n);
        n = View.MeasureSpec.getSize((int)n);
        int n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        int n5 = this.getPaddingTop();
        int n6 = this.getPaddingBottom();
        int n7 = this.getPaddingLeft();
        int n8 = this.getPaddingRight();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
        int n9 = 0;
        int n10 = 0;
        this.getLayoutParams();
        switch (n3) {
            default: {
                n = n9;
                break;
            }
            case Integer.MIN_VALUE: {
                dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                break;
            }
            case 0: {
                dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                n = n9;
                break;
            }
            case 1073741824: {
                n = Math.min(this.mMaxWidth, n) - (n7 + n8);
            }
        }
        switch (n4) {
            default: {
                n2 = n10;
                break;
            }
            case Integer.MIN_VALUE: {
                dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                break;
            }
            case 0: {
                dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                n2 = n10;
                break;
            }
            case 1073741824: {
                n2 = Math.min(this.mMaxHeight, n2) - (n5 + n6);
            }
        }
        this.mLayoutWidget.setMinWidth(0);
        this.mLayoutWidget.setMinHeight(0);
        this.mLayoutWidget.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.mLayoutWidget.setWidth(n);
        this.mLayoutWidget.setVerticalDimensionBehaviour(dimensionBehaviour2);
        this.mLayoutWidget.setHeight(n2);
        this.mLayoutWidget.setMinWidth(this.mMinWidth - this.getPaddingLeft() - this.getPaddingRight());
        this.mLayoutWidget.setMinHeight(this.mMinHeight - this.getPaddingTop() - this.getPaddingBottom());
    }

    private void updateHierarchy() {
        int n = this.getChildCount();
        boolean bl = false;
        int n2 = 0;
        do {
            block6: {
                boolean bl2;
                block5: {
                    bl2 = bl;
                    if (n2 >= n) break block5;
                    if (!this.getChildAt(n2).isLayoutRequested()) break block6;
                    bl2 = true;
                }
                if (bl2) {
                    this.mVariableDimensionsWidgets.clear();
                    this.setChildrenConstraints();
                }
                return;
            }
            ++n2;
        } while (true);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n, layoutParams);
        if (Build.VERSION.SDK_INT < 14) {
            this.onViewAdded(view);
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = this.getChildCount();
        bl = this.isInEditMode();
        n = 0;
        while (n < n2) {
            View view = this.getChildAt(n);
            Object object = (LayoutParams)view.getLayoutParams();
            if (view.getVisibility() != 8 || ((LayoutParams)object).isGuideline || bl) {
                object = ((LayoutParams)object).widget;
                n3 = ((ConstraintWidget)object).getDrawX();
                n4 = ((ConstraintWidget)object).getDrawY();
                view.layout(n3, n4, n3 + ((ConstraintWidget)object).getWidth(), n4 + ((ConstraintWidget)object).getHeight());
            }
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3 = this.getPaddingLeft();
        int n4 = this.getPaddingTop();
        this.mLayoutWidget.setX(n3);
        this.mLayoutWidget.setY(n4);
        this.setSelfDimensionBehaviour(n, n2);
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            this.updateHierarchy();
        }
        this.internalMeasureChildren(n, n2);
        if (this.getChildCount() > 0) {
            this.solveLinearSystem();
        }
        int n5 = 0;
        int n6 = 0;
        int n7 = this.mVariableDimensionsWidgets.size();
        int n8 = n4 + this.getPaddingBottom();
        int n9 = n3 + this.getPaddingRight();
        if (n7 > 0) {
            n3 = 0;
            n4 = this.mLayoutWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
            boolean bl = this.mLayoutWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            for (int i = 0; i < n7; ++i) {
                int n10;
                ConstraintWidget constraintWidget = this.mVariableDimensionsWidgets.get(i);
                if (constraintWidget instanceof android.support.constraint.solver.widgets.Guideline) {
                    n10 = n3;
                    n5 = n6;
                } else {
                    View view = (View)constraintWidget.getCompanionWidget();
                    n5 = n6;
                    n10 = n3;
                    if (view != null) {
                        n5 = n6;
                        n10 = n3;
                        if (view.getVisibility() != 8) {
                            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                            n5 = layoutParams.width == -2 ? ConstraintLayout.getChildMeasureSpec((int)n, (int)n9, (int)layoutParams.width) : View.MeasureSpec.makeMeasureSpec((int)constraintWidget.getWidth(), (int)1073741824);
                            n10 = layoutParams.height == -2 ? ConstraintLayout.getChildMeasureSpec((int)n2, (int)n8, (int)layoutParams.height) : View.MeasureSpec.makeMeasureSpec((int)constraintWidget.getHeight(), (int)1073741824);
                            view.measure(n5, n10);
                            n5 = view.getMeasuredWidth();
                            n10 = view.getMeasuredHeight();
                            if (n5 != constraintWidget.getWidth()) {
                                constraintWidget.setWidth(n5);
                                if (n4 != 0 && constraintWidget.getRight() > this.mLayoutWidget.getWidth()) {
                                    n3 = constraintWidget.getRight();
                                    n5 = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin();
                                    this.mLayoutWidget.setWidth(Math.max(this.mMinWidth, n3 + n5));
                                }
                                n3 = 1;
                            }
                            n5 = n3;
                            if (n10 != constraintWidget.getHeight()) {
                                constraintWidget.setHeight(n10);
                                if (bl && constraintWidget.getBottom() > this.mLayoutWidget.getHeight()) {
                                    n3 = constraintWidget.getBottom();
                                    n5 = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin();
                                    this.mLayoutWidget.setHeight(Math.max(this.mMinHeight, n3 + n5));
                                }
                                n5 = 1;
                            }
                            n3 = n5;
                            if (layoutParams.needsBaseline) {
                                n10 = view.getBaseline();
                                n3 = n5;
                                if (n10 != -1) {
                                    n3 = n5;
                                    if (n10 != constraintWidget.getBaselineDistance()) {
                                        constraintWidget.setBaselineDistance(n10);
                                        n3 = 1;
                                    }
                                }
                            }
                            n5 = n6;
                            n10 = n3;
                            if (Build.VERSION.SDK_INT >= 11) {
                                n5 = ConstraintLayout.combineMeasuredStates((int)n6, (int)view.getMeasuredState());
                                n10 = n3;
                            }
                        }
                    }
                }
                n6 = n5;
                n3 = n10;
            }
            n5 = n6;
            if (n3 != 0) {
                this.solveLinearSystem();
                n5 = n6;
            }
        }
        n6 = this.mLayoutWidget.getWidth() + n9;
        n3 = this.mLayoutWidget.getHeight() + n8;
        if (Build.VERSION.SDK_INT < 11) {
            this.setMeasuredDimension(n6, n3);
            return;
        }
        n = ConstraintLayout.resolveSizeAndState((int)n6, (int)n, (int)n5);
        n2 = ConstraintLayout.resolveSizeAndState((int)n3, (int)n2, (int)(n5 << 16));
        n = Math.min(this.mMaxWidth, n);
        n6 = Math.min(this.mMaxHeight, n2);
        n2 = n & 0xFFFFFF;
        n6 &= 0xFFFFFF;
        n = n2;
        if (this.mLayoutWidget.isWidthMeasuredTooSmall()) {
            n = n2 | 0x1000000;
        }
        n2 = n6;
        if (this.mLayoutWidget.isHeightMeasuredTooSmall()) {
            n2 = n6 | 0x1000000;
        }
        this.setMeasuredDimension(n, n2);
    }

    public void onViewAdded(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        Object object = this.getViewWidget(view);
        if (view instanceof Guideline && !(object instanceof android.support.constraint.solver.widgets.Guideline)) {
            object = (LayoutParams)view.getLayoutParams();
            ((LayoutParams)object).widget = new android.support.constraint.solver.widgets.Guideline();
            ((LayoutParams)object).isGuideline = true;
            ((android.support.constraint.solver.widgets.Guideline)((LayoutParams)object).widget).setOrientation(((LayoutParams)object).orientation);
            object = ((LayoutParams)object).widget;
        }
        this.mChildrenByIds.put(view.getId(), (Object)view);
        this.mDirtyHierarchy = true;
    }

    public void onViewRemoved(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        this.mLayoutWidget.remove(this.getViewWidget(view));
        this.mDirtyHierarchy = true;
    }

    public void removeView(View view) {
        super.removeView(view);
        if (Build.VERSION.SDK_INT < 14) {
            this.onViewRemoved(view);
        }
    }

    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = true;
    }

    public void setConstraintSet(ConstraintSet constraintSet) {
        this.mConstraintSet = constraintSet;
    }

    public void setId(int n) {
        this.mChildrenByIds.remove(this.getId());
        super.setId(n);
        this.mChildrenByIds.put(this.getId(), (Object)this);
    }

    public void setMaxHeight(int n) {
        if (n == this.mMaxHeight) {
            return;
        }
        this.mMaxHeight = n;
        this.requestLayout();
    }

    public void setMaxWidth(int n) {
        if (n == this.mMaxWidth) {
            return;
        }
        this.mMaxWidth = n;
        this.requestLayout();
    }

    public void setMinHeight(int n) {
        if (n == this.mMinHeight) {
            return;
        }
        this.mMinHeight = n;
        this.requestLayout();
    }

    public void setMinWidth(int n) {
        if (n == this.mMinWidth) {
            return;
        }
        this.mMinWidth = n;
        this.requestLayout();
    }

    public void setOptimizationLevel(int n) {
        this.mLayoutWidget.setOptimizationLevel(n);
    }

    protected void solveLinearSystem() {
        this.mLayoutWidget.layout();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int baselineToBaseline = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public String dimensionRatio = null;
        int dimensionRatioSide = 1;
        float dimensionRatioValue = 0.0f;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endToEnd = -1;
        public int endToStart = -1;
        public int goneBottomMargin = -1;
        public int goneEndMargin = -1;
        public int goneLeftMargin = -1;
        public int goneRightMargin = -1;
        public int goneStartMargin = -1;
        public int goneTopMargin = -1;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        boolean horizontalDimensionFixed = true;
        public float horizontalWeight = 0.0f;
        boolean isGuideline = false;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int matchConstraintDefaultHeight = 0;
        public int matchConstraintDefaultWidth = 0;
        public int matchConstraintMaxHeight = 0;
        public int matchConstraintMaxWidth = 0;
        public int matchConstraintMinHeight = 0;
        public int matchConstraintMinWidth = 0;
        boolean needsBaseline = false;
        public int orientation = -1;
        int resolveGoneLeftMargin = -1;
        int resolveGoneRightMargin = -1;
        float resolvedHorizontalBias = 0.5f;
        int resolvedLeftToLeft = -1;
        int resolvedLeftToRight = -1;
        int resolvedRightToLeft = -1;
        int resolvedRightToRight = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        boolean verticalDimensionFixed = true;
        public float verticalWeight = 0.0f;
        ConstraintWidget widget = new ConstraintWidget();

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public LayoutParams(Context context, AttributeSet object) {
            super(context, (AttributeSet)object);
            context = context.obtainStyledAttributes((AttributeSet)object, R.styleable.ConstraintLayout_Layout);
            int n = context.getIndexCount();
            int n2 = 0;
            do {
                block49: {
                    int n3;
                    block77: {
                        block78: {
                            int n4;
                            float f;
                            float f2;
                            block76: {
                                block75: {
                                    block74: {
                                        block73: {
                                            block72: {
                                                block71: {
                                                    block70: {
                                                        block69: {
                                                            block68: {
                                                                block67: {
                                                                    block66: {
                                                                        block65: {
                                                                            block64: {
                                                                                block63: {
                                                                                    block62: {
                                                                                        block61: {
                                                                                            block60: {
                                                                                                block59: {
                                                                                                    block58: {
                                                                                                        block57: {
                                                                                                            block56: {
                                                                                                                block55: {
                                                                                                                    block54: {
                                                                                                                        block53: {
                                                                                                                            block52: {
                                                                                                                                block51: {
                                                                                                                                    block50: {
                                                                                                                                        if (n2 >= n) {
                                                                                                                                            context.recycle();
                                                                                                                                            this.validate();
                                                                                                                                            return;
                                                                                                                                        }
                                                                                                                                        n3 = context.getIndex(n2);
                                                                                                                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf) break block50;
                                                                                                                                        this.leftToLeft = context.getResourceId(n3, this.leftToLeft);
                                                                                                                                        if (this.leftToLeft == -1) {
                                                                                                                                            this.leftToLeft = context.getInt(n3, -1);
                                                                                                                                        }
                                                                                                                                        break block49;
                                                                                                                                    }
                                                                                                                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf) break block51;
                                                                                                                                    this.leftToRight = context.getResourceId(n3, this.leftToRight);
                                                                                                                                    if (this.leftToRight == -1) {
                                                                                                                                        this.leftToRight = context.getInt(n3, -1);
                                                                                                                                    }
                                                                                                                                    break block49;
                                                                                                                                }
                                                                                                                                if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf) break block52;
                                                                                                                                this.rightToLeft = context.getResourceId(n3, this.rightToLeft);
                                                                                                                                if (this.rightToLeft == -1) {
                                                                                                                                    this.rightToLeft = context.getInt(n3, -1);
                                                                                                                                }
                                                                                                                                break block49;
                                                                                                                            }
                                                                                                                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf) break block53;
                                                                                                                            this.rightToRight = context.getResourceId(n3, this.rightToRight);
                                                                                                                            if (this.rightToRight == -1) {
                                                                                                                                this.rightToRight = context.getInt(n3, -1);
                                                                                                                            }
                                                                                                                            break block49;
                                                                                                                        }
                                                                                                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf) break block54;
                                                                                                                        this.topToTop = context.getResourceId(n3, this.topToTop);
                                                                                                                        if (this.topToTop == -1) {
                                                                                                                            this.topToTop = context.getInt(n3, -1);
                                                                                                                        }
                                                                                                                        break block49;
                                                                                                                    }
                                                                                                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf) break block55;
                                                                                                                    this.topToBottom = context.getResourceId(n3, this.topToBottom);
                                                                                                                    if (this.topToBottom == -1) {
                                                                                                                        this.topToBottom = context.getInt(n3, -1);
                                                                                                                    }
                                                                                                                    break block49;
                                                                                                                }
                                                                                                                if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf) break block56;
                                                                                                                this.bottomToTop = context.getResourceId(n3, this.bottomToTop);
                                                                                                                if (this.bottomToTop == -1) {
                                                                                                                    this.bottomToTop = context.getInt(n3, -1);
                                                                                                                }
                                                                                                                break block49;
                                                                                                            }
                                                                                                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf) break block57;
                                                                                                            this.bottomToBottom = context.getResourceId(n3, this.bottomToBottom);
                                                                                                            if (this.bottomToBottom == -1) {
                                                                                                                this.bottomToBottom = context.getInt(n3, -1);
                                                                                                            }
                                                                                                            break block49;
                                                                                                        }
                                                                                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf) break block58;
                                                                                                        this.baselineToBaseline = context.getResourceId(n3, this.baselineToBaseline);
                                                                                                        if (this.baselineToBaseline == -1) {
                                                                                                            this.baselineToBaseline = context.getInt(n3, -1);
                                                                                                        }
                                                                                                        break block49;
                                                                                                    }
                                                                                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX) break block59;
                                                                                                    this.editorAbsoluteX = context.getDimensionPixelOffset(n3, this.editorAbsoluteX);
                                                                                                    break block49;
                                                                                                }
                                                                                                if (n3 != R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY) break block60;
                                                                                                this.editorAbsoluteY = context.getDimensionPixelOffset(n3, this.editorAbsoluteY);
                                                                                                break block49;
                                                                                            }
                                                                                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin) break block61;
                                                                                            this.guideBegin = context.getDimensionPixelOffset(n3, this.guideBegin);
                                                                                            break block49;
                                                                                        }
                                                                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end) break block62;
                                                                                        this.guideEnd = context.getDimensionPixelOffset(n3, this.guideEnd);
                                                                                        break block49;
                                                                                    }
                                                                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent) break block63;
                                                                                    this.guidePercent = context.getFloat(n3, this.guidePercent);
                                                                                    break block49;
                                                                                }
                                                                                if (n3 != R.styleable.ConstraintLayout_Layout_android_orientation) break block64;
                                                                                this.orientation = context.getInt(n3, this.orientation);
                                                                                break block49;
                                                                            }
                                                                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf) break block65;
                                                                            this.startToEnd = context.getResourceId(n3, this.startToEnd);
                                                                            if (this.startToEnd == -1) {
                                                                                this.startToEnd = context.getInt(n3, -1);
                                                                            }
                                                                            break block49;
                                                                        }
                                                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf) break block66;
                                                                        this.startToStart = context.getResourceId(n3, this.startToStart);
                                                                        if (this.startToStart == -1) {
                                                                            this.startToStart = context.getInt(n3, -1);
                                                                        }
                                                                        break block49;
                                                                    }
                                                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf) break block67;
                                                                    this.endToStart = context.getResourceId(n3, this.endToStart);
                                                                    if (this.endToStart == -1) {
                                                                        this.endToStart = context.getInt(n3, -1);
                                                                    }
                                                                    break block49;
                                                                }
                                                                if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf) break block68;
                                                                this.endToEnd = context.getResourceId(n3, this.endToEnd);
                                                                if (this.endToEnd == -1) {
                                                                    this.endToEnd = context.getInt(n3, -1);
                                                                }
                                                                break block49;
                                                            }
                                                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft) break block69;
                                                            this.goneLeftMargin = context.getDimensionPixelSize(n3, this.goneLeftMargin);
                                                            break block49;
                                                        }
                                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_goneMarginTop) break block70;
                                                        this.goneTopMargin = context.getDimensionPixelSize(n3, this.goneTopMargin);
                                                        break block49;
                                                    }
                                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_goneMarginRight) break block71;
                                                    this.goneRightMargin = context.getDimensionPixelSize(n3, this.goneRightMargin);
                                                    break block49;
                                                }
                                                if (n3 != R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom) break block72;
                                                this.goneBottomMargin = context.getDimensionPixelSize(n3, this.goneBottomMargin);
                                                break block49;
                                            }
                                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_goneMarginStart) break block73;
                                            this.goneStartMargin = context.getDimensionPixelSize(n3, this.goneStartMargin);
                                            break block49;
                                        }
                                        if (n3 != R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd) break block74;
                                        this.goneEndMargin = context.getDimensionPixelSize(n3, this.goneEndMargin);
                                        break block49;
                                    }
                                    if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias) break block75;
                                    this.horizontalBias = context.getFloat(n3, this.horizontalBias);
                                    break block49;
                                }
                                if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias) break block76;
                                this.verticalBias = context.getFloat(n3, this.verticalBias);
                                break block49;
                            }
                            if (n3 != R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio) break block77;
                            this.dimensionRatio = context.getString(n3);
                            this.dimensionRatioValue = Float.NaN;
                            this.dimensionRatioSide = -1;
                            if (this.dimensionRatio == null) break block49;
                            int n5 = this.dimensionRatio.length();
                            n3 = this.dimensionRatio.indexOf(44);
                            if (n3 > 0 && n3 < n5 - 1) {
                                object = this.dimensionRatio.substring(0, n3);
                                if (((String)object).equalsIgnoreCase("W")) {
                                    this.dimensionRatioSide = 0;
                                } else if (((String)object).equalsIgnoreCase("H")) {
                                    this.dimensionRatioSide = 1;
                                }
                                ++n3;
                            } else {
                                n3 = 0;
                            }
                            if ((n4 = this.dimensionRatio.indexOf(58)) < 0 || n4 >= n5 - 1) break block78;
                            object = this.dimensionRatio.substring(n3, n4);
                            String string2 = this.dimensionRatio.substring(n4 + 1);
                            if (((String)object).length() <= 0 || string2.length() <= 0) break block49;
                            try {
                                f = Float.parseFloat((String)object);
                                f2 = Float.parseFloat(string2);
                            }
                            catch (NumberFormatException numberFormatException) {}
                            if (!(f > 0.0f) || !(f2 > 0.0f)) break block49;
                            if (this.dimensionRatioSide == 1) {
                                this.dimensionRatioValue = Math.abs(f2 / f);
                            }
                            f /= f2;
                            this.dimensionRatioValue = Math.abs(f);
                            break block49;
                            break block49;
                        }
                        object = this.dimensionRatio.substring(n3);
                        if (((String)object).length() > 0) {
                            try {
                                this.dimensionRatioValue = Float.parseFloat((String)object);
                            }
                            catch (NumberFormatException numberFormatException) {}
                        }
                        break block49;
                    }
                    if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight) {
                        this.horizontalWeight = context.getFloat(n3, 0.0f);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight) {
                        this.verticalWeight = context.getFloat(n3, 0.0f);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle) {
                        this.horizontalChainStyle = context.getInt(n3, 0);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle) {
                        this.verticalChainStyle = context.getInt(n3, 0);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default) {
                        this.matchConstraintDefaultWidth = context.getInt(n3, 0);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default) {
                        this.matchConstraintDefaultHeight = context.getInt(n3, 0);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min) {
                        this.matchConstraintMinWidth = context.getDimensionPixelSize(n3, this.matchConstraintMinWidth);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max) {
                        this.matchConstraintMaxWidth = context.getDimensionPixelSize(n3, this.matchConstraintMaxWidth);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min) {
                        this.matchConstraintMinHeight = context.getDimensionPixelSize(n3, this.matchConstraintMinHeight);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max) {
                        this.matchConstraintMaxHeight = context.getDimensionPixelSize(n3, this.matchConstraintMaxHeight);
                    } else if (n3 == R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator || n3 == R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator || n3 == R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator || n3 == R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator || n3 == R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator) {
                        // empty if block
                    }
                }
                ++n2;
            } while (true);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        /*
         * Enabled aggressive block sorting
         */
        @TargetApi(value=17)
        public void resolveLayoutDirection(int n) {
            int n2 = 1;
            super.resolveLayoutDirection(n);
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            this.resolvedHorizontalBias = this.horizontalBias;
            n = 1 == this.getLayoutDirection() ? n2 : 0;
            if (n != 0) {
                if (this.startToEnd != -1) {
                    this.resolvedRightToLeft = this.startToEnd;
                } else if (this.startToStart != -1) {
                    this.resolvedRightToRight = this.startToStart;
                }
                if (this.endToStart != -1) {
                    this.resolvedLeftToRight = this.endToStart;
                }
                if (this.endToEnd != -1) {
                    this.resolvedLeftToLeft = this.endToEnd;
                }
                if (this.goneStartMargin != -1) {
                    this.resolveGoneRightMargin = this.goneStartMargin;
                }
                if (this.goneEndMargin != -1) {
                    this.resolveGoneLeftMargin = this.goneEndMargin;
                }
                this.resolvedHorizontalBias = 1.0f - this.horizontalBias;
            } else {
                if (this.startToEnd != -1) {
                    this.resolvedLeftToRight = this.startToEnd;
                }
                if (this.startToStart != -1) {
                    this.resolvedLeftToLeft = this.startToStart;
                }
                if (this.endToStart != -1) {
                    this.resolvedRightToLeft = this.endToStart;
                }
                if (this.endToEnd != -1) {
                    this.resolvedRightToRight = this.endToEnd;
                }
                if (this.goneStartMargin != -1) {
                    this.resolveGoneLeftMargin = this.goneStartMargin;
                }
                if (this.goneEndMargin != -1) {
                    this.resolveGoneRightMargin = this.goneEndMargin;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1) {
                if (this.rightToLeft != -1) {
                    this.resolvedRightToLeft = this.rightToLeft;
                } else if (this.rightToRight != -1) {
                    this.resolvedRightToRight = this.rightToRight;
                }
            }
            if (this.startToStart != -1 || this.startToEnd != -1) return;
            {
                if (this.leftToLeft != -1) {
                    this.resolvedLeftToLeft = this.leftToLeft;
                    return;
                } else {
                    if (this.leftToRight == -1) return;
                    {
                        this.resolvedLeftToRight = this.leftToRight;
                        return;
                    }
                }
            }
        }

        public void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            if (this.width == 0 || this.width == -1) {
                this.horizontalDimensionFixed = false;
            }
            if (this.height == 0 || this.height == -1) {
                this.verticalDimensionFixed = false;
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.isGuideline = true;
                this.horizontalDimensionFixed = true;
                this.verticalDimensionFixed = true;
                if (!(this.widget instanceof android.support.constraint.solver.widgets.Guideline)) {
                    this.widget = new android.support.constraint.solver.widgets.Guideline();
                }
                ((android.support.constraint.solver.widgets.Guideline)this.widget).setOrientation(this.orientation);
            }
        }
    }

}

