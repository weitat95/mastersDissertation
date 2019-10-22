/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbs;
import com.google.android.gms.internal.zzdja;
import com.google.android.gms.internal.zzdjc;
import com.google.android.gms.internal.zzdjg;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzar;
import com.google.android.gms.tagmanager.zzbn;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzbr;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzdm;
import com.google.android.gms.tagmanager.zzdy;
import com.google.android.gms.tagmanager.zzea;
import com.google.android.gms.tagmanager.zzeo;
import com.google.android.gms.tagmanager.zzeq;
import com.google.android.gms.tagmanager.zzer;
import com.google.android.gms.tagmanager.zzfb;
import com.google.android.gms.tagmanager.zzff;
import com.google.android.gms.tagmanager.zzfg;
import com.google.android.gms.tagmanager.zzfh;
import com.google.android.gms.tagmanager.zzfi;
import com.google.android.gms.tagmanager.zzfj;
import com.google.android.gms.tagmanager.zzgk;
import com.google.android.gms.tagmanager.zzgn;
import com.google.android.gms.tagmanager.zzgo;
import com.google.android.gms.tagmanager.zzp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzfc {
    private static final zzea<zzbs> zzkil = new zzea<zzbs>(zzgk.zzbgs(), true);
    private final DataLayer zzkde;
    private final zzbo zzkin;
    private final Map<String, zzbr> zzkio;
    private final Map<String, zzbr> zzkip;
    private final Map<String, zzbr> zzkiq;
    private final zzp<zzdjc, zzea<zzbs>> zzkir;
    private final zzp<String, zzfi> zzkis;
    private final Set<zzdjg> zzkit;
    private final Map<String, zzfj> zzkiu;
    private volatile String zzkiv;
    private int zzkiw;

    /*
     * Enabled aggressive block sorting
     */
    private final zzea<zzbs> zza(zzbs object, Set<String> object2, zzgn zzea2) {
        if (!((zzbs)object).zzyu) {
            return new zzea<Object>(object, true);
        }
        switch (((zzbs)object).type) {
            default: {
                int n = ((zzbs)object).type;
                zzdj.e(new StringBuilder(25).append("Unknown type: ").append(n).toString());
                return zzkil;
            }
            case 2: {
                zzbs zzbs2 = zzdja.zzj((zzbs)object);
                zzbs2.zzyl = new zzbs[((zzbs)object).zzyl.length];
                int n = 0;
                do {
                    if (n >= ((zzbs)object).zzyl.length) {
                        return new zzea<zzbs>(zzbs2, false);
                    }
                    zzea<zzbs> zzea3 = this.zza(((zzbs)object).zzyl[n], (Set<String>)object2, zzea2.zzel(n));
                    if (zzea3 == zzkil) {
                        return zzkil;
                    }
                    zzbs2.zzyl[n] = zzea3.getObject();
                    ++n;
                } while (true);
            }
            case 3: {
                zzbs zzbs3 = zzdja.zzj((zzbs)object);
                if (((zzbs)object).zzym.length != ((zzbs)object).zzyn.length) {
                    object = ((String)(object = String.valueOf(((zzfjs)object).toString()))).length() != 0 ? "Invalid serving value: ".concat((String)object) : new String("Invalid serving value: ");
                    zzdj.e((String)object);
                    return zzkil;
                }
                zzbs3.zzym = new zzbs[((zzbs)object).zzym.length];
                zzbs3.zzyn = new zzbs[((zzbs)object).zzym.length];
                int n = 0;
                do {
                    if (n >= ((zzbs)object).zzym.length) {
                        return new zzea<zzbs>(zzbs3, false);
                    }
                    zzea<zzbs> zzea4 = this.zza(((zzbs)object).zzym[n], (Set<String>)object2, zzea2.zzem(n));
                    zzea<zzbs> zzea5 = this.zza(((zzbs)object).zzyn[n], (Set<String>)object2, zzea2.zzen(n));
                    if (zzea4 == zzkil || zzea5 == zzkil) {
                        return zzkil;
                    }
                    zzbs3.zzym[n] = zzea4.getObject();
                    zzbs3.zzyn[n] = zzea5.getObject();
                    ++n;
                } while (true);
            }
            case 4: {
                if (object2.contains(((zzbs)object).zzyo)) {
                    object = ((zzbs)object).zzyo;
                    object2 = object2.toString();
                    zzdj.e(new StringBuilder(String.valueOf(object).length() + 79 + String.valueOf(object2).length()).append("Macro cycle detected.  Current macro reference: ").append((String)object).append(".  Previous macro references: ").append((String)object2).append(".").toString());
                    return zzkil;
                }
                object2.add(((zzbs)object).zzyo);
                zzea2 = zzgo.zza(this.zza(((zzbs)object).zzyo, (Set<String>)object2, zzea2.zzbfj()), ((zzbs)object).zzyt);
                object2.remove(((zzbs)object).zzyo);
                return zzea2;
            }
            case 7: 
        }
        zzbs zzbs4 = zzdja.zzj((zzbs)object);
        zzbs4.zzys = new zzbs[((zzbs)object).zzys.length];
        int n = 0;
        while (n < ((zzbs)object).zzys.length) {
            zzea<zzbs> zzea6 = this.zza(((zzbs)object).zzys[n], (Set<String>)object2, zzea2.zzeo(n));
            if (zzea6 == zzkil) {
                return zzkil;
            }
            zzbs4.zzys[n] = zzea6.getObject();
            ++n;
        }
        return new zzea<zzbs>(zzbs4, false);
    }

    private final zzea<Boolean> zza(zzdjc object, Set<String> object2, zzeo zzeo2) {
        object = this.zza(this.zzkip, (zzdjc)object, (Set<String>)object2, zzeo2);
        object2 = zzgk.zzf((zzbs)((zzea)object).getObject());
        zzgk.zzam(object2);
        return new zzea<Object>(object2, ((zzea)object).zzbfk());
    }

    private final zzea<Boolean> zza(zzdjg object, Set<String> set, zzer zzer2) {
        zzea<Boolean> zzea2 = ((zzdjg)object).zzbin().iterator();
        boolean bl = true;
        while (zzea2.hasNext()) {
            zzea<Boolean> zzea3 = this.zza(zzea2.next(), set, zzer2.zzbfc());
            if (zzea3.getObject().booleanValue()) {
                zzgk.zzam(false);
                return new zzea<Boolean>(false, zzea3.zzbfk());
            }
            if (bl && zzea3.zzbfk()) {
                bl = true;
                continue;
            }
            bl = false;
        }
        object = ((zzdjg)object).zzbim().iterator();
        while (object.hasNext()) {
            zzea2 = this.zza((zzdjc)object.next(), set, zzer2.zzbfd());
            if (!((Boolean)zzea2.getObject()).booleanValue()) {
                zzgk.zzam(false);
                return new zzea<Boolean>(false, zzea2.zzbfk());
            }
            if (bl && zzea2.zzbfk()) {
                bl = true;
                continue;
            }
            bl = false;
        }
        zzgk.zzam(true);
        return new zzea<Boolean>(true, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final zzea<zzbs> zza(String string2, Set<String> object, zzdm zzea2) {
        ++this.zzkiw;
        Object object2 = this.zzkis.get(string2);
        if (object2 != null) {
            this.zza(((zzfi)object2).zzbfy(), (Set<String>)object);
            --this.zzkiw;
            return ((zzfi)object2).zzbfx();
        }
        object2 = this.zzkiu.get(string2);
        if (object2 == null) {
            object = this.zzbfw();
            zzdj.e(new StringBuilder(String.valueOf(object).length() + 15 + String.valueOf(string2).length()).append((String)object).append("Invalid macro: ").append(string2).toString());
            --this.zzkiw;
            return zzkil;
        }
        zzea<Set<zzdjc>> zzea3 = this.zza(string2, ((zzfj)object2).zzbfz(), ((zzfj)object2).zzbga(), ((zzfj)object2).zzbgb(), ((zzfj)object2).zzbgd(), ((zzfj)object2).zzbgc(), (Set<String>)object, zzea2.zzbek());
        if (zzea3.getObject().isEmpty()) {
            object2 = ((zzfj)object2).zzbge();
        } else {
            if (zzea3.getObject().size() > 1) {
                object2 = this.zzbfw();
                zzdj.zzcu(new StringBuilder(String.valueOf(object2).length() + 37 + String.valueOf(string2).length()).append((String)object2).append("Multiple macros active for macroName ").append(string2).toString());
            }
            object2 = zzea3.getObject().iterator().next();
        }
        if (object2 == null) {
            --this.zzkiw;
            return zzkil;
        }
        zzea2 = this.zza(this.zzkiq, (zzdjc)object2, (Set<String>)object, zzea2.zzbfb());
        boolean bl = zzea3.zzbfk() && zzea2.zzbfk();
        zzea2 = zzea2 == zzkil ? zzkil : new zzea<zzbs>(zzea2.getObject(), bl);
        object2 = ((zzdjc)object2).zzbfy();
        if (zzea2.zzbfk()) {
            this.zzkis.zzf(string2, new zzfi(zzea2, (zzbs)object2));
        }
        this.zza((zzbs)object2, (Set<String>)object);
        --this.zzkiw;
        return zzea2;
    }

    private final zzea<Set<zzdjc>> zza(String string2, Set<zzdjg> set, Map<zzdjg, List<zzdjc>> map, Map<zzdjg, List<String>> map2, Map<zzdjg, List<zzdjc>> map3, Map<zzdjg, List<String>> map4, Set<String> set2, zzfb zzfb2) {
        return this.zza(set, set2, new zzff(this, map, map2, map3, map4), zzfb2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final zzea<zzbs> zza(Map<String, zzbr> zzea2, zzdjc object, Set<String> zzea3, zzeo zzeo2) {
        boolean bl = true;
        Object object2 = ((zzdjc)object).zzbik().get(zzbh.zzqn.toString());
        if (object2 == null) {
            zzdj.e("No function id in properties");
            return zzkil;
        }
        String string2 = ((zzbs)object2).zzyp;
        zzbr zzbr2 = (zzbr)zzea2.get(string2);
        if (zzbr2 == null) {
            zzdj.e(String.valueOf(string2).concat(" has no backing implementation."));
            return zzkil;
        }
        object2 = this.zzkir.get((zzdjc)object);
        zzea2 = object2;
        if (object2 != null) return zzea2;
        zzea2 = new HashMap();
        object2 = ((zzdjc)object).zzbik().entrySet().iterator();
        boolean bl2 = true;
        while (object2.hasNext()) {
            Map.Entry<String, zzbs> entry = object2.next();
            zzea<zzbs> zzea4 = zzeo2.zzlt(entry.getKey());
            zzea4 = this.zza(entry.getValue(), (Set<String>)((Object)zzea3), zzea4.zza(entry.getValue()));
            if (zzea4 == zzkil) {
                return zzkil;
            }
            if (zzea4.zzbfk()) {
                ((zzdjc)object).zza(entry.getKey(), (zzbs)zzea4.getObject());
            } else {
                bl2 = false;
            }
            zzea2.put(entry.getKey(), (zzbs)zzea4.getObject());
        }
        if (!zzbr2.zzd(zzea2.keySet())) {
            object = String.valueOf(zzbr2.zzbex());
            zzea2 = String.valueOf(zzea2.keySet());
            zzdj.e(new StringBuilder(String.valueOf(string2).length() + 43 + String.valueOf(object).length() + String.valueOf(zzea2).length()).append("Incorrect keys for function ").append(string2).append(" required ").append((String)object).append(" had ").append((String)((Object)zzea2)).toString());
            return zzkil;
        }
        if (!bl2 || !zzbr2.zzbdp()) {
            bl = false;
        }
        zzea2 = zzea3 = new zzea<zzbs>(zzbr2.zzv((Map<String, zzbs>)((Object)zzea2)), bl);
        if (!bl) return zzea2;
        this.zzkir.zzf((zzdjc)object, zzea3);
        return zzea3;
    }

    private final zzea<Set<zzdjc>> zza(Set<zzdjg> object, Set<String> set, zzfh zzfh2, zzfb zzfb2) {
        HashSet<zzdjc> hashSet = new HashSet<zzdjc>();
        HashSet<zzdjc> hashSet2 = new HashSet<zzdjc>();
        object = object.iterator();
        boolean bl = true;
        while (object.hasNext()) {
            zzer zzer2;
            zzdjg zzdjg2 = (zzdjg)object.next();
            zzea<Boolean> zzea2 = this.zza(zzdjg2, set, zzer2 = zzfb2.zzbfi());
            if (zzea2.getObject().booleanValue()) {
                zzfh2.zza(zzdjg2, hashSet, hashSet2, zzer2);
            }
            if (bl && zzea2.zzbfk()) {
                bl = true;
                continue;
            }
            bl = false;
        }
        hashSet.removeAll(hashSet2);
        return new zzea<Set<zzdjc>>(hashSet, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(zzbs iterator, Set<String> object2) {
        if (iterator != null && (iterator = this.zza((zzbs)((Object)iterator), (Set<String>)object2, new zzdy())) != zzkil) {
            if ((iterator = zzgk.zzg((zzbs)((zzea)((Object)iterator)).getObject())) instanceof Map) {
                iterator = (Map)((Object)iterator);
                this.zzkde.push((Map<String, Object>)((Object)iterator));
                return;
            }
            if (!(iterator instanceof List)) {
                zzdj.zzcu("pushAfterEvaluate: value not a Map or List");
                return;
            }
            for (Object object2 : (List)((Object)iterator)) {
                if (object2 instanceof Map) {
                    object2 = (Map)object2;
                    this.zzkde.push((Map<String, Object>)object2);
                    continue;
                }
                zzdj.zzcu("pushAfterEvaluate: value not a Map");
            }
        }
    }

    private final String zzbfw() {
        if (this.zzkiw <= 1) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.zzkiw));
        for (int i = 2; i < this.zzkiw; ++i) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    private final void zzlx(String string2) {
        synchronized (this) {
            this.zzkiv = string2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzld(String object) {
        synchronized (this) {
            this.zzlx((String)object);
            object = this.zzkin.zzln((String)object).zzbev();
            Object object2 = this.zzkit;
            Object object3 = object.zzbek();
            object2 = this.zza((Set<zzdjg>)object2, (Set<String>)new HashSet<String>(), new zzfg(this), (zzfb)object3).getObject().iterator();
            do {
                if (!object2.hasNext()) {
                    this.zzlx(null);
                    return;
                }
                object3 = (zzdjc)object2.next();
                this.zza(this.zzkio, (zzdjc)object3, new HashSet<String>(), object.zzbej());
            } while (true);
        }
    }
}

