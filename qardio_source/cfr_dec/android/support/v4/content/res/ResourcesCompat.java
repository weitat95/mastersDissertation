/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.TypedValue
 *  android.widget.TextView
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.content.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat {
    public static Drawable getDrawable(Resources resources, int n, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(n, theme);
        }
        return resources.getDrawable(n);
    }

    public static Typeface getFont(Context context, int n, TypedValue typedValue, int n2, TextView textView) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return ResourcesCompat.loadFont(context, n, typedValue, n2, textView);
    }

    private static Typeface loadFont(Context context, int n, TypedValue typedValue, int n2, TextView textView) {
        Resources resources = context.getResources();
        resources.getValue(n, typedValue, true);
        context = ResourcesCompat.loadFont(context, resources, typedValue, n, n2, textView);
        if (context != null) {
            return context;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(n));
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Typeface loadFont(Context context, Resources resources, TypedValue object, int n, int n2, TextView textView) {
        String string2;
        void var3_9;
        FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry;
        void var4_10;
        block8: {
            Typeface typeface;
            void var2_6;
            if (object.string == null) {
                throw new Resources.NotFoundException("Resource \"" + resources.getResourceName((int)var3_9) + "\" (" + Integer.toHexString((int)var3_9) + ") is not a Font: " + (Object)object);
            }
            string2 = object.string.toString();
            if (!string2.startsWith("res/")) {
                return var2_6;
            }
            Typeface typeface2 = typeface = TypefaceCompat.findFromCache(resources, (int)var3_9, (int)var4_10);
            if (typeface != null) return var2_6;
            if (!string2.toLowerCase().endsWith(".xml")) return TypefaceCompat.createFromResourcesFontFile(context, resources, (int)var3_9, string2, (int)var4_10);
            familyResourceEntry = FontResourcesParserCompat.parse((XmlPullParser)resources.getXml((int)var3_9), resources);
            if (familyResourceEntry != null) break block8;
            Log.e((String)"ResourcesCompat", (String)"Failed to find font-family tag");
            return null;
        }
        try {
            void var5_11;
            return TypefaceCompat.createFromResourcesFamilyXml(context, familyResourceEntry, resources, (int)var3_9, (int)var4_10, (TextView)var5_11);
        }
        catch (XmlPullParserException xmlPullParserException) {
            Log.e((String)"ResourcesCompat", (String)("Failed to parse xml resource " + string2), (Throwable)xmlPullParserException);
            do {
                return null;
                break;
            } while (true);
        }
        catch (IOException iOException) {
            Log.e((String)"ResourcesCompat", (String)("Failed to read xml resource " + string2), (Throwable)iOException);
            return null;
        }
    }
}

