/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Parcel
 *  android.os.Parcelable
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.mixpanel.android.mpmetrics.BadDecideObjectException;
import com.mixpanel.android.util.JSONUtils;
import com.mixpanel.android.util.MPLog;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class InAppNotification
implements Parcelable {
    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("(\\.[^./]+$)");
    private final int mBackgroundColor;
    private final String mBody;
    private final int mBodyColor;
    protected final JSONObject mDescription;
    protected final JSONObject mExtras;
    protected final int mId;
    private Bitmap mImage;
    private final String mImageUrl;
    protected final int mMessageId;

    public InAppNotification() {
        this.mDescription = null;
        this.mExtras = null;
        this.mId = 0;
        this.mMessageId = 0;
        this.mBackgroundColor = 0;
        this.mBody = null;
        this.mBodyColor = 0;
        this.mImageUrl = null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public InAppNotification(Parcel parcel) {
        JSONObject jSONObject2;
        JSONObject jSONObject;
        block4: {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject = new JSONObject();
            jSONObject2 = new JSONObject(parcel.readString());
            jSONObject = jSONObject3 = new JSONObject(parcel.readString());
            break block4;
            {
                catch (JSONException jSONException) {}
            }
            catch (JSONException jSONException) {
                jSONObject2 = jSONObject3;
                MPLog.e("MixpanelAPI.InAppNotif", "Error reading JSON when creating InAppNotification from Parcel");
            }
        }
        this.mDescription = jSONObject2;
        this.mExtras = jSONObject;
        this.mId = parcel.readInt();
        this.mMessageId = parcel.readInt();
        this.mBackgroundColor = parcel.readInt();
        this.mBody = parcel.readString();
        this.mBodyColor = parcel.readInt();
        this.mImageUrl = parcel.readString();
        this.mImage = (Bitmap)parcel.readParcelable(Bitmap.class.getClassLoader());
    }

    InAppNotification(JSONObject jSONObject) throws BadDecideObjectException {
        try {
            this.mDescription = jSONObject;
            this.mExtras = jSONObject.getJSONObject("extras");
            this.mId = jSONObject.getInt("id");
            this.mMessageId = jSONObject.getInt("message_id");
            this.mBackgroundColor = jSONObject.getInt("bg_color");
            this.mBody = JSONUtils.optionalStringKey(jSONObject, "body");
            this.mBodyColor = jSONObject.optInt("body_color");
            this.mImageUrl = jSONObject.getString("image_url");
            this.mImage = Bitmap.createBitmap((int)500, (int)500, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            return;
        }
        catch (JSONException jSONException) {
            throw new BadDecideObjectException("Notification JSON was unexpected or bad", jSONException);
        }
    }

    static String sizeSuffixUrl(String string2, String string3) {
        Matcher matcher = FILE_EXTENSION_PATTERN.matcher(string2);
        if (matcher.find()) {
            string2 = matcher.replaceFirst(string3 + "$1");
        }
        return string2;
    }

    public int describeContents() {
        return 0;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public String getBody() {
        return this.mBody;
    }

    public int getBodyColor() {
        return this.mBodyColor;
    }

    JSONObject getCampaignProperties() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("campaign_id", this.getId());
            jSONObject.put("message_id", this.getMessageId());
            jSONObject.put("message_type", (Object)"inapp");
            jSONObject.put("message_subtype", (Object)this.getType().toString());
            return jSONObject;
        }
        catch (JSONException jSONException) {
            MPLog.e("MixpanelAPI.InAppNotif", "Impossible JSON Exception", jSONException);
            return jSONObject;
        }
    }

    protected JSONObject getExtras() {
        return this.mExtras;
    }

    public int getId() {
        return this.mId;
    }

    public Bitmap getImage() {
        return this.mImage;
    }

    public String getImage2xUrl() {
        return InAppNotification.sizeSuffixUrl(this.mImageUrl, "@2x");
    }

    public String getImage4xUrl() {
        return InAppNotification.sizeSuffixUrl(this.mImageUrl, "@4x");
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public int getMessageId() {
        return this.mMessageId;
    }

    public abstract Type getType();

    public boolean hasBody() {
        return this.mBody != null;
    }

    void setImage(Bitmap bitmap) {
        this.mImage = bitmap;
    }

    public String toString() {
        return this.mDescription.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mDescription.toString());
        parcel.writeString(this.mExtras.toString());
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mMessageId);
        parcel.writeInt(this.mBackgroundColor);
        parcel.writeString(this.mBody);
        parcel.writeInt(this.mBodyColor);
        parcel.writeString(this.mImageUrl);
        parcel.writeParcelable((Parcelable)this.mImage, n);
    }

    public static enum Type {
        UNKNOWN{

            public String toString() {
                return "*unknown_type*";
            }
        }
        ,
        MINI{

            public String toString() {
                return "mini";
            }
        }
        ,
        TAKEOVER{

            public String toString() {
                return "takeover";
            }
        };


    }

}

