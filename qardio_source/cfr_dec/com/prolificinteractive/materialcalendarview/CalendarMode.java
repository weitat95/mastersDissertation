/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview;

public enum CalendarMode {
    MONTHS(6),
    WEEKS(1);

    final int visibleWeeksCount;

    private CalendarMode(int n2) {
        this.visibleWeeksCount = n2;
    }
}

