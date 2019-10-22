/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.ui;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import java.util.Arrays;

public class ColorsUtil {
    private static ColorGenerator generator = ColorGenerator.create(Arrays.asList(-4776932, -7860657, -11922292, -13558894, -15064194, -15906911, -16752540, -16757440, -14983648, -13407970, -8227049, -688361, -37120, -1683200, -12703965));

    public static int generateColor(Object object) {
        return generator.getColor(object);
    }
}

