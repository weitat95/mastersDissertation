/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class InAppButton
implements Parcelable {
    public static final Parcelable.Creator<InAppButton> CREATOR = new Parcelable.Creator<InAppButton>(){

        public InAppButton createFromParcel(Parcel parcel) {
            return new InAppButton(parcel);
        }

        public InAppButton[] newArray(int n) {
            return new InAppButton[n];
        }
    };
    private int mBackgroundColor;
    private int mBorderColor;
    private String mCtaUrl;
    private JSONObject mDescription;
    private String mText;
    private int mTextColor;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InAppButton(Parcel parcel) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2;
            jSONObject = jSONObject2 = new JSONObject(parcel.readString());
        }
        catch (JSONException jSONException) {
            Log.e((String)"MixpanelAPI.InAppButton", (String)"Error reading JSON when creating InAppButton from Parcel");
        }
        this.mDescription = jSONObject;
        this.mText = parcel.readString();
        this.mTextColor = parcel.readInt();
        this.mBackgroundColor = parcel.readInt();
        this.mBorderColor = parcel.readInt();
        this.mCtaUrl = parcel.readString();
    }

    InAppButton(JSONObject jSONObject) throws JSONException {
        this.mDescription = jSONObject;
        this.mText = jSONObject.getString("text");
        this.mTextColor = jSONObject.getInt("text_color");
        this.mBackgroundColor = jSONObject.getInt("bg_color");
        this.mBorderColor = jSONObject.getInt("border_color");
        this.mCtaUrl = jSONObject.getString("cta_url");
    }

    public int describeContents() {
        return 0;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public String getCtaUrl() {
        return this.mCtaUrl;
    }

    public String getText() {
        return this.mText;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public String toString() {
        return this.mDescription.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mDescription.toString());
        parcel.writeString(this.mText);
        parcel.writeInt(this.mTextColor);
        parcel.writeInt(this.mBackgroundColor);
        parcel.writeInt(this.mBorderColor);
        parcel.writeString(this.mCtaUrl);
    }

}

