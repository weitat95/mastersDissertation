/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzclq;

final class zzcgh {
    private final String mAppId;
    private String zzcwz;
    private String zzdra;
    private String zzggb;
    private final zzcim zziwf;
    private String zziww;
    private String zziwx;
    private long zziwy;
    private long zziwz;
    private long zzixa;
    private long zzixb;
    private String zzixc;
    private long zzixd;
    private long zzixe;
    private boolean zzixf;
    private long zzixg;
    private boolean zzixh;
    private long zzixi;
    private long zzixj;
    private long zzixk;
    private long zzixl;
    private long zzixm;
    private long zzixn;
    private String zzixo;
    private boolean zzixp;
    private long zzixq;
    private long zzixr;

    zzcgh(zzcim zzcim2, String string2) {
        zzbq.checkNotNull(zzcim2);
        zzbq.zzgm(string2);
        this.zziwf = zzcim2;
        this.mAppId = string2;
        ((zzcjk)this.zziwf.zzawx()).zzve();
    }

    public final String getAppId() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.mAppId;
    }

    public final String getAppInstanceId() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzggb;
    }

    public final String getGmpAppId() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzcwz;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setAppVersion(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zzdra, string2);
        this.zzixp = bl2 | bl;
        this.zzdra = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setMeasurementEnabled(boolean bl) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl2 = this.zzixp;
        boolean bl3 = this.zzixf != bl;
        this.zzixp = bl3 | bl2;
        this.zzixf = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzal(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zziwz != l;
        this.zzixp = bl2 | bl;
        this.zziwz = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzam(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixa != l;
        this.zzixp = bl2 | bl;
        this.zzixa = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzan(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixb != l;
        this.zzixp = bl2 | bl;
        this.zzixb = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzao(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixd != l;
        this.zzixp = bl2 | bl;
        this.zzixd = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzap(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixe != l;
        this.zzixp = bl2 | bl;
        this.zzixe = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzaq(long l) {
        boolean bl = true;
        boolean bl2 = l >= 0L;
        zzbq.checkArgument(bl2);
        ((zzcjk)this.zziwf.zzawx()).zzve();
        bl2 = this.zzixp;
        if (this.zziwy == l) {
            bl = false;
        }
        this.zzixp = bl2 | bl;
        this.zziwy = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzar(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixq != l;
        this.zzixp = bl2 | bl;
        this.zzixq = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzas(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixr != l;
        this.zzixp = bl2 | bl;
        this.zzixr = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzat(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixi != l;
        this.zzixp = bl2 | bl;
        this.zzixi = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzau(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixj != l;
        this.zzixp = bl2 | bl;
        this.zzixj = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzav(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixk != l;
        this.zzixp = bl2 | bl;
        this.zzixk = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzaw(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixl != l;
        this.zzixp = bl2 | bl;
        this.zzixl = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzax(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixn != l;
        this.zzixp = bl2 | bl;
        this.zzixn = l;
    }

    public final void zzaxb() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        this.zzixp = false;
    }

    public final String zzaxc() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zziww;
    }

    public final String zzaxd() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zziwx;
    }

    public final long zzaxe() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zziwz;
    }

    public final long zzaxf() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixa;
    }

    public final long zzaxg() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixb;
    }

    public final String zzaxh() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixc;
    }

    public final long zzaxi() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixd;
    }

    public final long zzaxj() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixe;
    }

    public final boolean zzaxk() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixf;
    }

    public final long zzaxl() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zziwy;
    }

    public final long zzaxm() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixq;
    }

    public final long zzaxn() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixr;
    }

    public final void zzaxo() {
        long l;
        ((zzcjk)this.zziwf.zzawx()).zzve();
        long l2 = l = this.zziwy + 1L;
        if (l > Integer.MAX_VALUE) {
            this.zziwf.zzawy().zzazf().zzj("Bundle index overflow. appId", zzchm.zzjk(this.mAppId));
            l2 = 0L;
        }
        this.zzixp = true;
        this.zziwy = l2;
    }

    public final long zzaxp() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixi;
    }

    public final long zzaxq() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixj;
    }

    public final long zzaxr() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixk;
    }

    public final long zzaxs() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixl;
    }

    public final long zzaxt() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixn;
    }

    public final long zzaxu() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixm;
    }

    public final String zzaxv() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixo;
    }

    public final String zzaxw() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        String string2 = this.zzixo;
        this.zziw(null);
        return string2;
    }

    public final long zzaxx() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixg;
    }

    public final boolean zzaxy() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzixh;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzay(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixm != l;
        this.zzixp = bl2 | bl;
        this.zzixm = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzaz(long l) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = this.zzixg != l;
        this.zzixp = bl2 | bl;
        this.zzixg = l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzbl(boolean bl) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl2 = this.zzixh != bl;
        this.zzixp = bl2;
        this.zzixh = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzir(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zzggb, string2);
        this.zzixp = bl2 | bl;
        this.zzggb = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzis(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        String string3 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = null;
        }
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zzcwz, string3);
        this.zzixp = bl2 | bl;
        this.zzcwz = string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzit(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zziww, string2);
        this.zzixp = bl2 | bl;
        this.zziww = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zziu(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zziwx, string2);
        this.zzixp = bl2 | bl;
        this.zziwx = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zziv(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zzixc, string2);
        this.zzixp = bl2 | bl;
        this.zzixc = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zziw(String string2) {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        boolean bl = this.zzixp;
        boolean bl2 = !zzclq.zzas(this.zzixo, string2);
        this.zzixp = bl2 | bl;
        this.zzixo = string2;
    }

    public final String zzvj() {
        ((zzcjk)this.zziwf.zzawx()).zzve();
        return this.zzdra;
    }
}

