/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.internal.zzcxe;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzr {
    private final String zzebs;
    private final Account zzebz;
    private final Set<Scope> zzfmo;
    private final int zzfmq;
    private final View zzfmr;
    private final String zzfms;
    private final Set<Scope> zzfzg;
    private final Map<Api<?>, zzt> zzfzh;
    private final zzcxe zzfzi;
    private Integer zzfzj;

    /*
     * Enabled aggressive block sorting
     */
    public zzr(Account object, Set<Scope> iterator, Map<Api<?>, zzt> map, int n, View view, String string2, String string3, zzcxe zzcxe2) {
        this.zzebz = object;
        object = iterator == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(iterator);
        this.zzfmo = object;
        object = map;
        if (map == null) {
            object = Collections.EMPTY_MAP;
        }
        this.zzfzh = object;
        this.zzfmr = view;
        this.zzfmq = n;
        this.zzebs = string2;
        this.zzfms = string3;
        this.zzfzi = zzcxe2;
        object = new HashSet<Scope>(this.zzfmo);
        iterator = this.zzfzh.values().iterator();
        do {
            if (!iterator.hasNext()) {
                this.zzfzg = Collections.unmodifiableSet(object);
                return;
            }
            object.addAll(iterator.next().zzehs);
        } while (true);
    }

    public final Account getAccount() {
        return this.zzebz;
    }

    @Deprecated
    public final String getAccountName() {
        if (this.zzebz != null) {
            return this.zzebz.name;
        }
        return null;
    }

    public final Account zzakt() {
        if (this.zzebz != null) {
            return this.zzebz;
        }
        return new Account("<<default account>>", "com.google");
    }

    public final Set<Scope> zzakv() {
        return this.zzfmo;
    }

    public final Set<Scope> zzakw() {
        return this.zzfzg;
    }

    public final Map<Api<?>, zzt> zzakx() {
        return this.zzfzh;
    }

    public final String zzaky() {
        return this.zzebs;
    }

    public final String zzakz() {
        return this.zzfms;
    }

    public final zzcxe zzalb() {
        return this.zzfzi;
    }

    public final Integer zzalc() {
        return this.zzfzj;
    }

    public final void zzc(Integer n) {
        this.zzfzj = n;
    }
}

