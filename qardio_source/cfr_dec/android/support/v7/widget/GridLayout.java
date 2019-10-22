/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.LogPrinter
 *  android.util.Pair
 *  android.util.Printer
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.widget.Space;
import android.support.v7.gridlayout.R;
import android.util.AttributeSet;
import android.util.LogPrinter;
import android.util.Pair;
import android.util.Printer;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GridLayout
extends ViewGroup {
    private static final int ALIGNMENT_MODE;
    public static final Alignment BASELINE;
    public static final Alignment BOTTOM;
    public static final Alignment CENTER;
    private static final int COLUMN_COUNT;
    private static final int COLUMN_ORDER_PRESERVED;
    public static final Alignment END;
    public static final Alignment FILL;
    private static final Alignment LEADING;
    public static final Alignment LEFT;
    static final Printer LOG_PRINTER;
    static final Printer NO_PRINTER;
    private static final int ORIENTATION;
    public static final Alignment RIGHT;
    private static final int ROW_COUNT;
    private static final int ROW_ORDER_PRESERVED;
    public static final Alignment START;
    public static final Alignment TOP;
    private static final Alignment TRAILING;
    static final Alignment UNDEFINED_ALIGNMENT;
    private static final int USE_DEFAULT_MARGINS;
    int mAlignmentMode = 1;
    int mDefaultGap;
    final Axis mHorizontalAxis = new Axis(true);
    int mLastLayoutParamsHashCode = 0;
    int mOrientation = 0;
    Printer mPrinter;
    boolean mUseDefaultMargins = false;
    final Axis mVerticalAxis = new Axis(false);

    static {
        LOG_PRINTER = new LogPrinter(3, GridLayout.class.getName());
        NO_PRINTER = new Printer(){

            public void println(String string2) {
            }
        };
        ORIENTATION = R.styleable.GridLayout_orientation;
        ROW_COUNT = R.styleable.GridLayout_rowCount;
        COLUMN_COUNT = R.styleable.GridLayout_columnCount;
        USE_DEFAULT_MARGINS = R.styleable.GridLayout_useDefaultMargins;
        ALIGNMENT_MODE = R.styleable.GridLayout_alignmentMode;
        ROW_ORDER_PRESERVED = R.styleable.GridLayout_rowOrderPreserved;
        COLUMN_ORDER_PRESERVED = R.styleable.GridLayout_columnOrderPreserved;
        UNDEFINED_ALIGNMENT = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return Integer.MIN_VALUE;
            }

            @Override
            String getDebugString() {
                return "UNDEFINED";
            }

            @Override
            int getGravityOffset(View view, int n) {
                return Integer.MIN_VALUE;
            }
        };
        LEADING = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return 0;
            }

            @Override
            String getDebugString() {
                return "LEADING";
            }

            @Override
            int getGravityOffset(View view, int n) {
                return 0;
            }
        };
        TRAILING = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return n;
            }

            @Override
            String getDebugString() {
                return "TRAILING";
            }

            @Override
            int getGravityOffset(View view, int n) {
                return n;
            }
        };
        TOP = LEADING;
        BOTTOM = TRAILING;
        START = LEADING;
        END = TRAILING;
        LEFT = GridLayout.createSwitchingAlignment(START, END);
        RIGHT = GridLayout.createSwitchingAlignment(END, START);
        CENTER = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return n >> 1;
            }

            @Override
            String getDebugString() {
                return "CENTER";
            }

            @Override
            int getGravityOffset(View view, int n) {
                return n >> 1;
            }
        };
        BASELINE = new Alignment(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                if (view.getVisibility() == 8) {
                    return 0;
                }
                n = n2 = view.getBaseline();
                if (n2 != -1) return n;
                return Integer.MIN_VALUE;
            }

            @Override
            public Bounds getBounds() {
                return new Bounds(){
                    private int size;

                    @Override
                    protected int getOffset(GridLayout gridLayout, View view, Alignment alignment, int n, boolean bl) {
                        return Math.max(0, super.getOffset(gridLayout, view, alignment, n, bl));
                    }

                    @Override
                    protected void include(int n, int n2) {
                        super.include(n, n2);
                        this.size = Math.max(this.size, n + n2);
                    }

                    @Override
                    protected void reset() {
                        super.reset();
                        this.size = Integer.MIN_VALUE;
                    }

                    @Override
                    protected int size(boolean bl) {
                        return Math.max(super.size(bl), this.size);
                    }
                };
            }

            @Override
            String getDebugString() {
                return "BASELINE";
            }

            @Override
            int getGravityOffset(View view, int n) {
                return 0;
            }

        };
        FILL = new Alignment(){

            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                return Integer.MIN_VALUE;
            }

            @Override
            String getDebugString() {
                return "FILL";
            }

            @Override
            int getGravityOffset(View view, int n) {
                return 0;
            }

            @Override
            public int getSizeInCell(View view, int n, int n2) {
                return n2;
            }
        };
    }

    public GridLayout(Context context) {
        this(context, null);
    }

    public GridLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GridLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mPrinter = LOG_PRINTER;
        this.mDefaultGap = context.getResources().getDimensionPixelOffset(R.dimen.default_gap);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.GridLayout);
        try {
            this.setRowCount(context.getInt(ROW_COUNT, Integer.MIN_VALUE));
            this.setColumnCount(context.getInt(COLUMN_COUNT, Integer.MIN_VALUE));
            this.setOrientation(context.getInt(ORIENTATION, 0));
            this.setUseDefaultMargins(context.getBoolean(USE_DEFAULT_MARGINS, false));
            this.setAlignmentMode(context.getInt(ALIGNMENT_MODE, 1));
            this.setRowOrderPreserved(context.getBoolean(ROW_ORDER_PRESERVED, true));
            this.setColumnOrderPreserved(context.getBoolean(COLUMN_ORDER_PRESERVED, true));
            return;
        }
        finally {
            context.recycle();
        }
    }

    static int adjust(int n, int n2) {
        return View.MeasureSpec.makeMeasureSpec((int)View.MeasureSpec.getSize((int)(n + n2)), (int)View.MeasureSpec.getMode((int)n));
    }

    static <T> T[] append(T[] arrT, T[] arrT2) {
        Object[] arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), arrT.length + arrT2.length);
        System.arraycopy(arrT, 0, arrobject, 0, arrT.length);
        System.arraycopy(arrT2, 0, arrobject, arrT.length, arrT2.length);
        return arrobject;
    }

    static boolean canStretch(int n) {
        return (n & 2) != 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void checkLayoutParams(LayoutParams object, boolean bl) {
        void var1_3;
        void var2_8;
        int n;
        void var1_5;
        String string2 = var2_8 != false ? "column" : "row";
        if (var2_8 != false) {
            Spec spec = object.columnSpec;
        } else {
            Spec spec = object.rowSpec;
        }
        Interval interval = var1_3.span;
        if (interval.min != Integer.MIN_VALUE && interval.min < 0) {
            GridLayout.handleInvalidParams(string2 + " indices must be positive");
        }
        if (var2_8 != false) {
            Axis axis = this.mHorizontalAxis;
        } else {
            Axis axis = this.mVerticalAxis;
        }
        if ((n = var1_5.definedCount) != Integer.MIN_VALUE) {
            if (interval.max > n) {
                GridLayout.handleInvalidParams(string2 + " indices (start + span) mustn't exceed the " + string2 + " count");
            }
            if (interval.size() > n) {
                GridLayout.handleInvalidParams(string2 + " span mustn't exceed the " + string2 + " count");
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int clip(Interval interval, boolean bl, int n) {
        int n2;
        int n3 = interval.size();
        if (n == 0) {
            return n3;
        }
        if (bl) {
            n2 = Math.min(interval.min, n);
            do {
                return Math.min(n3, n - n2);
                break;
            } while (true);
        }
        n2 = 0;
        return Math.min(n3, n - n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int computeLayoutParamsHashCode() {
        int n = 1;
        int n2 = 0;
        int n3 = this.getChildCount();
        while (n2 < n3) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() != 8) {
                n = n * 31 + ((LayoutParams)view.getLayoutParams()).hashCode();
            }
            ++n2;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void consistencyCheck() {
        if (this.mLastLayoutParamsHashCode == 0) {
            this.validateLayoutParams();
            this.mLastLayoutParamsHashCode = this.computeLayoutParamsHashCode();
            return;
        } else {
            if (this.mLastLayoutParamsHashCode == this.computeLayoutParamsHashCode()) return;
            {
                this.mPrinter.println("The fields of some layout parameters were modified in between layout operations. Check the javadoc for GridLayout.LayoutParams#rowSpec.");
                this.invalidateStructure();
                this.consistencyCheck();
                return;
            }
        }
    }

    private static Alignment createSwitchingAlignment(final Alignment alignment, final Alignment alignment2) {
        return new Alignment(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public int getAlignmentValue(View view, int n, int n2) {
                Alignment alignment3;
                boolean bl = true;
                if (ViewCompat.getLayoutDirection(view) != 1) {
                    bl = false;
                }
                if (!bl) {
                    alignment3 = alignment;
                    return alignment3.getAlignmentValue(view, n, n2);
                }
                alignment3 = alignment2;
                return alignment3.getAlignmentValue(view, n, n2);
            }

            @Override
            String getDebugString() {
                return "SWITCHING[L:" + alignment.getDebugString() + ", R:" + alignment2.getDebugString() + "]";
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            int getGravityOffset(View view, int n) {
                Alignment alignment3;
                boolean bl = true;
                if (ViewCompat.getLayoutDirection(view) != 1) {
                    bl = false;
                }
                if (!bl) {
                    alignment3 = alignment;
                    return alignment3.getGravityOffset(view, n);
                }
                alignment3 = alignment2;
                return alignment3.getGravityOffset(view, n);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean fits(int[] arrn, int n, int n2, int n3) {
        if (n3 <= arrn.length) {
            do {
                if (n2 >= n3) {
                    return true;
                }
                if (arrn[n2] > n) break;
                ++n2;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    static Alignment getAlignment(int n, boolean bl) {
        int n2 = bl ? 7 : 112;
        int n3 = bl ? 0 : 4;
        switch ((n & n2) >> n3) {
            default: {
                return UNDEFINED_ALIGNMENT;
            }
            case 3: {
                if (bl) {
                    return LEFT;
                }
                return TOP;
            }
            case 5: {
                if (bl) {
                    return RIGHT;
                }
                return BOTTOM;
            }
            case 7: {
                return FILL;
            }
            case 1: {
                return CENTER;
            }
            case 8388611: {
                return START;
            }
            case 8388613: 
        }
        return END;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private int getDefaultMargin(View view, LayoutParams object, boolean bl, boolean bl2) {
        void var4_8;
        void var3_7;
        void var2_4;
        boolean bl3 = true;
        if (!this.mUseDefaultMargins) {
            return 0;
        }
        if (var3_7 != false) {
            Spec spec = object.columnSpec;
        } else {
            Spec spec = object.rowSpec;
        }
        Axis axis = var3_7 != false ? this.mHorizontalAxis : this.mVerticalAxis;
        Interval interval = var2_4.span;
        boolean bl4 = var3_7 != false && this.isLayoutRtlCompat() ? var4_8 == false : var4_8;
        if (!bl4) {
            bl4 = bl3;
            if (interval.max == axis.getCount()) return this.getDefaultMargin(view, bl4, (boolean)var3_7, (boolean)var4_8);
            bl4 = false;
            return this.getDefaultMargin(view, bl4, (boolean)var3_7, (boolean)var4_8);
        }
        if (interval.min == 0) {
            bl4 = bl3;
            return this.getDefaultMargin(view, bl4, (boolean)var3_7, (boolean)var4_8);
        }
        bl4 = false;
        return this.getDefaultMargin(view, bl4, (boolean)var3_7, (boolean)var4_8);
    }

    private int getDefaultMargin(View view, boolean bl, boolean bl2) {
        if (view.getClass() == Space.class) {
            return 0;
        }
        return this.mDefaultGap / 2;
    }

    private int getDefaultMargin(View view, boolean bl, boolean bl2, boolean bl3) {
        return this.getDefaultMargin(view, bl2, bl3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private int getMargin(View object, boolean bl, boolean bl2) {
        void var2_6;
        void var3_7;
        int n;
        void var1_4;
        if (this.mAlignmentMode == 1) {
            return this.getMargin1((View)object, (boolean)var2_6, (boolean)var3_7);
        }
        Object object2 = var2_6 != false ? this.mHorizontalAxis : this.mVerticalAxis;
        object2 = var3_7 != false ? object2.getLeadingMargins() : object2.getTrailingMargins();
        LayoutParams layoutParams = this.getLayoutParams((View)object);
        if (var2_6 != false) {
            Spec spec = layoutParams.columnSpec;
        } else {
            Spec spec = layoutParams.rowSpec;
        }
        if (var3_7 != false) {
            n = var1_4.span.min;
            return object2[n];
        }
        n = var1_4.span.max;
        return object2[n];
    }

    private int getMeasurement(View view, boolean bl) {
        if (bl) {
            return view.getMeasuredWidth();
        }
        return view.getMeasuredHeight();
    }

    private int getTotalMargin(View view, boolean bl) {
        return this.getMargin(view, bl, true) + this.getMargin(view, bl, false);
    }

    static void handleInvalidParams(String string2) {
        throw new IllegalArgumentException(string2 + ". ");
    }

    private void invalidateStructure() {
        this.mLastLayoutParamsHashCode = 0;
        if (this.mHorizontalAxis != null) {
            this.mHorizontalAxis.invalidateStructure();
        }
        if (this.mVerticalAxis != null) {
            this.mVerticalAxis.invalidateStructure();
        }
        this.invalidateValues();
    }

    private void invalidateValues() {
        if (this.mHorizontalAxis != null && this.mVerticalAxis != null) {
            this.mHorizontalAxis.invalidateValues();
            this.mVerticalAxis.invalidateValues();
        }
    }

    private boolean isLayoutRtlCompat() {
        return ViewCompat.getLayoutDirection((View)this) == 1;
    }

    static int max2(int[] arrn, int n) {
        int n2 = 0;
        int n3 = arrn.length;
        int n4 = n;
        for (n = n2; n < n3; ++n) {
            n4 = Math.max(n4, arrn[n]);
        }
        return n4;
    }

    private void measureChildWithMargins2(View view, int n, int n2, int n3, int n4) {
        view.measure(GridLayout.getChildMeasureSpec((int)n, (int)this.getTotalMargin(view, true), (int)n3), GridLayout.getChildMeasureSpec((int)n2, (int)this.getTotalMargin(view, false), (int)n4));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChildrenWithMargins(int n, int n2, boolean bl) {
        int n3 = 0;
        int n4 = this.getChildCount();
        while (n3 < n4) {
            View view = this.getChildAt(n3);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = this.getLayoutParams(view);
                if (bl) {
                    this.measureChildWithMargins2(view, n, n2, layoutParams.width, layoutParams.height);
                } else {
                    boolean bl2 = this.mOrientation == 0;
                    Object object = bl2 ? layoutParams.columnSpec : layoutParams.rowSpec;
                    if (object.getAbsoluteAlignment(bl2) == FILL) {
                        Interval interval = object.span;
                        object = bl2 ? this.mHorizontalAxis : this.mVerticalAxis;
                        object = object.getLocations();
                        int n5 = object[interval.max] - object[interval.min] - this.getTotalMargin(view, bl2);
                        if (bl2) {
                            this.measureChildWithMargins2(view, n, n2, n5, layoutParams.height);
                        } else {
                            this.measureChildWithMargins2(view, n, n2, layoutParams.width, n5);
                        }
                    }
                }
            }
            ++n3;
        }
        return;
    }

    private static void procrusteanFill(int[] arrn, int n, int n2, int n3) {
        int n4 = arrn.length;
        Arrays.fill(arrn, Math.min(n, n4), Math.min(n2, n4), n3);
    }

    private static void setCellGroup(LayoutParams layoutParams, int n, int n2, int n3, int n4) {
        layoutParams.setRowSpecSpan(new Interval(n, n + n2));
        layoutParams.setColumnSpecSpan(new Interval(n3, n3 + n4));
    }

    public static Spec spec(int n) {
        return GridLayout.spec(n, 1);
    }

    public static Spec spec(int n, int n2) {
        return GridLayout.spec(n, n2, UNDEFINED_ALIGNMENT);
    }

    public static Spec spec(int n, int n2, Alignment alignment) {
        return GridLayout.spec(n, n2, alignment, 0.0f);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Spec spec(int n, int n2, Alignment alignment, float f) {
        boolean bl;
        if (n != Integer.MIN_VALUE) {
            bl = true;
            do {
                return new Spec(bl, n, n2, alignment, f);
                break;
            } while (true);
        }
        bl = false;
        return new Spec(bl, n, n2, alignment, f);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateLayoutParams() {
        boolean bl = this.mOrientation == 0;
        Object object = bl ? this.mHorizontalAxis : this.mVerticalAxis;
        int n = ((Axis)object).definedCount != Integer.MIN_VALUE ? ((Axis)object).definedCount : 0;
        int n2 = 0;
        int n3 = 0;
        int[] arrn = new int[n];
        int n4 = 0;
        int n5 = this.getChildCount();
        do {
            int n6;
            int n7;
            LayoutParams layoutParams;
            int n8;
            int n9;
            block10: {
                block12: {
                    int n10;
                    int n11;
                    boolean bl2;
                    block11: {
                        if (n4 >= n5) {
                            return;
                        }
                        layoutParams = (LayoutParams)this.getChildAt(n4).getLayoutParams();
                        object = bl ? layoutParams.rowSpec : layoutParams.columnSpec;
                        Interval interval = ((Spec)object).span;
                        boolean bl3 = ((Spec)object).startDefined;
                        n7 = interval.size();
                        if (bl3) {
                            n2 = interval.min;
                        }
                        object = bl ? layoutParams.columnSpec : layoutParams.rowSpec;
                        interval = ((Spec)object).span;
                        bl2 = ((Spec)object).startDefined;
                        n8 = GridLayout.clip(interval, bl2, n);
                        if (bl2) {
                            n3 = interval.min;
                        }
                        n9 = n2;
                        n6 = n3;
                        if (n == 0) break block10;
                        n11 = n2;
                        n10 = n3;
                        if (!bl3) break block11;
                        n9 = n2;
                        n6 = n3;
                        if (bl2) break block12;
                        n10 = n3;
                        n11 = n2;
                    }
                    do {
                        n9 = ++n11;
                        n6 = ++n10;
                        if (GridLayout.fits(arrn, n11, n10, n10 + n8)) break;
                        if (bl2 || n10 + n8 <= n) continue;
                        n10 = 0;
                        ++n11;
                    } while (true);
                }
                GridLayout.procrusteanFill(arrn, n6, n6 + n8, n9 + n7);
            }
            if (bl) {
                GridLayout.setCellGroup(layoutParams, n9, n7, n6, n8);
            } else {
                GridLayout.setCellGroup(layoutParams, n6, n8, n9, n7);
            }
            n3 = n6 + n8;
            ++n4;
            n2 = n9;
        } while (true);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams object) {
        if (!(object instanceof LayoutParams)) {
            return false;
        }
        object = (LayoutParams)((Object)object);
        this.checkLayoutParams((LayoutParams)((Object)object), true);
        this.checkLayoutParams((LayoutParams)((Object)object), false);
        return true;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int getAlignmentMode() {
        return this.mAlignmentMode;
    }

    public int getColumnCount() {
        return this.mHorizontalAxis.getCount();
    }

    final LayoutParams getLayoutParams(View view) {
        return (LayoutParams)view.getLayoutParams();
    }

    /*
     * Enabled aggressive block sorting
     */
    int getMargin1(View view, boolean bl, boolean bl2) {
        LayoutParams layoutParams = this.getLayoutParams(view);
        int n = bl ? (bl2 ? layoutParams.leftMargin : layoutParams.rightMargin) : (bl2 ? layoutParams.topMargin : layoutParams.bottomMargin);
        int n2 = n;
        if (n != Integer.MIN_VALUE) return n2;
        return this.getDefaultMargin(view, layoutParams, bl, bl2);
    }

    final int getMeasurementIncludingMargin(View view, boolean bl) {
        if (view.getVisibility() == 8) {
            return 0;
        }
        return this.getMeasurement(view, bl) + this.getTotalMargin(view, bl);
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public Printer getPrinter() {
        return this.mPrinter;
    }

    public int getRowCount() {
        return this.mVerticalAxis.getCount();
    }

    public boolean getUseDefaultMargins() {
        return this.mUseDefaultMargins;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.consistencyCheck();
        n3 -= n;
        int n5 = this.getPaddingLeft();
        int n6 = this.getPaddingTop();
        int n7 = this.getPaddingRight();
        n = this.getPaddingBottom();
        this.mHorizontalAxis.layout(n3 - n5 - n7);
        this.mVerticalAxis.layout(n4 - n2 - n6 - n);
        int[] arrn = this.mHorizontalAxis.getLocations();
        int[] arrn2 = this.mVerticalAxis.getLocations();
        n = 0;
        n4 = this.getChildCount();
        while (n < n4) {
            View view = this.getChildAt(n);
            if (view.getVisibility() != 8) {
                Object object = this.getLayoutParams(view);
                Object object2 = ((LayoutParams)object).columnSpec;
                object = ((LayoutParams)object).rowSpec;
                Object object3 = ((Spec)object2).span;
                Object object4 = ((Spec)object).span;
                n2 = arrn[((Interval)object3).min];
                int n8 = arrn2[((Interval)object4).min];
                int n9 = arrn[((Interval)object3).max];
                int n10 = arrn2[((Interval)object4).max];
                int n11 = n9 - n2;
                int n12 = n10 - n8;
                int n13 = this.getMeasurement(view, true);
                int n14 = this.getMeasurement(view, false);
                object2 = ((Spec)object2).getAbsoluteAlignment(true);
                object = ((Spec)object).getAbsoluteAlignment(false);
                object3 = this.mHorizontalAxis.getGroupBounds().getValue(n);
                object4 = this.mVerticalAxis.getGroupBounds().getValue(n);
                int n15 = ((Alignment)object2).getGravityOffset(view, n11 - ((Bounds)object3).size(true));
                n10 = ((Alignment)object).getGravityOffset(view, n12 - ((Bounds)object4).size(true));
                int n16 = this.getMargin(view, true, true);
                n9 = this.getMargin(view, false, true);
                int n17 = this.getMargin(view, true, false);
                int n18 = this.getMargin(view, false, false);
                int n19 = n16 + n17;
                int n20 = n9 + n18;
                int n21 = ((Bounds)object3).getOffset(this, view, (Alignment)object2, n13 + n19, true);
                n18 = ((Bounds)object4).getOffset(this, view, (Alignment)object, n14 + n20, false);
                n11 = ((Alignment)object2).getSizeInCell(view, n13, n11 - n19);
                n12 = ((Alignment)object).getSizeInCell(view, n14, n12 - n20);
                n2 = n2 + n15 + n21;
                n2 = !this.isLayoutRtlCompat() ? n5 + n16 + n2 : n3 - n11 - n7 - n17 - n2;
                n8 = n6 + n8 + n10 + n18 + n9;
                if (n11 != view.getMeasuredWidth() || n12 != view.getMeasuredHeight()) {
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n12, (int)1073741824));
                }
                view.layout(n2, n8, n2 + n11, n8 + n12);
            }
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        this.consistencyCheck();
        this.invalidateValues();
        int n5 = this.getPaddingLeft() + this.getPaddingRight();
        int n6 = this.getPaddingTop() + this.getPaddingBottom();
        int n7 = GridLayout.adjust(n, -n5);
        int n8 = GridLayout.adjust(n2, -n6);
        this.measureChildrenWithMargins(n7, n8, true);
        if (this.mOrientation == 0) {
            n3 = this.mHorizontalAxis.getMeasure(n7);
            this.measureChildrenWithMargins(n7, n8, false);
            n4 = this.mVerticalAxis.getMeasure(n8);
        } else {
            n4 = this.mVerticalAxis.getMeasure(n8);
            this.measureChildrenWithMargins(n7, n8, false);
            n3 = this.mHorizontalAxis.getMeasure(n7);
        }
        n3 = Math.max(n3 + n5, this.getSuggestedMinimumWidth());
        n4 = Math.max(n4 + n6, this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)n3, (int)n, (int)0), View.resolveSizeAndState((int)n4, (int)n2, (int)0));
    }

    public void requestLayout() {
        super.requestLayout();
        this.invalidateStructure();
    }

    public void setAlignmentMode(int n) {
        this.mAlignmentMode = n;
        this.requestLayout();
    }

    public void setColumnCount(int n) {
        this.mHorizontalAxis.setCount(n);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setColumnOrderPreserved(boolean bl) {
        this.mHorizontalAxis.setOrderPreserved(bl);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setOrientation(int n) {
        if (this.mOrientation != n) {
            this.mOrientation = n;
            this.invalidateStructure();
            this.requestLayout();
        }
    }

    public void setPrinter(Printer printer) {
        Printer printer2 = printer;
        if (printer == null) {
            printer2 = NO_PRINTER;
        }
        this.mPrinter = printer2;
    }

    public void setRowCount(int n) {
        this.mVerticalAxis.setCount(n);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setRowOrderPreserved(boolean bl) {
        this.mVerticalAxis.setOrderPreserved(bl);
        this.invalidateStructure();
        this.requestLayout();
    }

    public void setUseDefaultMargins(boolean bl) {
        this.mUseDefaultMargins = bl;
        this.requestLayout();
    }

    public static abstract class Alignment {
        Alignment() {
        }

        abstract int getAlignmentValue(View var1, int var2, int var3);

        Bounds getBounds() {
            return new Bounds();
        }

        abstract String getDebugString();

        abstract int getGravityOffset(View var1, int var2);

        int getSizeInCell(View view, int n, int n2) {
            return n;
        }

        public String toString() {
            return "Alignment:" + this.getDebugString();
        }
    }

    static final class Arc {
        public final Interval span;
        public boolean valid = true;
        public final MutableInt value;

        public Arc(Interval interval, MutableInt mutableInt) {
            this.span = interval;
            this.value = mutableInt;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public String toString() {
            String string2;
            StringBuilder stringBuilder = new StringBuilder().append(this.span).append(" ");
            if (!this.valid) {
                string2 = "+>";
                do {
                    return stringBuilder.append(string2).append(" ").append(this.value).toString();
                    break;
                } while (true);
            }
            string2 = "->";
            return stringBuilder.append(string2).append(" ").append(this.value).toString();
        }
    }

    static final class Assoc<K, V>
    extends ArrayList<Pair<K, V>> {
        private final Class<K> keyType;
        private final Class<V> valueType;

        private Assoc(Class<K> class_, Class<V> class_2) {
            this.keyType = class_;
            this.valueType = class_2;
        }

        public static <K, V> Assoc<K, V> of(Class<K> class_, Class<V> class_2) {
            return new Assoc<K, V>(class_, class_2);
        }

        public PackedMap<K, V> pack() {
            int n = this.size();
            Object[] arrobject = (Object[])Array.newInstance(this.keyType, n);
            Object[] arrobject2 = (Object[])Array.newInstance(this.valueType, n);
            for (int i = 0; i < n; ++i) {
                arrobject[i] = ((Pair)this.get((int)i)).first;
                arrobject2[i] = ((Pair)this.get((int)i)).second;
            }
            return new PackedMap<Object, Object>(arrobject, arrobject2);
        }

        public void put(K k, V v) {
            this.add(Pair.create(k, v));
        }
    }

    final class Axis {
        static final /* synthetic */ boolean $assertionsDisabled;
        public Arc[] arcs;
        public boolean arcsValid = false;
        PackedMap<Interval, MutableInt> backwardLinks;
        public boolean backwardLinksValid = false;
        public int definedCount = Integer.MIN_VALUE;
        public int[] deltas;
        PackedMap<Interval, MutableInt> forwardLinks;
        public boolean forwardLinksValid = false;
        PackedMap<Spec, Bounds> groupBounds;
        public boolean groupBoundsValid = false;
        public boolean hasWeights;
        public boolean hasWeightsValid = false;
        public final boolean horizontal;
        public int[] leadingMargins;
        public boolean leadingMarginsValid = false;
        public int[] locations;
        public boolean locationsValid = false;
        private int maxIndex = Integer.MIN_VALUE;
        boolean orderPreserved = true;
        private MutableInt parentMax;
        private MutableInt parentMin = new MutableInt(0);
        public int[] trailingMargins;
        public boolean trailingMarginsValid = false;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !GridLayout.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        Axis(boolean bl) {
            this.parentMax = new MutableInt(-100000);
            this.horizontal = bl;
        }

        private void addComponentSizes(List<Arc> list, PackedMap<Interval, MutableInt> packedMap) {
            for (int i = 0; i < ((Interval[])packedMap.keys).length; ++i) {
                this.include(list, ((Interval[])packedMap.keys)[i], ((MutableInt[])packedMap.values)[i], false);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private String arcsToString(List<Arc> object) {
            String string2 = this.horizontal ? "x" : "y";
            Object object2 = new StringBuilder();
            boolean bl = true;
            Iterator iterator = object.iterator();
            object = object2;
            while (iterator.hasNext()) {
                object2 = (Arc)iterator.next();
                if (bl) {
                    bl = false;
                } else {
                    object = ((StringBuilder)object).append(", ");
                }
                int n = object2.span.min;
                int n2 = object2.span.max;
                int n3 = object2.value.value;
                object2 = n < n2 ? string2 + n2 + "-" + string2 + n + ">=" + n3 : string2 + n + "-" + string2 + n2 + "<=" + -n3;
                ((StringBuilder)object).append((String)object2);
            }
            return ((StringBuilder)object).toString();
        }

        /*
         * Enabled aggressive block sorting
         */
        private int calculateMaxIndex() {
            int n;
            int n2 = -1;
            int n3 = GridLayout.this.getChildCount();
            for (n = 0; n < n3; ++n) {
                Object object = GridLayout.this.getChildAt(n);
                object = GridLayout.this.getLayoutParams((View)object);
                object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                object = ((Spec)object).span;
                n2 = Math.max(Math.max(Math.max(n2, ((Interval)object).min), ((Interval)object).max), ((Interval)object).size());
            }
            n = n2;
            if (n2 != -1) return n;
            return Integer.MIN_VALUE;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private float calculateTotalWeight() {
            float f = 0.0f;
            int n = 0;
            int n2 = GridLayout.this.getChildCount();
            while (n < n2) {
                View view = GridLayout.this.getChildAt(n);
                if (view.getVisibility() != 8) {
                    void var4_8;
                    LayoutParams layoutParams = GridLayout.this.getLayoutParams(view);
                    if (this.horizontal) {
                        Spec spec = layoutParams.columnSpec;
                    } else {
                        Spec spec = layoutParams.rowSpec;
                    }
                    f += var4_8.weight;
                }
                ++n;
            }
            return f;
        }

        private void computeArcs() {
            this.getForwardLinks();
            this.getBackwardLinks();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void computeGroupBounds() {
            int n;
            Bounds[] arrbounds = (Bounds[])this.groupBounds.values;
            for (n = 0; n < arrbounds.length; ++n) {
                arrbounds[n].reset();
            }
            n = 0;
            int n2 = GridLayout.this.getChildCount();
            while (n < n2) {
                void var5_5;
                View view = GridLayout.this.getChildAt(n);
                LayoutParams layoutParams = GridLayout.this.getLayoutParams(view);
                if (this.horizontal) {
                    Spec spec = layoutParams.columnSpec;
                } else {
                    Spec spec = layoutParams.rowSpec;
                }
                int n3 = GridLayout.this.getMeasurementIncludingMargin(view, this.horizontal);
                int n4 = var5_5.weight == 0.0f ? 0 : this.getDeltas()[n];
                this.groupBounds.getValue(n).include(GridLayout.this, view, (Spec)var5_5, this, n3 + n4);
                ++n;
            }
            return;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private boolean computeHasWeights() {
            int n = 0;
            int n2 = GridLayout.this.getChildCount();
            while (n < n2) {
                View view = GridLayout.this.getChildAt(n);
                if (view.getVisibility() != 8) {
                    void var3_7;
                    LayoutParams layoutParams = GridLayout.this.getLayoutParams(view);
                    if (this.horizontal) {
                        Spec spec = layoutParams.columnSpec;
                    } else {
                        Spec spec = layoutParams.rowSpec;
                    }
                    if (var3_7.weight != 0.0f) {
                        return true;
                    }
                }
                ++n;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void computeLinks(PackedMap<Interval, MutableInt> packedMap, boolean bl) {
            int n;
            Object[] arrobject = (MutableInt[])packedMap.values;
            for (n = 0; n < arrobject.length; ++n) {
                arrobject[n].reset();
            }
            arrobject = (Bounds[])this.getGroupBounds().values;
            n = 0;
            while (n < arrobject.length) {
                int n2 = ((Bounds)arrobject[n]).size(bl);
                MutableInt mutableInt = packedMap.getValue(n);
                int n3 = mutableInt.value;
                if (!bl) {
                    n2 = -n2;
                }
                mutableInt.value = Math.max(n3, n2);
                ++n;
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void computeLocations(int[] arrn) {
            if (!this.hasWeights()) {
                this.solve(arrn);
            } else {
                this.solveAndDistributeSpace(arrn);
            }
            if (!this.orderPreserved) {
                int n = arrn[0];
                int n2 = arrn.length;
                for (int i = 0; i < n2; ++i) {
                    arrn[i] = arrn[i] - n;
                }
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void computeMargins(boolean bl) {
            int[] arrn = bl ? this.leadingMargins : this.trailingMargins;
            int n = 0;
            int n2 = GridLayout.this.getChildCount();
            while (n < n2) {
                View view = GridLayout.this.getChildAt(n);
                if (view.getVisibility() != 8) {
                    void var6_9;
                    LayoutParams layoutParams = GridLayout.this.getLayoutParams(view);
                    if (this.horizontal) {
                        Spec spec = layoutParams.columnSpec;
                    } else {
                        Spec spec = layoutParams.rowSpec;
                    }
                    Interval interval = var6_9.span;
                    int n3 = bl ? interval.min : interval.max;
                    arrn[n3] = Math.max(arrn[n3], GridLayout.this.getMargin1(view, this.horizontal, bl));
                }
                ++n;
            }
            return;
        }

        private Arc[] createArcs() {
            int n;
            ArrayList<Arc> arrayList = new ArrayList<Arc>();
            ArrayList<Arc> arrayList2 = new ArrayList<Arc>();
            this.addComponentSizes(arrayList, this.getForwardLinks());
            this.addComponentSizes(arrayList2, this.getBackwardLinks());
            if (this.orderPreserved) {
                for (n = 0; n < this.getCount(); ++n) {
                    this.include(arrayList, new Interval(n, n + 1), new MutableInt(0));
                }
            }
            n = this.getCount();
            this.include(arrayList, new Interval(0, n), this.parentMin, false);
            this.include(arrayList2, new Interval(n, 0), this.parentMax, false);
            return GridLayout.append(this.topologicalSort(arrayList), this.topologicalSort(arrayList2));
        }

        /*
         * Enabled aggressive block sorting
         */
        private PackedMap<Spec, Bounds> createGroupBounds() {
            Assoc<Spec, Bounds> assoc = Assoc.of(Spec.class, Bounds.class);
            int n = 0;
            int n2 = GridLayout.this.getChildCount();
            while (n < n2) {
                Object object = GridLayout.this.getChildAt(n);
                object = GridLayout.this.getLayoutParams((View)object);
                object = this.horizontal ? ((LayoutParams)object).columnSpec : ((LayoutParams)object).rowSpec;
                assoc.put((Spec)object, ((Spec)object).getAbsoluteAlignment(this.horizontal).getBounds());
                ++n;
            }
            return assoc.pack();
        }

        /*
         * Enabled aggressive block sorting
         */
        private PackedMap<Interval, MutableInt> createLinks(boolean bl) {
            Assoc<Interval, MutableInt> assoc = Assoc.of(Interval.class, MutableInt.class);
            Spec[] arrspec = (Spec[])this.getGroupBounds().keys;
            int n = 0;
            int n2 = arrspec.length;
            while (n < n2) {
                Interval interval = bl ? arrspec[n].span : arrspec[n].span.inverse();
                assoc.put(interval, new MutableInt());
                ++n;
            }
            return assoc.pack();
        }

        private PackedMap<Interval, MutableInt> getBackwardLinks() {
            if (this.backwardLinks == null) {
                this.backwardLinks = this.createLinks(false);
            }
            if (!this.backwardLinksValid) {
                this.computeLinks(this.backwardLinks, false);
                this.backwardLinksValid = true;
            }
            return this.backwardLinks;
        }

        private PackedMap<Interval, MutableInt> getForwardLinks() {
            if (this.forwardLinks == null) {
                this.forwardLinks = this.createLinks(true);
            }
            if (!this.forwardLinksValid) {
                this.computeLinks(this.forwardLinks, true);
                this.forwardLinksValid = true;
            }
            return this.forwardLinks;
        }

        private int getMaxIndex() {
            if (this.maxIndex == Integer.MIN_VALUE) {
                this.maxIndex = Math.max(0, this.calculateMaxIndex());
            }
            return this.maxIndex;
        }

        private int getMeasure(int n, int n2) {
            this.setParentConstraints(n, n2);
            return this.size(this.getLocations());
        }

        private boolean hasWeights() {
            if (!this.hasWeightsValid) {
                this.hasWeights = this.computeHasWeights();
                this.hasWeightsValid = true;
            }
            return this.hasWeights;
        }

        private void include(List<Arc> list, Interval interval, MutableInt mutableInt) {
            this.include(list, interval, mutableInt, true);
        }

        private void include(List<Arc> list, Interval interval, MutableInt mutableInt, boolean bl) {
            if (interval.size() == 0) {
                return;
            }
            if (bl) {
                Iterator<Arc> iterator = list.iterator();
                while (iterator.hasNext()) {
                    if (!iterator.next().span.equals(interval)) continue;
                    return;
                }
            }
            list.add(new Arc(interval, mutableInt));
        }

        private void init(int[] arrn) {
            Arrays.fill(arrn, 0);
        }

        private void logError(String string2, Arc[] arrarc, boolean[] arrbl) {
            ArrayList<Arc> arrayList = new ArrayList<Arc>();
            ArrayList<Arc> arrayList2 = new ArrayList<Arc>();
            for (int i = 0; i < arrarc.length; ++i) {
                Arc arc = arrarc[i];
                if (arrbl[i]) {
                    arrayList.add(arc);
                }
                if (arc.valid) continue;
                arrayList2.add(arc);
            }
            GridLayout.this.mPrinter.println(string2 + " constraints: " + this.arcsToString(arrayList) + " are inconsistent; permanently removing: " + this.arcsToString(arrayList2) + ". ");
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean relax(int[] arrn, Arc arc) {
            int n;
            block3: {
                block2: {
                    if (!arc.valid) break block2;
                    Interval interval = arc.span;
                    n = interval.min;
                    int n2 = arc.value.value;
                    int n3 = interval.max;
                    if ((n = arrn[n] + n2) > arrn[n3]) break block3;
                }
                return false;
            }
            arrn[n3] = n;
            return true;
        }

        private void setParentConstraints(int n, int n2) {
            this.parentMin.value = n;
            this.parentMax.value = -n2;
            this.locationsValid = false;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void shareOutDelta(int n, float f) {
            Arrays.fill(this.deltas, 0);
            int n2 = 0;
            int n3 = GridLayout.this.getChildCount();
            int n4 = n;
            n = n2;
            while (n < n3) {
                float f2;
                View view = GridLayout.this.getChildAt(n);
                if (view.getVisibility() == 8) {
                    f2 = f;
                    n2 = n4;
                } else {
                    void var8_12;
                    LayoutParams layoutParams = GridLayout.this.getLayoutParams(view);
                    if (this.horizontal) {
                        Spec spec = layoutParams.columnSpec;
                    } else {
                        Spec spec = layoutParams.rowSpec;
                    }
                    float f3 = var8_12.weight;
                    n2 = n4;
                    f2 = f;
                    if (f3 != 0.0f) {
                        this.deltas[n] = n2 = Math.round((float)n4 * f3 / f);
                        n2 = n4 - n2;
                        f2 = f - f3;
                    }
                }
                ++n;
                n4 = n2;
                f = f2;
            }
            return;
        }

        private int size(int[] arrn) {
            return arrn[this.getCount()];
        }

        private boolean solve(int[] arrn) {
            return this.solve(this.getArcs(), arrn);
        }

        private boolean solve(Arc[] arrarc, int[] arrn) {
            return this.solve(arrarc, arrn, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean solve(Arc[] arrarc, int[] arrn, boolean bl) {
            String string2 = this.horizontal ? "horizontal" : "vertical";
            int n = this.getCount() + 1;
            boolean[] arrbl = null;
            int n2 = 0;
            while (n2 < arrarc.length) {
                int n3;
                int n4;
                int n5;
                this.init(arrn);
                for (n5 = 0; n5 < n; ++n5) {
                    n3 = 0;
                    int n6 = arrarc.length;
                    for (n4 = 0; n4 < n6; n3 |= this.relax(arrn, arrarc[n4]), ++n4) {
                    }
                    if (n3 != 0) continue;
                    if (arrbl != null) {
                        this.logError(string2, arrarc, arrbl);
                    }
                    return true;
                }
                if (!bl) {
                    return false;
                }
                boolean[] arrbl2 = new boolean[arrarc.length];
                for (n5 = 0; n5 < n; ++n5) {
                    n3 = arrarc.length;
                    for (n4 = 0; n4 < n3; ++n4) {
                        arrbl2[n4] = arrbl2[n4] | this.relax(arrn, arrarc[n4]);
                    }
                }
                if (n2 == 0) {
                    arrbl = arrbl2;
                }
                for (n5 = 0; n5 < arrarc.length; ++n5) {
                    if (!arrbl2[n5]) continue;
                    Arc arc = arrarc[n5];
                    if (arc.span.min < arc.span.max) {
                        continue;
                    }
                    arc.valid = false;
                    break;
                }
                ++n2;
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void solveAndDistributeSpace(int[] arrn) {
            int n;
            float f;
            block6: {
                block5: {
                    Arrays.fill(this.getDeltas(), 0);
                    this.solve(arrn);
                    int n2 = this.parentMin.value * GridLayout.this.getChildCount() + 1;
                    if (n2 < 2) break block5;
                    int n3 = 0;
                    f = this.calculateTotalWeight();
                    n = -1;
                    boolean bl = true;
                    while (n3 < n2) {
                        int n4 = (int)(((long)n3 + (long)n2) / 2L);
                        this.invalidateValues();
                        this.shareOutDelta(n4, f);
                        bl = this.solve(this.getArcs(), arrn, false);
                        if (bl) {
                            n = n4;
                            n3 = n4 + 1;
                            continue;
                        }
                        n2 = n4;
                    }
                    if (n > 0 && !bl) break block6;
                }
                return;
            }
            this.invalidateValues();
            this.shareOutDelta(n, f);
            this.solve(arrn);
        }

        private Arc[] topologicalSort(List<Arc> list) {
            return this.topologicalSort(list.toArray(new Arc[list.size()]));
        }

        private Arc[] topologicalSort(final Arc[] arrarc) {
            return new Object(){
                static final /* synthetic */ boolean $assertionsDisabled;
                Arc[][] arcsByVertex;
                int cursor;
                Arc[] result;
                int[] visited;

                /*
                 * Enabled aggressive block sorting
                 */
                static {
                    boolean bl = !GridLayout.class.desiredAssertionStatus();
                    $assertionsDisabled = bl;
                }
                {
                    this.result = new Arc[arrarc.length];
                    this.cursor = this.result.length - 1;
                    this.arcsByVertex = Axis.this.groupArcsByFirstVertex(arrarc);
                    this.visited = new int[Axis.this.getCount() + 1];
                }

                Arc[] sort() {
                    int n = this.arcsByVertex.length;
                    for (int i = 0; i < n; ++i) {
                        this.walk(i);
                    }
                    if (!$assertionsDisabled && this.cursor != -1) {
                        throw new AssertionError();
                    }
                    return this.result;
                }

                /*
                 * Enabled aggressive block sorting
                 */
                void walk(int n) {
                    switch (this.visited[n]) {
                        default: {
                            return;
                        }
                        case 0: {
                            this.visited[n] = 1;
                            Arc[] arrarc3 = this.arcsByVertex[n];
                            int n2 = arrarc3.length;
                            int n3 = 0;
                            do {
                                if (n3 >= n2) {
                                    this.visited[n] = 2;
                                    return;
                                }
                                Arc arc = arrarc3[n3];
                                this.walk(arc.span.max);
                                Arc[] arrarc2 = this.result;
                                int n4 = this.cursor;
                                this.cursor = n4 - 1;
                                arrarc2[n4] = arc;
                                ++n3;
                            } while (true);
                        }
                        case 1: {
                            if ($assertionsDisabled) return;
                            {
                                throw new AssertionError();
                            }
                        }
                    }
                }
            }.sort();
        }

        public Arc[] getArcs() {
            if (this.arcs == null) {
                this.arcs = this.createArcs();
            }
            if (!this.arcsValid) {
                this.computeArcs();
                this.arcsValid = true;
            }
            return this.arcs;
        }

        public int getCount() {
            return Math.max(this.definedCount, this.getMaxIndex());
        }

        public int[] getDeltas() {
            if (this.deltas == null) {
                this.deltas = new int[GridLayout.this.getChildCount()];
            }
            return this.deltas;
        }

        public PackedMap<Spec, Bounds> getGroupBounds() {
            if (this.groupBounds == null) {
                this.groupBounds = this.createGroupBounds();
            }
            if (!this.groupBoundsValid) {
                this.computeGroupBounds();
                this.groupBoundsValid = true;
            }
            return this.groupBounds;
        }

        public int[] getLeadingMargins() {
            if (this.leadingMargins == null) {
                this.leadingMargins = new int[this.getCount() + 1];
            }
            if (!this.leadingMarginsValid) {
                this.computeMargins(true);
                this.leadingMarginsValid = true;
            }
            return this.leadingMargins;
        }

        public int[] getLocations() {
            if (this.locations == null) {
                this.locations = new int[this.getCount() + 1];
            }
            if (!this.locationsValid) {
                this.computeLocations(this.locations);
                this.locationsValid = true;
            }
            return this.locations;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int getMeasure(int n) {
            int n2 = 0;
            int n3 = View.MeasureSpec.getMode((int)n);
            n = View.MeasureSpec.getSize((int)n);
            switch (n3) {
                default: {
                    n = n2;
                    if ($assertionsDisabled) return n;
                    throw new AssertionError();
                }
                case 0: {
                    return this.getMeasure(0, 100000);
                }
                case 1073741824: {
                    return this.getMeasure(n, n);
                }
                case Integer.MIN_VALUE: 
            }
            return this.getMeasure(0, n);
        }

        public int[] getTrailingMargins() {
            if (this.trailingMargins == null) {
                this.trailingMargins = new int[this.getCount() + 1];
            }
            if (!this.trailingMarginsValid) {
                this.computeMargins(false);
                this.trailingMarginsValid = true;
            }
            return this.trailingMargins;
        }

        Arc[][] groupArcsByFirstVertex(Arc[] arrarc) {
            int n;
            int n2 = 0;
            int n3 = this.getCount() + 1;
            Arc[][] arrarc2 = new Arc[n3][];
            int[] arrn = new int[n3];
            int n4 = arrarc.length;
            for (n3 = 0; n3 < n4; ++n3) {
                n = arrarc[n3].span.min;
                arrn[n] = arrn[n] + 1;
            }
            for (n3 = 0; n3 < arrn.length; ++n3) {
                arrarc2[n3] = new Arc[arrn[n3]];
            }
            Arrays.fill(arrn, 0);
            n4 = arrarc.length;
            for (n3 = n2; n3 < n4; ++n3) {
                Arc arc = arrarc[n3];
                n2 = arc.span.min;
                Arc[] arrarc3 = arrarc2[n2];
                n = arrn[n2];
                arrn[n2] = n + 1;
                arrarc3[n] = arc;
            }
            return arrarc2;
        }

        public void invalidateStructure() {
            this.maxIndex = Integer.MIN_VALUE;
            this.groupBounds = null;
            this.forwardLinks = null;
            this.backwardLinks = null;
            this.leadingMargins = null;
            this.trailingMargins = null;
            this.arcs = null;
            this.locations = null;
            this.deltas = null;
            this.hasWeightsValid = false;
            this.invalidateValues();
        }

        public void invalidateValues() {
            this.groupBoundsValid = false;
            this.forwardLinksValid = false;
            this.backwardLinksValid = false;
            this.leadingMarginsValid = false;
            this.trailingMarginsValid = false;
            this.arcsValid = false;
            this.locationsValid = false;
        }

        public void layout(int n) {
            this.setParentConstraints(n, n);
            this.getLocations();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setCount(int n) {
            if (n != Integer.MIN_VALUE && n < this.getMaxIndex()) {
                StringBuilder stringBuilder = new StringBuilder();
                String string2 = this.horizontal ? "column" : "row";
                GridLayout.handleInvalidParams(stringBuilder.append(string2).append("Count must be greater than or equal to the maximum of all grid indices ").append("(and spans) defined in the LayoutParams of each child").toString());
            }
            this.definedCount = n;
        }

        public void setOrderPreserved(boolean bl) {
            this.orderPreserved = bl;
            this.invalidateStructure();
        }

    }

    static class Bounds {
        public int after;
        public int before;
        public int flexibility;

        Bounds() {
            this.reset();
        }

        protected int getOffset(GridLayout gridLayout, View view, Alignment alignment, int n, boolean bl) {
            return this.before - alignment.getAlignmentValue(view, n, ViewGroupCompat.getLayoutMode(gridLayout));
        }

        protected void include(int n, int n2) {
            this.before = Math.max(this.before, n);
            this.after = Math.max(this.after, n2);
        }

        protected final void include(GridLayout gridLayout, View view, Spec spec, Axis axis, int n) {
            this.flexibility &= spec.getFlexibility();
            int n2 = spec.getAbsoluteAlignment(axis.horizontal).getAlignmentValue(view, n, ViewGroupCompat.getLayoutMode(gridLayout));
            this.include(n2, n - n2);
        }

        protected void reset() {
            this.before = Integer.MIN_VALUE;
            this.after = Integer.MIN_VALUE;
            this.flexibility = 2;
        }

        protected int size(boolean bl) {
            if (!bl && GridLayout.canStretch(this.flexibility)) {
                return 100000;
            }
            return this.before + this.after;
        }

        public String toString() {
            return "Bounds{before=" + this.before + ", after=" + this.after + '}';
        }
    }

    static final class Interval {
        public final int max;
        public final int min;

        public Interval(int n, int n2) {
            this.min = n;
            this.max = n2;
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
                    object = (Interval)object;
                    if (this.max != ((Interval)object).max) {
                        return false;
                    }
                    if (this.min != ((Interval)object).min) break block6;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.min * 31 + this.max;
        }

        Interval inverse() {
            return new Interval(this.max, this.min);
        }

        int size() {
            return this.max - this.min;
        }

        public String toString() {
            return "[" + this.min + ", " + this.max + "]";
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        private static final int BOTTOM_MARGIN;
        private static final int COLUMN;
        private static final int COLUMN_SPAN;
        private static final int COLUMN_WEIGHT;
        private static final Interval DEFAULT_SPAN;
        private static final int DEFAULT_SPAN_SIZE;
        private static final int GRAVITY;
        private static final int LEFT_MARGIN;
        private static final int MARGIN;
        private static final int RIGHT_MARGIN;
        private static final int ROW;
        private static final int ROW_SPAN;
        private static final int ROW_WEIGHT;
        private static final int TOP_MARGIN;
        public Spec columnSpec;
        public Spec rowSpec = Spec.UNDEFINED;

        static {
            DEFAULT_SPAN = new Interval(Integer.MIN_VALUE, -2147483647);
            DEFAULT_SPAN_SIZE = DEFAULT_SPAN.size();
            MARGIN = R.styleable.GridLayout_Layout_android_layout_margin;
            LEFT_MARGIN = R.styleable.GridLayout_Layout_android_layout_marginLeft;
            TOP_MARGIN = R.styleable.GridLayout_Layout_android_layout_marginTop;
            RIGHT_MARGIN = R.styleable.GridLayout_Layout_android_layout_marginRight;
            BOTTOM_MARGIN = R.styleable.GridLayout_Layout_android_layout_marginBottom;
            COLUMN = R.styleable.GridLayout_Layout_layout_column;
            COLUMN_SPAN = R.styleable.GridLayout_Layout_layout_columnSpan;
            COLUMN_WEIGHT = R.styleable.GridLayout_Layout_layout_columnWeight;
            ROW = R.styleable.GridLayout_Layout_layout_row;
            ROW_SPAN = R.styleable.GridLayout_Layout_layout_rowSpan;
            ROW_WEIGHT = R.styleable.GridLayout_Layout_layout_rowWeight;
            GRAVITY = R.styleable.GridLayout_Layout_layout_gravity;
        }

        public LayoutParams() {
            this(Spec.UNDEFINED, Spec.UNDEFINED);
        }

        private LayoutParams(int n, int n2, int n3, int n4, int n5, int n6, Spec spec, Spec spec2) {
            super(n, n2);
            this.columnSpec = Spec.UNDEFINED;
            this.setMargins(n3, n4, n5, n6);
            this.rowSpec = spec;
            this.columnSpec = spec2;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.columnSpec = Spec.UNDEFINED;
            this.reInitSuper(context, attributeSet);
            this.init(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.columnSpec = Spec.UNDEFINED;
            this.rowSpec = layoutParams.rowSpec;
            this.columnSpec = layoutParams.columnSpec;
        }

        public LayoutParams(Spec spec, Spec spec2) {
            this(-2, -2, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, spec, spec2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.columnSpec = Spec.UNDEFINED;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.columnSpec = Spec.UNDEFINED;
        }

        private void init(Context context, AttributeSet attributeSet) {
            context = context.obtainStyledAttributes(attributeSet, R.styleable.GridLayout_Layout);
            try {
                int n = context.getInt(GRAVITY, 0);
                int n2 = context.getInt(COLUMN, Integer.MIN_VALUE);
                int n3 = context.getInt(COLUMN_SPAN, DEFAULT_SPAN_SIZE);
                float f = context.getFloat(COLUMN_WEIGHT, 0.0f);
                this.columnSpec = GridLayout.spec(n2, n3, GridLayout.getAlignment(n, true), f);
                n2 = context.getInt(ROW, Integer.MIN_VALUE);
                n3 = context.getInt(ROW_SPAN, DEFAULT_SPAN_SIZE);
                f = context.getFloat(ROW_WEIGHT, 0.0f);
                this.rowSpec = GridLayout.spec(n2, n3, GridLayout.getAlignment(n, false), f);
                return;
            }
            finally {
                context.recycle();
            }
        }

        private void reInitSuper(Context context, AttributeSet attributeSet) {
            context = context.obtainStyledAttributes(attributeSet, R.styleable.GridLayout_Layout);
            try {
                int n = context.getDimensionPixelSize(MARGIN, Integer.MIN_VALUE);
                this.leftMargin = context.getDimensionPixelSize(LEFT_MARGIN, n);
                this.topMargin = context.getDimensionPixelSize(TOP_MARGIN, n);
                this.rightMargin = context.getDimensionPixelSize(RIGHT_MARGIN, n);
                this.bottomMargin = context.getDimensionPixelSize(BOTTOM_MARGIN, n);
                return;
            }
            finally {
                context.recycle();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6: {
                block5: {
                    if (this == object) break block5;
                    if (object == null || ((Object)((Object)this)).getClass() != object.getClass()) {
                        return false;
                    }
                    LayoutParams layoutParams = (LayoutParams)((Object)object);
                    if (!this.columnSpec.equals(layoutParams.columnSpec)) {
                        return false;
                    }
                    if (!this.rowSpec.equals(layoutParams.rowSpec)) break block6;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.rowSpec.hashCode() * 31 + this.columnSpec.hashCode();
        }

        protected void setBaseAttributes(TypedArray typedArray, int n, int n2) {
            this.width = typedArray.getLayoutDimension(n, -2);
            this.height = typedArray.getLayoutDimension(n2, -2);
        }

        final void setColumnSpecSpan(Interval interval) {
            this.columnSpec = this.columnSpec.copyWriteSpan(interval);
        }

        final void setRowSpecSpan(Interval interval) {
            this.rowSpec = this.rowSpec.copyWriteSpan(interval);
        }
    }

    static final class MutableInt {
        public int value;

        public MutableInt() {
            this.reset();
        }

        public MutableInt(int n) {
            this.value = n;
        }

        public void reset() {
            this.value = Integer.MIN_VALUE;
        }

        public String toString() {
            return Integer.toString(this.value);
        }
    }

    static final class PackedMap<K, V> {
        public final int[] index;
        public final K[] keys;
        public final V[] values;

        PackedMap(K[] arrK, V[] arrV) {
            this.index = PackedMap.createIndex(arrK);
            this.keys = PackedMap.compact(arrK, this.index);
            this.values = PackedMap.compact(arrV, this.index);
        }

        private static <K> K[] compact(K[] arrK, int[] arrn) {
            int n = arrK.length;
            Object[] arrobject = (Object[])Array.newInstance(arrK.getClass().getComponentType(), GridLayout.max2(arrn, -1) + 1);
            for (int i = 0; i < n; ++i) {
                arrobject[arrn[i]] = arrK[i];
            }
            return arrobject;
        }

        private static <K> int[] createIndex(K[] arrK) {
            int n = arrK.length;
            int[] arrn = new int[n];
            HashMap<K, Integer> hashMap = new HashMap<K, Integer>();
            for (int i = 0; i < n; ++i) {
                Integer n2;
                K k = arrK[i];
                Integer n3 = n2 = (Integer)hashMap.get(k);
                if (n2 == null) {
                    n3 = hashMap.size();
                    hashMap.put(k, n3);
                }
                arrn[i] = n3;
            }
            return arrn;
        }

        public V getValue(int n) {
            return this.values[this.index[n]];
        }
    }

    public static class Spec {
        static final Spec UNDEFINED = GridLayout.spec(Integer.MIN_VALUE);
        final Alignment alignment;
        final Interval span;
        final boolean startDefined;
        final float weight;

        Spec(boolean bl, int n, int n2, Alignment alignment, float f) {
            this(bl, new Interval(n, n + n2), alignment, f);
        }

        private Spec(boolean bl, Interval interval, Alignment alignment, float f) {
            this.startDefined = bl;
            this.span = interval;
            this.alignment = alignment;
            this.weight = f;
        }

        final Spec copyWriteSpan(Interval interval) {
            return new Spec(this.startDefined, interval, this.alignment, this.weight);
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
                    object = (Spec)object;
                    if (!this.alignment.equals(((Spec)object).alignment)) {
                        return false;
                    }
                    if (!this.span.equals(((Spec)object).span)) break block6;
                }
                return true;
            }
            return false;
        }

        public Alignment getAbsoluteAlignment(boolean bl) {
            if (this.alignment != UNDEFINED_ALIGNMENT) {
                return this.alignment;
            }
            if (this.weight == 0.0f) {
                if (bl) {
                    return START;
                }
                return BASELINE;
            }
            return FILL;
        }

        final int getFlexibility() {
            if (this.alignment == UNDEFINED_ALIGNMENT && this.weight == 0.0f) {
                return 0;
            }
            return 2;
        }

        public int hashCode() {
            return this.span.hashCode() * 31 + this.alignment.hashCode();
        }
    }

}

