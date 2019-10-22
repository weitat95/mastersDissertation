/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ProviderInfo
 *  android.content.pm.Signature
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 *  android.widget.TextView
 */
package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.SelfDestructiveThread;
import android.support.v4.util.LruCache;
import android.support.v4.util.Preconditions;
import android.support.v4.util.SimpleArrayMap;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat {
    private static final SelfDestructiveThread sBackgroundThread;
    private static final Comparator<byte[]> sByteArrayComparator;
    private static final Object sLock;
    private static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>>> sPendingReplies;
    private static final LruCache<String, Typeface> sTypefaceCache;

    static {
        sTypefaceCache = new LruCache(16);
        sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
        sLock = new Object();
        sPendingReplies = new SimpleArrayMap();
        sByteArrayComparator = new Comparator<byte[]>(){

            @Override
            public int compare(byte[] arrby, byte[] arrby2) {
                if (arrby.length != arrby2.length) {
                    return arrby.length - arrby2.length;
                }
                for (int i = 0; i < arrby.length; ++i) {
                    if (arrby[i] == arrby2[i]) continue;
                    return arrby[i] - arrby2[i];
                }
                return 0;
            }
        };
    }

    private static List<byte[]> convertToByteArrayList(Signature[] arrsignature) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        for (int i = 0; i < arrsignature.length; ++i) {
            arrayList.add(arrsignature[i].toByteArray());
        }
        return arrayList;
    }

    private static boolean equalsByteArrayList(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (Arrays.equals(list.get(i), list2.get(i))) continue;
            return false;
        }
        return true;
    }

    public static FontFamilyResult fetchFonts(Context context, CancellationSignal cancellationSignal, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = FontsContractCompat.getProvider(context.getPackageManager(), fontRequest, context.getResources());
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        return new FontFamilyResult(0, FontsContractCompat.getFontFromProvider(context, fontRequest, providerInfo.authority, cancellationSignal));
    }

    private static List<List<byte[]>> getCertificates(FontRequest fontRequest, Resources resources) {
        if (fontRequest.getCertificates() != null) {
            return fontRequest.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, fontRequest.getCertificatesArrayResId());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static FontInfo[] getFontFromProvider(Context var0, FontRequest var1_2, String var2_4, CancellationSignal var3_5) {
        var14_6 = new ArrayList<E>();
        var16_7 = new Uri.Builder().scheme("content").authority(var2_4).build();
        var17_8 = new Uri.Builder().scheme("content").authority(var2_4).appendPath("file").build();
        var15_9 = null;
        var2_4 = var15_9;
        if (Build.VERSION.SDK_INT > 16) {
            var2_4 = var15_9;
            var0 /* !! */  = var0 /* !! */ .getContentResolver();
            var2_4 = var15_9;
            var1_2 = var1_2.getQuery();
            var2_4 = var15_9;
            var0 /* !! */  = var0 /* !! */ .query(var16_7, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{var1_2}, null, (CancellationSignal)var3_5);
        } else {
            var2_4 = var15_9;
            var0 /* !! */  = var0 /* !! */ .getContentResolver();
            var2_4 = var15_9;
            var1_2 = var1_2.getQuery();
            var2_4 = var15_9;
            var0 /* !! */  = var0 /* !! */ .query(var16_7, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{var1_2}, null);
        }
        var1_2 = var14_6;
        if (var0 /* !! */  == null) ** GOTO lbl52
        var1_2 = var14_6;
        var2_4 = var0 /* !! */ ;
        if (var0 /* !! */ .getCount() <= 0) ** GOTO lbl52
        var2_4 = var0 /* !! */ ;
        var7_10 = var0 /* !! */ .getColumnIndex("result_code");
        var2_4 = var0 /* !! */ ;
        var3_5 = new ArrayList<E>();
        try {
            var8_11 = var0 /* !! */ .getColumnIndex("_id");
            var9_12 = var0 /* !! */ .getColumnIndex("file_id");
            var10_13 = var0 /* !! */ .getColumnIndex("font_ttc_index");
            var11_14 = var0 /* !! */ .getColumnIndex("font_weight");
            var12_15 = var0 /* !! */ .getColumnIndex("font_italic");
            while (var0 /* !! */ .moveToNext()) {
                var4_16 = var7_10 != -1 ? var0 /* !! */ .getInt(var7_10) : 0;
                var5_17 = var10_13 != -1 ? var0 /* !! */ .getInt(var10_13) : 0;
                var1_2 = var9_12 == -1 ? ContentUris.withAppendedId((Uri)var16_7, (long)var0 /* !! */ .getLong(var8_11)) : ContentUris.withAppendedId((Uri)var17_8, (long)var0 /* !! */ .getLong(var9_12));
                var6_18 = var11_14 != -1 ? var0 /* !! */ .getInt(var11_14) : 400;
                var13_19 = var12_15 != -1 && var0 /* !! */ .getInt(var12_15) == 1;
                var3_5.add(new FontInfo((Uri)var1_2, var5_17, var6_18, var13_19, var4_16));
            }
            ** GOTO lbl51
        }
        catch (Throwable var1_3) {
            block9: {
                var2_4 = var0 /* !! */ ;
                var0 /* !! */  = var1_3;
                break block9;
lbl51:
                // 1 sources
                var1_2 = var3_5;
lbl52:
                // 3 sources
                if (var0 /* !! */  == null) return var1_2.toArray(new FontInfo[0]);
                var0 /* !! */ .close();
                return var1_2.toArray(new FontInfo[0]);
                catch (Throwable var0_1) {}
            }
            if (var2_4 == null) throw var0 /* !! */ ;
            var2_4.close();
            throw var0 /* !! */ ;
        }
    }

    private static Typeface getFontInternal(Context context, FontRequest fontRequest, int n) {
        block2: {
            FontFamilyResult fontFamilyResult;
            Object var3_4 = null;
            try {
                fontFamilyResult = FontsContractCompat.fetchFonts(context, null, fontRequest);
                fontRequest = var3_4;
                if (fontFamilyResult.getStatusCode() != 0) break block2;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return null;
            }
            fontRequest = TypefaceCompat.createFromFontInfo(context, null, fontFamilyResult.getFonts(), n);
        }
        return fontRequest;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Typeface getFontSync(final Context object, FontRequest object2, TextView object3, int n, int n2, int n3) {
        final String string2 = ((FontRequest)object2).getIdentifier() + "-" + n3;
        Object object4 = sTypefaceCache.get(string2);
        if (object4 != null) {
            return object4;
        }
        if ((n = n == 0 ? 1 : 0) != 0 && n2 == -1) {
            return FontsContractCompat.getFontInternal(object, (FontRequest)object2, n3);
        }
        object = new Callable<Typeface>((FontRequest)object2, n3, string2){
            final /* synthetic */ String val$id;
            final /* synthetic */ FontRequest val$request;
            final /* synthetic */ int val$style;
            {
                this.val$request = fontRequest;
                this.val$style = n;
                this.val$id = string2;
            }

            @Override
            public Typeface call() throws Exception {
                Typeface typeface = FontsContractCompat.getFontInternal(object, this.val$request, this.val$style);
                if (typeface != null) {
                    sTypefaceCache.put(this.val$id, typeface);
                }
                return typeface;
            }
        };
        if (n != 0) {
            try {
                return (Typeface)sBackgroundThread.postAndWait(object, n2);
            }
            catch (InterruptedException interruptedException) {
                return null;
            }
        }
        object3 = new SelfDestructiveThread.ReplyCallback<Typeface>(new WeakReference<TextView>((TextView)object3), object3, n3){
            final /* synthetic */ int val$style;
            final /* synthetic */ TextView val$targetView;
            final /* synthetic */ WeakReference val$textViewWeak;
            {
                this.val$textViewWeak = weakReference;
                this.val$targetView = textView;
                this.val$style = n;
            }

            @Override
            public void onReply(Typeface typeface) {
                if ((TextView)this.val$textViewWeak.get() != null) {
                    this.val$targetView.setTypeface(typeface, this.val$style);
                }
            }
        };
        object2 = sLock;
        synchronized (object2) {
            if (sPendingReplies.containsKey(string2)) {
                sPendingReplies.get(string2).add((SelfDestructiveThread.ReplyCallback<Typeface>)object3);
                return null;
            }
            object4 = new ArrayList();
            ((ArrayList)object4).add(object3);
            sPendingReplies.put(string2, (ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>>)object4);
        }
        sBackgroundThread.postAndReply(object, new SelfDestructiveThread.ReplyCallback<Typeface>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onReply(Typeface typeface) {
                ArrayList arrayList;
                Object object = sLock;
                synchronized (object) {
                    arrayList = (ArrayList)sPendingReplies.get(string2);
                    sPendingReplies.remove(string2);
                }
                int n = 0;
                while (n < arrayList.size()) {
                    ((SelfDestructiveThread.ReplyCallback)arrayList.get(n)).onReply(typeface);
                    ++n;
                }
                return;
            }
        });
        return null;
    }

    public static ProviderInfo getProvider(PackageManager object, FontRequest object2, Resources object3) throws PackageManager.NameNotFoundException {
        String string2 = ((FontRequest)object2).getProviderAuthority();
        ProviderInfo providerInfo = object.resolveContentProvider(string2, 0);
        if (providerInfo == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + string2);
        }
        if (!providerInfo.packageName.equals(((FontRequest)object2).getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + string2 + ", but package was not " + ((FontRequest)object2).getProviderPackage());
        }
        object = FontsContractCompat.convertToByteArrayList(object.getPackageInfo((String)providerInfo.packageName, (int)64).signatures);
        Collections.sort(object, sByteArrayComparator);
        object2 = FontsContractCompat.getCertificates((FontRequest)object2, object3);
        for (int i = 0; i < object2.size(); ++i) {
            object3 = new ArrayList((Collection)object2.get(i));
            Collections.sort(object3, sByteArrayComparator);
            if (!FontsContractCompat.equalsByteArrayList((List<byte[]>)object, (List<byte[]>)object3)) continue;
            return providerInfo;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] arrfontInfo, CancellationSignal cancellationSignal) {
        HashMap<FontInfo, ByteBuffer> hashMap = new HashMap<FontInfo, ByteBuffer>();
        int n = arrfontInfo.length;
        int n2 = 0;
        while (n2 < n) {
            FontInfo fontInfo = arrfontInfo[n2];
            if (fontInfo.getResultCode() == 0 && !hashMap.containsKey(fontInfo = fontInfo.getUri())) {
                hashMap.put(fontInfo, TypefaceCompatUtil.mmap(context, cancellationSignal, (Uri)fontInfo));
            }
            ++n2;
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static class FontFamilyResult {
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        public FontFamilyResult(int n, FontInfo[] arrfontInfo) {
            this.mStatusCode = n;
            this.mFonts = arrfontInfo;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }
    }

    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        public FontInfo(Uri uri, int n, int n2, boolean bl, int n3) {
            this.mUri = Preconditions.checkNotNull(uri);
            this.mTtcIndex = n;
            this.mWeight = n2;
            this.mItalic = bl;
            this.mResultCode = n3;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }
    }

}

