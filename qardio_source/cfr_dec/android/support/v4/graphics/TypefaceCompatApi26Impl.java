/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.fonts.FontVariationAxis
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompatApi21Impl;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TypefaceCompatApi26Impl
extends TypefaceCompatApi21Impl {
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static {
        try {
            var4 = Class.forName("android.graphics.FontFamily");
            var5_1 = var4.getConstructor(new Class[0]);
            var1_2 = var4.getMethod("addFontFromAssetManager", new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
            var2_3 = var4.getMethod("addFontFromBuffer", new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
            var6_4 = var4.getMethod("freeze", new Class[0]);
            var0_5 = var4.getMethod("abortCreation", new Class[0]);
            var3_8 = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(var4, 1).getClass(), Integer.TYPE, Integer.TYPE});
            var3_8.setAccessible(true);
        }
        catch (ClassNotFoundException var0_6) {}
        ** GOTO lbl-1000
        catch (NoSuchMethodException var0_7) {}
lbl-1000:
        // 2 sources
        {
            Log.e((String)"TypefaceCompatApi26Impl", (String)("Unable to collect necessary methods for class " + var0_5.getClass().getName()), (Throwable)var0_5);
            var4 = null;
            var5_1 = null;
            var1_2 = null;
            var2_3 = null;
            var6_4 = null;
            var0_5 = null;
            var3_8 = null;
        }
        TypefaceCompatApi26Impl.sFontFamilyCtor = var5_1;
        TypefaceCompatApi26Impl.sFontFamily = var4;
        TypefaceCompatApi26Impl.sAddFontFromAssetManager = var1_2;
        TypefaceCompatApi26Impl.sAddFontFromBuffer = var2_3;
        TypefaceCompatApi26Impl.sFreeze = var6_4;
        TypefaceCompatApi26Impl.sAbortCreation = var0_5;
        TypefaceCompatApi26Impl.sCreateFromFamiliesWithDefault = var3_8;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean abortCreation(Object object) {
        void var0_2;
        try {
            return (Boolean)sAbortCreation.invoke(object, new Object[0]);
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new RuntimeException((Throwable)var0_2);
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean addFontFromAssetManager(Context context, Object object, String string2, int n, int n2, int n3) {
        void var0_2;
        try {
            return (Boolean)sAddFontFromAssetManager.invoke(object, new Object[]{context.getAssets(), string2, 0, false, n, n2, n3, null});
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new RuntimeException((Throwable)var0_2);
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean addFontFromBuffer(Object object, ByteBuffer byteBuffer, int n, int n2, int n3) {
        void var0_2;
        try {
            return (Boolean)sAddFontFromBuffer.invoke(object, byteBuffer, n, null, n2, n3);
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new RuntimeException((Throwable)var0_2);
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Typeface createFromFamiliesWithDefault(Object object) {
        void var0_2;
        try {
            Object object2 = Array.newInstance(sFontFamily, 1);
            Array.set(object2, 0, object);
            return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, object2, -1, -1);
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new RuntimeException((Throwable)var0_2);
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean freeze(Object object) {
        void var0_2;
        try {
            return (Boolean)sFreeze.invoke(object, new Object[0]);
        }
        catch (IllegalAccessException illegalAccessException) {
            do {
                throw new RuntimeException((Throwable)var0_2);
                break;
            } while (true);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w((String)"TypefaceCompatApi26Impl", (String)"Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object newFamily() {
        void var0_2;
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        }
        catch (InstantiationException instantiationException) {
            do {
                throw new RuntimeException((Throwable)var0_2);
                break;
            } while (true);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException((Throwable)var0_2);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry arrfontFileResourceEntry, Resources object, int n) {
        if (!TypefaceCompatApi26Impl.isFontFamilyPrivateAPIAvailable()) {
            int n2;
            return super.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry)arrfontFileResourceEntry, (Resources)object, n2);
        }
        Object object2 = TypefaceCompatApi26Impl.newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : arrfontFileResourceEntry.getEntries()) {
            int n3;
            int n4;
            String string2 = fontFileResourceEntry.getFileName();
            if (TypefaceCompatApi26Impl.addFontFromAssetManager(context, object2, string2, 0, n3 = fontFileResourceEntry.getWeight(), n4 = fontFileResourceEntry.isItalic() ? 1 : 0)) continue;
            TypefaceCompatApi26Impl.abortCreation(object2);
            return null;
        }
        if (!TypefaceCompatApi26Impl.freeze(object2)) {
            return null;
        }
        return TypefaceCompatApi26Impl.createFromFamiliesWithDefault(object2);
    }

    /*
     * Exception decompiling
     */
    @Override
    public Typeface createFromFontInfo(Context var1_1, CancellationSignal var2_5, FontsContractCompat.FontInfo[] var3_9, int var4_11) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    @Override
    public Typeface createFromResourcesFontFile(Context context, Resources object, int n, String string2, int n2) {
        if (!TypefaceCompatApi26Impl.isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, (Resources)object, n, string2, n2);
        }
        object = TypefaceCompatApi26Impl.newFamily();
        if (!TypefaceCompatApi26Impl.addFontFromAssetManager(context, object, string2, 0, -1, -1)) {
            TypefaceCompatApi26Impl.abortCreation(object);
            return null;
        }
        if (!TypefaceCompatApi26Impl.freeze(object)) {
            return null;
        }
        return TypefaceCompatApi26Impl.createFromFamiliesWithDefault(object);
    }
}

