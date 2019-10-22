/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.os.Parcel;
import android.os.Parcelable;
import com.mixpanel.android.mpmetrics.BadDecideObjectException;
import com.mixpanel.android.mpmetrics.InAppButton;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.util.JSONUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TakeoverInAppNotification
extends InAppNotification {
    public static final Parcelable.Creator<TakeoverInAppNotification> CREATOR = new Parcelable.Creator<TakeoverInAppNotification>(){

        public TakeoverInAppNotification createFromParcel(Parcel parcel) {
            return new TakeoverInAppNotification(parcel);
        }

        public TakeoverInAppNotification[] newArray(int n) {
            return new TakeoverInAppNotification[n];
        }
    };
    private final ArrayList<InAppButton> mButtons;
    private final int mCloseButtonColor;
    private final boolean mShouldFadeImage;
    private final String mTitle;
    private final int mTitleColor;

    /*
     * Enabled aggressive block sorting
     */
    public TakeoverInAppNotification(Parcel parcel) {
        super(parcel);
        this.mButtons = parcel.createTypedArrayList(InAppButton.CREATOR);
        this.mCloseButtonColor = parcel.readInt();
        this.mTitle = parcel.readString();
        this.mTitleColor = parcel.readInt();
        boolean bl = parcel.readByte() != 0;
        this.mShouldFadeImage = bl;
    }

    TakeoverInAppNotification(JSONObject jSONObject) throws BadDecideObjectException {
        super(jSONObject);
        JSONArray jSONArray = jSONObject.getJSONArray("buttons");
        this.mButtons = new ArrayList();
        int n = 0;
        do {
            if (n >= jSONArray.length()) break;
            JSONObject jSONObject2 = (JSONObject)jSONArray.get(n);
            this.mButtons.add(new InAppButton(jSONObject2));
            ++n;
            continue;
            break;
        } while (true);
        try {
            this.mCloseButtonColor = jSONObject.getInt("close_color");
            this.mTitle = JSONUtils.optionalStringKey(jSONObject, "title");
            this.mTitleColor = jSONObject.optInt("title_color");
            this.mShouldFadeImage = this.getExtras().getBoolean("image_fade");
            return;
        }
        catch (JSONException jSONException) {
            throw new BadDecideObjectException("Notification JSON was unexpected or bad", jSONException);
        }
    }

    public InAppButton getButton(int n) {
        if (this.mButtons.size() > n) {
            return this.mButtons.get(n);
        }
        return null;
    }

    public int getCloseColor() {
        return this.mCloseButtonColor;
    }

    public int getNumButtons() {
        return this.mButtons.size();
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int getTitleColor() {
        return this.mTitleColor;
    }

    @Override
    public InAppNotification.Type getType() {
        return InAppNotification.Type.TAKEOVER;
    }

    public boolean hasTitle() {
        return this.mTitle != null;
    }

    public boolean setShouldShowShadow() {
        return this.mShouldFadeImage;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeTypedList(this.mButtons);
        parcel.writeInt(this.mCloseButtonColor);
        parcel.writeString(this.mTitle);
        parcel.writeInt(this.mTitleColor);
        n = this.mShouldFadeImage ? 1 : 0;
        parcel.writeByte((byte)n);
    }

}

