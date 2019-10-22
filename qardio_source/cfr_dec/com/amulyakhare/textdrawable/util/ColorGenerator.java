/*
 * Decompiled with CFR 0.147.
 */
package com.amulyakhare.textdrawable.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorGenerator {
    public static ColorGenerator DEFAULT = ColorGenerator.create(Arrays.asList(-957596, -686759, -416706, -1784274, -9977996, -10902850, -14642227, -5414233, -8366207));
    public static ColorGenerator MATERIAL = ColorGenerator.create(Arrays.asList(-1739917, -1023342, -4560696, -6982195, -8812853, -10177034, -11549705, -11677471, -11684180, -8271996, -5319295, -30107, -2825897, -10929, -18611, -6190977, -7297874));
    private final List<Integer> mColors;
    private final Random mRandom;

    private ColorGenerator(List<Integer> list) {
        this.mColors = list;
        this.mRandom = new Random(System.currentTimeMillis());
    }

    public static ColorGenerator create(List<Integer> list) {
        return new ColorGenerator(list);
    }

    public int getColor(Object object) {
        return this.mColors.get(Math.abs(object.hashCode()) % this.mColors.size());
    }
}

