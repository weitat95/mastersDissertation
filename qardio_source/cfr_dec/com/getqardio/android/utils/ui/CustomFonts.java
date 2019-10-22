/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Typeface
 */
package com.getqardio.android.utils.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Map;

public class CustomFonts {
    private static CustomFonts instance;
    private Map<String, Typeface> fontMap = new HashMap<String, Typeface>();

    public static CustomFonts getInstance() {
        if (instance == null) {
            instance = new CustomFonts();
        }
        return instance;
    }

    public Typeface getTypeface(Context context, String string2) {
        Typeface typeface;
        Typeface typeface2 = typeface = this.fontMap.get(string2);
        if (typeface == null) {
            typeface2 = Typeface.createFromAsset((AssetManager)context.getAssets(), (String)string2);
            this.fontMap.put(string2, typeface2);
        }
        return typeface2;
    }
}

