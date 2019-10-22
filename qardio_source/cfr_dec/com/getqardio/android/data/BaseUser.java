/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.data;

import com.getqardio.android.util.DateUtil;
import java.util.Date;

public class BaseUser {
    public final String authToken;
    public final int born;
    public final Integer display;
    public int goal;
    public final int height;
    public final int id;
    public final int male;
    public final String name;
    public int rate;
    public final int units;

    /*
     * Enabled aggressive block sorting
     */
    public BaseUser(int n, String string2, int n2, boolean bl, Date date, int n3, Integer n4, String string3) {
        this.id = n;
        this.name = string2;
        n = bl ? 1 : 0;
        this.male = n;
        this.born = DateUtil.getDaysForDate(date);
        this.units = n3;
        this.height = n2;
        this.display = n4;
        this.authToken = string3;
    }
}

