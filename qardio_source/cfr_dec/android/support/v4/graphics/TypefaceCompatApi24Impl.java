/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompatBaseImpl;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class TypefaceCompatApi24Impl
extends TypefaceCompatBaseImpl {
    private static final Method sAddFontWeightStyle;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static {
        try {
            var2 = Class.forName("android.graphics.FontFamily");
            var3_1 = var2.getConstructor(new Class[0]);
            var0_2 = var2.getMethod("addFontWeightStyle", new Class[]{ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE});
            var1_5 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(var2, 1).getClass()});
        }
        catch (ClassNotFoundException var0_3) {}
        ** GOTO lbl-1000
        catch (NoSuchMethodException var0_4) {}
lbl-1000:
        // 2 sources
        {
            Log.e((String)"TypefaceCompatApi24Impl", (String)var0_2.getClass().getName(), (Throwable)var0_2);
            var2 = null;
            var3_1 = null;
            var0_2 = null;
            var1_5 = null;
        }
        TypefaceCompatApi24Impl.sFontFamilyCtor = var3_1;
        TypefaceCompatApi24Impl.sFontFamily = var2;
        TypefaceCompatApi24Impl.sAddFontWeightStyle = var0_2;
        TypefaceCompatApi24Impl.sCreateFromFamiliesWithDefault = var1_5;
    }

    TypefaceCompatApi24Impl() {
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean addFontWeightStyle(Object object, ByteBuffer byteBuffer, int n, int n2, boolean bl) {
        void var0_2;
        try {
            return (Boolean)sAddFontWeightStyle.invoke(object, byteBuffer, n, null, n2, bl);
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
            return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, object2);
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

    public static boolean isUsable() {
        if (sAddFontWeightStyle == null) {
            Log.w((String)"TypefaceCompatApi24Impl", (String)"Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontWeightStyle != null;
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

    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry arrfontFileResourceEntry, Resources resources, int n) {
        Object object = TypefaceCompatApi24Impl.newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : arrfontFileResourceEntry.getEntries()) {
            if (TypefaceCompatApi24Impl.addFontWeightStyle(object, TypefaceCompatUtil.copyToDirectBuffer(context, resources, fontFileResourceEntry.getResourceId()), 0, fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic())) continue;
            return null;
        }
        return TypefaceCompatApi24Impl.createFromFamiliesWithDefault(object);
    }

    @Override
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        Object object = TypefaceCompatApi24Impl.newFamily();
        SimpleArrayMap<Uri, ByteBuffer> simpleArrayMap = new SimpleArrayMap<Uri, ByteBuffer>();
        for (FontsContractCompat.FontInfo fontInfo : arrfontInfo) {
            ByteBuffer byteBuffer;
            Uri uri = fontInfo.getUri();
            ByteBuffer byteBuffer2 = byteBuffer = (ByteBuffer)simpleArrayMap.get((Object)uri);
            if (byteBuffer == null) {
                byteBuffer2 = TypefaceCompatUtil.mmap(context, cancellationSignal, uri);
                simpleArrayMap.put(uri, byteBuffer2);
            }
            if (TypefaceCompatApi24Impl.addFontWeightStyle(object, byteBuffer2, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic())) continue;
            return null;
        }
        return TypefaceCompatApi24Impl.createFromFamiliesWithDefault(object);
    }
}

