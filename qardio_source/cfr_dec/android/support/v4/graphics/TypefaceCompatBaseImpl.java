/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 */
package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

class TypefaceCompatBaseImpl
implements TypefaceCompat.TypefaceCompatImpl {
    TypefaceCompatBaseImpl() {
    }

    private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, int n) {
        return TypefaceCompatBaseImpl.findBestFont(fontFamilyFilesResourceEntry.getEntries(), n, new StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry>(){

            @Override
            public int getWeight(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.getWeight();
            }

            @Override
            public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
                return fontFileResourceEntry.isItalic();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private static <T> T findBestFont(T[] arrT, int n, StyleExtractor<T> styleExtractor) {
        int n2 = (n & 1) == 0 ? 400 : 700;
        boolean bl = (n & 2) != 0;
        T t = null;
        int n3 = Integer.MAX_VALUE;
        int n4 = arrT.length;
        n = 0;
        do {
            int n5;
            block6: {
                int n6;
                T t2;
                block5: {
                    if (n >= n4) {
                        return t;
                    }
                    t2 = arrT[n];
                    n6 = Math.abs(styleExtractor.getWeight(t2) - n2);
                    n5 = styleExtractor.isItalic(t2) == bl ? 0 : 1;
                    n6 = n6 * 2 + n5;
                    if (t == null) break block5;
                    n5 = n3;
                    if (n3 <= n6) break block6;
                }
                t = t2;
                n5 = n6;
            }
            ++n;
            n3 = n5;
        } while (true);
    }

    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry object, Resources resources, int n) {
        if ((object = this.findBestEntry((FontResourcesParserCompat.FontFamilyFilesResourceEntry)object, n)) == null) {
            return null;
        }
        return TypefaceCompat.createFromResourcesFontFile(context, resources, ((FontResourcesParserCompat.FontFileResourceEntry)object).getResourceId(), ((FontResourcesParserCompat.FontFileResourceEntry)object).getFileName(), n);
    }

    @Override
    public Typeface createFromFontInfo(Context context, CancellationSignal object, FontsContractCompat.FontInfo[] object2, int n) {
        if (((FontsContractCompat.FontInfo[])object2).length < 1) {
            return null;
        }
        Object object3 = this.findBestInfo((FontsContractCompat.FontInfo[])object2, n);
        object2 = null;
        object = null;
        object3 = context.getContentResolver().openInputStream(((FontsContractCompat.FontInfo)object3).getUri());
        object = object3;
        object2 = object3;
        try {
            context = this.createFromInputStream(context, (InputStream)object3);
        }
        catch (IOException iOException) {
            TypefaceCompatUtil.closeQuietly((Closeable)object);
            return null;
        }
        catch (Throwable throwable) {
            TypefaceCompatUtil.closeQuietly((Closeable)object2);
            throw throwable;
        }
        TypefaceCompatUtil.closeQuietly((Closeable)object3);
        return context;
    }

    protected Typeface createFromInputStream(Context object, InputStream inputStream) {
        block6: {
            if ((object = TypefaceCompatUtil.getTempFile((Context)object)) == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, inputStream);
            if (bl) break block6;
            ((File)object).delete();
            return null;
        }
        try {
            inputStream = Typeface.createFromFile((String)((File)object).getPath());
            return inputStream;
        }
        catch (RuntimeException runtimeException) {
            return null;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            ((File)object).delete();
        }
    }

    @Override
    public Typeface createFromResourcesFontFile(Context object, Resources resources, int n, String string2, int n2) {
        block6: {
            if ((object = TypefaceCompatUtil.getTempFile((Context)object)) == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, resources, n);
            if (bl) break block6;
            ((File)object).delete();
            return null;
        }
        try {
            resources = Typeface.createFromFile((String)((File)object).getPath());
            return resources;
        }
        catch (RuntimeException runtimeException) {
            return null;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            ((File)object).delete();
        }
    }

    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        return TypefaceCompatBaseImpl.findBestFont(arrfontInfo, n, new StyleExtractor<FontsContractCompat.FontInfo>(){

            @Override
            public int getWeight(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.getWeight();
            }

            @Override
            public boolean isItalic(FontsContractCompat.FontInfo fontInfo) {
                return fontInfo.isItalic();
            }
        });
    }

    private static interface StyleExtractor<T> {
        public int getWeight(T var1);

        public boolean isItalic(T var1);
    }

}

