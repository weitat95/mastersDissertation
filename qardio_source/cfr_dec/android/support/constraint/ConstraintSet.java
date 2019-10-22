/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.util.Xml
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.constraint;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.constraint.R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet {
    private static final int[] VISIBILITY_FLAGS = new int[]{0, 4, 8};
    private static SparseIntArray mapToConstant = new SparseIntArray();
    private HashMap<Integer, Constraint> mConstraints = new HashMap();

    static {
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_toRightOf, 26);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_toLeftOf, 29);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_toRightOf, 30);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_toTopOf, 36);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_toBottomOf, 35);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_toTopOf, 4);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
        mapToConstant.append(R.styleable.ConstraintSet_layout_editor_absoluteX, 6);
        mapToConstant.append(R.styleable.ConstraintSet_layout_editor_absoluteY, 7);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_begin, 17);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_end, 18);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_percent, 19);
        mapToConstant.append(R.styleable.ConstraintSet_android_orientation, 27);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintStart_toEndOf, 32);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintStart_toStartOf, 33);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintEnd_toStartOf, 10);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintEnd_toEndOf, 9);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginLeft, 13);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginTop, 16);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginRight, 14);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginBottom, 11);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginStart, 15);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginEnd, 12);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_weight, 40);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_weight, 39);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_chainStyle, 42);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_bias, 20);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_bias, 37);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintDimensionRatio, 5);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBaseline_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginLeft, 24);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginRight, 28);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginStart, 31);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginEnd, 8);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginTop, 34);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginBottom, 2);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_width, 23);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_height, 21);
        mapToConstant.append(R.styleable.ConstraintSet_android_visibility, 22);
        mapToConstant.append(R.styleable.ConstraintSet_android_alpha, 43);
        mapToConstant.append(R.styleable.ConstraintSet_android_elevation, 44);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotationX, 45);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotationY, 46);
        mapToConstant.append(R.styleable.ConstraintSet_android_scaleX, 47);
        mapToConstant.append(R.styleable.ConstraintSet_android_scaleY, 48);
        mapToConstant.append(R.styleable.ConstraintSet_android_transformPivotX, 49);
        mapToConstant.append(R.styleable.ConstraintSet_android_transformPivotY, 50);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationX, 51);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationY, 52);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationZ, 53);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_default, 54);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_default, 55);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_max, 56);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_max, 57);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_min, 58);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_min, 59);
        mapToConstant.append(R.styleable.ConstraintSet_android_id, 38);
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attributeSet) {
        Constraint constraint = new Constraint();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ConstraintSet);
        this.populateConstraint(constraint, (TypedArray)context);
        context.recycle();
        return constraint;
    }

    private static int lookupID(TypedArray typedArray, int n, int n2) {
        int n3;
        n2 = n3 = typedArray.getResourceId(n, n2);
        if (n3 == -1) {
            n2 = typedArray.getInt(n, -1);
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void populateConstraint(Constraint constraint, TypedArray typedArray) {
        int n = typedArray.getIndexCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = typedArray.getIndex(n2);
            switch (mapToConstant.get(n3)) {
                default: {
                    Log.w((String)"ConstraintSet", (String)("Unknown attribute 0x" + Integer.toHexString(n3) + "   " + mapToConstant.get(n3)));
                    break;
                }
                case 25: {
                    constraint.leftToLeft = ConstraintSet.lookupID(typedArray, n3, constraint.leftToLeft);
                    break;
                }
                case 26: {
                    constraint.leftToRight = ConstraintSet.lookupID(typedArray, n3, constraint.leftToRight);
                    break;
                }
                case 29: {
                    constraint.rightToLeft = ConstraintSet.lookupID(typedArray, n3, constraint.rightToLeft);
                    break;
                }
                case 30: {
                    constraint.rightToRight = ConstraintSet.lookupID(typedArray, n3, constraint.rightToRight);
                    break;
                }
                case 36: {
                    constraint.topToTop = ConstraintSet.lookupID(typedArray, n3, constraint.topToTop);
                    break;
                }
                case 35: {
                    constraint.topToBottom = ConstraintSet.lookupID(typedArray, n3, constraint.topToBottom);
                    break;
                }
                case 4: {
                    constraint.bottomToTop = ConstraintSet.lookupID(typedArray, n3, constraint.bottomToTop);
                    break;
                }
                case 3: {
                    constraint.bottomToBottom = ConstraintSet.lookupID(typedArray, n3, constraint.bottomToBottom);
                    break;
                }
                case 1: {
                    constraint.baselineToBaseline = ConstraintSet.lookupID(typedArray, n3, constraint.baselineToBaseline);
                    break;
                }
                case 6: {
                    constraint.editorAbsoluteX = typedArray.getDimensionPixelOffset(n3, constraint.editorAbsoluteX);
                    break;
                }
                case 7: {
                    constraint.editorAbsoluteY = typedArray.getDimensionPixelOffset(n3, constraint.editorAbsoluteY);
                    break;
                }
                case 17: {
                    constraint.guideBegin = typedArray.getDimensionPixelOffset(n3, constraint.guideBegin);
                    break;
                }
                case 18: {
                    constraint.guideEnd = typedArray.getDimensionPixelOffset(n3, constraint.guideEnd);
                    break;
                }
                case 19: {
                    constraint.guidePercent = typedArray.getFloat(n3, constraint.guidePercent);
                    break;
                }
                case 27: {
                    constraint.orientation = typedArray.getInt(n3, constraint.orientation);
                    break;
                }
                case 32: {
                    constraint.startToEnd = ConstraintSet.lookupID(typedArray, n3, constraint.startToEnd);
                    break;
                }
                case 33: {
                    constraint.startToStart = ConstraintSet.lookupID(typedArray, n3, constraint.startToStart);
                    break;
                }
                case 10: {
                    constraint.endToStart = ConstraintSet.lookupID(typedArray, n3, constraint.endToStart);
                    break;
                }
                case 9: {
                    constraint.bottomToTop = ConstraintSet.lookupID(typedArray, n3, constraint.endToEnd);
                    break;
                }
                case 13: {
                    constraint.goneLeftMargin = typedArray.getDimensionPixelSize(n3, constraint.goneLeftMargin);
                    break;
                }
                case 16: {
                    constraint.goneTopMargin = typedArray.getDimensionPixelSize(n3, constraint.goneTopMargin);
                    break;
                }
                case 14: {
                    constraint.goneRightMargin = typedArray.getDimensionPixelSize(n3, constraint.goneRightMargin);
                    break;
                }
                case 11: {
                    constraint.goneBottomMargin = typedArray.getDimensionPixelSize(n3, constraint.goneBottomMargin);
                    break;
                }
                case 15: {
                    constraint.goneStartMargin = typedArray.getDimensionPixelSize(n3, constraint.goneStartMargin);
                    break;
                }
                case 12: {
                    constraint.goneEndMargin = typedArray.getDimensionPixelSize(n3, constraint.goneEndMargin);
                    break;
                }
                case 20: {
                    constraint.horizontalBias = typedArray.getFloat(n3, constraint.horizontalBias);
                    break;
                }
                case 37: {
                    constraint.verticalBias = typedArray.getFloat(n3, constraint.verticalBias);
                    break;
                }
                case 24: {
                    constraint.leftMargin = typedArray.getDimensionPixelSize(n3, constraint.leftMargin);
                    break;
                }
                case 28: {
                    constraint.rightMargin = typedArray.getDimensionPixelSize(n3, constraint.rightMargin);
                    break;
                }
                case 31: {
                    constraint.startMargin = typedArray.getDimensionPixelSize(n3, constraint.startMargin);
                    break;
                }
                case 8: {
                    constraint.endMargin = typedArray.getDimensionPixelSize(n3, constraint.endMargin);
                    break;
                }
                case 34: {
                    constraint.topMargin = typedArray.getDimensionPixelSize(n3, constraint.topMargin);
                    break;
                }
                case 2: {
                    constraint.bottomMargin = typedArray.getDimensionPixelSize(n3, constraint.bottomMargin);
                    break;
                }
                case 23: {
                    constraint.mWidth = typedArray.getLayoutDimension(n3, constraint.mWidth);
                    break;
                }
                case 21: {
                    constraint.mHeight = typedArray.getLayoutDimension(n3, constraint.mHeight);
                    break;
                }
                case 22: {
                    constraint.visibility = typedArray.getInt(n3, constraint.visibility);
                    constraint.visibility = VISIBILITY_FLAGS[constraint.visibility];
                    break;
                }
                case 43: {
                    constraint.alpha = typedArray.getFloat(n3, constraint.alpha);
                    break;
                }
                case 44: {
                    constraint.applyElevation = true;
                    constraint.elevation = typedArray.getFloat(n3, constraint.elevation);
                    break;
                }
                case 45: {
                    constraint.rotationX = typedArray.getFloat(n3, constraint.rotationX);
                    break;
                }
                case 46: {
                    constraint.rotationY = typedArray.getFloat(n3, constraint.rotationY);
                    break;
                }
                case 47: {
                    constraint.scaleX = typedArray.getFloat(n3, constraint.scaleX);
                    break;
                }
                case 48: {
                    constraint.scaleY = typedArray.getFloat(n3, constraint.scaleY);
                    break;
                }
                case 49: {
                    constraint.transformPivotX = typedArray.getFloat(n3, constraint.transformPivotX);
                    break;
                }
                case 50: {
                    constraint.transformPivotY = typedArray.getFloat(n3, constraint.transformPivotY);
                    break;
                }
                case 51: {
                    constraint.translationX = typedArray.getFloat(n3, constraint.translationX);
                    break;
                }
                case 52: {
                    constraint.translationY = typedArray.getFloat(n3, constraint.translationY);
                    break;
                }
                case 53: {
                    constraint.translationZ = typedArray.getFloat(n3, constraint.translationZ);
                    break;
                }
                case 40: {
                    constraint.verticalWeight = typedArray.getFloat(n3, constraint.verticalWeight);
                    break;
                }
                case 39: {
                    constraint.horizontalWeight = typedArray.getFloat(n3, constraint.horizontalWeight);
                    break;
                }
                case 42: {
                    constraint.verticalChainStyle = typedArray.getInt(n3, constraint.verticalChainStyle);
                    break;
                }
                case 41: {
                    constraint.horizontalChainStyle = typedArray.getInt(n3, constraint.horizontalChainStyle);
                    break;
                }
                case 38: {
                    constraint.mViewId = typedArray.getResourceId(n3, constraint.mViewId);
                    break;
                }
                case 5: {
                    constraint.dimensionRatio = typedArray.getString(n3);
                    break;
                }
                case 60: {
                    Log.w((String)"ConstraintSet", (String)("unused attribute 0x" + Integer.toHexString(n3) + "   " + mapToConstant.get(n3)));
                }
            }
            ++n2;
        }
        return;
    }

    void applyToInternal(ConstraintLayout constraintLayout) {
        Object object;
        Object object2;
        Object object3;
        int n = constraintLayout.getChildCount();
        Object object4 = new HashSet<Integer>(this.mConstraints.keySet());
        for (int i = 0; i < n; ++i) {
            object2 = constraintLayout.getChildAt(i);
            int n2 = object2.getId();
            if (!this.mConstraints.containsKey(n2)) continue;
            ((HashSet)object4).remove(n2);
            object = this.mConstraints.get(n2);
            object3 = (ConstraintLayout.LayoutParams)object2.getLayoutParams();
            ((Constraint)object).applyTo((ConstraintLayout.LayoutParams)((Object)object3));
            object2.setLayoutParams((ViewGroup.LayoutParams)object3);
            object2.setVisibility(((Constraint)object).visibility);
            if (Build.VERSION.SDK_INT < 17) continue;
            object2.setAlpha(((Constraint)object).alpha);
            object2.setRotationX(((Constraint)object).rotationX);
            object2.setRotationY(((Constraint)object).rotationY);
            object2.setScaleX(((Constraint)object).scaleX);
            object2.setScaleY(((Constraint)object).scaleY);
            object2.setPivotX(((Constraint)object).transformPivotX);
            object2.setPivotY(((Constraint)object).transformPivotY);
            object2.setTranslationX(((Constraint)object).translationX);
            object2.setTranslationY(((Constraint)object).translationY);
            if (Build.VERSION.SDK_INT < 21) continue;
            object2.setTranslationZ(((Constraint)object).translationZ);
            if (!((Constraint)object).applyElevation) continue;
            object2.setElevation(((Constraint)object).elevation);
        }
        object4 = ((HashSet)object4).iterator();
        while (object4.hasNext()) {
            object3 = (Integer)object4.next();
            object2 = this.mConstraints.get(object3);
            if (!((Constraint)object2).mIsGuideline) continue;
            object = new Guideline(constraintLayout.getContext());
            object.setId(object3.intValue());
            object3 = constraintLayout.generateDefaultLayoutParams();
            ((Constraint)object2).applyTo((ConstraintLayout.LayoutParams)((Object)object3));
            constraintLayout.addView((View)object, (ViewGroup.LayoutParams)object3);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void load(Context var1_1, int var2_4) {
        var3_5 = var1_1.getResources().getXml(var2_4);
        try {
            var2_4 = var3_5.getEventType();
            ** GOTO lbl12
        }
        catch (XmlPullParserException var1_2) {
            var1_2.printStackTrace();
            return;
        }
        catch (IOException var1_3) {
            var1_3.printStackTrace();
            return;
        }
lbl-1000:
        // 5 sources
        {
            var2_4 = var3_5.next();
lbl12:
            // 2 sources
            if (var2_4 == 1) return;
            switch (var2_4) {
                case 1: {
                    continue block9;
                }
                case 0: {
                    var3_5.getName();
                    continue block9;
                }
                case 2: {
                    var4_6 = var3_5.getName();
                    var5_7 = this.fillFromAttributeList(var1_1, Xml.asAttributeSet((XmlPullParser)var3_5));
                    if (var4_6.equalsIgnoreCase("Guideline")) {
                        var5_7.mIsGuideline = true;
                    }
                    this.mConstraints.put(var5_7.mViewId, var5_7);
                    continue block9;
                }
                case 3: {
                    continue block9;
                }
            }
            ** while (true)
        }
    }

    private static class Constraint {
        public float alpha = 1.0f;
        public boolean applyElevation = false;
        public int baselineToBaseline = -1;
        public int bottomMargin = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public String dimensionRatio = null;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public float elevation = 0.0f;
        public int endMargin = -1;
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
        public int heightDefault = -1;
        public int heightMax = -1;
        public int heightMin = -1;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        public float horizontalWeight = 0.0f;
        public int leftMargin = -1;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int mHeight;
        boolean mIsGuideline = false;
        int mViewId;
        public int mWidth;
        public int orientation = -1;
        public int rightMargin = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public int startMargin = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topMargin = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float transformPivotX = 0.0f;
        public float transformPivotY = 0.0f;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        public float verticalWeight = 0.0f;
        public int visibility = 0;
        public int widthDefault = -1;
        public int widthMax = -1;
        public int widthMin = -1;

        private Constraint() {
        }

        public void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            layoutParams.leftToLeft = this.leftToLeft;
            layoutParams.leftToRight = this.leftToRight;
            layoutParams.rightToLeft = this.rightToLeft;
            layoutParams.rightToRight = this.rightToRight;
            layoutParams.topToTop = this.topToTop;
            layoutParams.topToBottom = this.topToBottom;
            layoutParams.bottomToTop = this.bottomToTop;
            layoutParams.bottomToBottom = this.bottomToBottom;
            layoutParams.baselineToBaseline = this.baselineToBaseline;
            layoutParams.startToEnd = this.startToEnd;
            layoutParams.startToStart = this.startToStart;
            layoutParams.endToStart = this.endToStart;
            layoutParams.endToEnd = this.endToEnd;
            layoutParams.leftMargin = this.leftMargin;
            layoutParams.rightMargin = this.rightMargin;
            layoutParams.topMargin = this.topMargin;
            layoutParams.bottomMargin = this.bottomMargin;
            layoutParams.goneStartMargin = this.goneStartMargin;
            layoutParams.goneEndMargin = this.goneEndMargin;
            layoutParams.horizontalBias = this.horizontalBias;
            layoutParams.verticalBias = this.verticalBias;
            layoutParams.dimensionRatio = this.dimensionRatio;
            layoutParams.editorAbsoluteX = this.editorAbsoluteX;
            layoutParams.editorAbsoluteY = this.editorAbsoluteY;
            layoutParams.verticalWeight = this.verticalWeight;
            layoutParams.horizontalWeight = this.horizontalWeight;
            layoutParams.verticalChainStyle = this.verticalChainStyle;
            layoutParams.horizontalChainStyle = this.horizontalChainStyle;
            layoutParams.matchConstraintDefaultWidth = this.widthDefault;
            layoutParams.matchConstraintDefaultHeight = this.heightDefault;
            layoutParams.matchConstraintMaxWidth = this.widthMax;
            layoutParams.matchConstraintMaxHeight = this.heightMax;
            layoutParams.matchConstraintMinWidth = this.widthMin;
            layoutParams.matchConstraintMinHeight = this.heightMin;
            layoutParams.orientation = this.orientation;
            layoutParams.guidePercent = this.guidePercent;
            layoutParams.guideBegin = this.guideBegin;
            layoutParams.guideEnd = this.guideEnd;
            layoutParams.width = this.mWidth;
            layoutParams.height = this.mHeight;
            if (Build.VERSION.SDK_INT >= 17) {
                layoutParams.setMarginStart(this.startMargin);
                layoutParams.setMarginEnd(this.endMargin);
            }
            layoutParams.validate();
        }

        public Constraint clone() {
            Constraint constraint = new Constraint();
            constraint.mIsGuideline = this.mIsGuideline;
            constraint.mWidth = this.mWidth;
            constraint.mHeight = this.mHeight;
            constraint.guideBegin = this.guideBegin;
            constraint.guideEnd = this.guideEnd;
            constraint.guidePercent = this.guidePercent;
            constraint.leftToLeft = this.leftToLeft;
            constraint.leftToRight = this.leftToRight;
            constraint.rightToLeft = this.rightToLeft;
            constraint.rightToRight = this.rightToRight;
            constraint.topToTop = this.topToTop;
            constraint.topToBottom = this.topToBottom;
            constraint.bottomToTop = this.bottomToTop;
            constraint.bottomToBottom = this.bottomToBottom;
            constraint.baselineToBaseline = this.baselineToBaseline;
            constraint.startToEnd = this.startToEnd;
            constraint.startToStart = this.startToStart;
            constraint.endToStart = this.endToStart;
            constraint.endToEnd = this.endToEnd;
            constraint.horizontalBias = this.horizontalBias;
            constraint.verticalBias = this.verticalBias;
            constraint.dimensionRatio = this.dimensionRatio;
            constraint.editorAbsoluteX = this.editorAbsoluteX;
            constraint.editorAbsoluteY = this.editorAbsoluteY;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.orientation = this.orientation;
            constraint.leftMargin = this.leftMargin;
            constraint.rightMargin = this.rightMargin;
            constraint.topMargin = this.topMargin;
            constraint.bottomMargin = this.bottomMargin;
            constraint.endMargin = this.endMargin;
            constraint.startMargin = this.startMargin;
            constraint.visibility = this.visibility;
            constraint.goneLeftMargin = this.goneLeftMargin;
            constraint.goneTopMargin = this.goneTopMargin;
            constraint.goneRightMargin = this.goneRightMargin;
            constraint.goneBottomMargin = this.goneBottomMargin;
            constraint.goneEndMargin = this.goneEndMargin;
            constraint.goneStartMargin = this.goneStartMargin;
            constraint.verticalWeight = this.verticalWeight;
            constraint.horizontalWeight = this.horizontalWeight;
            constraint.horizontalChainStyle = this.horizontalChainStyle;
            constraint.verticalChainStyle = this.verticalChainStyle;
            constraint.alpha = this.alpha;
            constraint.applyElevation = this.applyElevation;
            constraint.elevation = this.elevation;
            constraint.rotationX = this.rotationX;
            constraint.rotationY = this.rotationY;
            constraint.scaleX = this.scaleX;
            constraint.scaleY = this.scaleY;
            constraint.transformPivotX = this.transformPivotX;
            constraint.transformPivotY = this.transformPivotY;
            constraint.translationX = this.translationX;
            constraint.translationY = this.translationY;
            constraint.translationZ = this.translationZ;
            constraint.widthDefault = this.widthDefault;
            constraint.heightDefault = this.heightDefault;
            constraint.widthMax = this.widthMax;
            constraint.heightMax = this.heightMax;
            constraint.widthMin = this.widthMin;
            constraint.heightMin = this.heightMin;
            return constraint;
        }
    }

}

