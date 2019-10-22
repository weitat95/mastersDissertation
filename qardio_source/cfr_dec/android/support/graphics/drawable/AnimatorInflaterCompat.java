/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorSet
 *  android.animation.Keyframe
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TimeInterpolator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.util.Xml
 *  android.view.InflateException
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.graphics.drawable.AndroidResources;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.graphics.drawable.ArgbEvaluator;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflaterCompat {
    private static Animator createAnimatorFromXml(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, float f) throws XmlPullParserException, IOException {
        return AnimatorInflaterCompat.createAnimatorFromXml(context, resources, theme, xmlPullParser, Xml.asAttributeSet((XmlPullParser)xmlPullParser), null, 0, f);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Animator createAnimatorFromXml(Context arranimator, Resources object, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int n, float f) throws XmlPullParserException, IOException {
        int n2;
        PropertyValuesHolder[] arrpropertyValuesHolder = null;
        ArrayList<PropertyValuesHolder[]> arrayList = null;
        int n3 = xmlPullParser.getDepth();
        while (((n2 = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n3) && n2 != 1) {
            if (n2 != 2) continue;
            PropertyValuesHolder[] arrpropertyValuesHolder2 = xmlPullParser.getName();
            n2 = 0;
            if (arrpropertyValuesHolder2.equals("objectAnimator")) {
                arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadObjectAnimator((Context)arranimator, (Resources)object, theme, attributeSet, f, xmlPullParser);
            } else if (arrpropertyValuesHolder2.equals("animator")) {
                arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadAnimator((Context)arranimator, (Resources)object, theme, attributeSet, null, f, xmlPullParser);
            } else if (arrpropertyValuesHolder2.equals("set")) {
                arrpropertyValuesHolder2 = new AnimatorSet();
                arrpropertyValuesHolder = TypedArrayUtils.obtainAttributes((Resources)object, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR_SET);
                int n4 = TypedArrayUtils.getNamedInt((TypedArray)arrpropertyValuesHolder, xmlPullParser, "ordering", 0, 0);
                AnimatorInflaterCompat.createAnimatorFromXml((Context)arranimator, (Resources)object, theme, xmlPullParser, attributeSet, (AnimatorSet)arrpropertyValuesHolder2, n4, f);
                arrpropertyValuesHolder.recycle();
            } else {
                if (!arrpropertyValuesHolder2.equals("propertyValuesHolder")) {
                    throw new RuntimeException("Unknown animator name: " + xmlPullParser.getName());
                }
                arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadValues((Context)arranimator, (Resources)object, theme, xmlPullParser, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
                if (arrpropertyValuesHolder2 != null && arrpropertyValuesHolder != null && arrpropertyValuesHolder instanceof ValueAnimator) {
                    ((ValueAnimator)arrpropertyValuesHolder).setValues(arrpropertyValuesHolder2);
                }
                n2 = 1;
                arrpropertyValuesHolder2 = arrpropertyValuesHolder;
            }
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            if (animatorSet == null) continue;
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            if (n2 != 0) continue;
            ArrayList<PropertyValuesHolder[]> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<PropertyValuesHolder[]>();
            }
            arrayList2.add(arrpropertyValuesHolder2);
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            arrayList = arrayList2;
        }
        if (animatorSet != null && arrayList != null) {
            arranimator = new Animator[arrayList.size()];
            n2 = 0;
            for (Animator arranimator[n2] : arrayList) {
                ++n2;
            }
            if (n != 0) {
                animatorSet.playSequentially(arranimator);
                return arrpropertyValuesHolder;
            }
            animatorSet.playTogether(arranimator);
        }
        return arrpropertyValuesHolder;
    }

    private static Keyframe createNewKeyframe(Keyframe keyframe, float f) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat((float)f);
        }
        if (keyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt((float)f);
        }
        return Keyframe.ofObject((float)f);
    }

    private static void distributeKeyframes(Keyframe[] arrkeyframe, float f, int n, int n2) {
        f /= (float)(n2 - n + 2);
        while (n <= n2) {
            arrkeyframe[n].setFraction(arrkeyframe[n - 1].getFraction() + f);
            ++n;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static PropertyValuesHolder getPVH(TypedArray object, int n, int n2, int n3, String string2) {
        int n4;
        int n5;
        void var12_17;
        void var3_11;
        void var0_5;
        void var4_12;
        TypedValue typedValue = object.peekValue(n4);
        boolean bl = typedValue != null;
        int n6 = bl ? typedValue.type : 0;
        TypedValue typedValue2 = object.peekValue((int)var3_11);
        boolean bl2 = typedValue2 != null;
        int n7 = bl2 ? typedValue2.type : 0;
        int n8 = n5;
        if (n5 == 4) {
            n8 = bl && AnimatorInflaterCompat.isColorType(n6) || bl2 && AnimatorInflaterCompat.isColorType(n7) ? 3 : 0;
        }
        n5 = n8 == 0 ? 1 : 0;
        String string3 = null;
        Object var12_15 = null;
        if (n8 == 2) {
            String string4 = object.getString(n4);
            string3 = object.getString((int)var3_11);
            PathParser.PathDataNode[] arrpathDataNode = PathParser.createNodesFromPathData(string4);
            PathParser.PathDataNode[] arrpathDataNode2 = PathParser.createNodesFromPathData(string3);
            if (arrpathDataNode == null) {
                Object var0_1 = var12_15;
                if (arrpathDataNode2 == null) return var0_5;
            }
            if (arrpathDataNode != null) {
                PathDataEvaluator pathDataEvaluator = new PathDataEvaluator();
                if (arrpathDataNode2 == null) {
                    return PropertyValuesHolder.ofObject((String)var4_12, (TypeEvaluator)pathDataEvaluator, (Object[])new Object[]{arrpathDataNode});
                }
                if (!PathParser.canMorph(arrpathDataNode, arrpathDataNode2)) {
                    throw new InflateException(" Can't morph from " + string4 + " to " + string3);
                }
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject((String)var4_12, (TypeEvaluator)pathDataEvaluator, (Object[])new Object[]{arrpathDataNode, arrpathDataNode2});
                return var0_5;
            } else {
                Object var0_6 = var12_15;
                if (arrpathDataNode2 == null) return var0_5;
                return PropertyValuesHolder.ofObject((String)var4_12, (TypeEvaluator)new PathDataEvaluator(), (Object[])new Object[]{arrpathDataNode2});
            }
        }
        ArgbEvaluator argbEvaluator = null;
        if (n8 == 3) {
            argbEvaluator = ArgbEvaluator.getInstance();
        }
        if (n5 != 0) {
            if (bl) {
                float f = n6 == 5 ? object.getDimension(n4, 0.0f) : object.getFloat(n4, 0.0f);
                if (bl2) {
                    float f2 = n7 == 5 ? object.getDimension((int)var3_11, 0.0f) : object.getFloat((int)var3_11, 0.0f);
                    PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat((String)var4_12, (float[])new float[]{f, f2});
                } else {
                    PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat((String)var4_12, (float[])new float[]{f});
                }
            } else {
                float f = n7 == 5 ? object.getDimension((int)var3_11, 0.0f) : object.getFloat((int)var3_11, 0.0f);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat((String)var4_12, (float[])new float[]{f});
            }
        } else if (bl) {
            n5 = n6 == 5 ? (int)object.getDimension(n4, 0.0f) : (AnimatorInflaterCompat.isColorType(n6) ? object.getColor(n4, 0) : object.getInt(n4, 0));
            if (bl2) {
                n4 = n7 == 5 ? (int)object.getDimension((int)var3_11, 0.0f) : (AnimatorInflaterCompat.isColorType(n7) ? object.getColor((int)var3_11, 0) : object.getInt((int)var3_11, 0));
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofInt((String)var4_12, (int[])new int[]{n5, n4});
            } else {
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofInt((String)var4_12, (int[])new int[]{n5});
            }
        } else {
            String string5 = string3;
            if (bl2) {
                n5 = n7 == 5 ? (int)object.getDimension((int)var3_11, 0.0f) : (AnimatorInflaterCompat.isColorType(n7) ? object.getColor((int)var3_11, 0) : object.getInt((int)var3_11, 0));
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofInt((String)var4_12, (int[])new int[]{n5});
            }
        }
        void var0_7 = var12_17;
        if (var12_17 == null) return var0_5;
        {
            void var0_8 = var12_17;
            if (argbEvaluator == null) return var0_5;
            {
                var12_17.setEvaluator((TypeEvaluator)argbEvaluator);
                return var12_17;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int inferValueTypeFromValues(TypedArray typedArray, int n, int n2) {
        int n3 = 1;
        TypedValue typedValue = typedArray.peekValue(n);
        n = typedValue != null ? 1 : 0;
        int n4 = n != 0 ? typedValue.type : 0;
        n2 = (typedArray = typedArray.peekValue(n2)) != null ? n3 : 0;
        n3 = n2 != 0 ? typedArray.type : 0;
        if (n != 0 && AnimatorInflaterCompat.isColorType(n4) || n2 != 0 && AnimatorInflaterCompat.isColorType(n3)) {
            return 3;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int inferValueTypeOfKeyframe(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        int n = 0;
        resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_KEYFRAME);
        theme = TypedArrayUtils.peekNamedValue((TypedArray)resources, xmlPullParser, "value", 0);
        if (theme != null) {
            n = 1;
        }
        n = n != 0 && AnimatorInflaterCompat.isColorType(theme.type) ? 3 : 0;
        resources.recycle();
        return n;
    }

    private static boolean isColorType(int n) {
        return n >= 28 && n <= 31;
    }

    public static Animator loadAnimator(Context context, int n) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 24) {
            return AnimatorInflater.loadAnimator((Context)context, (int)n);
        }
        return AnimatorInflaterCompat.loadAnimator(context, context.getResources(), context.getTheme(), n);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, int n) throws Resources.NotFoundException {
        return AnimatorInflaterCompat.loadAnimator(context, resources, theme, n, 1.0f);
    }

    /*
     * Exception decompiling
     */
    public static Animator loadAnimator(Context var0, Resources var1_4, Resources.Theme var2_5, int var3_6, float var4_7) throws Resources.NotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
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

    private static ValueAnimator loadAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR);
        theme = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        resources = valueAnimator;
        if (valueAnimator == null) {
            resources = new ValueAnimator();
        }
        AnimatorInflaterCompat.parseAnimatorFromTypeArray((ValueAnimator)resources, typedArray, (TypedArray)theme, f, xmlPullParser);
        int n = TypedArrayUtils.getNamedResourceId(typedArray, xmlPullParser, "interpolator", 0, 0);
        if (n > 0) {
            resources.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(context, n));
        }
        typedArray.recycle();
        if (theme != null) {
            theme.recycle();
        }
        return resources;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Keyframe loadKeyframe(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, int n, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        attributeSet = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_KEYFRAME);
        theme = null;
        float f = TypedArrayUtils.getNamedFloat((TypedArray)attributeSet, xmlPullParser, "fraction", 3, -1.0f);
        resources = TypedArrayUtils.peekNamedValue((TypedArray)attributeSet, xmlPullParser, "value", 0);
        boolean bl = resources != null;
        int n2 = n;
        if (n == 4) {
            n2 = bl && AnimatorInflaterCompat.isColorType(resources.type) ? 3 : 0;
        }
        if (bl) {
            resources = theme;
            switch (n2) {
                default: {
                    resources = theme;
                    break;
                }
                case 0: {
                    resources = Keyframe.ofFloat((float)f, (float)TypedArrayUtils.getNamedFloat((TypedArray)attributeSet, xmlPullParser, "value", 0, 0.0f));
                }
                case 2: {
                    break;
                }
                case 1: 
                case 3: {
                    resources = Keyframe.ofInt((float)f, (int)TypedArrayUtils.getNamedInt((TypedArray)attributeSet, xmlPullParser, "value", 0, 0));
                    break;
                }
            }
        } else {
            resources = n2 == 0 ? Keyframe.ofFloat((float)f) : Keyframe.ofInt((float)f);
        }
        if ((n = TypedArrayUtils.getNamedResourceId((TypedArray)attributeSet, xmlPullParser, "interpolator", 1, 0)) > 0) {
            resources.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(context, n));
        }
        attributeSet.recycle();
        return resources;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        AnimatorInflaterCompat.loadAnimator(context, resources, theme, attributeSet, (ValueAnimator)objectAnimator, f, xmlPullParser);
        return objectAnimator;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static PropertyValuesHolder loadPvh(Context var0, Resources var1_1, Resources.Theme var2_2, XmlPullParser var3_3, String var4_4, int var5_5) throws XmlPullParserException, IOException {
        var13_6 = null;
        var11_7 = null;
        var8_8 = var5_5;
        while ((var5_5 = var3_3.next()) != 3 && var5_5 != 1) {
            if (!var3_3.getName().equals("keyframe")) continue;
            var5_5 = var8_8;
            if (var8_8 == 4) {
                var5_5 = AnimatorInflaterCompat.inferValueTypeOfKeyframe((Resources)var1_1, var2_2, Xml.asAttributeSet((XmlPullParser)var3_3), var3_3);
            }
            var14_10 = AnimatorInflaterCompat.loadKeyframe((Context)var0, (Resources)var1_1, var2_2, Xml.asAttributeSet((XmlPullParser)var3_3), var5_5, var3_3);
            var12_9 = var11_7;
            if (var14_10 != null) {
                var12_9 = var11_7;
                if (var11_7 == null) {
                    var12_9 = new ArrayList<Keyframe>();
                }
                var12_9.add(var14_10);
            }
            var3_3.next();
            var11_7 = var12_9;
            var8_8 = var5_5;
        }
        var0 = var13_6;
        if (var11_7 == null) return var0;
        var7_11 = var11_7.size();
        var0 = var13_6;
        if (var7_11 <= 0) return var0;
        var0 = (Keyframe)var11_7.get(0);
        var1_1 = (Keyframe)var11_7.get(var7_11 - 1);
        var6_12 = var1_1.getFraction();
        var5_5 = var7_11;
        if (var6_12 < 1.0f) {
            if (var6_12 < 0.0f) {
                var1_1.setFraction(1.0f);
                var5_5 = var7_11;
            } else {
                var11_7.add(var11_7.size(), AnimatorInflaterCompat.createNewKeyframe(var1_1, 1.0f));
                var5_5 = var7_11 + 1;
            }
        }
        var6_12 = var0.getFraction();
        var9_13 = var5_5;
        if (var6_12 != 0.0f) {
            if (var6_12 < 0.0f) {
                var0.setFraction(0.0f);
                var9_13 = var5_5;
            } else {
                var11_7.add(0, AnimatorInflaterCompat.createNewKeyframe(var0, 0.0f));
                var9_13 = var5_5 + 1;
            }
        }
        var0 = new Keyframe[var9_13];
        var11_7.toArray((T[])var0);
        var5_5 = 0;
        block1: do {
            if (var5_5 >= var9_13) {
                var0 = var1_1 = PropertyValuesHolder.ofKeyframe((String)var4_4, (Keyframe[])var0);
                if (var8_8 != 3) return var0;
                var1_1.setEvaluator((TypeEvaluator)ArgbEvaluator.getInstance());
                return var1_1;
            }
            var1_1 = var0[var5_5];
            if (var1_1.getFraction() < 0.0f) {
                if (var5_5 == 0) {
                    var1_1.setFraction(0.0f);
                } else {
                    if (var5_5 != var9_13 - 1) break;
                    var1_1.setFraction(1.0f);
                }
            }
lbl64:
            // 5 sources
            do {
                ++var5_5;
                continue block1;
                break;
            } while (true);
            break;
        } while (true);
        var10_14 = var5_5;
        var7_11 = var5_5 + 1;
        do {
            if (var7_11 >= var9_13 - 1 || var0[var7_11].getFraction() >= 0.0f) {
                AnimatorInflaterCompat.distributeKeyframes((Keyframe[])var0, var0[var10_14 + 1].getFraction() - var0[var5_5 - 1].getFraction(), var5_5, var10_14);
                ** continue;
            }
            var10_14 = var7_11++;
        } while (true);
    }

    private static PropertyValuesHolder[] loadValues(Context arrpropertyValuesHolder, Resources arrpropertyValuesHolder2, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        ArrayList arrayList = null;
        while ((n = xmlPullParser.getEventType()) != 3 && n != 1) {
            if (n != 2) {
                xmlPullParser.next();
                continue;
            }
            ArrayList arrayList2 = arrayList;
            if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                TypedArray typedArray = TypedArrayUtils.obtainAttributes((Resources)arrpropertyValuesHolder2, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                String string2 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyName", 3);
                n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 2, 4);
                ArrayList arrayList3 = arrayList2 = AnimatorInflaterCompat.loadPvh((Context)arrpropertyValuesHolder, (Resources)arrpropertyValuesHolder2, theme, xmlPullParser, string2, n);
                if (arrayList2 == null) {
                    arrayList3 = AnimatorInflaterCompat.getPVH(typedArray, n, 0, 1, string2);
                }
                arrayList2 = arrayList;
                if (arrayList3 != null) {
                    arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(arrayList3);
                }
                typedArray.recycle();
            }
            xmlPullParser.next();
            arrayList = arrayList2;
        }
        arrpropertyValuesHolder = null;
        if (arrayList != null) {
            int n2 = arrayList.size();
            arrpropertyValuesHolder2 = new PropertyValuesHolder[n2];
            n = 0;
            do {
                arrpropertyValuesHolder = arrpropertyValuesHolder2;
                if (n >= n2) break;
                arrpropertyValuesHolder2[n] = (PropertyValuesHolder)arrayList.get(n);
                ++n;
            } while (true);
        }
        return arrpropertyValuesHolder;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, float f, XmlPullParser xmlPullParser) {
        int n;
        long l = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "duration", 1, 300);
        long l2 = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "startOffset", 2, 0);
        int n2 = n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueFrom")) {
            n2 = n;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueTo")) {
                int n3 = n;
                if (n == 4) {
                    n3 = AnimatorInflaterCompat.inferValueTypeFromValues(typedArray, 5, 6);
                }
                PropertyValuesHolder propertyValuesHolder = AnimatorInflaterCompat.getPVH(typedArray, n3, 5, 6, "");
                n2 = n3;
                if (propertyValuesHolder != null) {
                    valueAnimator.setValues(new PropertyValuesHolder[]{propertyValuesHolder});
                    n2 = n3;
                }
            }
        }
        valueAnimator.setDuration(l);
        valueAnimator.setStartDelay(l2);
        valueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatCount", 3, 0));
        valueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatMode", 4, 1));
        if (typedArray2 != null) {
            AnimatorInflaterCompat.setupObjectAnimator(valueAnimator, typedArray2, n2, f, xmlPullParser);
        }
    }

    private static void setupObjectAnimator(ValueAnimator valueAnimator, TypedArray typedArray, int n, float f, XmlPullParser object) {
        valueAnimator = (ObjectAnimator)valueAnimator;
        String string2 = TypedArrayUtils.getNamedString(typedArray, object, "pathData", 1);
        if (string2 != null) {
            String string3 = TypedArrayUtils.getNamedString(typedArray, object, "propertyXName", 2);
            object = TypedArrayUtils.getNamedString(typedArray, object, "propertyYName", 3);
            if (n == 2 || n == 4) {
                // empty if block
            }
            if (string3 == null && object == null) {
                throw new InflateException(typedArray.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            AnimatorInflaterCompat.setupPathMotion(PathParser.createPathFromPathData(string2), (ObjectAnimator)valueAnimator, 0.5f * f, string3, (String)object);
            return;
        }
        valueAnimator.setPropertyName(TypedArrayUtils.getNamedString(typedArray, object, "propertyName", 0));
    }

    private static void setupPathMotion(Path path, ObjectAnimator objectAnimator, float f, String object, String string2) {
        float f2;
        float[] arrf = new PathMeasure(path, false);
        float f3 = 0.0f;
        ArrayList<Float> arrayList = new ArrayList<Float>();
        arrayList.add(Float.valueOf(0.0f));
        do {
            f2 = f3 + arrf.getLength();
            arrayList.add(Float.valueOf(f2));
            f3 = f2;
        } while (arrf.nextContour());
        path = new PathMeasure(path, false);
        int n = Math.min(100, (int)(f2 / f) + 1);
        float[] arrf2 = new float[n];
        arrf = new float[n];
        float[] arrf3 = new float[2];
        int n2 = 0;
        f2 /= (float)(n - 1);
        f = 0.0f;
        for (int i = 0; i < n; ++i) {
            path.getPosTan(f, arrf3, null);
            path.getPosTan(f, arrf3, null);
            arrf2[i] = arrf3[0];
            arrf[i] = arrf3[1];
            f3 = f + f2;
            int n3 = n2;
            f = f3;
            if (n2 + 1 < arrayList.size()) {
                n3 = n2;
                f = f3;
                if (f3 > ((Float)arrayList.get(n2 + 1)).floatValue()) {
                    f = f3 - ((Float)arrayList.get(n2 + 1)).floatValue();
                    n3 = n2 + 1;
                    path.nextContour();
                }
            }
            n2 = n3;
        }
        path = null;
        arrayList = null;
        if (object != null) {
            path = PropertyValuesHolder.ofFloat((String)object, (float[])arrf2);
        }
        object = arrayList;
        if (string2 != null) {
            object = PropertyValuesHolder.ofFloat((String)string2, (float[])arrf);
        }
        if (path == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{object});
            return;
        }
        if (object == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{path});
            return;
        }
        objectAnimator.setValues(new PropertyValuesHolder[]{path, object});
    }

    private static class PathDataEvaluator
    implements TypeEvaluator<PathParser.PathDataNode[]> {
        private PathParser.PathDataNode[] mNodeArray;

        private PathDataEvaluator() {
        }

        public PathParser.PathDataNode[] evaluate(float f, PathParser.PathDataNode[] arrpathDataNode, PathParser.PathDataNode[] arrpathDataNode2) {
            if (!PathParser.canMorph(arrpathDataNode, arrpathDataNode2)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            if (this.mNodeArray == null || !PathParser.canMorph(this.mNodeArray, arrpathDataNode)) {
                this.mNodeArray = PathParser.deepCopyNodes(arrpathDataNode);
            }
            for (int i = 0; i < arrpathDataNode.length; ++i) {
                this.mNodeArray[i].interpolatePathDataNode(arrpathDataNode[i], arrpathDataNode2[i], f);
            }
            return this.mNodeArray;
        }
    }

}

