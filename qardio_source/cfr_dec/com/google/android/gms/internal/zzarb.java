/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.XmlResourceParser
 *  android.text.TextUtils
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzara;
import com.google.android.gms.internal.zzarc;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

class zzarb<T extends zzara>
extends zzapz {
    private zzarc<T> zzdvk;

    public zzarb(zzaqc zzaqc2, zzarc<T> zzarc2) {
        super(zzaqc2);
        this.zzdvk = zzarc2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final T zza(XmlResourceParser xmlResourceParser) {
        block16: {
            try {
                xmlResourceParser.next();
                n = xmlResourceParser.getEventType();
lbl5:
                // 2 sources
                do {
                    if (n == 1) return this.zzdvk.zzyo();
                    if (xmlResourceParser.getEventType() != 2) break block16;
                    string3 = xmlResourceParser.getName().toLowerCase();
                    if (string3.equals("screenname")) {
                        string3 = xmlResourceParser.getAttributeValue(null, "name");
                        string2 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty((CharSequence)string3) && !TextUtils.isEmpty((CharSequence)string2)) {
                            this.zzdvk.zzi(string3, string2);
                        }
                        break block16;
                    }
                    if (string3.equals("string")) {
                        string3 = xmlResourceParser.getAttributeValue(null, "name");
                        string2 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty((CharSequence)string3) && string2 != null) {
                            this.zzdvk.zzj(string3, string2);
                        }
                        break block16;
                    }
                    if (string3.equals("bool")) {
                        string2 = xmlResourceParser.getAttributeValue(null, "name");
                        string3 = xmlResourceParser.nextText().trim();
                        if (!TextUtils.isEmpty((CharSequence)string2) && !(bl = TextUtils.isEmpty((CharSequence)string3))) {
                            try {
                                bl = Boolean.parseBoolean(string3);
                                this.zzdvk.zzc(string2, bl);
                            }
                            catch (NumberFormatException numberFormatException) {
                                this.zzc("Error parsing bool configuration value", string3, numberFormatException);
                            }
                        }
                        break block16;
                    }
                    if (!string3.equals("integer")) break block16;
                    string2 = xmlResourceParser.getAttributeValue(null, "name");
                    string3 = xmlResourceParser.nextText().trim();
                    if (TextUtils.isEmpty((CharSequence)string2) || (bl = TextUtils.isEmpty((CharSequence)string3))) break block16;
                    ** try [egrp 4[TRYBLOCK] [19 : 337->358)] { 
lbl37:
                    // 1 sources
                    break;
                } while (true);
            }
            catch (XmlPullParserException xmlPullParserException) {
                this.zze("Error parsing tracker configuration file", (Object)xmlPullParserException);
            }
            return this.zzdvk.zzyo();
            catch (IOException iOException) {
                this.zze("Error parsing tracker configuration file", iOException);
                return this.zzdvk.zzyo();
            }
            {
                n = Integer.parseInt(string3);
                this.zzdvk.zzd(string2, n);
            }
lbl47:
            // 1 sources
            catch (NumberFormatException numberFormatException) {
                this.zzc("Error parsing int configuration value", string3, numberFormatException);
            }
        }
        n = xmlResourceParser.next();
        ** while (true)
    }

    public final T zzav(int n) {
        T t;
        try {
            t = this.zza(this.zzwr().zzxg().getResources().getXml(n));
        }
        catch (Resources.NotFoundException notFoundException) {
            this.zzd("inflate() called with unknown resourceId", (Object)notFoundException);
            return null;
        }
        return t;
    }
}

