/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.zzb;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zzb();
    private static zzd zzegr = zzh.zzamg();
    private int versionCode;
    private String zzbuz;
    private List<Scope> zzecp;
    private String zzefb;
    private String zzefc;
    private String zzefs;
    private String zzegs;
    private String zzegt;
    private Uri zzegu;
    private String zzegv;
    private long zzegw;
    private String zzegx;
    private Set<Scope> zzegy = new HashSet<Scope>();

    GoogleSignInAccount(int n, String string2, String string3, String string4, String string5, Uri uri, String string6, long l, String string7, List<Scope> list, String string8, String string9) {
        this.versionCode = n;
        this.zzbuz = string2;
        this.zzefs = string3;
        this.zzegs = string4;
        this.zzegt = string5;
        this.zzegu = uri;
        this.zzegv = string6;
        this.zzegw = l;
        this.zzegx = string7;
        this.zzecp = list;
        this.zzefb = string8;
        this.zzefc = string9;
    }

    private static GoogleSignInAccount zza(String string2, String string3, String string4, String string5, String string6, String string7, Uri uri, Long l, String string8, Set<Scope> set) {
        Long l2 = l;
        if (l == null) {
            l2 = zzegr.currentTimeMillis() / 1000L;
        }
        return new GoogleSignInAccount(3, string2, string3, string4, string5, uri, null, l2, zzbq.zzgm(string8), new ArrayList<Scope>((Collection)zzbq.checkNotNull(set)), string6, string7);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static GoogleSignInAccount zzeu(String object) throws JSONException {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject((String)object);
        object = jSONObject.optString("photoUrl", null);
        object = !TextUtils.isEmpty((CharSequence)object) ? Uri.parse((String)object) : null;
        long l = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet<Scope> hashSet = new HashSet<Scope>();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int n = jSONArray.length();
        int n2 = 0;
        do {
            if (n2 >= n) {
                object = GoogleSignInAccount.zza(jSONObject.optString("id"), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), (Uri)object, l, jSONObject.getString("obfuscatedIdentifier"), hashSet);
                ((GoogleSignInAccount)object).zzegv = jSONObject.optString("serverAuthCode", null);
                return object;
            }
            hashSet.add(new Scope(jSONArray.getString(n2)));
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof GoogleSignInAccount)) {
                    return false;
                }
                object = (GoogleSignInAccount)object;
                if (!((GoogleSignInAccount)object).zzegx.equals(this.zzegx) || !((GoogleSignInAccount)object).zzabb().equals(this.zzabb())) break block5;
            }
            return true;
        }
        return false;
    }

    public Account getAccount() {
        if (this.zzegs == null) {
            return null;
        }
        return new Account(this.zzegs, "com.google");
    }

    public String getDisplayName() {
        return this.zzegt;
    }

    public String getEmail() {
        return this.zzegs;
    }

    public String getFamilyName() {
        return this.zzefc;
    }

    public String getGivenName() {
        return this.zzefb;
    }

    public String getId() {
        return this.zzbuz;
    }

    public String getIdToken() {
        return this.zzefs;
    }

    public Uri getPhotoUrl() {
        return this.zzegu;
    }

    public String getServerAuthCode() {
        return this.zzegv;
    }

    public int hashCode() {
        return (this.zzegx.hashCode() + 527) * 31 + this.zzabb().hashCode();
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.versionCode);
        zzbfp.zza(parcel, 2, this.getId(), false);
        zzbfp.zza(parcel, 3, this.getIdToken(), false);
        zzbfp.zza(parcel, 4, this.getEmail(), false);
        zzbfp.zza(parcel, 5, this.getDisplayName(), false);
        zzbfp.zza(parcel, 6, (Parcelable)this.getPhotoUrl(), n, false);
        zzbfp.zza(parcel, 7, this.getServerAuthCode(), false);
        zzbfp.zza(parcel, 8, this.zzegw);
        zzbfp.zza(parcel, 9, this.zzegx, false);
        zzbfp.zzc(parcel, 10, this.zzecp, false);
        zzbfp.zza(parcel, 11, this.getGivenName(), false);
        zzbfp.zza(parcel, 12, this.getFamilyName(), false);
        zzbfp.zzai(parcel, n2);
    }

    public final Set<Scope> zzabb() {
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zzecp);
        hashSet.addAll(this.zzegy);
        return hashSet;
    }
}

