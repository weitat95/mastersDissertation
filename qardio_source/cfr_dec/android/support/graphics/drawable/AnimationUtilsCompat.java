/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.util.AttributeSet
 *  android.util.Xml
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.AnticipateOvershootInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.CycleInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.OvershootInterpolator
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimationUtilsCompat {
    private static Interpolator createInterpolatorFromXml(Context context, Resources object, Resources.Theme object2, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        object = null;
        int n2 = xmlPullParser.getDepth();
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            object = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
            object2 = xmlPullParser.getName();
            if (((String)object2).equals("linearInterpolator")) {
                object = new LinearInterpolator();
                continue;
            }
            if (((String)object2).equals("accelerateInterpolator")) {
                object = new AccelerateInterpolator(context, (AttributeSet)object);
                continue;
            }
            if (((String)object2).equals("decelerateInterpolator")) {
                object = new DecelerateInterpolator(context, (AttributeSet)object);
                continue;
            }
            if (((String)object2).equals("accelerateDecelerateInterpolator")) {
                object = new AccelerateDecelerateInterpolator();
                continue;
            }
            if (((String)object2).equals("cycleInterpolator")) {
                object = new CycleInterpolator(context, (AttributeSet)object);
                continue;
            }
            if (((String)object2).equals("anticipateInterpolator")) {
                object = new AnticipateInterpolator(context, (AttributeSet)object);
                continue;
            }
            if (((String)object2).equals("overshootInterpolator")) {
                object = new OvershootInterpolator(context, (AttributeSet)object);
                continue;
            }
            if (((String)object2).equals("anticipateOvershootInterpolator")) {
                object = new AnticipateOvershootInterpolator(context, (AttributeSet)object);
                continue;
            }
            if (((String)object2).equals("bounceInterpolator")) {
                object = new BounceInterpolator();
                continue;
            }
            if (((String)object2).equals("pathInterpolator")) {
                object = new PathInterpolatorCompat(context, (AttributeSet)object, xmlPullParser);
                continue;
            }
            throw new RuntimeException("Unknown interpolator name: " + xmlPullParser.getName());
        }
        return object;
    }

    /*
     * Exception decompiling
     */
    public static Interpolator loadInterpolator(Context var0, int var1_3) throws Resources.NotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CATCHBLOCK]], but top level block is 8[CATCHBLOCK]
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
}

