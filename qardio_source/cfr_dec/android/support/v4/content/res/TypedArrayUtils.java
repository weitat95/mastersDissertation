/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;

public class TypedArrayUtils {
    public static boolean getNamedBoolean(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, boolean bl) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return bl;
        }
        return typedArray.getBoolean(n, bl);
    }

    public static int getNamedColor(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, int n2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return n2;
        }
        return typedArray.getColor(n, n2);
    }

    public static float getNamedFloat(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, float f) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return f;
        }
        return typedArray.getFloat(n, f);
    }

    public static int getNamedInt(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, int n2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return n2;
        }
        return typedArray.getInt(n, n2);
    }

    public static int getNamedResourceId(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, int n2) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return n2;
        }
        return typedArray.getResourceId(n, n2);
    }

    public static String getNamedString(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return null;
        }
        return typedArray.getString(n);
    }

    public static boolean hasAttribute(XmlPullParser xmlPullParser, String string2) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", string2) != null;
    }

    public static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, arrn);
        }
        return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }

    public static TypedValue peekNamedValue(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, string2)) {
            return null;
        }
        return typedArray.peekValue(n);
    }
}

