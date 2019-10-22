/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.samsung.android.sdk.shealth.tracker;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Date;

final class TrackerTileData
implements Parcelable {
    public static final Parcelable.Creator<TrackerTileData> CREATOR = new Parcelable.Creator<TrackerTileData>(){

        public TrackerTileData createFromParcel(Parcel parcel) {
            return new TrackerTileData(parcel);
        }

        public TrackerTileData[] newArray(int n) {
            return new TrackerTileData[n];
        }
    };
    public ArrayList<InternalAction> mActionArray = new ArrayList(1);
    public InternalAction[] mActions;
    public int mContentColor;
    public InternalIntent mContentIntent;
    public String mContentUnit;
    public String mContentValue;
    public Date mDate;
    public String mIconResourceName;
    public String mPackageName;
    public int mTemplate;
    public String mTileId;
    public CharSequence mTitle;
    public String mTrackerId;

    public TrackerTileData() {
    }

    public TrackerTileData(Parcel arrinternalAction) {
        this.mPackageName = arrinternalAction.readString();
        this.mTileId = arrinternalAction.readString();
        this.mTrackerId = arrinternalAction.readString();
        this.mTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)arrinternalAction);
        this.mIconResourceName = arrinternalAction.readString();
        this.mContentValue = arrinternalAction.readString();
        this.mContentUnit = arrinternalAction.readString();
        this.mDate = new Date(arrinternalAction.readLong());
        this.mContentColor = arrinternalAction.readInt();
        if (arrinternalAction.readInt() != 0) {
            this.mContentIntent = (InternalIntent)InternalIntent.CREATOR.createFromParcel((Parcel)arrinternalAction);
        }
        if (arrinternalAction.readInt() != 0) {
            this.mActions = (InternalAction[])arrinternalAction.createTypedArray(InternalAction.CREATOR);
        }
        this.mTemplate = arrinternalAction.readInt();
        if (this.mActions != null) {
            for (InternalAction internalAction : this.mActions) {
                this.mActionArray.add(internalAction);
            }
        }
    }

    public static CharSequence safeCharSequence(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (charSequence instanceof Parcelable) {
            charSequence2 = charSequence.toString();
        }
        return charSequence2;
    }

    public TrackerTileData clone() {
        TrackerTileData trackerTileData = new TrackerTileData();
        trackerTileData.mPackageName = this.mPackageName;
        trackerTileData.mTileId = this.mTileId;
        trackerTileData.mTrackerId = this.mTrackerId;
        trackerTileData.mTitle = this.mTitle;
        trackerTileData.mIconResourceName = this.mIconResourceName;
        trackerTileData.mContentValue = this.mContentValue;
        trackerTileData.mContentUnit = this.mContentUnit;
        trackerTileData.mDate = this.mDate;
        trackerTileData.mContentColor = this.mContentColor;
        trackerTileData.mContentIntent = this.mContentIntent;
        if (this.mActions != null) {
            trackerTileData.mActions = new InternalAction[this.mActions.length];
            for (int i = 0; i < this.mActions.length; ++i) {
                trackerTileData.mActions[i] = this.mActions[i].clone();
            }
        }
        trackerTileData.mTemplate = this.mTemplate;
        return trackerTileData;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mTileId);
        parcel.writeString(this.mTrackerId);
        TextUtils.writeToParcel((CharSequence)this.mTitle, (Parcel)parcel, (int)n);
        parcel.writeString(this.mIconResourceName);
        parcel.writeString(this.mContentValue);
        parcel.writeString(this.mContentUnit);
        parcel.writeLong(this.mDate.getTime());
        parcel.writeInt(this.mContentColor);
        if (this.mContentIntent != null) {
            parcel.writeInt(1);
            this.mContentIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mActions != null) {
            parcel.writeInt(1);
            parcel.writeTypedArray((Parcelable[])this.mActions, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mTemplate);
    }

    public static class InternalAction
    implements Parcelable {
        public static final Parcelable.Creator<InternalAction> CREATOR = new Parcelable.Creator<InternalAction>(){

            public InternalAction createFromParcel(Parcel parcel) {
                return new InternalAction(parcel);
            }

            public InternalAction[] newArray(int n) {
                return new InternalAction[n];
            }
        };
        public InternalIntent mInternalIntent;
        public CharSequence mTitle;

        private InternalAction() {
        }

        private InternalAction(Parcel parcel) {
            this.mTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mInternalIntent = (InternalIntent)InternalIntent.CREATOR.createFromParcel(parcel);
        }

        public InternalAction(CharSequence charSequence, InternalIntent internalIntent) {
            this.mTitle = TrackerTileData.safeCharSequence(charSequence);
            this.mInternalIntent = internalIntent;
        }

        public InternalAction clone() {
            return new InternalAction(this.mTitle, this.mInternalIntent);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            TextUtils.writeToParcel((CharSequence)this.mTitle, (Parcel)parcel, (int)n);
            this.mInternalIntent.writeToParcel(parcel, n);
        }

    }

    public static class InternalIntent
    implements Parcelable {
        public static final Parcelable.Creator<InternalIntent> CREATOR = new Parcelable.Creator<InternalIntent>(){

            public InternalIntent createFromParcel(Parcel parcel) {
                return new InternalIntent(parcel);
            }

            public InternalIntent[] newArray(int n) {
                return new InternalIntent[n];
            }
        };
        public Intent mIntent;
        public int mType;

        /*
         * Enabled aggressive block sorting
         */
        public InternalIntent(int n, Intent intent) {
            n = n == 1 ? 1 : 0;
            this.mType = n;
            this.mIntent = intent;
        }

        public InternalIntent(Parcel parcel) {
            this.mType = parcel.readInt();
            this.mIntent = (Intent)Intent.CREATOR.createFromParcel(parcel);
        }

        public InternalIntent clone() {
            return new InternalIntent(this.mType, this.mIntent);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mType);
            this.mIntent.writeToParcel(parcel, 0);
        }

    }

}

