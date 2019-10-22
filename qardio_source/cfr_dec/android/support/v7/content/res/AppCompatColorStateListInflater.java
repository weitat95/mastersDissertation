/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.util.StateSet
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.GrowingArrayUtils;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

final class AppCompatColorStateListInflater {
    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return AppCompatColorStateListInflater.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
    }

    private static ColorStateList createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        String string2 = xmlPullParser.getName();
        if (!string2.equals("selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + string2);
        }
        return AppCompatColorStateListInflater.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ColorStateList inflate(Resources arrn, XmlPullParser arrarrn, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n = arrarrn.getDepth() + 1;
        int[][] arrarrn2 = new int[20][];
        int[] arrn2 = new int[arrarrn2.length];
        int n2 = 0;
        do {
            int n3;
            int n4;
            int n5;
            int[] arrn3;
            float f;
            int n6;
            if ((n6 = arrarrn.next()) != 1 && ((n4 = arrarrn.getDepth()) >= n || n6 != 3)) {
                if (n6 != 2 || n4 > n || !arrarrn.getName().equals("item")) continue;
                arrn3 = AppCompatColorStateListInflater.obtainAttributes((Resources)arrn, theme, attributeSet, R.styleable.ColorStateListItem);
                n5 = arrn3.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                f = 1.0f;
                if (arrn3.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                    f = arrn3.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f);
                } else if (arrn3.hasValue(R.styleable.ColorStateListItem_alpha)) {
                    f = arrn3.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f);
                }
                arrn3.recycle();
                n3 = attributeSet.getAttributeCount();
                arrn3 = new int[n3];
                n6 = 0;
            } else {
                arrn = new int[n2];
                arrarrn = new int[n2][];
                System.arraycopy(arrn2, 0, arrn, 0, n2);
                System.arraycopy(arrarrn2, 0, arrarrn, 0, n2);
                return new ColorStateList((int[][])arrarrn, arrn);
            }
            for (n4 = 0; n4 < n3; ++n4) {
                int n7 = attributeSet.getAttributeNameResource(n4);
                if (n7 == 16843173 || n7 == 16843551 || n7 == R.attr.alpha) continue;
                int n8 = n6 + 1;
                if (!attributeSet.getAttributeBooleanValue(n4, false)) {
                    n7 = -n7;
                }
                arrn3[n6] = n7;
                n6 = n8;
            }
            arrn3 = StateSet.trimStateSet((int[])arrn3, (int)n6);
            n6 = AppCompatColorStateListInflater.modulateColorAlpha(n5, f);
            if (n2 == 0 || arrn3.length == 0) {
                // empty if block
            }
            arrn2 = GrowingArrayUtils.append(arrn2, n2, n6);
            arrarrn2 = GrowingArrayUtils.append(arrarrn2, n2, arrn3);
            ++n2;
        } while (true);
    }

    private static int modulateColorAlpha(int n, float f) {
        return ColorUtils.setAlphaComponent(n, Math.round((float)Color.alpha((int)n) * f));
    }

    private static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, arrn);
        }
        return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }
}

