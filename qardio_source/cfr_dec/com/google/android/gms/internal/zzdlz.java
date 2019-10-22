/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdma;
import com.google.android.gms.internal.zzdmb;
import com.google.android.gms.internal.zzdmc;
import com.google.android.gms.internal.zzdmd;
import com.google.android.gms.internal.zzdme;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.List;

public final class zzdlz {
    /*
     * Enabled aggressive block sorting
     */
    private static int zza(String string2, zzdmd[] arrzzdmd) {
        int n = arrzzdmd.length;
        int n2 = 0;
        int n3 = 14;
        while (n2 < n) {
            int n4;
            zzdmd zzdmd2 = arrzzdmd[n2];
            if (n3 == 14) {
                if (zzdmd2.type == 9 || zzdmd2.type == 2 || zzdmd2.type == 6) {
                    n4 = zzdmd2.type;
                } else {
                    n4 = n3;
                    if (zzdmd2.type != 14) {
                        n3 = zzdmd2.type;
                        throw new IllegalArgumentException(new StringBuilder(String.valueOf(string2).length() + 48).append("Unexpected TypedValue type: ").append(n3).append(" for key ").append(string2).toString());
                    }
                }
            } else {
                n4 = n3;
                if (zzdmd2.type != n3) {
                    n2 = zzdmd2.type;
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(string2).length() + 126).append("The ArrayList elements should all be the same type, but ArrayList with key ").append(string2).append(" contains items of type ").append(n3).append(" and ").append(n2).toString());
                }
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public static DataMap zza(zzdma zzdma2) {
        DataMap dataMap = new DataMap();
        for (zzdmc zzdmc2 : zzdma2.zzlmg.zzlmi) {
            zzdlz.zza(zzdma2.zzlmh, dataMap, zzdmc2.name, zzdmc2.zzlmk);
        }
        return dataMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ArrayList zza(List<Asset> list, zzdme arrzzdmd, int n) {
        ArrayList<Object> arrayList = new ArrayList<Object>(arrzzdmd.zzlmw.length);
        arrzzdmd = arrzzdmd.zzlmw;
        int n2 = arrzzdmd.length;
        int n3 = 0;
        while (n3 < n2) {
            zzdmd zzdmd2 = arrzzdmd[n3];
            if (zzdmd2.type == 14) {
                arrayList.add(null);
            } else if (n == 9) {
                DataMap dataMap = new DataMap();
                for (zzdmc zzdmc2 : zzdmd2.zzlmm.zzlmv) {
                    zzdlz.zza(list, dataMap, zzdmc2.name, zzdmc2.zzlmk);
                }
                arrayList.add(dataMap);
            } else if (n == 2) {
                arrayList.add(zzdmd2.zzlmm.zzlmo);
            } else {
                if (n != 6) {
                    throw new IllegalArgumentException(new StringBuilder(39).append("Unexpected typeOfArrayList: ").append(n).toString());
                }
                arrayList.add(zzdmd2.zzlmm.zzlms);
            }
            ++n3;
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void zza(List<Asset> object, DataMap dataMap, String string2, zzdmd object2) {
        int n = ((zzdmd)object2).type;
        if (n == 14) {
            dataMap.putString(string2, null);
            return;
        }
        zzdmc[] arrzzdmc = ((zzdmd)object2).zzlmm;
        if (n == 1) {
            dataMap.putByteArray(string2, arrzzdmc.zzlmn);
            return;
        }
        if (n == 11) {
            dataMap.putStringArray(string2, arrzzdmc.zzlmx);
            return;
        }
        if (n == 12) {
            dataMap.putLongArray(string2, arrzzdmc.zzlmy);
            return;
        }
        if (n == 15) {
            dataMap.putFloatArray(string2, arrzzdmc.zzlmz);
            return;
        }
        if (n == 2) {
            dataMap.putString(string2, arrzzdmc.zzlmo);
            return;
        }
        if (n == 3) {
            dataMap.putDouble(string2, arrzzdmc.zzlmp);
            return;
        }
        if (n == 4) {
            dataMap.putFloat(string2, arrzzdmc.zzlmq);
            return;
        }
        if (n == 5) {
            dataMap.putLong(string2, arrzzdmc.zzlmr);
            return;
        }
        if (n == 6) {
            dataMap.putInt(string2, arrzzdmc.zzlms);
            return;
        }
        if (n == 7) {
            dataMap.putByte(string2, (byte)arrzzdmc.zzlmt);
            return;
        }
        if (n == 8) {
            dataMap.putBoolean(string2, arrzzdmc.zzlmu);
            return;
        }
        if (n == 13) {
            if (object == null) {
                object = String.valueOf(string2);
                if (((String)object).length() != 0) {
                    object = "populateBundle: unexpected type for: ".concat((String)object);
                    do {
                        throw new RuntimeException((String)object);
                        break;
                    } while (true);
                }
                object = new String("populateBundle: unexpected type for: ");
                throw new RuntimeException((String)object);
            }
            dataMap.putAsset(string2, (Asset)object.get((int)arrzzdmc.zzlna));
            return;
        }
        if (n == 9) {
            object2 = new DataMap();
            for (zzdmc zzdmc2 : arrzzdmc.zzlmv) {
                zzdlz.zza((List<Asset>)object, (DataMap)object2, zzdmc2.name, zzdmc2.zzlmk);
            }
            dataMap.putDataMap(string2, (DataMap)object2);
            return;
        }
        if (n != 10) throw new RuntimeException(new StringBuilder(43).append("populateBundle: unexpected type ").append(n).toString());
        n = zzdlz.zza(string2, arrzzdmc.zzlmw);
        object = zzdlz.zza((List<Asset>)object, (zzdme)arrzzdmc, n);
        if (n == 14) {
            dataMap.putStringArrayList(string2, (ArrayList<String>)object);
            return;
        }
        if (n == 9) {
            dataMap.putDataMapArrayList(string2, (ArrayList<DataMap>)object);
            return;
        }
        if (n == 2) {
            dataMap.putStringArrayList(string2, (ArrayList<String>)object);
            return;
        }
        if (n != 6) throw new IllegalStateException(new StringBuilder(39).append("Unexpected typeOfArrayList: ").append(n).toString());
        dataMap.putIntegerArrayList(string2, (ArrayList<Integer>)object);
    }
}

