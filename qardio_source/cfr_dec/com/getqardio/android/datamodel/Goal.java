/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;

public class Goal
extends BaseEntity {
    public Integer excellentBoundary;
    public Integer neutralBoundary;
    public Integer positiveBoundary;
    public Long startDate;
    public Integer syncStatus;
    public Float target;
    public Float targetPerWeek;
    public String type;
    public Long userId;

    public static float getMaxGoalWeight(float f) {
        return 1.3f * f;
    }

    public static float getMinGoalWeight(float f) {
        return 0.7f * f;
    }

    public static int getMinWeeksCount(float f, float f2) {
        return (int)Math.ceil(Math.abs(f - f2) / 2.0f);
    }
}

