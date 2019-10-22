/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.os.Parcel;
import android.os.Parcelable;
import com.mixpanel.android.mpmetrics.BadDecideObjectException;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.util.JSONUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MiniInAppNotification
extends InAppNotification {
    public static final Parcelable.Creator<MiniInAppNotification> CREATOR = new Parcelable.Creator<MiniInAppNotification>(){

        public MiniInAppNotification createFromParcel(Parcel parcel) {
            return new MiniInAppNotification(parcel);
        }

        public MiniInAppNotification[] newArray(int n) {
            return new MiniInAppNotification[n];
        }
    };
    private final int mBorderColor;
    private final String mCtaUrl;
    private final int mImageTintColor;

    public MiniInAppNotification(Parcel parcel) {
        super(parcel);
        this.mCtaUrl = parcel.readString();
        this.mImageTintColor = parcel.readInt();
        this.mBorderColor = parcel.readInt();
    }

    MiniInAppNotification(JSONObject jSONObject) throws BadDecideObjectException {
        super(jSONObject);
        try {
            this.mCtaUrl = JSONUtils.optionalStringKey(jSONObject, "cta_url");
            this.mImageTintColor = jSONObject.getInt("image_tint_color");
            this.mBorderColor = jSONObject.getInt("border_color");
            return;
        }
        catch (JSONException jSONException) {
            throw new BadDecideObjectException("Notification JSON was unexpected or bad", jSONException);
        }
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public String getCtaUrl() {
        return this.mCtaUrl;
    }

    public int getImageTintColor() {
        return this.mImageTintColor;
    }

    @Override
    public InAppNotification.Type getType() {
        return InAppNotification.Type.MINI;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.mCtaUrl);
        parcel.writeInt(this.mImageTintColor);
        parcel.writeInt(this.mBorderColor);
    }

}

