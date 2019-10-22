/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.widget.calendar.entity;

import java.util.Date;

public class MonthCellEntity {
    private Date date;
    private boolean isCurrentMonth;
    private boolean isHighlighted;
    private boolean isToday;
    private int value;

    public MonthCellEntity(Date date, boolean bl, boolean bl2, boolean bl3, int n) {
        this.date = date;
        this.isCurrentMonth = bl;
        this.isHighlighted = bl3;
        this.isToday = bl2;
        this.value = n;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isCurrentMonth() {
        return this.isCurrentMonth;
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public boolean isToday() {
        return this.isToday;
    }
}

