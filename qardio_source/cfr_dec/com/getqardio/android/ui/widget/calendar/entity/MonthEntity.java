/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.widget.calendar.entity;

import java.util.Date;

public class MonthEntity {
    private Date date;
    private String label;
    private int month;
    private int year;

    public MonthEntity(int n, int n2, Date date, String string2) {
        this.month = n;
        this.year = n2;
        this.date = date;
        this.label = string2;
    }

    public Date getDate() {
        return this.date;
    }

    public String getLabel() {
        return this.label;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public void setLabel(String string2) {
        this.label = string2;
    }
}

